package com.caciquesport.inventario.inventario.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.TipoGastoDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;
import com.caciquesport.inventario.inventario.repository.TipoGastoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.TipoGastoServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Implementación de la interfaz TipoGastoServicio.
 * Proporciona la lógica de negocio para la gestión de Tipos de Gasto.
 * Actualmente, los métodos no están implementados y lanzan una excepción.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TipoGastoServicioImpl implements TipoGastoServicio{

    /*
     * Repositorio
     */
    private final TipoGastoRepository tipoGastoRepository;


    /**
     * Crea un nuevo tipo de gasto.
     * 
     * @param tipoGastoDto Cadena JSON o representación del DTO del tipo de gasto a crear.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la creación.
     */
    @Override
    public String crearTipoGasto(String tipoGastoDto) throws Exception {
        
        //buscar si ya existe
        Optional<TipoGasto> tipoGastoEncontrado = tipoGastoRepository.findByNombre(tipoGastoDto);

        if(tipoGastoEncontrado.isPresent()){
            throw new Exception("El tipo de gasto ya existe");
        }
        
        //si no existe, crearlo
        tipoGastoRepository.save(new TipoGasto(null, tipoGastoDto));

        //retornar respuesta
        return "Tipo de gasto creado correctamente";
    }


    /**
     * Busca y retorna la lista de todos los tipos de gasto existentes.
     * 
     * @return Lista de objetos TipoGastoDto.
     * @throws Exception si ocurre un error durante la consulta.
     */
    @Override
    public List<TipoGastoDto> buscarTiposGasto() throws Exception {
        
        List<TipoGasto> tiposGasto = tipoGastoRepository.findAll();

        List<TipoGastoDto> tiposGastoDto = convertObjectDto(tiposGasto);

        return tiposGastoDto;
    }


    /**
     * Actualiza un tipo de gasto existente identificado por su ID.
     * 
     * @param tipoGastoDto Cadena JSON o representación del DTO con los nuevos datos.
     * @param id Identificador del tipo de gasto a actualizar.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la actualización.
     */
    @Override
    public String actualizarTipoGasto(String tipoGastoDto, Integer id) throws Exception {
        
        Optional<TipoGasto> tipoGastoEncontrado = tipoGastoRepository.findById(id);

        if(tipoGastoEncontrado.isEmpty()){
            throw new Exception("El tipo de gasto no existe");
        }

        tipoGastoEncontrado.get().setNombre(tipoGastoDto);

        tipoGastoRepository.save(tipoGastoEncontrado.get());
        
        return "Tipo de gasto actualizado correctamente";
    }

    /**
     * Elimina un tipo de gasto por su ID.
     * 
     * @param id Identificador del tipo de gasto a eliminar.
     * @return Mensaje de confirmación o información relevante.
     * @throws Exception si ocurre un error durante la eliminación.
     */
    @Override
    public String eliminarTipoGasto(Integer id) throws Exception {
        
        tipoGastoRepository.deleteById(id);

        return "Tipo de gasto eliminado correctamente";
    }


    /**
     * Convierte una lista de objetos TipoGasto a una lista de objetos TipoGastoDto.
     * 
     * @param tiposGasto Lista de objetos TipoGasto a convertir.
     * @return Lista de objetos TipoGastoDto.
     */
    private List<TipoGastoDto> convertObjectDto(List<TipoGasto> tiposGasto){
        
        List<TipoGastoDto> tiposGastoDto = new ArrayList<>();

        for(TipoGasto tipoGasto : tiposGasto){
            tiposGastoDto.add(new TipoGastoDto(tipoGasto.getId(), tipoGasto.getNombre()));
        }

        return tiposGastoDto;
    }
    
}
