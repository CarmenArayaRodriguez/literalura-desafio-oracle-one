package com.aluracursos.desafio_literalura.servicio;

import com.aluracursos.desafio_literalura.dto.LibroDTO;
import com.aluracursos.desafio_literalura.entidad.Autor;
import com.aluracursos.desafio_literalura.entidad.Libro;
import com.aluracursos.desafio_literalura.error.ErrorInfo;
import com.aluracursos.desafio_literalura.repositorio.AutorRepositorioInterface;
import com.aluracursos.desafio_literalura.repositorio.LibroRepositorioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ClienteApiLibros {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private List<Libro> librosBuscados = new ArrayList<>(); // Almacenar los libros buscados

    @Autowired
    private LibroRepositorioInterface libroRepositorio; // Repositorio inyectado

    @Autowired
    private AutorRepositorioInterface autorRepositorio; // Repositorio de autores inyectado

    public ClienteApiLibros() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper(); // Inicializa ObjectMapper
    }

    public Libro buscarLibros(String titulo) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?search=" + titulo))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Libro libro = objectMapper.readValue(response.body(), Libro.class);
                librosBuscados.add(libro); // Guardar cada búsqueda
                return libro;
            } else {
                ErrorInfo errorInfo = objectMapper.readValue(response.body(), ErrorInfo.class);
                System.err.println("API Error: " + errorInfo.getMessage() + " Status Code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("HTTP Request Error: " + e.getMessage());
        }
        return null;
    }

    public List<Libro> buscarLibrosPorIdioma(String idioma) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?languages=" + idioma))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Libro>>(){});
            } else {
                System.err.println("Error: Respuesta de la API con código de estado " + response.statusCode());
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la solicitud HTTP: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Libro> getLibrosBuscados() {
        return librosBuscados;
    }

    public List<Autor> getTodosLosAutores() {
        return librosBuscados.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Autor> getAutoresVivosEnAnio(int anio) {
        return getTodosLosAutores().stream()
                .filter(autor -> autor.getAnioFallecimiento() == null || autor.getAnioFallecimiento() > anio)
                .collect(Collectors.toList());
    }

    public Libro guardarLibro(LibroDTO libroDTO) {
        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitulo().orElse(null));
        libro.setIdioma(libroDTO.getIdioma().orElse(null));
        libro.setNumeroDescargas(libroDTO.getNumeroDescargas());

        // Supongamos que aquí convertimos y asignamos autores si es necesario
        if (libroDTO.getAutores().isPresent()) {
            libro.setAutores(
                    Arrays.stream(libroDTO.getAutores().get())
                            .map(autorDTO -> new Autor(autorDTO.getNombre(), autorDTO.getAnioNacimiento(), autorDTO.getAnioFallecimiento(), libro))
                            .collect(Collectors.toSet())
            );
        }

        // Guardar en la base de datos
        return libroRepositorio.save(libro);
    }

    public Map<String, Integer> contarLibrosPorIdioma(List<String> idiomas) {
        Map<String, Integer> resultados = new HashMap<>();
        for (String idioma : idiomas) {
            int conteo = libroRepositorio.contarPorIdioma(idioma);
            resultados.put(idioma, conteo);
        }
        return resultados;
    }

    public List<Autor> listarAutoresVivos(int anio) {
        return autorRepositorio.encontrarAutoresVivosEnAnio(anio);
    }
}
