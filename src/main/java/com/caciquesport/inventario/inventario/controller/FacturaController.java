package com.caciquesport.inventario.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoPendienteDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/factura")
@RequiredArgsConstructor
@Tag(name = "Factura", description = "Operaciones relacionadas con la gestión de facturas y productos pendientes")
public class FacturaController {

    /*
     * Servicio para el manejo de la factura
     */
    private final FacturaService facturaService;

    /**
     * API encargada de la generación de una factura.
     * 
     * @param facturaDto - datos de la factura la cual deberá ser generada
     * @return String - mensaje de confirmación
     * @throws Exception - errores en la creación de una factura
     */
    @PostMapping
    @Operation(summary = "Generar factura", description = "Crea una nueva factura con los datos proporcionados.")
    public ResponseEntity<RespuestaDto<String>> generarFactura(@RequestBody FacturaDto facturaDto) throws Exception {
        String respuesta = facturaService.generarFactura(facturaDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, respuesta));
    }

    /**
     * API encargada de devolver una lista de facturas.
     * 
     * @param codigo - código único que puede ser la cédula del cliente o un código de factura
     * @param tipoCodigo - tipo del código proporcionado: 'cedula' o 'factura'
     * @return lista de facturas encontradas
     * @throws Exception
     */
    @GetMapping("/{codigo}/{tipoCodigo}")
    @Operation(summary = "Buscar facturas", description = "Consulta facturas por cédula o por código de factura.")
    public ResponseEntity<RespuestaDto<List<FacturaDto>>> buscarFactura(
        @Parameter(description = "Código único: puede ser cédula o código de factura") 
        @PathVariable("codigo") String codigo,

        @Parameter(description = "Tipo de código: 'cedula' o 'factura'") 
        @PathVariable("tipoCodigo") String tipoCodigo
    ) throws Exception {
        List<FacturaDto> factura = facturaService.buscarFacturaDto(codigo, tipoCodigo);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, factura));
    }

    /**
     * API encargada de actualizar y generar cambios en una factura.
     * 
     * @param facturaDto - datos de la factura a la cual se le realizarán los cambios
     * @param idFactura - ID de la factura a actualizar
     * @return respuesta de la operación 
     * @throws Exception 
     */
    @PutMapping("/{idFactura}")
    @Operation(summary = "Actualizar factura", description = "Modifica una factura existente con los nuevos datos enviados.")
    public ResponseEntity<RespuestaDto<String>> actualizarFactura(
        @Parameter(description = "ID de la factura a actualizar") 
        @PathVariable("idFactura") Integer idFactura,

        @RequestBody FacturaDto facturaDto
    ) throws Exception {
        String respuesta = facturaService.guardarCambios(facturaDto, idFactura);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, respuesta));
    }

    /**
     * API encargada de retornar la lista de productos que están en estado pendiente.
     * 
     * @return lista de productos pendientes
     */
    @GetMapping("/consultarProductosPendientes")
    @Operation(summary = "Consultar productos pendientes", description = "Devuelve la lista de productos asociados a facturas en estado pendiente.")
    public ResponseEntity<RespuestaDto<List<ProductoPendienteDto>>> consultarProductosPendientes() throws Exception {
        List<ProductoPendienteDto> listaProductos = facturaService.consultarProductosPendientes();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, listaProductos));
    }
}
