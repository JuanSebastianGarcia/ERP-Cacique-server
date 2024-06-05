package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.LoginDto;

/**
 * Interfaz para definir los servicios para el ingreso a la plataforma
 */
public interface AutenticacionServicio {

    public String verificarIdentidad(LoginDto loginDto) throws Exception;
}
