package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.repository.ClienteRepository;
import com.caciquesport.inventario.inventario.repository.FacturaRepository;
import com.caciquesport.inventario.inventario.service.interfaces.FacturaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Transactional
@RequiredArgsConstructor
@Service
public class FacturaServiceImpl implements FacturaService{


    /*
     * Repositorios 
     */
    private final ClienteRepository clienteRepository;
    private final ProductoFacturaServicioImpl productoFacturaServicioImpl;
    private final FacturaRepository facturaRepository;


    /**
     *Metodo que se encarga de la generacion de una factura. en este proceso se hacen las validaciones necesarias
     *para asegurarse que la factura no solo se genera correctamente si no que sus datos seran integros y coherentes.
     *despues de las validaciones se generan los datos necesarios para la factura y se almacena. para finalizar retorna un 
     *mensaje de confirmacion
     *
     * @param facturaDto - contiene los datos neccesaios para generar una factura
     * 
     * @return String - mensaje de confirmacion
     */
    @Override
    public String generarFactura(FacturaDto facturaDto) throws Exception {
        
        //validar datos
        validarDatos(facturaDto);

        //se genera una factura con fecha
        Factura facturaNueva= instanciarFactura();

        //agregar cliente a la factura
        agregarCliente(facturaNueva,facturaDto.cedulaCliente());


        //agregar lista de productos a la factura
        productoFacturaServicioImpl.generarListaProductos(facturaDto.listaProductos(), facturaNueva);


        //crear soporte de pago
        facturaNueva.generarSoportePago();


        //calcular valor de la factura
        facturaNueva.calcularValorFactura(facturaDto.listaProductos());


        //agregar pago  
        facturaNueva.agregarPago(facturaDto);


        //identificar estado de factura
        facturaNueva.identificarEstado();

        
        //almacenar factura
        facturaRepository.save(facturaNueva);

        /*
        *se debe de agregar el metodo que realiza el proceso de facturacion
        */

        return "la factura ha sido creada y su estado es :"+facturaNueva.getEstadoFactura();
    }












    /**
     * 
     */
    @Override
    public String guardarCambios(FacturaDto facturaDto) throws Exception {
    
        throw new UnsupportedOperationException("Unimplemented method 'guardarCambios'");
    }




    /**
     * 
     */
    @Override
    public List<FacturaDto> buscarFacturaDto(int codigo) throws Exception {
        
        throw new UnsupportedOperationException("Unimplemented method 'buscarFacturaDto'");
    }





    


    /**
     * Este metodo se encarga de llamar las validaciones necesarias para verificar que una factura puede ser generada. cada metodo 
     * invocado ejecuta una validacion especifica
     * 
     * @param facturaDto - contiene los datos necesaios para generar una factura
     * @throws Exception - error en alguna de las validaciones
     */
    private void validarDatos(FacturaDto facturaDto) throws Exception {
        
        validarDatosMinimos(facturaDto);
    }




    /*
     * Este metodo, se asegura que la factura tenga los datos minimos para poder funcionar. los datos minimos son
     * 1-una cedula para agregar el cliente
     * 2-al menos un producto en la factura
     * 3-un pago minimo mayor que cero
     * 
     * @param facturaDto - contiene los datos de una factura
     */
    private void validarDatosMinimos(FacturaDto facturaDto) throws Exception {

        //cedula del cliente inexistente
        if (facturaDto.cedulaCliente().equals("")) {
            throw new Exception("debe de haber una cedula para el cliente");
        }

        //cero productos
        if(facturaDto.listaProductos().isEmpty()){
            throw new Exception("debe de existir por lo menos un producto");
        }

        //sin ningun pago
        if(facturaDto.pago()<=0){
            throw new Exception("debe de haber un pago minimo para generar la factura");
        }
    }





    /*
     * Este metodo crea una instancia de la factura, le agrega una fecha
     */
    private Factura instanciarFactura(){

        Factura nuevaFactura = new Factura();

        nuevaFactura.inicializarFecha();

        return nuevaFactura;
    }




    
    /**
     * Este metodo se encarga de buscar un cliente, y al encontrarlo lo agregara a la factura32
     * 
     * @param facturaNueva - instancia de la nueva factura 
     * @param cedulaCliente -  codigo unico del cliente
     * @throws Exception 
     * 
     * @exception - el cliente no ha sido encontrado
     */
    private void agregarCliente(Factura facturaNueva, String cedulaCliente) throws Exception {
        
        Optional<Cliente> clienteEncontrado=clienteRepository.findById(cedulaCliente);

        if(clienteEncontrado.isEmpty()){
            throw new Exception("El cliente no ha sido encontrado");
        }

        facturaNueva.setCliente(clienteEncontrado.get());
    }



}
