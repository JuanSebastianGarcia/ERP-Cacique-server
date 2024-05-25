package com.caciquesport.inventario.inventario.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.exceptions.types.EmpleadoNoEncontradoException;

@RestControllerAdvice
public class ManejoExcepcionesGlobales {


    /*
     * maneja la excepcion que indica que un empleado no ha sido encontrado
     */
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<RespuestaDto<String>> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException exception){

        return ResponseEntity.badRequest().body(new RespuestaDto<>(true,exception.getMessage()));
    }

    /*
     * manejar la excepcion mas basica "excepcion"
     * 
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaDto<String>> generalException(Exception exception){
        return ResponseEntity.internalServerError().body( new RespuestaDto<>(true, exception.getMessage())
        );
    }



}
