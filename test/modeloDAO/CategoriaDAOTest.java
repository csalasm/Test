/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloDAO;

import java.util.ArrayList;
import modelo.Categoria;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andresbailen93
 */
public class CategoriaDAOTest {
    
    public CategoriaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of InsertarCategoria method, of class CategoriaDAO.
     * Prueba Insertar una Categoria existente.
     */
    @Test
    public void testInsertarCategoria() {
        System.out.println("InsertarCategoria");
        Categoria cat = null;
        CategoriaDAO instance = new CategoriaDAO();
        instance.InsertarCategoria(cat);
        Categoria cate=new Categoria(1,"TCP");
        Boolean a=instance.InsertarCategoria(cate);
        if(a){
            fail("Fallo insercion de dos Categorias iguales");
        }

    }
    

    /**
     * Test of ListarCategorias method, of class CategoriaDAO.
     */
    @Test
    public void testListarCategorias() {
        System.out.println("ListarCategorias");
        CategoriaDAO instance = new CategoriaDAO();
        ArrayList<Categoria> result = instance.ListarCategorias();
        assertNotNull(result);
    }

    /**
     * Test of devuelveCategoria method, of class CategoriaDAO.
     */
    @Test
    public void testDevuelveCategoria() {
        System.out.println("devuelveCategoria");
        String nombr = "TCP";
        CategoriaDAO instance = new CategoriaDAO();
        int result = instance.devuelveCategoria(nombr);
        assertNotNull(result);
    }

    /**
     * Test of finalize method, of class CategoriaDAO.
     */

    
}
