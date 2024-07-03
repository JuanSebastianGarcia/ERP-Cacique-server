package com.caciquesport.inventario.inventario.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

/*
 * clase para hacer pruebas unitarias del crud de cliente
 */
@SpringBootTest
public class ClienteTest {

    @Autowired
    private   ClienteRepository clienteRepository;


    @Test
    public void crearBuscarCliente(){

        //crear cliente
        Cliente cliente=new Cliente();
        cliente.setCedula("123");
        cliente.setDireccion("calle45#23-05");
        cliente.setEmail("juan@gmail.com");
        cliente.setNombre("juan");
        cliente.setTelefono("3233392630");
        
        //guardar cliente
        clienteRepository.save(cliente);

        //buscar cliente
        Cliente clienteEncontrado=clienteRepository.findById("123").get();
        
        
        //comparar clientes
        Assertions.assertEquals(cliente, clienteEncontrado);
    }





}
