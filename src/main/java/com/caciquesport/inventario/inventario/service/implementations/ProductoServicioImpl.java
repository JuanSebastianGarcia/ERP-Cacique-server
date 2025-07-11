package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;
import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.repository.ProductoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoServicio;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServicioImpl implements ProductoServicio {

    /*
     * Respositorio que maneja el objeto principal del servicio
     */
    private final ProductoRepository productoRepository;

    /*
     * servicios usados en el proceso
     */
    private final TipoGeneroServicioImpl tipoGeneroServicioImpl;
    private final TipoHorarioServicioImpl tipoHorarioServicioImpl;
    private final TipoInstitucionServicioImpl tipoInstitucionServicioImpl;
    private final TipoPrendaServicioImpl tipoPrendaServicioImpl;
    private final TipoTallaServicioImpl tipoTallaServicioImpl;

    /*
     * crear un producto
     *
     * @param nuevoProducto - el objeto producto que se va a almacenar
     * 
     * @return - el id del producto almacenado
     */
    @Override
    public Integer crearProducto(ProductoDto registroProductoDto) throws Exception {

        boolean existencia = verificarExistenciaProducto(registroProductoDto);

        if (existencia == false) {
            Producto nuevoProducto = almacenarProducto(registroProductoDto);
            return nuevoProducto.getId();
        } else {
            throw new Exception("el producto ya existe");
        }
    }

    /*
     * crear y almacenar el objeto producto
     * 
     * @regitroProductoDto - objeto que contiene la informacion para la creacion de
     * un producto
     * 
     * @return el producto almacenado
     */
    private Producto almacenarProducto(ProductoDto registroProductoDto) throws Exception {

        // creacion de objetos
        Producto nuevoProducto = new Producto();
        DetalleProducto nuevoDetalleProducto = new DetalleProducto();

        // asignacion de datos al producto
        asignarDatosProducto(registroProductoDto, nuevoProducto);
        asignarDatosDetalleProducto(registroProductoDto, nuevoDetalleProducto);

        // agregar el detalle al producto
        nuevoProducto.setDetalleProducto(nuevoDetalleProducto);

        // enviar a la base de datos
        productoRepository.save(nuevoProducto);

        return nuevoProducto;
    }

    /*
     * asigar o agregar datos a un objeto detalleProducto
     * 
     * @param registroProductoDto - contiene la informacion necesaria para crear el
     * objeto
     * 
     * @param nuevoDetalleProducto - referencia al objeto que se esta construyendo
     */
    private void asignarDatosDetalleProducto(ProductoDto registroProductoDto, DetalleProducto nuevoDetalleProducto) {

        nuevoDetalleProducto.setCantidad(registroProductoDto.cantidad());
        nuevoDetalleProducto.setPrecio(registroProductoDto.precio());

    }

    /*
     * asigar o agregar datos a un objeto producto. se le asignan los datos de
     * configuracion al producto que se va a almacenar
     * 
     * @param registroProductoDto - contiene la informacion necesaria para crear el
     * objeto
     * 
     * @param nuevoProducto - referencia al objeto que se esta construyendo
     */
    private void asignarDatosProducto(ProductoDto registroProductoDto, Producto nuevoProducto) throws Exception {

        nuevoProducto.setTipoPrenda(tipoPrendaServicioImpl.obtenerPrenda(registroProductoDto.prenda()));
        nuevoProducto.setTipoHorario(tipoHorarioServicioImpl.obtenerHorario(registroProductoDto.horario()));
        nuevoProducto.setTipoTalla(tipoTallaServicioImpl.obtenerTalla(registroProductoDto.talla()));
        nuevoProducto
                .setTipoInstitucion(tipoInstitucionServicioImpl.obtenerInstitucion(registroProductoDto.institucion()));
        nuevoProducto.setTipoGenero(tipoGeneroServicioImpl.obtenerGenero(registroProductoDto.genero()));

    }

    /**
     * verifiar la existena del producto validando que no haya otro objeto
     * con los mismos valores para los atributos principales(talla, prenda, horario,
     * institucion, genero)
     * 
     * @param registroProductoDto - contiene la informacion necesarioa
     * 
     * @return - el objeto existe(true) o no existe(false)
     */
    private boolean verificarExistenciaProducto(ProductoDto registroProductoDto) throws Exception {

        boolean respuesta = false;

        TipoGenero genero = tipoGeneroServicioImpl.obtenerGenero(registroProductoDto.genero());
        TipoTalla talla = tipoTallaServicioImpl.obtenerTalla(registroProductoDto.talla());
        TipoInstitucion institucion = tipoInstitucionServicioImpl.obtenerInstitucion(registroProductoDto.institucion());
        TipoHorario horario = tipoHorarioServicioImpl.obtenerHorario(registroProductoDto.horario());
        TipoPrenda prenda = tipoPrendaServicioImpl.obtenerPrenda(registroProductoDto.prenda());

        Optional<Producto> productoEncontrado = productoRepository.verificarExistenciaProducto(institucion, talla,
                genero, horario, prenda);

        if (!productoEncontrado.isEmpty()) {
            respuesta = true;
        }

        return respuesta;
    }

    /*
     * actualiza el producto. si el producto no existe se lanza una excepcion
     *
     * @param producto - el objeto el cual se desea actualizar/almacenar
     * 
     * @return - id del producto almacenado
     * 
     */
    @Override
    public Integer actualizarProducto(ProductoDto productoDto, Integer idProducto) throws Exception {

        Optional<Producto> productoEncontrado = productoRepository.findById(idProducto);

        // actualizar los datos
        Producto productoActualizado = actualizarDatos(productoEncontrado.get(), productoDto);

        if (productoEncontrado.isEmpty()) {
            throw new Exception("no se puede actualizar el producto debido a que no se encuentra");
        } else {
            return productoRepository.save(productoActualizado).getId();
        }
    }

    /*
     * actualizar los datos en el detalle de un producto
     * 
     * @param producto - el producto que se va a actualizar
     * 
     * @param productoDto - contiene los datos a actualizar
     * 
     * @return producto actualizado
     * 
     */
    private Producto actualizarDatos(Producto producto, ProductoDto productoDto) {

        producto.getDetalleProducto().setCantidad(productoDto.cantidad());
        producto.getDetalleProducto().setPrecio(productoDto.precio());

        return producto;
    }

    /*
     * eliminar el producto por medio del id. si no se encuentra lanza una excepcion
     * 
     * @param id- es el id unico del producto
     * 
     */
    @Override
    public void eliminarProducto(Integer id) throws Exception {

        Optional<Producto> productoEncontrado = productoRepository.findById(id);

        if (productoEncontrado.isEmpty()) {
            throw new Exception("no se puede eliminar el producto");
        } else {
            productoRepository.delete(productoEncontrado.get());
        }
    }

    /*
     * obtener toda la lista de los productos
     * 
     * @return - la lista de productos
     */
    @Override
    public List<Producto> listarProductos() throws Exception {
        return productoRepository.findAll();
    }

    /**
     * filtra la lista de productos con base en una serie de parametros institucion,
     * talla, prenda, genero y horario
     * 
     * @param filtroProducto - lista de datos para filtrar una lista de productos
     */
    @Override
    public List<ProductoDto> filtrarListaProducto(FiltroProductoDto filtroProductoDto) throws Exception {

        List<Producto> listaProductos = listarProductos();

        listaProductos = filtrarPorPrenda(listaProductos, filtroProductoDto);
        listaProductos = filtrarPorGenero(listaProductos, filtroProductoDto);
        listaProductos = filtrarPorHorario(listaProductos, filtroProductoDto);
        listaProductos = filtrarPorInstitucion(listaProductos, filtroProductoDto);
        listaProductos = filtrarPorTalla(listaProductos, filtroProductoDto);

        return convertirListaDto(listaProductos);
    }

    /**
     * Metodo encargado de buscar un producto espcifico en la base de datos y
     * retornar la instancia
     * 
     * @param filtroProducto - criterios de busqueda
     * 
     * @return Producto - objeto producto encontrado
     */
    @Override
    public Producto buscarObjetoProducto(FiltroProductoDto filtroProductoDto) throws Exception {

        TipoGenero genero = tipoGeneroServicioImpl.obtenerGenero(filtroProductoDto.genero());
        TipoTalla talla = tipoTallaServicioImpl.obtenerTalla(filtroProductoDto.talla());
        TipoInstitucion institucion = tipoInstitucionServicioImpl.obtenerInstitucion(filtroProductoDto.institucion());
        TipoHorario horario = tipoHorarioServicioImpl.obtenerHorario(filtroProductoDto.horario());
        TipoPrenda prenda = tipoPrendaServicioImpl.obtenerPrenda(filtroProductoDto.prenda());

        Optional<Producto> productoEncontrado = productoRepository.verificarExistenciaProducto(institucion, talla,
                genero, horario, prenda);

        if (productoEncontrado.isEmpty()) {
            throw new Exception("Un producto no ha sido encontrado");
        }

        return productoEncontrado.get();
    }

    /*
     * convertir la lista de productos en formato de objeto a un formato dto
     * 
     * @param listaProductos - lista de productos a convertir
     * 
     * @return lista de datos en formato dto
     */
    private List<ProductoDto> convertirListaDto(List<Producto> listaProductos) {

        List<ProductoDto> listaDto = new ArrayList<>();

        for (Producto producto : listaProductos) {

            listaDto.add(new ProductoDto(producto.getId(),
                    producto.getTipoPrenda().getPrenda(),
                    producto.getTipoInstitucion().getInstitucion(),
                    producto.getTipoTalla().getTalla(),
                    producto.getTipoHorario().getHorario(),
                    producto.getTipoGenero().getGenero(),
                    producto.getDetalleProducto().getPrecio(),
                    producto.getDetalleProducto().getCantidad()));

        }

        return listaDto;
    }

    /*
     * filtra una lista de productos removiendo los que no concuerden con una Prenda
     */
    private List<Producto> filtrarPorPrenda(List<Producto> listaProductos, FiltroProductoDto filtroProductoDto) {

        String prenda = filtroProductoDto.prenda();
        List<Producto> nuevaLista = new ArrayList<>();

        if (!prenda.equals("")) {

            for (Producto producto : listaProductos) {
                if (producto.getTipoPrenda().getPrenda().equals(prenda)) {
                    nuevaLista.add(producto);
                }
            }

        } else {
            nuevaLista = listaProductos;
        }
        return nuevaLista;
    }

    /*
     * filtra una lista de productos removiendo los que no concuerden con una
     * Institucion
     */
    private List<Producto> filtrarPorInstitucion(List<Producto> listaProductos, FiltroProductoDto filtroProductoDto) {

        String institucion = filtroProductoDto.institucion();
        List<Producto> nuevaLista = new ArrayList<>();

        if (!institucion.equals("")) {

            for (Producto producto : listaProductos) {
                if (producto.getTipoInstitucion().getInstitucion().equals(institucion)) {
                    nuevaLista.add(producto);
                }
            }

        } else {
            nuevaLista = listaProductos;
        }
        return nuevaLista;
    }

    /*
     * filtra una lista de productos removiendo los que no concuerden con una Talla
     */
    private List<Producto> filtrarPorTalla(List<Producto> listaProductos, FiltroProductoDto filtroProductoDto) {

        String talla = filtroProductoDto.talla();
        List<Producto> nuevaLista = new ArrayList<>();

        if (!talla.equals("")) {
            for (Producto producto : listaProductos) {
                if (producto.getTipoTalla().getTalla().equals(talla)) {
                    nuevaLista.add(producto);
                }
            }
        } else {
            nuevaLista = listaProductos;
        }
        return nuevaLista;
    }

    /*
     * filtra una lista de productos removiendo los que no concuerden con un Horario
     */
    private List<Producto> filtrarPorHorario(List<Producto> listaProductos, FiltroProductoDto filtroProductoDto) {

        String horario = filtroProductoDto.horario();
        List<Producto> nuevaLista = new ArrayList<>();

        if (!horario.equals("")) {
            for (Producto producto : listaProductos) {
                if (producto.getTipoHorario().getHorario().equals(horario)) {
                    nuevaLista.add(producto);
                }
            }
        } else {
            nuevaLista = listaProductos;
        }
        return nuevaLista;
    }

    /*
     * filtra una lista de productos removiendo los que no concuerden con una Genero
     */
    private List<Producto> filtrarPorGenero(List<Producto> listaProductos, FiltroProductoDto filtroProductoDto) {

        String genero = filtroProductoDto.genero();
        List<Producto> nuevaLista = new ArrayList<>();

        if (!genero.equals("")) {
            for (Producto producto : listaProductos) {
                if (producto.getTipoGenero().getGenero().equals(genero)) {
                    nuevaLista.add(producto);
                }
            }
        } else {
            nuevaLista = listaProductos;
        }
        return nuevaLista;
    }

    /**
     * Este metodo consulta una lista de productos con base en una lista de ID
     * llegados por parametro
     *
     * @param listaID - contiene los id que se solicitan para la consulta
     * 
     * @return lista de productos
     */
    public Map<Integer, Producto> buscarListaProductos(Set<Integer> listaId) {

        Map<Integer, Producto> mapaProductos = new HashMap<>();

        for (int id : listaId) {
            mapaProductos.put(id, productoRepository.findById(id).get());
        }

        return mapaProductos;
    }

}
