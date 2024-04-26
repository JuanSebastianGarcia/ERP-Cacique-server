package com.caciquesport.inventario.inventario.model.configTypes;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import com.caciquesport.inventario.inventario.repository.TipoGeneroRepository;

@DataJpaTest
public class TipoGeneroTest {

    @Autowired
    private TipoGeneroRepository tipoGeneroRepository;

    /*
     * crear genero. en este test se verifica que se puede crear y buscar un genero
     */
    @Test
    public void crearGenero(){

        TipoGenero tipoGenero=new TipoGenero();
        tipoGenero.setGenero("hombre");
        tipoGeneroRepository.save(tipoGenero);
        Optional<TipoGenero> generoEncontrado=tipoGeneroRepository.findByGenero("hombre");
        assertTrue(generoEncontrado.isPresent());

    }
}
