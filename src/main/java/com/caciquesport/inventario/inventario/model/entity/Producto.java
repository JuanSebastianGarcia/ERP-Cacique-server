package com.caciquesport.inventario.inventario.model.entity;

import java.io.Serializable;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un producto en el inventario
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto implements Serializable {

    // _____________FIELDS__________________ //

    // Identificador único del producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    // Tipo de prenda asociada al producto
    @ManyToOne  
    @JoinColumn(name = "tipo_prenda")
    private TipoPrenda tipoPrenda;

    // Tipo de horario asociado al producto
    @ManyToOne
    @JoinColumn(name = "tipo_horario")
    private TipoHorario tipoHorario;

    // Tipo de talla asociada al producto
    @ManyToOne
    @JoinColumn(name = "tipo_talla")
    private TipoTalla tipoTalla;

    // Tipo de institución asociada al producto
    @ManyToOne
    @JoinColumn(name = "tipo_institucion")
    private TipoInstitucion tipoInstitucion;

    // Tipo de género asociado al producto
    @ManyToOne
    @JoinColumn(name = "tipo_genero")
    private TipoGenero tipoGenero;

    // Detalle adicional del producto
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_producto")
    private DetalleProducto detalleProducto;
}

