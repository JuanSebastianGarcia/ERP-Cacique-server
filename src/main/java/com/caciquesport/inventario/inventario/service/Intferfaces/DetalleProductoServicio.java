package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;
import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;

public interface DetalleProductoServicio {


    Integer crearDetalleProducto(DetalleProducto nuevoDetalleProducto) throws Exception;

    Integer actualizarDetalleProducto(DetalleProducto detalleProducto) throws Exception;

    DetalleProducto obtenerDetalleProducto(Integer Id) throws Exception;

    void EliminarDetalleProducto(Integer Id) throws Exception;

    List<DetalleProducto> listarDetalleProducto() throws Exception;

    
}
