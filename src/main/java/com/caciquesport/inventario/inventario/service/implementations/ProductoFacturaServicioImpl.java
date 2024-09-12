package com.caciquesport.inventario.inventario.service.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.model.entity.ProductoFactura;
import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;
import com.caciquesport.inventario.inventario.repository.FacturaRepository;
import com.caciquesport.inventario.inventario.repository.ProductoFacturaRepository;
import com.caciquesport.inventario.inventario.repository.ProductoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoFacturaServicio;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/*
 * Este servicio esta diseñado para poder agregar una lista de productos a una factura. Para esta relacion se usa 
 * un objeto "ProductoFactura" que representa una relacion entre un objeto y factura. 
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductoFacturaServicioImpl implements ProductoFacturaServicio {

    /*
     * Servicio para el manejo de los productos
     */
    private final ProductoServicioImpl productoServicioImpl;
    private final ProductoFacturaRepository productoFacturaRepository;

    /*
     * Repositorio para el manejo de los productos
     */
    private final ProductoRepository productoRepository;

    /**
     * Metodo encargado de agregar una lista de productos a una factura. se 
     * debera buscar la instancia de cada producto, agregarla a la lista de productos y
     * actualizar el inventario segun el estado en el que quede cada producto. por ultimo
     * la lista de productos con las instancias sera agregada a la factura
     * 
     * @param listaProductos - contiene la informacion necesaria de los productos
     * @param factura        - instancia de la factura a la que se le agregara la lista
     * 
     */
    @Override
    public void generarListaProductos(List<ProductoFacturaDto> listaProductosDto, Factura factura) throws Exception {

        List<Producto> listaObjetosProducto = new ArrayList<>();

        for (ProductoFacturaDto producto : listaProductosDto) {

            // buscar el objeto producto
            Producto productoEncontrado = productoServicioImpl.buscarObjetoProducto(
                    new FiltroProductoDto(
                            producto.prenda(), producto.talla(), producto.horario(), producto.genero(),
                            producto.institucion()));

            eliminarUnidadProducto(productoEncontrado, producto.estado());

            // agregar el objeto a la lista
            listaObjetosProducto.add(productoEncontrado);
        }

        agregarProductosAFactura(listaObjetosProducto, factura, listaProductosDto);
    }

    /*
     * Este metodo se encarga de eliminar una unidad de la cantidad de un producto
     * Sí el estado es empacado o entregado.
     * 
     */
    private void eliminarUnidadProducto(Producto productoEncontrado, String estado) {

        EstadoProducto estadoEnum = EstadoProducto.valueOf(estado);

        if (estadoEnum.equals(EstadoProducto.EMPACADO) || estadoEnum.equals(EstadoProducto.ENTREGADO)) {// estado
                                                                                                        // entregado o
                                                                                                        // empacado

            if (productoEncontrado.getDetalleProducto().getCantidad() >= 1) {
                productoEncontrado.getDetalleProducto()
                        .setCantidad(productoEncontrado.getDetalleProducto().getCantidad() - 1);

                productoRepository.save(productoEncontrado);
            }

        }

    }

    /**
     * Este metodo toma una lista de objetos de productos, extrae su id y lo agrega
     * a una relacion entre una factura y producto.
     * esta lista de relaciones sera agregada a la factura para representar cuales
     * productos contiene
     * 
     * @param listaObjetosProducto - contiene las instancias de los objetos
     *                             encontrados
     * @param factura              - instancia de la factura
     */
    private void agregarProductosAFactura(List<Producto> listaObjetosProducto, Factura factura,
            List<ProductoFacturaDto> listaProductosDto) {

        ArrayList<ProductoFactura> productosFactura = new ArrayList<>();

        // se deben de crear las relaciones
        for (int i = 0; i < listaObjetosProducto.size(); i++) {

            // se crea la instancia
            ProductoFactura productoFactura = new ProductoFactura();

            // se establece el estado
            String estadoString = listaProductosDto.get(i).estado();
            EstadoProducto estadoProducto = EstadoProducto.valueOf(estadoString.toUpperCase());

            // se agregan los datos
            productoFactura.setEstadoProducto(estadoProducto);
            productoFactura.setFactura(factura);
            productoFactura.setProducto(listaObjetosProducto.get(i).getId());

            // se agrega la relacion
            productosFactura.add(productoFactura);
        }

        factura.setListaProductosFactura(productosFactura);

    }

    /**
     * Metodo encargado de recibir una lista de productos de una factura y
     * convertirla a una lista pero en formato dto.
     * 
     * @param listaProductosFactura lista de relaciones entre una factura y los
     *                              productos
     * @return lista de productos dto
     */
    public List<ProductoFacturaDto> convertirListaProductosDto(List<ProductoFactura> listaProductosFactura) {

        List<ProductoFacturaDto> listaProductosDto = new ArrayList<>();

        for (ProductoFactura producto : listaProductosFactura) {

            Producto productoEncontrado = productoRepository.findById(producto.getProducto()).get();

            listaProductosDto.add(new ProductoFacturaDto(producto.getId(),
                    productoEncontrado.getTipoPrenda().getPrenda(),
                    productoEncontrado.getTipoInstitucion().getInstitucion(),
                    productoEncontrado.getTipoTalla().getTalla(),
                    productoEncontrado.getTipoHorario().getHorario(), productoEncontrado.getTipoGenero().getGenero(),
                    productoEncontrado.getDetalleProducto().getPrecio(), producto.getEstadoProducto().toString()));
        }

        return listaProductosDto;
    }

    /**
     * Este metodo se encarga de revisar si los productos que fueron entregados, se
     * mantienen asi en los cambios que se
     * quieren realizar
     * 
     * @param listaAnterior - representa la lista de productos de la factura
     *                      anteriormente generada
     * @param listaActual   - represennta la nueva lista de productos que sera
     *                      cambiada
     */
    public void ValidarListaProductos(List<ProductoFactura> listaAnterior, List<ProductoFacturaDto> listaActual)
            throws Exception {

        for (ProductoFactura productoAnterior : listaAnterior) {

            if (productoAnterior.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {

                // verificar si el producto mantiene el estado entregado
                verificarContinuidadDeProductoEntregado(productoAnterior.getId(), listaActual);

            }
        }

    }

    /**
     * este metodo se encarga de verificar si un producto entregado permanece en
     * estado entregado en la nueva lista
     * 
     * @param idProductoAnterior - id del producto de la factura
     * @param listaActual        - nueva lista de productos
     * @throws Exception - en caso de cambiar el estado de un producto entregado o eliminarlo
     */
    private void verificarContinuidadDeProductoEntregado(int idProductoAnterior, 
            List<ProductoFacturaDto> listaActual) throws Exception {

        boolean inexistente=false; //esta bandera nos permite identificar si el producto no fue encontrado 

        for (ProductoFacturaDto productoFacturaDto : listaActual) {

            if (productoFacturaDto.idRelacion() == idProductoAnterior) {

                EstadoProducto estadoProducto = EstadoProducto.valueOf(productoFacturaDto.estado());

                if (!(estadoProducto.equals(EstadoProducto.ENTREGADO))) {
                    throw new Exception("los productos que ya fueron entregados no pueden cambiar de estado");
                }
                inexistente=false;
                break;
            }else{
                inexistente=true;
            }
        }

        //con este if si el producto no se encontro fue porque se elimino
        if(inexistente==true){
            throw new Exception("los productos entregados no se pueden devolver");
        }



    }


    /**
     * Este metodo se encarga de verificar que se entreguen la lista de productos
     * correspondientes en una factura. en este proceso se
     * verifica que la suma de los productos entregados no supere el valor total
     * pagado
     * 
     * @param valorTotalPagado
     * @param listaProductosFactura
     * @throws Exception
     */
    public void validarProductosEntregados(double valorTotalPagado, List<ProductoFactura> listaProductosFactura)
            throws Exception {

        double sumaProductosEntregados = 0;

        for (ProductoFactura productoFactura : listaProductosFactura) {

            if (productoFactura.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {

                Producto producto = productoRepository.findById(productoFactura.getProducto()).get();
                sumaProductosEntregados += producto.getDetalleProducto().getPrecio();
            }
        }

        if (sumaProductosEntregados > valorTotalPagado) {
            throw new Exception("El valor de los productos entregados supera el valor pagado");
        }

    }

    /**
     * Este método se encarga de actualizar la lista de productos de una factura y
     * de cambiar su estado. La única
     * acción que se puede hacer es cambiar el estado de un producto o retirar un
     * producto que este en estado pendiente o empacado; en este punto no es posible
     * agregar un producto a la factura
     * 
     * @param listaProductosDto - lista de productos actualizada 
     * @param factura           - instancia de la factura origin en la base de datos
     * 
     * @throws Exception
     */
    public void actualizarListaProductos(List<ProductoFacturaDto> listaProductosDto, Factura factura)
            throws Exception {

        List<ProductoFactura> listaProductosFactura = factura.getListaProductosFactura();
        Map<Long, ProductoFactura> productoMap = new HashMap<>();

        // Crear un mapa de los productos existentes por ID
        for (ProductoFactura producto : listaProductosFactura) {
            productoMap.put(Long.valueOf(producto.getId()), producto);
        }

        //Iterar sobre la nueva lista de productos
        for (ProductoFacturaDto productoDto : listaProductosDto) {

            ProductoFactura productoExistente = productoMap.get(Long.valueOf(productoDto.idRelacion()));

            if (productoExistente != null) {

                factura.actualizarEstadoProducto(productoExistente,productoDto.estado());
               
                // Remover el producto del mapa para marcarlo como procesado
                productoMap.remove(Long.valueOf(productoDto.idRelacion()));

            }
        }

        // Devolver los productos que no se encontraron en la nueva lista
        for (ProductoFactura productoEliminado : productoMap.values()) {
            factura.removeProductoFactura(productoEliminado);
            productoFacturaRepository.delete(productoEliminado);
            devolverUnidadProducto(productoEliminado);
        }

    }




    /**
     * Este método se encarga de hacer la devolución de un producto agregando una
     * unidad a la cantidad en la base de datos
     * 
     * @param productoEliminado - información del producto
     */
    private void devolverUnidadProducto(ProductoFactura productoEliminado) {
        Producto producto = productoRepository.findById(productoEliminado.getProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.getDetalleProducto().setCantidad(producto.getDetalleProducto().getCantidad() + 1); // Se devuelve la unidad al producto

        productoRepository.save(producto);
    }

}
