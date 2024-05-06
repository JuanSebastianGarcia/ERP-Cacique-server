package com.caciquesport.inventario.inventario.dto;

public record EmpleadoDto(
    Integer id,
    String nombre,
    String cedula,
    String telefono,
    String email,
    String password,
    String tipoEmpleado
) {

}
