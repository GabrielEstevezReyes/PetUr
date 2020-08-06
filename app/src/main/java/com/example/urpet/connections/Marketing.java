package com.example.urpet.connections;


public class Marketing extends BasicObject {

    private int userID = -1;
    private int vet = 0;
    private int food = 0;
    private int nearbyP = 0;
    private int tips = 0;
    private int stores = 0;
    private int events = 0;

    public Marketing(int uID){
        setUserID(uID);
    }

    public void turnAll(){
        setVet(1);
        setFood(1);
        setNearbyP(1);
        setTips(1);
        setStores(1);
        setEvents(1);
    }

    public void get(){
    }

    public void create(){
    }

    public void update(){
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVet() {
        return vet;
    }

    public void setVet(int vet) {
        this.vet = vet;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getNearbyP() {
        return nearbyP;
    }

    public void setNearbyP(int nearbyP) {
        this.nearbyP = nearbyP;
    }

    public int getTips() {
        return tips;
    }

    public void setTips(int tips) {
        this.tips = tips;
    }

    public int getStores() {
        return stores;
    }

    public void setStores(int stores) {
        this.stores = stores;
    }

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }
}
