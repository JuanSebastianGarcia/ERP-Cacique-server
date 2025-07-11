package com.caciquesport.inventario.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.EmpleadoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/empleado")
@AllArgsConstructor
@Tag(name = "Empleado", description = "Operaciones para gestionar empleados en el sistema")
public class EmpleadoController {

    /*
     * SERVICIOS
     */
    private final EmpleadoServicio empleadoServicio;

    /*
     * API encargada de la creación de un empleado
     * 
     * @param empleadoDto - contiene la información necesaria para la creación
     */
    @PostMapping
    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado en el sistema.")
    public ResponseEntity<RespuestaDto<String>> crearEmpleado(@RequestBody @Valid EmpleadoDto EmpleadoDto) throws Exception {
        Integer id = empleadoServicio.crearEmpleado(EmpleadoDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "el empleado ha sido creado id:" + id));
    }

    /*
     * API encargada de la eliminación de un empleado
     * 
     * @param cedula - cédula del empleado que se va a eliminar
     */
    @DeleteMapping("/{cedula}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado existente según su cédula.")
    public ResponseEntity<RespuestaDto<String>> eliminarEmpleado(
            @Parameter(description = "Cédula del empleado") @PathVariable("cedula") String cedula) throws Exception {
        empleadoServicio.eliminarEmpleado(cedula);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "el empleado ha sido eliminado"));
    }

    /*
     * API encargada de la búsqueda de todos los empleados
     */
    @GetMapping
    @Operation(summary = "Listar empleados", description = "Obtiene una lista de todos los empleados registrados.")
    public ResponseEntity<RespuestaDto<List<EmpleadoDto>>> buscarEmpleados() throws Exception {
        List<EmpleadoDto> lista = empleadoServicio.listarEmpleado();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }
}
