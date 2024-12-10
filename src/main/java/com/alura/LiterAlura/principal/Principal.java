package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.model.ApiResponse;
import com.alura.LiterAlura.model.Author;
import com.alura.LiterAlura.model.DatosLibros;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    private LibroRepository libroRepository;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Escoje una opcion por su numero:
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar Autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libro por idioma
                    0- salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
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
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private DatosLibros getDatosLibros() {
        System.out.println("Ingresa el nombre del libro");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        ApiResponse datos = conversor.obtenerDatos(json);

        if (datos.getResults().isEmpty()) {
            System.out.println("No se encontraron resultados");
            muestraElMenu();
            return null;
        }

        DatosLibros libro = datos.getResults().get(0); // Obtiene solo el primer resultado
        mostrarDatosLibro(libro); // Muestra solo el primer resultado
        return libro;
    }

    private void buscarLibro() {
        DatosLibros datos = getDatosLibros();
        if (datos != null) {
            Libro libro = new Libro(datos);
            libroRepository.save(libro); // Persistir libro en la base de datos
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private void listarAutores() {
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros) {
            for (Author autor : libro.getAutores()) {
                System.out.println(autor.getName());
            }
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Ingresa el año de inicio:");
        int añoInicio = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Ingresa el año de fin:");
        int añoFin = teclado.nextInt();
        teclado.nextLine();

        String url = URL_BASE + "?author_year_start=" + añoInicio + "&author_year_end=" + añoFin;
        var json = consumoApi.obtenerDatos(url);
        ApiResponse datos = conversor.obtenerDatos(json);

        if (datos.getResults().isEmpty()) {
            System.out.println("No se encontraron resultados");
            muestraElMenu();
            return;
        }

        for (DatosLibros libro : datos.getResults()) {
            for (Author autor : libro.autores()) {
                System.out.println(autor.getName());
            }
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingresa el idioma:");
        String idioma = teclado.nextLine();
        String url = URL_BASE + "?languages=" + idioma;
        var json = consumoApi.obtenerDatos(url);
        ApiResponse datos = conversor.obtenerDatos(json);

        if (datos.getResults().isEmpty()) {
            System.out.println("No se encontraron resultados");
            muestraElMenu();
            return;
        }

        for (DatosLibros libro : datos.getResults()) {
            mostrarDatosLibro(libro);
        }
    }

    private void mostrarDatosLibro(DatosLibros libro) {
        System.out.println("Título: " + libro.titulo());
        System.out.println("Autores: ");
        for (Author autor : libro.autores()) {
            if (autor != null && autor.getName() != null) {
                System.out.println("\t" + autor.getName());
            } else {
                System.out.println("\tAutor no disponible");
            }
        }
        System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
        System.out.println("Número de descargas: " + libro.numeroDescargas());
    }
}
