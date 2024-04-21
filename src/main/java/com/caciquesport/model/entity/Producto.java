package com.caciquesport.model.entity;

import com.caciquesport.model.configTypes.TipoHorario;
import com.caciquesport.model.configTypes.TipoPrenda;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="producto")
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ip_producto")
    private int idProducto;
    
    @ManyToOne
    private TipoPrenda tipoPrenda;

    @ManyToOne
    private TipoHorario tipoHorario;


}
