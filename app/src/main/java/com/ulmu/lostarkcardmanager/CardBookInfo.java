package com.ulmu.lostarkcardmanager;

import java.util.ArrayList;

public class CardBookInfo implements Comparable<CardBookInfo> {
    private ArrayList<CardInfo> cardInfo;
    private int id;         //id
    private String name;    //이름
    private int value;      //도감 완성시 증가되는 스탯 수치
    private String card0;   //카드0번 이름
    private String card1;   //카드1번 이름
    private String card2;   //카드2번 이름
    private String card3;   //카드3번 이름
    private String card4;   //카드4번 이름
    private String card5;   //카드5번 이름
    private String card6;   //카드6번 이름
    private String card7;   //카드7번 이름
    private String card8;   //카드8번 이름
    private String card9;   //카드9번 이름
    private String option;  //도감 완성시 증가되는 스탯 종류
    private int needCard;   //도감 완성에 필요한 카드 수
    private int haveCard;   //현재 수집한 카드 수

    public CardBookInfo() {
        this.cardInfo = ((MainPage)MainPage.mainContext).cardInfo;
        this.haveCard = 0;
    }

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCard0() {
        return card0;
    }

    public void setCard0(String card0) {
        this.card0 = card0;
    }

    public String getCard1() {
        return card1;
    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public String getCard2() {
        return card2;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }

    public String getCard3() {
        return card3;
    }

    public void setCard3(String card3) {
        this.card3 = card3;
    }

    public String getCard4() {
        return card4;
    }

    public void setCard4(String card4) {
        this.card4 = card4;
    }

    public String getCard5() {
        return card5;
    }

    public void setCard5(String card5) {
        this.card5 = card5;
    }

    public String getCard6() {
        return card6;
    }

    public void setCard6(String card6) {
        this.card6 = card6;
    }

    public String getCard7() {
        return card7;
    }

    public void setCard7(String card7) {
        this.card7 = card7;
    }

    public String getCard8() {
        return card8;
    }

    public void setCard8(String card8) {
        this.card8 = card8;
    }

    public String getCard9() {
        return card9;
    }

    public void setCard9(String card9) {
        this.card9 = card9;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    //도감 완성에 필요한 카드
    public int getNeedCard() {
        needCard = 0;
        if (!getCard0().isEmpty()) needCard++;
        if (!getCard1().isEmpty()) needCard++;
        if (!getCard2().isEmpty()) needCard++;
        if (!getCard3().isEmpty()) needCard++;
        if (!getCard4().isEmpty()) needCard++;
        if (!getCard5().isEmpty()) needCard++;
        if (!getCard6().isEmpty()) needCard++;
        if (!getCard7().isEmpty()) needCard++;
        if (!getCard8().isEmpty()) needCard++;
        if (!getCard9().isEmpty()) needCard++;
        return needCard;
    }

    //현재 보유 카드
    public int getHaveCard() {
        haveCard = 0;
        if (isCheckCard0()) haveCard++;
        if (isCheckCard1()) haveCard++;
        if (isCheckCard2()) haveCard++;
        if (isCheckCard3()) haveCard++;
        if (isCheckCard4()) haveCard++;
        if (isCheckCard5()) haveCard++;
        if (isCheckCard6()) haveCard++;
        if (isCheckCard7()) haveCard++;
        if (isCheckCard8()) haveCard++;
        if (isCheckCard9()) haveCard++;

        return haveCard;
    }

    //X번 카드 보유 유무
    public boolean isCheckCard0() {
        return getCardCheck(getCard0());
    }

    public boolean isCheckCard1() {
        return getCardCheck(getCard1());
    }

    public boolean isCheckCard2() {
        return getCardCheck(getCard2());
    }

    public boolean isCheckCard3() {
        return getCardCheck(getCard3());
    }

    public boolean isCheckCard4() {
        return getCardCheck(getCard4());
    }

    public boolean isCheckCard5() {
        return getCardCheck(getCard5());
    }

    public boolean isCheckCard6() {
        return getCardCheck(getCard6());
    }

    public boolean isCheckCard7() {
        return getCardCheck(getCard7());
    }

    public boolean isCheckCard8() {
        return getCardCheck(getCard8());
    }

    public boolean isCheckCard9() {
        return getCardCheck(getCard9());
    }
    

    //완성도 순 정렬에 호출할 함수
    public int getSubComplete() {
        if (getNeedCard() - getHaveCard() == 1)
            return 1;
        else if (getNeedCard() - getHaveCard() == 2)
            return 2;
        else if (getNeedCard() - getHaveCard() == 3)
            return 3;
        else if (getNeedCard() - getHaveCard() == 4)
            return 4;
        else if (getNeedCard() - getHaveCard() == 5)
            return 5;
        else if (getNeedCard() - getHaveCard() == 6)
            return 6;
        else if (getNeedCard() - getHaveCard() == 7)
            return 7;
        else if (getNeedCard() - getHaveCard() == 8)
            return 8;
        else if (getNeedCard() - getHaveCard() == 9)
            return 9;
        else if(getNeedCard() - getHaveCard() == 10)
            return 10;
        else
            return 0;
    }

    @Override
    public int compareTo(CardBookInfo o) {
        return this.getName().compareTo(o.getName());
    }


    private boolean getCardCheck(String cardX) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardX) && cardInfo.get(i).getGetCard())
                return true;
        }
        return false;
    }

}
