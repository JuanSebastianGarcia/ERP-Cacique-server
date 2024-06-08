package com.caciquesport.inventario.inventario.dto;

/*
 * Este DTO es recibido para filtrar la lista de productos que sera retornado
 */
public record FiltroProductoDto(

    String prenda,
    String talla,
    String horario,
    String genero,
    String institucion
) {
}
