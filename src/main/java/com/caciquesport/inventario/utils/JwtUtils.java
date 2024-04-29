package com.caciquesport.inventario.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String clave;

    public String generarToken(){
        return "";
    }
}
