package com.caciquesport.model.entity;

import com.caciquesport.model.configTypes.Institucion;
import com.caciquesport.model.configTypes.Talla;
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
    private int id;
    
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
    
    @OneToOne
    @JoinColumn(name = "detalle_producto")
    private DetalleProducto detalleProducto;
  
}
