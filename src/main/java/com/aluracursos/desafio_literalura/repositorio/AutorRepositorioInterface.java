package com.aluracursos.desafio_literalura.repositorio;

import com.aluracursos.desafio_literalura.entidad.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepositorioInterface extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento > :anio)")
    List<Autor> encontrarAutoresVivosEnAnio(int anio);
}
