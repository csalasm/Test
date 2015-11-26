/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author andresbailen93
 */
public class Categoria {
    
    private int id_categoria;
    private String nombre;

    
    /**
     * Constructor de la clase Categoría
     * @param id_categoria Identificador de la categoría
     * @param nombre Nombre de la categoría
     */
    public Categoria(int id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    
    
    
    /**
     * Función que devuelve el ID de la categoría
     * @return ID de la categoría
     */
    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Procedimiento que establece un ID para una categoría
     * @param id_categoria Recibe como parámetro un ID para la categoría
     */
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    /**
     * Función que devuelve el Nombre de una categoría
     * @return Nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Procedimiento que establece el nombre de una categoría
     * @param nombre Recibe como parámetro el nombre de la categoría
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
