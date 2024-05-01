package com.caciquesport.inventario.inventario.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.caciquesport.inventario.inventario.dto.RegistroProductoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.ProductoServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/*
 * Controlador encargado de brindar los servicios principales al jefe de inventario, el controlador brindara los 
 * servicios para poder gestionar el inventario
 */
@RestController
@RequestMapping("/api/jefe")
@RequiredArgsConstructor
public class JefeController {


    /*
     * SERVICIOS 
     */
    private final ProductoServicioImpl productoServicioImpl;


    /*
     * 
     */
    @PostMapping("/crearProducto")
    public ResponseEntity<RespuestaDto<String>> crearProducto(@Valid @RequestBody RegistroProductoDto RegistroProductoDto) throws Exception{

        productoServicioImpl.crearProducto(RegistroProductoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el producto ha sido creado"));
    }


}
