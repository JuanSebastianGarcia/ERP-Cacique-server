package com.caciquesport.inventario.inventario.service.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.HitoricoIndicadoresDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaAnualDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaDiariaDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaMensualDto;
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


        // Obtener las facturas del mes actual
        List<Factura> facturasMesActual = obtenerFacturasMesActual();

        // Obtener los gastos del mes actual 
        List<Gasto> gastosMesActual = obtenerGastosMesActual();

        //realizar los calculos para los indicadores
        IndicadoresMensualesDto indicadoresMensuales = calcularIndicadoresMensuales(facturasMesActual, gastosMesActual);


        return indicadoresMensuales;
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



    @Override
    public HitoricoIndicadoresDto ObtenerHistoricoIndicadores(String TipoIndicador) {

        switch (TipoIndicador) {
            case "INGRESOS":
                return obtenerHistoricoIngresos();
            case "GASTOS":
                return obtenerHistoricoGastos();
            case "UTILIDADES":
                return obtenerHistoricoUtilidades();
            default:
                throw new IllegalArgumentException("Tipo de indicador no válido");
        }
    }


    /*
     * Obtener los ingresos del mes actual
     */
    private HitoricoIndicadoresDto obtenerHistoricoIngresos() {

        // Obtener las facturas
        List<Factura> facturas = obtenerTodasLasFacturas();

        //obtener ingresos diarios
        Map<String, Double> ingresosDiarios = obtenerHistoricoIngresosDiarios(facturas);

        //obtener ingresos mensuales
        Map<String, Double> ingresosMensuales = obtenerHistoricoIngresosMensuales(facturas);

        //obtener ingresos anuales
        Map<String, Double> ingresosAnuales = obtenerHistoricoIngresosAnuales(facturas);

        return mapearHistoricoIngresos(ingresosDiarios, ingresosMensuales, ingresosAnuales);
    }

    

    /*
     * Esta funcion mapea un objeto con los ingresos diarios, mensuales y anuales en el dto HitoricoIndicadoresDto
     * 
     * @param ingresosDiarios: ingresos diarios
     * @param ingresosMensuales: ingresos mensuales
     * @param ingresosAnuales: ingresos anuales
     * @return objeto con los ingresos diarios, mensuales y anuales
     */
    private HitoricoIndicadoresDto mapearHistoricoIngresos(Map<String, Double> ingresosDiarios, Map<String, Double> ingresosMensuales, Map<String, Double> ingresosAnuales) {
    
        //inicializar las graficas
        List<GraficaDiariaDto> listaDiaria = new ArrayList<>();
        List<GraficaMensualDto> listaMensual = new ArrayList<>();
        List<GraficaAnualDto> listaAnual = new ArrayList<>();

        // Recorrer el mapa diario y llenar la lista
        for (Map.Entry<String, Double> entry : ingresosDiarios.entrySet()) {
            listaDiaria.add(new GraficaDiariaDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa mensual y llenar la lista
        for (Map.Entry<String, Double> entry : ingresosMensuales.entrySet()) {
            listaMensual.add(new GraficaMensualDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa anual y llenar la lista
        for (Map.Entry<String, Double> entry : ingresosAnuales.entrySet()) {
            listaAnual.add(new GraficaAnualDto(entry.getKey(), entry.getValue()));
        }
                
        return new HitoricoIndicadoresDto(listaDiaria, listaMensual, listaAnual);
    }

    /*
     * Obtener los ingresos anuales y su respectivo año durante 5 años anteriores al año actual
     * 
     * @param facturas: lista de facturas
     * @return mapa con los ingresos anuales y su respectivo año
     */
    private Map<String, Double> obtenerHistoricoIngresosAnuales(List<Factura> facturas) {
        // Obtener los ingresos
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "years");

        Map<String, Double> facturasPorFecha = new HashMap<>();

        for (Factura factura : facturasFiltradas) {
            List<Factura> facturasDelDia = new ArrayList<>();
            facturasDelDia.add(factura);
            facturasPorFecha.merge(
                String.valueOf(factura.getFechaFactura().getYear()),
                calcularIngresosTotales(facturasDelDia),
                Double::sum
            );
        }
        
        return facturasPorFecha;
    }



    /*
     * Esta funcion devuelve un arreglo con los ingresos mensuales y su respectivo mes durante
     * 12 meses anteriores al mes actual
     * 
     * @param facturas: lista de facturas
     * @return mapa con los ingresos mensuales y su respectivo mes
     */
    private Map<String, Double> obtenerHistoricoIngresosMensuales(List<Factura> facturas) {

        // Obtener los ingresos
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "months");

        Map<String, Double> facturasPorFecha = new HashMap<>();

        for (Factura factura : facturasFiltradas) {
            List<Factura> facturasDelDia = new ArrayList<>();
            facturasDelDia.add(factura);
            facturasPorFecha.merge(
                factura.getFechaFactura().getMonth().toString(),
                calcularIngresosTotales(facturasDelDia),
                Double::sum
            );
        }
        
        return facturasPorFecha;
    }



    /*
     * Esta funcion devuelve un arreglo con los ingresos diarios y su respectivo dia durante
     * 30 dias anteriores al dia actual
     * 
     * @param facturas: lista de facturas
     * @return mapa con los ingresos diarios y su respectivo dia
     */
    private Map<String, Double> obtenerHistoricoIngresosDiarios(List<Factura> facturas) {

        
        // Obtener los ingresos
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "days");

        Map<String, Double> facturasPorFecha = new HashMap<>();

        for (Factura factura : facturasFiltradas) {
            List<Factura> facturasDelDia = new ArrayList<>();
            facturasDelDia.add(factura);
            facturasPorFecha.merge(
                factura.getFechaFactura().toString(),
                calcularIngresosTotales(facturasDelDia),
                Double::sum
            );
        }
        
        return facturasPorFecha;
    }




    /*
     * Filtrar las facturas por la fecha y el tipo de fecha
     * 
     * @param facturas: lista de facturas
     * @param tipoFecha: tipo de fecha a filtrar (days, months, years)
     * @return lista de facturas filtradas
     */
    private List<Factura> filtrarFacturas(List<Factura> facturas, String tipoFecha) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicial;

        //se selecciona el rango inicial de la fecha
        if (tipoFecha.equals("days")) {
            fechaInicial = hoy.minusDays(30);
        } else if (tipoFecha.equals("months")) {
            fechaInicial = hoy.minusMonths(12);
        } else if (tipoFecha.equals("years")) {
            fechaInicial = hoy.minusYears(5);
        } else {
            throw new IllegalArgumentException("Tipo de fecha no válido");
        }
    
        return facturas.stream()
                .filter(f -> (f.getFechaFactura().isEqual(hoy) || f.getFechaFactura().isBefore(hoy)) 
                        && (f.getFechaFactura().isAfter(fechaInicial) || f.getFechaFactura().isEqual(fechaInicial)))
                .toList(); 
    }






    /*
     * Obtener todas las facturas
     */
    private List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }



    /*
     * Obtener los gastos del mes actual
     */
    private HitoricoIndicadoresDto obtenerHistoricoGastos() {

        // Obtener los gastos
        List<Gasto> gastos = obtenerTodosLosGastos();

        //obtener gastos diarios
        Map<String, Double> gastosDiarios = obtenerHistoricoGastosDiarios(gastos);

        //obtener gastos mensuales
        Map<String, Double> gastosMensuales = obtenerHistoricoGastosMensuales(gastos);

        //obtener gastos anuales
        Map<String, Double> gastosAnuales = obtenerHistoricoGastosAnuales(gastos);

        return obtenerHistoricoGastos(gastosDiarios, gastosMensuales, gastosAnuales);
    }

    /*
     * Esta funcion mapea un objeto con los gastos diarios, mensuales y anuales en el dto HitoricoIndicadoresDto
     * 
     * @param gastosDiarios: gastos diarios
     * @param gastosMensuales: gastos mensuales
     * @param gastosAnuales: gastos anuales
     * @return objeto con los gastos diarios, mensuales y anuales
     */
    private HitoricoIndicadoresDto obtenerHistoricoGastos(Map<String, Double> gastosDiarios, Map<String, Double> gastosMensuales, Map<String, Double> gastosAnuales) {
    
        //inicializar las graficas
        List<GraficaDiariaDto> listaDiaria = new ArrayList<>();
        List<GraficaMensualDto> listaMensual = new ArrayList<>();
        List<GraficaAnualDto> listaAnual = new ArrayList<>();

        // Recorrer el mapa diario y llenar la lista
        for (Map.Entry<String, Double> entry : gastosDiarios.entrySet()) {
            listaDiaria.add(new GraficaDiariaDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa mensual y llenar la lista
        for (Map.Entry<String, Double> entry : gastosMensuales.entrySet()) {
            listaMensual.add(new GraficaMensualDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa anual y llenar la lista
        for (Map.Entry<String, Double> entry : gastosAnuales.entrySet()) {
            listaAnual.add(new GraficaAnualDto(entry.getKey(), entry.getValue()));
        }
                
        return new HitoricoIndicadoresDto(listaDiaria, listaMensual, listaAnual);
    }


    /*
     * Obtener los gastos anuales y su respectivo año durante 5 años anteriores al año actual
     * 
     * @param gastos: lista de gastos
     * @return mapa con los gastos anuales y su respectivo año
     */
    private Map<String, Double> obtenerHistoricoGastosAnuales(List<Gasto> gastos) {
        // Obtener los gastos
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "years");

        Map<String, Double> gastosPorFecha = new HashMap<>();

        for (Gasto gasto : gastosFiltrados) {
            List<Gasto> gastosDelDia = new ArrayList<>();
            gastosDelDia.add(gasto);
            gastosPorFecha.merge(
                String.valueOf(gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().getYear()),
                calcularGastosTotales(gastosDelDia),
                Double::sum
            );
        }
        
        return gastosPorFecha;
    }

    /*
     * Esta funcion devuelve un arreglo con los gastos mensuales y su respectivo mes durante
     * 12 meses anteriores al mes actual
     * 
     * @param gastos: lista de gastos
     * @return mapa con los gastos mensuales y su respectivo mes
     */
    private Map<String, Double> obtenerHistoricoGastosMensuales(List<Gasto> gastos) {

        // Obtener los gastos
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "months");

        Map<String, Double> gastosPorFecha = new HashMap<>();

        for (Gasto gasto : gastosFiltrados) {
            List<Gasto> gastosDelDia = new ArrayList<>();
            gastosDelDia.add(gasto);
            gastosPorFecha.merge(
                String.valueOf(gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().getMonth()),
                calcularGastosTotales(gastosDelDia),
                Double::sum
            );
        }
        
        return gastosPorFecha;
    }

    /*
     * Esta funcion devuelve un arreglo con los gastos diarios y su respectivo dia durante
     * 30 dias anteriores al dia actual
     * 
     * @param gastos: lista de gastos
     * @return mapa con los gastos diarios y su respectivo dia
     */
    private Map<String, Double> obtenerHistoricoGastosDiarios(List<Gasto> gastos) {

        
        // Obtener los gastos
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "days");

        Map<String, Double> gastosPorFecha = new HashMap<>();

        for (Gasto gasto : gastosFiltrados) {
            List<Gasto> gastosDelDia = new ArrayList<>();
            gastosDelDia.add(gasto);
            gastosPorFecha.merge(
                gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().toString(),
                calcularGastosTotales(gastosDelDia),
                Double::sum
            );
        }
        
        return gastosPorFecha;
    }

    /*
     * Filtrar los gastos por la fecha y el tipo de fecha
     * 
     * @param gastos: lista de gastos
     * @param tipoFecha: tipo de fecha a filtrar (days, months, years)
     * @return lista de gastos filtrados
     */
    private List<Gasto> filtrarGastos(List<Gasto> gastos, String tipoFecha) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicial;

        //se selecciona el rango inicial de la fecha
        if (tipoFecha.equals("days")) {
            fechaInicial = hoy.minusDays(30);
        } else if (tipoFecha.equals("months")) {
            fechaInicial = hoy.minusMonths(12);
        } else if (tipoFecha.equals("years")) {
            fechaInicial = hoy.minusYears(5);
        } else {
            throw new IllegalArgumentException("Tipo de fecha no válido");
        }
    
        return gastos.stream()
                .filter(g -> (g.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().isEqual(hoy) || g.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().isBefore(hoy)) 
                        && (g.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().isAfter(fechaInicial) || g.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().isEqual(fechaInicial)))
                .toList(); 
    }

    /*
     * Obtener todos los gastos
     */
    private List<Gasto> obtenerTodosLosGastos() {
        return gastoRepository.findAll();
    }


    /*
     * Obtener las utilidades del mes actual
     */
    private HitoricoIndicadoresDto obtenerHistoricoUtilidades() {

        // Obtener las facturas y gastos
        List<Factura> facturas = obtenerTodasLasFacturas();
        List<Gasto> gastos = obtenerTodosLosGastos();

        //obtener utilidades diarias
        Map<String, Double> utilidadesDiarias = obtenerHistoricoUtilidadesDiarias(facturas, gastos);

        //obtener utilidades mensuales
        Map<String, Double> utilidadesMensuales = obtenerHistoricoUtilidadesMensuales(facturas, gastos);

        //obtener utilidades anuales
        Map<String, Double> utilidadesAnuales = obtenerHistoricoUtilidadesAnuales(facturas, gastos);

        return mapearHistoricoUtilidades(utilidadesDiarias, utilidadesMensuales, utilidadesAnuales);
    }

    /*
     * Esta funcion mapea un objeto con las utilidades diarias, mensuales y anuales en el dto HitoricoIndicadoresDto
     * 
     * @param utilidadesDiarias: utilidades diarias
     * @param utilidadesMensuales: utilidades mensuales
     * @param utilidadesAnuales: utilidades anuales
     * @return objeto con las utilidades diarias, mensuales y anuales
     */
    private HitoricoIndicadoresDto mapearHistoricoUtilidades(Map<String, Double> utilidadesDiarias, Map<String, Double> utilidadesMensuales, Map<String, Double> utilidadesAnuales) {
    
        //inicializar las graficas
        List<GraficaDiariaDto> listaDiaria = new ArrayList<>();
        List<GraficaMensualDto> listaMensual = new ArrayList<>();
        List<GraficaAnualDto> listaAnual = new ArrayList<>();

        // Recorrer el mapa diario y llenar la lista
        for (Map.Entry<String, Double> entry : utilidadesDiarias.entrySet()) {
            listaDiaria.add(new GraficaDiariaDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa mensual y llenar la lista
        for (Map.Entry<String, Double> entry : utilidadesMensuales.entrySet()) {
            listaMensual.add(new GraficaMensualDto(entry.getKey(), entry.getValue()));
        }

        // Recorrer el mapa anual y llenar la lista
        for (Map.Entry<String, Double> entry : utilidadesAnuales.entrySet()) {
            listaAnual.add(new GraficaAnualDto(entry.getKey(), entry.getValue()));
        }
                
        return new HitoricoIndicadoresDto(listaDiaria, listaMensual, listaAnual);
    }

    /*
     * Obtener las utilidades anuales y su respectivo año durante 5 años anteriores al año actual
     * 
     * @param facturas: lista de facturas
     * @param gastos: lista de gastos
     * @return mapa con las utilidades anuales y su respectivo año
     */
    private Map<String, Double> obtenerHistoricoUtilidadesAnuales(List<Factura> facturas, List<Gasto> gastos) {
        // Obtener las facturas y gastos filtrados
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "years");
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "years");

        Map<String, Double> utilidadesPorFecha = new HashMap<>();

        // Procesar facturas por año
        for (Factura factura : facturasFiltradas) {
            String año = String.valueOf(factura.getFechaFactura().getYear());
            List<Factura> facturasDelAño = new ArrayList<>();
            facturasDelAño.add(factura);
            
            double ingresosDelAño = calcularIngresosTotales(facturasDelAño);
            utilidadesPorFecha.merge(año, ingresosDelAño, Double::sum);
        }

        // Restar gastos por año
        for (Gasto gasto : gastosFiltrados) {
            String año = String.valueOf(gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().getYear());
            List<Gasto> gastosDelAñoList = new ArrayList<>();
            gastosDelAñoList.add(gasto);
            
            double gastosDelAñoValor = calcularGastosTotales(gastosDelAñoList);
            utilidadesPorFecha.merge(año, -gastosDelAñoValor, Double::sum);
        }
        
        return utilidadesPorFecha;
    }

    /*
     * Esta funcion devuelve un arreglo con las utilidades mensuales y su respectivo mes durante
     * 12 meses anteriores al mes actual
     * 
     * @param facturas: lista de facturas
     * @param gastos: lista de gastos
     * @return mapa con las utilidades mensuales y su respectivo mes
     */
    private Map<String, Double> obtenerHistoricoUtilidadesMensuales(List<Factura> facturas, List<Gasto> gastos) {

        // Obtener las facturas y gastos filtrados
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "months");
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "months");

        Map<String, Double> utilidadesPorFecha = new HashMap<>();

        // Procesar facturas por mes
        for (Factura factura : facturasFiltradas) {
            String mes = factura.getFechaFactura().getMonth().toString();
            List<Factura> facturasDelMes = new ArrayList<>();
            facturasDelMes.add(factura);
            
            double ingresosDelMes = calcularIngresosTotales(facturasDelMes);
            utilidadesPorFecha.merge(mes, ingresosDelMes, Double::sum);
        }

        // Restar gastos por mes
        for (Gasto gasto : gastosFiltrados) {
            String mes = String.valueOf(gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().getMonth());
            List<Gasto> gastosDelMesList = new ArrayList<>();
            gastosDelMesList.add(gasto);
            
            double gastosDelMesValor = calcularGastosTotales(gastosDelMesList);
            utilidadesPorFecha.merge(mes, -gastosDelMesValor, Double::sum);
        }
        
        return utilidadesPorFecha;
    }

    /*
     * Esta funcion devuelve un arreglo con las utilidades diarias y su respectivo dia durante
     * 30 dias anteriores al dia actual
     * 
     * @param facturas: lista de facturas
     * @param gastos: lista de gastos
     * @return mapa con las utilidades diarias y su respectivo dia
     */
    private Map<String, Double> obtenerHistoricoUtilidadesDiarias(List<Factura> facturas, List<Gasto> gastos) {

        // Obtener las facturas y gastos filtrados
        List<Factura> facturasFiltradas = filtrarFacturas(facturas, "days");
        List<Gasto> gastosFiltrados = filtrarGastos(gastos, "days");

        Map<String, Double> utilidadesPorFecha = new HashMap<>();

        // Procesar facturas por dia
        for (Factura factura : facturasFiltradas) {
            String dia = factura.getFechaFactura().toString();
            List<Factura> facturasDelDia = new ArrayList<>();
            facturasDelDia.add(factura);
            
            double ingresosDelDia = calcularIngresosTotales(facturasDelDia);
            utilidadesPorFecha.merge(dia, ingresosDelDia, Double::sum);
        }

        // Restar gastos por dia
        for (Gasto gasto : gastosFiltrados) {
            String dia = gasto.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().toString();
            List<Gasto> gastosDelDiaList = new ArrayList<>();
            gastosDelDiaList.add(gasto);
            
            double gastosDelDiaValor = calcularGastosTotales(gastosDelDiaList);
            utilidadesPorFecha.merge(dia, -gastosDelDiaValor, Double::sum);
        }
        
        return utilidadesPorFecha;
    }


}
