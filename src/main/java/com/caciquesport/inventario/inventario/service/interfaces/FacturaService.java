package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoPendienteDto;

/**
 * Interfaz para definir los servicios de gestión de Factura.
 * Proporciona métodos para operaciones CRUD básicas en factura.
 */
public interface FacturaService {

    /**
     * Generar una factura por primera vez. usando los datos ingresados por parametro realiza las validaciones 
     * para generar una factura por primera vez, registra el cliente, los productos y el pago, y devuelve un
     * mensaje de confirmacion
     * 
     * @param facturaDto - representa los datos necesarios para generar una factura
     * 
     * @return String - se debe de retornar un mensaje de confirmacion
     **/
    String generarFactura(FacturaDto facturaDto)throws Exception;


    /**
     * Actualiza una factura existente utilizando los datos proporcionados.
     * Realiza las validaciones necesarias sobre los productos y el estado de pago,
     * aplica los cambios correspondientes y devuelve un mensaje de confirmación.
     *
     * @param facturaDto Objeto con los datos actualizados de la factura.
     * @param idFactura ID de la factura que se desea modificar.
     * @return Mensaje de confirmación indicando el resultado de la operación.
     * @throws Exception Si ocurre un error durante la validación o el proceso de actualización.
     */
    String guardarCambios(FacturaDto facturaDto, Integer idFactura) throws Exception;



    /**
     * buscar una factura por medio de la cedula del cliente o el id de la factura. este debe de retornar la factura encontrada.
     * en caso de buscarla por la cedula del cliente, debe de retornar una lista de facturas
     * 
     * @param codigo - este codigo puede representar un id de la factura o la cedula de un cliente
     * 
     * @return FacturaDto - al encontrar una factura se debe de retornar la factura encontrada
     **/
    List<FacturaDto> buscarFacturaDto(String codigo, String tipoCodigo)throws Exception;



    /*
     * Consultar una lista de productos que estan en estado pendiente
     * 
     * @return ProductoFacturaDto - contiene la informacion de cada producto pendiente ademas de datos como la fecha 
     * de generacion y el id de su factura
     */
    List<ProductoPendienteDto> consultarProductosPendientes()throws Exception;
}
