package com.caciquesport.inventario.inventario.service.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.service.interfaces.NotificacionFacturaService;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Authenticator;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/*
 * Implementacion de servicio que se encarga de realizar notificaciones de las facturas 
 */
@Service
public class NotificacionFacturaImpl implements NotificacionFacturaService {

    // Esta es la contraseña con la que se inicia sesion para enviar los correos
    @Value("${email_Caciq}")
    private String emailCacique;

    @Value("${password_Caciq}")
    private String passWordCacique;

    /**
     * Notificar factura. este metodo verifica que exista un correo al cual enviar
     * el pdf que se genera.
     * si no se encuentra correo, este no se generara
     * 
     * @param factura - factura la cual sera notificada
     */
    @Override
    public String notificarFactura(Factura factura) {

        // se verifica que el cliente tenga correo
        if (!factura.getCliente().getEmail().isEmpty()) {
            try {
                generarPdf(factura);
                enviarPDfCorreo(factura.getCliente().getEmail(),factura.getCliente().getNombre());
                return "factura enviada correctamente";
            } catch (Exception e) {
                return "hubo un error al enviar la factura :" + e.getMessage();
            }
        }
        return "la factura no se envio porque el usuario no tiene correo";
    }

    /**
     * inicializar la session de conexion con el correo electronico. posteriormente
     * se realiza el envio del documento
     * 
     * @param emailCliente - correo del receptor del pdf
     */
    private void enviarPDfCorreo(String emailCliente,String nombre)throws Exception  {

        Properties properties = new Properties();

        // inicializar propiedades del servidor del correo
        generarPropertiesParaEmail(properties);

        // Crear sesión autenticada
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailCacique, passWordCacique);
            }
        });

        enviarCorreo(session, emailCliente,nombre);
    }

    /**
     * Generar cuerpo del mensaje que sera enviado al cliente
     * 
     * @param emailCliente - correo del receptor
     */
    private void enviarCorreo(Session session, String emailCliente,String nombreCliente)throws Exception {

        Message message = new MimeMessage(session);

        // mensaje del correo
        message.setFrom(new InternetAddress(emailCacique));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailCliente));
        message.setSubject("Factura de Compra");

        // Crear cuerpo del mensaje con saludo
        String body = "Hola "+ nombreCliente+", Cacique Sport te manda tu factura de compra.";
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/plain");

        // ruta para el correo
        String dest = System.getProperty("user.dir") + "/facturaspdf/" + "factura1.pdf";

        // Adjuntar el archivo PDF
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File(dest)); // Ruta del archivo PDF

        // Combinar el cuerpo y el archivo adjunto
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentPart);

        // Establecer el contenido del mensaje
        message.setContent(multipart);

        // Enviar el mensaje
        Transport.send(message);

    }


    /**
     * Agregar las configuraciones basicas(propiedades) para el manejo del servidor
     * del correo electronico
     * del cual enviaremos los correos
     * 
     * @param properties - instancia a la cual se le agregan las propiedades
     */
    private void generarPropertiesParaEmail(Properties properties) {

        // Configurar las propiedades del servidor SMTP de Gmail
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    }



    /**
     * Generar documento pdf en el cual se agregara todo el contenido para la
     * factura
     * 
     * @return document - documento pdf
     */
    private Document generarDocumentoParaPDf() throws FileNotFoundException {

        // ruta para guardar el pdf
        String dest = System.getProperty("user.dir") + "/facturaspdf/" + "factura1.pdf";

        // Inicializar el PdfWriter
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        return document;
    }

    /**
     * Generar un documento pdf a partir de los datos de la factura
     * 
     * @param factura - instancia de la factura con la informacion
     */
    private void generarPdf(Factura factura) throws FileNotFoundException {

        // generar documento
        Document document = generarDocumentoParaPDf();

        // Color personalizado
        Color greenColor = new DeviceRgb(139, 195, 74);

        construirTitulo(document);

        // generar datos de la factura como codigo, titular, fecha
        generarDetallesFactura(document, factura);

        // crear tabla y agregar productos
        crearLlenarTabla(document, factura, greenColor);

        // agregar Totales
        document.add(new Paragraph("\nTotal: $" + factura.getSoportePago().getValorTotalFactura())
                .setFontSize(15)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT));

        double valorRestante = factura.getSoportePago().getValorTotalFactura()
                - factura.getSoportePago().getValorTotalPagado();
        document.add(new Paragraph("Valor total cancelado: $" + factura.getSoportePago().getValorTotalPagado() +
                "\nValor total adeudado: $" + valorRestante).setFontSize(15));

        // agregar informacion final
        agregarInformacionFinal(document, greenColor);

        // Cerrar el documento
        document.close();
    }

    /**
     * agregar datos e informacion final de la factura. estos datos son generales y
     * no representan
     * detalles relevantes para la factura como tal
     * 
     * @param document - instancia del documento pdf de la factura
     */
    private void agregarInformacionFinal(Document document, Color greenColor) {

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
        document.add(new Paragraph("Cra. 26 #No. 46 - 31, Calarcá, Quindío\nTeléfono: 3154pos675135")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));

    }

    /**
     * Generar una tabla para el documento y agregar la lista de productos
     * 
     * @param document   - instancia del documento pdf para la factura
     * @param factura    - instancia de la factura con la informacion
     * @param greenColor - color base para pintar la tabla
     */
    private void crearLlenarTabla(Document document, Factura factura, Color greenColor) {

        // Crear tabla de productos
        float[] columnWidths = { 4, 4, 4 }; // Anchos de las columnas
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold().setFontColor(greenColor)));
        table.addHeaderCell(new Cell().add(new Paragraph("Estado").setBold().setFontColor(greenColor)));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold().setFontColor(greenColor)));

        agregarProductosTabla(table, factura);

        document.add(table);
    }

    /**
     * Agregar la lista de productos de la factura a una tabla
     * 
     * @param table   - tabla que pertenece al documento pdf
     * @param factura - instancia de la factura con la informacion
     */
    private void agregarProductosTabla(Table table, Factura factura) {

        // agregar todos los productos de la factura
        for (int i = 0; i < factura.getListaProductosFactura().size(); i++) {
            Producto producto = factura.getListaProductosFactura().get(i).getProducto();

            // se contruye la informacion para la columna de producto
            String caracteristicasProducto = producto.getTipoPrenda().getPrenda() + " del "
                    + producto.getTipoInstitucion().getInstitucion() + " " + producto.getTipoTalla().getTalla() +
                    " de " + producto.getTipoHorario().getHorario() + " para " + producto.getTipoGenero().getGenero();

            String estado = factura.getListaProductosFactura().get(i).getEstadoProducto().toString();

            String precio = "" + producto.getDetalleProducto().getPrecio();

            // Filas de la tabla
            table.addCell(caracteristicasProducto);
            table.addCell(estado);
            table.addCell(precio);
        }

    }

    /**
     * Este metodo agrega datos generales a la factura. los datos son:
     * codigo de la factura, titular de la factura, fecha de la factura y estado
     */
    private void generarDetallesFactura(Document document, Factura factura) {

        // Detalles de la factura
        document.add(new Paragraph("Factura de venta")
                .setFontSize(15)
                .setBold());

        String codigo = factura.getIdFactura().toString();
        String fecha = factura.getFechaFactura().toString();
        String titular = factura.getCliente().getNombre();
        String estado = factura.getEstadoFactura().toString();

        document.add(new Paragraph("Código: " + codigo + "\n" +
                "Fecha: " + fecha + "\n" +
                "Titular: " + titular + "\n" +
                "Estado: " + estado).setFontSize(13));
    }

    /**
     * agregar un titulo principal al documento
     * 
     * @param document - documento al que se le agrega el titulo
     */
    private void construirTitulo(Document document) {

        document.add(new Paragraph("Cacique Sport")
                .setFontSize(40)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Confecciones")
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
    }

}
