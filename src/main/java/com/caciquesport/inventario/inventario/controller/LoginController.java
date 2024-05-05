package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.dto.TokenDto;
import com.caciquesport.inventario.inventario.service.implementations.AutenticacionServicioImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    /*
     * servicios encargado de la autenticacion y generacion de tokens para los usuarios
     */
    private final AutenticacionServicioImpl autenticacionServicioImpl;

     
    /*
     * controlador para verificar la existencia de un usuario
     * 
     * @param loginDto - objeto que contiene los datos requeridos para el proceso
     * 
     * @return - estado correcto del proceso  y el token
     */
    @PostMapping("/ingresar")
    public ResponseEntity<RespuestaDto<TokenDto>> ingresar(@Valid @RequestBody LoginDto loginDto)throws Exception{

            TokenDto tokenDto = autenticacionServicioImpl.verificarIdentidad(loginDto);

            return ResponseEntity.ok().body(new RespuestaDto<>(false,tokenDto));
    }


}
