/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloDAO;

import java.util.ArrayList;
import modelo.Test;
import modelo.Usuario;

/**
 *
 * @author andresbailen93
 */
public class DemoBBDD {
    public static void main(String[] args) {
        ConexionOrcl conec= new ConexionOrcl();
        conec.conecta();
        System.out.println(conec);
        conec.desconecta(conec.getCon());
        System.out.println(conec);
        
        TestDAO testdao=new TestDAO();
        System.out.println(testdao.devuelveSequence()+1);
        
       // Test test= new Test(7,"PRUEBA PUTO TEST",60,2,"77774444P",true);
       // System.out.println(test.toString());
        //testdao.insertaTest(test);
        
        
        Usuario user= new Usuario("77774444P","","","",true);
        ArrayList<Test> array_list= testdao.devuelveTestes(user);
        System.out.println(testdao.devuelveTestes(user).toString());
        for(int i=0;i<array_list.size();i++){
           System.out.println(array_list.get(i).getNombre());
        }
        
        
    }
}
