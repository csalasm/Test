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
import modelo.Categoria;

/**
 *
 * @author andresbailen93
 */
public class CategoriaDAO {

    private Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    /**
     * Contructor de la clase CategoríaDAO, en el que al atributo de tipo
     * Connection se asigna una conexión llamando a la clase ConexiónOrcl
     */
    public CategoriaDAO() {
        this.con = new ConexionOrcl().conecta();
    }

    /**
     * Procedimiento que inserta una nueva categoría en el BBDD
     *
     * @param cat Recibe como parámetro un objeto de la clase Categoría
     */
    public void InsertarCategoria(Categoria cat) {

        try {
            pstmt = con.prepareStatement("INSERT INTO CATEGORIA VALUES (?,?)");
            pstmt.clearParameters();
            pstmt.setInt(1, cat.getId_categoria());
            pstmt.setString(2, cat.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pstmt = null;
        }

    }

    /**
     * Función que devuelve una lista con todos los objetos categorias que
     * existen en la base de datos
     *
     * @return ArrayList de tipos Categoria
     */
    public ArrayList<Categoria> ListarCategorias() {

        ArrayList<Categoria> lista_categorias = new ArrayList<>();

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORIA");

            Categoria cat;

            while (rs.next()) {

                cat = new Categoria(rs.getInt("ID_CATEGORIA"), rs.getString("NOMBRE"));
                lista_categorias.add(cat);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stmt = null;
        }

        return lista_categorias;
    }

    /**
     * Funcion que devuelve el numero de secuencia siguiente para inserta en el
     * ID de la tabla.
     *
     * @return Int de la secuencia que deber in en la insercion.
     */
    public int devuelveSequence() {
        int sequence = 0;
        if (pstmt == null) {
            try {
                try {
                    pstmt = con.prepareStatement("SELECT categoria_seq.nextval as seq FROM dual");
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    sequence = rs.getInt("seq");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                pstmt = null;
            }
        }
        return sequence;
    }

    /**
     * Funcion que devuelve el ID_Categoria dado un nombre de categoria.
     * @param nombr Nombre de la categoria.
     * @return Idenfiticador de la categoria.
     */
       public int devuelveCategoria(String nombr) {
           int idcategoria=-1;
        if (pstmt == null) {
            try {
                try {
                    pstmt = con.prepareStatement("SELECT ID_CATEGORIA  FROM CATEGORIA WHERE NOMBRE=?");
                    pstmt.clearParameters();
                    pstmt.setString(1, nombr);
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    idcategoria = rs.getInt("ID_CATEGORIA");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                pstmt = null;
            }
        }
        return idcategoria;
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
