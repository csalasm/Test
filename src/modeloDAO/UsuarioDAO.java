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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 *
 * @author andresbailen93
 */
public class UsuarioDAO {

    private Connection con = null;
    PreparedStatement psSentencia = null;

    /**
     * Constructor por defecto que instancia la conexion con la base de datos.
     */
    public UsuarioDAO() {

        con = new ConexionOrcl().conecta();
    }

    /**
     * Funcion que inserta un usuario en la base de datos usando
     * PreparedStatement
     *
     * @param usua Objeto de la clase Usuario.
     */
    public void insertaUsuario(Usuario usua) {

        if (null == psSentencia) {
            try {
                psSentencia = con.prepareStatement("INSERT INTO USUARIO (DNI,NOMBRE,APELLIDOS,PASSWORD,ES_PROF) VALUES (?,?,?,?,?)");
                psSentencia.clearParameters();
                psSentencia.setString(1, usua.getDni());
                psSentencia.setString(2, usua.getNombre());
                psSentencia.setString(3, usua.getApellidos());
                psSentencia.setString(4, usua.getContraseña());
                psSentencia.setBoolean(5, usua.isEs_profesor());
                psSentencia.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }

        }
    }

    /**
     * Funcion que comprueba el loggin de un usuario
     *
     * @param dni DNI de un usuario
     * @param password Contraseña del usuario
     * @return Devuelve un true si son correctos los datos introducidos o false
     * si no son correctos.
     */
    public Usuario logginUser(String dni, String password) {
        String userdni = dni;
        String userpass = password;
        boolean loggin = false;
        Usuario user = null;

        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM USUARIO WHERE DNI=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, dni);
 
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    user = new Usuario(rs.getString("DNI"), rs.getString("NOMBRE"),
                            rs.getString("APELLIDOS"), rs.getString("PASSWORD"),
                            rs.getBoolean("ES_PROF"));
                    userdni = rs.getString("dni");
                    userpass = rs.getString("password");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }

            if (dni.equals(userdni) && password.equals(userpass)) {
                loggin = true;
                return user;
            }
        }
        return null;
    }

    /**
     * Funcion que comprueba si un usuario es profesor.
     *
     * @param user Objecto de la clase Usuario.
     * @return Devuelve true si es profesor y false en otro caso.
     */
    public boolean isProfesor(Usuario user) {
        Boolean isprofesor = false;
        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT ES_PROF FROM USUARIO WHERE DNI=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, user.getDni());
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    isprofesor = rs.getBoolean("ES_PROF");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return isprofesor;

    }

    /**
     * Funcion que devuelve un objeto Usuario.
     *
     * @param dni DNI del objeto que se quiere obtener la informacion
     * @return Devuelve un Objeto de la clase Usuario.
     */
    public Usuario devuelveUsuario(String dni) {
        Usuario user = null;
        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM USUARIO WHERE DNI=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, dni);
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    user = new Usuario(rs.getString("dni"), rs.getString("nombre"),
                            rs.getString("APELLIDOS"), rs.getString("PASSWORD"),
                            rs.getBoolean("es_prof"));

                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return user;
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
