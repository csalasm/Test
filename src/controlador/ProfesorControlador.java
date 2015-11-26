/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vistas.VistaActivarTest;
import Vistas.VistaCrearPregunta;
import Vistas.VistaNuevoTest;
import Vistas.VistaNuevoUsuario;
import Vistas.VistaProfesor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Test;
import modelo.Usuario;
import modelo.Pregunta;
import modelo.Respuesta;
import modeloDAO.CategoriaDAO;
import modeloDAO.TestDAO;
import modeloDAO.UsuarioDAO;
import modeloDAO.PreguntaDAO;
import modeloDAO.RespuestaDAO;

/**
 *
 * @author andresbailen93
 */
public class ProfesorControlador  implements ActionListener {

    final private UsuarioDAO usuario;
    private TestDAO testdao=null;
    private CategoriaDAO categodao=null;
    private PreguntaDAO preguntadao=null;
    private RespuestaDAO respuestadao=null;
    final private VistaProfesor vistaProfesor;
    private VistaNuevoUsuario vnu=null;
    private VistaNuevoTest vnt=null;
    private VistaCrearPregunta vcp=null;
    private VistaActivarTest vat=null;
    private Usuario creauser=null;
    private final Usuario userprof;
    private Test test=null;
    private Test teste=null;
    private Pregunta pregunta=null;
    private Respuesta respuesta=null;
    private ArrayList<Test> lista_test;
    private ArrayList<Categoria> lista_cate;
    private byte[] bytes;

