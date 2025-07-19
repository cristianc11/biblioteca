package com.alura.literatura.principal;

import com.alura.literatura.dto.AutorDTO;
import com.alura.literatura.dto.DatosLibrosDTO;
import com.alura.literatura.dto.LibroDTO;
import com.alura.literatura.model.Autor;
import com.alura.literatura.model.DatosLibro;
import com.alura.literatura.model.Libro;
//import com.alura.literatura.repositorio.LibroRepository;
import com.alura.literatura.repositorio.AutorRepository;
import com.alura.literatura.repositorio.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConversorDatos conversor = new ConversorDatos();
    private LibroRepository repositorio;
    private AutorRepository repositorioAutor;

   public Principal(LibroRepository repositoryLibro, AutorRepository repositorioAutor) {
       this.repositorio = repositoryLibro;
       this.repositorioAutor = repositorioAutor;
   }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
            
            
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    getDatosLibro();
                    break;
                case 2:
                    getListaLibros();
                    break;
                case 3:
                    getListaAutores();
                    break;
                case 4:
                    getListaAutoresAnio();
                    break;
                case 5:
                    getListaLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibrosDTO getDatosLibro() {
        System.out.println("Escriba el nombre del libro que desea buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumo.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));

        var datosLibros = conversor.obtenerDatos(json, DatosLibrosDTO.class);

        if (datosLibros.results().isEmpty()) {
            System.out.println("No se encontraron libros con ese nombre.");
            return null;
        }

        System.out.println("\nLibros encontrados:");
        for (int i = 0; i < datosLibros.results().size(); i++) {
            System.out.printf("%d. %s\n", i + 1, datosLibros.results().get(i).title());
        }

        System.out.println("Digite el número del libro que desea guardar:");
        var opcion = teclado.nextInt();
        teclado.nextLine();

        var libroDTO = datosLibros.results().get(opcion - 1);

        if (libroDTO.authors().isEmpty()) {
            System.out.println("Este libro no tiene autor asociado, no se guardará.");
            return null;
        }

        var autorDTO = libroDTO.authors().get(0);
        Optional<Autor> autorExistente = repositorioAutor.findByNombre(autorDTO.name());

        Autor autor;
        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor(autorDTO);
            repositorioAutor.save(autor);
        }

        Libro libro = new Libro(libroDTO); // Aquí no se debe crear autor
        libro.setAutor(autor);
        repositorio.save(libro);

        autor.agregarLibro(libro);
        repositorioAutor.save(autor);

        System.out.println("Libro y autor guardados exitosamente.");
        return datosLibros;
    }



    public void getListaLibros(){
        List<Libro> libros = repositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos.");
            return;
        }

        System.out.println("\n--- Libros Guardados ---");
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());

            Autor autor = libro.getAutor();
            if (autor != null) {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido"));
                System.out.println("Fallecimiento: " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "Desconocido"));
            } else {
                System.out.println("Autor: No registrado");
            }

            System.out.println("-------------------------");
        }
    }


    public void getListaAutores() {
        List<Autor> autores = repositorioAutor.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados en la base de datos.");
            return;
        }

        System.out.println("\n--- Autores Guardados ---");
        for (Autor autor : autores) {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " +
                    (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocida"));
            System.out.println("Fecha de fallecimiento: " +
                    (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "Desconocida"));

            List<Libro> libros = autor.getLibros();
            if (libros != null && !libros.isEmpty()) {
                System.out.println("Libros asociados:");
                for (Libro libro : libros) {
                    System.out.println("- " + libro.getTitulo());
                }
            } else {
                System.out.println("Libros asociados: Ninguno");
            }

            System.out.println("-------------------------");
        }
    }


    public void getListaAutoresAnio() {
        System.out.println("Ingresa el año que deseas buscar: ");
        try {
            int anio = Integer.parseInt(teclado.nextLine());
            LocalDate anioInicio = LocalDate.of(anio, 1, 1);
            LocalDate anioFinal = LocalDate.of(anio, 12, 31);

            List<Autor> autores = repositorioAutor.buscarAutoresVivosEnAnio(anioInicio, anioFinal);

            if (autores.isEmpty()) {
                System.out.println("No hay autores guardados en la base de datos con el año buscado.");
                return;
            }

            System.out.println("\n--- Autores Guardados ---");
            for (Autor autor : autores) {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocida"));
                System.out.println("Fecha de fallecimiento: " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "Desconocida"));

                List<Libro> libros = autor.getLibros();
                if (libros != null && !libros.isEmpty()) {
                    System.out.println("Libros asociados:");
                    for (Libro libro : libros) {
                        System.out.println("- " + libro.getTitulo());
                    }
                } else {
                    System.out.println("Libros asociados: Ninguno");
                }

                System.out.println("-------------------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Ingresa un número como 1985.");
        }
    }

    public void getListaLibrosIdioma(){
       String idioma;
        do{
            System.out.println("Ingresa las iniciales del idioma de libros que deseas buscar: ");
            idioma = teclado.nextLine();
            if (idioma.length() != 2){
                System.out.println("Debes ingresar exactamente 2 caracteres. Intenta de nuevo.");
            }
        }while (idioma.length() != 2);

        List<Libro> libros = repositorio.buscarLibrosIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos con ese idioma.");
            return;
        }


        System.out.println("\n--- Libros Guardados ---");
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());

            Autor autor = libro.getAutor();
            if (autor != null) {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido"));
                System.out.println("Fallecimiento: " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "Desconocido"));
            } else {
                System.out.println("Autor: No registrado");
            }

            System.out.println("-------------------------");
        }
    }

}
