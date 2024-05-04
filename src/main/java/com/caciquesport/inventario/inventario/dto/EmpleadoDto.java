package com.caciquesport.inventario.inventario.dto;

public record EmpleadoDto(

    String nombre,
    String cedula,
    String telefono,
    String email,
    String password,
    String tipoEmpleado
) {

}
