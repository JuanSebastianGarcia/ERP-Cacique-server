package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para manejar los datos de inicio de sesión con email y contraseña.
 */
public record LoginDto(

    @Email @NotNull
    String email,

    @NotNull
    String password
    ){
}
