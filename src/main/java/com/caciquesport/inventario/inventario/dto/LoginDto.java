package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) para manejar los datos de inicio de sesión con email y contraseña.
 */
public record LoginDto(

    // Dirección de correo electrónico del usuario. Debe ser un email válido.
    @Email @NotNull
    String email,

    // Contraseña del usuario.
    @NotNull
    String password
) {
}
