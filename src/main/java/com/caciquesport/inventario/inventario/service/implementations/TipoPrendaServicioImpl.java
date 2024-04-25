package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import com.caciquesport.inventario.inventario.repository.TipoPrendaRepostirory;
import com.caciquesport.inventario.inventario.service.Intferfaces.TipoPrendaServicio;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoPrendaServicioImpl implements TipoPrendaServicio{

    private final TipoPrendaRepostirory tipoPrendaRepostirory;

    public TipoPrendaServicioImpl(TipoPrendaRepostirory tipoPrendaRepostirory){
        this.tipoPrendaRepostirory=tipoPrendaRepostirory;
    }

    /*
     * crear un tipoPrenda.
     * 
     * @param nuevoTipoPrenda - objeto tipoPrenda que se va a almacenar
     */
    @Override
    public Integer crearTipoPrenda(TipoPrenda nuevoTipoHorario) throws Exception {
        return tipoPrendaRepostirory.save(nuevoTipoHorario).getId();
    }


    /*
     * actualizar tipoPrenda. se busca por medio de la prenda que es unico. si no se encuentra 
     * se lanza una excepcion
     */
    @Override
    public Integer actualizarTipoPrenda(TipoPrenda tipoPrenda) throws Exception {
        
        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(tipoPrenda.getPrenda());

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            return tipoPrendaRepostirory.save(tipoPrendaEncontrado.get()).getId();
        }
        
    }


    /*
     * eliminarTipoPrenda. se busca por medio de la prenda que es unico. si no se encuentra 
     * se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     */
    @Override
    public void eliminarTipoPrenda(String prenda) throws Exception {
                
        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(prenda);

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            tipoPrendaRepostirory.delete(tipoPrendaEncontrado.get());
        }
    }


    /*
     * obtener tipoPrenda. se busca por medio de la prenda que es unico. si no se encuentra se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     * 
     * @return - objeto tipoPreda encontrado
     */
    @Override
    public TipoPrenda obtenerTipoPrenda(String prenda) throws Exception {

        Optional<TipoPrenda> tipoPrendaEncontrado=tipoPrendaRepostirory.findByPrenda(prenda);

        if(tipoPrendaEncontrado.isEmpty()){
            throw new Exception("no se puede encontrar la prenda");
        }else{
            return tipoPrendaEncontrado.get();
        }
    }


    /*
     * buscar la lista de tipoPrendas
     */
    @Override
    public List<TipoPrenda> listarPrendas() throws Exception {
        return tipoPrendaRepostirory.findAll();
    }
}
