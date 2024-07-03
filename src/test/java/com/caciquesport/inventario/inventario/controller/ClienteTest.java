package com.caciquesport.inventario.inventario.controller;

import org.junit.jupiter.api.Test;

import com.caciquesport.inventario.inventario.model.entity.Cliente;

/*
 * clase para hacer pruebas unitarias del crud de cliente
 */
public class ClienteTest {




    @Test
    public void crearBuscarCliente(){

        //crear cliente
        Cliente cliente=new Cliente();
        cliente.setCedula("123");
        
        //guardar cliente


        //buscar cliente
        Cliente clienteEncontrado = buscarCliente();
        
        
        //comparar clientes

    }


    public Cliente buscarCliente(){
        return null;
    }




}
