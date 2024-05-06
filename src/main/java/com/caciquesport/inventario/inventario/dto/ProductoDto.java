package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
 * DTO que maneja los datos necesarios para la creacion de un producto
 */
public record ProductoDto(

    
    Integer id,

    @NotNull
    String prenda,

    @NotNull
    String institucion,

    @NotNull
    String talla,

    @NotNull
    String horario,

    @NotNull
    String genero,

    @NotNull
    double precio,
    
    @Min(value = 0,message = "la cantidad minima debe de ser cero")
    int cantidad,

    String descripcion

    ){

}
