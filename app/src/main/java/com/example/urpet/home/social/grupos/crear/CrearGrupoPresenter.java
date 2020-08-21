package com.example.urpet.home.social.grupos.crear;

import com.example.urpet.connections.social.Group;

public class CrearGrupoPresenter implements CrearGrupoInteractor.onInteractorListener {

    private CrearGrupoView mView;
    private CrearGrupoInteractor mInteractor;

    public CrearGrupoPresenter(CrearGrupoView mView, CrearGrupoInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onCreado() {
        if(mView != null){
            mView.onHideLoader();
            mView.onGrupoCreado();
        }
    }

    @Override
    public void onError() {
        if(mView != null){
            mView.onHideLoader();
            mView.onGrupoError();
        }
    }

    @Override
    public void onCrearGrupo(Group grupo) {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.crearGrupo(this, grupo);
        }
    }
}
