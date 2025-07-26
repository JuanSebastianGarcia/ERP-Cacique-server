package com.caciquesport.inventario.inventario.service.implementations;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.caciquesport.inventario.inventario.dto.EstadisticasDto;
import com.caciquesport.inventario.inventario.dto.GastoDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;
import com.caciquesport.inventario.inventario.model.entity.Gasto;
import com.caciquesport.inventario.inventario.repository.GastoRepositorio;
import com.caciquesport.inventario.inventario.repository.TipoGastoRepository;
import com.caciquesport.inventario.inventario.service.interfaces.GastoServicio;

/**
 * Implementation of the GastoServicio interface for managing expense operations.
 * This service handles all business logic related to expense management including
 * creation, retrieval, updates, and deletion of expense records.
 * 
 * All database operations are transactional to ensure data consistency.
 * Uses Spring's dependency injection for repository access.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GastoServicioImpl implements GastoServicio {

    // Repository for expense entity database operations
    private final GastoRepositorio gastoRepositorio;

    // Repository for expense type configuration operations
    private final TipoGastoRepository tipoGastoRepositorio;

    /**
     * Creates a new expense record in the database.
     * TODO: This method needs to be completed - currently it saves an empty Gasto object
     * without populating it with data from the DTO.
     * 
     * @param gastoDto The expense data transfer object containing expense information
     * @return Success message indicating the expense was created
     * @throws Exception If validation fails or database operation fails
     */
    @Override
    public String crearGasto(GastoDto gastoDto) throws Exception{

        // Create new expense entity
        Gasto gasto = new Gasto();
        
        gasto.setValor(gastoDto.valor());
        gasto.setDescripcion(gastoDto.descripcion());
        gasto.setFecha(gastoDto.fecha());
        gasto.setTipoGasto(tipoGastoRepositorio.findById(gastoDto.tipoGastoId()).orElseThrow());

        // Save the expense to database
        this.gastoRepositorio.save(gasto);
        
        return "Gasto registrado";
    }

    /**
     * Searches for expenses based on optional filter criteria.
     * Supports filtering by date, expense type, both, or neither (returns all).
     * 
     * @param fecha Optional date filter - if provided, only expenses from this date are returned
     * @param tipoGastoId Optional expense type ID filter - if provided, only expenses of this type are returned
     * @return List of expense DTOs matching the search criteria
     * @throws Exception If database operation fails or expense type is not found
     */
    @Override
    public List<GastoDto> buscarGastos(Date fecha, Integer tipoGastoId) throws Exception {
        
        List<Gasto> gastos = new ArrayList<>();

        // Search without any filters - return all expenses
        if (fecha == null && tipoGastoId == null) {
            gastos = this.gastoRepositorio.findAll();
        }
        
        // Search by date only
        if (fecha != null && tipoGastoId == null) {
            gastos = this.gastoRepositorio.findByFecha(fecha);
        }

        // Search by expense type only
        if (fecha == null && tipoGastoId != null) {
            Optional<TipoGasto> tipoGasto = this.tipoGastoRepositorio.findById(tipoGastoId);
            gastos = this.gastoRepositorio.findByTipoGasto(tipoGasto.get());
        }

        // Search by both date and expense type
        if (fecha != null && tipoGastoId != null) {
            Optional<TipoGasto> tipoGasto = this.tipoGastoRepositorio.findById(tipoGastoId);
            gastos = this.gastoRepositorio.findByTipoGastoAndFecha(tipoGasto.get(), fecha);
        }

        // Convert entity list to DTO list for response
        return this.mapGastosToDto(gastos);
    }

    /**
     * Updates an existing expense record with new information.
     * All fields from the DTO are used to update the expense entity.
     * 
     * @param id The unique identifier of the expense to update
     * @param gastoDto The updated expense data
     * @return Success message indicating the expense was updated
     * @throws Exception If expense is not found or database operation fails
     */
    @Override
    public String actualizarGasto(Integer id, GastoDto gastoDto) throws Exception {
        
        // Find the existing expense by ID
        Optional<Gasto> gasto = this.gastoRepositorio.findById(id);

        if (gasto.isPresent()) {
            // Update all expense fields with new data
            gasto.get().setValor(gastoDto.valor());
            gasto.get().setDescripcion(gastoDto.descripcion());
            gasto.get().setFecha(gastoDto.fecha());
            
            // Update expense type relationship
            gasto.get().setTipoGasto(this.tipoGastoRepositorio.findById(gastoDto.tipoGastoId()).get());
            
            // Save the updated expense
            this.gastoRepositorio.save(gasto.get());
            return "Gasto actualizado";
        }
        else{
            throw new Exception("Gasto no encontrado");
        }
    }

    /**
     * Permanently deletes an expense record from the database.
     * 
     * @param id The unique identifier of the expense to delete
     * @return Success message indicating the expense was deleted
     * @throws Exception If expense is not found or database operation fails
     */
    @Override
    public String eliminarGasto(Integer id) throws Exception {
        
        // Find the expense to delete
        Optional<Gasto> gasto = this.gastoRepositorio.findById(id);

        if (gasto.isPresent()) {
            // Remove the expense from database
            this.gastoRepositorio.delete(gasto.get());
            return "Gasto eliminado";
        }
        else{
            throw new Exception("Gasto no encontrado");
        }
    }

    /**
     * Private helper method to convert a list of Gasto entities to GastoDto objects.
     * This mapping is necessary to decouple the internal entity structure from the API response.
     * 
     * @param gastos List of Gasto entities from database
     * @return List of GastoDto objects for API response
     */
    private List<GastoDto> mapGastosToDto(List<Gasto> gastos) {
     
        List<GastoDto> gastosDto = new ArrayList<>();

        // Convert each entity to its corresponding DTO
        for (Gasto gasto : gastos) {
            gastosDto.add(new GastoDto(
                gasto.getId(), 
                gasto.getValor(), 
                gasto.getDescripcion(), 
                gasto.getFecha(), 
                gasto.getTipoGasto().getId()
            ));
        }

        return gastosDto;
    }

    /**
     * Retrieves statistical information about expenses, including:
     * - Total expenses for today
     * - Total expenses for the current month
     * - Total number of expenses for today
     *
     * @return EstadisticasDto containing the calculated statistics
     */
    @Override
    public EstadisticasDto obtenerEstadisticas() {
        
        double totalGastosHoy = this.obtenerTotalGastosHoy();
        double totalGastosMes = this.obtenerTotalGastosMes();
        int totalNumeroGastos = this.obtenerTotalNumeroGastos();

        return new EstadisticasDto(totalGastosHoy, totalGastosMes, totalNumeroGastos);
    }

    /**
     * Calculates the total amount of expenses for the current month.
     *
     * @return The sum of all expenses for the current month
     */
    private double obtenerTotalGastosMes(){
    
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate inicioSiguienteMes = inicioMes.plusMonths(1);
    
        Date inicio = Date.from(inicioMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(inicioSiguienteMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
    
        List<Gasto> gastos = gastoRepositorio.findByFechaBetweenDates(inicio, fin);
        return gastos.stream().mapToDouble(Gasto::getValor).sum();

    }

    /**
     * Calculates the total amount of expenses for today.
     *
     * @return The sum of all expenses for today
     */
    private double obtenerTotalGastosHoy() {
        LocalDate hoy = LocalDate.now();
        ZoneId zona = ZoneId.systemDefault();
    
        Date inicio = Date.from(hoy.atStartOfDay(zona).toInstant());
        Date fin = Date.from(hoy.plusDays(1).atStartOfDay(zona).toInstant());
    
        List<Gasto> gastos = gastoRepositorio.findByFechaBetweenDates(inicio, fin);
        return gastos.stream().mapToDouble(Gasto::getValor).sum();
    }
    

    /**
     * Calculates the total number of expenses for today.
     *
     * @return The count of expenses for today
     */
    private int obtenerTotalNumeroGastos(){
        LocalDate hoy = LocalDate.now();
        ZoneId zona = ZoneId.systemDefault();
    
        Date inicio = Date.from(hoy.atStartOfDay(zona).toInstant());
        Date fin = Date.from(hoy.plusDays(1).atStartOfDay(zona).toInstant());
    
        List<Gasto> gastos = gastoRepositorio.findByFechaBetweenDates(inicio, fin);
        return gastos.size();
    }
}
