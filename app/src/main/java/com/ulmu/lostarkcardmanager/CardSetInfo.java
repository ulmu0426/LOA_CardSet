package com.ulmu.lostarkcardmanager;

import android.util.Log;

import java.util.ArrayList;

public class CardSetInfo implements Comparable<CardSetInfo> {
    private ArrayList<CardInfo> cardInfo;

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

    private int haveAwake;

    private int needAwake0;         //카드세트 효과 발동을 위한 필요 각성도0
    private int needAwake1;         //카드세트 효과 발동을 위한 필요 각성도1
    private int needAwake2;         //카드세트 효과 발동을 위한 필요 각성도2

    private boolean favorite;        //즐겨찾기 포함 유무(값이 있는경우 즐겨찾기 된 것)

    public CardSetInfo() {
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.favorite = false;
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

    //세트효과 발동에 필요한 카드 수
    public int getNeedCard() {
        int needCard = 0;
        if (!getCard0().isEmpty()) needCard++;
        if (!getCard1().isEmpty()) needCard++;
        if (!getCard2().isEmpty()) needCard++;
        if (!getCard3().isEmpty()) needCard++;
        if (!getCard4().isEmpty()) needCard++;
        if (!getCard5().isEmpty()) needCard++;
        if (!getCard6().isEmpty()) needCard++;
        if (needCard > 6)
            needCard = 6;

        return needCard;
    }

    //현재 보유 카드 수
    public int getHaveCard() {
        int haveCard = 0;
        if (isCheckCard0()) haveCard++;
        if (isCheckCard1()) haveCard++;
        if (isCheckCard2()) haveCard++;
        if (isCheckCard3()) haveCard++;
        if (isCheckCard4()) haveCard++;
        if (isCheckCard5()) haveCard++;
        if (isCheckCard6()) haveCard++;
        return haveCard;
    }

    public int getHaveAwake() { //카드세트 효과 발동을 위한 각성도 합
        haveAwake = 0;
        int min = 6;
        int[] awakeArray = {getAwakeCard0(), getAwakeCard1(), getAwakeCard2(), getAwakeCard3(), getAwakeCard4(), getAwakeCard5(), getAwakeCard6()};
        for (int i = 0; i < awakeArray.length; i++) {
            haveAwake += awakeArray[i];
        }

        if (getHaveCard() > getNeedCard()) {
            for (int i = 0; i < awakeArray.length; i++) {
                if (awakeArray[i] < min)
                    min = awakeArray[i];
            }
            haveAwake -= min;
        }

        return haveAwake;
    }

    //활성화에 필요한 각성도
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

    //즐겨찾기 유무
    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isCompleteCardSet() {   //세트 효과 발동만 가능한 경우
        if (getNeedCard() <= getHaveCard()) {
            return true;
        } else {
            return false;
        }
    }

    //완성도 순 정렬을 위해 필요한 메소드. 완성도를 퍼센트로 나타내줌.(모든 카드 수집이 다 됐다는 전제가 필요)
    public double completePercent() {
        int needCompleteAwake = getNeedCard() * 5;
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
        if (favorite)
            return -1;
        else
            return 1;
    }

    //이름 순 정렬
    @Override
    public int compareTo(CardSetInfo cardSetInfo) {
        return this.getName().compareTo(cardSetInfo.getName());
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

