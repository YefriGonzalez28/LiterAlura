package com.alura.LiterAlura.service;

import com.alura.LiterAlura.model.ApiResponse;
import com.alura.LiterAlura.model.Author;
import com.alura.LiterAlura.model.DatosLibros;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ApiResponse obtenerDatos(String json) {
        try {
            return objectMapper.readValue(json, ApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        return null;
    }

    public void mostrarDatosLibros(List<DatosLibros> libros) {
        for (DatosLibros libro : libros) {
            String titulo = libro.titulo();
            List<Author> autores = libro.autores();
            List<String> idiomas = libro.idiomas();
            int numeroDescargas = libro.numeroDescargas();

            String autoresStr = autores.stream()
                    .map(Author::getName) // Acceder correctamente al método getName
                    .collect(Collectors.joining(", "));

            String idiomasStr = String.join(", ", idiomas);

            System.out.println("Título: " + titulo);
            System.out.println("Autores: " + autoresStr);
            System.out.println("Idiomas: " + idiomasStr);
            System.out.println("Número de descargas: " + numeroDescargas);
            System.out.println();
        }
    }
}
