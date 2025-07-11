package com.caciquesport.inventario.inventario.controller;

import com.caciquesport.inventario.inventario.dto.ClienteDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.ClienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@AllArgsConstructor
@Tag(name = "Cliente", description = "Operaciones relacionadas con la gestión de clientes")
public class ClienteController {

    private final ClienteServicio clienteServicio;

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param clienteDto Datos del cliente a registrar.
     * @return Datos del cliente registrado.
     */
    @PostMapping
    @Operation(
        summary = "Crear cliente",
        description = "Registra un nuevo cliente en el sistema con la información proporcionada"
    )
    public ResponseEntity<RespuestaDto<ClienteDto>> crearCliente(
            @RequestBody @Valid ClienteDto clienteDto) throws Exception {

        clienteDto = clienteServicio.crearCliente(clienteDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, clienteDto));
    }

    /**
     * Obtiene los datos de un cliente según su cédula.
     *
     * @param cedula Identificador único del cliente.
     * @return Datos del cliente encontrado.
     */
    @GetMapping("/{cedula}")
    @Operation(
        summary = "Buscar cliente",
        description = "Consulta los datos de un cliente registrado usando su cédula"
    )
    public ResponseEntity<RespuestaDto<ClienteDto>> buscarCliente(
            @Parameter(description = "Cédula del cliente", required = true)
            @PathVariable("cedula") String cedula) throws Exception {

        ClienteDto clienteDto = clienteServicio.buscarCliente(cedula);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, clienteDto));
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param clienteDto Nuevos datos del cliente.
     * @param cedula Cédula del cliente a actualizar.
     * @return Mensaje indicando el resultado de la operación.
     */
    @PutMapping("/{cedula}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza los datos de un cliente existente identificado por su cédula"
    )
    public ResponseEntity<RespuestaDto<String>> actualizarCliente(
            @RequestBody ClienteDto clienteDto,
            @Parameter(description = "Cédula del cliente", required = true)
            @PathVariable("cedula") String cedula) throws Exception {

        String respuesta = clienteServicio.actualizarCliente(clienteDto, cedula);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, respuesta));
    }
}
