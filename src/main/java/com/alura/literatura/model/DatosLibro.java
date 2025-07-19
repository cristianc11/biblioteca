package com.alura.literatura.model;

import com.alura.literatura.dto.LibroDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro {

    @JsonProperty("count")
    private Integer cantidad;

    @JsonProperty("results")
    private List<LibroDTO> resultados;

    public DatosLibro() {
    }

    public DatosLibro(Integer cantidad, List<LibroDTO> resultados) {
        this.cantidad = cantidad;
        this.resultados = resultados;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<LibroDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<LibroDTO> resultados) {
        this.resultados = resultados;
    }
}
