/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vistas.VistaAlumno;
import Vistas.VistaLogin;
import Vistas.VistaProfesor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

/**
 *
 * @author csalas
 */
public class LoginControlador implements ActionListener {
    final private UsuarioDAO usuario;
    final private VistaLogin vistaLogin;
    
    public LoginControlador(UsuarioDAO u, VistaLogin vl) {
        usuario = (u == null) ? new UsuarioDAO() : u;
        vistaLogin = vl;
        vistaLogin.setVisible(true);     
        vistaLogin.setLocationRelativeTo(null);       
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
                    ProfesorControlador pc= new ProfesorControlador(usuario, u,new VistaProfesor());
                    vistaLogin.dispose();
                }
                else {
                    AlumnoControlador ac = new AlumnoControlador(u, new VistaAlumno(u.getNombre()));    
                    vistaLogin.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(vistaLogin, "Usuario/Contraseña incorrecta",null,JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void initEvents() {
        vistaLogin.btnConectar.setActionCommand("LOGIN");
        vistaLogin.btnConectar.addActionListener(this);
    }
    
    
    
}
