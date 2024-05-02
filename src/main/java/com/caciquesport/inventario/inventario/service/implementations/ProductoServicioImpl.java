package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.caciquesport.inventario.inventario.dto.RegistroProductoDto;
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
public class ProductoServicioImpl implements ProductoServicio{

    /*
     * Respositorio que maneja el objeto principal del servicio
     */
    private final ProductoRepository productoRepository;


    /*
     *servicios usados en el proceso 
     */
    private final TipoGeneroServicioImpl tipoGeneroServicioImpl;
    private final TipoHorarioServicioImpl tipoHorarioServicioImpl;
    private final TipoInstitucionServicioImpl tipoInstitucionServicioImpl;
    private final TipoPrendaServicioImpl tipoPrendaServicioImpl;
    private final TipoTallaServicioImpl tipoTallaServicioImpl;


    /*
     *crear un producto 
     *
     * @param nuevoProducto - el objeto producto que se va a almacenar
     * 
     * @return - el id del producto almacenado 
     */
    @Override
    public Integer crearProducto(RegistroProductoDto registroProductoDto) throws Exception {

        boolean existencia=verificarExistenciaProducto(registroProductoDto);

        if(existencia==false){
            Producto nuevoProducto = almacenarProducto(registroProductoDto);
            return nuevoProducto.getId();
        }else{
            throw new Exception("el producto ya existe");
        }
    }



    /*
     * crear y almacenar el objeto producto
     */
    private Producto almacenarProducto(RegistroProductoDto registroProductoDto) throws Exception {

        //creacion de objetos
        Producto nuevoProducto = new Producto();
        DetalleProducto nuevoDetalleProducto = new DetalleProducto();

        //asignacion de datos al detalle de producto


        //asignacion de datos al producto
        asignarDatosProducto(registroProductoDto, nuevoProducto);
        asignarDatosDetalleProducto(registroProductoDto,nuevoDetalleProducto);

        //agregar el detalle al producto
        nuevoProducto.setDetalleProducto(nuevoDetalleProducto);

        //enviar a la base de datos
        productoRepository.save(nuevoProducto);

        return nuevoProducto;
    }


    /*
     * asigar o agregar datos a un objeto detalleProducto
     * 
     * @param registroProductoDto - contiene la informacion necesaria para crear el objeto
     * @param nuevoDetalleProducto - referencia al objeto que se esta construyendo
     */
    private void asignarDatosDetalleProducto(RegistroProductoDto registroProductoDto, DetalleProducto nuevoDetalleProducto) {
        
        nuevoDetalleProducto.setCantidad(registroProductoDto.cantidad());
        nuevoDetalleProducto.setPrecio(registroProductoDto.precio());
        nuevoDetalleProducto.setDescripcion(registroProductoDto.descripcion());

    }



    /*
     * asigar o agregar datos a un objeto producto
     * 
     * @param registroProductoDto - contiene la informacion necesaria para crear el objeto
     * @param nuevoProducto - referencia al objeto que se esta construyendo
     */
    private void asignarDatosProducto(RegistroProductoDto registroProductoDto, Producto nuevoProducto ) throws Exception{

        nuevoProducto.setTipoPrenda(tipoPrendaServicioImpl.obtenerPrenda(registroProductoDto.prenda()));
        nuevoProducto.setTipoHorario(tipoHorarioServicioImpl.obtenerHorario(registroProductoDto.horario()));
        nuevoProducto.setTipoTalla(tipoTallaServicioImpl.obtenerTalla(registroProductoDto.talla()));
        nuevoProducto.setTipoInstitucion(tipoInstitucionServicioImpl.obtenerInstitucion(registroProductoDto.institucion()));
        nuevoProducto.setTipoGenero(tipoGeneroServicioImpl.obtenerGenero(registroProductoDto.genero()));

    }



    /*
     * vericiar la existena del producto validando que no haya otro objeto 
     * con los mismos valores para los atributos principales(talla, prenda, horario, institucion, genero)
     * 
     * @param registroProductoDto - contiene la informacion necesarioa
     * 
     * @return - el objeto existe(true) o no existe(false)
     */
    private boolean verificarExistenciaProducto(RegistroProductoDto registroProductoDto) throws Exception {

       boolean respuesta=false;

       TipoGenero genero = tipoGeneroServicioImpl.obtenerGenero(registroProductoDto.genero());
       TipoTalla talla = tipoTallaServicioImpl.obtenerTalla(registroProductoDto.talla());
       TipoInstitucion institucion = tipoInstitucionServicioImpl.obtenerInstitucion(registroProductoDto.institucion());
       TipoHorario horario = tipoHorarioServicioImpl.obtenerHorario(registroProductoDto.horario());
       TipoPrenda prenda = tipoPrendaServicioImpl.obtenerPrenda(registroProductoDto.prenda());

       Optional<Producto> productoEncontrado = productoRepository.verificarExistenciaProducto(institucion, talla,genero, horario, prenda);

       if (!productoEncontrado.isEmpty()) {
            respuesta=true;
       }

        return respuesta;
    }




    /*
     *actualiza el producto. si el producto no existe se lanza una excepcion
     *
     * @param producto - el objeto el cual se desea actualizar/almacenar
     * 
     * @return -  id del producto almacenado
     * 
     */
    @Override
    public Integer actualizarProducto(Producto producto) throws Exception {
        
        Optional<Producto> productoEncontrado=productoRepository.findById(producto.getId());

        if(productoEncontrado.isEmpty()){
            throw new Exception("no se puede actualizar el producto");
        }else{
            return productoRepository.save(producto).getId(); 
        }
    }






    /*
     * eliminar el producto por medio del id. si no se encuentra lanza una excepcion
     * 
     * @param id- es el id unico del producto
     * 
     */
    @Override
    public void eliminarProducto(Integer id) throws Exception {

        Optional<Producto> productoEncontrado=productoRepository.findById(id);

        if(productoEncontrado.isEmpty()){
            throw new Exception("no se puede eliminar el producto");
        }else{
            productoRepository.delete(productoEncontrado.get());
        }
    }





    /*
     * obtener el producto por medio del id. si el producto no existe lanza una excepcion
     * 
     * @param id- es el id unico del producto
     * 
     * @return - el producto encontrado
     */
    @Override
    public Producto obtenerProducto(Integer id) throws Exception {
        
        Optional<Producto> productoEncontrado=productoRepository.findById(id);

        if(productoEncontrado.isEmpty()){
            throw new Exception("el producto no se puede encontrar");
        }else{
            return productoEncontrado.get();
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
}
