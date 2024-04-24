package com.caciquesport.inventario.inventario.service.Intferfaces;

import com.caciquesport.inventario.inventario.model.entity.Empleado;
import java.util.List;

public interface EmpleadoServicio {

    Integer crearEmpleado(Empleado nuevoEmpleado) throws Exception;

    Integer actualizarEmpleado(Empleado empleado) throws Exception;

    void eliminarEmpleado(String cedula) throws Exception;

    Empleado obtenerEmpleado(String cedula) throws Exception;

    List<Empleado> listarEmpleado() throws Exception;

}
