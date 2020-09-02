package com.example.urpet.home.mascota.opciones.grupos.listado;

import com.example.urpet.connections.social.Group;

import java.text.ParseException;
import java.util.ArrayList;

public class ListadoGrupsInteractor {

    public interface onInteractorListener{
        void onCallGrupos();
        void onErrorGrupo();
        void onGruposObtenidos(ArrayList<Group> data);
    }

    public void onCallAllGrupos(onInteractorListener listener){
        Group obtener = new Group();
        try {
            ArrayList<Group> grupos;
            grupos = obtener.readAll();
            if(grupos == null){
                listener.onErrorGrupo();
            }
            else{
                listener.onGruposObtenidos(grupos);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            listener.onErrorGrupo();
        }
    }

}