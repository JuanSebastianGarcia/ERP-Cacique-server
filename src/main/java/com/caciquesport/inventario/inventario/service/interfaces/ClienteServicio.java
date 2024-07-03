package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.ClienteDto;

/**
 * Interfaz para definir los servicios de gestión de Cliente.
 * Proporciona métodos para operaciones CRUD básicas en Empleado.
 */
public interface ClienteServicio {

    /*
     * Metodo abstracto usado para la creacion de un cliente
     */
    ClienteDto crearCliente(ClienteDto nuevoClienteDto)throws Exception ;

    /*
     * Metodo abstracto para la busqueda de un cliente
     */
    ClienteDto buscarCliente(String cedula)throws Exception ;

    /*
     * Metodo abstracto para actualizacion de un cliente
     */
    String actualizarCliente(ClienteDto clienteDto)throws Exception ;

}
