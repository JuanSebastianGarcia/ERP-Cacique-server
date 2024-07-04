package com.caciquesport.inventario.inventario.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.ClienteDto;
import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.service.implementations.ClienteServicioImpl;

/*
 * clase para hacer pruebas unitarias del crud de cliente
 */
@SpringBootTest
public class ClienteTest {

    private ClienteServicioImpl clienteServicioImpl;

    @Autowired
    public ClienteTest(ClienteServicioImpl clienteServicioImpl) {
        this.clienteServicioImpl = clienteServicioImpl;
    }

   


    @Test
    public void crearBuscarCliente() throws Exception {

        // crear cliente
        Cliente cliente = new Cliente();
        cliente.setCedula("123");
        cliente.setDireccion("calle45#23-05");
        cliente.setEmail("juan@gmail.com");
        cliente.setNombre("juan");
        cliente.setTelefono("123");

        // convertir objeto cliente en dto
        ClienteDto clienteDto = new ClienteDto(cliente.getCedula(), cliente.getNombre(), cliente.getTelefono(),
                cliente.getEmail(), cliente.getDireccion());

        // guardar cliente
        clienteServicioImpl.crearCliente(clienteDto);

        // buscar cliente
        ClienteDto clienteEncontradoDto = clienteServicioImpl.buscarCliente("123");
        Cliente clienteEncontrado = convertirDtoEnObjeto(clienteEncontradoDto);

        // comparar clientes
        Assertions.assertEquals(cliente, clienteEncontrado);
    }

   
    /*
     * convirte un dto de un cliente en un objeto
     */
    private Cliente convertirDtoEnObjeto(ClienteDto clienteDto) {
        return new Cliente(clienteDto.cedula(), clienteDto.nombre(), clienteDto.telefono(),
                clienteDto.email(), clienteDto.direccion());
    }


}
