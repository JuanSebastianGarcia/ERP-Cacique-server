package com.caciquesport.inventario.inventario.service.Intferfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

public interface TipoHorarioServicio {


    
    Integer crearHorario(TipoHorario nuevoTipoHorario) throws Exception;

    Integer actualizarHorario(TipoHorario tipoHorario) throws Exception;

    void eliminarHorario(String horario) throws Exception;

    TipoHorario obtenerHorario(String horario) throws Exception;

    List<TipoHorario> listarHorarios() throws Exception;

}
