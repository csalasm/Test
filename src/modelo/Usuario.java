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
public class Usuario {
    
    private String dni;
    private String nombre;
    private String apellidos;
    private String password;
    private Boolean es_profesor;
/**
 * Constructor por defecto.
 */
    public Usuario() {
    }

    
    /**
     * Constructor de la clase usuario
     * @param dni DNI del usuario
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param contraseña Contraseña del usuario
     * @param es_profesor Booleano que indica si el usuario es profesor, en caso de que su valor sea 'false' indica que es alumno 
     */
    public Usuario(String dni, String nombre, String apellidos, String contraseña, boolean es_profesor) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = contraseña;
        this.es_profesor = es_profesor;
    }

    /**
     * Funcion que devuelve el DNI del usuario
     * @return DNI del usuario
     */
    public String getDni() {
        return dni;
    }

    /**
     * Procedimiento que establece el DNI de un usuario
     * @param dni Recibe como parámetro el DNI del usuario
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Funcion que devuelve el Nombre de un usuario
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Procedimiento que establece el nombre de un usuario
     * @param nombre Recibe como parámetro el nombre del usuario 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" + "dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", password=" + password + ", es_profesor=" + es_profesor + '}';
    }

    /**
     * Funcion que devuelve los apellidos de un usuario
     * @return Apellidos del usuario
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Procedimiento que establece los apellidos de un usuario
     * @param apellidos Recibe como parámetro los apellidos del usuario
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Función que devuelve la contraseña de un usuario
     * @return Contraseña del usuario
     */
    public String getContraseña() {
        return password;
    }

    /**
     * Procedimiento que establece la contraseña de un usuario
     * @param contraseña Recibe como parámetro la contraseña del usuario
     */
    public void setContraseña(String contraseña) {
        this.password = contraseña;
    }

    /**
     * Funcion que devuelve si el usuario es profesor o no
     * @return True si es profesor o false si es alumno
     */
    public Boolean isEs_profesor() {
        return es_profesor;
    }

    /**
     * Procedimiento que establece si un usuario es profesor o alumno
     * @param es_profesor Recibe True si es profesor o False si es alumno
     */
    public void setEs_profesor(boolean es_profesor) {
        this.es_profesor = es_profesor;
    }
    
    
    
    
    
}
