package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;
import com.caciquesport.inventario.inventario.repository.TipoTallaRepository;
import com.caciquesport.inventario.inventario.service.interfaces.TipoTallaServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoTallaServicioImpl implements TipoTallaServicio{

    private final TipoTallaRepository tallaRepository;



    /*
     * crear una talla. 
     * 
     * @param nuevaTalla- objeto talla que se va a almacenar
     */
    @Override
    public Integer crearTalla(TipoTalla nuevoTipoTalla) throws Exception {
        return tallaRepository.save(nuevoTipoTalla).getId();
    }



    /*
     * actualizar talla. se busca por medio del nombre de la talla. si no se encuentra lanza una excepcion
     * 
     * @param tipoTalla -  objeto talla que se va a actualizar
     * 
     * @return - id de la talla almacenada
     */
    @Override
    public Integer actualizarTalla(TipoTalla tipoTalla) throws Exception {
        
        Optional<TipoTalla> tallaEncontrada=tallaRepository.findByTalla(tipoTalla.getTalla());

        if(tallaEncontrada.isEmpty()){
            throw new Exception("no se puede actualizar la talla");
        }else{
            return tallaRepository.save(tipoTalla).getId();
        }
    }



    /*
     * eliminar talla por medio del nombre de la talla. si no se encuentra se lanza una excepcion
     * 
     * @param talla - nombre de la talla
     * 
     */
    @Override
    public void eliminarTalla(String talla) throws Exception {
        
        Optional<TipoTalla> tallaEncontrada=tallaRepository.findByTalla(talla);

        if(tallaEncontrada.isEmpty()){
            throw new Exception("no se puede eliminar la talla");
        }else{
            tallaRepository.delete(tallaEncontrada.get());
        }
    }



    /*
     * buscar una talla por medio del nombre que es unico. si no se encuentra se lanza una excepcion
     * 
     * @param talla - nombre de la talla
     * 
     * @return - objeto talla encontrado
     */
    @Override
    public TipoTalla obtenerTalla(String talla) throws Exception {
        
        Optional<TipoTalla> tallaEncontrada=tallaRepository.findByTalla(talla);

        if(tallaEncontrada.isEmpty()){
            throw new Exception("no se puede encontrar la talla");
        }else{
            return tallaEncontrada.get();
        }
    }


    /*
     * devuelve la lista de todas las tallas
     */
    @Override
    public List<TipoTalla> listarTallas() throws Exception {
        return tallaRepository.findAll();
    }

}
