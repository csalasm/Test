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
import modelo.Test;
import modelo.Usuario;

/**
 *
 * @author andresbailen93
 */
public class TestDAO {

    private Connection con = null;
    PreparedStatement psSentencia = null;

    /**
     * Constructor de la clase TestDAO en el que al atributo de tipo Connection
     * se asigna una conexión llamando a la clase ConexiónOrcl
     */
    public TestDAO() {
        con = new ConexionOrcl().conecta();
    }

    /**
     * Funcion que inserta un test en la base de datos.
     *
     * @param tester Objecto de la clase Test
     */
    public void insertaTest(Test tester) {
        if (psSentencia == null) {
            try {
                psSentencia = con.prepareStatement("INSERT INTO TEST (ID_TEST,NOMBRE,DURACION,RESTA,DNI,ACTIVO) VALUES (?,?,?,?,?,?)");
                psSentencia.clearParameters();
                psSentencia.setInt(1, tester.getId_test());
                psSentencia.setString(2, tester.getNombre());
                psSentencia.setInt(3, tester.getDuracion());
                psSentencia.setInt(4, tester.getResta());
                psSentencia.setString(5, tester.getDni());
                psSentencia.setBoolean(6, tester.getActivo());
                psSentencia.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }

        }

    }

    /**
     * Actualiza si un test esta activo o no.
     *
     * @param tes
     * @param disp
     */
    public void UpdateDispo(Test tes, Boolean disp) {
        if (psSentencia == null) {
            try {
                psSentencia = con.prepareStatement("UPDATE TEST SET ACTIVO=?  WHERE ID_TEST=?");
                psSentencia.clearParameters();
                psSentencia.setBoolean(1, disp);
                psSentencia.setInt(2, tes.getId_test());
                psSentencia.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }

        }
    }

    /**
     * Devuelve las listas de Test dado un usuario.
     *
     * @param user Objeto de la clase Usuario.
     * @return ArrayList con los test de un Usuario.
     */
    public ArrayList<Test> devuelveTestes(Usuario user) {
        ArrayList<Test> lista_test = new ArrayList<>();

        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE DNI=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, user.getDni());
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Test tester;
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    tester = new Test(rs.getInt("ID_TEST"), rs.getString("NOMBRE"),
                            rs.getInt("DURACION"), rs.getInt("RESTA"),
                            rs.getString("DNI"), rs.getBoolean("ACTIVO"));
                    lista_test.add(tester);

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return lista_test;
    }

    /**
     * Devuelve los test Activos que tiene un profesor.
     *
     * @param u
     * @return
     */
    public ArrayList<Test> devuelveTestActivosProf(Usuario u) {
        ArrayList<Test> listaActivos_test = new ArrayList<>();

        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE ACTIVO=1 AND DNI = ?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, u.getDni());
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Test tester;
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    tester = new Test(rs.getInt("ID_TEST"), rs.getString("NOMBRE"),
                            rs.getInt("DURACION"), rs.getInt("RESTA"),
                            rs.getString("DNI"), rs.getBoolean("ACTIVO"));
                    listaActivos_test.add(tester);

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return listaActivos_test;

    }
    /**
     * Devuelve los Test que esten desactivados por el profesor.
     * @param u
     * @return 
     */
    public ArrayList<Test> devuelveTestDesactivosProf(Usuario u) {
        ArrayList<Test> listaDesactivos_test = new ArrayList<>();
        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE ACTIVO=0 AND DNI=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, u.getDni());
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Test tester;
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    tester = new Test(rs.getInt("ID_TEST"), rs.getString("NOMBRE"),
                            rs.getInt("DURACION"), rs.getInt("RESTA"),
                            rs.getString("DNI"), rs.getBoolean("ACTIVO"));
                    listaDesactivos_test.add(tester);

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return listaDesactivos_test;

    }

    /**
     * Funcion que devuelve todos los test de la base de datos que esten activos
     *
     * @return ArrayList con la lista de los test que estan activos
     */

    public ArrayList<Test> devuelveTestActivos(Usuario u, boolean all) {
        //DEVOLVER TEST CUYO VALOR ESTE ACTIVO.
        ArrayList<Test> listaActivos_test = new ArrayList<>();

        if (psSentencia == null) {
            try {
                try {
                    if (!all) {
                        psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE ACTIVO=1 AND ID_TEST NOT IN (SELECT ID_TEST FROM EXAMEN WHERE DNI = ?)");
                        psSentencia.clearParameters();
                        psSentencia.setString(1, u.getDni());
                    }
                    else {
                        psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE ACTIVO=1");
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Test tester;
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    tester = new Test(rs.getInt("ID_TEST"), rs.getString("NOMBRE"),
                            rs.getInt("DURACION"), rs.getInt("RESTA"),
                            rs.getString("DNI"), rs.getBoolean("ACTIVO"));
                    listaActivos_test.add(tester);

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return listaActivos_test;

    }

    /**
     * Devuelve el test dado un nombre de Test.
     *
     * @param nombre
     * @return
     */
    public Test devuelveTestNomUsuario(String nombre) {
        //DEVOLVER TEST CUYO VALOR ESTE ACTIVO.
        Test tester = null;

        if (psSentencia == null) {
            try {
                try {
                    psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE NOMBRE=?");
                    psSentencia.clearParameters();
                    psSentencia.setString(1, nombre);
                } catch (SQLException ex) {
                    Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs = psSentencia.executeQuery();
                while (rs.next()) {
                    tester = new Test(rs.getInt("ID_TEST"), rs.getString("NOMBRE"),
                            rs.getInt("DURACION"), rs.getInt("RESTA"),
                            rs.getString("DNI"), rs.getBoolean("ACTIVO"));

                }
            } catch (SQLException ex) {
                Logger.getLogger(RespuestaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                psSentencia = null;
            }
        }
        return tester;

    }

    /**
     *
     * @param id
     * @return
     */
    public Test getTest(Integer id) {
        Test t = null;
        try {
            psSentencia = con.prepareStatement("SELECT * FROM TEST WHERE ID_TEST = ?");
            psSentencia.clearParameters();
            psSentencia.setInt(1, id);
            ResultSet rs = psSentencia.executeQuery();
            while (rs.next()) {
                t = new Test(rs.getInt("ID_TEST"),
                        rs.getString("NOMBRE"),
                        rs.getInt("DURACION"),
                        rs.getInt("RESTA"),
                        rs.getString("DNI"),
                        rs.getBoolean("ACTIVO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return t;
    }

    /**
     *
     * @param nombr
     * @return
     */
    public int getIdTest(String nombr) {
        int idtest = 0;
        try {
            psSentencia = con.prepareStatement("SELECT ID_TEST FROM TEST WHERE NOMBRE = ?");
            psSentencia.clearParameters();
            psSentencia.setString(1, nombr);
            ResultSet rs = psSentencia.executeQuery();
            while (rs.next()) {
                idtest = rs.getInt("ID_TEST");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            psSentencia = null;
        }

        return idtest;
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
                    psSentencia = con.prepareStatement("SELECT test_seq.nextval as seq FROM dual");
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
