/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.text.StyleConstants;
import modelo.Pregunta;
import modelo.Respuesta;



/**
 *
 * @author csalas
 */
public class PDF {
    
    public static final String PDF_PATH = "pdf/";
    ArrayList<Pregunta>listaPreguntas = null;
    ArrayList<ArrayList<Respuesta>>listaRespuestas = null;
    
    public PDF(ArrayList<Pregunta> listaPreguntas, ArrayList<ArrayList<Respuesta>> listaRespuestas) {
        this.listaPreguntas = listaPreguntas;
        this.listaRespuestas = listaRespuestas;
        
        
    }
    
    public void createPDF(String titleExamen, String filename) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(PDF_PATH+filename));
        document.open();
        document.add(new Paragraph(titleExamen));
        document.add(new Paragraph("\n"));
        document.add(createFile());
        document.add(new Paragraph(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime())));
        document.close();
    }
    
    private PdfPTable createFile() {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;
        //Font green = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GREEN);
        int i=0;
        for (Pregunta p: listaPreguntas) {
            cell = new PdfPCell(new Phrase(p.getTexto()));
            // Agregamos la pregunta
            table.addCell(cell);
            // Agregamos las respuestas
            ArrayList<Respuesta>respuestasPregunta = listaRespuestas.get(i);
            Phrase phrase = new Phrase();
            for (Respuesta r: respuestasPregunta) {
                if (!r.isCorrecta())
                    phrase.add(r.getTexto()+"\n");
                else {
                   phrase.add("* "+r.getTexto()+"\n");
                    
                }
            }
            cell.addElement(phrase);
            table.addCell(cell);
            i++;
        }
        
        return table;
    }
    
}
