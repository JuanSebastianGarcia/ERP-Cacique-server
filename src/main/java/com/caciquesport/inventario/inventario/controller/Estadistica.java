package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;

import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/estadistica")
@AllArgsConstructor
@Tag(name = "Estadistica", description = "Operaciones para gestionar estadisticas en el sistema")
public class Estadistica {

    /*
     * SERVICIOS
     */
    private final EstadisticaServicio estadisticaServicio;





    @GetMapping("/indicadores-mensuales")
    @Operation(summary = "Obtener indicadores mensuales", description = "Obtiene los indicadores mensuales del sistema")
    public ResponseEntity<RespuestaDto<IndicadoresMensualesDto>> ObtenerIndicadoresMensuales() {
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "indicadores mensuales"));
    }



    
    @GetMapping("/historico-indicadores")
    @Operation(summary = "Obtener historico indicadores", description = "Obtiene el historico de indicadores del sistema")
    public ResponseEntity<RespuestaDto<HistoricoIndicadoresDto>> ObtenerHistoricoIndicadores(@RequestParam(required = true)String TipoIndicador) {
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "historico indicadores"));
    }


    @GetMapping("/analisis-ventas-agrupadas")
    @Operation(summary = "Obtener analisis ventas agrupadas", description = "Obtiene el analisis de ventas agrupadas del sistema")
    public ResponseEntity<RespuestaDto<AnalisisVentasAgrupadasDto>> obtenerAnalisisVentasAgrupadas(@RequestParam(required = true)String TipoDato){
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "analisis ventas agrupadas"));
    }


}
