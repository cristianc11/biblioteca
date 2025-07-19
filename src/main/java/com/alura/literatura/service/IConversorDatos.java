package com.alura.literatura.service;

public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
