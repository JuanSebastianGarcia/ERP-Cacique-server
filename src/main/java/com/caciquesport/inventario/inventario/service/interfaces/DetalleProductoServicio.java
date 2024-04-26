package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;
import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;

/**
 * Interfaz para definir los servicios de gestión de DetalleProducto.
 * Proporciona métodos para operaciones CRUD básicas en DetalleProducto.
 */
public interface DetalleProductoServicio {

    Integer crearDetalleProducto(DetalleProducto nuevoDetalleProducto) throws Exception;

    Integer actualizarDetalleProducto(DetalleProducto detalleProducto) throws Exception;

    DetalleProducto obtenerDetalleProducto(Integer Id) throws Exception;

    void EliminarDetalleProducto(Integer Id) throws Exception;

    List<DetalleProducto> listarDetalleProducto() throws Exception;

    
}
