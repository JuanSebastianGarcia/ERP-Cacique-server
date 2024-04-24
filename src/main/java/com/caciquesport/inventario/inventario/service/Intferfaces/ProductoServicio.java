package com.caciquesport.inventario.inventario.service.Intferfaces;

import java.util.List;
import com.caciquesport.inventario.inventario.model.entity.Producto;

public interface ProductoServicio {

    Integer crearProducto(Producto nuevoProducto) throws Exception;

    Integer actualizarProducto(Producto producto) throws Exception;

    void eliminarProducto() throws Exception;

    Producto obtenerProducto(Producto productoBuscado) throws Exception;

    List<Producto> listarProductos() throws Exception;
    
}
