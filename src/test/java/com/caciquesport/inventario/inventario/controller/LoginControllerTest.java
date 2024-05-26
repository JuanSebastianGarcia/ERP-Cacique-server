package com.caciquesport.inventario.inventario.controller;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.TokenDto;
import com.caciquesport.inventario.inventario.service.implementations.AutenticacionServicioImpl;



@SpringBootTest
public class LoginControllerTest {

    //service
    @Autowired
    AutenticacionServicioImpl autenticacionServicioImpl;

    /*
     *Test que valida el funcionamiento del caso 1
     * PRUEBA DEL SERVICIO DE LOGIN
     */
    @Test
    public void usuarioLogin() throws Exception{

        //creacion del formato aceptado por el servicio
        LoginDto loginDto =new LoginDto("carlos@gmail.com", "123");
       
        //generacion del token
        TokenDto tokenDto=autenticacionServicioImpl.verificarIdentidad(loginDto);

        //extraccion del token
        String token = tokenDto.token();

        //verificacion de que hay algo dentro el token
        assertNotEquals("", token);
    }

}
