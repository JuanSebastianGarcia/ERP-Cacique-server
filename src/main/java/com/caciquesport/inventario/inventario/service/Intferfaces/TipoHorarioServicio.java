package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

public interface TipoHorarioServicio {


    
    Integer crearTipoHorario(TipoHorario nuevoTipoHorario) throws Exception;

    Integer actualizarTipoHorario(TipoHorario tipoHorario) throws Exception;

    void eliminarTipoHorario(String horario) throws Exception;

    TipoHorario obtenerTipoHortario(String horario) throws Exception;

    List<TipoHorario> listarTipoHorarios() throws Exception;

}