    public ProfesorControlador(UsuarioDAO u,Usuario us, VistaProfesor vp) throws NullPointerException{
        usuario = (u == null) ? new UsuarioDAO() : u;
        vistaProfesor = vp;
        vistaProfesor.setVisible(true);
        vistaProfesor.setLocationRelativeTo(null);
        userprof=us;
        initEvents();
    }


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
            case"ADDcategoria":
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
                    
        }
    }

    private void initEvents() throws NullPointerException{
        vistaProfesor.btnAnadirUsuario.setActionCommand("ADDEUSUARIO");
        vistaProfesor.btnAnadirUsuario.addActionListener(this);
        vistaProfesor.btnAnadeTest.setActionCommand("ADDTEST");
        vistaProfesor.btnAnadeTest.addActionListener(this);
        vistaProfesor.btnCreaPregunta.setActionCommand("ADDPREGUNTA");
        vistaProfesor.btnCreaPregunta.addActionListener(this);
        vistaProfesor.btnActivaTest.setActionCommand("ACTIVAtest");
        vistaProfesor.btnActivaTest.addActionListener(this);

    }

    private void aniadeVistaUsuario() throws NullPointerException{
        vnu= new VistaNuevoUsuario();
        vnu.setVisible(true);
        vnu.setLocationRelativeTo(null);
        vnu.btnAnadir.setActionCommand("ADDu");
        vnu.btnAnadir.addActionListener(this);
        
    }

    private void aniadeUsuario() throws NullPointerException{
       
        creauser = new Usuario(vnu.tfDniUser.getText(),vnu.tfNombre.getText(), 
                                vnu.tfApellidos.getText(), vnu.pfPassword.getText(),
                                vnu.rbSiPermiso.isSelected());
        usuario.insertaUsuario(creauser);
        vnu.tfNombre.setText("");
        vnu.tfDniUser.setText("");
        vnu.tfApellidos.setText("");
        vnu.pfPassword.setText("");
        vnu.setVisible(false);
        vnu.pfPassword.setText("");
        
        vistaProfesor.setVisible(true);

      
        }
    private void aniadeNuevoTest()throws NullPointerException{
        vnt=new VistaNuevoTest();
        vnt.setVisible(true);
        vnt.setLocationRelativeTo(null);
        vnt.btnNuevoTest.setActionCommand("ADDt");
        vnt.btnNuevoTest.addActionListener(this);
        vnt.jTextAutor.setText(userprof.getDni());
        
    }
    private void aniadeTest()throws NullPointerException{
        testdao=new TestDAO();
        test= new Test(testdao.devuelveSequence(),vnt.jTextNombre.getText(),vnt.cbDuracion.getSelectedIndex()*60,
                            vnt.cbRestada.getSelectedIndex(),userprof.getDni(),vnt.rbActivo.isSelected());
        testdao.insertaTest(test);
        vnt.jTextNombre.setText("");
        vnt.cbDuracion.setSelectedIndex(0);
        vnt.cbRestada.setSelectedIndex(0);
        vnt.setVisible(false);

    }
    private void aniadeNuevaPregunta()throws NullPointerException{
        vcp=new VistaCrearPregunta();
        testdao=new TestDAO();
        categodao=new CategoriaDAO();
        vcp.setVisible(true);

        lista_test=testdao.devuelveTestes(userprof);
        vcp.setLocationRelativeTo(null);
        
        for(int i=0;i<lista_test.size();i++){
            vcp.cbSelecTestID.addItem(lista_test.get(i).getNombre());
        }
        
        lista_cate=categodao.ListarCategorias();
        for(int i=0;i<lista_cate.size();i++){
            vcp.cbSelecTema.addItem(lista_cate.get(i).getNombre());
        }
        vcp.btnaddTema.setActionCommand("ADDcategoria");
        vcp.btnaddTema.addActionListener(this);
        vcp.btnAnadirPreg.setActionCommand("ADDpregunta");
        vcp.btnAnadirPreg.addActionListener(this);
        vcp.btnFinal.setActionCommand("CLOSEpregunta");
        vcp.btnFinal.addActionListener(this);
    }
    private void aniadeCategoria(){
        String categoria=vcp.tfAnadeTema.getText();
        int idcategoria=categodao.devuelveSequence();
        Categoria cat=new Categoria(idcategoria,categoria);
        categodao.InsertarCategoria(cat);
        vcp.cbSelecTema.addItem(cat.getNombre());
    }
    private void aniadePregunta(){
        preguntadao=new PreguntaDAO();
        respuestadao=new RespuestaDAO();
        int idpregunta=preguntadao.devuelveSequence();
        int idcategoria=categodao.devuelveCategoria((String)vcp.cbSelecTema.getSelectedItem());
        pregunta=new Pregunta(idpregunta,vcp.tfTextoPregunta.getText(),idcategoria,bytes );
        int idtest=testdao.getIdTest((String)vcp.cbSelecTestID.getSelectedItem());
        preguntadao.setPregunta(pregunta,idtest);
        
        if(!vcp.tfRespUno.getText().equals("")){
            respuesta=new Respuesta(respuestadao.devuelveSequence(),vcp.tfRespUno.getText(),vcp.rbtnUno.isSelected(),idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta=null;
        }
        if(!vcp.tfRespDos.getText().equals("")){
            respuesta=new Respuesta(respuestadao.devuelveSequence(),vcp.tfRespDos.getText(),vcp.rbtnDos.isSelected(),idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta=null;
        }
        if(!vcp.tfRespTres.getText().equals("")){
            respuesta=new Respuesta(respuestadao.devuelveSequence(),vcp.tfRespTres.getText(),vcp.rbtnTres.isSelected(),idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta=null;
        }
        if(!vcp.tfRespCuatro.getText().equals("")){
            respuesta=new Respuesta(respuestadao.devuelveSequence(),vcp.tfRespCuatro.getText(),vcp.rbtnCuatro.isSelected(),idpregunta);
            respuestadao.insertaRespuesta(respuesta);
            respuesta=null;
        }
        vcp.tfTextoPregunta.setText("");
        vcp.tfAnadeTema.setText("");
        vcp.tfRespUno.setText("");
        vcp.tfRespDos.setText("");
        vcp.tfRespTres.setText("");
        vcp.tfRespCuatro.setText("");
}
    private void cierrapregunta(){
        vcp.setVisible(false);
    }
    private void activaTest(){
        testdao=new TestDAO();
        vat=new VistaActivarTest();
        vat.setVisible(true);
        
        ArrayList<Test> lista_mitesACT=testdao.devuelveTestActivosProf(userprof);
        for(int i=0;i<lista_mitesACT.size();i++){
            vat.cbTesDesct.addItem(lista_mitesACT.get(i).getNombre());
            //ESto hay que revisarlo
        }
        ArrayList<Test> lista_mitesDESC=testdao.devuelveTestDesactivosProf(userprof);
        for(int i=0;i>lista_mitesDESC.size();i++){
            vat.cbTestAct.addItem(lista_mitesDESC.get(i).getNombre());
        }
        //String nombretestact=(String)vat.cbTestAct.getSelectedItem();        
        //teste=testdao.devuelveTestNomUsuario(nombretestact);

        vat.btnActiva.setActionCommand("ACTIVAdispo");
        vat.btnActiva.addActionListener(this);
        vat.btnDesactiva.setActionCommand("DESACTdispo");
        vat.btnDesactiva.addActionListener(this);
        vat.btnFinaliza.setActionCommand("CLOSEupdate");
        vat.btnFinaliza.addActionListener(this);
    }
    private void activaDispo(){
        String nombretestact=(String)vat.cbTestAct.getSelectedItem();
        teste=testdao.devuelveTestNomUsuario(nombretestact);
        testdao.UpdateDispo(teste, Boolean.TRUE);
        //vat.cbTestAct.;
         JOptionPane.showMessageDialog(vat, "Test Activo.",null,JOptionPane.INFORMATION_MESSAGE);
    }
    private void desactivaDispo(){
        String nombretestdest=(String)vat.cbTesDesct.getSelectedItem();
        teste=testdao.devuelveTestNomUsuario(nombretestdest);
        testdao.UpdateDispo(teste, Boolean.FALSE);
        JOptionPane.showMessageDialog(vat, "Test Desactivado.",null,JOptionPane.INFORMATION_MESSAGE);
    }
    
}
