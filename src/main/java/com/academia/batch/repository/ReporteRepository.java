package com.academia.batch.repository;

import com.academia.batch.model.EstudianteReporte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interfaz ReporteRepository que extiende MongoRepository<EstudianteReporte, String>
// con metodos para consultar reportes en MongoDB

@Repository
public interface ReporteRepository extends MongoRepository<EstudianteReporte, String> {

    // Buscar reportes por grupo
    List<EstudianteReporte> findByGrupo(String grupo);

    // Buscar reportes por estado (APROBADO o REPROBADO)
    List<EstudianteReporte> findByEstado(String estado);

    // Buscar reportes por grupo y estado
    List<EstudianteReporte> findByGrupoAndEstado(String grupo, String estado);

    // Contar reportes por estado
    long countByEstado(String estado);

    // Contar reportes por grupo
    long countByGrupo(String grupo);
}