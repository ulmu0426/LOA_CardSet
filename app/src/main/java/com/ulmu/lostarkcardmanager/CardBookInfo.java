package com.ulmu.lostarkcardmanager;

public class CardBookInfo implements Comparable<CardBookInfo> {
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
    private int cardListSum;//완성에 필요한 카드 수
    private int haveCard;   //현재 수집한 카드 수
    private int checkCard0; //0번 카드 획득 유무
    private int checkCard1; //1번 카드 획득 유무
    private int checkCard2; //2번 카드 획득 유무
    private int checkCard3; //3번 카드 획득 유무
    private int checkCard4; //4번 카드 획득 유무
    private int checkCard5; //5번 카드 획득 유무
    private int checkCard6; //6번 카드 획득 유무
    private int checkCard7; //7번 카드 획득 유무
    private int checkCard8; //8번 카드 획득 유무
    private int checkCard9; //9번 카드 획득 유무


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

    public int getCompleteCardBook() {
        return cardListSum;
    }

    public void setCompleteCardBook(int cardListSum) {
        this.cardListSum = cardListSum;
    }

    public int getHaveCard() {
        return haveCard = checkCard0 + checkCard1 + checkCard2 + checkCard3 + checkCard4 + checkCard5 + checkCard6 + checkCard7 + checkCard8 + checkCard9;
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

    public int getCheckCard7() {
        return checkCard7;
    }

    public void setCheckCard7(int checkCard7) {
        this.checkCard7 = checkCard7;
    }

    public int getCheckCard8() {
        return checkCard8;
    }

    public void setCheckCard8(int checkCard8) {
        this.checkCard8 = checkCard8;
    }

    public int getCheckCard9() {
        return checkCard9;
    }

    public void setCheckCard9(int checkCard9) {
        this.checkCard9 = checkCard9;
    }

    //완성도 순 정렬에 호출할 함수
    public int getSubComplete() {
        if (getCompleteCardBook() - getHaveCard() == 1)
            return 1;
        else if (getCompleteCardBook() - getHaveCard() == 2)
            return 2;
        else if (getCompleteCardBook() - getHaveCard() == 3)
            return 3;
        else if (getCompleteCardBook() - getHaveCard() == 4)
            return 4;
        else if (getCompleteCardBook() - getHaveCard() == 5)
            return 5;
        else if (getCompleteCardBook() - getHaveCard() == 6)
            return 6;
        else if (getCompleteCardBook() - getHaveCard() == 7)
            return 7;
        else if (getCompleteCardBook() - getHaveCard() == 8)
            return 8;
        else if (getCompleteCardBook() - getHaveCard() == 9)
            return 9;
        else if(getCompleteCardBook() - getHaveCard() == 10)
            return 10;
        else
            return 0;
    }

    @Override
    public int compareTo(CardBookInfo o) {
        return this.getName().compareTo(o.getName());
    }
}
