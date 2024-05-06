package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.model.entity.Producto;

/**
 * Interfaz para definir los servicios de gestión de Producto.
 * Proporciona métodos para operaciones CRUD básicas en Producto.
 */
public interface ProductoServicio {

    Integer crearProducto(ProductoDto RegistroProductoDto) throws Exception;

    Integer actualizarProducto(ProductoDto productoDto) throws Exception;

    void eliminarProducto(Integer id) throws Exception;

    List<ProductoDto> filtrarListaProducto(FiltroProductoDto filtroProductoDto) throws Exception;

    List<Producto> listarProductos() throws Exception;
    
}
