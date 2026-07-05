package com.academia.batch.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad alternativa (si se necesita) para el paquete repository.
@Entity
@Table(name = "estudiantes_procesados")
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String grupo;
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;

    // Constructor vacío
    public EstudianteEntity() {
    }

    // Constructor con parámetros
    public EstudianteEntity(String nombre, String grupo, double nota1, double nota2, double nota3) {
        this.nombre = nombre;
        this.grupo = grupo;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.promedio = calcularPromedio();
    }

    // Método para calcular el promedio
    private double calcularPromedio() {
        return (nota1 + nota2 + nota3) / 3.0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
        this.promedio = calcularPromedio();
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
        this.promedio = calcularPromedio();
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
        this.promedio = calcularPromedio();
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    // toString que muestre nombre, grupo y promedio
    @Override
    public String toString() {
        return "EstudianteEntity{" +
                "nombre='" + nombre + '\'' +
                ", grupo='" + grupo + '\'' +
                ", promedio=" + String.format("%.2f", promedio) +
                '}';
    }
}