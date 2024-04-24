package com.caciquesport.inventario.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.repository.DetalleProductoRepository;
import com.caciquesport.inventario.inventario.repository.InstitucionRepository;
import com.caciquesport.inventario.inventario.repository.ProductoRepository;
import com.caciquesport.inventario.inventario.repository.TallaRepository;
import com.caciquesport.inventario.inventario.repository.TipoHorarioRepository;
import com.caciquesport.inventario.inventario.repository.TipoPrendaRepostirory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductoTest {

    @Autowired
    private ProductoRepository productoRepository; 

    @Autowired
    private DetalleProductoRepository detalleProductoRepository;

    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private TallaRepository tallaRepository;

    @Autowired
    private TipoHorarioRepository tipoHorarioRepository;

    @Autowired
    private TipoPrendaRepostirory tipoPrendaRepostirory;

    @Test
    public void RegistrarProductoTest(){

        Producto producto=new Producto();
       
       // producto.setDetalleProducto();
        producto.setInstitucion(null);
        producto.setTalla(null);
        producto.setTipoHorario(null);
        producto.setTipoPrenda(null);
    }

    @Test
    public void buscarDetalleProductoTest(){

        detalleProductoRepository.findById(1);

    }



    }


