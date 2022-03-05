package com.example.lostarkcardstatus;

import android.widget.Toast;

public class CardInfo implements Comparable<CardInfo> {
    /*
     * 카드 id 등급별 id 앞 번호
     * 전설 : 1
     * 영웅 : 2
     * 희귀 : 3
     * 고급 : 4
     * 일반 : 5
     * 스페셜 : 6
     */
    private int id;                     //카드 id
    private String name;                //카드 이름
    private int count;                  //보유 카드 장수
    private int awake;                  //카드 각성도
    private String acquisition_info;    //카드 획득처 정보
    private String grade;               //카드 등급 정보
    private int getCard = 0;            //카드 획득 유무(기본 0)
    private String path;              //카드 리소스 경로

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
        if (maxHaveValue(awake) < count) {
            this.count = maxHaveValue(awake);
        } else
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
        this.acquisition_info = acquisition_info;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getGetCard() {
        return getCard;
    }

    public void setGetCard(int getCard) {
        this.getCard = getCard;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int compareTo(CardInfo cardInfo) {
        return this.getName().compareTo(cardInfo.getName());
    }

    private int maxHaveValue(int haveAwake) {
        if (haveAwake == 5) {
            return 0;
        } else if (haveAwake == 4) {
            return 5;
        } else if (haveAwake == 3) {
            return 9;
        } else if (haveAwake == 2) {
            return 12;
        } else if (haveAwake == 1) {
            return 14;
        } else if (haveAwake == 0) {
            return 15;
        } else
            return 0;
    }
}
