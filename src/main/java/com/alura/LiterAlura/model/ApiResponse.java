package com.alura.LiterAlura.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private List<DatosLibros> results;
    public List<DatosLibros> getResults() {
        return results; }

    public void setResults(List<DatosLibros> results) {
        this.results = results; }
}