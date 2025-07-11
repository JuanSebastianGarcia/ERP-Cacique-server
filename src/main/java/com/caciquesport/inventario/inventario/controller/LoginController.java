package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.AutenticacionServicioImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Controlador para el inicio de sesión y generación de tokens")
public class LoginController {

    /*
     * Servicio encargado de la autenticación y generación de tokens para los usuarios
     */
    private final AutenticacionServicioImpl autenticacionServicioImpl;

    /*
     * Controlador para verificar la existencia de un usuario
     * 
     * @param loginDto - objeto que contiene los datos requeridos para el proceso
     * 
     * @return - estado correcto del proceso y el token
     */
    @PostMapping("/ingresar")
    @Operation(summary = "Iniciar sesión", description = "Verifica las credenciales del usuario y genera un token de acceso.")
    public ResponseEntity<RespuestaDto<String>> ingresar(@Valid @RequestBody LoginDto loginDto) throws Exception {
        String token = autenticacionServicioImpl.verificarIdentidad(loginDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, token));
    }
}
