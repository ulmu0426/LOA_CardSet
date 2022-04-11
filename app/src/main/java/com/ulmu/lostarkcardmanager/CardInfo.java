package com.ulmu.lostarkcardmanager;

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
    private int num;                  //보유 카드 장수
    private int awake;                  //카드 각성도
    private String acquisition_info;    //카드 획득처 정보
    private String grade;               //카드 등급 정보
    private boolean getCard = false;            //카드 획득 유무(기본 0)
    private String path;                //카드 리소스 경로

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        if (maxHaveValue(awake) < num) {
            this.num = maxHaveValue(awake);
        } else
            this.num = num;
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

    public boolean getGetCard() {   //각성도가 1이상 있다면 획득한 카드로 침
        if (awake > 0) {
            getCard = true;
        }
        return getCard;
    }

    public void setGetCard(boolean getCard) {
        this.getCard = getCard;
    }

    public int getAcquired(){
        if(getCard)
            return 1;
        else
            return 0;
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

    //각성 수치에 따른 최대 보유 카드 수 제한을 위한 함수
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
