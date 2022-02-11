package com.example.lostarkcardstatus;

import java.util.Arrays;

public class CardSetInfo implements Comparable<CardSetInfo> {

    private int id;
    private String name;
    private String card0;
    private String card1;
    private String card2;
    private String card3;
    private String card4;
    private String card5;
    private String card6;
    private String set_bonus0;
    private String set_bonus1;
    private String set_bonus2;
    private String set_bonus3;
    private String set_bonus4;
    private String set_bonus5;
    private int haveCard;   //세트 활성화에 필요한 최소 카드 수
    private int haveAwake;
    private int checkCard0;
    private int checkCard1;
    private int checkCard2;
    private int checkCard3;
    private int checkCard4;
    private int checkCard5;
    private int checkCard6;
    private int awakeCard0;
    private int awakeCard1;
    private int awakeCard2;
    private int awakeCard3;
    private int awakeCard4;
    private int awakeCard5;
    private int awakeCard6;
    private String favorite;

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

    public String getSet_bonus0() {
        return set_bonus0;
    }

    public void setSet_bonus0(String set_bonus0) {
        this.set_bonus0 = set_bonus0;
    }

    public String getSet_bonus1() {
        return set_bonus1;
    }

    public void setSet_bonus1(String set_bonus1) {
        this.set_bonus1 = set_bonus1;
    }

    public String getSet_bonus2() {
        return set_bonus2;
    }

    public void setSet_bonus2(String set_bonus2) {
        this.set_bonus2 = set_bonus2;
    }

    public String getSet_bonus3() {
        return set_bonus3;
    }

    public void setSet_bonus3(String set_bonus3) {
        this.set_bonus3 = set_bonus3;
    }

    public String getSet_bonus4() {
        return set_bonus4;
    }

    public void setSet_bonus4(String set_bonus4) {
        this.set_bonus4 = set_bonus4;
    }

    public String getSet_bonus5() {
        return set_bonus5;
    }

    public void setSet_bonus5(String set_bonus5) {
        this.set_bonus5 = set_bonus5;
    }

    public int getHaveCard() {
        return haveCard;
    }

    public void setHaveCard(int haveCard) {
        this.haveCard = haveCard;
    }

    public int getHaveAwake() { //카드세트 효과 발동을 위한 각성도 합
        int min = 6;
        int check = checkCard0 + checkCard1 + checkCard2 + checkCard3 + checkCard4 + checkCard5 + checkCard6;
        int[] awakeArray = {awakeCard0, awakeCard1, awakeCard2, awakeCard3, awakeCard4, awakeCard5, awakeCard6};
        if (getHaveCard() < check) {
            for (int i = 0; i < awakeArray.length; i++) {
                if (awakeArray[i] < min)
                    min = awakeArray[i];
            }
            haveAwake = (awakeCard0 + awakeCard1 + awakeCard2 + awakeCard3 + awakeCard4 + awakeCard5 + awakeCard6) - min;
        } else
            haveAwake = awakeCard0 + awakeCard1 + awakeCard2 + awakeCard3 + awakeCard4 + awakeCard5 + awakeCard6;

        return haveAwake;
    }

    public void setHaveAwake(int haveAwake) {

        this.haveAwake = haveAwake;
    }

    public int getCheckCard0() {
        return checkCard0;
    }

    public void setCheckCard0(int checkCard0) {
        this.checkCard0 = checkCard0;
    }

    public int getCheckCard1() {
        return checkCard1;
    }

    public void setCheckCard1(int checkCard1) {
        this.checkCard1 = checkCard1;
    }

    public int getCheckCard2() {
        return checkCard2;
    }

    public void setCheckCard2(int checkCard2) {
        this.checkCard2 = checkCard2;
    }

    public int getCheckCard3() {
        return checkCard3;
    }

    public void setCheckCard3(int checkCard3) {
        this.checkCard3 = checkCard3;
    }

    public int getCheckCard4() {
        return checkCard4;
    }

    public void setCheckCard4(int checkCard4) {
        this.checkCard4 = checkCard4;
    }

    public int getCheckCard5() {
        return checkCard5;
    }

    public void setCheckCard5(int checkCard5) {
        this.checkCard5 = checkCard5;
    }

    public int getCheckCard6() {
        return checkCard6;
    }

    public void setCheckCard6(int checkCard6) {
        this.checkCard6 = checkCard6;
    }

    public int getAwakeCard0() {
        return awakeCard0;
    }

    public void setAwakeCard0(int awakeCard0) {
        this.awakeCard0 = awakeCard0;
    }

    public int getAwakeCard1() {
        return awakeCard1;
    }

    public void setAwakeCard1(int awakeCard1) {
        this.awakeCard1 = awakeCard1;
    }

    public int getAwakeCard2() {
        return awakeCard2;
    }

    public void setAwakeCard2(int awakeCard2) {
        this.awakeCard2 = awakeCard2;
    }

    public int getAwakeCard3() {
        return awakeCard3;
    }

    public void setAwakeCard3(int awakeCard3) {
        this.awakeCard3 = awakeCard3;
    }

    public int getAwakeCard4() {
        return awakeCard4;
    }

    public void setAwakeCard4(int awakeCard4) {
        this.awakeCard4 = awakeCard4;
    }

    public int getAwakeCard5() {
        return awakeCard5;
    }

    public void setAwakeCard5(int awakeCard5) {
        this.awakeCard5 = awakeCard5;
    }

    public int getAwakeCard6() {
        return awakeCard6;
    }

    public void setAwakeCard6(int awakeCard6) {
        this.awakeCard6 = awakeCard6;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public int compareTo(CardSetInfo cardSetInfo) {
        return this.getName().compareTo(cardSetInfo.getName());
    }

}

