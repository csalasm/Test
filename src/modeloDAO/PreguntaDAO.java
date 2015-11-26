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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Categoria;
import modelo.Pregunta;
import modelo.Test;

/**
 * Clase que gestiona las llamadas a la BD para la entidad Pregunta
 *
 * @author andresbailen93
 */
public class PreguntaDAO {

    private Connection con = null;
    PreparedStatement ps = null;

    public PreguntaDAO() {

        con = new ConexionOrcl().conecta();
    }

    /**
     * Método que agrega una nueva pregunta al sistema
     *
     * @param p Pregunta.
     * @param idtest Identificador del test al que pertenece.
     */
    public void setPregunta(Pregunta p,int idtest) {
        try {
            ps = con.prepareStatement("INSERT INTO PREGUNTA(ID_PREGUNTA,TEXTO, IMAGEN, ID_CATEGORIA) VALUES (?,?, NULL, ?)");
            ps.clearParameters();
            ps.setInt(1, p.getId_pregunta());
            ps.setString(2, p.getTexto());
            ps.setInt(3, p.getId_categoría());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PreguntaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ps=null;
        }
        try {
            ps = con.prepareStatement("INSERT INTO PREGUNTA_TEST(ID_PREGUNTA,ID_TEST) VALUES (?,?)");
            ps.clearParameters();
            ps.setInt(1, p.getId_pregunta());
            ps.setInt(2, idtest);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PreguntaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ps=null;
        }
    }

    /**
     * Método que devuelve una pregunta dado su ID
     *
     * @param id ID de la pregunta
     * @return Pregunta
     */
    public Pregunta getPregunta(Integer id) {
        Pregunta p = null;
        try {
            ps = con.prepareStatement("SELECT * FROM PREGUNTA WHERE ID_PREGUNTA = ?");
            ps.clearParameters();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Pregunta(
                        rs.getInt("ID_PREGUNTA"),
                        rs.getString("TEXTO"),
                        rs.getInt("ID_CATEGORIA"),
                        null
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PreguntaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    /**
     * Método que devuelve una lista de preguntas dada una categoría
     *
     * @param c Categoria
     * @return ArrayList de tipo Pregunta
     */
    public ArrayList<Pregunta> getPreguntasFromCategoria(Categoria c) {
        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM PREGUNTA WHERE ID_CATEGORIA = ?");
            ps.clearParameters();
            ps.setInt(1, c.getId_categoria());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pregunta p = new Pregunta(
                        rs.getInt("ID_PREGUNTA"),
                        rs.getString("TEXTO"),
                        rs.getInt("ID_CATEGORIA"),
                        null
                );
                listaPreguntas.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PreguntaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaPreguntas;
    }

    /**
     * Método que devuelve las preguntas dado un test
     *
     * @param t Test
     * @return ArrayList de tipo Pregunta
     */
    public ArrayList<Pregunta> getPreguntasFromTest(Test t) {
        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT PREGUNTA.ID_PREGUNTA AS ID_PREGUNTA, PREGUNTA.TEXTO AS TEXTO, PREGUNTA.IMAGEN AS IMAGEN, PREGUNTA.ID_CATEGORIA AS ID_CATEGORIA  FROM PREGUNTA, PREGUNTA_TEST WHERE PREGUNTA_TEST.ID_PREGUNTA = PREGUNTA.ID_PREGUNTA AND PREGUNTA_TEST.ID_TEST = ?");
            ps.clearParameters();
            ps.setInt(1, t.getId_test());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pregunta p = new Pregunta(
                        rs.getInt("ID_PREGUNTA"),
                        rs.getString("TEXTO"),
                        rs.getInt("ID_CATEGORIA"),
                        rs.getBytes("IMAGEN")
                );
                listaPreguntas.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PreguntaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPreguntas;
    }

    /**
     * Funcion que devuelve el numero de secuencia siguiente para inserta en el
     * ID de la tabla.
     *
     * @return Int de la secuencia que deber in en la insercion.
     */
    public int devuelveSequence() {
        int sequence = 0;
        if (ps == null) {
            try {
                try {
                    ps = con.prepareStatement("SELECT pregunta_seq.nextval as seq FROM dual");
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    sequence = rs.getInt("seq");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ps = null;
            }
        }
        return sequence;
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
