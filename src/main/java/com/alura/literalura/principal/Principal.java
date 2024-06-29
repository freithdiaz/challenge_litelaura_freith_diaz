package com.alura.literalura.principal;

import com.alura.literalura.model.AutorInfo;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libro;
import com.alura.literalura.model.Libro1;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    private LibroService libroService;

    public void muestraElMenu() throws IOException, InterruptedException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma

                    0 - Salir
                    """;
            System.out.println(menu);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } else {
                System.out.println("Por favor, introduzca un número válido.");
                teclado.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }

    private DatosLibros getDatosLibro() throws IOException, InterruptedException {
        System.out.print("Ingrese el título del libro: ");
        String titulo = teclado.nextLine();
        var url = URL_BASE + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        var json = consumoApi.obtenerDatos(url);
        DatosLibros datosLibros = conversor.obtenerDatos(json, DatosLibros.class);
        List<Libro1> libros = datosLibros.libros();

        return datosLibros;
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException {
        System.out.print("Ingrese el título del libro: ");
        String titulo = teclado.nextLine();

        // Verificar si el libro ya está registrado
        Libro libro = libroService.buscarYRegistrarLibro(titulo);

        if (libro != null) {
            // Si el libro ya está en la base de datos, mostrar la información del libro existente
            mostrarInformacionLibro(libro);
        } else {
            // Si el libro no está en la base de datos, intentar registrarlo
            libro = libroService.buscarYRegistrarLibro(titulo);

            if (libro != null) {
                // Mostrar información del libro registrado
                mostrarInformacionLibro(libro);
            } else {
                // Si no se encontró ni se pudo registrar el libro
                System.out.println("Libro no encontrado ");
            }
        }
    }

    private void mostrarInformacionLibro(Libro libro) {
        // Mostrar la información del libro
        System.out.println("""
            ------INFORMACIÓN DEL LIBRO------
            Título: %s
            Autor: %s
            Idioma: %s
            Número de descargas: %s
            ---------------------------------
            """.formatted(libro.getTitulo(), libro.getAutor(), libro.getIdioma(), libro.getNumeroDescargas()));
    }


    private void listarLibros() {
        List<Libro> libros = libroService.listarLibros();
        libros.forEach(libro ->  System.out.println("""
                    ------LIBRO------
                    Titulo: %s
                    Autor: %s
                    Idioma: %s
                    Numero de descargas: %s
                    -----------------
                    ----------
                    """.formatted(libro.getTitulo(), libro.getAutor(), libro.getIdioma(), libro.getNumeroDescargas())));
    }


    private void listarAutores() {
        List<Libro> libros = libroService.listarLibros();
        libros.stream()
                .collect(Collectors.groupingBy(Libro::getAutor))
                .forEach((autor, librosDelAutor) -> {
                    System.out.println("""
                      Autor: %s
                      Fecha de nacimiento: %s
                      Fecha de fallecimiento: %s
                      Libros:
                      """.formatted(
                            autor,
                            librosDelAutor.get(0).getAñoNacimiento(),
                            librosDelAutor.get(0).getAñoMuerte() == null ? "N/A" : librosDelAutor.get(0).getAñoMuerte()
                    ));
                    librosDelAutor.forEach(libro -> System.out.println(String.format("""
                      Titulo: %s
                      Idioma: %s
                      Numero de descargas: %d
                      -----------------
                      """,
                            libro.getTitulo(),
                            libro.getIdioma(),
                            libro.getNumeroDescargas()
                    )));
                    System.out.println("----------");

                });}
    private void listarAutoresVivos() {
        System.out.print("Ingrese el año: ");
        int ano = Integer.parseInt(teclado.nextLine());

        List<AutorInfo> autoresVivos = libroService.listarAutoresVivosEnAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano + ".");
        } else {
            System.out.println("Autores vivos en el año " + ano + ":");
            autoresVivos.forEach(autor -> {
                System.out.println(String.format("""
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: %s
                """,
                        autor.getNombre(),
                        autor.getAñoNacimiento(),
                        autor.getAñoMuerte() == null ? "N/A" : autor.getAñoMuerte(),
                        String.join(", ", autor.getLibros())
                ));
            });
        }
    }

    private void listarLibrosPorIdioma() {
        var idioma1 = """
                    Indique el idioma a consultar:
                    es - español 
                    en - ingles
                    fr - frances
                    pt - portugues
                    """;
        System.out.print(idioma1);
        String idioma = teclado.nextLine();
        List<Libro> libros = libroService.listarLibros();
        libros.stream()
                .filter(libro -> libro.getIdioma().equalsIgnoreCase(idioma))
                .forEach(libro -> System.out.println("""
                    ------LIBRO------
                    Titulo: %s
                    Autor: %s
                    Idioma: %s
                    Numero de descargas: %s
                    -----------------
                    ----------
                    """.formatted(libro.getTitulo(), libro.getAutor(), libro.getIdioma(), libro.getNumeroDescargas())));
    }
}