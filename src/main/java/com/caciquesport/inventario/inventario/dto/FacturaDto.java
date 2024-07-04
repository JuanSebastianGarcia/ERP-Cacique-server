package com.caciquesport.inventario.inventario.dto;

import java.util.List;

public record FacturaDto(

    String cedulaCliente,

    List<ProductoFacturaDto> listaProductos,

    String metodoPago,

    Double pago
) {

}
