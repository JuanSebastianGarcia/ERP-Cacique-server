package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.FacturaServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/majejoFactura")
@RequiredArgsConstructor
public class FacturaController {



    /*
     * Servicio para el manejo de la factura
     */
    private final FacturaServiceImpl facturaServiceImpl;


    /**
     * api encargada de la generacion de una factura 
     * @param facturaDto - datos de la factura la cual debera ser generada
     * @return String - mensaje de confirmacion
     * @throws Exception - errores en la creacion de una factura
     */
    @PostMapping("/generarFactura")
    public ResponseEntity<RespuestaDto<String>> generarFactura(@RequestBody FacturaDto facturaDto) throws Exception{

        String respuesta = facturaServiceImpl.generarFactura(facturaDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,respuesta));
    }
}
