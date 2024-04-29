package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.TokenDto;

/**
 * Interfaz para definir los servicios para el ingreso a la plataforma
 */
public interface AutenticacionServicio {

    public TokenDto verificarIdentidad(LoginDto loginDto) throws Exception;
}
