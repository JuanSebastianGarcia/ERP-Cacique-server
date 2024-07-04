package com.caciquesport.inventario.inventario.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.model.entity.Factura;

@SpringBootTest
public class FacturaTest {

    /*
     * Servicios
     */

     public FacturaTest(){}


    @Test
    public void generarFactura(){

        Factura factura = new Factura();
        factura.setIdFactura(0);
        factura.setListaProductosFactura(null);
        factura.setSoportePago(null);
        
        factura.setCliente(null);//se busca

        factura.setValorFactura(0);//se calcula
        factura.setEstadoFactura(null);//se calcula
        factura.setFechaFactura(null);//se calcula
    }
}

