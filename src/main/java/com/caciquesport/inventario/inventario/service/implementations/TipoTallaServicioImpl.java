package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
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
    public void  crearTalla(String nuevoTipoTalla) throws Exception {

        try {
            TipoTalla tipoTalla = new TipoTalla();
            tipoTalla.setTalla(nuevoTipoTalla);
    
            tallaRepository.save(tipoTalla).getId();
        } catch (Exception e) {
            throw new Exception("la talla esta repetida");
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
            throw new Exception("no se encuentra la talla");
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
    public List<ConfigTypesDto> listarTallas() throws Exception {

        List<TipoTalla> listaTallas = tallaRepository.findAll();
        List<ConfigTypesDto> listaDto = new ArrayList<>();

        for (TipoTalla tipoTalla : listaTallas) {
            listaDto.add(new ConfigTypesDto(tipoTalla.getId(),tipoTalla.getTalla()));
        }

        ordenarLista(listaDto);
        
        return listaDto;
    }




    /*
     * Ordenar una lista en orden con el metodo de burbuja
     * @param listaDto - arreglo de datos de configuracion del producto
     * @return arreglo ordenado de menor a mayor
     */
    private void ordenarLista(List<ConfigTypesDto> listaDto) {

        ConfigTypesDto configType=null;

        for(int i=0;i<listaDto.size();i++){
            for(int j=0;j<listaDto.size();j++){
                
                if(j<listaDto.size()-1){
                    if(listaDto.get(j).idTipo()>listaDto.get(j+1).idTipo()){
                        
                        configType=listaDto.get(j);
                        listaDto.set(j,listaDto.get(j+1));
                        listaDto.set(j+1,configType);

                    }
                }
            }   
        }
    }

}
