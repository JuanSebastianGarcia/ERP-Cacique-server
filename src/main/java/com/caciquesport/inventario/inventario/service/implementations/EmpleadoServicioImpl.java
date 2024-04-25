package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import com.caciquesport.inventario.inventario.repository.EmpleadoRepository;
import com.caciquesport.inventario.inventario.service.Intferfaces.EmpleadoServicio;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpleadoServicioImpl implements EmpleadoServicio {


    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServicioImpl(EmpleadoRepository empleadoRepository){
        this.empleadoRepository=empleadoRepository;
    }

    /*
     * crear empleado
     * 
     * @param nuevoEmpleado- el objeto empleado que se va a almacenar
     * 
     * @return - id del empleado almacenado
     */
    @Override
    public Integer crearEmpleado(Empleado nuevoEmpleado) throws Exception {
        
        return empleadoRepository.save(nuevoEmpleado).getId();
    }




    /*
     * actualizar empleado. si el empleado no existe lanza una excepcion
     * 
     * @param empleado- el objeto empleado que se va a actualizar
     * 
     * @return - id del empleado actualizado
     */
    @Override
    public Integer actualizarEmpleado(Empleado empleado) throws Exception {
        
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findById(empleado.getId());

        if(empleadoEncontrado.isEmpty()){
            throw new Exception("el empleado no se puede actualizar");
        }else{
            return empleadoRepository.save(empleadoEncontrado.get()).getId();
        }
    }


    

    /*
     * eliminar empleado por medio de la cedula. si el empleado no existe lanza una excepcion
     * 
     * @param cedula - la cedula unica del empleado
     */
    @Override
    public void eliminarEmpleado(String cedula) throws Exception {
     
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findByCedula(cedula);

        if(empleadoEncontrado.isEmpty()){
            throw new Exception("el empleado no ha podido ser encontrado");
        }else{
            empleadoRepository.delete(empleadoEncontrado.get());
        }

    }



    /*
     *obtener el empleado por medio de la cedula. si el empleado no existe lanza una excepcion
     *
     * @param cedula - cedula unica del empleado
     * 
     * @return - empleado encontrado
     */
    @Override
    public Empleado obtenerEmpleado(String cedula) throws Exception {
        
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findByCedula(cedula);

        if(empleadoEncontrado.isEmpty()){
            throw new Exception("el empleado no ha podido ser encontrado");
        }else{
            return empleadoEncontrado.get();
        }
    }



    /*
     * obtener toda la lista de empleados
     * 
     * @return - la lista de empleados
     */
    @Override
    public List<Empleado> listarEmpleado() throws Exception {

        return empleadoRepository.findAll();
    }




}
