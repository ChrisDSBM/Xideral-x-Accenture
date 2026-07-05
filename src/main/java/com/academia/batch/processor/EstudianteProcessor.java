package com.academia.batch.processor;
import com.academia.batch.model.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Processor de Spring Batch que implementa ItemProcessor<Estudiante, Estudiante>.
// En el metodo process: calcula el promedio como (nota1 + nota2 + nota3) / 3,
// asigna el promedio al estudiante con setPromedio, registra un log con SLF4J
// "Step 1 - Procesando: {estudiante}" y devuelve el estudiante.

public class EstudianteProcessor implements ItemProcessor<Estudiante, Estudiante> {

    private static final Logger logger = LoggerFactory.getLogger(EstudianteProcessor.class);

    @Override
    public Estudiante process(Estudiante estudiante) throws Exception {
        // Calcular el promedio
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3.0;

        // Asignar el promedio al estudiante
        estudiante.setPromedio(promedio);

        // Registrar log con SLF4J
        logger.info("Step 1 - Procesando: {}", estudiante);

        // Devolver el estudiante
        return estudiante;
    }
}