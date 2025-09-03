package com.caciquesport.inventario.inventario.service.implementations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.AnalisisVentasAgrupadasDto;
import com.caciquesport.inventario.inventario.dto.HitoricoIndicadoresDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresDiariosDto;
import com.caciquesport.inventario.inventario.dto.IndicadoresMensualesDto;
import com.caciquesport.inventario.inventario.dto.ListaRegistrosMovimientoDto;
import com.caciquesport.inventario.inventario.dto.RegistroMovimientoDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaAnualDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaDiariaDto;
import com.caciquesport.inventario.inventario.dto.graficas.GraficaMensualDto;
import com.caciquesport.inventario.inventario.dto.VentaAgrupadaDto;
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
        ZoneId zonaColombia = ZoneId.of("America/Bogota");

        // Primer día del mes
        LocalDate inicioMes = ahora.withDayOfMonth(1);

        // Primer día del mes siguiente (para usar como límite exclusivo)
        LocalDate inicioMesSiguiente = inicioMes.plusMonths(1);

        // Convertir a java.util.Date
        Date inicio = Date.from(inicioMes.atStartOfDay(zonaColombia).toInstant());
        Date fin = Date.from(inicioMesSiguiente.atStartOfDay(zonaColombia).toInstant());

        // Consultar por rango en el repositorio
        return gastoRepository.findByFechaBetweenDates(inicio, fin);
    }






    /*
    * Buscar las facturas que se registraron en el mes actual
    */
    private List<Factura> obtenerFacturasMesActual() {
        LocalDate ahora = LocalDate.now();

        // Primer día del mes
        LocalDate inicio = ahora.withDayOfMonth(1);

        // Último día del mes
        LocalDate fin = ahora.withDayOfMonth(ahora.lengthOfMonth());

        // Consultar por rango de fechas
        return facturaRepository.findByFechaBetween(inicio, fin);
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

    @Override
    public AnalisisVentasAgrupadasDto obtenerAnalisisVentasAgrupadas(String tipoDato) {
        
        // Get current year
        int añoActual = LocalDate.now().getYear();
        
        // Query invoices from current year using existing method
        List<Factura> facturasAñoActual = obtenerFacturasDelAño(añoActual);
        
        // Map to store accumulated income for each distinct value of the specified data type
        Map<String, Double> ingresosPorTipoDato = new HashMap<>();
        
        // Iterate through each invoice
        for (Factura factura : facturasAñoActual) {
            
            // Iterate through each product in the invoice
            for (ProductoFactura productoFactura : factura.getListaProductosFactura()) {
                
                // Only process delivered products
                if (productoFactura.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {
                    
                    // Get the product detail
                    String valorTipoDato = obtenerValorTipoDato(productoFactura, tipoDato);
                    double precioProducto = productoFactura.getProducto().getDetalleProducto().getPrecio();
                    
                    // Accumulate income for each distinct value of the specified data type
                    ingresosPorTipoDato.merge(valorTipoDato, precioProducto, Double::sum);
                }
            }
        }
        
        // Convert the map to the DTO format
        return mapearAnalisisVentasAgrupadas(ingresosPorTipoDato);
    }
    
    /**
     * Extracts the value of the specified data type from a product
     * 
     * @param productoFactura The product invoice to extract data from
     * @param tipoDato The type of data to extract (institucion, genero, prenda, talla, horario)
     * @return The value of the specified data type
     */
    private String obtenerValorTipoDato(ProductoFactura productoFactura, String tipoDato) {
        
        switch (tipoDato.toUpperCase()) {
            case "INSTITUCION":
                return productoFactura.getProducto().getTipoInstitucion().getInstitucion()  ;
            case "GENERO":
                return productoFactura.getProducto().getTipoGenero().getGenero();
            case "PRENDA":
                return productoFactura.getProducto().getTipoPrenda().getPrenda();
            case "TALLA":
                return productoFactura.getProducto().getTipoTalla().getTalla();
            case "HORARIO":
                return productoFactura.getProducto().getTipoHorario().getHorario();
            default:
                throw new IllegalArgumentException("Tipo de dato no válido: " + tipoDato);
        }
    }
    
    /**
     * Maps the accumulated income data to the AnalisisVentasAgrupadasDto format
     * 
     * @param ingresosPorTipoDato Map containing accumulated income for each distinct value
     * @return AnalisisVentasAgrupadasDto with the grouped sales analysis
     */
    private AnalisisVentasAgrupadasDto mapearAnalisisVentasAgrupadas(Map<String, Double> ingresosPorTipoDato) {
        
        // Create list to store the analysis data
        List<VentaAgrupadaDto> detalles = new ArrayList<>();
        
        // Convert map entries to the required format
        for (Map.Entry<String, Double> entry : ingresosPorTipoDato.entrySet()) {
            VentaAgrupadaDto ventaAgrupada = new VentaAgrupadaDto(entry.getKey(), entry.getValue());
            detalles.add(ventaAgrupada);
        }
        
        // Create and return the DTO
        return new AnalisisVentasAgrupadasDto(detalles);
    }
    
    /**
     * Gets invoices from a specific year by using the repository's date range query
     * This method is more efficient than fetching all invoices and filtering in memory
     * 
     * @param año The year to filter invoices
     * @return List of invoices from the specified year
     */
    private List<Factura> obtenerFacturasDelAño(int año) {
        // Define start and end dates for the year
        LocalDate fechaInicio = LocalDate.of(año, 1, 1);  // January 1st of the year
        LocalDate fechaFin = LocalDate.of(año, 12, 31);   // December 31st of the year
        
        // Use the repository method to query invoices within the date range
        return facturaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    @Override
    public IndicadoresDiariosDto obtenerKpisDiarios(String fecha) {
        
        // Parse the date parameter to LocalDate
        LocalDate fechaConsulta = LocalDate.parse(fecha);
        
        // Get invoices for the specific date
        List<Factura> facturasDelDia = obtenerFacturasDelDia(fechaConsulta);
        
        // Get expenses for the specific date
        List<Gasto> gastosDelDia = obtenerGastosDelDia(fechaConsulta);
        
        // Calculate KPIs
        double ingresosTotalesCaja = calcularIngresosTotalesDelDia(facturasDelDia, fechaConsulta);
        double gastosTotales = calcularGastosTotalesDelDia(gastosDelDia);
        int numeroFacturasEmitidas = facturasDelDia.size();
        
        // Create and return the DTO with calculated KPIs
        return new IndicadoresDiariosDto(ingresosTotalesCaja, gastosTotales, numeroFacturasEmitidas);
    }
    
    /**
     * Retrieves invoices issued on a specific date
     * 
     * @param fecha The specific date to query invoices for
     * @return List of invoices issued on the specified date
     */
    private List<Factura> obtenerFacturasDelDia(LocalDate fecha) {
        // Use the repository method to query invoices for the specific date
        return facturaRepository.findByFechaBetween(fecha, fecha);
    }
    
    /**
     * Obtiene los gastos de un día específico
     *
     * @param fecha Día a consultar
     * @return Lista de gastos registrados en la fecha indicada
     */
    private List<Gasto> obtenerGastosDelDia(LocalDate fecha) {
        // Convertir LocalDate a Date (inicio y fin del día)
        ZoneId zonaColombia = ZoneId.of("America/Bogota");

        Date inicio = Date.from(fecha.atStartOfDay(zonaColombia).toInstant());
        Date fin = Date.from(fecha.plusDays(1).atStartOfDay(zonaColombia).toInstant());

        // Consultar usando el repositorio
        return gastoRepository.findByFechaBetweenDates(inicio, fin);
    }

    
    /**
     * Calculates total income for a specific date by reviewing payment support
     * and summing all payments made on that date
     * 
     * @param facturasDelDia List of invoices for the specific date
     * @param fecha The specific date to calculate income for
     * @return Total income received on the specified date
     */
    private double calcularIngresosTotalesDelDia(List<Factura> facturasDelDia, LocalDate fecha) {
        double ingresosTotales = 0.0;
        
        // Colombia timezone
        java.time.ZoneId zonaColombia = java.time.ZoneId.of("America/Bogota");
        
        for (Factura factura : facturasDelDia) {
            // Check if the invoice has payment support
            if (factura.getSoportePago() != null && factura.getSoportePago().getListaPagos() != null) {
                
                // Iterate through all payments in the payment support
                for (Object pago : factura.getSoportePago().getListaPagos()) {
                    // Check if the payment was made on the specified date
                    if (esPagoDelDia(pago, fecha, zonaColombia)) {
                        // Add the payment amount to total income
                        ingresosTotales += obtenerMontoPago(pago);
                    }
                }
            }
        }
        
        return ingresosTotales;
    }
    
    /**
     * Checks if a payment was made on a specific date
     * 
     * @param pago The payment object to check
     * @param fecha The specific date to compare against
     * @param zonaColombia Colombia timezone
     * @return true if payment was made on the specified date, false otherwise
     */
    private boolean esPagoDelDia(Object pago, LocalDate fecha, java.time.ZoneId zonaColombia) {
        try {
            // Use reflection to get the payment date
            java.lang.reflect.Method getFechaMethod = pago.getClass().getMethod("getFecha");
            Object fechaPago = getFechaMethod.invoke(pago);
    
            if (fechaPago instanceof java.util.Date) {
                java.util.Date date = (java.util.Date) fechaPago;
                LocalDate fechaPagoLocal = date.toInstant().atZone(zonaColombia).toLocalDate();
                return fechaPagoLocal.equals(fecha);
            }
        } catch (Exception e) {
            // Log error or handle gracefully
            System.err.println("Error checking payment date: " + e.getMessage());
        }
        return false;
    }
    
    
    /**
     * Extracts the payment amount from a payment object
     * 
     * @param pago The payment object
     * @return The payment amount
     */
    private double obtenerMontoPago(Object pago) {
        try {
            // Use reflection to get the payment amount
            java.lang.reflect.Method getMontoMethod = pago.getClass().getMethod("getMonto");
            Object monto = getMontoMethod.invoke(pago);
            
            if (monto instanceof Number) {
                return ((Number) monto).doubleValue();
            }
        } catch (Exception e) {
            // Log error or handle gracefully
            System.err.println("Error getting payment amount: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Calculates total expenses for a specific date
     * 
     * @param gastosDelDia List of expenses for the specific date
     * @return Total expenses incurred on the specified date
     */
    private double calcularGastosTotalesDelDia(List<Gasto> gastosDelDia) {
        return gastosDelDia.stream()
                .mapToDouble(Gasto::getValor)
                .sum();
    }

    @Override
    public ListaRegistrosMovimientoDto obtenerRegistrosMovimientos(String fecha) {
        
        // Parse the date parameter to LocalDate
        LocalDate fechaConsulta = LocalDate.parse(fecha);
        
        // Get invoices and expenses for the specific date
        List<Factura> facturasDelDia = obtenerFacturasDelDia(fechaConsulta);
        List<Gasto> gastosDelDia = obtenerGastosDelDia(fechaConsulta);
        
        // Create movement records from expenses and income
        List<RegistroMovimientoDto> registros = new ArrayList<>();
        registros.addAll(crearRegistrosGastos(gastosDelDia, fechaConsulta));
        registros.addAll(crearRegistrosIngresos(facturasDelDia, fechaConsulta));
        
        // Create and return the DTO with all movement records
        return new ListaRegistrosMovimientoDto(registros);
    }
    
    /**
     * Creates movement records from expenses for a specific date
     * 
     * @param gastosDelDia List of expenses for the specific date
     * @param fechaConsulta The date to create records for
     * @return List of movement records for expenses
     */
    private List<RegistroMovimientoDto> crearRegistrosGastos(List<Gasto> gastosDelDia, LocalDate fechaConsulta) {
        List<RegistroMovimientoDto> registros = new ArrayList<>();
        
        for (Gasto gasto : gastosDelDia) {
            RegistroMovimientoDto registro = new RegistroMovimientoDto(
                fechaConsulta.toString(),
                "GASTO",
                gasto.getValor()
            );
            registros.add(registro);
        }
        
        return registros;
    }
    
    /**
     * Creates movement records from income (invoice payments) for a specific date
     * 
     * @param facturasDelDia List of invoices for the specific date
     * @param fechaConsulta The date to create records for
     * @return List of movement records for income
     */
    private List<RegistroMovimientoDto> crearRegistrosIngresos(List<Factura> facturasDelDia, LocalDate fechaConsulta) {
        List<RegistroMovimientoDto> registros = new ArrayList<>();
        
        for (Factura factura : facturasDelDia) {
            if (tieneSoportePago(factura)) {
                registros.addAll(procesarPagosFactura(factura, fechaConsulta));
            }
        }
        
        return registros;
    }
    
    /**
     * Checks if an invoice has payment support
     * 
     * @param factura The invoice to check
     * @return true if the invoice has payment support, false otherwise
     */
    private boolean tieneSoportePago(Factura factura) {
        return factura.getSoportePago() != null && factura.getSoportePago().getListaPagos() != null;
    }
    
    /**
     * Processes payments from an invoice and creates movement records for payments made on the specified date
     * 
     * @param factura The invoice to process payments from
     * @param fechaConsulta The date to filter payments for
     * @return List of movement records for payments made on the specified date
     */
    private List<RegistroMovimientoDto> procesarPagosFactura(Factura factura, LocalDate fechaConsulta) {
        List<RegistroMovimientoDto> registros = new ArrayList<>();
        
        // Colombia timezone
        java.time.ZoneId zonaColombia = java.time.ZoneId.of("America/Bogota");
        
        for (Object pago : factura.getSoportePago().getListaPagos()) {
            if (esPagoDelDia(pago, fechaConsulta, zonaColombia)) {
                RegistroMovimientoDto registro = crearRegistroIngreso(pago, fechaConsulta);
                registros.add(registro);
            }
        }
        
        return registros;
    }
    
    /**
     * Creates an income movement record from a payment
     * 
     * @param pago The payment object
     * @param fechaConsulta The date for the record
     * @return Movement record for the income
     */
    private RegistroMovimientoDto crearRegistroIngreso(Object pago, LocalDate fechaConsulta) {
        double montoPago = obtenerMontoPago(pago);
        
        return new RegistroMovimientoDto(
            fechaConsulta.toString(),
            "INGRESO",
            montoPago
        );
    }

}
