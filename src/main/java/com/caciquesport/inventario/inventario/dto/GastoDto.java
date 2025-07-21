package com.caciquesport.inventario.inventario.dto;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for expense records in the inventory system.
 * This record encapsulates all the necessary information for expense operations
 * including creation, updates, and queries.
 * 
 * @param id The unique identifier for the expense record
 * @param valor The monetary value of the expense
 * @param descripcion A descriptive text explaining the expense purpose
 * @param fecha The date when the expense was incurred
 * @param tipoGastoId The foreign key reference to the expense type classification
 */
public record GastoDto(

    // Unique identifier for the expense record
    Integer id,
    
    // Monetary value of the expense in the system's base currency
    double valor,
    
    // Descriptive text explaining the nature or purpose of the expense
    String descripcion,
    
    // Date when the expense was incurred or recorded
    Date fecha,
    
    // Foreign key reference to the expense type (TipoGasto) for categorization
    Integer tipoGastoId
) {
}
