package com.aluracursos.desafio_literalura.repositorio;

import com.aluracursos.desafio_literalura.entidad.Autor;
import com.aluracursos.desafio_literalura.entidad.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepositorioInterface extends JpaRepository<Libro, Long> {

    // Método para contar libros por idioma
    int contarPorIdioma(String idioma);


}
