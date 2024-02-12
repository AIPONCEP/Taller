package org.example.taller.model;

public class Coche  extends Vehiculo{

    private Integer puertas;
    private String software;

    public Coche(String matricula, String marca, String modelo, String fichaTecnica, String seguro, Integer puertas, String software) {
        super(matricula, marca, modelo, fichaTecnica, seguro);
        this.puertas = puertas;
        this.software = software;
    }
    public Integer getPuertas() {
        return puertas;
    }
    public void setPuertas(Integer puertas) {
        this.puertas = puertas;
    }
    public String getSoftware() {
        return software;
    }
    public void setSoftware(String software) {
        this.software = software;
    }
}
