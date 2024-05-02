package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import com.caciquesport.inventario.inventario.repository.TipoPrendaRepostirory;
import com.caciquesport.inventario.inventario.service.interfaces.TipoPrendaServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoPrendaServicioImpl implements TipoPrendaServicio{

    private final TipoPrendaRepostirory tipoPrendaRepostirory;


    /*
     * crear un tipoPrenda.
     * 
     * @param nuevoTipoPrenda - objeto tipoPrenda que se va a almacenar
     */
    @Override
    public Integer crearPrenda(TipoPrenda nuevoTipoPrenda) throws Exception {
        return tipoPrendaRepostirory.save(nuevoTipoPrenda).getId();
    }


    /*
     * actualizar tipoPrenda. se busca por medio del nombre de la prenda que es unico. si no se encuentra 
     * se lanza una excepcion
     * 
     * @param tipoPrenda - el tipo prenda que se va a actualizar
     * 
     * @return - id de la prenda actualizada
     */
    @Override
    public Integer actualizarPrenda(TipoPrenda tipoPrenda) throws Exception {
        
        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(tipoPrenda.getPrenda());

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            return tipoPrendaRepostirory.save(tipoPrenda).getId();
        }
        
    }


    /*
     * eliminarTipoPrenda. se busca por medio del nombre de la prenda que es unico. si no se encuentra 
     * se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     */
    @Override
    public void eliminarPrenda(String prenda) throws Exception {
                
        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(prenda);

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            tipoPrendaRepostirory.delete(tipoPrendaEncontrado.get());
        }
    }


    /*
     * obtener tipoPrenda. se busca por medio del nombre de la prenda que es unico. si no se encuentra se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     * 
     * @return - objeto tipoPreda encontrado
     */
    @Override
    public TipoPrenda obtenerPrenda(String prenda) throws Exception {

        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(prenda);

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            return tipoPrendaEncontrado.get();
        }
    }


    /*
     * buscar la lista de tipoPrendas
     * 
     * @return - lista de prendas
     */
    @Override
    public List<TipoPrenda> listarPrendas() throws Exception {
        return tipoPrendaRepostirory.findAll();
    }
}
