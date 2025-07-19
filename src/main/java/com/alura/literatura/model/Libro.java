package com.alura.literatura.model;

import com.alura.literatura.dto.AutorDTO;
import com.alura.literatura.dto.LibroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    @Column(name = "idioma")
    private String idioma;
    private Integer numeroDeDescargas;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.title();
        this.idioma = libroDTO.languages().isEmpty() ? "Desconocido" : libroDTO.languages().get(0);
        this.numeroDeDescargas = libroDTO.download_count();

//        if (!libroDTO.authors().isEmpty()) {
//            AutorDTO autorDTO = libroDTO.authors().get(0);
//            this.autor = new Autor(autorDTO);
//        }
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getNumeroDeDescargas() { return numeroDeDescargas; }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) { this.numeroDeDescargas = numeroDeDescargas; }

    public Autor getAutor() { return autor; }

    public void setAutor(Autor autor) { this.autor = autor; }
}
