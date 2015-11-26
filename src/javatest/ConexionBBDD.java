/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest;

/**
 *
 * @author inftel22
 */
public class ConexionBBDD {
    
    private String param_conex = "jdbc:oracle:thin:INFTEL15_17/INFTEL@olimpia.lcc.uma.es:1521:edgar";
    
    /*public int consulta_permisos(String n){ //Consultar si un usuario tiene permisos (profesor o alumno)
        int permisos=0; //Inicialmente no tiene permisos hay que consultar en la base de datos si tiene.
        
        /*if(permisos where nombre=n){
            permisos=1;
        }
        
      return permisos;  
    }*/
    
    public String consulta_password(String usuario){
    
        return "1234";
    }

    public int consulta_permisos(String usuario) {

        if ("Profesor".equals(usuario)) {

            return 1; //profesor
        } else {
            return 0;
        }
    }
}
