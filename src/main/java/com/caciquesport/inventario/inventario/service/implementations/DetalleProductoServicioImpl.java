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
        
          return detalleProductoRepository.save(nuevoDetalleProducto).getId();
  
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
        
            Optional<DetalleProducto>  detalleEncontrado =  detalleProductoRepository.findById(detalleProducto.getId());

            if(detalleEncontrado.isEmpty()){
                throw new Exception("error aztualizando el detalle del producto");
            }else{
                return detalleProductoRepository.save(detalleProducto).getId();
            }
            
    }


    /*
     * buscar el detalle de un producto por medio del codigo unico id, en caso de que el objeto no sea encontrado
     * lanzara una excepcion
     * 
     * @param id - identificador unico del detalle de producto, este esta vinculado al producto propietario
     * 
     * @return - devolvera el producto si lo encuentra, y si no lo encuentra devuelve un null
     * 
     */
    @Override
    public DetalleProducto obtenerDetalleProducto(Integer id) throws Exception {
 
            Optional<DetalleProducto> detalleEncontrado=detalleProductoRepository.findById(id);
        
            if(detalleEncontrado.isEmpty()){
                throw new Exception("el detalle no ha sido encontrado");
            }else{
                return detalleEncontrado.get();
            }

    }



    /*
     * eliminar el detalle del producto por medio del id, en caso de que no se encuentre el detalle o ocurra un error 
     * lanzara una excepcion
     * 
     * @param id - identificador unico del detalle del producto, este esta vinculado al producto propietario
     * 
     */
    @Override
    public void EliminarDetalleProducto(Integer id) throws Exception {
  
        Optional<DetalleProducto> detalleEncontrado=detalleProductoRepository.findById(id);

        if(detalleEncontrado.isEmpty()){
            throw new Exception("el detalle no ha podido ser eliminado");
        }else{
            detalleProductoRepository.delete(detalleEncontrado.get());
        }  
    }



    /*
     *buscar toda la lista de detalle de producto 4
     *
     * @return- retorna la lista completa de detalle de producto
     */
    @Override
    public List<DetalleProducto> listarDetalleProducto() throws Exception {
        
        return detalleProductoRepository.findAll();
    }

}
