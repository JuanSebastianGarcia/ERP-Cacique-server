package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.AnalisisVentasAgrupadasDto;
import com.caciquesport.inventario.inventario.dto.HitoricoIndicadoresDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.EstadisticaServicio;

import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for managing statistics operations in the inventory system.
 * Provides endpoints for retrieving various statistical data including monthly indicators,
 * historical indicators, and sales analysis.
 */
@RestController
@RequestMapping("/api/estadistica")
@AllArgsConstructor
@Tag(name = "Estadistica", description = "Operaciones para gestionar estadisticas en el sistema")
public class EstadisticaController {

    // ========================================
    // DEPENDENCY INJECTION
    // ========================================
    
    /**
     * Service layer for statistics operations
     */
    private final EstadisticaServicio estadisticaServicio;

    // ========================================
    // PUBLIC ENDPOINTS
    // ========================================

    /**
     * Retrieves monthly indicators for the system.
     * This endpoint provides key performance metrics and statistics
     * aggregated on a monthly basis.
     * 
     * @return ResponseEntity containing monthly indicators wrapped in RespuestaDto
     */
    @GetMapping("/indicadores-mensuales")
    @Operation(summary = "Obtener indicadores mensuales", description = "Obtiene los indicadores mensuales del sistema")
    public ResponseEntity<RespuestaDto<IndicadoresMensualesDto>> ObtenerIndicadoresMensuales() {
        
        // Retrieve monthly indicators from service layer
        IndicadoresMensualesDto indicadoresMensuales = estadisticaServicio.obtenerIndicadoresMensuales();
        
        // Return successful response with data
        return ResponseEntity.ok().body(new RespuestaDto<>(false, indicadoresMensuales));
        
    }

    /**
     * Retrieves historical indicators based on the specified indicator type.
     * This endpoint allows filtering of historical data by different indicator categories
     * such as gross income, net income, and expenses
     * 
     * @param TipoIndicador The type of indicator to retrieve historical data for (required)
     * @return ResponseEntity containing historical indicators wrapped in RespuestaDto
     */
    @GetMapping("/historico-indicadores")
    @Operation(summary = "Obtener historico indicadores", description = "Obtiene el historico de indicadores del sistema")
    public ResponseEntity<RespuestaDto<HitoricoIndicadoresDto>> ObtenerHistoricoIndicadores(@RequestParam(required = true)String TipoIndicador) {
        
        // Retrieve historical indicators from service layer based on indicator type
        HitoricoIndicadoresDto historicoIndicadores = estadisticaServicio.ObtenerHistoricoIndicadores(TipoIndicador);
        
        // Return successful response with historical data
        return ResponseEntity.ok().body(new RespuestaDto<>(false, historicoIndicadores));

    }

    /**
     * Retrieves grouped sales analysis data.
     * This endpoint provides sales analysis grouped by different criteria
     * such as product category, time period, or region.
     * 
     * @param TipoDato The type of data grouping for sales analysis (required)
     * @return ResponseEntity containing sales analysis data wrapped in RespuestaDto
     */
    @GetMapping("/analisis-ventas-agrupadas")
    @Operation(summary = "Obtener analisis ventas agrupadas", description = "Obtiene el analisis de ventas agrupadas del sistema")
    public ResponseEntity<RespuestaDto<AnalisisVentasAgrupadasDto>> obtenerAnalisisVentasAgrupadas(@RequestParam(required = true)String TipoDato){
        
        AnalisisVentasAgrupadasDto analisisVentasAgrupadas = estadisticaServicio.obtenerAnalisisVentasAgrupadas(TipoDato);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false, analisisVentasAgrupadas));

    }

}
