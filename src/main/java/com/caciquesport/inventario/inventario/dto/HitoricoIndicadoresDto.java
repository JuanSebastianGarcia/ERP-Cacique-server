package com.caciquesport.inventario.inventario.dto;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.graficas.GraficaAnualDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaDiariaDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaMensualDto;

public record HitoricoIndicadoresDto(
    
    List<GraficaDiariaDto> graficaDiaria,

    List<GraficaMensualDto> graficaMensual,

    List<GraficaAnualDto> graficaAnual
) {
}


