package com.ulmu.lostarkcardmanager;

import java.text.DecimalFormat;

public class DemonExtraDmgInfo implements Comparable<DemonExtraDmgInfo> {
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
    private int awake_sum0;     //0번째 활성화를 위한 각성도 수치
    private int awake_sum1;     //1번째 활성화를 위한 각성도 수치
    private int awake_sum2;     //2번째 활성화를 위한 각성도 수치
    private float dmg_p0;       //0번째 활성화시 증가하는 데미지 값
    private float dmg_p1;       //1번째 활성화시 증가하는 데미지 값
    private float dmg_p2;       //2번째 활성화시 증가하는 데미지 값
    private int haveAwake;      //현재 각성도 합계
    private int cardListSum;    //도감 완성에 필요한 카드 수집 수
    private int haveCard;       //현재 수집한 카드 수
    private int checkCard0;     //0번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard1;     //1번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard2;     //2번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard3;     //3번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard4;     //4번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard5;     //5번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard6;     //6번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard7;     //7번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard8;     //8번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int checkCard9;     //9번 카드 획득 유무(1이면 획득 0이면 미획득)
    private int awakeCard0;     //0번 카드 각성도 수치
    private int awakeCard1;     //1번 카드 각성도 수치
    private int awakeCard2;     //2번 카드 각성도 수치
    private int awakeCard3;     //3번 카드 각성도 수치
    private int awakeCard4;     //4번 카드 각성도 수치
    private int awakeCard5;     //5번 카드 각성도 수치
    private int awakeCard6;     //6번 카드 각성도 수치
    private int awakeCard7;     //7번 카드 각성도 수치
    private int awakeCard8;     //8번 카드 각성도 수치
    private int awakeCard9;     //9번 카드 각성도 수치

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

    //DED 도감 달성시 현재 도감의 악마 추가피해 수치
    public float getDmgSum() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        float result = 0;
        if (!(getHaveCard() == getCompleteDEDBook())) {  //도감 미완성시 0 리턴
            return 0;
        }
        if (getAwake_sum0() <= getHaveAwake() && getHaveAwake() < getAwake_sum1())    //각성합이 첫번째 각성 조건 달성시
            result = getDmg_p0();
        else if (getAwake_sum1() <= getHaveAwake() && getHaveAwake() < getAwake_sum2())    //각성합이 두번째 각성 조건 달성시
            result = getDmg_p0() + getDmg_p1();
        else if (getAwake_sum2() == getHaveAwake())                                   //각성합이 최대 조건 달성시
            result = getDmg_p0() + getDmg_p1() + getDmg_p2();
        result = Float.parseFloat(df.format(result));
        return result;
    }

    public int getHaveAwake() {
        return haveAwake = awakeCard0 + awakeCard1 + awakeCard2 + awakeCard3 + awakeCard4 + awakeCard5 + awakeCard6 + awakeCard7 + awakeCard8 + awakeCard9;
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

    public int getAwakeCard7() {
        return awakeCard7;
    }

    public void setAwakeCard7(int awakeCard7) {
        this.awakeCard7 = awakeCard7;
    }

    public int getAwakeCard8() {
        return awakeCard8;
    }

    public void setAwakeCard8(int awakeCard8) {
        this.awakeCard8 = awakeCard8;
    }

    public int getAwakeCard9() {
        return awakeCard9;
    }

    public void setAwakeCard9(int awakeCard9) {
        this.awakeCard9 = awakeCard9;
    }

    //완성도 순 정렬을 위해 필요한 메소드. 완성도를 퍼센트로 나타내줌.(모든 카드 수집이 다 됐다는 전제가 필요)
    public double completePercent() {
        if (getHaveCard() != getCompleteDEDBook()) {  //카드 미획득시 우선순위 하위
            return -5;
        }
        if (awake_sum2 > haveAwake) {
            if (haveAwake == 0)
                return 0;
            else {
                return ((double) haveAwake / (double) awake_sum2) * 100;
            }
        } else    //all Complete의 경우 우선순위 최상위
            return 100;
    }

    //다음 완성도가 가까운 순서대로 정렬을 위해 필요한 메소드
    public int fastComplete() {
        if (getHaveCard() != getCompleteDEDBook()) {  //카드 미획득시 우선순위 하위
            return 999;
        }
        if (getHaveAwake() < getAwake_sum0()) {
            return getAwake_sum0() - getHaveAwake();
        } else if (getHaveAwake() >= getAwake_sum0() && getHaveAwake() < getAwake_sum1()) {
            return getAwake_sum1() - getHaveAwake();
        } else if (getHaveAwake() >= getAwake_sum1() && getHaveAwake() < getAwake_sum2()) {
            return getAwake_sum2() - getHaveAwake();
        } else {    //all Complete의 경우 우선순위 최하위
            return 1000;
        }
    }
    
    //이름 순서대로 정렬
    @Override
    public int compareTo(DemonExtraDmgInfo o) {
        return this.getName().compareTo(o.getName());
    }

}
