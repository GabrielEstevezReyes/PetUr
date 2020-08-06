package com.example.urpet.connections;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class PetRace extends ApiPetition {

    int id_tipomascota = -1;
    String tipo = "";

    public PetRace(){

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
        res.put("id_raza", getIDtype());
        res.put("nombre", getType());
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fromJson(JSONObject obj) throws ParseException {
        setIDtype(Integer.parseInt(obj.optString("id_raza")));
        setType(obj.optString("nombre"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PetRace> read() throws ParseException {
        ArrayList<PetRace> listaParentesco = new ArrayList<>();
        PetRace parentesco = null;
        ArrayList<JSONObject> serverPets = ApiPetition.getDataList("razas", "", "");
        for (int i=0 ; i<serverPets.size(); i++){
            parentesco = new PetRace();
            parentesco.fromJson(serverPets.get(i));
            listaParentesco.add(parentesco);
        }
        return listaParentesco;
    }
}
