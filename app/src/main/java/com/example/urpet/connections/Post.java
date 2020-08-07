package com.example.urpet.connections;


import java.util.ArrayList;
import java.util.Calendar;

public class Post extends BasicObject {

    private String name = "";
    private String description = "";
    private String image= "";
    private float price = 0;
    private int forSale = 0;
    private int groupBelong = 0;
    private int poster = 0;
    private String fecha = "";

    public String toString(){
        return "User [ID=" + getID() + "]" + "[Name=" + name + "]" + "[Description=" + description + "]";
    }

    public Post(int group){
        setGroupBelong(group);
    }

    public boolean create(){
        boolean resultado = false;
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        int hours = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        int seconds = cldr.get(Calendar.SECOND);
        String date = day + "/" + (month + 1) + "/" + year + "." + hours + ":" + minutes + ":" + seconds;

        return resultado;
    }

    public ArrayList<Post> readFromGroup() {

        ArrayList<Post> listaParentesco = new ArrayList<Post>();
        Post parentesco = null;

        return listaParentesco;
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