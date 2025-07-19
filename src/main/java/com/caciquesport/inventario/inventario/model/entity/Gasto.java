package com.caciquesport.inventario.inventario.model.entity;

import java.util.Date;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Integer id;

    @Column(name = "valor_gasto")
    private double valor;

    @ManyToOne
    @JoinColumn(name = "id_tipo_gasto")
    private TipoGasto tipoGasto;

    @Column(name = "descripcion_gasto")
    private String descripcion;

    @Column(name = "fecha_gasto")
    private Date fecha;

}
