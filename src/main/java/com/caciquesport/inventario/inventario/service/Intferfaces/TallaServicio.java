package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

public interface TallaServicio {

    Integer crearTalla(TallaServicio nuevTalla) throws Exception;

    Integer actualizarTalla(TallaServicio talla) throws Exception;

    void eliminarTalla(String talla) throws Exception;

    TallaServicio obtenerTalla(String talla) throws Exception;

    List<TallaServicio> listarTallas() throws Exception;
}
