package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;
import com.caciquesport.inventario.inventario.repository.DetalleProductoRepository;
import com.caciquesport.inventario.inventario.service.Intferfaces.DetalleProductoServicio;
import java.util.Optional;

@Service
@Transactional
public class DetalleProductoServicioImpl implements DetalleProductoServicio{


    private final DetalleProductoRepository detalleProductoRepository;


    public DetalleProductoServicioImpl(DetalleProductoRepository detalleProductoRepository){
        this.detalleProductoRepository=detalleProductoRepository;
    }


    /*
     * crear un detalle el cual esta ligado a un solo producto
     * 
     * @param nuevoDetalleProducto - el detalle que se va a almacenar
     * 
     * @return el codigo del detalle almacenado en la base de datos
     */
    @Override
    public Integer crearDetalleProducto(DetalleProducto nuevoDetalleProducto) throws Exception {
        
        try {
            return detalleProductoRepository.save(nuevoDetalleProducto).getId();
        } catch (Exception e) {
            throw new UnsupportedOperationException("error guardando el detalle del producto");
        }
    }


    /*
     *actualizar la informacion de un detalle de producto
     *  
     * @param detalleProducto - detalle de producto con la informacion actualizada
     *  
     * @return - el codigo del detalle almacenado en la base de datos
     */
    @Override
    public Integer actualizarDetalleProducto(DetalleProducto detalleProducto) throws Exception {
        
        try {
            return detalleProductoRepository.save(detalleProducto).getId();
        } catch (Exception e) {
            throw new UnsupportedOperationException("error aztualizando el detalle del producto");
        }
        
    }

    /*
     * buscar el detalle de un producto por medio del codigo unico id, en caso de que el objeto no sea encontrado
     * devolvera un null
     * 
     * @param id - identificador unico del detalle de producto, este esta vinculado al producto para encontrarlo
     * 
     * @return - devolvera el producto si lo encuentra, y si no lo encuentra devuelve un null
     * 
     */
    @Override
    public DetalleProducto obtenerDetalleProducto(Integer id) throws Exception {
        
        try {

            Optional<DetalleProducto> detalleEncontrado=detalleProductoRepository.findById(id);
        
            DetalleProducto detalleProducto = detalleEncontrado.orElse(null);

            return detalleProducto;   
    
        } catch (Exception e) {
            throw new UnsupportedOperationException("error obteniendo el detalle del producto");
        }
    }

    @Override
    public void EliminarDetalleProducto(Integer Id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("error eliminando el detalle del producto");
    }

    @Override
    public List<DetalleProducto> listarDetalleProducto() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("error listando los detalles de los productos");
    }

}
