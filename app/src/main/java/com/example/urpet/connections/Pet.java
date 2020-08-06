package com.example.urpet.connections;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.urpet.PersonalInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Pet extends BasicObject {

    private String name = null;
    private int ownerId = -1;
    private String race = "";
    private String type = "";
    private String color = "";
    private String bornDate = "";
    private String description = "";
    private String base64Image = "";
    private int age = 0;
    private String food = "-1";
    private String carnet = "-1";
    private String route = "-1";
    private String status = "-1";
    private String URL = "";

    public Pet(String supposedName){
        setName(supposedName);
    }
    public Pet(){}
    public String getName() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void create(String newName, int OID, String nRace, String nType, String nColor, String nDescription, String image64, String bDate) throws JSONException, ParseException {
        setName(newName);
        setOwnerId(OID);
        setRace(nRace);
        setType(nType);
        setColor(nColor);
        setDescription(nDescription);
        setBase64Image(URL);
        setBornDate(bDate);
        Log.println(Log.DEBUG, "JSON:", "[" + toJson(false) + "]");
        ApiPetition.insertData("mascotas", toJson(false));
    }

    public void update(boolean newBase) throws JSONException {
        ApiPetition.updateData("mascotas", toJson(true));
    }

    public void delete(){
        ApiPetition.deleteData("mascotas", getID());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Pet> read() throws ParseException {
        ArrayList<Pet> listaParentesco = new ArrayList<Pet>();
        Pet parentesco = null;
        ArrayList<JSONObject> serverPets = ApiPetition.getDataList("mascotas", "IDUsuario", String.valueOf(PersonalInfo.currentUser.getID()));
        for (int i=0 ; i<serverPets.size(); i++){
            parentesco = new Pet();
            parentesco.fromJson(serverPets.get(i));
            listaParentesco.add(parentesco);
        }
        return listaParentesco;
    }

    public String toString(){
        return "Pet [ID=" + getID() + "]" + "[OwnerId=" + ownerId + "]" + "[PetId=" + getID() + "]" + "[Name=" + name + "]";
    }

    public JSONObject toJson(boolean ID) throws JSONException {
        JSONObject res = new JSONObject();
        res.put("nombre", getName());
        res.put("idraza", getRace());
        res.put("idtipomascota", getType());
        res.put("color", getColor());
        res.put("descripcion", getDescription());
        res.put("alimento", getFood());
        res.put("carnet", getCarnet());
        res.put("recorridos", getRoute());
        res.put("estatus", getStatus());
        res.put("fecha_nacimiento", getBornDate());
        res.put("idusuario", getOwnerId());
        if(ID){
            res.put("id_mascota", getID());
        }
        res.put("image", getBase64Image());
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fromJson(JSONObject obj) throws ParseException {
        setName(obj.optString("nombre"));
        setRace(obj.optString("idraza"));
        setType(obj.optString("idtipomascota"));
        setColor(obj.optString("color"));
        setDescription(obj.optString("descripcion"));
        setFood(obj.optString("alimento"));
        setCarnet(obj.optString("carnet"));
        setRoute(obj.optString("recorridos"));
        setStatus(obj.optString("estatus"));
        setBornDate(obj.optString("fecha_nacimiento"));
        setBase64Image(obj.optString("image"));
        setID(Integer.parseInt(obj.optString("id_mascota")));
        setOwnerId(Integer.parseInt(obj.optString("idusuario")));
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }


    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBornDate() {
        return bornDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBornDate(String borndate) throws ParseException {
        if(borndate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = sdf.parse(borndate);
                LocalDate today = LocalDate.now();                          //Today's date
                Period p = Period.between(convertToLocalDateViaInstant(birthDate), today);
                setAge(p.getYears());
            } catch (ParseException e) {
                e.printStackTrace();
                setAge(0);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                setAge(0);
            }
            this.bornDate = borndate;
        }
        else{
            setAge(0);
            Log.println(Log.INFO, "kk", "NO FECHA");
            this.bornDate = "";
        }
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
