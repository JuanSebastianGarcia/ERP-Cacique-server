package com.caciquesport.inventario.inventario.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.EstadisticasDto;
import com.caciquesport.inventario.inventario.dto.GastoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.GastoServicio;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

/**
 * REST Controller for managing expense operations in the inventory system.
 * Provides HTTP endpoints for creating, retrieving, updating, and deleting expense records.
 * 
 * All endpoints return responses wrapped in RespuestaDto for consistent API structure.
 * The controller handles HTTP requests and delegates business logic to the GastoServicio.
 * 
 * Base URL: /api/gasto
 */
@RestController
@RequestMapping("/api/gasto")
@RequiredArgsConstructor
public class GastoController {

    /**
     * Service layer dependency for handling expense-related business operations.
     * Injected via constructor using Lombok's @RequiredArgsConstructor.
     */
    private final GastoServicio gastoServicio;

    /**
     * Creates a new expense record in the system.
     * 
     * HTTP POST /api/gasto
     * 
     * @param gastoDto The expense data transfer object containing all necessary expense information
     * @return ResponseEntity containing success/error status and confirmation message
     * @throws Exception If validation fails, business rules are violated, or database error occurs
     */
    @PostMapping
    public ResponseEntity<RespuestaDto<String>> crearGasto(@RequestBody GastoDto gastoDto) throws Exception {
        
        // Delegate expense creation to service layer
        String respuesta = this.gastoServicio.crearGasto(gastoDto);
        
        // Return success response with confirmation message
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }

    /**
     * Retrieves expenses based on optional filter criteria.
     * 
     * HTTP GET /api/gasto
     * 
     * NOTE: Current implementation uses @PathVariable but should use @RequestParam
     * for optional query parameters (fecha and tipoGastoId).
     * 
     * @param fecha Optional date filter for searching expenses by date
     * @param tipoGastoId Optional expense type ID filter for searching by category
     * @return ResponseEntity containing list of expenses matching the search criteria
     * @throws Exception If database error occurs or search parameters are invalid
     */
    @GetMapping
    public ResponseEntity<RespuestaDto<List<GastoDto>>> buscarGastos(
        @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha, 
        @RequestParam(name = "tipoGastoId", required = false) Integer tipoGastoId) throws Exception {
    
        List<GastoDto> gastos = this.gastoServicio.buscarGastos(fecha, tipoGastoId);
        return ResponseEntity.ok(new RespuestaDto<>(false, gastos));
    }

    /**
     * Updates an existing expense record with new information.
     * 
     * HTTP PUT /api/gasto/{id}
     * 
     * @param id The unique identifier of the expense to update (from URL path)
     * @param gastoDto The updated expense data from request body
     * @return ResponseEntity containing success/error status and confirmation message
     * @throws Exception If expense not found, validation fails, or database error occurs
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDto<String>> actualizarGasto(
        @PathVariable(name = "id") Integer id, 
        @RequestBody GastoDto gastoDto
        ) throws Exception 
    {
        
        // Delegate update operation to service layer
        String respuesta = this.gastoServicio.actualizarGasto(id, gastoDto);
        
        // Return success response with confirmation message
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }

    /**
     * Permanently deletes an expense record from the system.
     * 
     * HTTP DELETE /api/gasto/{id}
     * 
     * @param id The unique identifier of the expense to delete (from URL path)
     * @return ResponseEntity containing success/error status and confirmation message
     * @throws Exception If expense not found, deletion conflicts exist, or database error occurs
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDto<String>> eliminarGasto(
        @PathVariable(name = "id") Integer id) 
        throws Exception 
    {
        
        // Delegate deletion operation to service layer
        String respuesta = this.gastoServicio.eliminarGasto(id);
        
        // Return success response with confirmation message
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }




    @GetMapping("/statistics")
    public ResponseEntity<RespuestaDto<EstadisticasDto>> obtenerEstadisticas() {

        EstadisticasDto estadisticas = this.gastoServicio.obtenerEstadisticas();

        return ResponseEntity.ok(new RespuestaDto<>(false, estadisticas));
    }



}
