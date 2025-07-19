package com.caciquesport.inventario.inventario.dto;

/**
 * DTO (Data Transfer Object) para la entidad TipoGasto.
 * Se utiliza para transferir información sobre los tipos de gasto
 * entre las distintas capas de la aplicación.
 */
public record TipoGastoDto(

    /**
     * Identificador único del tipo de gasto.
     */
    Integer idTipoGasto,

    /**
     * Nombre o descripción del tipo de gasto.
     */
    String nombreTipoGasto
) {
}
