package com.example.urpet.connections;

public class BasicObject {
    private int id = -1; //ID_parentesco

    public int getID() {
        return id;
    }
    public void setID(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {
        return "Parentesco [idParentesco=" + id + "]";
    }
}
