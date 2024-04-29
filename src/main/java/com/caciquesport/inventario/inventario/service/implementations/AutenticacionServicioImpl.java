package com.caciquesport.inventario.inventario.service.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.LoginDto;
import com.caciquesport.inventario.inventario.dto.TokenDto;
import com.caciquesport.inventario.inventario.model.entity.Empleado;
import com.caciquesport.inventario.inventario.repository.EmpleadoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.AutenticacionServicio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio{

    private final EmpleadoRepository empleadoRepository;


    /*
     * Verificar la existencia de un usuario verificando el email y la contraseña
     * 
     * @param loginDto - objeto que contiene la informacion para el login (email y contraseña)
     */
    @Override
    public TokenDto verificarIdentidad(LoginDto loginDto)throws Exception {
        
        Optional<Empleado> empleadoEncontrado=empleadoRepository.findByEmail(loginDto.email());

        if(empleadoEncontrado.isEmpty() &&  verificarPassword(empleadoEncontrado.get())){
            throw new Exception("el correo no esta registrado");
        }else{
           
        }
    }


    /*
     * verificar que la contraseña sea correcta
     */
    private boolean verificarPassword(Empleado empleadoEncontrado) {
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
    }


    


}
