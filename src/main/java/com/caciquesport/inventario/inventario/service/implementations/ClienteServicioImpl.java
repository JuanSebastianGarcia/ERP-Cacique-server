package com.caciquesport.inventario.inventario.service.implementations;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.caciquesport.inventario.inventario.dto.ClienteDto;
import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.repository.ClienteRepository;
import com.caciquesport.inventario.inventario.service.interfaces.ClienteServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServicioImpl implements ClienteServicio{

    /*
     * Respositorios necesarios para el servicio
     */
    private final ClienteRepository clienteRepository;


    /*
     * Comenzar con la creacion de un cliente. se valdia si el usuario existe o no, y finaliza retornando el cliente
     * 
     * @param nuevoClienteDto - datos a registrar del cliente
     * @return ClienteDto- datos del cliente registrado
     */
    @Override
    public ClienteDto crearCliente(ClienteDto nuevoClienteDto)throws Exception  {
        
        boolean clienteExistente=verificarExistenciaCliente(nuevoClienteDto.cedula());

        if(clienteExistente==false){
            registrarEmpleado(nuevoClienteDto);
        }

        Cliente cliente = clienteRepository.findById(nuevoClienteDto.cedula()).get();

        return convertirClienteDto(cliente);
    }



    /*
     * Buscar un cliente por medio de la cedula 
     * 
     * @param cedula - codigo unico de un cliente
     * @return clienteDto informacion
     */
    @Override
    public ClienteDto buscarCliente(String cedula) throws Exception {
        
        Optional<Cliente> clienteEncontrado=clienteRepository.findById(cedula);

        if(clienteEncontrado.isEmpty()){
            throw new Exception("el cliente no existe");
        }

        return convertirClienteDto(clienteEncontrado.get());
    }



    /*
     * Actualizar los datos de un cliente
     * @clienteDto - los nuevos datos que seran persistidos
     * @return - mensaje de confirmacion
     */
    @Override
    public String actualizarCliente(ClienteDto clienteDto) {

        Cliente cliente= new Cliente();

        cliente.setCedula(clienteDto.cedula());
        cliente.setDireccion(clienteDto.direccion());
        cliente.setEmail(clienteDto.email());
        cliente.setNombre(clienteDto.nombre());
        cliente.setTelefono(clienteDto.telefono());

        clienteRepository.save(cliente);

        return "cliente actualizado";
    }



    /*
     * verificar si un cliente existe
     * @param cedula - numero de identificacion unico de un cliente
     * @return true - el cliente existe
     * @return false - el cliente no existe
     */
    private boolean verificarExistenciaCliente(String cedula){

        boolean respuesta=false;

        if(!clienteRepository.findById(cedula).isEmpty()){
            respuesta=true;
        }

        return respuesta;
    }

    
    /*
     * Crea un objeto de tipo cliente para ser persistido en la base de datos
     * 
     * @param nuevoClienteDto - datos del cliente para ser registrados
     */
    private void registrarEmpleado(ClienteDto nuevoClienteDto) {
        
        Cliente cliente= new Cliente();

        cliente.setCedula(nuevoClienteDto.cedula());
        cliente.setDireccion(nuevoClienteDto.direccion());
        cliente.setEmail(nuevoClienteDto.email());
        cliente.setNombre(nuevoClienteDto.nombre());
        cliente.setTelefono(nuevoClienteDto.telefono());

        clienteRepository.save(cliente);
    }


    /*
     *convertir un objeto de tipo cliente a un clienteDto
     *@param cliente - objeto de tipo cliente que sera convertido
     *@return clienteDto - objeto de tipo clienteDto 
     */
    private ClienteDto convertirClienteDto(Cliente cliente) {
        
        return new ClienteDto(cliente.getCedula(),cliente.getNombre(), cliente.getTelefono(), 
        cliente.getEmail(), cliente.getDireccion());
    }

}
