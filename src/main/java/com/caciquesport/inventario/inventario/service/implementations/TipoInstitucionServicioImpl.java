package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;
import com.caciquesport.inventario.inventario.repository.TipoInstitucionRepository;
import com.caciquesport.inventario.inventario.service.interfaces.TipoIstitucionServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoInstitucionServicioImpl implements TipoIstitucionServicio{

    private final TipoInstitucionRepository institucionRepository;


    /*
     * crear tipo institucion
     * 
     * @param nuevoTipoInstitucion - objeto institucion que se va a almcenar
     * 
     * @return - id de la institucion almacenada
     */
    @Override
    public void  crearInstitucion(String  nuevaInstitucion) throws Exception {
        try{
        TipoInstitucion tipoInstitucion = new TipoInstitucion();
        tipoInstitucion.setInstitucion(nuevaInstitucion);
        institucionRepository.save(tipoInstitucion);
        } 
        catch(Exception e){ 
            throw new Exception("la insticion esta repetida");
        }

    }



    

    /*
     * eliminar una institucion por medio del nombre que es unico, si no se encuentra se lanza una excepcion
     * 
     * @param nombrInstitucion - nombre de la institucion
     * 
     */
    @Override
    public void eliminarInstitucion(String institucion) throws Exception {
        
        Optional<TipoInstitucion> institucionEncontrada=institucionRepository.findByInstitucion(institucion);

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede eliminar la institucion");
        }else{
            institucionRepository.delete(institucionEncontrada.get());
        }
    }


    /*
     * obtener una institucion por medio del nombre que es unico, si no se encuentra se lanza una excepcion
     * 
     * @param institucion - nombre de la institucion 
     * 
     * @return - institucion encontrada
     */
    @Override
    public TipoInstitucion obtenerInstitucion(String institucion) throws Exception {
      
        Optional<TipoInstitucion> institucionEncontrada=institucionRepository.findByInstitucion(institucion);

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede encontras la institucion");
        }else{
            return institucionEncontrada.get();
        }
    }


    /*
     * obtener la lista de instituciones
     * 
     * @return - lista de instituciones
     */
    @Override
    public List<ConfigTypesDto> listarInstituciones() throws Exception {

        List<TipoInstitucion> listaInstituciones = institucionRepository.findAll();
        List<ConfigTypesDto> listaDto = new ArrayList<>();

        for (TipoInstitucion institucion : listaInstituciones) {
            listaDto.add(new ConfigTypesDto(institucion.getInstitucion()));
        }
        return listaDto;
    }

}
