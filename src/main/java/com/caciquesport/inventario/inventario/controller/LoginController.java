package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.dto.TokenDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    /*
     * servicios necesarios para la ejecucion del proceso
     */


     
    /*
     * controlador para verificar la existencia de un usuario
     */
    public ResponseEntity<RespuestaDto<TokenDto>> login(@Valid @RequestBody LoginDto loginDto){






        return null;
    }




}
