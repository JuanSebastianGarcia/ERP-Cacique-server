package com.caciquesport.inventario.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a movement record in the system.
 * Contains information about financial movements including date, type, and value.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMovimientoDto {
    
    /**
     * Date when the movement was recorded
     */
    private String fechaRegistro;
    
    /**
     * Type of movement: "INGRESO" (income) or "GASTO" (expense)
     */
    private String tipoMovimiento;
    
    /**
     * Value of the movement in currency
     */
    private double valorMovimiento;
}
