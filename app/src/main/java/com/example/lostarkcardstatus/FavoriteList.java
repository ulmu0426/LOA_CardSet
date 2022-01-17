package com.example.lostarkcardstatus;

public class FavoriteList {
    private String name;
    private int awake;

    public FavoriteList(String name, int haveAwake) {
        this.name = name;
        this.awake = haveAwake;
    }

    public FavoriteList() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }
}
