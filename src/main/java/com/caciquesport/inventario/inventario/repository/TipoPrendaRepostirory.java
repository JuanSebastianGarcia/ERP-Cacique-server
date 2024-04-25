package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

@Repository
public interface TipoPrendaRepostirory extends JpaRepository<TipoPrenda,Integer>{

    Optional<TipoPrenda> findByPrenda(String prenda);
}
