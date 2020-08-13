package com.example.urpet.home.social.post;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.urpet.PersonalInfo;
import com.example.urpet.connections.Post;

public class PostInteractor {

    public interface onInteractorInmterface{
        void onHacerPost(int idGrupo, boolean isForSale, String titulo, String descripcion, float precio, String encodedImage);
        void onPostExitoso();
        void onPostError();
    }

    public void onEnviarPost(onInteractorInmterface listener, int idGrupo, boolean isForSale, String titulo,
                             String descripcion, float precio, String encodedImage){
        Post newPost = new Post(idGrupo);
        if(isForSale){
            newPost.setForSale(1);
        }
        newPost.setName(titulo);
        newPost.setDescription(descripcion);
        newPost.setPrice(precio);
        if(!encodedImage.isEmpty()){
            newPost.setImage(encodedImage);
        }
        if(newPost.create()) {
            listener.onPostExitoso();
        }
        else {
            listener.onPostError();
        }
    }

}
