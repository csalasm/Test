/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author andresbailen93
 */
public class Examen {

    private String dni;
    private int id_test;
    private Date fecha;
    private int aciertos;
    private int fallos;
    private double nota;

    /**
     * Constructor por defecto.
     */

    public Examen() {
    }

    /**
     * Constructor con parametros.
     *
     * @param dni DNI de una persona.
     * @param id_test Identificador del test.
     * @param fecha Fecha del examen.
     * @param aciertos Numero de aciertos en el examen.
     * @param fallos Numero de fallos en el examen.
     * @param nota Nota final en el examen
     */
    public Examen(String dni, int id_test, Date fecha, int aciertos, int fallos, double nota) {
        this.dni = dni;
        this.id_test = id_test;
        this.fecha = fecha;
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.nota = nota;
    }

    /**
     * Funcion que devuelve el DNI
     *
     * @return DNI.
     */

    public String getDni() {
        return dni;
    }

    /**
     * Funcion que devuelve el identificador del test.
     *
     * @return identificador del test.
     */
    public int getId_test() {
        return id_test;
    }

    /**
     * Funcion que devuelve la fecha del examen.
     *
     * @return Calendar devuelve la fecha del examen.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Funcion que devuelve los aciertos.
     *
     * @return el numero de aciertos en el examen.
     */
    public int getAciertos() {
        return aciertos;
    }

    /**
     * Funcion que devuelve el numero de fallos.
     *
     * @return el numero de fallos en el examen.
     */
    public int getFallos() {
        return fallos;
    }

    /**
     * Funcion que devuelve la nota del examen.
     *
     * @return la nota total del examen.
     */
    public double getNota() {
        return nota;
    }

    /**
     * Funcion que inserta el DNI
     *
     * @param dni DNI de la persona que hace el examen.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Funcion que inserta el identificador del test.
     *
     * @param id_test identificador del test.
     */
    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    /**
     * Funcion que inserta la fecha del examen
     *
     * @param fecha fecha del examen
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Funcion que inserta el numero de aciertos.
     *
     * @param aciertos numero de aciertos del examen.
     */
    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    /**
     * Funcion que inserta el numero de fallos.
     *
     * @param fallos numero de fallos del examen.
     */
    public void setFallos(int fallos) {
        this.fallos = fallos;
    }

    /**
     * Funcion que inserta la nota obtenida en el examen.
     *
     * @param nota Nota total del examen
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Examen{" + "dni=" + dni + ", id_test=" + id_test + ", fecha=" + fecha + ", aciertos=" + aciertos + ", fallos=" + fallos + ", nota=" + nota + '}';
    }
    
    

}
