package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Tests para ReporteEstudianteProcessor")
class ReporteEstudianteProcessorTest {

    private ReporteEstudianteProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new ReporteEstudianteProcessor();
    }

    @Test
    @DisplayName("Debe asignar estado APROBADO cuando promedio es 70.0")
    void testEstadoAprobadoPromedio70() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Juan", "A", 70.0, 70.0, 70.0);
        estudiante.setPromedio(70.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("APROBADO", result.getEstado(),
                     "Estado debe ser APROBADO cuando promedio es 70.0");
    }

    @Test
    @DisplayName("Debe asignar estado REPROBADO cuando promedio es 69.9")
    void testEstadoReprobadoPromedio69_9() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Maria", "B", 69.9, 69.9, 69.9);
        estudiante.setPromedio(69.9);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("REPROBADO", result.getEstado(),
                     "Estado debe ser REPROBADO cuando promedio es 69.9");
    }

    @Test
    @DisplayName("Debe asignar estado APROBADO cuando promedio es mayor a 70")
    void testEstadoAprobadoPromedioMayorA70() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Pedro", "C", 85.0, 90.0, 80.0);
        estudiante.setPromedio(85.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("APROBADO", result.getEstado(),
                     "Estado debe ser APROBADO cuando promedio es mayor a 70");
    }

    @Test
    @DisplayName("Debe asignar estado REPROBADO cuando promedio es menor a 70")
    void testEstadoReprobadoPromedioMenorA70() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Ana", "D", 50.0, 60.0, 55.0);
        estudiante.setPromedio(55.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("REPROBADO", result.getEstado(),
                     "Estado debe ser REPROBADO cuando promedio es menor a 70");
    }

    @Test
    @DisplayName("Debe copiar correctamente los datos del estudiante al reporte")
    void testCopiarDatosEstudiante() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos", "E", 75.0, 80.0, 78.0);
        estudiante.setPromedio(77.666666);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("Carlos", result.getNombre(),
                     "Debe copiar el nombre");
        assertEquals("E", result.getGrupo(),
                     "Debe copiar el grupo");
        assertEquals(77.666666, result.getPromedio(), 0.001,
                     "Debe copiar el promedio");
    }

    @Test
    @DisplayName("Debe crear un EstudianteReporte válido (no null)")
    void testCrearReporteValido() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Laura", "F", 100.0, 100.0, 100.0);
        estudiante.setPromedio(100.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertNotNull(result, "El reporte no debe ser null");
        assertNotNull(result.getNombre(), "El nombre no debe ser null");
        assertNotNull(result.getGrupo(), "El grupo no debe ser null");
        assertNotNull(result.getEstado(), "El estado no debe ser null");
    }

    @Test
    @DisplayName("Debe asignar estado REPROBADO cuando promedio es 0")
    void testEstadoReprobadoPromedioCero() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("David", "G", 0.0, 0.0, 0.0);
        estudiante.setPromedio(0.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("REPROBADO", result.getEstado(),
                     "Estado debe ser REPROBADO cuando promedio es 0");
    }

    @Test
    @DisplayName("Debe asignar estado APROBADO cuando promedio es 100")
    void testEstadoAprobadoPromedio100() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Sophia", "H", 100.0, 100.0, 100.0);
        estudiante.setPromedio(100.0);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("APROBADO", result.getEstado(),
                     "Estado debe ser APROBADO cuando promedio es 100");
    }

    @Test
    @DisplayName("Debe asignar estado APROBADO cuando promedio está en límite superior (70.01)")
    void testEstadoAprobadoLimiteSuperior() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Miguel", "I", 70.01, 70.01, 70.01);
        estudiante.setPromedio(70.01);

        // Act
        EstudianteReporte result = processor.process(estudiante);

        // Assert
        assertEquals("APROBADO", result.getEstado(),
                     "Estado debe ser APROBADO cuando promedio es 70.01");
    }
}

