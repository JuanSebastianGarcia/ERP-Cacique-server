package com.caciquesport.inventario.inventario.service.interfaces;


import com.caciquesport.inventario.inventario.model.entity.Factura;

/*
 * Interfaz definida para realizar la notificacion de las facturas generadas en el sistema
 */
public interface NotificacionFacturaService {

    /**
     * Metodo encargado de notificar las facturas que fueron realizadas. esta notificacion podra implementarse de cualquier 
     * manera ya sea por medio de correo, mensaje u otros.
     * 
     * @param factura
     * @throws Exception
     */
    String notificarFactura(Factura factura);


}
