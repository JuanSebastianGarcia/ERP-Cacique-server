package com.caciquesport.inventario.inventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.service.implementations.EmpleadoServicioImpl;

@SpringBootTest
public class EmpleadoControllerTest {

    //servicios
    @Autowired
    EmpleadoServicioImpl empleadoServicioImpl;

    @Test
    public void crearEmpleado() throws Exception{

        //se crea el empleado
        empleadoServicioImpl.crearEmpleado(new EmpleadoDto(1, "juan", "1005", "3145849871", "juan@gmail.com", "123", "JEFE"));
    
        //se busca la lista de empleados
        assertEquals(false,empleadoServicioImpl.listarEmpleado().isEmpty());
    }

    @Test
    public void buscarEmpleado() throws Exception{
        
        //en la base de prueba se tiene un empleado, verificar su existencia. se busca la lista de empleados
        assertEquals(false,empleadoServicioImpl.listarEmpleado().isEmpty());
    }

    @Test
    public void EliminarEmpleado() throws Exception{
        
        //se elimina el empleado en la base de prueba h2
        empleadoServicioImpl.eliminarEmpleado("1005133918");

        //se busca la lista de empleados
        assertEquals(true,empleadoServicioImpl.listarEmpleado().isEmpty());
    }



}
