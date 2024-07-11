package com.caciquesport.inventario.inventario.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;
import com.caciquesport.inventario.inventario.repository.ClienteRepository;
import com.caciquesport.inventario.inventario.repository.FacturaRepository;
import com.caciquesport.inventario.inventario.service.interfaces.ClienteServicio;
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
     * Este metodo se encarga de consultar una lista de facturas por medio del codigo de la factura o 
     * la cedula del cliente a la cual la factura este asociada
     */
    @Override
    public List<FacturaDto> buscarFacturaDto(String codigo, String tipoCodigo) throws Exception {
        
        List<Factura> listaFacturas=new ArrayList<>();


        if(tipoCodigo.equals("cedula")){
            listaFacturas=buscarFacturaPorCliente(codigo);
        }

        if(tipoCodigo.equals("codigo")){
            listaFacturas=buscarFacturaPorCodigo(codigo);
        }

        return convertirFacturaDto(listaFacturas);
    }




    /**
     * Metodo encargado de convertir una lista de objetos facturas en una lista Dto
     * 
     * @param listaFacturas - lista de objetos de factura
     * @return listaFacturaDto - lista de facturas en formato dto
     */
    private List<FacturaDto> convertirFacturaDto(List<Factura> listaFacturas) {



        return null;
    }






    /**
     * Metodo encargado de buscar una factura por medio de su codigo unico 
     * 
     * @param codigo - codigo unico de la factura
     * @return factura - instancia de la factura encontrada
     * @throws Exception - si no se encuentra una factura se lanza una excepcion
     */
    private List<Factura> buscarFacturaPorCodigo(String codigo) throws Exception {

        int id=Integer.parseInt(codigo);
        List<Factura> listaFacturas = new ArrayList<>();

        Optional<Factura> factura = facturaRepository.findById(id);//buscar factura por codigo

        if(factura.isEmpty()){//no existe ninguna factura
            throw new Exception("la factura por el codigo proporcionado no existe");
        }

        listaFacturas.add(factura.get());
        
        return listaFacturas;
    }






    /**
     * Metodo encargado de buscar una lista de facturas por medio de la cedula del cliente
     * @param codigo - cedula del cliente
     * @return - lista de facturas encontradas pertenecientes al cliente
     * @throws Exception - en caso de que el cliente no exista o no se encuentre ninguna factura
     */
    private List<Factura> buscarFacturaPorCliente(String codigo) throws Exception {
        

        Optional<Cliente> cliente = clienteRepository.findById(codigo);//buscar el cliente

        if(cliente.isEmpty()){//el cliente no existe
            throw new Exception("el cliente no existe");
        }
        
        List<Factura> facturas = facturaRepository.findByCliente(cliente.get());//buscar facturas por el cliente

        if(facturas.isEmpty()){//no existe ninguna factura
            throw new Exception("no hay facturas por el cliente");
        }

        return facturas;
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

        validarEntregaProductos(facturaDto);
    }



    /**
     * Se encarga de validar que se entregen los productos correspondientes al cliente. los productos correspondientes 
     * son aquellos en los que el valor de los productos entregados no superan el valor pagado.
     * 
     * @param facturaDto - contiene los datos necesaios para generar una factura
     * @throws Exception 
     */
    private void validarEntregaProductos(FacturaDto facturaDto) throws Exception {
        
        double sumaProductosEntregados=0;

        for (ProductoFacturaDto producto : facturaDto.listaProductos()) {
            
            if(producto.estado().equals("ENTREGADO")){
                sumaProductosEntregados+=producto.precio();
            }
        }

        if(sumaProductosEntregados>facturaDto.pago()){
            throw new Exception("no se pueden entregar productos que superen el pago");
        }

    }












    /**
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
