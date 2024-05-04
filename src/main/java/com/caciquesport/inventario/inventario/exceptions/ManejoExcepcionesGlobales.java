package com.caciquesport.inventario.inventario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.exceptions.types.EmpleadoNoEncontradoException;

@RestControllerAdvice
public class ManejoExcepcionesGlobales {


    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<RespuestaDto> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException exception){

        return ResponseEntity.badRequest().body(new RespuestaDto<>(true,HttpStatus.BAD_REQUEST));
    }




}
