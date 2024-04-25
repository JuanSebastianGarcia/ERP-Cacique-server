package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.configTypes.Talla;
import com.caciquesport.inventario.inventario.repository.TallaRepository;
import com.caciquesport.inventario.inventario.service.Intferfaces.TallaServicio;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TallaServicioImpl implements TallaServicio{

    private final TallaRepository tallaRepository;

    public TallaServicioImpl(TallaRepository tallaRepository){
        this.tallaRepository=tallaRepository;
    }


    /*
     * crear una talla. 
     * 
     * @param nuevaTalla- objeto talla que se va a almacenar
     */
    @Override
    public Integer crearTalla(Talla nuevaTalla) throws Exception {
        return tallaRepository.save(nuevaTalla).getId();
    }



    /*
     * actualizar talla. se busca por medio del nombre. si no se encuentra lanza una excepcion
     * 
     * @param talla -  objeto talla que se va a actualizar
     * 
     * @return - id de la talla almacenada
     */
    @Override
    public Integer actualizarTalla(Talla talla) throws Exception {
        
        Optional<Talla> tallaEncontrada=tallaRepository.findByTalla(talla.getTalla());

        if(tallaEncontrada.isEmpty()){
            throw new Exception("no se puede actualizar la talla");
        }else{
            return tallaRepository.save(tallaEncontrada.get()).getId();
        }
    }



    /*
     * eliminar talla por medio del nombre de la talla. si no se encuentra se lanza una excepcion
     * 
     * @param talla - objeto talla que se va a eliminar
     */
    @Override
    public void eliminarTalla(String talla) throws Exception {
        
        Optional<Talla> tallaEncontrada=tallaRepository.findByTalla(talla);

        if(tallaEncontrada.isEmpty()){
            throw new Exception("no se puede eliminar la talla");
        }else{
            tallaRepository.delete(tallaEncontrada.get());
        }
    }



    /*
     * buscar una talla por medio del nombre que es unico. si no se encuentra se lanza una excepcion
     * 
     * @param talla - nombre por el cual se busca la talla
     * 
     * @return - objeto talla encontrado
     */
    @Override
    public Talla obtenerTalla(String talla) throws Exception {
        
        Optional<Talla> tallaEncontrada=tallaRepository.findByTalla(talla);

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
    public List<Talla> listarTallas() throws Exception {
        return tallaRepository.findAll();
    }

}
