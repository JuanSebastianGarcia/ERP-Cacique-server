package com.caciquesport.inventario.inventario.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FacturaDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.repository.ClienteRepository;
import com.caciquesport.inventario.inventario.repository.FacturaRepository;
import com.caciquesport.inventario.inventario.service.interfaces.FacturaService;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoPendienteDto;
import com.caciquesport.inventario.inventario.service.interfaces.excepcion;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FacturaServiceImpl implements FacturaService {

    /*
     * Repositorios
     */
    private final ClienteRepository clienteRepository;
    private final ProductoFacturaServicioImpl productoFacturaServicioImpl;
    private final FacturaRepository facturaRepository;

    /**
     * Metodo que se encarga de la generacion de una factura. en este proceso se
     * hacen las validaciones necesarias
     * para asegurarse que la factura no solo se genera correctamente si no que sus
     * datos seran integros y coherentes.
     * despues de las validaciones se generan los datos necesarios para la factura y
     * se almacena. para finalizar retorna un
     * mensaje de confirmacion
     *
     * @param facturaDto - contiene los datos neccesaios para generar una factura
     * 
     * @return String - mensaje de confirmacion
     */
    @Override
    public String generarFactura(FacturaDto facturaDto) throws Exception {

        // validar datos
        validarDatos(facturaDto);

        // se genera una factura con fecha
        Factura facturaNueva = instanciarFactura();

        // agregar cliente a la factura
        agregarCliente(facturaNueva, facturaDto.cedulaCliente());

        // agregar lista de productos a la factura
        productoFacturaServicioImpl.generarListaProductos(facturaDto.listaProductos(), facturaNueva);

        // crear soporte de pago
        facturaNueva.generarSoportePago();

        // calcular valor de la factura
        facturaNueva.calcularValorFactura(facturaDto.listaProductos());

        // agregar pago
        facturaNueva.agregarPago(facturaDto);

        // identificar estado de factura
        facturaNueva.identificarEstado();

        // validar los productos entregados
        productoFacturaServicioImpl.validarProductosEntregados(facturaNueva.getSoportePago().getValorTotalPagado(),facturaNueva.getListaProductosFactura());

        // almacenar factura
        facturaRepository.save(facturaNueva);

        return "la factura ha sido creada y su estado es :" + facturaNueva.getEstadoFactura();
    }



    /**
     * Este metodo comienza el proceso para generar cambios en una factura, se puede:
     *  1.eliminar productos los cuales esten en estado pendiente.
     *  2.agregar pagos
     * despues de cada cambio el estado de la factura es
     * identificado y retornado
     * 
     * @param facturaDto - datos para actualizar la factura
     *
     * @return respuesta del estado de la factura
     * 
     */
    @Override
    public String guardarCambios(FacturaDto facturaDto) throws Exception {

        Factura facturaAnterior = obtenerFactura(facturaDto.idFactura());

        validarCambiosEnFactura(facturaDto, facturaAnterior);// validar cambios

        // agregar lista de productos a la factura
        productoFacturaServicioImpl.actualizarListaProductos(facturaDto.listaProductos(), facturaAnterior);

        //identificar nuevo valor de la factura
        facturaAnterior.calcularValorFactura(facturaDto.listaProductos());

        // agregar pago
        facturaAnterior.agregarPago(facturaDto);

        // identificar estado de factura
        facturaAnterior.identificarEstado();

        // validar los productos entregados
        productoFacturaServicioImpl.validarProductosEntregados(facturaAnterior.getSoportePago().getValorTotalPagado(),
                facturaAnterior.getListaProductosFactura());


        // almacenar factura
        facturaRepository.save(facturaAnterior);


        return "La factura ha sido actualizada y el estado es:" + facturaAnterior.getEstadoFactura();
    }


    @Override
    public List<ProductoPendienteDto> consultarProductosPendientes() throws excepcion {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarProductosPendientes'");
    }

    /**
     * Este metodo se encarga de relaizar las validaciones que son necesarias para
     * poder gestionar cambios en una
     * factura. estas validaciones son distintas a ñas validaciones para generar una factura
     * 
     * @param facturaDto - nuevos datos para la factura
     * @throws Exception
     */
    private void validarCambiosEnFactura(FacturaDto facturaDto, Factura facturaAnterior) throws Exception {

        // verificar que los productos entregados se mantienen asi
        productoFacturaServicioImpl.ValidarListaProductos(facturaAnterior.getListaProductosFactura(),
                facturaDto.listaProductos());

    }

    /**
     * Este metodo busca una factura por su id y retorna la instancia
     * 
     * @param idFactura - codigo unico de la factura
     * 
     * @return instancia de la factura
     * @throws Exception
     * 
     * @exception - factura no fue encontrada
     */
    private Factura obtenerFactura(int idFactura) throws Exception {

        Optional<Factura> facturaEncontrada = facturaRepository.findById(idFactura);

        if (facturaEncontrada.isEmpty()) {
            throw new Exception("la factura por el id no fue encontrada");
        }

        return facturaEncontrada.get();
    }

    /**
     * Este metodo se encarga de consultar una lista de facturas por medio del
     * codigo de la factura o
     * la cedula del cliente a la cual la factura este asociada
     * 
     * @param codigo     - codigo unico, puede ser del cliente o de la factura
     * @param tipoCodigo - especifica si el codigo es del cliente o de la factura
     * 
     * @return lista de facturas en formato dto
     */
    @Override
    public List<FacturaDto> buscarFacturaDto(String codigo, String tipoCodigo) throws Exception {

        List<Factura> listaFacturas = new ArrayList<>();

        if (tipoCodigo.equals("cedula")) {
            listaFacturas = buscarFacturaPorCliente(codigo);
        }

        if (tipoCodigo.equals("codigo")) {
            listaFacturas = buscarFacturaPorCodigo(codigo);
        }

        return convertirFacturaDto(listaFacturas);
    }

    /**
     * Metodo encargado de convertir una lista de  facturas en una lista DtoFactura pata retornar los datos al cliente
     * 
     * @param listaFacturas - lista de objetos de factura
     * @return listaFacturaDto - lista de facturas en formato dto
     */
    private List<FacturaDto> convertirFacturaDto(List<Factura> listaFacturas) {

        List<FacturaDto> listaFacturasDto = new ArrayList<>();

        for (Factura factura : listaFacturas) {

            List<ProductoFacturaDto> produtosFacturaDtos = productoFacturaServicioImpl
                    .convertirListaProductosDto(factura.getListaProductosFactura());

            listaFacturasDto.add(new FacturaDto(factura.getIdFactura(),factura.getEstadoFactura().toString(), factura.getCliente().getCedula(),
                    produtosFacturaDtos, "NA", factura.getSoportePago().getValorTotalPagado()));
        }

        return listaFacturasDto;
    }

    /**
     * Metodo encargado de buscar una factura por medio de su codigo unico
     * 
     * @param codigo - codigo unico de la factura
     * @return factura - instancia de la factura encontrada
     * @throws Exception - si no se encuentra una factura se lanza una excepcion
     */
    private List<Factura> buscarFacturaPorCodigo(String codigo) throws Exception {

        int id = Integer.parseInt(codigo);
        List<Factura> listaFacturas = new ArrayList<>();

        Optional<Factura> factura = facturaRepository.findById(id);// buscar factura por codigo

        if (factura.isEmpty()) {// no existe ninguna factura
            throw new Exception("la factura por el codigo proporcionado no existe");
        }

        listaFacturas.add(factura.get());

        return listaFacturas;
    }

    /**
     * Metodo encargado de buscar una lista de facturas por medio de la cedula del
     * cliente
     * 
     * @param codigo - cedula del cliente
     * @return - lista de facturas encontradas pertenecientes al cliente
     * @throws Exception - en caso de que el cliente no exista o no se encuentre
     *                   ninguna factura
     */
    private List<Factura> buscarFacturaPorCliente(String codigo) throws Exception {

        Optional<Cliente> cliente = clienteRepository.findById(codigo);// buscar el cliente

        if (cliente.isEmpty()) {// el cliente no existe
            throw new Exception("el cliente no existe");
        }

        List<Factura> facturas = facturaRepository.findByCliente(cliente.get());// buscar facturas por el cliente

        if (facturas.isEmpty()) {// no existe ninguna factura
            throw new Exception("no hay facturas por el cliente");
        }

        return facturas;
    }

    /**
     * Este metodo se encarga de llamar las validaciones necesarias para verificar
     * que una factura puede ser generada. cada metodo
     * invocado ejecuta una validacion especifica
     * 
     * @param facturaDto - contiene los datos necesaios para generar una factura
     * @throws Exception - error en alguna de las validaciones
     */
    private void validarDatos(FacturaDto facturaDto) throws Exception {

        validarDatosMinimos(facturaDto);

    }

    /**
     * Este metodo, se asegura que la factura tenga los datos minimos para poder
     * funcionar. los datos minimos son
     * 1-una cedula para agregar el cliente
     * 2-al menos un producto en la factura
     * 3-un pago minimo mayor que cero
     * 
     * @param facturaDto - contiene los datos de una factura
     */
    private void validarDatosMinimos(FacturaDto facturaDto) throws Exception {

        // cedula del cliente inexistente
        if (facturaDto.cedulaCliente().equals("")) {
            throw new Exception("debe de haber una cedula para el cliente");
        }

        // cero productos
        if (facturaDto.listaProductos().isEmpty()) {
            throw new Exception("debe de existir por lo menos un producto");
        }

        // sin ningun pago
        if (facturaDto.pago() <= 0) {
            throw new Exception("debe de haber un pago minimo para generar la factura");
        }
    }

    /*
     * Este metodo crea una instancia de la factura, le agrega una fecha
     */
    private Factura instanciarFactura() {

        Factura nuevaFactura = new Factura();

        nuevaFactura.inicializarFecha();

        return nuevaFactura;
    }

    /**
     * Este metodo se encarga de buscar un cliente, y al encontrarlo lo agregara a
     * la factura
     * 
     * @param facturaNueva  - instancia de la nueva factura
     * @param cedulaCliente - codigo unico del cliente
     * @throws Exception
     * 
     * @exception - el cliente no ha sido encontrado
     */
    private void agregarCliente(Factura facturaNueva, String cedulaCliente) throws Exception {

        Optional<Cliente> clienteEncontrado = clienteRepository.findById(cedulaCliente);

        if (clienteEncontrado.isEmpty()) {
            throw new Exception("El cliente no ha sido encontrado");
        }

        facturaNueva.setCliente(clienteEncontrado.get());
    }





}
