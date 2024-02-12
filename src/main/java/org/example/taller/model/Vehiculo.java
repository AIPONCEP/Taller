package org.example.taller.model;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private String fichaTecnica;
    private String seguro;

    public Vehiculo(String matricula, String marca, String modelo, String fichaTecnica, String seguro) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.fichaTecnica = fichaTecnica;
        this.seguro = seguro;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getFichaTecnica() {
        return fichaTecnica;
    }
    public void setFichaTecnica(String fichaTecnica) {
        this.fichaTecnica = fichaTecnica;
    }
    public String getSeguro() {
        return seguro;
    }
    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }
}
