package com.caciquesport.inventario.inventario.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO containing a list of movement records.
 * Wraps the collection of RegistroMovimientoDto objects for API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaRegistrosMovimientoDto {
    
    /**
     * List of movement records
     */
    private List<RegistroMovimientoDto> registros;
}
