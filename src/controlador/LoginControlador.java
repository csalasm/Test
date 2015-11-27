/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Utilidades.Messages;
import Vistas.VistaAlumno;
import Vistas.VistaLogin;
import Vistas.VistaProfesor;
import com.sun.jmx.snmp.ThreadContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

/**
 *
 * @author csalas
 */
public class LoginControlador implements ActionListener {
    final private UsuarioDAO usuario;
    private VistaLogin vistaLogin;
    
    public LoginControlador(UsuarioDAO u, VistaLogin vl) {
        usuario = (u == null) ? new UsuarioDAO() : u;
        vistaLogin = vl;
        vistaLogin.setLocationRelativeTo(null);   
        vistaLogin.setVisible(true);     
        vistaLogin.toFront();
        initEvents();
    }

    @Override
    public void actionPerformed(ActionEvent e) throws NullPointerException{
        if (e.getActionCommand().equals("LOGIN")) {
            
            Usuario u = usuario.logginUser(vistaLogin.user.getText(), vistaLogin.pfPassword.getText());//He quitado los toString era redundante
            //System.out.println(u);
            if (u != null) {
                
                if (u.isEs_profesor()) {
                    ProfesorControlador pc= new ProfesorControlador(usuario, u,new VistaProfesor(u.getNombre()));
                    vistaLogin.dispose();
                }
                else {
                    AlumnoControlador ac = new AlumnoControlador(u, new VistaAlumno(u.getNombre()));    
                    vistaLogin.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(vistaLogin,Messages.getString("msg_error"),null,JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getActionCommand().equals("BTN_ES")) {
            Messages.setLocale(new Locale("es", "ES"));
            resetLogin();
            
        }
        else if (e.getActionCommand().equals("BTN_EN")) {
            Messages.setLocale(new Locale("en", "UK"));
            resetLogin();
        }
    }
   
    
    private void initEvents() {
        vistaLogin.btnConectar.setActionCommand("LOGIN");
        vistaLogin.btnConectar.addActionListener(this);
        vistaLogin.btnIdiomaES.setActionCommand("BTN_ES");
        vistaLogin.btnIdiomaES.addActionListener(this);
        vistaLogin.btnIdiomaUK.setActionCommand("BTN_EN");
        vistaLogin.btnIdiomaUK.addActionListener(this);
    }
    
    private void resetLogin() {
        vistaLogin.setVisible(false);
        vistaLogin.dispose();
        vistaLogin = new VistaLogin();
        vistaLogin.setLocationRelativeTo(null);   
        vistaLogin.setVisible(true);     
        vistaLogin.toFront();
        initEvents();
    }
    
    
    
}
