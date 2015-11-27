/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Utilidades.Messages;
import Vistas.VistaActivarTest;
import Vistas.VistaCrearPregunta;
import Vistas.VistaNuevoTest;
import Vistas.VistaNuevoUsuario;
import Vistas.VistaProfesor;
import Vistas.VistaResultadosExamen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.Categoria;
import modelo.Examen;
import modelo.Test;
import modelo.Usuario;
import modelo.Pregunta;
import modelo.Respuesta;
import modeloDAO.CategoriaDAO;
import modeloDAO.ExamenDAO;
import modeloDAO.TestDAO;
import modeloDAO.UsuarioDAO;
import modeloDAO.PreguntaDAO;
import modeloDAO.RespuestaDAO;

/**
 *
 * @author andresbailen93
 */
public class ProfesorControlador implements ActionListener,WindowListener {

    final private UsuarioDAO usuario;
    private TestDAO testdao = null;
    private CategoriaDAO categodao = null;
    private PreguntaDAO preguntadao = null;
    private RespuestaDAO respuestadao = null;
    final private VistaProfesor vistaProfesor;
    private VistaNuevoUsuario vnu = null;
    private VistaNuevoTest vnt = null;
    private VistaCrearPregunta vcp = null;
    private VistaActivarTest vat = null;
    private VistaResultadosExamen vre = null;
    private Usuario creauser = null;
    private final Usuario userprof;
    private Test test = null;
    private Test teste = null;
    private Pregunta pregunta = null;
    private Respuesta respuesta = null;
    private ArrayList<Test> lista_test;
    private ArrayList<Categoria> lista_cate;
    private byte[] bytes;

    /**
     * Contructor de la clase ProfesorControlador
     *
     * @param u Objeto de la clase UsuarioDAO
     * @param us Objeto de la clase Usuario
     * @param vp Objeto de la clase VistaProfesor
     * @throws NullPointerException Excepcion puntero nulo
     */
    public ProfesorControlador(UsuarioDAO u, Usuario us, VistaProfesor vp) throws NullPointerException {
        usuario = (u == null) ? new UsuarioDAO() : u;
        vistaProfesor = vp;
        vistaProfesor.setLocationRelativeTo(null);
        vistaProfesor.setVisible(true);
        userprof = us;
        initEvents();
    }

