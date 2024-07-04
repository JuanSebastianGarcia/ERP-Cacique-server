package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
 * DTO (Data Transfer Object) que maneja los datos necesarios para la creación de un producto o la consulta del mismo.
 * Este DTO será usado para ambos casos.
 */
public record ProductoDto(

    // Identificación única del producto.
    Integer id,

    // Tipo de prenda 
    @NotNull
    String prenda,

    // Institución relacionada con el producto.
    @NotNull
    String institucion,

    // Talla de la prenda 
    @NotNull
    String talla,

    // Horario específico relacionado con el producto
    @NotNull
    String horario,

    // Género para el cual el producto está destinado 
    @NotNull
    String genero,

    // Precio del producto. 
    @NotNull
    double precio,

    // Cantidad disponible del producto.
    @Min(value = 0, message = "La cantidad mínima debe ser cero")
    int cantidad


) {
}
