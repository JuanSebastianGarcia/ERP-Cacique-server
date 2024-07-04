package com.caciquesport.inventario.inventario.dto.Entities;

/*
 * Este DTO (Data Transfer Object) se utiliza para crear y enviar la información de los datos de configuración
 * (institución, género, horario, prenda, talla).
 */
public record ConfigTypesDto(

    // Identificación única del tipo de configuración.
    int idTipo, 
    
    // Nombre del tipo de configuración 
    String nombreTipo
    
){
}
