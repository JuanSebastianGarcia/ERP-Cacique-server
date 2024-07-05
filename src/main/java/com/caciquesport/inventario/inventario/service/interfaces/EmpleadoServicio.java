package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import java.util.List;

/**
 * Interfaz para definir los servicios de gestión de Empleado.
 * Proporciona métodos para operaciones CRUD básicas en Empleado.
 */
public interface EmpleadoServicio {

    /**
     * Método para crear un nuevo Empleado.
     *
     * @param nuevoEmpleadoDto Los datos del nuevo Empleado a crear.
     * @return El ID del Empleado creado.
    */
    Integer crearEmpleado(EmpleadoDto nuevoEmpleadoDto) throws Exception;

    /**
     * Método para actualizar un Empleado existente.
     *
     * @param empleado Los datos del Empleado a actualizar.
     * @return El ID del Empleado actualizado.
    */
    Integer actualizarEmpleado(Empleado empleado) throws Exception;

    /**
     * Método para eliminar un Empleado por su cédula.
     *
     * @param cedula La cédula del Empleado a eliminar.
    */
    void eliminarEmpleado(String cedula) throws Exception;

    /**
     * Método para obtener un Empleado por su cédula.
     *
     * @param cedula La cédula del Empleado a obtener.
     * @return El Empleado encontrado.
*/
    Empleado obtenerEmpleado(String cedula) throws Exception;

    /**
     * Método para listar todos los Empleados.
     *
     * @return Una lista de todos los Empleados.
    */
    List<EmpleadoDto> listarEmpleado() throws Exception;

}

