package com.academia.batch.controller;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.ReporteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteRepository reporteRepository;

    public ReporteController(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    // GET /api/reportes: Lista todos los reportes almacenados en MongoDB
    @GetMapping
    public List<EstudianteReporte> listarTodos() {
        // Al devolver la lista directamente, Spring retorna 200 OK por defecto
        return reporteRepository.findAll();
    }

    // GET /api/reportes/estado/{estado}: Devuelve reportes filtrados por APROBADO o REPROBADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EstudianteReporte>> listarPorEstado(@PathVariable String estado) {
        // Convertimos el estado a mayúsculas para asegurar que coincida con los datos en la base
        String estadoMayusculas = estado.toUpperCase();
        List<EstudianteReporte> reportes = reporteRepository.findByEstado(estadoMayusculas);

        // Retornamos 200 OK con la lista filtrada
        return ResponseEntity.ok(reportes);
    }
}