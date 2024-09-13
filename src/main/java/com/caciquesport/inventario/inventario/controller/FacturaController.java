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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/manejoFactura")
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
    @GetMapping("/buscarFactura/{codigo}/{tipoCodigo}")
    public ResponseEntity<RespuestaDto<List<FacturaDto>>> buscarFactura(@PathVariable("codigo") String codigo,
    @PathVariable("tipoCodigo") String tipoCodigo ) throws Exception {

        List<FacturaDto> factura=facturaServiceImpl.buscarFacturaDto(codigo,tipoCodigo);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, factura));
    }
    



    /**
     * api encargada de actualizar y generar cambios en una factura. 
     * 
     * @param facturaDto - datos de la factura a la cual se le realizaran los cambios
     * @return respuesta de la operacion 
     * @throws Exception 
     */
    @PutMapping("/actualizarFactura")
    public ResponseEntity<RespuestaDto<String>> actualizarFactura(@RequestBody FacturaDto facturaDto) throws Exception {
        
        String respuesta=facturaServiceImpl.guardarCambios(facturaDto);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false, respuesta));
    }



    /*
     * api encargada de retornar la lista de productos que estan en estado pendiente
     */
    @GetMapping("/consultarProductosPendientes")
    public String getMethodName() throws Exception {

        facturaServiceImpl.consultarProductosPendientes();

        return new String();
    }
    
}
