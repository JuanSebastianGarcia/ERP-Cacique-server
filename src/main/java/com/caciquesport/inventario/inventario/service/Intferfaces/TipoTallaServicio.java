package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;

public interface TipoTallaServicio {

    Integer crearTalla(TipoTalla nuevoTipoTalla) throws Exception;

    Integer actualizarTalla(TipoTalla tipoTalla) throws Exception;

    void eliminarTalla(String talla) throws Exception;

    TipoTalla obtenerTalla(String talla) throws Exception;

    List<TipoTalla> listarTallas() throws Exception;
}
