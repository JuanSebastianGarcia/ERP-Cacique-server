package com.caciquesport.inventario.inventario.service.implementations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.exceptions.types.EmpleadoNoEncontradoException;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import com.caciquesport.inventario.inventario.service.interfaces.AutenticacionServicio;
import com.caciquesport.inventario.inventario.utils.TokenJwtServicio;

import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

/*
 * Este servicio esta diseñado para poder validar un usuario dentro de la base de datos
 */
@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio{

    //servicio usado para la comunicacion con la tabla de empleados
    private final EmpleadoServicioImpl empleadoServicioImpl;

    //servicio usado para la gestion de los token
    private final TokenJwtServicio jwtUtils;




    /*
     * Verificar la existencia de un usuario verificando el email y la contraseña
     * 
     * @param loginDto - objeto que contiene la informacion para el login (email y contraseña)
     */
    @Override
    public String verificarIdentidad(LoginDto loginDto)throws Exception {

        Empleado empleadoEncontrado=empleadoServicioImpl.obtenerEmpleado(loginDto.email());

        if(!verificarPassword(empleadoEncontrado , loginDto.password())){
            throw new EmpleadoNoEncontradoException("la contraseña no coincide");
        }

        return generarToken(empleadoEncontrado);
    }




    /*
     * generar un token si las credenciales fueron validadas
     * 
     * @param empleadoEncontrado - empleado ingresado en el sistema 
     * 
     * @return token en su representacion dto
     */
    private String generarToken(Empleado empleadoEncontrado) {
        
        Map<String,Object> datos=new HashMap<>();

        datos.put("tipoEmpleado", empleadoEncontrado.getTipoEmpleado().toString());
        datos.put("id", empleadoEncontrado.getId());
        datos.put("nombre",empleadoEncontrado.getNombre());

        //invocacion a metodos que generan el token 
        String token = jwtUtils.generarToken(empleadoEncontrado.getEmail(),datos);

        return token;
    }





    /*
     * verificar que la contraseña sea correcta 
     * 
     * @param empleadoEncontrado - representa la entidad encontrada en la base de datos
     * @param password - la contraseña que sera validada con la real
     * 
     * @return - son iguales(true) o diferentes(false) 
     */
    private boolean verificarPassword(Empleado empleadoEncontrado,String password) {
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        if(passwordEncoder.matches(password, empleadoEncontrado.getPassword())){
            return true;
        }else{
            return false;
        }
    }


    


}
