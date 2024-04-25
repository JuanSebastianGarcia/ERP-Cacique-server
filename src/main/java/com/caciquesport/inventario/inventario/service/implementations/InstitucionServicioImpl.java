package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.caciquesport.inventario.inventario.model.configTypes.Institucion;
import com.caciquesport.inventario.inventario.repository.InstitucionRepository;
import com.caciquesport.inventario.inventario.service.Intferfaces.InstitucionServicio;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstitucionServicioImpl implements InstitucionServicio{

    private final InstitucionRepository institucionRepository;

    public InstitucionServicioImpl(InstitucionRepository institucionRepository){
        this.institucionRepository=institucionRepository;
    }

    /*
     * crear institucion
     * 
     * @param nuevaInstitucion - objeto institucion que se va a almcenar
     * 
     * @return - id de la institucion almacenada
     */
    @Override
    public Integer crearInstitucion(Institucion nuevaInstitucion) throws Exception {
        return institucionRepository.save(nuevaInstitucion).getId();
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
    public Integer actualizarInstitucion(Institucion institucion) throws Exception {
        
        Optional<Institucion> institucionEncontrada=institucionRepository.findByInstitucion(institucion.getInstitucion());

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede actualizar la institucion");
        }else{
            return institucionRepository.save(institucionEncontrada.get()).getId();
        }
    }

    

    /*
     * eliminar una institucion por medio del nombre que es unico, si no se encuentra se lanza una excepcion
     * 
     * @param nombrInstitucion - el nombre por el cual se localiza la institucion
     * 
     */
    @Override
    public void eliminarInstitucion(String nombreInstitucion) throws Exception {
        
        Optional<Institucion> institucionEncontrada=institucionRepository.findByInstitucion(nombreInstitucion);

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede eliminar la institucion");
        }else{
            institucionRepository.delete(institucionEncontrada.get());
        }
    }


    /*
     * obtener una institucion por medio del nombre que es unico, si no se encuentra se lanza una excepcion
     * 
     * @param nombrInstitucion - el nombre por el cual se localiza la institucion
     * 
     * @return - institucion encontrada
     */
    @Override
    public Institucion obtenerInstitucion(String nombreInstitucion) throws Exception {
      
        Optional<Institucion> institucionEncontrada=institucionRepository.findByInstitucion(nombreInstitucion);

        if(institucionEncontrada.isEmpty()){
            throw new Exception("no se puede encontras la institucion");
        }else{
            return institucionEncontrada.get();
        }
    }


    /*
     * obtener la lista de instituciones
     */
    @Override
    public List<Institucion> listarInstituciones() throws Exception {
        return institucionRepository.findAll();
    }

}
