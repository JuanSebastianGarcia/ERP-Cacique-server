package com.caciquesport.inventario.inventario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;
import com.caciquesport.inventario.inventario.model.entity.Gasto;

@Repository
public interface GastoRepositorio extends JpaRepository<Gasto, Integer> {

    // 1. Buscar por fecha exacta
    List<Gasto> findByFecha(Date fecha);

    // 2. Buscar por tipo de gasto
    List<Gasto> findByTipoGasto(TipoGasto tipoGasto);

    // 3. Buscar por tipo de gasto y fecha exacta
    List<Gasto> findByTipoGastoAndFecha(TipoGasto tipoGasto, Date fecha);
}
