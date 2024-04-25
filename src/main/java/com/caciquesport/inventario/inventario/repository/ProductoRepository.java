package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{

    //Optional<Producto> findByTipoPrenda(String tipoPrenda);

}
