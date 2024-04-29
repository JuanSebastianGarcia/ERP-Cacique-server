package com.caciquesport.inventario.inventario.dto;

/*
 * Clase que da forma a totdas las respuestas realizadas
 * 
 * @param error - indica si ocurrio un error(true) o no(false)
 * @param respuesta - dato u objeto cualquiera
 */
public record RespuestaDto<T>(boolean error,T respuesta) {

}
