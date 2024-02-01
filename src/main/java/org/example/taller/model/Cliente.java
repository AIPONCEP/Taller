package org.example.taller.model;

import java.sql.ResultSet;
import java.util.Arrays;

public class Cliente extends Persona {
    private Integer idClient;
    private String tlfs;
    private Integer numCoches;

    public Cliente(Integer idClient, String nombre, Integer num, String calle, String ciudad, String cp, Integer numCoches, String tlfs) {
        super(nombre, num, calle, ciudad, cp);
        this.idClient = idClient;
        this.tlfs = tlfs;
        this.numCoches = numCoches;
    }
    public Cliente(String nombre, Integer num, String calle, String ciudad, String cp, Integer numCoches, String tlfs) {
        super(nombre, num, calle, ciudad, cp);
        this.tlfs = Arrays.toString(convertirString(tlfs));
        this.numCoches = numCoches;
    }

    public Integer getIdClient() {
        return idClient;
    }
    public String getTlfs() {
        return tlfs;
    }
    public void setTlfs(String tlfs) {
        this.tlfs = tlfs;
    }
    public Integer getNumCoches() {
        return numCoches;
    }
    public void setNumCoches(Integer numCoches) {
        this.numCoches = numCoches;
    }


    public static String[] convertirString(String texto){
        String[] vector;
        if(texto.contains(",")){
            vector= texto.split(",");
            return vector;
        }else{
            vector= new String[]{texto};
            return vector;
        }
    }

}