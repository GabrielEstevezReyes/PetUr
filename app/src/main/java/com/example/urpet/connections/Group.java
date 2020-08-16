package com.example.urpet.connections;

import android.util.Log;

import com.example.urpet.PersonalInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class Group extends BasicObject {

    private String name;
    private String description;
    private String image = "";
    private int isClosed;
    private int creatorID;

    public String toString(){
        return "User [ID=" + getID() + "]" + "[Name=" + name + "]" + "[Creator=" + creatorID + "]";
    }

    public Group(){
        setCreatorID(PersonalInfo.currentUser.getID());
    }

    public Group(String newName, String newDescription, int closed){
        setName(newName);
        setDescription(newDescription);
        setIsClosed(closed);
        setImage("");
        setCreatorID(PersonalInfo.clickedPet.getID());
    }

    public JSONObject toJson(boolean ID) throws JSONException {
        JSONObject res = new JSONObject();
        res.put("nombre", getName());
        res.put("esCerrado", getIsClosed());
        res.put("id_mascota", PersonalInfo.clickedPet.getID());
        res.put("descripcion", getDescription());
        if(ID){
            res.put("id_grupos", getID());
        }
        res.put("image", getImage());
        return res;
    }

    public void fromJson(JSONObject obj) throws ParseException {
        setName(obj.optString("nombre"));
        setDescription(obj.optString("descripcion"));
        setIsClosed(Integer.parseInt(obj.optString("esCerrado")));
        setImage(obj.optString("image"));
        setID(Integer.parseInt(obj.optString("id_grupos")));
        setCreatorID(Integer.parseInt(obj.optString("id_mascota")));
    }

    public void create() throws JSONException {
        ApiPetition.insertDatar("grupos", toJson(false));
    }

    public boolean createGroup() {
        try{
            return ApiPetition.insertDatar("grupos", toJson(false));
        } catch (Exception ex){
            return false;
        }
    }

    public void update() throws JSONException {
        ApiPetition.updateData("grupos", toJson(true));
    }

    public void getImageSafe(){

    }

    public Group getFromName(String name1){
        Group parentesco = null;
        return parentesco;
    }

    public ArrayList<Group> readAll() throws ParseException {
        ArrayList<Group> listaGrupos = new ArrayList<Group>();
        Group parentesco = null;
        ArrayList<JSONObject> recibidosWeb = ApiPetition.getDataList("grupos", "", "");
        for(int i = 0; i<recibidosWeb.size(); i++){
            parentesco = new Group();
            parentesco.fromJson(recibidosWeb.get(i));
            listaGrupos.add(parentesco);
        }
        return listaGrupos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
