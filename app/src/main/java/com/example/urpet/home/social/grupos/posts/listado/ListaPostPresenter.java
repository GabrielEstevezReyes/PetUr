package com.example.urpet.home.social.grupos.posts.listado;

import com.example.urpet.connections.Post;

import java.util.ArrayList;

public class ListaPostPresenter implements ListaPostInteractor.onInteractorInterface {

    private ListaPostView mView;
    private ListaPostInteractor mInteractor;

    public ListaPostPresenter(ListaPostView mView, ListaPostInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onTraerPosts(int idGrupo) {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.onCallPosts(this, idGrupo);
        }
    }

    @Override
    public void onMuestraPosts(ArrayList<Post> data) {
        if(mView != null){
            mView.onHideLoader();
            mView.muestraListado(data);
        }
    }

    @Override
    public void onError() {
        if(mView != null){
            mView.onHideLoader();
            mView.errorListado();
        }
    }
}
