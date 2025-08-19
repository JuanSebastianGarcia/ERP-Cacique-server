package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.HitoricoIndicadoresDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;

public interface EstadisticaServicio {


    /*
     * Obtener los indicadores mensuales
     */
    public IndicadoresMensualesDto obtenerIndicadoresMensuales();


    /*
     * Obtener Informacion de indicadores para graficas
     */
    public HitoricoIndicadoresDto ObtenerHistoricoIndicadores(String TipoIndicador);
    
}
