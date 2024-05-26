package com.caciquesport.inventario.inventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.service.implementations.ProductoServicioImpl;

@SpringBootTest
public class ProductoControllerTest {

    //service
    @Autowired
    ProductoServicioImpl productoServicioImpl;

    /*
     * Test que valida el funcionamiento del caso 2
     * verifica que un producto si sea creado
     */
    @Test
    public void crearProducto() throws Exception{

        //se crea el dto
        ProductoDto productoDto =new ProductoDto(1, "camisa", "robledo", "10"
        ,"diario", "hombre", 30000, 10, "producto en tela");

        //envio de la informacion
        Integer id_Producto=productoServicioImpl.crearProducto(productoDto);

        //se valida que el id sea un numero
        assertTrue(!(id_Producto.toString().equals("")));
    }

    /*
     * Test que valida el funcionamiento del caso 3 
     * valida que una lista de productos si este siento encontrada 
     */
    @Test
    public void buscarProducto() throws Exception{

        //crear producto de prueba
        crearProducto();
        
        //se crea el filtro
        FiltroProductoDto filtroProductoDto=new FiltroProductoDto(""
        , ""
        , ""
        , ""
        , ""
        );

        //se hace la peiticon y se envia la informacion
        List<ProductoDto> lista=productoServicioImpl.filtrarListaProducto(filtroProductoDto);

        //validar que el objeto no esta vacio
        assertEquals(false,lista.isEmpty());
    }    
    

    /*
     * Test que valida el funcionamiento del caso 4
     */
    @Test
    public void eliminarProducto() throws Exception{

        //crear producto de prueba
        Integer id_producto=crearProductoPrueba();

        //se verifica que el producto existe buscandol
        assertFalse(productoServicioImpl.filtrarListaProducto(new FiltroProductoDto("camisa", "10", "diario", "hombre", "robledo")).isEmpty());

        //datos del producto especifico que se elimina
        productoServicioImpl.eliminarProducto(id_producto);

         //se verifica que el producto ya no existe
         assertTrue(productoServicioImpl.filtrarListaProducto(new FiltroProductoDto("camisa", "10", "diario", "hombre", "robledo")).isEmpty());

    }    
    


    @Test
    public void actualizarProducto() throws Exception{

        //se crea un producto de prueba
        Integer id_producto=crearProductoPrueba();

        //se actualiza el producto creado
        productoServicioImpl.actualizarProducto(new ProductoDto(id_producto, "camisa", "robledo", "10", "diario", "hombre", 50000, 15,"nueva descripcion"));
        
        //se busca la informacion del producto
        ProductoDto productoDto = productoServicioImpl.filtrarListaProducto(new FiltroProductoDto("camisa", "10", "diario", "hombre", "robledo")).get(0);
    
        //se validan los 3 nuevos campor, precio, cantidad y descripcion
        assertEquals(50000, productoDto.precio());
        assertEquals(15,productoDto.cantidad());
        assertEquals("nueva descripcion", productoDto.descripcion());
    
    }




    /*
     * crear un producto con datos especificos
     * 
     * @return id del producto creado que es autogenerado
     */
    private Integer crearProductoPrueba() throws Exception {

         //se crea el dto
         ProductoDto productoDto =new ProductoDto(1, "camisa", "robledo", "10"
         ,"diario", "hombre", 30000, 10, "producto en tela");

         
        return productoServicioImpl.crearProducto(productoDto);
    }

}
