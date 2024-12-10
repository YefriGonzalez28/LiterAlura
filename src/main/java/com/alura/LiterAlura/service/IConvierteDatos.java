package com.alura.LiterAlura.service;

import com.alura.LiterAlura.model.ApiResponse;

public interface IConvierteDatos  {
    ApiResponse obtenerDatos(String json);

    <T> T obtenerDatos(String json, Class<T> clase);
}
