package com.caciquesport.inventario.inventario.service.interfaces;

import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import java.util.List;

/**
 * Interfaz para definir los servicios de gestión de Empleado.
 * Proporciona métodos para operaciones CRUD básicas en Empleado.
 */
public interface EmpleadoServicio {

    Integer crearEmpleado(EmpleadoDto nuevoEmpleadoDto) throws Exception;

    Integer actualizarEmpleado(Empleado empleado) throws Exception;

    void eliminarEmpleado(String cedula) throws Exception;

    Empleado obtenerEmpleado(String cedula) throws Exception;

    List<EmpleadoDto> listarEmpleado() throws Exception;

}
