package com.caciquesport.inventario.inventario.entity;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.service.implementations.FacturaServiceImpl;

@SpringBootTest
public class FacturaTest {

    /*
     * Servicios
     */
    private FacturaServiceImpl facturaServiceImpl;

    @Autowired
    public FacturaTest(FacturaServiceImpl facturaServiceImpl){
        this.facturaServiceImpl=facturaServiceImpl;
    }

    /**
     * este test se encarga de validar que una factura es generada y se recibe la respuesta esperada
     */
    @Test
    public void generarFactura() throws Exception{

        List<ProductoFacturaDto> listaProductos = new ArrayList<>();

        listaProductos.add(new ProductoFacturaDto("camisa", "robledo", "10", "diario", "hombre",60000,"ENTREGADO"));
        listaProductos.add(new ProductoFacturaDto("pantalon", "tecnologico", "s", "fisica", "mujer",60000,"ENTREGADO"));


        FacturaDto facturaDto = new FacturaDto("1234567890", listaProductos, "EFECTIVO", 120.000);

        
        String respuesta=facturaServiceImpl.generarFactura(facturaDto);
        
        Assertions.assertEquals("la factura ha sido creada y su estado es :FINALIZADA", respuesta);
    }
}

