package org.example.taller.model;

/**
 * Clase Persona
 * extiended de Dirreccion
 */
public class Persona extends Direccion {
    private String nombre;
    public Persona(String nombre,Integer num, String calle, String ciudad, String cp) {
        super(num, calle, ciudad, cp);
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
