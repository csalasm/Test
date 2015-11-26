/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Examen;
import modelo.Test;
import modelo.Usuario;

/**
 *
 * @author andresbailen93
 */
public class ExamenDAO {

    private Connection con = null;
    PreparedStatement pstmt = null;
    

    /**
     * Constructor de la clase ExamenDAO, en el que al atributo de tipo
     * Connection se asigna una conexión llamando a la clase ConexiónOrcl
     */
    public ExamenDAO() {
        con = new ConexionOrcl().conecta();
    }

    /**
     * Procedimiento que introduce un examen en la Base de datos
     *
     * @param exam Recibe como parámetro un objeto de la clase Examen
     */
    public void crearExamen(Examen exam) {
        try {
            pstmt = con.prepareStatement("INSERT INTO EXAMEN VALUES (?,?,?,?,?,?)");
            pstmt.clearParameters();
            pstmt.setString(1, exam.getDni());
            pstmt.setInt(2, exam.getId_test());
            pstmt.setDate(3, exam.getFecha());
            pstmt.setInt(4, exam.getAciertos());
            pstmt.setInt(5, exam.getFallos());
            pstmt.setDouble(6, exam.getNota());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExamenDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pstmt = null;
        }

    }

    /**
     * Función que devuelve una lista de objetos Examenes que se corresponden
     * con un determinado TestID
     *
     * @param test Recibe como parámetro un objeto Test
     * @return Devuelve un ArrayList de tipo Examen
     */
    public ArrayList<Examen> devolverExamenes(Test test) {

        ArrayList<Examen> lista_examenes = new ArrayList<Examen>();

        try {
            pstmt = con.prepareStatement("SELECT * FROM EXAMEN WHERE ID_TEST=?");
            pstmt.clearParameters();
            pstmt.setInt(1,test.getId_test());
            Examen exam;
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()) {
                exam = new Examen(rs.getString("DNI"), rs.getInt("ID_TEST"), rs.getDate("FECHA"), rs.getInt("ACIERTOS"), rs.getInt("FALLOS"), rs.getDouble("NOTA"));
                lista_examenes.add(exam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista_examenes;

    }

    /**
     * Función que devuelve una lista de Examenes que se corresponden con un
     * determinado usuario
     *
     * @param usser Recibe como parámetro un objeto Usuario
     * @return Devuelve un ArrayList de tipo Examen
     */
    public ArrayList<Examen> devolverExamenesAlumno(Usuario usser) {

        
        ArrayList<Examen> lista_examenes = new ArrayList<Examen>();

        try {
            pstmt = con.prepareStatement("SELECT * FROM EXAMEN WHERE DNI=?");
            pstmt.clearParameters();
            pstmt.setString(1, usser.getDni());
            Examen exam;
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                exam = new Examen(rs.getString("DNI"), rs.getInt("ID_TEST"), rs.getDate("FECHA"), rs.getInt("ACIERTOS"), rs.getInt("FALLOS"), rs.getDouble("NOTA"));
                lista_examenes.add(exam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista_examenes;

    }

    /**
     * Funcion que hace que se cierre la conexion cuando se elimina el objeto.
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        con.close();

    }

}
