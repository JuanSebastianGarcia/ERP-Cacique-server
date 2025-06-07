package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.caciquesport.inventario.inventario.dto.ClienteDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.ClienteServicio;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/cliente")
@AllArgsConstructor
public class ClienteController {

    /*
     * Servicio para las funciones posibles con el cliente
     */
    private final ClienteServicio clienteServicio;


    /**
     * crear un cliente
     * 
     * @param clienteDto - datos del cliente que sera registrado
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @PostMapping
    public ResponseEntity<RespuestaDto<ClienteDto>> crearCliente(@RequestBody @Valid ClienteDto clienteDto) throws Exception{

        clienteDto = clienteServicio.crearCliente(clienteDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,clienteDto));
    }



    /**
     * buscar un cliente 
     * 
     * @param cedula - codigo unico del cliente
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @GetMapping("/{cedula}")
    public ResponseEntity<RespuestaDto<ClienteDto>> buscarCliente(@PathVariable("cedula")String cedula) throws Exception{

        ClienteDto clienteDto=clienteServicio.buscarCliente(cedula);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false,clienteDto));
    }



    /**
     * actualizar un cliente
     * 
     * @param clienteDto - datos del cliente que sera registrado
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @PutMapping("/{cedula}")
    public ResponseEntity<RespuestaDto<String>> actualizarCliente(@RequestBody ClienteDto clienteDto, @PathVariable("cedula") String cedula) throws Exception{

        String respuesta = clienteServicio.actualizarCliente(clienteDto,cedula);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,respuesta));
    }

}
