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
import modelo.Pregunta;
import modelo.Respuesta;

/**
 *
 * @author andresbailen93
 */
public class RespuestaDAO {

    private Connection con = null;
    PreparedStatement psSentencia = null;

    /**
     * Constructor por defecto que instancia la conexion con la base de datos.
     */
    public RespuestaDAO() {
        con = new ConexionOrcl().conecta();
    }

    /**
     * Funcion que inserta una Respuesta en la base de datos.
     *
     * @param resp Objeto de la case Respuesta.
     */
    public void insertaRespuesta(Respuesta resp) {
        if (psSentencia == null) {
            try {
                psSentencia = con.prepareStatement("INSERT INTO RESPUESTA (ID_RESPUESTA,TEXTO,CORRECTA,ID_PREGUNTA) VALUES (?,?,?,?)");
                psSentencia.clearParameters();
                psSentencia.setInt(1, resp.getId_respuesta());
                psSentencia.setString(2, resp.getTexto());
                psSentencia.setBoolean(3, resp.isCorrecta());
                psSentencia.setInt(4, resp.getId_pregunta());
                psSentencia.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }

        }

    }

    /**
     * Funcion que devuelve un ArrayList con las posibles respuestas.
     *
     * @param preg Objeto de la clase Pregunta.
     * @return ArrayList con las posibles respuestas.
     */
    public ArrayList<Respuesta> devuelveRespuesta(Pregunta preg) {
        ArrayList<Respuesta> lista_respuesta = new ArrayList<>();

        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM RESPUESTA WHERE ID_PREGUNTA=?");
                    psSentencia.clearParameters();
                    psSentencia.setInt(1, preg.getId_pregunta());
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Respuesta respuesta;
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    respuesta = new Respuesta(rs.getInt("ID_RESPUESTA"),
                            rs.getString("TEXTO"), rs.getBoolean("CORRECTA"),
                            rs.getInt("ID_PREGUNTA"));
                    lista_respuesta.add(respuesta);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return lista_respuesta;
    }
    
    public Respuesta getRespuestaCorrecta(Pregunta p) {
        Respuesta r = null;
        try {
            psSentencia = con.prepareStatement("SELECT * FROM RESPUESTA WHERE CORRECTA = 1 AND ID_PREGUNTA = ?");
            psSentencia.clearParameters();
            psSentencia.setInt(1, p.getId_pregunta());
            
            ResultSet rs = psSentencia.executeQuery();
            while (rs.next()) {
                r = new Respuesta (
                        rs.getInt("ID_RESPUESTA"),
                        rs.getString("TEXTO"),
                        rs.getBoolean("CORRECTA"),
                        rs.getInt("ID_PREGUNTA")
                );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    /**
     * Funcion que devuelve el numero de secuencia siguiente para inserta en el
     * ID de la tabla.
     *
     * @return Int de la secuencia que deber in en la insercion.
     */
    public int devuelveSequence() {
        int sequence = 0;
        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT pregunta_seq.nextval as seq FROM dual");
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    sequence = rs.getInt("seq");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
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
