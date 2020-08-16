package com.example.urpet.home.social.grupos.posts.listado.publicar;

public class PostPresenter implements PostInteractor.onInteractorInmterface {

    private PostView mView;
    private PostInteractor mInteractor;

    public PostPresenter(PostView mView, PostInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onHacerPost(int idGrupo, boolean isForSale, String titulo, String descripcion,
                            float precio, String encodedImage, int idmascota) {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.onEnviarPost(this, idGrupo, isForSale, titulo, descripcion, precio, encodedImage, idmascota);
        }
    }

    @Override
    public void onPostExitoso() {
        if(mView != null){
            mView.onHideLoader();
            mView.onPostCreado();
        }
    }

    @Override
    public void onPostError() {
        if(mView != null){
            mView.onHideLoader();
            mView.onPostError();
        }
    }
}
