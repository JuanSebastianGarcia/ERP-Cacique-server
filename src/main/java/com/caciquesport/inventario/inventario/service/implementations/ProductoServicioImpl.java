package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;

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
     * 
     */
    @Override
    public Integer actualizarProducto(Producto producto) throws Exception {
        
        throw new UnsupportedOperationException("Unimplemented method 'actualizarProducto'");
    }

    @Override
    public void eliminarProducto() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarProducto'");
    }

    @Override
    public Producto obtenerProducto(Producto productoBuscado) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerProducto'");
    }

    @Override
    public List<Producto> listarProductos() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarProductos'");
    }
}
