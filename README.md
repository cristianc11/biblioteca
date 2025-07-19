# 📚 Aplicación de Gestión de Libros - Alura Literaria

Esta aplicación permite registrar y consultar libros junto con sus autores, utilizando una fuente de datos externa (como la API de Gutendex). Está desarrollada en Java con Spring Boot, JPA y una base de datos relacional (como H2, MySQL o PostgreSQL).

## 🛠️ Tecnologías usadas
- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Base de datos relacional (H2, MySQL, etc.)
- Maven
- API externa: Gutendex

## 🧩 Funcionalidades principales
- Buscar libros por título y guardarlos en base de datos
- Registrar automáticamente el autor del libro si no existe
- Consultar todos los libros guardados
- Listar libros filtrando por idioma
- Mostrar detalles del autor: nombre, fecha de nacimiento y muerte
- Evita la duplicación de autores ya existentes

## ▶️ Cómo usar la aplicación
1. Ejecuta el proyecto (puedes hacerlo desde tu IDE o usando Maven).
2. En consola, verás un menú interactivo que te permitirá:
  - Buscar libros por título en la API
  - Listar libros almacenados en la BD
  - Listar autores almacenados en la BD
  - Filtrar autores vivos en determinado año
  - Filtrar libros por idioma (por ejemplo: en, es, fr)
3. Los datos se guardan en una base de datos conectada vía JPA.

## 📁 Estructura principal del proyecto
<pre>
com.alura.literatura
├── dto
│   ├── AutorDTO.java
│   └── DatosLibroDTO.java
|   └── LibroDTO.java
|
├── model
│   ├── Libro.java
│   └── Autor.java
|   └── DatosLibro.java
│
├── principal
│   ├── Principal.java
|
├── repositorio
│   ├── LibroRepository.java
│   └── AutorRepository.java
│
├── service
│   └── ConsumoAPI.java
|   └── ConversorDatos.java
|   └── IConversorDatos.java
│
└── LiteraturaApplication.java
</pre>

## 📌 Notas
- Si el libro ya existe, no se vuelve a guardar.
- Los autores se validan por nombre y se asocian correctamente con los libros.
