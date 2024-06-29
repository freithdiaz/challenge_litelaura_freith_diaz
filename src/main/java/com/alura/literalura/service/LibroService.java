package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private static final String URL_BASE = "https://gutendex.com/books/?search="; // Cambia esto a tu URL base real

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ConvierteDatos conversor;

    @Autowired
    private ConsumoAPI consumoApi;

    public Libro buscarYRegistrarLibro(String titulo) throws IOException, InterruptedException {
        var url = URL_BASE + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        var json = consumoApi.obtenerDatos(url);
        DatosLibros datosLibros = conversor.obtenerDatos(json, DatosLibros.class);

        List<Libro1> libros1 = datosLibros.libros();

        if (!libros1.isEmpty()) {
            Libro1 libro1 = libros1.get(0); // Tomamos el primer libro encontrado

            Libro libro = new Libro();
            libro.setId((long) libro1.hashCode()); // Puedes ajustar esto si necesitas una lógica de ID diferente
            libro.setTitulo(libro1.titulo());
            libro.setAutor(libro1.autores().stream().map(a -> a.nombre()).collect(Collectors.joining(", ")));
            libro.setAñoNacimiento(libro1.autores().stream().map(a -> a.añoNacimiento() != null ? a.añoNacimiento().toString() : "Desconocido").collect(Collectors.joining(", ")));
            libro.setAñoMuerte(libro1.autores().stream().map(a -> a.añoMuerte() != null ? a.añoMuerte().toString() : "Desconocido").collect(Collectors.joining(", ")));
            libro.setIdioma(libro1.idiomas().isEmpty() ? "Desconocido" : libro1.idiomas().get(0));
            libro.setNumeroDescargas(libro1.cantidadDescargas());

            // Guardamos el libro en el repositorio
            return libroRepository.save(libro);
        }
        return null;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<AutorInfo> listarAutoresVivosEnAno(int ano) {
        List<Libro> libros = listarLibros();
        return libros.stream()
                .filter(libro -> {
                    String añoNacimiento = libro.getAñoNacimiento();
                    String añoMuerte = libro.getAñoMuerte();

                    // Convertir los años a números enteros, manejar "Desconocido"
                    int nacimiento = convertirAnio(añoNacimiento);
                    int muerte = convertirAnio(añoMuerte);

                    return ano >= nacimiento && ano <= muerte;
                })
                .collect(Collectors.groupingBy(Libro::getAutor))
                .entrySet()
                .stream()
                .map(entry -> new AutorInfo(
                        entry.getKey(),
                        entry.getValue().get(0).getAñoNacimiento(),
                        entry.getValue().get(0).getAñoMuerte(),
                        entry.getValue().stream().map(Libro::getTitulo).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    private int convertirAnio(String anioStr) {
        try {
            return anioStr != null && !anioStr.equals("Desconocido") ? Integer.parseInt(anioStr) : Integer.MIN_VALUE;
        } catch (NumberFormatException e) {
            // Manejar el error o retornar un valor por defecto
            return Integer.MIN_VALUE;
        }
    }

}
