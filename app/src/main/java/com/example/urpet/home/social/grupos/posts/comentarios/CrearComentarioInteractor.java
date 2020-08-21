package com.example.urpet.home.social.grupos.posts.comentarios;

import com.example.urpet.connections.social.Comentario;

import org.json.JSONException;

public class CrearComentarioInteractor {

    public interface CrearComentarioListener{
        void onComentar(int idPost, int idMascota, String comentario);
        void onComentarioExitoso();
        void onComentarError();
    }

    public void Comentar(CrearComentarioListener listener, int idPost, int idMascota, String comentario){
        Comentario c = new Comentario(idPost, idMascota, comentario);
        try {
            if(c.create()) {
                listener.onComentarioExitoso();
            }
            else {
                listener.onComentarError();
            }
        } catch (JSONException e) {
            listener.onComentarError();
        }
    }

}
