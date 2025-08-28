package com.caciquesport.inventario.inventario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 4. Buscar por fecha entre dos fechas
    @Query("SELECT g FROM Gasto g WHERE g.fecha >= :inicio AND g.fecha < :fin")
    List<Gasto> findByFechaBetweenDates(@Param("inicio") Date inicio, @Param("fin") Date fin);

}
