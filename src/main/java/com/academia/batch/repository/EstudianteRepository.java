package com.academia.batch.repository;

import com.academia.batch.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Estudiante.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Devuelve todos los estudiantes de un grupo dado
    List<Estudiante> findByGrupo(String grupo);
}
