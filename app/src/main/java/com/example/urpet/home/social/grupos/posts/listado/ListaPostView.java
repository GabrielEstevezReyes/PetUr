package com.example.urpet.home.social.grupos.posts.listado;

import com.example.urpet.Utils.ViewInterface;
import com.example.urpet.connections.Post;

import java.util.ArrayList;

public interface ListaPostView extends ViewInterface {
    void muestraListado(ArrayList<Post> data);
    void errorListado();
}
