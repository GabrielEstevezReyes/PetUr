package com.example.urpet.connections;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User extends BasicObject {
    private String tableName = "usuarios";
    private String name = "";
    private String lastName = "";
    private String mail = "";
    private String smsCC = "";
    private String addr = "";
    private String phoneNumber = "";
    private String birthDate = ""; //solo se maneja en la app, no en la base de datos
    private String base64Image = "";

    public String toString(){
        return "User [ID=" + getID() + "]" + "[Name=" + name + "]";
    }

    public User(String supposedMail){
        setMail(supposedMail);
    }

    public User(int supposedID){
        setID(supposedID);
    }

    public void getImageSafe(){
    }

    public void create(String newName, String newLastN, String newMail,String newPhoneNumber) throws JSONException {
        setName(newName);
        setLastName(newLastN);
        setMail(newMail);
        setPhoneNumber(newPhoneNumber);
        ApiPetition.insertData("usuarios", toJson(false));
    }

    public void createShort(String newName, String newMail) throws JSONException {
        setName(newName);
        setMail(newMail);
        ApiPetition.insertData("usuarios", toJson(false));
    }

    public void getIDSafe(){
        fromJson(ApiPetition.getData(tableName, "Correo", getMail()));
    }

    public List<String> getAllPhoneNumbers(){
        List<String> phones = new ArrayList();
        return phones;
    }

    public void getFromMail() {
        JSONObject obj = ApiPetition.getData(tableName, "Correo", getMail());
        if(obj != null){
            Log.println(Log.DEBUG, "usuario recibido:[", obj.toString() + "]");
            fromJson(obj);
        }
        else{
            Log.println(Log.DEBUG, "usuario recibido: ", "NULO");
            setID(-1);
        }
    }

    public PairM getFromID(int idSearch) {
        PairM users = new PairM("", "", -1);
        return users;
    }

    public void update(boolean newBase) throws JSONException {
        String imagee = "";
        if(newBase){
            imagee ="', Image = '" + getBase64Image();
        }
        Log.println(Log.DEBUG, "usuarioactualizar: ", toJson(true).toString());
        ApiPetition.updateData("usuarios", toJson(true));
    }

    public JSONObject toJson(boolean ID) throws JSONException {
        JSONObject res = new JSONObject();
        res.put("nombre", getName());
        res.put("apellidos", getLastName());
        res.put("correo", getMail());
        res.put("telefono", getPhoneNumber());
        res.put("direccion", getAddr());
        res.put("smsconf", getSmsCC());
        res.put("fecha_nacimiento", getBirthDate());
        if(ID){
            res.put("id_usuario", getID());
        }
        res.put("image", getBase64Image());
        return res;
    }

    public void fromJson(JSONObject obj)
    {
        setName(obj.optString("nombre"));
        setLastName(obj.optString("apellidos"));
        setMail(obj.optString("correo"));
        setPhoneNumber(obj.optString("telefono"));
        setAddr(obj.optString("direccion"));
        setSmsCC(obj.optString("smsconf"));
        setBirthDate(obj.optString("fecha_nacimiento"));
        setBase64Image(obj.optString("image"));
        setID(Integer.parseInt(obj.optString("id_usuario")));
    }

    public boolean delete(String id) {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSmsCC() {
        return smsCC;
    }

    public void setSmsCC(String smsCC) {
        this.smsCC = smsCC;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
