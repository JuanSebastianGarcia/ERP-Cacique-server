package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

/**
 * Interfaz para definir los servicios de gestión de TipoHorario.
 * Proporciona métodos para operaciones CRUD básicas en TipoHorario.
 */
public interface TipoHorarioServicio {

    /**
     * Crear un nuevo horario.
     *
     * @param nuevoTipoHorario El objeto de tipo ConfigTypesDto que representa el nuevo horario a almacenar.
     */
    void crearHorario(ConfigTypesDto nuevoTipoHorario) throws Exception;

    /**
     * Buscar y obtener un horario por su identificador único.
     *
     * @param horario El identificador único del horario a obtener.
     * @return El objeto de tipo TipoHorario que representa el horario encontrado.
     */
    TipoHorario obtenerHorario(String horario) throws Exception;

    /**
     * Eliminar un horario por su identificador único.
     *
     * @param horario El identificador único del horario a eliminar.
     */
    void eliminarHorario(String horario) throws Exception;

    /**
     * Obtener la lista de todos los horarios.
     *
     * @return Una lista de objetos ConfigTypesDto que representa todos los horarios.
     */
    List<ConfigTypesDto> listarHorarios() throws Exception;
}
