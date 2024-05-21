package com.caciquesport.inventario.inventario.service.implementations;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.caciquesport.inventario.inventario.dto.EmpleadoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.exceptions.types.EmpleadoNoEncontradoException;
import com.caciquesport.inventario.inventario.model.configTypes.TipoEmpleado;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import com.caciquesport.inventario.inventario.repository.EmpleadoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.EmpleadoServicio;
import java.util.ArrayList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmpleadoServicioImpl implements EmpleadoServicio {


    private final EmpleadoRepository empleadoRepository;


    /*
     * crear empleado. en caso de que exista una excepcion sera disparada
     * 
     * @param nuevoEmpleado- el objeto empleado que se va a almacenar
     * 
     * @return - id del empleado almacenado
     */
    @Override
    public Integer crearEmpleado(EmpleadoDto nuevoEmpleadoDto) throws Exception {
        
        //se verifica que el empleado no exista
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findByEmail(nuevoEmpleadoDto.email());

        if(empleadoEncontrado.isPresent()){
            throw new Exception("ya existe un empleado con este correo");
        }

        Empleado nuevoEmpleado = new Empleado();

        registrarDatos(nuevoEmpleado,nuevoEmpleadoDto);


        return empleadoRepository.save(nuevoEmpleado).getId();
    }



    /*
     * construir un objeto Empleado con base en una clase EmpleadoDto
     */
    private void registrarDatos(Empleado nuevoEmpleado,EmpleadoDto nuevoEmpleadoDto) {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncrypt=encoder.encode(nuevoEmpleadoDto.password());

        nuevoEmpleado.setCedula(nuevoEmpleadoDto.cedula());
        nuevoEmpleado.setEmail(nuevoEmpleadoDto.email());
        nuevoEmpleado.setNombre(nuevoEmpleadoDto.nombre());
        nuevoEmpleado.setPassword(passwordEncrypt);
        nuevoEmpleado.setTelefono(nuevoEmpleadoDto.telefono());

        if(nuevoEmpleadoDto.tipoEmpleado().equals("JEFE")){
            nuevoEmpleado.setTipoEmpleado(TipoEmpleado.JEFE);
        }
        if(nuevoEmpleadoDto.tipoEmpleado().equals("EMPLEADO")){
            nuevoEmpleado.setTipoEmpleado(TipoEmpleado.EMPLEADO);
        }


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
            return empleadoRepository.save(empleado).getId();
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
            throw new Exception("el empleado no ha sido encontrado");
        }
        empleadoRepository.delete(empleadoEncontrado.get());
    }



    /*
     *obtener el empleado por medio de la cedula. si el empleado no existe lanza una excepcion
     *
     * @param cedula - cedula unica del empleado
     * 
     * @return - empleado encontrado
     */
    @Override
    public Empleado obtenerEmpleado(String email) throws Exception {
        
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findByEmail(email);

        if(empleadoEncontrado.isEmpty()){
            throw new EmpleadoNoEncontradoException("el email no ha sido encontrado");
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
    public List<EmpleadoDto> listarEmpleado() throws Exception {

        List<Empleado> listaEmpleados=empleadoRepository.findAll();

        return listarEmpleadosDto(listaEmpleados);
    }



    /*
     * recibe una lista de empleados y saca una nueva lista con el formato dto
     * @param listaEmpleados - lista que contiene la lista Empleados
     * 
     * @return lista que contiene los EmpeladosDto
     */
    private List<EmpleadoDto> listarEmpleadosDto(List<Empleado> listaEmpleados) {
        
        List<EmpleadoDto> listaDto=new ArrayList<>();

        for (Empleado empleado : listaEmpleados) {

        
            EmpleadoDto empleadoDto = new EmpleadoDto(empleado.getId(),
                        empleado.getNombre(),
                        empleado.getCedula(),
                        empleado.getTelefono(),
                        empleado.getEmail(),
                        "empty",
                        empleado.getTipoEmpleado().toString());

            listaDto.add(empleadoDto);
        }

        return listaDto;
    }




}
