package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
//    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    //List<Libro> findAll();

//    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autores a WHERE a.nombre = :autor")
//    List<Libro> findBynAutor(String autor);
//
//    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autores a WHERE YEAR(a.fechaNacimiento) <= :year AND (a.fechaDefuncion IS NULL OR YEAR(a.fechaDefuncion) >= :year)")
//    List<Libro> findLibrosPorAutoresVivosEnAnio(int year);
//
//    List<Libro> findByIdioma(String idioma);

//    @Query(value = "SELECT DISTINCT autor FROM libros " +
//            "WHERE (:ano >= CAST(año_nacimiento AS INTEGER) " +
//            "AND :ano <= CAST(año_muerte AS INTEGER)) " +
//            "AND año_nacimiento != 'Desconocido' " +
//            "AND año_muerte != 'Desconocido'",
//            nativeQuery = true)
//    List<String> findAutoresVivosEnAno(int ano);

}
