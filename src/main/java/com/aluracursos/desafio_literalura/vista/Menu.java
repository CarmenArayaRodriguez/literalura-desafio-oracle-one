package com.aluracursos.desafio_literalura.vista;

import com.aluracursos.desafio_literalura.entidad.Autor;
import com.aluracursos.desafio_literalura.entidad.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.aluracursos.desafio_literalura.servicio.ClienteApiLibros;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    private ClienteApiLibros clienteApiLibros;

    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                System.out.println("Bienvenido al Menú de LiterAlura");
                System.out.println("1. Buscar libro por título");
                System.out.println("2. Mostrar todos los libros buscados");
                System.out.println("3. Mostrar libros por idioma");
                System.out.println("4. Listar todos los autores");
                System.out.println("5. Listar autores vivos en un año específico");
                System.out.println("6. Estadísticas de libros por idioma");
                System.out.println("7. Salir");
                System.out.print("Ingrese su opción: ");

                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un número válido.");
                    scanner.next(); // Consume la entrada no deseada
                    System.out.print("Ingrese su opción: ");
                }
                int opcion = scanner.nextInt();
                scanner.nextLine(); // consume the newline

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = scanner.nextLine();
                        Libro libro = clienteApiLibros.buscarLibros(titulo);
                        if (libro != null) {
                            System.out.println("Título del libro: " + libro.getTitulo().orElse("Título no disponible"));
                            System.out.println("Idioma: " + libro.getIdioma().orElse("Idioma no disponible"));
                            System.out.println(libro); // Usar el método toString() implícito
                        } else {
                            System.out.println("No se encontró el libro o hubo un error.");
                        }
                        break;
                    case 2:
                        clienteApiLibros.getLibrosBuscados().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Ingrese el idioma de los libros que desea buscar: ");
                        String idioma = scanner.nextLine();
                        List<Libro> librosPorIdioma = clienteApiLibros.buscarLibrosPorIdioma(idioma);
                        librosPorIdioma.forEach(System.out::println);
                        break;
                    case 4:
                        List<Autor> autores = clienteApiLibros.getTodosLosAutores();
                        autores.forEach(System.out::println);
                        break;
                    case 5:
                        System.out.print("Ingrese el año para verificar autores vivos: ");
                        int anio = scanner.nextInt();
                        scanner.nextLine();
                        List<Autor> autoresVivos = clienteApiLibros.listarAutoresVivos(anio);
                        autoresVivos.forEach(autor -> System.out.println(autor.toString()));
                        break;
                    case 6:
                        List<String> idiomas = Arrays.asList("es", "en");
                        Map<String, Integer> estadisticas = clienteApiLibros.contarLibrosPorIdioma(idiomas);
                        estadisticas.forEach((id, conteo) -> System.out.println("Idioma " + id+ " tiene " + conteo + " libros."));
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida, por favor intente de nuevo.");
                }
            }
        }
    }
}
