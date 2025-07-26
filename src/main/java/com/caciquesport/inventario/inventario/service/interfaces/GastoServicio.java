package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.Date;
import java.util.List;

import com.caciquesport.inventario.inventario.dto.EstadisticasDto;
import com.caciquesport.inventario.inventario.dto.GastoDto;

/**
 * Service interface for managing expense operations in the inventory system.
 * This interface defines the contract for all expense-related business logic
 * including CRUD operations and search functionality.
 * 
 * All methods may throw exceptions for validation errors, database connectivity
 * issues, or other business rule violations.
 */
public interface GastoServicio {

    /**
     * Creates a new expense record in the system.
     * 
     * @param gastoDto The expense data transfer object containing all necessary information
     * @return A success message indicating the expense was created successfully
     * @throws Exception If validation fails, database error occurs, or business rules are violated
     */
    String crearGasto(GastoDto gastoDto) throws Exception;
    
    /**
     * Searches for expenses based on optional filter criteria.
     * If both parameters are null, returns all expenses in the system.
     * 
     * @param fecha Optional date filter to find expenses from a specific date
     * @param tipoGastoId Optional expense type filter to find expenses of a specific category
     * @return A list of expense DTOs matching the search criteria
     * @throws Exception If database error occurs or search parameters are invalid
     */
    List<GastoDto> buscarGastos(Date fecha, Integer tipoGastoId) throws Exception;
    
    /**
     * Updates an existing expense record with new information.
     * 
     * @param id The unique identifier of the expense to update
     * @param gastoDto The updated expense data
     * @return A success message indicating the expense was updated successfully
     * @throws Exception If expense not found, validation fails, or database error occurs
     */
    String actualizarGasto(Integer id, GastoDto gastoDto) throws Exception;
    
    /**
     * Permanently removes an expense record from the system.
     * 
     * @param id The unique identifier of the expense to delete
     * @return A success message indicating the expense was deleted successfully
     * @throws Exception If expense not found, deletion conflicts exist, or database error occurs
     */
    String eliminarGasto(Integer id) throws Exception;


    /**
     * Retrieves statistical information about expenses.
     * 
     * @return A DTO containing total expenses for today, total expenses for the current month, and total number of expenses
     */
    EstadisticasDto obtenerEstadisticas();
}
