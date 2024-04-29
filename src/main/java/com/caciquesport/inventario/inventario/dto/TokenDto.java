package com.caciquesport.inventario.inventario.dto;
import jakarta.validation.constraints.NotBlank;

/*
 * almacena la informa
 */
public record TokenDto(@NotBlank String token) {

}
