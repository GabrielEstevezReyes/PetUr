package com.example.urpet.home.mascota.opciones.grupos.posts.listado;

import com.example.urpet.Utils.ViewInterface;
import com.example.urpet.connections.social.Post;

import java.util.ArrayList;

public interface ListaPostView extends ViewInterface {
    void muestraListado(ArrayList<Post> data);
    void errorListado();
}
