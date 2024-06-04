package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
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
    public void crearHorario(String nuevoTipoHorario) throws Exception {
        try{
            TipoHorario tipoHorario =new TipoHorario();
            tipoHorario.setHorario(nuevoTipoHorario);
            tipoHorarioRepository.save(tipoHorario).getId();
        }catch(Exception e){
            throw new Exception("el horario esta repetido");
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
     * @return - lista de horarios en 
     */
    @Override
    public List<ConfigTypesDto> listarHorarios() throws Exception {

        List<TipoHorario> listaHorarios = tipoHorarioRepository.findAll();
        List<ConfigTypesDto> listaDto = new ArrayList<>();

        for (TipoHorario tipoHorario : listaHorarios) {
            listaDto.add(new ConfigTypesDto(tipoHorario.getId(),tipoHorario.getHorario()));
        }

        return listaDto;       
    }



}
