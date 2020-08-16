package com.example.urpet.home.mascota.agenda.events;

public class EventoDTO {

    private String mFechaInicio;
    private String mFechaFin;
    private String mNombreEvento;

    public EventoDTO(String mFechaInicio, String mFechaFin, String mNombreEvento) {
        this.mFechaInicio = mFechaInicio;
        this.mFechaFin = mFechaFin;
        this.mNombreEvento = mNombreEvento;
    }

    public String getmFechaInicio() {
        return mFechaInicio;
    }

    public String getmFechaFin() {
        return mFechaFin;
    }

    public String getmNombreEvento() {
        return mNombreEvento;
    }
}
