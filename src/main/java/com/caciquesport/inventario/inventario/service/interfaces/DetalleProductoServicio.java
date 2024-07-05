package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;
import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;

/**
 * Interfaz para definir los servicios de gestión de DetalleProducto.
 * Proporciona métodos para operaciones CRUD básicas en DetalleProducto.
 */
public interface DetalleProductoServicio {

    /**
     * Método para crear un nuevo DetalleProducto.
     *
     * @param nuevoDetalleProducto Los datos del nuevo DetalleProducto a crear.
     * @return El ID del DetalleProducto creado.

     */
    Integer crearDetalleProducto(DetalleProducto nuevoDetalleProducto) throws Exception;

    /**
     * Método para actualizar un DetalleProducto existente.
     *
     * @param detalleProducto Los datos del DetalleProducto a actualizar.
     * @return El ID del DetalleProducto actualizado.
     */
    Integer actualizarDetalleProducto(DetalleProducto detalleProducto) throws Exception;

    /**
     * Método para obtener un DetalleProducto por su ID.
     *
     * @param id El ID del DetalleProducto a obtener.
     * @return El DetalleProducto encontrado.
    */
    DetalleProducto obtenerDetalleProducto(Integer id) throws Exception;

    /**
     * Método para eliminar un DetalleProducto por su ID.
     *
     * @param id El ID del DetalleProducto a eliminar.
    */
    void eliminarDetalleProducto(Integer id) throws Exception;

    /**
     * Método para listar todos los DetalleProducto.
     *
     * @return Una lista de todos los DetalleProducto.
     */
    List<DetalleProducto> listarDetalleProducto() throws Exception;

}
