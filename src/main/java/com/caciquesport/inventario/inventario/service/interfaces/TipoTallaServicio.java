package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;

/**
 * Interfaz para definir los servicios de gestión de TipoTalla.
 * Proporciona métodos para operaciones CRUD básicas en TipoTalla.
 */
public interface TipoTallaServicio {

    /**
     * Crea una nueva talla de prenda en la base de datos.
     *
     * @param nuevoTipoTalla objeto {@code TipoTalla} que contiene la información de la nueva talla
     * @return el identificador generado para la nueva talla
     * @throws Exception si ocurre un error durante la operación de creación
     */
    Integer crearTalla(TipoTalla nuevoTipoTalla) throws Exception;

    Integer actualizarTalla(TipoTalla tipoTalla) throws Exception;

    void eliminarTalla(String talla) throws Exception;

    TipoTalla obtenerTalla(String talla) throws Exception;

    List<TipoTalla> listarTallas() throws Exception;
}
