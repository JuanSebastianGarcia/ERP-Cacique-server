package com.caciquesport.inventario.inventario.dto;

/*
 * Este DTO (Data Transfer Object) se utiliza para filtrar la lista de productos
 * que será retornada. Cada campo representa un criterio de filtrado.
 */
public record FiltroProductoDto(

    // Tipo de prenda
    String prenda,
    
    // Talla de la prenda
    String talla,
    
    // Horario específico relacionado con el producto
    String horario,
    
    // Género para el cual el producto está destinado
    String genero,
    
    // Institución relacionada con el producto
    String institucion
) {
}
