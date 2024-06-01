package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

/**
 * Interfaz para definir los servicios de gestión de TipoHorario.
 * Proporciona métodos para operaciones CRUD básicas en TipoHorario.
 */
public interface TipoHorarioServicio {


    /*
    * crear un horario
    * @param nuevoTipoHorario - objeto a almacenar
    */
    void crearHorario(String nuevoTipoHorario) throws Exception;

    /*
    * buscar  un horario
    * @param horario - identificador unico
    */
    TipoHorario obtenerHorario(String horario) throws Exception;


    /*
     * eliminar un horario
     * @param horario - identificador unico
     */
    void eliminarHorario(String horario) throws Exception;


    /*
    * buscar la lista de horarios
    * @param horario - identificador unico
    */
    List<ConfigTypesDto> listarHorarios() throws Exception;

}
