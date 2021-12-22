package com.example.lostarkcardstatus;

public class CardInfo {
    private int id;                     //카드 id
    private String name;                //카드 이름
    private int count;                  //보유 카드 장수
    private int awake;                  //카드 각성도
    private String acquisition_info;    //카드 획득 정보

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }

    public String getAcquisition_info() {
        return acquisition_info;
    }

    public void setAcquisition_info(String acquisition_info) {
        if(acquisition_info == null)
            return;
        this.acquisition_info = acquisition_info;
    }
}