    /**
     * Funcion que lee las acciones de los botones y elige la accion a realizar
     *
     * @param e Objeto de la clase ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ADDEUSUARIO":
                aniadeVistaUsuario();
                break;
            case "ADDu":
                aniadeUsuario();
                break;
            case "ADDTEST":
                aniadeNuevoTest();
                break;
            case "ADDt":
                aniadeTest();
                break;
            case "ADDPREGUNTA":
                aniadeNuevaPregunta();
                break;
            case "ADDcategoria":
                aniadeCategoria();
                break;
            case "ADDpregunta":
                aniadePregunta();
                break;
            case "CLOSEpregunta":
                cierrapregunta();
                break;
            case "ACTIVAtest":
                activaTest();
                break;
            case "ACTIVAdispo":
                activaDispo();
                break;
            case "DESACTdispo":
                desactivaDispo();
                break;
            case "CLOSEupdate":
                vat.setVisible(false);
                break;
            case "RESULTADOS":
                gestionaResultados();
                break;
            case "SELECCIONA_TEST":
                updateResultTable(lista_test.get(vre.jListSelectTest.getSelectedIndex()));
                break;

        }
    }

    /**
     * Funcion que inicializa las acciones principales de los botones de la
     * Vista Vista Profesor
     *
     * @throws NullPointerException
     */
    private void initEvents() throws NullPointerException {
        vistaProfesor.btnAnadirUsuario.setActionCommand("ADDEUSUARIO");
        vistaProfesor.btnAnadirUsuario.addActionListener(this);
        vistaProfesor.btnAnadeTest.setActionCommand("ADDTEST");
        vistaProfesor.btnAnadeTest.addActionListener(this);
        vistaProfesor.btnCreaPregunta.setActionCommand("ADDPREGUNTA");
        vistaProfesor.btnCreaPregunta.addActionListener(this);
        vistaProfesor.btnActivaTest.setActionCommand("ACTIVAtest");
        vistaProfesor.btnActivaTest.addActionListener(this);
        vistaProfesor.btnResultadosTest.setActionCommand("RESULTADOS");
        vistaProfesor.btnResultadosTest.addActionListener(this);
        vistaProfesor.addWindowListener(this);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                usuario.loggeaUsuario(userprof, Boolean.FALSE);

            }
        });
    }

    /**
     * Funcoin que instancia el objeto VistaNuevoUsuario y la accion del boton
     *
     * @throws NullPointerException
     */
    private void aniadeVistaUsuario() throws NullPointerException {
        vnu = new VistaNuevoUsuario();
        vnu.setLocationRelativeTo(null);
        vnu.setVisible(true);
        vnu.btnAnadir.setActionCommand("ADDu");
        vnu.btnAnadir.addActionListener(this);

    }

    /**
     * Funcion que obtiene los datos de los campos, crea un Usuario y lo inserta
     * en la base de datos
     *
     * @throws NullPointerException
     */
    private void aniadeUsuario() throws NullPointerException {

        creauser = new Usuario(vnu.tfDniUser.getText(), vnu.tfNombre.getText(),
                vnu.tfApellidos.getText(), vnu.pfPassword.getText(),
                vnu.rbSiPermiso.isSelected());
        try {
            usuario.insertaUsuario(creauser);
            vnu.tfNombre.setText("");
            vnu.tfDniUser.setText("");
            vnu.tfApellidos.setText("");
            vnu.pfPassword.setText("");
            JOptionPane.showMessageDialog(vnu, Messages.getString("useradd"), null, JOptionPane.INFORMATION_MESSAGE);
            vnu.setVisible(false);
            vnu.pfPassword.setText("");

            vistaProfesor.setVisible(true);
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            if (e.getErrorCode() == 1400)
                JOptionPane.showMessageDialog(vnu, Messages.getString("textUsuarioVacio"), null, JOptionPane.ERROR_MESSAGE);
            else if (e.getErrorCode() == 1)
                JOptionPane.showMessageDialog(vnu, Messages.getString("textUsuarioExistente"), null, JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            
        }
        
    }

    /**
     * Funcion que instancia un Objeto de la clase VistaNuevoTest y la accion
     * del boton
     *
     * @throws NullPointerException
     */
    private void aniadeNuevoTest() throws NullPointerException {
        vnt = new VistaNuevoTest();
        vnt.setLocationRelativeTo(null);
        vnt.setVisible(true);
        vnt.btnNuevoTest.setActionCommand("ADDt");
        vnt.btnNuevoTest.addActionListener(this);
        vnt.jTextAutor.setText(userprof.getDni());

    }

    /**
     * Funcion que recoge los datos de los campos, instancia un nuevo Test, lo
     * inserta en la base de datos
     *
     * @throws NullPointerException
     */
    private void aniadeTest() throws NullPointerException {
        testdao = new TestDAO();
        test = new Test(testdao.devuelveSequence(), vnt.jTextNombre.getText(), vnt.cbDuracion.getSelectedIndex() * 60,
                vnt.cbRestada.getSelectedIndex(), userprof.getDni(), Boolean.FALSE);
        try {
            testdao.insertaTest(test);
            vnt.jTextNombre.setText("");
            vnt.cbDuracion.setSelectedIndex(0);
            vnt.cbRestada.setSelectedIndex(0);
            JOptionPane.showMessageDialog(vnt, Messages.getString("testadd"), null, JOptionPane.INFORMATION_MESSAGE);
            vnt.setVisible(false);
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            if (e.getErrorCode() == 1400)
                JOptionPane.showMessageDialog(vnu, Messages.getString("textNombreTest"), null, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                
            }

    }

    /**
     * Funcion que instancia el obajeto de la clase VistaCrearPregunta, rellena
     * algunos campos dinamicos e instancia los botones de la vista
     *
     * @throws NullPointerException
     */
    private void aniadeNuevaPregunta() throws NullPointerException {
        vcp = new VistaCrearPregunta();
        testdao = new TestDAO();
        categodao = new CategoriaDAO();
        vcp.setLocationRelativeTo(null);
        vcp.setVisible(true);

        lista_test = testdao.devuelveTestes(userprof);

        for (int i = 0; i < lista_test.size(); i++) {
            vcp.cbSelecTestID.addItem(lista_test.get(i).getNombre());
        }

        lista_cate = categodao.ListarCategorias();
        for (int i = 0; i < lista_cate.size(); i++) {
            vcp.cbSelecTema.addItem(lista_cate.get(i).getNombre());
        }
        vcp.btnaddTema.setActionCommand("ADDcategoria");
        vcp.btnaddTema.addActionListener(this);
        vcp.btnAnadirPreg.setActionCommand("ADDpregunta");
        vcp.btnAnadirPreg.addActionListener(this);
        vcp.btnFinal.setActionCommand("CLOSEpregunta");
        vcp.btnFinal.addActionListener(this);
    }

    /**
     * Funcion que crea un nuevo Objeto de la clase Categoria y lo inserta en la
     * base de datos.
     */
    private void aniadeCategoria() {
        String categoria = vcp.tfAnadeTema.getText();
        int idcategoria = categodao.devuelveSequence();
        Categoria cat = new Categoria(idcategoria, categoria);
        Boolean a=categodao.InsertarCategoria(cat);
        if(a){
        vcp.cbSelecTema.addItem(cat.getNombre());
        vcp.tfAnadeTema.setText("");
        }
    }

    /**
     * Funcion que inserta un Objeto de la clase Pregunta, y Respuestas en la
     * base de datos despues de leer los datos de los campos
     */
    private void aniadePregunta() {
        preguntadao = new PreguntaDAO();
        respuestadao = new RespuestaDAO();
        int idpregunta = preguntadao.devuelveSequence();
        int idcategoria = categodao.devuelveCategoria((String) vcp.cbSelecTema.getSelectedItem());
        pregunta = new Pregunta(idpregunta, vcp.tfTextoPregunta.getText(), idcategoria, bytes);
        int idtest = testdao.getIdTest((String) vcp.cbSelecTestID.getSelectedItem());
        preguntadao.setPregunta(pregunta, idtest);

        if (!vcp.tfRespUno.getText().equals("")) {
            respuesta = new Respuesta(respuestadao.devuelveSequence(), vcp.tfRespUno.getText(), vcp.rbtnUno.isSelected(), idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta = null;
        }
        if (!vcp.tfRespDos.getText().equals("")) {
            respuesta = new Respuesta(respuestadao.devuelveSequence(), vcp.tfRespDos.getText(), vcp.rbtnDos.isSelected(), idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta = null;
        }
        if (!vcp.tfRespTres.getText().equals("")) {
            respuesta = new Respuesta(respuestadao.devuelveSequence(), vcp.tfRespTres.getText(), vcp.rbtnTres.isSelected(), idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta = null;
        }
        if (!vcp.tfRespCuatro.getText().equals("")) {
            respuesta = new Respuesta(respuestadao.devuelveSequence(), vcp.tfRespCuatro.getText(), vcp.rbtnCuatro.isSelected(), idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta = null;
        }
        vcp.tfTextoPregunta.setText("");
        vcp.tfAnadeTema.setText("");
        vcp.tfRespUno.setText("");
        vcp.tfRespDos.setText("");
        vcp.tfRespTres.setText("");
        vcp.tfRespCuatro.setText("");
    }

    /**
     * Funcion que cierra la VistaCreaPregunta
     */
    private void cierrapregunta() {
        vcp.setVisible(false);
    }

    /**
     * Funcion Instancia el objeto VistaActivaTest y muestra los test activos y
     * no activos de un Usuario con roll profesor e instancia los botones de la
     * vista
     */
    private void activaTest() {
        testdao = new TestDAO();
        vat = new VistaActivarTest();
        vat.setLocationRelativeTo(null);
        vat.setVisible(true);

        ArrayList<Test> lista_mitesACT = testdao.devuelveTestActivosProf(userprof);
        for (int i = 0; i < lista_mitesACT.size(); i++) {
            vat.cbTesDesct.addItem(lista_mitesACT.get(i).getNombre());
        }
        testdao = new TestDAO();
        ArrayList<Test> lista_mitesNOACT = testdao.devuelveTestDesactivosProf(userprof);
        for (int i = 0; i < lista_mitesNOACT.size(); i++) {
            vat.cbActivaTest.addItem(lista_mitesNOACT.get(i).getNombre());
        }

        vat.btnActiva.setActionCommand("ACTIVAdispo");
        vat.btnActiva.addActionListener(this);
        vat.btnDesactiva.setActionCommand("DESACTdispo");
        vat.btnDesactiva.addActionListener(this);
        vat.btnFinaliza.setActionCommand("CLOSEupdate");
        vat.btnFinaliza.addActionListener(this);
    }

    /**
     * Funcion que Activa los un Test
     */
    private void activaDispo() {
        String nombretestact = (String) vat.cbActivaTest.getSelectedItem();
        teste = testdao.devuelveTestNomUsuario(nombretestact);
        testdao.UpdateDispo(teste, Boolean.TRUE);
        vat.cbTesDesct.addItem(nombretestact);
        vat.cbActivaTest.removeItem(nombretestact);
        JOptionPane.showMessageDialog(vat, Messages.getString("test_acti."), null, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Funcion que desactiva un Test
     */
    private void desactivaDispo() {
        String nombretestdest = (String) vat.cbTesDesct.getSelectedItem();
        teste = testdao.devuelveTestNomUsuario(nombretestdest);
        testdao.UpdateDispo(teste, Boolean.FALSE);
        vat.cbActivaTest.addItem(nombretestdest);
        vat.cbTesDesct.removeItem(nombretestdest);
        JOptionPane.showMessageDialog(vat, Messages.getString("test_desacti"), null, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gestionaResultados() {
        vre = new VistaResultadosExamen();
        vre.jListSelectTest.addActionListener(this);
        vre.jListSelectTest.setActionCommand("SELECCIONA_TEST");
        lista_test = new TestDAO().devuelveTestes(userprof);
        for (Test t: lista_test)
            vre.jListSelectTest.addItem(t.getNombre());
        
        vre.setLocationRelativeTo(null);
        vre.setVisible(true);
    }
    
    private void updateResultTable(Test t) {
        ArrayList<Examen>listaExamen = new ExamenDAO().devolverExamenes(t);
        vre.modeloTabla = new DefaultTableModel(new Object[] { Messages.getString("labelDNI"), Messages.getString("Fecha"), Messages.getString("Aciertos"),Messages.getString("Fallos"),Messages.getString("Puntuacion") }, 0);
        for (Examen e: listaExamen) {
            Object[] row ={e.getDni(),new SimpleDateFormat("dd-MM-yyyy").format(e.getFecha()),e.getAciertos(),e.getFallos(),e.getNota()};
            vre.modeloTabla.addRow(row);  
        }
        vre.tablaResultadosTest.setModel(vre.modeloTabla);
        vre.tablaResultadosTest.setEnabled(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x=0;x<vre.tablaResultadosTest.getColumnCount();x++){
            vre.tablaResultadosTest.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        usuario.loggeaUsuario(userprof, Boolean.FALSE);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        usuario.loggeaUsuario(userprof, Boolean.FALSE);

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
