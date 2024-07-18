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
    public FacturaTest(FacturaServiceImpl facturaServiceImpl, ProductoServicioImpl productoServicioImpl) {
        this.facturaServiceImpl = facturaServiceImpl;
        this.productoServicioImpl = productoServicioImpl;
    }

    /**
     * este test se encarga de validar que una factura es generada y se recibe la
     * respuesta esperada
     */
    @Test
    public void generarFactura() throws Exception {

        List<ProductoFacturaDto> listaProductos = new ArrayList<>();

        listaProductos
                .add(new ProductoFacturaDto(0, "camisa", "robledo", "10", "diario", "hombre", 60000, "ENTREGADO"));
        listaProductos
                .add(new ProductoFacturaDto(0, "pantalon", "tecnologico", "s", "fisica", "mujer", 60000, "PENDIENTE"));

        FacturaDto facturaDto = new FacturaDto(0, "1234567890", listaProductos, "EFECTIVO", 60000);

        String respuesta = facturaServiceImpl.generarFactura(facturaDto);

        Assertions.assertEquals("la factura ha sido creada y su estado es :PENDIENTE", respuesta);

        validarEliminacionDeUnidades();

        guardarCambiosFactura();
    }



    /*
     * Este metodo se encarga de validar si la cantidad de un producto es restada
     * despues de registrar la factura.
     * es importante que sea ejecutado desde el metodo "generar factura()" para
     * poder generar una factura, y hacer la
     * correcta validacion
     */
    @Test
    public void validarEliminacionDeUnidades() throws Exception {

        // el producto que se busca es el agregado a la factura anterior
        Producto producto = productoServicioImpl
                .buscarObjetoProducto(new FiltroProductoDto("camisa", "10", "diario", "hombre", "robledo"));

        int cantidadActualProducto = producto.getDetalleProducto().getCantidad();

        // en los datos de prueba este producto tiene 10 unidades, por lo que despues de
        // generar la factura debera ser 9
        Assertions.assertEquals(9, cantidadActualProducto);

    }



    /*
     * Este metodo nos permite verificar que una factura esta procesando los
     * cambios. es importante que sea ejecutada
     * despues de generar una factura para validar los cambios en la factura
     * previamente generada
     */
    @Test
    public void guardarCambiosFactura() throws Exception {

        FacturaDto facturaEncontrada = facturaServiceImpl.buscarFacturaDto("1234567890", "cedula").get(0);

        List<ProductoFacturaDto> listaProductos = new ArrayList<>();

        listaProductos=facturaEncontrada.listaProductos();

        int posicion=0;

        for(int i=0;i<listaProductos.size();i++){
            if(listaProductos.get(i).estado().equals("PENDIENTE")){
                posicion=i;
                break;
            }
        }

        listaProductos.remove(posicion);
    
        FacturaDto facturaDto = new FacturaDto(facturaEncontrada.idFactura(), "1234567890", listaProductos, "EFECTIVO", 0);


        String respuesta = facturaServiceImpl.guardarCambios(facturaDto);

        Assertions.assertEquals("La factura ha sido actualizada y el estado es:FINALIZADA",respuesta);
    }
}
