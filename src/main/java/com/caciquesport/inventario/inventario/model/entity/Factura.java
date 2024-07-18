package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.estados.EstadoFactura;
import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;
import com.caciquesport.inventario.inventario.model.estados.EstadoSoportePago;

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
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoFactura> listaProductosFactura = new ArrayList<>();


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
        
        if(soportePago==null){

            SoportePago soportePago = new SoportePago();
            this.soportePago=soportePago;

        }

    }



    /**
     * Este metodo calcula el valor total de la factura y agrega el valor de la misma al soporte de pago
     * 
     * @param list - representa la lista de productos que pertenecen a la factura
     */
    public void calcularValorFactura(List<ProductoFacturaDto> list){

        double sumaTotal=0;

        for (ProductoFacturaDto producto : list) {
            sumaTotal+=producto.precio();
        }

        this.soportePago.setValorTotalFactura(sumaTotal);
    }



    /**
     * Este metodo se encarga de recibir un pago y agregarlo al soporte de pago
     * 
     * @param facturaDto - contiene la informacion necesaria
     * @throws Exception 
     */
    public void agregarPago(FacturaDto facturaDto) throws Exception {
        this.soportePago.agregarPago(facturaDto.pago(),facturaDto.metodoPago());
    }


    /*
     * Este metodo revisa el estado del soporte de pago, el estado de los productos para identificar cual es el estado de la factura.
     * si hay algun producto empacado o pendiente ("factura pendiente")
     * si el soporte de pago esta incompleto ("factura pendiente")
     * si todos los productos estan entregados y el pago completo ("factura finalizada")
     */
    public void identificarEstado() {

        boolean productoPedienteEmpacado=hallarProductoPendiente();

        boolean pagoIncompleto=solicitarEstadoSoporte();

        System.out.print("los productos estan :" + productoPedienteEmpacado +" el pago esta :"+pagoIncompleto);

        if(productoPedienteEmpacado==true || pagoIncompleto==true){
            this.estadoFactura=EstadoFactura.PENDIENTE;
        }else{
            this.estadoFactura=EstadoFactura.FINALIZADA;
        }

    }


    /**
     * Este metodo se encarga de solicitar el estado del soporte de pago
     * 
     * @return true - el pago esta incompleto 
     * @return false - el pago esta completo
     */
    private boolean solicitarEstadoSoporte() {
       
        boolean respuesta=false;
        EstadoSoportePago estadoSoportePago = this.soportePago.getEstadoSoporte();

        if(estadoSoportePago.equals(EstadoSoportePago.PAGO_INCOMPLETO)){
            respuesta=true;
        }

        return respuesta;
    }


    /**
     * Este metodo revisa la lista de productos en busca de productos que puedan estar en estado pendiente o empado
     * 
     * @return true - existen productos pendientes y/o empacados
     * @return false - todos los productos estan entregados
     */
    private boolean hallarProductoPendiente() {
       
        boolean respuesta=false;

        for (ProductoFactura productoFactura : this.listaProductosFactura) {
            
            if(productoFactura.getEstadoProducto()!=EstadoProducto.ENTREGADO){
                respuesta=true;
                break;
            }

        }
        return respuesta;
    }

    /*
     * Metodo que se encarga de retirar un producto de la factura
     */
    public void removeProductoFactura(ProductoFactura productoFactura) {
        int index=this.listaProductosFactura.indexOf(productoFactura);
        productoFactura.setFactura(null);
        this.listaProductosFactura.remove(index);
    }


    /**
     * Este metodo se encarga de actualizar el estado de un producto de la factura
     * 
     * @param productoExistente - instancia del producto a actualizar
     * @param estado - nuevo estado del producto 
     */
    public void actualizarEstadoProducto(ProductoFactura productoExistente, String estado) {
    
        int index=this.listaProductosFactura.indexOf(productoExistente);

        this.listaProductosFactura.get(index).setEstadoProducto(EstadoProducto.valueOf(estado));

    }





}
