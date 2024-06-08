package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
 * Dto que es usado para transmitir la informacion de los empleados, al momento de crear y solicitar
 */
public record EmpleadoDto(

    Integer id,
    
    @NotNull 
    String nombre,

    @NotNull
    String cedula,

    @NotNull
    @Size(min=10,message="el telefono es minimo de 10 digitos")
    String telefono,

    @NotNull
    @Email
    String email,

    @NotNull
    String password,

    @NotNull
    String tipoEmpleado
) {

}
