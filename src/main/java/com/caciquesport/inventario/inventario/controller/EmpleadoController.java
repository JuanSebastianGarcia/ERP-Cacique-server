package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.EmpleadoServicioImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/manejoEmpleado")
@AllArgsConstructor
public class EmpleadoController {

    /*
     * SERVICIOS
     */
    private final EmpleadoServicioImpl empleadoServicioImpl;


    @PostMapping("/crearEmpleado")
    public ResponseEntity<RespuestaDto<String>> crearEmpleado(@RequestBody @Valid EmpleadoDto EmpleadoDto) throws Exception{

        Integer id=empleadoServicioImpl.crearEmpleado(EmpleadoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el empleado ha sido creado id:"+id));
    }
}
