package com.alura.literatura.model;

import com.alura.literatura.dto.AutorDTO;
import com.alura.literatura.model.Libro;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDate fechaNacimiento;
    private LocalDate fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(AutorDTO autorDTO) {
        this.nombre = autorDTO.name();
        this.fechaNacimiento = autorDTO.birth_year() != null ? LocalDate.of(autorDTO.birth_year(), 1, 1) : null;
        this.fechaMuerte = autorDTO.death_year() != null ? LocalDate.of(autorDTO.death_year(), 1, 1) : null;
    }

    private LocalDate parseFecha(String fecha) {
        try {
            if (fecha == null || fecha.isBlank()) return null;
            return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy"));
        } catch (Exception e) {
            return null;
        }
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDate getFechaMuerte() { return fechaMuerte; }

    public void setFechaMuerte(LocalDate fechaMuerte) { this.fechaMuerte = fechaMuerte; }

    public List<Libro> getLibros() {
        return libros;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }
}
