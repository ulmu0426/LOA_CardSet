package com.ulmu.lostarkcardmanager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DemonExtraDmgInfo implements Comparable<DemonExtraDmgInfo> {
    private ArrayList<CardInfo> cardInfo;
    private int id;             //id
    private String name;        //DED도감 이름
    private String card0;       //0번 카드 이름
    private String card1;       //1번 카드 이름
    private String card2;       //2번 카드 이름
    private String card3;       //3번 카드 이름
    private String card4;       //4번 카드 이름
    private String card5;       //5번 카드 이름
    private String card6;       //6번 카드 이름
    private String card7;       //7번 카드 이름
    private String card8;       //8번 카드 이름
    private String card9;       //9번 카드 이름
    private float dmg_p0;       //0번째 활성화시 증가하는 데미지 값
    private float dmg_p1;       //1번째 활성화시 증가하는 데미지 값
    private float dmg_p2;       //2번째 활성화시 증가하는 데미지 값

    private int needCard;
    private int haveCard;

    public DemonExtraDmgInfo() {
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

    //옵션 활성화에 필요한 각성도 수치들
    public int getAwakeSum0() {
        return (needCard * 2);
    }

    public int getAwakeSum1() {
        return (needCard * 4);
    }

    public int getAwakeSum2() {
        return (needCard * 5);
    }

    //도감 완성에 필요 카드 수
    public int getNeedCard() {
        this.needCard = 0;
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

    //현재 각성도
    public int getHaveAwake() {
        return (getAwakeCard0() + getAwakeCard1() + getAwakeCard2() + getAwakeCard3() + getAwakeCard4()
                + getAwakeCard5() + getAwakeCard6() + getAwakeCard7() + getAwakeCard8() + getAwakeCard9());
    }

    //현재 보유 카드 수
    public int getHaveCard() {
        this.haveCard = 0;
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

    //X번 카드 각성도
    public int getAwakeCard0() {
        return getCardAwake(getCard0());
    }

    public int getAwakeCard1() {
        return getCardAwake(getCard1());
    }

    public int getAwakeCard2() {
        return getCardAwake(getCard2());
    }

    public int getAwakeCard3() {
        return getCardAwake(getCard3());
    }

    public int getAwakeCard4() {
        return getCardAwake(getCard4());
    }

    public int getAwakeCard5() {
        return getCardAwake(getCard5());
    }

    public int getAwakeCard6() {
        return getCardAwake(getCard6());
    }

    public int getAwakeCard7() {
        return getCardAwake(getCard7());
    }

    public int getAwakeCard8() {
        return getCardAwake(getCard8());
    }

    public int getAwakeCard9() {
        return getCardAwake(getCard9());
    }


    //DED 도감 달성시 현재 도감의 악마 추가피해 수치
    public float getDmgSum() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        float result = 0;
        if (!(getHaveCard() == getNeedCard())) {  //도감 미완성시 0 리턴
            return 0;
        }
        if (getAwakeSum0() <= getHaveAwake() && getHaveAwake() < getAwakeSum1())    //각성합이 첫번째 각성 조건 달성시
            result = getDmg_p0();
        else if (getAwakeSum1() <= getHaveAwake() && getHaveAwake() < getAwakeSum2())    //각성합이 두번째 각성 조건 달성시
            result = getDmg_p0() + getDmg_p1();
        else if (getAwakeSum2() == getHaveAwake())                                   //각성합이 최대 조건 달성시
            result = getDmg_p0() + getDmg_p1() + getDmg_p2();
        result = Float.parseFloat(df.format(result));
        return result;
    }


    //완성도 순 정렬을 위해 필요한 메소드. 완성도를 퍼센트로 나타내줌.(모든 카드 수집이 다 됐다는 전제가 필요)
    public double completePercent() {
        if (getHaveCard() != getNeedCard()) {  //카드 미획득시 우선순위 하위
            return -5;
        }
        if (getAwakeSum2() > getHaveAwake()) {
            if (getHaveAwake() == 0)
                return 0;
            else {
                return ((double) getHaveAwake() / (double) getAwakeSum2()) * 100;
            }
        } else    //all Complete의 경우 우선순위 최상위
            return 100;
    }

    //다음 완성도가 가까운 순서대로 정렬을 위해 필요한 메소드
    public int fastComplete() {
        if (getHaveCard() != getNeedCard()) {  //카드 미획득시 우선순위 하위
            return 999;
        }
        if (getHaveAwake() < getAwakeSum0()) {
            return getAwakeSum0() - getHaveAwake();
        } else if (getHaveAwake() >= getAwakeSum0() && getHaveAwake() < getAwakeSum1()) {
            return getAwakeSum1() - getHaveAwake();
        } else if (getHaveAwake() >= getAwakeSum1() && getHaveAwake() < getAwakeSum2()) {
            return getAwakeSum2() - getHaveAwake();
        } else {    //all Complete의 경우 우선순위 최하위
            return 1000;
        }
    }
    
    //이름 순서대로 정렬
    @Override
    public int compareTo(DemonExtraDmgInfo o) {
        return this.getName().compareTo(o.getName());
    }


    private boolean getCardCheck(String cardX) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardX) && cardInfo.get(i).getGetCard())
                return true;
        }
        return false;
    }

    private int getCardAwake(String cardX) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardX))
                return cardInfo.get(i).getAwake();
        }
        return 0;
    }

}
