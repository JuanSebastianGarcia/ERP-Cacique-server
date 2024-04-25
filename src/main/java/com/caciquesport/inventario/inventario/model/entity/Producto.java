package com.caciquesport.inventario.inventario.model.entity;


import java.io.Serializable;

import com.caciquesport.inventario.inventario.model.configTypes.Institucion;
import com.caciquesport.inventario.inventario.model.configTypes.Talla;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="producto")
public class Producto implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Integer id;
    
    @ManyToOne  
    @JoinColumn(name = "tipo_prenda_id")
    private TipoPrenda tipoPrenda;

    @ManyToOne
    @JoinColumn(name = "tipo_horario_id")
    private TipoHorario tipoHorario;

    @ManyToOne
    @JoinColumn(name = "talla_id")
    private Talla talla;

    @ManyToOne
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_producto")
    private DetalleProducto detalleProducto;
  
}
