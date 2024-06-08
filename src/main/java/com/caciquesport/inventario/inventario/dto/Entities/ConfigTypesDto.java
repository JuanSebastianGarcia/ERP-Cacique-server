package com.caciquesport.inventario.inventario.dto.Entities;

/*
 * Este Dto es usado para crear y enviar la informacion de los datos de configuracion
 * (institucion , genero , hoario , prenda, talla)
 */
public record ConfigTypesDto(
    int idTipo, 
    String nombreTipo) {

}
