package com.example.urpet.home.social.grupos.posts.listado;

import com.example.urpet.connections.Post;

import java.util.ArrayList;

public class ListaPostInteractor {

    public interface onInteractorInterface{
        void onTraerPosts(int idGrupo);
        void onMuestraPosts(ArrayList<Post> data);
        void onError();
    }

    public void onCallPosts(onInteractorInterface listener, int idGrupo){
        ArrayList<Post> listado;
        Post p = new Post(idGrupo);
        listado = p.getAllPosts(idGrupo);
        if(listado == null){
            listener.onError();
        } else{
            listener.onMuestraPosts(listado);
        }
    }


}
