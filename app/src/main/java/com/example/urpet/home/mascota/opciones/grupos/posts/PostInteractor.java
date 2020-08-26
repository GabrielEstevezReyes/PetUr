package com.example.urpet.home.mascota.opciones.grupos.posts;

import com.example.urpet.connections.social.Comentario;

import java.util.ArrayList;

public class PostInteractor {

    public interface OnInteractorInterface{
        void onCallComentarios(int idPost);
        void onGetComentarios(ArrayList<Comentario> mlist);
        void onError();
    }

    public void callComentarios(OnInteractorInterface mListener, int idPost){
        Comentario C = new Comentario();
        ArrayList<Comentario> mListComents;
        mListComents = C.getAllComments(idPost);
        if(mListComents == null){
            mListener.onError();
        } else{
            mListener.onGetComentarios(mListComents);
        }
    }

}
