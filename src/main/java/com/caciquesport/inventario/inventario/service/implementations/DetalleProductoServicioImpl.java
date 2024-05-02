package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;
import com.caciquesport.inventario.inventario.repository.DetalleProductoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.DetalleProductoServicio;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DetalleProductoServicioImpl implements DetalleProductoServicio{


    private final DetalleProductoRepository detalleProductoRepository;



    /*
     * crear un detalleProducto
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
     *actualizar un detalleProducto. si el dealle no existe se lanza una excepcion
     *  
     * @param detalleProducto - detalle de producto que se va a actualizada
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
     * buscar el detalleProducto por el id. si no existe lanza una excepcion
     * 
     * @param id - id unico del detalleProducto
     * 
     * @return - devuelve el detalleProducto encontrado
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
     * eliminar el detalle del producto por medio del id, si no se encuentra lanza una excepcion
     * 
     * @param id - id unico del detalleProducto
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
     *buscar toda la lista de detalleProducto
     *
     * @return- retorna la lista  de detalle de producto
     */
    @Override
    public List<DetalleProducto> listarDetalleProducto() throws Exception {
        
        return detalleProductoRepository.findAll();
    }

}
