package com.example.urpet.home.social.grupos.posts.comentarios;

public class CrearComentarioPresenter implements CrearComentarioInteractor.CrearComentarioListener {

    private CrearComentarioView mView;
    private CrearComentarioInteractor mInteractor;

    public CrearComentarioPresenter(CrearComentarioView mView, CrearComentarioInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onComentar(int idPost, int idMascota, String comentario) {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.Comentar(this, idPost, idMascota, comentario);
        }
    }

    @Override
    public void onComentarioExitoso() {
        if(mView != null){
            mView.onHideLoader();
            mView.onComentarioEnviado();
        }
    }

    @Override
    public void onComentarError() {
        if(mView != null){
            mView.onHideLoader();
            mView.onComentarioError();
        }
    }
}
