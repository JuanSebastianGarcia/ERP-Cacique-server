package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
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
    public Integer crearInstitucion(TipoInstitucion nuevoTipoInstitucion) throws Exception {
        return institucionRepository.save(nuevoTipoInstitucion).getId();
    }



    /*
     * actualizar institucion. se busca por medio del nombre que es unico. si no se encuentra
     * se lanza una exception
     * 
     * @param institucion - el objeto institucion que se va a actualizar
     * 
     * @return - id de la institucion actualizada
     */
    @Override
    public Integer actualizarInstitucion(TipoInstitucion tipoInstitucion) throws Exception {
        
        Optional<TipoInstitucion> institucionEncontrada=institucionRepository.findByInstitucion(tipoInstitucion.getInstitucion());

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede actualizar la institucion");
        }else{
            return institucionRepository.save(tipoInstitucion).getId();
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
    public List<TipoInstitucion> listarInstituciones() throws Exception {
        return institucionRepository.findAll();
    }

}
