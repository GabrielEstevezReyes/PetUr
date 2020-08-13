package com.example.urpet.connections;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Post extends BasicObject {

    private String name = "";
    private String description = "";
    private String image= "";
    private float price = 0;
    private int forSale = 0;
    private int groupBelong = 0;
    private int idMascota;
    private int poster = 0;
    private String fecha = "";

    public String toString(){
        return "User [ID=" + getID() + "]" + "[Name=" + name + "]" + "[Description=" + description + "]";
    }

    public Post(int group){
        setGroupBelong(group);
    }

    public boolean create() throws JSONException {
        return ApiPetition.insertDatar("posts", toJson());
    }


    public JSONObject toJson() throws JSONException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        JSONObject res = new JSONObject();
        res.put("tittle", getName());
        res.put("descripcion", getDescription());
        res.put("price", getPrice() * 1.0d);
        res.put("for_sale", getForSale());
        res.put("pinned", 0);
        res.put("fecha", date.format(Calendar.getInstance().getTime()));
        res.put("imagen", getImage());
        res.put("idgrupos", getID());
        res.put("id_mascota", getIdMascota());
        return res;
    }

    public ArrayList<Post> getAllPosts(int idGrupo) {
        ArrayList<Post> mListadoPosts = new ArrayList<>();
        ArrayList<JSONObject> listadoJson;
        listadoJson = ApiPetition.getDataList("posts", "", "");
        if(listadoJson == null){
            mListadoPosts = null;
        }
        else{
            for(int i = 0; i < listadoJson.size(); i++){
                JSONObject post = listadoJson.get(i);
                if(post.optInt("idgrupos") == idGrupo){
                    mListadoPosts.add(new Post(post));
                }
            }
        }

        return mListadoPosts;
    }

    public Post(JSONObject data) {
        name = data.optString("tittle");
        description = data.optString("descripcion");
        image= data.optString("imagen");
        price = data.optInt("price");
        forSale = data.optInt("for_sale");
        idMascota = data.optInt("id_mascota");
        fecha = data.optString("fecha");
        groupBelong = data.optInt("idgrupo");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getForSale() {
        return forSale;
    }

    public void setForSale(int forSale) {
        this.forSale = forSale;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public int getGroupBelong() {
        return groupBelong;
    }

    public void setGroupBelong(int groupBelong) {
        this.groupBelong = groupBelong;
    }
}
