package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.TipoGastoDto;

/**
 * Interfaz para definir los servicios relacionados con la gestión de Tipos de Gasto.
 * Proporciona métodos para operaciones CRUD básicas sobre los tipos de gasto.
 */
public interface TipoGastoServicio {

    /**
     * Crea un nuevo tipo de gasto.
     *
     * @param tipoGastoDto Objeto DTO con los datos del tipo de gasto a crear.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la creación.
     */
    String crearTipoGasto(String tipoGastoDto) throws Exception;

    /**
     * Busca y retorna la lista de todos los tipos de gasto existentes.
     *
     * @return Lista de objetos TipoGastoDto.
     * @throws Exception si ocurre un error durante la consulta.
     */
    List<TipoGastoDto> buscarTiposGasto() throws Exception;

    /**
     * Actualiza un tipo de gasto existente identificado por su ID.
     *
     * @param tipoGastoDto Objeto DTO con los nuevos datos del tipo de gasto.
     * @param id Identificador del tipo de gasto a actualizar.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la actualización.
     */
    String actualizarTipoGasto(String tipoGastoDto, Integer id) throws Exception;

    /**
     * Elimina un tipo de gasto por su ID.
     *
     * @param id Identificador del tipo de gasto a eliminar.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la eliminación.
     */
    String eliminarTipoGasto(Integer id) throws Exception;

}
