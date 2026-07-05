package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Processor que implementa ItemProcessor<Estudiante, EstudianteReporte>.
// Convierte un Estudiante en un EstudianteReporte copiando nombre, grupo y promedio,
// y asigna estado "APROBADO" si el promedio es >= 70, o "REPROBADO" si es menor.
// Loguea "Step 2 - Reporte: {reporte}" y devuelve el reporte.

public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

    private static final Logger logger = LoggerFactory.getLogger(ReporteEstudianteProcessor.class);
    private static final double CALIFICACION_MINIMA = 70.0;

    @Override
    public EstudianteReporte process(Estudiante estudiante) throws Exception {
        // Crear un nuevo EstudianteReporte
        EstudianteReporte reporte = new EstudianteReporte();

        // Copiar datos del estudiante
        reporte.setNombre(estudiante.getNombre());
        reporte.setGrupo(estudiante.getGrupo());
        reporte.setPromedio(estudiante.getPromedio());

        // Asignar estado según el promedio
        if (estudiante.getPromedio() >= CALIFICACION_MINIMA) {
            reporte.setEstado("APROBADO");
        } else {
            reporte.setEstado("REPROBADO");
        }

        // Loguear el reporte
        logger.info("Step 2 - Reporte: {}", reporte);

        // Devolver el reporte
        return reporte;
    }
}
