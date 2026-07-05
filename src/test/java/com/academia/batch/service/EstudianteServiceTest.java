package com.academia.batch.service;

import com.academia.batch.model.Estudiante;
import com.academia.batch.repository.EstudianteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para EstudianteService")
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    @DisplayName("Debe contar 2 estudiantes aprobados")
    void contarAprobadosDebeRetornarDos() {
        Estudiante aprobado1 = new Estudiante("Juan", "A", 80.0, 75.0, 90.0);
        Estudiante aprobado2 = new Estudiante("Maria", "B", 70.0, 70.0, 70.0);
        Estudiante reprobado = new Estudiante("Pedro", "C", 60.0, 65.0, 50.0);

        when(estudianteRepository.findAll()).thenReturn(List.of(aprobado1, aprobado2, reprobado));

        long resultado = estudianteService.contarAprobados();

        assertEquals(2L, resultado);
    }
}
