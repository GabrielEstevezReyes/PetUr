package com.example.urpet.connections;
import java.util.ArrayList;
import java.util.List;

public class BelongGroup extends BasicObject {
    private int user;
    private int group;

    public BelongGroup(int u, int g){
        setUser(u);
        setGroup(g);
    }

    public void create(){

    }

    public void requestJoin(){

    }

    public List<Integer> getFromUser(){
        List<Integer> ret = new ArrayList<Integer>();
        return ret;
    }

    public List<Integer> getRequests(){
        List<Integer> ret = new ArrayList<Integer>();
        return ret;
    }

    public void deleteJoin(){
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
