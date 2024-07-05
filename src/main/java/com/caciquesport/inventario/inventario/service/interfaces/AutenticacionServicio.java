package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.LoginDto;

/**
 * Interfaz para definir los servicios para el ingreso a la plataforma.
 */
public interface AutenticacionServicio {

    /**
     * Verifica la identidad del usuario utilizando los datos de inicio de sesión proporcionados.
     *
     * @param loginDto Los datos de inicio de sesión del usuario, que incluyen email y contraseña.
     * 
     * @return Una cadena que representa el resultado de la verificación de identidad, 
     *         por ejemplo, un token de autenticación si la verificación es exitosa.
    **/
    public String verificarIdentidad(LoginDto loginDto) throws Exception;
}

