package com.caciquesport.inventario.inventario.service.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.model.entity.Gasto;
import com.caciquesport.inventario.inventario.model.entity.ProductoFactura;
import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;
import com.caciquesport.inventario.inventario.repository.FacturaRepository;
import com.caciquesport.inventario.inventario.repository.GastoRepositorio;
import com.caciquesport.inventario.inventario.service.interfaces.EstadisticaServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EstadisticaServicioImpl implements EstadisticaServicio {

    /*
     * Repositorios
     */
    private final FacturaRepository facturaRepository;
    private final GastoRepositorio gastoRepository;


    @Override
    public IndicadoresMensualesDto obtenerIndicadoresMensuales() {


        // Obtener las facturas del mes actual y anterior
        List<Factura> facturasMesActual = obtenerFacturasMesActual();

        // Obtener los gastos del mes actual y anterior
        List<Gasto> gastosMesActual = obtenerGastosMesActual();

        //realizar los calculos para los indicadores
        calcularIndicadoresMensuales(facturasMesActual, gastosMesActual);


        return null;
    }

    /*
     * Calcular los indicadores mensuales. Los calculos que se realizan son:
     * - Ingresos totales del mes actual 
     * - Gastos totales del mes actual 
     * - Utilidad neta del mes actual 
     * - total de productos vendidos del mes actual 
     */
    private IndicadoresMensualesDto calcularIndicadoresMensuales(
        List<Factura> facturasMesActual,
        List<Gasto> gastosMesActual) 
    {

        double ingresosTotalesMesActual = calcularIngresosTotales(facturasMesActual);

        double gastosTotalesMesActual = calcularGastosTotales(gastosMesActual);

        double utilidadesNetas = ingresosTotalesMesActual - gastosTotalesMesActual;

        int totalProductosVendidos = calcularTotalProductosVendidos(facturasMesActual);


        return new IndicadoresMensualesDto(ingresosTotalesMesActual, gastosTotalesMesActual, utilidadesNetas, totalProductosVendidos);
    }



    /*
     * Calcular el total de productos vendidos del mes actual
     */
    private int calcularTotalProductosVendidos(List<Factura> facturasMesActual) {
    
        int totalProductosVendidos = 0;

        // Calcular el ingreso total del mes actual
        for (Factura factura : facturasMesActual) {
            for (ProductoFactura productoFactura : factura.getListaProductosFactura()) {
                if (productoFactura.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {
                    totalProductosVendidos += 1;
                }
            }
        }

        return totalProductosVendidos;
    }



    /*
     * Calcular los gastos totales del mes actual
     */
    private double calcularGastosTotales(List<Gasto> gastosMesActual) {
        double sumaTotalMesActual = 0;

        for (Gasto gasto : gastosMesActual) {
            sumaTotalMesActual += gasto.getValor();
        }

        return sumaTotalMesActual;
    }



    /*
     * Calcular los ingresos totales del mes actual
     */
    private double calcularIngresosTotales(List<Factura> facturasMesActual) {
    
        double sumaTotalMesActual = 0;

        // Calcular el ingreso total del mes actual
        for (Factura factura : facturasMesActual) {
            for (ProductoFactura productoFactura : factura.getListaProductosFactura()) {
                if (productoFactura.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {
                    sumaTotalMesActual += productoFactura.getProducto().getDetalleProducto().getPrecio();
                }
            }
        }

        return sumaTotalMesActual;
    }


    
    /*
     * Obtener los gastos del mes actual
     */
    private List<Gasto> obtenerGastosMesActual() {
        LocalDate ahora = LocalDate.now();

        // Mes actual
        int mesActual = ahora.getMonthValue();
        int añoActual = ahora.getYear();

        // Consultas
        List<Gasto> gastosMesActual = gastoRepository.findByYearAndMonth(añoActual, mesActual);

        return gastosMesActual;
    }



    /*
     * Obtener las facturas del mes actual
     */
    private List<Factura> obtenerFacturasMesActual() {
        LocalDate ahora = LocalDate.now();

        // Mes actual
        int mesActual = ahora.getMonthValue();
        int añoActual = ahora.getYear();

        // Consultas
        List<Factura> facturasMesActual = facturaRepository.findByYearAndMonth(añoActual, mesActual);

        return facturasMesActual;
    }



}
