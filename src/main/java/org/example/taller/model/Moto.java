package org.example.taller.model;
/**
 * Clase Moto
 * Se utiliza para crear nuevas motos extiende de vehiculo
 */
public class Moto extends Vehiculo {

    private String tiempos;
    private boolean maleta;

    public Moto(String matricula, String marca, String modelo, String fichaTecnica, String seguro, String tiempos, boolean maleta) {
        super(matricula, marca, modelo, fichaTecnica, seguro);
        this.tiempos = tiempos;
        this.maleta = maleta;
    }
    public String getTiempos() {
        return tiempos;
    }
    public void setTiempos(String tiempos) {
        this.tiempos = tiempos;
    }
    public boolean isMaleta() {
        return maleta;
    }
    public void setMaleta(boolean maleta) {
        this.maleta = maleta;
    }
}
