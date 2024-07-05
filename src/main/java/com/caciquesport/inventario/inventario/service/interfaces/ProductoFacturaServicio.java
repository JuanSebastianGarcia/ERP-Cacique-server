package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Factura;

/*
 * Esta interfaz esta hecha para proveer servicios en la relacion que tiene una factura con ciertos productos del inventario
 */
public interface ProductoFacturaServicio {

    /*
     * Metodo que esta encargado de recibir una lista de objetos Dto para con los datos para instanciar los productos.
     * luego debera agregar esta nueva lista de productos instanciados a la factura
     */
    void generarListaProductos(List<ProductoFacturaDto> listaProductos,Factura factura)throws Exception;
}
