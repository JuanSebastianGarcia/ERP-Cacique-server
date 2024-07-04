package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.NotNull;

/*
 * DTO (Data Transfer Object) que se usa para transmitir la información de los clientes,
 * tanto para crear como para solicitar datos de los mismos.
 */
public record ClienteDto(

    // Identificación única del cliente
    @NotNull
    String cedula,
    
    // Nombre completo del cliente
    @NotNull
    String nombre,
    
    // Número de teléfono de contacto del cliente
    @NotNull
    String telefono,
    
    // Dirección de correo electrónico del cliente
    String email,
    
    // Dirección física del cliente
    String direccion

) {
}
