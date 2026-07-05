package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests para EstudianteProcessor")
class EstudianteProcessorTest {

    private EstudianteProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new EstudianteProcessor();
    }

    @Test
    @DisplayName("Debe calcular correctamente el promedio (90, 85, 80)")
    void testCalcularPromedioValores() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Juan", "A", 90.0, 85.0, 80.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        double expectedPromedio = (90.0 + 85.0 + 80.0) / 3.0; // 85.0
        assertEquals(expectedPromedio, result.getPromedio(), 0.01,
                     "El promedio debe ser 85.0");
    }

    @Test
    @DisplayName("Debe calcular correctamente el promedio con valores iguales")
    void testCalcularPromedioValoresIguales() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Maria", "B", 75.0, 75.0, 75.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        assertEquals(75.0, result.getPromedio(), 0.01,
                     "El promedio debe ser 75.0 cuando todas las notas son iguales");
    }

    @Test
    @DisplayName("Debe calcular correctamente el promedio con valores decimales")
    void testCalcularPromedioDecimales() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Pedro", "C", 79.5, 80.5, 81.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        double expectedPromedio = (79.5 + 80.5 + 81.0) / 3.0; // 80.333...
        assertEquals(expectedPromedio, result.getPromedio(), 0.01,
                     "El promedio debe calcularse correctamente con decimales");
    }

    @Test
    @DisplayName("Debe devolver el mismo estudiante procesado")
    void testReturnEstudiante() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Ana", "D", 100.0, 100.0, 100.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        assertEquals(estudiante.getNombre(), result.getNombre(),
                     "Debe devolver el mismo estudiante");
        assertEquals(estudiante.getGrupo(), result.getGrupo(),
                     "Debe mantener el grupo");
    }

    @Test
    @DisplayName("Debe calcular promedio con notas mínimas")
    void testCalcularPromedioMinimoValores() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos", "E", 0.0, 0.0, 0.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        assertEquals(0.0, result.getPromedio(), 0.01,
                     "El promedio de notas 0 debe ser 0");
    }

    @Test
    @DisplayName("Debe calcular promedio con notas máximas")
    void testCalcularPromedioMaximoValores() throws Exception {
        // Arrange
        Estudiante estudiante = new Estudiante("Laura", "F", 100.0, 100.0, 100.0);

        // Act
        Estudiante result = processor.process(estudiante);

        // Assert
        assertEquals(100.0, result.getPromedio(), 0.01,
                     "El promedio de notas 100 debe ser 100");
    }
}

