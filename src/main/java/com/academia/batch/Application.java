package com.academia.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// Clase principal de Spring Boot con @SpringBootApplication y el metodo main
// que arranca la aplicacion con SpringApplication.run.
// Configuración explícita para separar repositorios JPA y MongoDB.

@SpringBootApplication
@EnableJpaRepositories(
    basePackages = "com.academia.batch.repository",
    includeFilters = @ComponentScan.Filter(
        type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
        classes = com.academia.batch.repository.EstudianteRepository.class
    )
)
@EnableMongoRepositories(
    basePackages = "com.academia.batch.repository",
    includeFilters = @ComponentScan.Filter(
        type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
        classes = com.academia.batch.repository.ReporteRepository.class
    )
)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

