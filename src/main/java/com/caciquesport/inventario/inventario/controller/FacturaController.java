package com.caciquesport.inventario.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.FacturaServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



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



    /**
     * Api encarga de devolver una lista de facturas
     * 
     * @param codigo - codigo unico que puede ser la cedula del cliente o un codigo de factura
     * @return -lista de facturas encontradas
     * @throws Exception
     */
    @GetMapping("/buscarFactura/{codigo}")
    public ResponseEntity<RespuestaDto<List<FacturaDto>>> getMethodName(@PathVariable("codigo") String codigo) throws Exception {

        List<FacturaDto> factura=facturaServiceImpl.buscarFacturaDto(codigo);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, factura));
    }
    

    
}
