package com.ulmu.lostarkcardmanager;

import android.util.Log;

public class CardSetInfo implements Comparable<CardSetInfo> {

    private int id;                 //id
    private String name;            //카드세트이름
    private String card0;           //카드0 이름
    private String card1;           //카드1 이름
    private String card2;           //카드2 이름
    private String card3;           //카드3 이름
    private String card4;           //카드4 이름
    private String card5;           //카드5 이름
    private String card6;           //카드6 이름
    private String set_bonus0;      //0번째 카드세트 보너스 효과
    private String set_bonus1;      //1번째 카드세트 보너스 효과
    private String set_bonus2;      //2번째 카드세트 보너스 효과
    private String set_bonus3;      //3번째 카드세트 보너스 효과
    private String set_bonus4;      //4번째 카드세트 보너스 효과
    private String set_bonus5;      //5번째 카드세트 보너스 효과
    private int haveCard;           //세트 활성화에 필요한 최소 카드 수
    private int haveAwake;          //카드 세트 각성도 합
    private int needAwake0;         //카드세트 효과 발동을 위한 필요 각성도0
    private int needAwake1;         //카드세트 효과 발동을 위한 필요 각성도1
    private int needAwake2;         //카드세트 효과 발동을 위한 필요 각성도2
    private int checkCard0;         //카드0 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard1;         //카드1 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard2;         //카드2 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard3;         //카드3 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard4;         //카드4 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard5;         //카드5 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int checkCard6;         //카드6 획득 유무(값이 1이명 획득 0 이면 미획득)
    private int awakeCard0;         //카드0 각성도
    private int awakeCard1;         //카드1 각성도
    private int awakeCard2;         //카드2 각성도
    private int awakeCard3;         //카드3 각성도
    private int awakeCard4;         //카드4 각성도
    private int awakeCard5;         //카드5 각성도
    private int awakeCard6;         //카드6 각성도
    private String favorite;        //즐겨찾기 포함 유무(값이 있는경우 즐겨찾기 된 것)

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

    public int getNeedAwake0() {
        return needAwake0;
    }

    public void setNeedAwake0(int needAwake0) {
        this.needAwake0 = needAwake0;
    }

    public int getNeedAwake1() {
        return needAwake1;
    }

    public void setNeedAwake1(int needAwake1) {
        this.needAwake1 = needAwake1;
    }

    public int getNeedAwake2() {
        return needAwake2;
    }

    public void setNeedAwake2(int needAwake2) {
        this.needAwake2 = needAwake2;
    }

    public int getCheckCard0() {
        if (awakeCard0 > 0)
            checkCard0 = 1;
        return checkCard0;
    }

    public void setCheckCard0(int checkCard0) {
        this.checkCard0 = checkCard0;
    }

    public int getCheckCard1() {
        if (awakeCard1 > 0)
            checkCard1 = 1;
        return checkCard1;
    }

    public void setCheckCard1(int checkCard1) {
        this.checkCard1 = checkCard1;
    }

    public int getCheckCard2() {
        if (awakeCard2 > 0)
            checkCard2 = 1;
        return checkCard2;
    }

    public void setCheckCard2(int checkCard2) {
        this.checkCard2 = checkCard2;
    }

    public int getCheckCard3() {
        if (awakeCard3 > 0)
            checkCard3 = 1;
        return checkCard3;
    }

    public void setCheckCard3(int checkCard3) {
        this.checkCard3 = checkCard3;
    }

    public int getCheckCard4() {
        if (awakeCard4 > 0)
            checkCard4 = 1;
        return checkCard4;
    }

    public void setCheckCard4(int checkCard4) {
        this.checkCard4 = checkCard4;
    }

    public int getCheckCard5() {
        if (awakeCard5 > 0)
            checkCard5 = 1;
        return checkCard5;
    }

    public void setCheckCard5(int checkCard5) {
        this.checkCard5 = checkCard5;
    }

    public int getCheckCard6() {
        if (awakeCard6 > 0)
            checkCard6 = 1;
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

    public boolean isCompleteCardSet() {   //세트 효과 발동만 가능한 경우
        if (haveCard <= checkCard0 + checkCard1 + checkCard2 + checkCard3 + checkCard4 + checkCard5 + checkCard6) {
            return true;
        } else {
            return false;
        }
    }

    //완성도 순 정렬을 위해 필요한 메소드. 완성도를 퍼센트로 나타내줌.(모든 카드 수집이 다 됐다는 전제가 필요)
    public double completePercent() {
        int needCompleteAwake = haveCard * 5;
        if (!isCompleteCardSet()) {  //카드 미획득시 우선순위 하위
            return -5;
        }
        if (needCompleteAwake > haveAwake) {
            if (haveAwake == 0)
                return 0;
            else {
                Log.v("test", name + " complete percent : " + ((double) haveAwake / (double) needCompleteAwake) * 100);
                return ((double) haveAwake / (double) needCompleteAwake) * 100;
            }
        } else    //all Complete의 경우 우선순위 최하위
            return 100;
    }

    //즐겨찾기 순 정렬에서 Collections.sort 를 위해 필요한 메소드
    public int favoriteCheck() {
        if (favorite.isEmpty())
            return 1;
        else
            return 0;
    }

    //이름 순 정렬
    @Override
    public int compareTo(CardSetInfo cardSetInfo) {
        return this.getName().compareTo(cardSetInfo.getName());
    }

}

