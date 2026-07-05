package com.academia.batch.controller;

import com.academia.batch.model.Estudiante;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteRepository estudianteRepository, EstudianteService estudianteService) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<Estudiante> listarTodos() {
        // Al devolver una lista directamente, Spring asume automáticamente el código 200 OK.
        return estudianteRepository.findAll();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        // ResponseEntity permite manejar el caso de éxito (200) y el caso de no encontrado (404)
            
        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aprobados/total")
    public Map<String, Long> obtenerTotalAprobados() {
        long total = estudianteService.contarAprobados();
        return Collections.singletonMap("totalAprobados", total);
    }

    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante estudiante) {
        Estudiante creado = estudianteRepository.save(estudiante);
        // Retornamos 201 Created para indicar que el recurso se creó exitosamente.
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> reemplazar(@PathVariable Long id, @RequestBody Estudiante estudianteDetalle) {
        return estudianteRepository.findById(id).map(estudiante -> {
            estudiante.setNombre(estudianteDetalle.getNombre());
            estudiante.setGrupo(estudianteDetalle.getGrupo());
            estudiante.setNota1(estudianteDetalle.getNota1());
            estudiante.setNota2(estudianteDetalle.getNota2());
            estudiante.setNota3(estudianteDetalle.getNota3());
            estudiante.setPromedio(estudianteDetalle.getPromedio());
            return ResponseEntity.ok(estudianteRepository.save(estudiante));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarGrupo(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        return estudianteRepository.findById(id).map(estudiante -> {
            if (updates.containsKey("grupo")) {
                estudiante.setGrupo(updates.get("grupo"));
            }
            return ResponseEntity.ok(estudianteRepository.save(estudiante));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            // Retornamos 204 No Content porque la operación fue exitosa pero no hay cuerpo de respuesta.
            return ResponseEntity.noContent().build();
        }
        // Retornamos 404 Not Found si el ID no existe en la base de datos.
        return ResponseEntity.notFound().build();
    }
}