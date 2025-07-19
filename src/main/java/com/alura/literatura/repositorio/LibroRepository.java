package com.alura.literatura.repositorio;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE LOWER(l.idioma) LIKE LOWER(CONCAT('%', :idioma, '%'))")
    List<Libro> buscarLibrosIdioma(@Param("idioma") String idioma);
}

