package com.caciquesport.inventario.inventario.service.implementations;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.service.interfaces.NotificacionFacturaService;

/*
 * Implementacion de servicio que se encarga de realizar notificaciones de las facturas 
 */
@Service
public class NotificacionFacturaImpl implements NotificacionFacturaService{

    /**
     * Notificar factura 
     * 
     * @param factura - factura la cual sera notificada
     */
    @Override
    public void notificarFactura(Factura factura) throws Exception {
        
    }

}
