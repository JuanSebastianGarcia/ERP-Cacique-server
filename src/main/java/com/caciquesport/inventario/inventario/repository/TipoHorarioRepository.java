package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad TipoHorario. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface TipoHorarioRepository extends JpaRepository<TipoHorario,Integer>{

    Optional<TipoHorario> findByHorario(String horario);
}
