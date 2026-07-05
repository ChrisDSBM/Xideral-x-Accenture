package com.academia.batch.config;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.processor.EstudianteProcessor;
import com.academia.batch.processor.ReporteEstudianteProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoTemplate mongoTemplate;

    // ===== STEP 1: Leer CSV, Procesar y Escribir en MySQL =====

    @Bean
    public FlatFileItemReader<Estudiante> estudianteItemReader() {
        FlatFileItemReader<Estudiante> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("estudiantes.csv"));
        reader.setLineMapper(new DefaultLineMapper<Estudiante>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("nombre", "grupo", "nota1", "nota2", "nota3");
                setDelimiter(",");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Estudiante>() {{
                setTargetType(Estudiante.class);
            }});
        }});
        reader.setLinesToSkip(1); // Saltar la línea de encabezado
        return reader;
    }

    @Bean
    public EstudianteProcessor estudianteProcessor() {
        return new EstudianteProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Estudiante> estudianteItemWriter() {
        JdbcBatchItemWriter<Estudiante> writer = new JdbcBatchItemWriter<>();
        writer.setJdbcTemplate(new NamedParameterJdbcTemplate(dataSource));
        writer.setSql("INSERT INTO estudiantes_procesados " +
                "(nombre, grupo, nota1, nota2, nota3, promedio) " +
                "VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)");
        writer.setItemSqlParameterSourceProvider(item -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("nombre", item.getNombre());
            params.addValue("grupo", item.getGrupo());
            params.addValue("nota1", item.getNota1());
            params.addValue("nota2", item.getNota2());
            params.addValue("nota3", item.getNota3());
            params.addValue("promedio", item.getPromedio());
            return params;
        });
        return writer;
    }

    @Bean(name = "paso1")
    public Step paso1(FlatFileItemReader<Estudiante> reader,
                      EstudianteProcessor processor,
                      JdbcBatchItemWriter<Estudiante> writer) {
        return new StepBuilder("paso1", jobRepository)
                .<Estudiante, Estudiante>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // ===== STEP 2: Leer de MySQL, Procesar y Escribir en MongoDB =====

    @Bean
    public JdbcCursorItemReader<Estudiante> estudianteJdbcCursorItemReader() {
        JdbcCursorItemReader<Estudiante> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT nombre, grupo, promedio FROM estudiantes_procesados");
        reader.setRowMapper((rs, rowNum) -> {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setGrupo(rs.getString("grupo"));
            estudiante.setPromedio(rs.getDouble("promedio"));
            return estudiante;
        });
        return reader;
    }

    @Bean
    public ReporteEstudianteProcessor reporteEstudianteProcessor() {
        return new ReporteEstudianteProcessor();
    }

    @Bean
    public ItemWriter<EstudianteReporte> mongoItemWriter() {
        return items -> {
            if (items != null && !items.isEmpty()) {
                mongoTemplate.insert(items, "reportes_estudiantes");
            }
        };
    }

    @Bean(name = "paso2")
    public Step paso2(JdbcCursorItemReader<Estudiante> reader,
                      ReporteEstudianteProcessor processor,
                      ItemWriter<EstudianteReporte> writer) {
        return new StepBuilder("paso2", jobRepository)
                .<Estudiante, EstudianteReporte>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // ===== JOB =====

    @Bean
    public Job procesarCalificacionesJob(Step paso1, Step paso2) {
        return new JobBuilder("procesarCalificacionesJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(paso1)
                .next(paso2)
                .build();
    }
}









