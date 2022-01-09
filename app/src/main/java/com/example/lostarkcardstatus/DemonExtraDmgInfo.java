package com.example.lostarkcardstatus;

public class DemonExtraDmgInfo {
    private int id;
    private String name;
    private String card0;
    private String card1;
    private String card2;
    private String card3;
    private String card4;
    private String card5;
    private String card6;
    private String card7;
    private String card8;
    private String card9;
    private int awake_sum0;
    private int awake_sum1;
    private int awake_sum2;
    private float dmg_p0;
    private float dmg_p1;
    private float dmg_p2;
    private int haveAwake;
    private int cardListSum;
    private int haveCard;
    private int checkCard0;
    private int checkCard1;
    private int checkCard2;
    private int checkCard3;
    private int checkCard4;
    private int checkCard5;
    private int checkCard6;
    private int checkCard7;
    private int checkCard8;
    private int checkCard9;

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

    public int getAwake_sum0() {
        return awake_sum0;
    }

    public void setAwake_sum0(int awake_sum0) {
        this.awake_sum0 = awake_sum0;
    }

    public int getAwake_sum1() {
        return awake_sum1;
    }

    public void setAwake_sum1(int awake_sum1) {
        this.awake_sum1 = awake_sum1;
    }

    public int getAwake_sum2() {
        return awake_sum2;
    }

    public void setAwake_sum2(int awake_sum2) {
        this.awake_sum2 = awake_sum2;
    }

    public float getDmg_p0() {
        return dmg_p0;
    }

    public void setDmg_p0(float dmg_p0) {
        this.dmg_p0 = dmg_p0;
    }

    public float getDmg_p1() {
        return dmg_p1;
    }

    public void setDmg_p1(float dmg_p1) {
        this.dmg_p1 = dmg_p1;
    }

    public float getDmg_p2() {
        return dmg_p2;
    }

    public void setDmg_p2(float dmg_p2) {
        this.dmg_p2 = dmg_p2;
    }

    public float getDmgSum(int awakeInfo){
        float result = 0;
        if(awakeInfo < getAwake_sum0())     //각성합이 최소 각성 미만일시
            result = 0;
        else if(getAwake_sum0() <= awakeInfo && awakeInfo < getAwake_sum1())    //각성합이 첫번째 각성 조건 달성시
            result = getDmg_p0();
        else if(getAwake_sum1() <= awakeInfo && awakeInfo < getAwake_sum2())    //각성합이 두번째 각성 조건 달성시
            result = getDmg_p0()+getDmg_p1();
        else if(getAwake_sum2() <= awakeInfo)                                   //각성합이 최대 조건 달성시
            result = getDmg_p0()+getDmg_p1()+getDmg_p2();

        return result;
    }

    public int getHaveAwake() {
        return haveAwake;
    }

    public void setHaveAwake(int haveAwake) {
        this.haveAwake = haveAwake;
    }

    public int getCompleteDEDBook() {
        return cardListSum;
    }

    public void setCompleteDEDBook(int cardListSum) {
        this.cardListSum = cardListSum;
    }

    public int getHaveCard() {
        return haveCard;
    }

    public void setHaveCard(int haveCard) {
        this.haveCard = haveCard;
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
}
