package com.ulmu.lostarkcardmanager;

public class FavoriteCardSetInfo {
    private String name;        //이름
    private int awake;          //각성도
    private boolean activation;     //즐겨찾기 활성이 되어있는가

    public FavoriteCardSetInfo() {
        this.activation = false;
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

    public boolean getActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

}
