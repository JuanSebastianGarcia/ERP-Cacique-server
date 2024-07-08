package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.MetodoPago;
import com.caciquesport.inventario.inventario.model.estados.EstadoSoportePago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un soporte de pago
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "soporte_pago")
public class SoportePago {

    // Identificador único del soporte de pago
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte")
    private int id;

    // Estado del soporte de pago
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_soporte", updatable = true, nullable = false)
    private EstadoSoportePago estadoSoporte;

    //valor total de la factura
    @Column(name="valor_total_factura",updatable = true,nullable = false)
    private double valorTotalFactura;

    // Valor total pagado en el soporte de pago
    @Column(name = "valor_total_pagado", updatable = true, nullable = false)
    private double valorTotalPagado=0;

    // Lista de pagos asociados a este soporte de pago
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "soporte_pago_id",updatable = true) 
    private List<Pago> listaPagos = new ArrayList<>();











    /**
     * Este metodo se encarga de identificar si se necesita un pago y lo agrega a la lista de pagos.
     * 
     * @param pago  - valor del pago realizado
     * @param metodoPago -  metodo del pago (efectivo, transferencia)
     * @throws Exception - en caso de que no hagan falta mas pagos, saltara una excepcion
     */
    public void agregarPago(Double pago, String metodoPago) throws Exception {
        
        if(!(valorTotalPagado+pago<=valorTotalFactura)){
            throw new Exception("no se puede realizar mas pagos");
        }

        instanciarAgregarPago(pago,metodoPago);
    }



    /**
     *Este metodo se encarga de instanciar el pago, agregarlo a la lista y llamar a la identificacion del estado del soporte de pago
     *el estado del soporte de pago indica si un pago esta completo o incompleto
     *
     * @param pago  - valor del pago realizado
     * @param metodoPago -  metodo del pago (efectivo, transferencia)      
     */
    private void instanciarAgregarPago(Double pago, String metodoPago) {
        
        Pago nuevoPago = new Pago();

        nuevoPago.setFechaPago(LocalDate.now());
        nuevoPago.setMetodoPago(MetodoPago.valueOf(metodoPago.toUpperCase()));
        nuevoPago.setValorPago(pago);

        listaPagos.add(nuevoPago);

        identificarEstado();
    }


    /**
     *Este metodo se encarga de validar si el soporte dde pago esta con un pago completo o un pago incompleto.
     *la suma del valor de los pagos debe de ser igual al valor total de la factura para estar en un estado("PAGO_COMPLETO")  
     *de lo contrario estara en estado ("PAGO_INCOMPLETO")
     *
     */
    private void identificarEstado() {
        
        double sumaPagos=0;

        for (Pago pago : listaPagos) {
            sumaPagos+=pago.getValorPago();
        }

        if(sumaPagos==valorTotalFactura){
            this.estadoSoporte=EstadoSoportePago.PAGO_COMPLETO;
        }else{
            this.estadoSoporte=EstadoSoportePago.PAGO_INCOMPLETO;
        }
    }

}
