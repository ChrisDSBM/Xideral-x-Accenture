package com.academia.batch.service;

import com.academia.batch.model.Estudiante;
import com.academia.batch.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio que proporciona operaciones de negocio relacionadas con estudiantes.
 *
 * <p>Esta clase es un componente Spring Service que gestiona la lógica de negocio
 * para los estudiantes, incluyendo cálculos estadísticos como el conteo de estudiantes
 * aprobados. Utiliza inyección de dependencia por constructor para acceder al repositorio
 * de estudiantes.</p>
 *
 * @author Academia
 * @version 1.0
 * @since 1.0
 */
@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    /**
     * Constructor que inyecta la dependencia de {@link EstudianteRepository}.
     *
     * @param estudianteRepository el repositorio de estudiantes a ser inyectado.
     *                             No puede ser nulo.
     */
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Cuenta la cantidad de estudiantes con promedio mayor o igual a 70.0 (aprobados).
     *
     * <p>Este método recupera todos los estudiantes del repositorio, aplica un filtro
     * para seleccionar aquellos cuyo promedio sea mayor o igual a 70.0, y devuelve
     * el total de estudiantes aprobados. Utiliza operaciones de stream para procesar
     * la colección de manera funcional.</p>
     *
     * @return el número de estudiantes aprobados (promedio >= 70.0)
     */
    public long contarAprobados() {
        return estudianteRepository.findAll()
                .stream()
                .filter(estudiante -> estudiante.getPromedio() >= 70.0)
                .count();
    }
}