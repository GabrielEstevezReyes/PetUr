package com.example.urpet.connections.social;

import com.example.urpet.connections.ApiPetition;
import com.example.urpet.connections.BasicObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Comentario extends BasicObject implements Serializable {

    private int idPost;
    private int idComentario;
    private int idMascota;
    private String fecha;
    private String comentario;

    private static final String COMENTARIO_KEY = "comentario";
    private static final String FECHA_KEY = "fecha";
    private static final String ID_MASCOTA_KEY = "id_mascota";
    private static final String ID_POST_KEY = "idpost";

    public boolean create() throws JSONException {
        return ApiPetition.insertDatar("comentarios", toJson());
    }

    public JSONObject toJson() throws JSONException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        JSONObject res = new JSONObject();
        res.put(COMENTARIO_KEY, comentario);
        res.put(FECHA_KEY, date.format(Calendar.getInstance().getTime()));
        res.put(ID_MASCOTA_KEY, idMascota);
        res.put(ID_POST_KEY, idPost);
        return res;
    }

    public ArrayList<Comentario> getAllComments(int idPost) {
        ArrayList<Comentario> mListadoComentarios = new ArrayList<>();
        ArrayList<JSONObject> listadoJson;
        listadoJson = ApiPetition.getDataList("comentarios", "", "");
        if(listadoJson == null){
            mListadoComentarios = null;
        }
        else{
            for(int i = 0; i < listadoJson.size(); i++){
                JSONObject comentario = listadoJson.get(i);
                if(comentario.optInt("idpost") == idPost){
                    mListadoComentarios.add(new Comentario(comentario));
                }
            }
        }

        return mListadoComentarios;
    }

    public Comentario() {
    }

    public Comentario(int idPost, int idMascota, String comentario) {
        this.idPost = idPost;
        this.idMascota = idMascota;
        this.comentario = comentario;
    }

    public Comentario(JSONObject data) {
        idPost = data.optInt("id_post");
        idComentario = data.optInt("id_comment");
        comentario = data.optString("comentario");
        idMascota = data.optInt("id_mascota");
        fecha = data.optString("fecha");
    }

    public int getIdPost() {
        return idPost;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public String getFecha() {
        return fecha;
    }

    public String getComentario() {
        return comentario;
    }
}
