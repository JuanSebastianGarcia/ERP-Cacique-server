package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
 * DTO (Data Transfer Object) que se usa para transmitir la información de los empleados,
 * tanto para crear como para solicitar datos de los mismos.
 */
public record EmpleadoDto(

    // Identificación única del empleado, generalmente un número entero autoincremental
    Integer id,
    
    // Nombre completo del empleado. Este campo no puede ser nulo.
    @NotNull 
    String nombre,

    // Cédula del empleado. Este campo no puede ser nulo y debe tener al menos 10 dígitos.
    @NotNull
    @Size(min=10, message="La cédula debe tener mínimo 10 dígitos")
    String cedula,

    // Número de teléfono del empleado. Este campo no puede ser nulo y debe tener al menos 10 dígitos.
    @NotNull
    @Size(min=10, message="El teléfono debe tener mínimo 10 dígitos")
    String telefono,

    // Dirección de correo electrónico del empleado. Este campo no puede ser nulo y debe ser un email válido.
    @NotNull
    @Email
    String email,

    // Contraseña del empleado. Este campo no puede ser nulo.
    @NotNull
    String password,

    // Tipo de empleado (por ejemplo, administrador, operador, etc.). Este campo no puede ser nulo.
    @NotNull
    String tipoEmpleado
) {
}

