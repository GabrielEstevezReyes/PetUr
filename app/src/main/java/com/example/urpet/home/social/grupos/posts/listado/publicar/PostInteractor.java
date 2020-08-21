package com.example.urpet.home.social.grupos.posts.listado.publicar;

import com.example.urpet.connections.social.Post;

import org.json.JSONException;

public class PostInteractor {

    public interface onInteractorInmterface{
        void onHacerPost(int idGrupo, boolean isForSale, String titulo, String descripcion, float precio, String encodedImage, int idMascota);
        void onPostExitoso();
        void onPostError();
    }

    public void onEnviarPost(onInteractorInmterface listener, int idGrupo, boolean isForSale, String titulo,
                             String descripcion, float precio, String encodedImage, int idMascota){
        Post newPost = new Post(idGrupo);
        newPost.setForSale(isForSale ? 1 : 0);
        newPost.setName(titulo);
        newPost.setDescription(descripcion);
        newPost.setPrice(precio);
        newPost.setIdMascota(idMascota);
        newPost.setID(idGrupo);
        if(!encodedImage.isEmpty()){
            newPost.setImage(encodedImage);
        }
        try {
            if(newPost.create()) {
                listener.onPostExitoso();
            }
            else {
                listener.onPostError();
            }
        } catch (JSONException e) {
            listener.onPostError();
        }
    }

}
