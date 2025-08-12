package com.caciquesport.inventario.inventario.dto;

public record IndicadoresMensualesDto(
    double ingresosTotalesMesActual,
    double gastosTotalesMesActual,
    double utilidadNetaMesActual,
    int productosVendidosMesActual
    ) {
}
