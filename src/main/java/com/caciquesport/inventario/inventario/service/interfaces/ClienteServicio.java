package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.ClienteDto;
/**
 * Interfaz para definir los servicios de gestión de Cliente.
 * Proporciona métodos para operaciones CRUD básicas en Cliente.
 */
public interface ClienteServicio {

    /**
     * registrar un cliente en la basde de datos
     *
     * @param nuevoClienteDto Los datos del nuevo cliente a crear.
     * @return El DTO del cliente creado.
     * @throws Exception Si ocurre un error durante la creación del cliente, 
     *                   como problemas de validación o de conexión con la base de datos.
     */
    ClienteDto crearCliente(ClienteDto nuevoClienteDto) throws Exception;

    /**
     * buscar un cliente en la base de datos por medio de su cedula
     *
     * @param cedula La cédula del cliente a buscar.
     * @return El DTO del cliente encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda del cliente, 
     *                   como si el cliente no existe o problemas de conexión con la base de datos.
     */
    ClienteDto buscarCliente(String cedula) throws Exception;

    /**
     *actualizar un cliente en la base de datos
     *
     * @param clienteDto Los datos del cliente a actualizar.
     * @return Un mensaje indicando el resultado de la actualización.
     * @throws Exception Si ocurre un error durante la actualización del cliente, 
     *                   como problemas de validación o de conexión con la base de datos.
     */
    String actualizarCliente(ClienteDto clienteDto) throws Exception;
}
