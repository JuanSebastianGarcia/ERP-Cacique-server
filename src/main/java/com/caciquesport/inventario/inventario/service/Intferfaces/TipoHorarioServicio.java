package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

public interface TipoHorarioServicio {


    
    Integer crearTipoHorario(TipoHorarioServicio tipoHorario) throws Exception;

    Integer actudalizarTipoHorario(TipoHorarioServicio tipoHorario) throws Exception;

    void eliminarTipoHorario(TipoHorarioServicio tipoHorario) throws Exception;

    TipoHorarioServicio obtenerTipoHortario(TipoHorarioServicio tipoHorario) throws Exception;

    List<TipoHorarioServicio> listarTipoHorarios() throws Exception;

}
