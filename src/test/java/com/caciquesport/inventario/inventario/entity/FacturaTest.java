package com.caciquesport.inventario.inventario.entity;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.service.implementations.FacturaServiceImpl;
import com.caciquesport.inventario.inventario.service.implementations.ProductoServicioImpl;

@SpringBootTest
public class FacturaTest {

    /*
     * Servicios
     */
    private FacturaServiceImpl facturaServiceImpl;
    private ProductoServicioImpl productoServicioImpl;

    @Autowired
    public FacturaTest(FacturaServiceImpl facturaServiceImpl, ProductoServicioImpl productoServicioImpl){
        this.facturaServiceImpl=facturaServiceImpl;
        this.productoServicioImpl=productoServicioImpl;
    }

    /**
     * este test se encarga de validar que una factura es generada y se recibe la respuesta esperada
     */
    @Test
    public void generarFactura() throws Exception{

        List<ProductoFacturaDto> listaProductos = new ArrayList<>();

        listaProductos.add(new ProductoFacturaDto("camisa", "robledo", "10", "diario", "hombre",60000,"ENTREGADO"));
        listaProductos.add(new ProductoFacturaDto("pantalon", "tecnologico", "s", "fisica", "mujer",60000,"PENDIENTE"));


        FacturaDto facturaDto = new FacturaDto(0,"1234567890", listaProductos, "EFECTIVO", 60000);

        String respuesta=facturaServiceImpl.generarFactura(facturaDto);
        
        Assertions.assertEquals("la factura ha sido creada y su estado es :PENDIENTE", respuesta);

        validarEliminacionDeUnidades();
    }


    /*
     * Este metodo se encarga de validar si la cantidad de un producto es restada despues de registrar la factura.
     * es importante que sea ejecutado desde el metodo "generar factura()" para poder generar una factura, y hacer la
     * correcta validacion
     */
    @Test
    public void validarEliminacionDeUnidades() throws Exception{

        //el producto que se busca es el agregado a la factura anterior
        Producto producto=productoServicioImpl.buscarObjetoProducto(new FiltroProductoDto("camisa", "10", "diario", "hombre", "robledo"));

        int cantidadActualProducto = producto.getDetalleProducto().getCantidad();
        
        //en los datos de prueba este producto tiene 10 unidades, por lo que despues de generar la factura debera ser 9
        Assertions.assertEquals(0, cantidadActualProducto);

    }
}

