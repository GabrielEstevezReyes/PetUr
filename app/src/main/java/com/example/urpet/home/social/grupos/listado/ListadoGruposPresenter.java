package com.example.urpet.home.social.grupos.listado;

import com.example.urpet.connections.Group;

import java.util.ArrayList;

public class ListadoGruposPresenter implements ListadoGrupsInteractor.onInteractorListener {

    private ListadoGruposView mView;
    private ListadoGrupsInteractor mInteractor;

    public ListadoGruposPresenter(ListadoGruposView mView, ListadoGrupsInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onCallGrupos() {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.onCallAllGrupos(this);
        }
    }

    @Override
    public void onErrorGrupo() {
        if(mView != null){
            mView.onHideLoader();
            mView.onErrorListado();
        }
    }

    @Override
    public void onGruposObtenidos(ArrayList<Group> data) {
        if(mView != null){
            mView.onHideLoader();
            mView.onListadoPbtenido(data);
        }
    }
}
