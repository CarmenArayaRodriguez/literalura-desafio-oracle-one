package com.aluracursos.desafio_literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {
    @JsonProperty("title")
    private String titulo;

    @JsonProperty("authors")
    private AutorDTO[] autores;

    @JsonProperty("languages")
    private String idioma;

    @JsonProperty("download_count")
    private int numeroDescargas;

    // Getters y setters con Optional para manejo de nulos
    public Optional<String> getTitulo() {
        return Optional.ofNullable(titulo);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Optional<AutorDTO[]> getAutores() {
        return Optional.ofNullable(autores);
    }

    public void setAutores(AutorDTO[] autores) {
        this.autores = autores;
    }

    public Optional<String> getIdioma() {
        return Optional.ofNullable(idioma);
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "LibroDTO{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + Arrays.toString(autores) +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}
