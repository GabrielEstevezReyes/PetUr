package com.example.urpet.home.social.grupos.posts;

import com.example.urpet.Utils.ViewInterface;
import com.example.urpet.connections.social.Comentario;

import java.util.ArrayList;

public interface PostView extends ViewInterface {
    void onGetListadoComentarios(ArrayList<Comentario> mComentarios);
    void onError();
}
