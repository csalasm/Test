/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author alejandroruiz
 */

    
    
public class Messages {
// La siguiente sentencia obtiene el Nombre del fichero Properties = mbgi18n.messages
private static final String BUNDLE_NAME = Messages.class.getPackage().getName()+".mensajes";
// Crea una tabla ‘hash’ con las entradas en el idioma elegido
private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
// Metodo para Cambiar la localizacion
public static void setLocale(Locale locale) {
// Recarga las entradas de la tabla con la nueva localizacion 
    RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
}
// Metodo para consultar un termino
public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key); // Devuelve una entrada de la tabla 
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

public static void getCadena(){
    System.out.println(Messages.class.getPackage().getName());
}
}


