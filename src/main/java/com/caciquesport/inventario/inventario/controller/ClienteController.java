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
import com.caciquesport.inventario.inventario.service.implementations.ClienteServicioImpl;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/manejocliente")
@AllArgsConstructor
public class ClienteController {

    /*
     * Servicio para las funciones posibles con el cliente
     */
    private final ClienteServicioImpl clienteServicioImpl;


    /**
     * crear un cliente
     * 
     * @param clienteDto - datos del cliente que sera registrado
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @PostMapping("/crearCliente")
    public ResponseEntity<RespuestaDto<ClienteDto>> crearCliente(@RequestBody ClienteDto clienteDto) throws Exception{

        clienteDto = clienteServicioImpl.crearCliente(clienteDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,clienteDto));
    }



    /**
     * buscar un cliente 
     * 
     * @param cedula - codigo unico del cliente
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @GetMapping("/buscarCliente/{cedula}")
    public ResponseEntity<RespuestaDto<ClienteDto>> buscarCliente(@PathVariable("cedula")String cedula) throws Exception{

        ClienteDto clienteDto=clienteServicioImpl.buscarCliente(cedula);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false,clienteDto));
    }



    /**
     * actualizar un cliente
     * 
     * @param clienteDto - datos del cliente que sera registrado
     * 
     * @return clienteDto - datos del cliente registrado
     */
    @PutMapping ("/actualizarCliente")
    public ResponseEntity<RespuestaDto<String>> actualizarCliente(@RequestBody ClienteDto clienteDto) throws Exception{

        String respuesta = clienteServicioImpl.actualizarCliente(clienteDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,respuesta));
    }

}
