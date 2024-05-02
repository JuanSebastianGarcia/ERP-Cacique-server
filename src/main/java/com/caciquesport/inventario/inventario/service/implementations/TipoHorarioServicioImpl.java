package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;
import com.caciquesport.inventario.inventario.repository.TipoHorarioRepository;
import com.caciquesport.inventario.inventario.service.interfaces.TipoHorarioServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoHorarioServicioImpl implements TipoHorarioServicio{


    private final TipoHorarioRepository tipoHorarioRepository;


    /*
     * crear tipo horario
     * 
     * @param nuevoTipoHorario - objeto tipo horario que se va a almacenar
     */
    @Override
    public Integer crearHorario(TipoHorario nuevoTipoHorario) throws Exception {
        return tipoHorarioRepository.save(nuevoTipoHorario).getId();
    }


    /*
     * actualizar tipo horario. se busca por medio del nombre que es unico, y si no se encuentra
     * se lanza una excepcion
     * 
     * @param tipoHorario -  el tipo horario que se va a actualizar
     * 
     * @return - id del tipo horario actualizado
     */
    @Override
    public Integer actualizarHorario(TipoHorario tipoHorario) throws Exception {
        Optional<TipoHorario> tipoHorarioEncontrado=tipoHorarioRepository.findByHorario(tipoHorario.getHorario());

        if(tipoHorarioEncontrado.isEmpty()){
            throw new Exception("no se puede actualizar el tipo horario");
        }else{
            return tipoHorarioRepository.save(tipoHorario).getId();
        }

    }



    /*
     * eliminar tipo horario. se busca por medio del nombre que es unico, y si no se encuentra
     * se lanza una excepcion
     * 
     * @param horario - horario por el que se busca el objeto
     */
    @Override
    public void eliminarHorario(String horario) throws Exception {

        Optional<TipoHorario> tipoHorarioEncontrado=tipoHorarioRepository.findByHorario(horario);

        if(tipoHorarioEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar el horario");
        }else{
            tipoHorarioRepository.delete(tipoHorarioEncontrado.get());
        }
    }



    /*
     * obtener tipo horario. se busca por medio del nombre que es unico y si no se encuentra 
     * se lanza una excepcion
     * 
     * @param horario - horario por el que se busca el objeto
     */
    @Override
    public TipoHorario obtenerHorario(String horario) throws Exception {
 
        Optional<TipoHorario> tipoHorarioEncontrado= tipoHorarioRepository.findByHorario(horario);

        if(tipoHorarioEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar el horario");
        }else{
            return tipoHorarioEncontrado.get();
        }
        
    }


    /*
     * obtener la lista de tipos horario
     * 
     * @return - lista de horarios
     */
    @Override
    public List<TipoHorario> listarHorarios() throws Exception {
        return tipoHorarioRepository.findAll();       
    }



}
