package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;

/**
 * Interfaz para definir los servicios de gestión de TipoGenero.
 * Proporciona métodos para operaciones CRUD básicas en TipoGenero.
 */
public interface TipoGeneroServicio {

    Integer crearGenero(TipoGenero nuevoTipoGenero) throws Exception;

    Integer actualizarGenero(TipoGenero tipoGenero) throws Exception;

    void eliminarGenero(String genero) throws Exception;

    TipoGenero obtenerGenero(String genero) throws Exception;
    
    List<TipoGenero> listarGeneros() throws Exception;
}
