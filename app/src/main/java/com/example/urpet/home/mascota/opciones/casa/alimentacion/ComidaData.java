package com.example.urpet.home.mascota.opciones.casa.alimentacion;

public class ComidaData {
    private String nombre;
    private String descripcion;
    private String presentacion;
    private String fecha;

    public ComidaData(String nombre, String descripcion, String presentacion, String fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String getFecha() {
        return fecha;
    }
}
