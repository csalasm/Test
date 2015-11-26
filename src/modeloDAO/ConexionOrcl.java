/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Bailen Jimenez
 */
public class ConexionOrcl {

    Connection con = null;

    public ConexionOrcl() {
    }

    /**
     * Funcion que devuelve el objeto de la clase Connection
     *
     * @return Objeto Connection.
     */
    public Connection getCon() {
        return con;
    }

    /**
     * Funcion conecta, que establece la conexion con la base de datos de oracle
     * cogiendo los parametros de un fichero .properties
     *
     * @return devuelve un objeto de la clase Connection
     */
    public Connection conecta() {
        try {

            Properties pr1 = new Properties();
            try (FileInputStream conexionconf = new FileInputStream("./conexionconf.properties")) {
                pr1.load(conexionconf);
            }
            String conexion = "jdbc:oracle:" + pr1.getProperty("tipoConexion")
                    + ":" + pr1.getProperty("user") + "/" + pr1.getProperty("pass") + "@"
                    + pr1.getProperty("host") + ":" + pr1.getProperty("port") + ":"
                    + pr1.getProperty("SID");

            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(conexion);

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ConexionOrcl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }

        } catch (IOException ex) {
            Logger.getLogger(ConexionOrcl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    /**
     * Funcion que desconecta la conexion con la base de datos.
     *
     * @param descon Como parametro tiene un objeto de la clase Connection para
     * que cierre la conexion de ese objeto.
     */

    public void desconecta(Connection descon) {
        try {
            descon.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionOrcl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
