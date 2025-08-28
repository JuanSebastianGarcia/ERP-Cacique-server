package com.caciquesport.inventario.inventario.dto;

/**
 * Data Transfer Object for daily Key Performance Indicators (KPIs).
 * Contains comprehensive metrics for a specific date including total income,
 * total expenses, and number of invoices issued.
 * 
 * @author System
 * @version 1.0
 * @since 2024
 */
public record IndicadoresDiariosDto(
    
    /**
     * Total income received in cash for the specified date.
     * This represents the sum of all completed payments from invoices.
     */
    double ingresosTotalesCaja,
    
    /**
     * Total expenses incurred on the specified date.
     * This represents the sum of all expenses recorded for the day.
     */
    double gastosTotales,
    
    /**
     * Total number of invoices issued on the specified date.
     * This represents the count of all invoices created for the day.
     */
    int numeroFacturasEmitidas
    
) {
    
    /**
     * Calculates the net income for the day (income minus expenses).
     * 
     * @return The net income value
     */
    public double getIngresoNeto() {
        return ingresosTotalesCaja - gastosTotales;
    }
    
    /**
     * Checks if the day was profitable (positive net income).
     * 
     * @return true if net income is positive, false otherwise
     */
    public boolean esDiaRentable() {
        return getIngresoNeto() > 0;
    }
}
