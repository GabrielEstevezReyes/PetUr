package com.example.urpet.home.social.grupos.posts;

import com.example.urpet.connections.social.Comentario;

import java.util.ArrayList;

public class PostPresenter implements PostInteractor.OnInteractorInterface {

    private PostView mView;
    private PostInteractor mInteractor;

    public PostPresenter(PostView mView, PostInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void onCallComentarios(int idPost) {
        if(mView != null){
            mView.onShowLoader();
            mInteractor.callComentarios(this, idPost);
        }
    }

    @Override
    public void onGetComentarios(ArrayList<Comentario> mlist) {
        if(mView != null){
            mView.onHideLoader();
            mView.onGetListadoComentarios(mlist);
        }
    }

    @Override
    public void onError() {
        if(mView != null){
            mView.onHideLoader();
            mView.onError();
        }
    }
}
