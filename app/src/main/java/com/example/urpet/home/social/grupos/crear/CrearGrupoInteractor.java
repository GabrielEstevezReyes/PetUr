package com.example.urpet.home.social.grupos.crear;

import com.example.urpet.connections.social.Group;

public class CrearGrupoInteractor {

    public interface onInteractorListener{
        void onCreado();
        void onError();
        void onCrearGrupo(Group grupo);
    }

    public void crearGrupo(onInteractorListener listener, Group grupo){
        if(grupo.createGroup()){
            listener.onCreado();
        } else{
            listener.onError();
        }
    }


}
