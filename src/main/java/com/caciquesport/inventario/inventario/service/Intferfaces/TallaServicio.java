package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.Talla;

public interface TallaServicio {

    Integer crearTalla(Talla nuevaTalla) throws Exception;

    Integer actualizarTalla(Talla talla) throws Exception;

    void eliminarTalla(String talla) throws Exception;

    Talla obtenerTalla(String talla) throws Exception;

    List<Talla> listarTallas() throws Exception;
}
