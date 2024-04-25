package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

@Repository
public interface TipoHorarioRepository extends JpaRepository<TipoHorario,Integer>{

    Optional<TipoHorario> findByHorario(String horario);
}
