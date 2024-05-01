package com.caciquesport.inventario.inventario.dto;
import jakarta.validation.constraints.NotBlank;

/*
 * DTO que maneja la cadea del token 
 */
public record TokenDto(@NotBlank String token) {

}
