package com.example.urpet.home.social.grupos.listado;

import com.example.urpet.Utils.ViewInterface;
import com.example.urpet.connections.Group;

import java.util.ArrayList;

public interface ListadoGruposView extends ViewInterface {
    void onListadoPbtenido(ArrayList<Group> data);
    void onErrorListado();
}
