package com.caciquesport.inventario.inventario.service.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;
import com.caciquesport.inventario.inventario.repository.TipoGeneroRepository;
import com.caciquesport.inventario.inventario.service.interfaces.TipoGeneroServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class TipoGeneroServicioImpl implements TipoGeneroServicio{

    private final TipoGeneroRepository tipoGeneroRepository;

    /*
     * crear genero
     * 
     * @param nuevoTipoGenero - objeto tipoGenero que se va a almacenar 
     * 
     * @Return - id del genero almacenado
     */
    @Override
    public void crearGenero(String nuevoTipoGenero) throws Exception {

        try {
            TipoGenero genero = new TipoGenero();
            genero.setGenero(nuevoTipoGenero);
            tipoGeneroRepository.save(genero).getId();
        } catch (Exception e) { 
            throw new Exception("el horario esta repetido"); 
        }

    }


    /*
     * eliminar tipoGenero. se busca por medio del nombre el cual es unico. si no se encuentra lanza una excepcion
     * 
     * @param - el nombre del genero
     */
    @Override
    public void eliminarGenero(String genero) throws Exception {

        Optional<TipoGenero> tipoGeneroEncontrado=tipoGeneroRepository.findByGenero(genero);

        if(tipoGeneroEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar el genero");
        }else{
            tipoGeneroRepository.delete(tipoGeneroEncontrado.get());
        }
    }



    /*
     * obtener tipoGenero. lo hace por medio del nombre el cual es unico. si no lo encuentra lanza una excepcion
     * 
     * @param genero-nombre del genero
     * 
     * @return - genero encontrado
     */
    @Override
    public TipoGenero obtenerGenero(String genero) throws Exception {

        Optional<TipoGenero> tipoGeneroEncontrado=tipoGeneroRepository.findByGenero(genero);

        if(tipoGeneroEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar el genero");
        }else{
            return tipoGeneroEncontrado.get();
        }
    }

    

    /*
     * obtener la lista de generos
     * 
     * @return - la lista de generos
     */
    @Override
    public List<ConfigTypesDto> listarGeneros() throws Exception {

        List<TipoGenero> listaGeneros=tipoGeneroRepository.findAll();
        List<ConfigTypesDto> listaDto=new ArrayList<>();

        for (TipoGenero tipoGenero : listaGeneros) {
            listaDto.add(new ConfigTypesDto(tipoGenero.getId(),tipoGenero.getGenero()));
        }

        return listaDto;
    }

}
