package com.caciquesport.inventario.inventario.service.implementations;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.service.interfaces.NotificacionFacturaService;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.geom.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

/*
 * Implementacion de servicio que se encarga de realizar notificaciones de las facturas 
 */
@Service
public class NotificacionFacturaImpl implements NotificacionFacturaService {

    /**
     * Notificar factura
     * 
     * @param factura - factura la cual sera notificada
     */
    @Override
    public void notificarFactura(Factura factura) throws Exception {

    }

    public void generarPdf(Factura factura) throws FileNotFoundException {

        String dest = System.getProperty("user.dir") + "/facturaspdf/" + "factura1.pdf";

        // Inicializar el PdfWriter
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        // Color personalizado
        Color greenColor = new DeviceRgb(139, 195, 74);

        // Agregar título y logo
        document.add(new Paragraph("Cacique Sport")
                .setFontSize(40)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Confecciones")
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        // Detalles de la factura
        document.add(new Paragraph("Factura de venta")
                .setFontSize(15)
                .setBold());

        document.add(new Paragraph("Código: 0001\n" +
                "Fecha: 31 de agosto 2024\n" +
                "Titular: Carlos Andrés\n" +
                "Estado: Pendiente").setFontSize(13));

        // Crear tabla de productos
        float[] columnWidths = { 4, 4, 4 }; // Anchos de las columnas
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold().setFontColor(greenColor)));
        table.addHeaderCell(new Cell().add(new Paragraph("Estado").setBold().setFontColor(greenColor)));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold().setFontColor(greenColor)));

        // Filas de la tabla
        table.addCell("Camibuso");
        table.addCell("Pendiente");
        table.addCell("$50.000");

        table.addCell("Pantalon");
        table.addCell("Entregado");
        table.addCell("$60.000");

        table.addCell("Medias");
        table.addCell("Entregado");
        table.addCell("$12.000");

        document.add(table);

        // Totales
        document.add(new Paragraph("\nTotal: $122.000")
                .setFontSize(15)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("Valor total cancelado: $72.000\nValor total adeudado: $50.000").setFontSize(15));

        // Mensaje de agradecimiento
        document.add(new Paragraph("Muchas gracas")
                .setFontSize(16)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(greenColor));

            document.add(new Paragraph("Cacique Sport cuida la imagen de tu hijo en su etapa mas importante")
                .setFontSize(13)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(greenColor));

        // Datos de contacto
        document.add(new Paragraph("Cra. 26 #No. 46 - 31, Calarcá, Quindío\nTeléfono: 3233392630")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));

        // Cerrar el documento
        document.close();
    }

}
