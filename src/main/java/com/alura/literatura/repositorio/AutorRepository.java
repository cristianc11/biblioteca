package com.alura.literatura.repositorio;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAll();
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE " +
            "a.fechaNacimiento <= :anioFinal AND " +
            "(a.fechaMuerte IS NULL OR a.fechaMuerte >= :anioInicio)")
    List<Autor> buscarAutoresVivosEnAnio(@Param("anioInicio") LocalDate anioInicio,
                                         @Param("anioFinal") LocalDate anioFinal);
}
