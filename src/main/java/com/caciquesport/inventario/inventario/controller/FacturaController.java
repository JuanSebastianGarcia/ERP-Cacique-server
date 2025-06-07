package com.caciquesport.inventario.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoPendienteDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.FacturaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/factura")
@RequiredArgsConstructor
public class FacturaController {



    /*
     * Servicio para el manejo de la factura
     */
    private final FacturaService facturaService;


    /**
     * api encargada de la generacion de una factura 
     * @param facturaDto - datos de la factura la cual debera ser generada
     * @return String - mensaje de confirmacion
     * @throws Exception - errores en la creacion de una factura
     */
    @PostMapping
    public ResponseEntity<RespuestaDto<String>> generarFactura(@RequestBody FacturaDto facturaDto) throws Exception{

        String respuesta = facturaService.generarFactura(facturaDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,respuesta));
    }



    
    /**
     * Api encarga de devolver una lista de facturas
     * 
     * @param codigo - codigo unico que puede ser la cedula del cliente o un codigo de factura
     * @return -lista de facturas encontradas
     * @throws Exception
     */
    @GetMapping("/{codigo}/{tipoCodigo}")
    public ResponseEntity<RespuestaDto<List<FacturaDto>>> buscarFactura(
        @PathVariable("codigo") String codigo,
        @PathVariable("tipoCodigo") String tipoCodigo ) 
    throws Exception {

        List<FacturaDto> factura=facturaService.buscarFacturaDto(codigo,tipoCodigo);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, factura));
    }
    




    /**
     * api encargada de actualizar y generar cambios en una factura. 
     * 
     * @param facturaDto - datos de la factura a la cual se le realizaran los cambios
     * @return respuesta de la operacion 
     * @throws Exception 
     */
    @PutMapping("/{idFactura}")
    public ResponseEntity<RespuestaDto<String>> actualizarFactura(
        @PathVariable("idFactura") Integer idFactura,
        @RequestBody FacturaDto facturaDto) 
    throws Exception {
        
        String respuesta=facturaService.guardarCambios(facturaDto,idFactura);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false, respuesta));
    }




    
    /**
     * api encargada de retornar la lista de productos que estan en estado pendiente
     * 
     * @Return lista de productos pendientes
     */
    @GetMapping("/consultarProductosPendientes")
    public ResponseEntity<RespuestaDto<List<ProductoPendienteDto>>> consultarProductosPendientes() throws Exception {

        List<ProductoPendienteDto> listaProductos = facturaService.consultarProductosPendientes();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, listaProductos));
    }
    


    
}
