package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import com.caciquesport.inventario.inventario.repository.TipoPrendaRepostirory;
import com.caciquesport.inventario.inventario.service.interfaces.TipoPrendaServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoPrendaServicioImpl implements TipoPrendaServicio {

    private final TipoPrendaRepostirory tipoPrendaRepostirory;

    /*
     * crear un tipoPrenda.
     * 
     * @param nuevoTipoPrenda - objeto tipoPrenda que se va a almacenar
     */
    @Override
    public void crearPrenda(String nuevaPrenda) throws Exception {
        try {

            TipoPrenda nuevoTipoPrenda = new TipoPrenda();
            nuevoTipoPrenda.setPrenda(nuevaPrenda);
            tipoPrendaRepostirory.save(nuevoTipoPrenda);

        } catch (Exception e) {
            throw new Exception("la prenda esta repetida");
        }

    }

    /*
     * eliminarTipoPrenda. se busca por medio del nombre de la prenda que es unico.
     * si no se encuentra
     * se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     */
    @Override
    public void eliminarPrenda(String prenda) throws Exception {

        Optional<TipoPrenda> tipoPrendaEncontrado = tipoPrendaRepostirory.findByPrenda(prenda);

        if (tipoPrendaEncontrado.isEmpty()) {
            throw new Exception("no se puede encontrar la prenda");
        } else {
            tipoPrendaRepostirory.delete(tipoPrendaEncontrado.get());
        }

    }

    /*
     * obtener tipoPrenda. se busca por medio del nombre de la prenda que es unico.
     * si no se encuentra se lanza una excepcion
     * 
     * @param prenda - nombre de la prenda
     * 
     * @return - objeto tipoPreda encontrado
     */
    @Override
    public TipoPrenda obtenerPrenda(String prenda) throws Exception {

        Optional<TipoPrenda> tipoPrendaEncontrado = tipoPrendaRepostirory.findByPrenda(prenda);

        if (tipoPrendaEncontrado.isEmpty()) {
            throw new Exception("no se puede encontrar la prenda");
        } else {
            return tipoPrendaEncontrado.get();
        }
    }


    /*
     * buscar la lista de tipoPrendas, convertirlas en formato dto y retornarlas
     * 
     * @return - lista de prendas
     */
    @Override
    public List<ConfigTypesDto> listarPrendas() throws Exception {

        List<TipoPrenda> lista = tipoPrendaRepostirory.findAll();

        List<ConfigTypesDto> listaDto = new ArrayList<>();

        for (TipoPrenda prenda : lista) {
            listaDto.add(new ConfigTypesDto(prenda.getId(),prenda.getPrenda()));
        }

        return listaDto;
    }
}
