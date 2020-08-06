package com.example.urpet.connections;

public class PairM {
    private String name = "";
    private String Image = "";
    private int ID;

    public PairM(String n, String I, int i){
        setName(n);
        setImage(I);
        setID(i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
