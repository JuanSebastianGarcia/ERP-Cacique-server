package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

/**
 * Interfaz para definir los servicios de gestión de TipoHorario.
 * Proporciona métodos para operaciones CRUD básicas en TipoHorario.
 */
public interface TipoHorarioServicio {


    
    Integer crearHorario(TipoHorario nuevoTipoHorario) throws Exception;

    Integer actualizarHorario(TipoHorario tipoHorario) throws Exception;

    void eliminarHorario(String horario) throws Exception;

    TipoHorario obtenerHorario(String horario) throws Exception;

    List<TipoHorario> listarHorarios() throws Exception;

}
