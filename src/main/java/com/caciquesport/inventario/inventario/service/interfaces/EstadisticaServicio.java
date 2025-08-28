package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.AnalisisVentasAgrupadasDto;
import com.caciquesport.inventario.inventario.dto.HitoricoIndicadoresDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresDiariosDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.dto.ListaRegistrosMovimientoDto;

public interface EstadisticaServicio {


    /*
     * Obtener los indicadores mensuales
     */
    public IndicadoresMensualesDto obtenerIndicadoresMensuales();


    /*
     * Obtener Informacion de indicadores para graficas
     */
    public HitoricoIndicadoresDto ObtenerHistoricoIndicadores(String TipoIndicador);



    /*
     * Obtener analisis de ventas agrupadas por tipo de dato
     */
    public AnalisisVentasAgrupadasDto obtenerAnalisisVentasAgrupadas(String tipoDato);
    
    /*
     * Obtener indicadores clave de rendimiento (KPIs) del día
     * 
     * @param fecha Fecha específica para la cual se requieren los KPIs (formato: yyyy-MM-dd)
     * @return IndicadoresDiariosDto con los KPIs del día especificado
     */
    public IndicadoresDiariosDto obtenerKpisDiarios(String fecha);
    
    /*
     * Obtener lista de registros de movimientos (ingresos y gastos) para una fecha específica
     * 
     * @param fecha Fecha específica para la cual se requieren los registros (formato: yyyy-MM-dd)
     * @return ListaRegistrosMovimientoDto con la lista de registros de movimientos del día especificado
     */
    public ListaRegistrosMovimientoDto obtenerRegistrosMovimientos(String fecha);
    
}
