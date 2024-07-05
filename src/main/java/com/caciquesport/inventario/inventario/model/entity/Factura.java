package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.caciquesport.inventario.inventario.model.estados.EstadoFactura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una factura de venta
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "factura")
public class Factura {

    //Identificador único de la factura
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;


    //Fecha en la que se generó la factura
    @Column(name = "fecha_factura", nullable = false, updatable = false)
    private LocalDate fechaFactura;


    //Estado actual de la factura
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_factura", nullable = false, updatable = true)
    private EstadoFactura estadoFactura;


    //Cliente asociado a la factura
    @ManyToOne
    @JoinColumn(name = "cliente_factura", nullable = false ,  updatable = false)
    private Cliente cliente;


    //Soporte de pago asociado a la factura
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "soporte_pago_factura" , nullable = false , updatable = true)
    private SoportePago soportePago;


    //Lista de productos asociados a la factura a través de la entidad intermedia ProductoFactura
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "factura_id")
    private ArrayList<ProductoFactura> listaProductosFactura;


    /*
     * Este metodo iniciliza la fecha de la factura en el momento en que el metodo es invocado.
     * La fecha para la generacion de la factura debe ser protegida por lo que solamente 1 vez es asignada
     */
    public void inicializarFecha() {
        this.fechaFactura= LocalDate.now();
    }


    /*
     * Este metodo instancia un soporte de pago y lo agrega a la factura
     */
    public void generarSoportePago() {
        
        SoportePago soportePago = new SoportePago();

        this.soportePago=soportePago;
    }



    /*
     * Este metodo calcula el valor total de la factura
     */
    public void calcularValorFactura(List<ProductoFactura> listaProductos){

        
    }
}
