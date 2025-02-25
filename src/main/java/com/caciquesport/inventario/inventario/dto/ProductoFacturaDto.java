package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.NotNull;

/*
 * DTO (Data Transfer Object) que maneja los datos necesarios para la asignacion de un producto a la factura, 
 * se tienen en cuenta datos necesarios para establecer la relacion
 */
public record ProductoFacturaDto(

        //esta variable corresponde a la relacion unica entre un producto y una factura
        int idRelacion,

        //Tipo de prenda
        @NotNull String prenda,

        //Institución relacionada con el producto.
        @NotNull String institucion,

        //Talla de la prenda
        @NotNull String talla,

        //Horario específico relacionado con el producto
        @NotNull String horario,

        //Género para el cual el producto está destinado
        @NotNull String genero,

        //Precio del producto.
        @NotNull double precio,

        //Estado del producto
        @NotNull String estado,

        //Descripcion del producto
        String descripcion
        ) {
}
