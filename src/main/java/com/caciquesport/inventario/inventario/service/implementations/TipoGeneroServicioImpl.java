package com.caciquesport.inventario.inventario.service.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;
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
    public Integer crearGenero(TipoGenero nuevoTipoGenero) {
        return tipoGeneroRepository.save(nuevoTipoGenero).getId();
    }



    /*
     * actualizar TipoGenero. se busca por medio del nombre el cual es unico. si no se encuentra lanza una excepcion
     * 
     * @param tipoGenero - objeto TipoGenero que se va a actualizar
     * 
     * @return - id del objeto tipoGeneto actualizado
     */
    @Override
    public Integer actualizarGenero(TipoGenero tipoGenero) throws Exception {
        
        Optional<TipoGenero> tipoGeneroEncontrado=tipoGeneroRepository.findByGenero(tipoGenero.getGenero());

        if(tipoGeneroEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar el genero");
        }else{
            return tipoGeneroRepository.save(tipoGenero).getId();
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
    public List<TipoGenero> listarGeneros() throws Exception {
        return tipoGeneroRepository.findAll();
    }

}
