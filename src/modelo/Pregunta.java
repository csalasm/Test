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
public class Pregunta {
    
    private int id_pregunta;
    private String texto;
    private int id_categoria;

   
    private byte[] imagen; ///////////Revisar

    /**
     * Constructor de la case Pregunta
     * @param id_pregunta Identificador de la pregunta
     * @param texto Texto que compone la pregunta
     * @param id_categoria Identificador de la categoría a la que pertenece la pregunta
     * @param imagen Imagen que ilustra a la pregunta
     */
    public Pregunta(int id_pregunta, String texto, int id_categoria, byte[] imagen) {
        this.id_pregunta = id_pregunta;
        this.texto = texto;
        this.id_categoria = id_categoria;
        this.imagen = imagen;
    }

    /**
     * Función que devuelve el ID de una pregunta
     * @return ID de la pregunta
     */
    public int getId_pregunta() {
        return id_pregunta;
    }

    /**
     * Procedimiento que establece el ID de una pregunta
     * @param id_pregunta Recibe como parámetro el ID de la pregunta
     */
    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    /**
     * Función que devuelve el texto de una pregunta
     * @return Texto que compone la pregunta
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Procedimiento que establece el texto de una pregunta
     * @param texto Recibe como parámetro el texto de una pregunta
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Función que recibe el ID de la categoría a la que corresponde una pregunta
     * @return ID de la categoría a la que pertenece la pregunta
     */
    public int getId_categoría() {
        return id_categoria;
    }

    /**
     * Procedimiento que establece el ID de la categoría a la que pertenece la pregunta
     * @param id_categoría Recibe como parámetro el ID de la categoría a la que pertenece
     */
    public void setId_categoría(int id_categoría) {
        this.id_categoria = id_categoría;
    }

   /**
    * Función que devuelve la imagen que ilustra a una pregunta
    * @return Imagen que ilustra a la pregunta
    */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * Procedimiento que establece la imagen que ilustra a una pregunta
     * @param imagen Recibe como parámetro la imagen
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    
    @Override
    public String toString() {
        return "Pregunta{" + "id_pregunta=" + id_pregunta + ", texto=" + texto + ", id_categor\u00eda=" + id_categoria + ", imagen=" + imagen + '}';
    }
    
    
    
}
