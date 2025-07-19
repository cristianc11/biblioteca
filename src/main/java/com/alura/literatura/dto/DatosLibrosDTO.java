package com.alura.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibrosDTO(
        int count,
        List<LibroDTO> results) {
}
