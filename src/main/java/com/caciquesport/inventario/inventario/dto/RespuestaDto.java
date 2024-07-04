package com.caciquesport.inventario.inventario.dto;

/*
 * Clase que da forma a todas las respuestas realizadas.
 *
 * @param <T> - El tipo de dato u objeto que es retornado en la solicitud.
 */
public record RespuestaDto<T>(

    // Indica si ocurrió un error (true) o no (false).
    boolean error,

    // Dato u objeto cualquiera que es retornado en la solicitud.
    T respuesta
    
) {
}
