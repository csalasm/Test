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
public class Respuesta {

    private int id_respuesta;
    private String texto;
    private boolean correcta;
    private int id_pregunta;

    /**
     * Constructor por defecto.
     */
    public Respuesta() {
    }

    /**
     * Constructor con parametros.
     *
     * @param id_respuesta identificador de la respuesta.
     * @param texto enunciado de la respuesta.
     * @param correcta indica si es la respuesta correcta o no.
     * @param id_pregunta identificador de la pregunta.
     */
    public Respuesta(int id_respuesta, String texto, boolean correcta, int id_pregunta) {
        this.id_respuesta = id_respuesta;
        this.texto = texto;
        this.correcta = correcta;
        this.id_pregunta = id_pregunta;
    }

    /**
     * Devuelve el identificador de la respuesta
     *
     * @return identificador de la respuesta
     */
    public int getId_respuesta() {
        return id_respuesta;
    }

    /**
     * Devuelve el enunciado de la respuesta.
     *
     * @return enunciado de la respuesta.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Devuelve si es la opcion correcta o no.
     *
     * @return si es correcta o no.
     */
    public boolean isCorrecta() {
        return correcta;
    }

    /**
     * Devuelve el identificador de la pregunta.
     *
     * @return identificador de la pregunta.
     */
    public int getId_pregunta() {
        return id_pregunta;
    }

    /**
     * Inserta el identificador de la respuesta.
     *
     * @param id_respuesta identificador de la respuesta.
     */
    public void setId_respuesta(int id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    /**
     * Inserta el enunciado de la respuesta.
     *
     * @param texto enunciado de la respuesta.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Inserta si es la opcion correcta de la respuesta.
     *
     * @param correcta indica si es la opcion correcta.
     */
    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }

    /**
     * Inserta el identificador de la pregunta.
     *
     * @param id_pregunta identificador de la pregunta.
     */
    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }
    
    @Override
    public String toString() {
        return "Respuesta{" + "id_respuesta=" + id_respuesta + ", texto=" + texto + ", correcta=" + correcta + ", id_pregunta=" + id_pregunta + '}';
    }

}
