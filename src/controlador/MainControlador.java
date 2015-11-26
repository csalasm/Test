/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Utilidades.Messages;
import Vistas.VistaLogin;
import java.util.Locale;

/**
 *
 * @author csalas
 */
public class MainControlador {
    public Locale locale;
    
    public static void main(String args[]) {
        LoginControlador lc = new LoginControlador(null, new VistaLogin());
        Messages.getCadena();
        Messages.setLocale(new Locale("en","UK"));
        System.out.println(Messages.getString("msg_error"));
    }
    
}
