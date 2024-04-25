package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.repository.ProductoRepository;
import com.caciquesport.inventario.inventario.service.Intferfaces.ProductoServicio;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoServicioImpl implements ProductoServicio{

    private final ProductoRepository productoRepository;

    public ProductoServicioImpl(ProductoRepository productoRepository){
        this.productoRepository=productoRepository;
    }

    /*
     *crear un producto 
     *
     * @param nuevoProducto - el producto que se va a almacenar
     * 
     * @return - el codigo del producto almacenado en la base de datos
     */
    @Override
    public Integer crearProducto(Producto nuevoProducto) throws Exception {
        return productoRepository.save(nuevoProducto).getId();
    }


    /*
     *actualiza el producto, es importante que el parametro llege con el id correcto del producto para
     *actualizar correctamente. si el producto no existe se lanza una excepcion
     *
     * @param producto - el objeto el cual se desea actualizar/almacenar
     * 
     * @return - retorna el id del producto almacenado
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
     * eliminar el producto por medio del id
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
     */
    @Override
    public List<Producto> listarProductos() throws Exception {
        return productoRepository.findAll();
    }
}
