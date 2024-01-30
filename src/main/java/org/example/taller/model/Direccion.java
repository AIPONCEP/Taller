package org.example.taller.model;

public class Direccion{
    private Integer num;
    private String calle;
    private String ciudad;
    private String cp;
    public Direccion(int num, String calle, String ciudad, String cp) {
        this.num = num;
        this.calle = calle;
        this.ciudad = ciudad;
        this.cp = cp;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getCp() {
        return cp;
    }
    public void setCp(String cp) {
        this.cp = cp;
    }
}
