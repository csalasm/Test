/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Utilidades.Messages;
import Utilidades.PDF;
import Vistas.VistaAlumno;
import Vistas.VistaHacerTest;
import Vistas.VistaResultados;
import Vistas.VistaSeleccionarTest;
import com.itextpdf.text.DocumentException;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.Pregunta;
import modelo.Respuesta;

import modelo.Examen;

import modelo.Test;
import modelo.Usuario;
import modeloDAO.ExamenDAO;

import modeloDAO.PreguntaDAO;
import modeloDAO.RespuestaDAO;

import modeloDAO.TestDAO;
import modeloDAO.UsuarioDAO;

/**
 *
 * @author alejandroruiz
 */
class AlumnoControlador implements ActionListener,WindowListener{
    
    final private VistaAlumno va;
    private VistaSeleccionarTest vst;
    private VistaHacerTest vht;
    private TestDAO testDAO;

    private RespuestaDAO respuestaDAO;
    
    private UsuarioDAO usuarioDAO;

    private ExamenDAO examenDAO;
    String dni;
    private Usuario alumno;

    private ArrayList<Test> listaTest;
    private ArrayList<Pregunta> listaPreguntas;
    private int preguntaActual = 0; // Indica la pregunta por la que va el test
    private ArrayList<String> listaRespuestasUsuario;
    private ArrayList<ArrayList<Respuesta>> listaDelistaRespuestas;
    private Test testActual;
    private Usuario usuario;
    /**
     * Contructor de la Clase AlumnoControlador
     * @param u Objeto de la clase Usuario
     * @param v Objeto de la clase VistaAlumno
     */
    public AlumnoControlador(Usuario u, VistaAlumno v){
       this.va=v;
        va.setLocationRelativeTo(null);
        va.setVisible(true);
        initEvents();
        this.usuario = u; 
        listaDelistaRespuestas = new ArrayList<>();
    }
/**
 * Funcion que elije las funciones a realizar segun el Evento recibido
 * @param e Objeto de la Clase ActionEvent
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "SELECCIONAR":
                vst = new VistaSeleccionarTest();
                vst.btnSeleccionarTest.setActionCommand("SELECCIONAR_TEST");
                vst.btnSeleccionarTest.addActionListener(this);
                processTestList();
                vst.setLocationRelativeTo(null);
                vst.setVisible(true);
                break;
            case "RESULTADOS":
                alumno = new UsuarioDAO().devuelveUsuario(usuario.getDni());
                //System.out.println(alumno);
                ArrayList<Examen> lista_examenes = new ExamenDAO().devolverExamenesAlumno(alumno);
                VistaResultados vr = new VistaResultados();
               
                
                double count_nota=0;
                double count_aciertos=0;
                double count_fallos=0;
                for(Examen ex:lista_examenes){
                   
                     count_nota=count_nota+(ex.getNota());
                     count_aciertos=count_aciertos+(ex.getAciertos());
                     count_fallos=count_fallos+(ex.getFallos());
                    
                    String fecha = new SimpleDateFormat("dd-MM-yyyy").format(ex.getFecha());
                    String nombre_test = new TestDAO().getNombreTest(ex.getId_test());
                    
                    Object[] row ={fecha,nombre_test,ex.getAciertos(),ex.getFallos(),ex.getNota()};
                    vr.modeloTabla.addRow(row);  
                }
                vr.modeloTabla2.addRow(new Object[]{count_aciertos, count_fallos, count_nota/lista_examenes.size()});
                vr.tablaResultados.setEnabled(false);
                vr.tablaResultadosMedios.setEnabled(false);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                for(int x=0;x<vr.tablaResultados.getColumnCount();x++){
                    vr.tablaResultados.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
                }
                
                for(int x=0;x<vr.tablaResultadosMedios.getColumnCount();x++){
                    vr.tablaResultadosMedios.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
                }
                vr.setLocationRelativeTo(null);
                vr.setVisible(true);
                
                break;
            case "SELECCIONAR_TEST":
                preguntaActual = 0;
                listaRespuestasUsuario = new ArrayList<>();
                testActual = listaTest.get(vst.jListTest.getSelectedIndex());
                vht = new VistaHacerTest(testActual.getDuracion(), testActual.getNombre());
                // Recuperamos las preguntas que tiene el test seleccionado
                
                listaPreguntas = new PreguntaDAO().getPreguntasFromTest(testActual);
                if (listaPreguntas.size() > 0) {
                    processPregunta();
                    vht.setLocationRelativeTo(null);
                    vht.pack();
                    vht.setVisible(true);
                    vht.btnSiguiente.setActionCommand("SIGUIENTE_PREGUNTA");
                    vht.btnSiguiente.addActionListener(this);
                    vht.vistaCronometro.iniciarCronometro();
                }
                else
                    JOptionPane.showMessageDialog(vst, Messages.getString("msgSelectedTestHasNoQuestion"), "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "SIGUIENTE_PREGUNTA":
                processPregunta();
                vht.pack();
                break;
        }
    }
/**
 * Funcion que inicia los eventos del Objeto VistaAlumno
 */
    private void initEvents() {
        va.btnSeleccionar.setActionCommand("SELECCIONAR");
        va.btnSeleccionar.addActionListener(this);
        va.btnResultados.setActionCommand("RESULTADOS");
        va.btnResultados.addActionListener(this);
        va.addWindowListener(this);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                usuarioDAO = new UsuarioDAO();
                usuarioDAO.loggeaUsuario(usuario, Boolean.FALSE);
            }
        });
        
    }
    /**
     * Funcion que lista los Test activos dado un Usuario.
     */
    private void processTestList() {
        // Recuperamos los test activos
        testDAO = new TestDAO();
        listaTest = testDAO.devuelveTestActivos(usuario, true);
        DefaultListModel<String> listaNombreTest = new DefaultListModel<>();
        for (Test t: listaTest) {
            listaNombreTest.addElement(t.getNombre());
        }
        vst.jListTest.setModel(listaNombreTest);
        vst.jListTest.setSelectedIndex(0);
    }
    /**
     * Funcion que lista las Respuestas dada una Pregunta
     */
    private void processPregunta() {
        // Primera pregunta
        if (preguntaActual == 0) {
            ArrayList<Respuesta> listaRespuestas = new RespuestaDAO().devuelveRespuesta(listaPreguntas.get(preguntaActual));
            listaDelistaRespuestas.add(listaRespuestas);
            vht.setPregunta(listaPreguntas.get(preguntaActual).getTexto(), listaRespuestas, listaPreguntas.size(), preguntaActual);
            preguntaActual++;
        } // Quedan preguntas por realizar y hay tiempo
        else if (listaPreguntas.size() > preguntaActual && !vht.isAcabado()) {
            if (vht.mi_panel.respSeleccionada != null) {
                listaRespuestasUsuario.add(vht.mi_panel.respSeleccionada);
                ArrayList<Respuesta> listaRespuestas = new RespuestaDAO().devuelveRespuesta(listaPreguntas.get(preguntaActual));
                listaDelistaRespuestas.add(listaRespuestas);
                vht.setPregunta(listaPreguntas.get(preguntaActual).getTexto(), listaRespuestas, listaPreguntas.size(), preguntaActual);
                preguntaActual++;
            }
        } // Se acaba el test o el tiempo
        else if (listaPreguntas.size() == preguntaActual || vht.isAcabado()) {
            if (vht.mi_panel.respSeleccionada != null) {
                // Hay respuesta del usuario
                listaRespuestasUsuario.add(vht.mi_panel.respSeleccionada);        
                corregirTest();
            }
            else { // No hay respuesta en la ultima pregunta que se muestra
                corregirTest();
            }
        }
    }
    /**
     * Funcion que corrige los test.
     */
    private void corregirTest() {
        int correctas = 0;
        int falladas = 0;
        // Para cada pregunta
        respuestaDAO = new RespuestaDAO();
        // Si ha respondido preguntas
        if (listaRespuestasUsuario.size() > 0) {
            for (int i=0; i<listaPreguntas.size(); i++) {
                // Obtenemos su respuesta correcta
                Respuesta r = respuestaDAO.getRespuestaCorrecta(listaPreguntas.get(i));
                //System.out.println("RESPUESTA = "+r.getTexto());
                //System.out.println("RESPUESTA USUARIO = "+listaRespuestasUsuario.get(i));
                // Si la respuesta es correcta
                if (listaRespuestasUsuario.size() > i)
                    if (r.getTexto().equals(listaRespuestasUsuario.get(i))) 
                        correctas++;
                    else
                        falladas++;
            }
        }
        calcularNota(correctas, falladas, listaPreguntas.size());
    }
    /**
     * Funcion que calcula la nota de un Test
     * @param correctas Numero de respuestas correctas
     * @param falladas Numero de respuetas incorrecta
     * @param numPreguntas Numero total de preguntas
     */
    @SuppressWarnings("empty-statement")
    private void calcularNota(int correctas, int falladas, int numPreguntas) {
        double nota;
        double resp_nota = (double)10/(numPreguntas);
        // Comprobamos la configuración del test
        Test t = testDAO.getTest(testActual.getId_test());
        if (t.getResta() == 0)
            nota = resp_nota*correctas;
        else { // Una mal resta una bien, por tanto sería tener el doble de fallos
            if (t.getResta() == 1)
                nota = (resp_nota *correctas) - (resp_nota * falladas);
            else 
                nota = (resp_nota * correctas) - (resp_nota * falladas/t.getResta());
            
            nota =Math.round(nota*100.0)/100.0;
        }
        if (nota < 0)
            nota = 0;
        
        vht.setVisible(false);
        vht.dispose();
        // DefaultListModel model = (DefaultListModel) vst.jListTest.getModel();
        //model.remove(vst.jListTest.getSelectedIndex());
        // Guardamos el examen realizado
        
        Examen e = new Examen(usuario.getDni(), t.getId_test(), new Date(Calendar.getInstance().getTime().getTime()), correctas, falladas, nota);
        new ExamenDAO().crearExamen(e);
        
        // Generamos el PDF
        boolean generarPdf = false;
        if (listaPreguntas.size() == listaRespuestasUsuario.size()) {
            PDF pdf = new PDF(listaPreguntas, listaDelistaRespuestas);
            generarPdf = true;
            try {
                pdf.createPDF(t.getNombre(), usuario.getNombre()+" "+usuario.getApellidos(), String.valueOf(nota),usuario.getDni()+".pdf");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AlumnoControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(AlumnoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (generarPdf) {
            Object [] resp =  {Messages.getString("textAceptar"), Messages.getString("textMostrarPDF")};
            int selectedOption = JOptionPane.showOptionDialog(vst, Messages.getString("msgTestScore")+" "+nota, null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, resp, resp[0]);
            if (selectedOption == JOptionPane.NO_OPTION) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File(PDF.PDF_PATH+usuario.getDni()+".pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
            }
        }
        else
            JOptionPane.showMessageDialog(vst, Messages.getString("msgTestScore")+" "+nota, null, JOptionPane.INFORMATION_MESSAGE);
    }
/**
 * Funcion que Devuelve los examenes del Usuario alumno
 */
   private void processSeleccionar(){
       ArrayList<Examen> listaExamenes = new ExamenDAO().devolverExamenesAlumno(null);
       
       
   }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        usuarioDAO=new UsuarioDAO();
        usuarioDAO.loggeaUsuario(usuario, Boolean.FALSE);

    }

    @Override
    public void windowClosed(WindowEvent e) {
        usuarioDAO=new UsuarioDAO();
        usuarioDAO.loggeaUsuario(usuario, Boolean.FALSE);
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

   
}
