package org.example.taller.model;

public class Reparacion {
    private Integer idServicio;
    private Integer idMecanico;
    private String matricula;
    private String fecha;
    private String estado;

    public Reparacion(Integer idServicio, Integer idMecanico, String matricula, String fecha, String estado) {
        this.idServicio = idServicio;
        this.idMecanico = idMecanico;
        this.matricula = matricula;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
