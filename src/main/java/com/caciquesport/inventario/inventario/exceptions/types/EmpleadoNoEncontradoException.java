package com.caciquesport.inventario.inventario.exceptions.types;

/*
 * excepcion para cuando un usuario no es validado
 */
public class EmpleadoNoEncontradoException extends RuntimeException{


    public EmpleadoNoEncontradoException(String mensaje){
        super(mensaje);
    }


}
