package com.caciquesport.inventario.inventario.controller;

import java.util.List;

import org.checkerframework.dataflow.qual.Pure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.dto.TipoGastoDto;
import com.caciquesport.inventario.inventario.service.interfaces.TipoGastoServicio;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de tipos de gastos.
 * 
 * Este controlador proporciona endpoints para realizar operaciones CRUD
 * sobre los tipos de gastos del sistema de inventario.
 * 
 * @author Sistema de Inventario Cacique Sport
 * @version 1.0
 */
@RestController
@RequestMapping("/api/tipo-gasto")
@RequiredArgsConstructor
@Tag(name = "TipoGasto", description = "Operaciones para la gestión de los tipos de gastos")
public class TipoGastoController {

    /**
     * Servicio para la gestión de tipos de gastos.
     * Inyectado automáticamente por Spring mediante constructor.
     */
    private final TipoGastoServicio tipoGastoServicio;

    /**
     * Crea un nuevo tipo de gasto en el sistema.
     * 
     * @param tipoGasto El nombre del tipo de gasto a crear
     * @return ResponseEntity con el resultado de la operación
     * @throws Exception si ocurre un error durante la creación
     */
    @PostMapping("/{tipoGasto}")
    public ResponseEntity<RespuestaDto<String>> crearTipoGasto(@PathVariable String tipoGasto) throws Exception {
        // Llamar al servicio para crear el tipo de gasto
        String respuesta = tipoGastoServicio.crearTipoGasto(tipoGasto);
        
        // Retornar respuesta exitosa con el mensaje del servicio
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }

    /**
     * Obtiene la lista completa de todos los tipos de gastos registrados.
     * 
     * @return ResponseEntity con la lista de tipos de gastos
     * @throws Exception si ocurre un error durante la consulta
     */
    @GetMapping
    public ResponseEntity<RespuestaDto<List<TipoGastoDto>>> buscarTiposGasto() throws Exception {
        // Obtener todos los tipos de gastos desde el servicio
        List<TipoGastoDto> respuesta = tipoGastoServicio.buscarTiposGasto();

        // Retornar la lista de tipos de gastos
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }

    /**
     * Actualiza un tipo de gasto existente.
     * 
     * @param id El identificador único del tipo de gasto a actualizar
     * @param tipoGasto El nuevo nombre para el tipo de gasto
     * @return ResponseEntity con el resultado de la operación
     * @throws Exception si ocurre un error durante la actualización
     */
    @PutMapping("/{id}/{tipoGasto}")
    public ResponseEntity<RespuestaDto<String>> actualizarTipoGasto(@PathVariable Integer id, @PathVariable String tipoGasto) throws Exception {
        // Actualizar el tipo de gasto usando el servicio
        String respuesta = tipoGastoServicio.actualizarTipoGasto(tipoGasto, id);

        // Retornar respuesta exitosa con el mensaje del servicio
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }

    /**
     * Elimina un tipo de gasto del sistema.
     * 
     * @param id El identificador único del tipo de gasto a eliminar
     * @return ResponseEntity con el resultado de la operación
     * @throws Exception si ocurre un error durante la eliminación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDto<String>> eliminarTipoGasto(@PathVariable Integer id) throws Exception {
        // Eliminar el tipo de gasto usando el servicio
        String respuesta = tipoGastoServicio.eliminarTipoGasto(id);

        // Retornar respuesta exitosa con el mensaje del servicio
        return ResponseEntity.ok(new RespuestaDto<>(false, respuesta));
    }
}