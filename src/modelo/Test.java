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
public class Test {

    private int id_test;
    private String nombre;
    private int duracion;
    private int resta;
    private String dni;
    private Boolean activo;

    /**
     * Constructor por defecto.
     */
    public Test() {
    }

    /**
     * Constructor con parametros
     *
     * @param id_test Identificacion del test
     * @param nombre Nombre del test
     * @param duracion Duracion en segundos del test
     * @param resta Indica si 0 si al fallar no resta, 1 resta lo mismo que al
     *  la pregunta, 2 resta la mitad, 3 resta la tercera parte
     * @param dni Indica el DNI de la persona que esta haciendo el test.
     * @param activo 
     */
    public Test(int id_test, String nombre, int duracion, int resta, String dni,Boolean activo) {
        this.id_test = id_test;
        this.nombre = nombre;
        this.duracion = duracion;
        this.resta = resta;
        this.dni = dni;
        this.activo=activo;
    }

    /**
     * Funcion que devuelve el identificador del test.
     *
     * @return identificador del test
     */
    public int getId_test() {
        return id_test;
    }

    /**
     * Funcion que devuelve el Nombre del test
     *
     * @return nombre del test
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Funcion que devuelve la duracion en segundos del test.
     *
     * @return duracion del test.
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Funcion que indica si resta una pregunta no acertada, y cuanto resta.
     *
     * @return resta devuelve lo que resta una pregunta mal contestada.
     */
    public int getResta() {
        return resta;
    }

    /**
     * Funcion que devuelve el Dni.
     *
     * @return dni Devuelve el DNI.
     */
    public String getDni() {
        return dni;
    }
    /**
     * Funcion que devuelve si un test esta activo o no.
     * @return boolean indica si test esta activo o no
     */
    public Boolean getActivo(){
        return activo;
    }

    /**
     * Funcion que inserta el identificador del test.
     *
     * @param id_test Indica el identificador del test.
     */
    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    /**
     * Funcion que inserta el Nombre del test.
     *
     * @param nombre Indica el nombre del test
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Funcion que inserta la duracion del test.
     *
     * @param duracion Indica la duracion del test en segundos.
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Funcion que inserta lo que resta una pregunta mal contestada.
     *
     * @param resta indica lo que resta una funcion mal contestada.
     */
    public void setResta(int resta) {
        this.resta = resta;
    }

    /**
     * Funcion que inserta el DNI.
     *
     * @param dni Indica el dni de una persona
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Funcion que inserta el estado de un test (Activo=1, No activo=0).
     * @param activo Boolean que indica el estado del test.
     */
    public void setStart(Boolean activo){
        this.activo=activo;
    }

    @Override
    public String toString() {
        return "Test{" + "id_test=" + id_test + ", nombre=" + nombre + ", duracion=" + duracion + ", resta=" + resta + ", dni=" + dni + ", activo=" + activo + '}';
    }
}
