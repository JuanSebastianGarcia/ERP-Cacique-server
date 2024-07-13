package com.caciquesport.inventario.inventario.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/*
 * DTO (Data Transfer Object) que se usa para transmitir la información de las facturas,
 * tanto para crear como para solicitar datos de las mismas.
 */
public record FacturaDto(

    int idFactura,

    // Cédula del cliente asociado con la factura. Este campo no puede ser nulo.
    @NotNull 
    String cedulaCliente,

    // Lista de productos incluidos en la factura. Este campo no puede ser nulo.
    @NotNull 
    List<ProductoFacturaDto> listaProductos,

    // Método de pago utilizado para la factura. Este campo no puede ser nulo.
    @NotNull 
    String metodoPago,

    // Monto total pagado por la factura. Este campo no puede ser nulo.
    @NotNull 
    double pago
) {
}
