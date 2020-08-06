package com.example.urpet.connections;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.urpet.PersonalInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class PetType extends ApiPetition {

    int id_tipomascota = -1;
    String tipo = "";

    public PetType(){

    }

    public String getType(){
        return tipo;
    }

    public void setType(String type){
        tipo = type;
    }

    public int getIDtype(){
        return id_tipomascota;
    }

    public void setIDtype(int id){
        id_tipomascota = id;
    }

    public JSONObject toJson(boolean ID) throws JSONException {
        JSONObject res = new JSONObject();
        res.put("id_tipomascota", getIDtype());
        res.put("nombre", getType());
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fromJson(JSONObject obj) throws ParseException {
        setIDtype(Integer.parseInt(obj.optString("id_tipomascota")));
        setType(obj.optString("nombre"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PetType> read() throws ParseException {
        ArrayList<PetType> listaParentesco = new ArrayList<>();
        PetType parentesco = null;
        ArrayList<JSONObject> serverPets = ApiPetition.getDataList("tiposMascotas", "", "");
        for (int i=0 ; i<serverPets.size(); i++){
            parentesco = new PetType();
            parentesco.fromJson(serverPets.get(i));
            listaParentesco.add(parentesco);
        }
        return listaParentesco;
    }
}
