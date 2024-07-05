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

    /**
     * Método para crear un nuevo Producto.
     *
     * @param registroProductoDto Los datos del nuevo Producto a crear.
     * @return El ID del Producto creado.
     */
    Integer crearProducto(ProductoDto registroProductoDto) throws Exception;

    /**
     * Método para actualizar un Producto existente.
     *
     * @param productoDto Los datos del Producto a actualizar.
     * @return El ID del Producto actualizado.
     */
    Integer actualizarProducto(ProductoDto productoDto) throws Exception;

    /**
     * Método para eliminar un Producto por su ID.
     *
     * @param id El ID del Producto a eliminar.
     */
    void eliminarProducto(Integer id) throws Exception;

    /**
     * Método para filtrar una lista de Productos según los criterios especificados.
     *
     * @param filtroProductoDto Los criterios de filtrado.
     * @return Una lista de Productos que coinciden con los criterios de filtrado.
     */
    List<ProductoDto> filtrarListaProducto(FiltroProductoDto filtroProductoDto) throws Exception;

    /**
     * Método para listar todos los Productos.
     *
     * @return Una lista de todos los Productos.
     */
    List<Producto> listarProductos() throws Exception;


    /**
     * Método que busca un producto por ciertos parametros y retorna el objeto
     *
     * @return Una lista de todos los Productos.
     */
    Producto buscarObjetoProducto(FiltroProductoDto filtroProductoDto) throws Exception; 
}

