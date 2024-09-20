package com.caciquesport.inventario.inventario.dto;

import jakarta.validation.constraints.NotNull;

public record ProductoPendienteDto(


    //Tipo de prenda 
    @NotNull
    String prenda,

    //Institución relacionada con el producto.
    @NotNull
    String institucion,

    //Talla de la prenda 
    @NotNull
    String talla,

    //Horario específico relacionado con el producto
    @NotNull
    String horario,

    //Género para el cual el producto está destinado 
    @NotNull
    String genero,

    //codigo unico de la factura a la cual pertenece el producto pendiente
    @NotNull
    int idFactura,

    //fecha en la cual fue generado el producto pendiente
    @NotNull
    String fecha,

    String descripcion
) {

}
