package com.ulmu.lostarkcardmanager;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CardSetNeedCardInfo extends CardSetInfo {

    private int[] cardSetNeedCard;
    private ArrayList<CardInfo> tempCardList;
    int[] goalAwake = new int[]{0, 0};

    private int card0NeedCard = 0;
    private int card1NeedCard = 0;
    private int card2NeedCard = 0;
    private int card3NeedCard = 0;
    private int card4NeedCard = 0;
    private int card5NeedCard = 0;
    private int card6NeedCard = 0;

    public CardSetNeedCardInfo(CardSetInfo selectCardSet, int goal) {
        super(selectCardSet);
        Log.v("test", "Error 1");
        setDefault();
        Log.v("test", "Error 2");
        setCardList();
        Log.v("test", "Error 3");
        setGoalAwake();
        Log.v("test", "Error 4");
        cardListCheck();
        Log.v("test", "Error 5");         //카드세트의 포함카드가 7장인지 확인 후 7장 미만으로 조정
        setCardSetNeedCard(goal);
        Log.v("test", "Error 6");  //목표 각성수치에 따라 cardSetNeedCard 값 조정.
        setCardXNeedCard();
    }

    public int getCard0NeedCard() {
        return card0NeedCard;
    }

    public int getCard1NeedCard() {
        return card1NeedCard;
    }

    public int getCard2NeedCard() {
        return card2NeedCard;
    }

    public int getCard3NeedCard() {
        return card3NeedCard;
    }

    public int getCard4NeedCard() {
        return card4NeedCard;
    }

    public int getCard5NeedCard() {
        return card5NeedCard;
    }

    public int getCard6NeedCard() {
        return card6NeedCard;
    }

    public int getCardSetNeedCardSum() {
        return Arrays.stream(cardSetNeedCard).sum();
    }

    private void setDefault() {
        cardSetNeedCard = new int[getNeedCard()];
        for (int i = 0; i < cardSetNeedCard.length; i++) {
            cardSetNeedCard[i] = 0;
        }
    }

    //깊은 복사로 기존 DB 보존
    private void setCardList() {
        tempCardList = new ArrayList<>();
        ArrayList<CardInfo> cardInfo = super.getCardInfo();
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo tempInfo = new CardInfo();
            if (getCard0().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard1().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard2().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard3().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard4().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard5().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
            if (getCard6().equals(cardInfo.get(i).getName())) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                Log.v("test", tempInfo.getName());
                tempCardList.add(tempInfo);
                continue;
            }
        }
        for (int i = 0; i < tempCardList.size(); i++) {
            Log.v("test", "tempCardList"+i+tempCardList.get(i).getName());
        }

    }

    private void setGoalAwake() {
        if (getNeedAwake2() == 0) {
            goalAwake[0] = getNeedAwake0();
            goalAwake[1] = getNeedAwake1();
        } else {
            goalAwake[0] = getNeedAwake1();
            goalAwake[1] = getNeedAwake2();
        }

        Log.v("test", "goalAwake[0]" + goalAwake[0]);
        Log.v("test", "goalAwake[1]" + goalAwake[1]);
    }

    private void setCardSetNeedCard(int goal) {
        if (goal == goalAwake[0]) {
            int i = 0;
            while (haveAwakeCheck() < goal) {
                if (isSmaller(i)) {
                    i++;
                    if (i == tempCardList.size()) {
                        i = 0;
                    }
                    continue;
                }
                if (tempCardList.get(i).getAwake() < 5) {
                    cardSetNeedCard[i] += (tempCardList.get(i).nextAwake() - tempCardList.get(i).getNum());
                    tempCardList.get(i).setAwake(tempCardList.get(i).getAwake() + 1);
                    tempCardList.get(i).setNum(0);
                }

                i++;
                //무한루프(목표 각성도가 될때까지)
                if (i == tempCardList.size()) {
                    i = 0;
                }
            }
        } else {
            for (int i = 0; i < cardSetNeedCard.length; i++) {
                cardSetNeedCard[i] = tempCardList.get(i).awakeMax();
            }
        }
        Log.v("test", tempCardList.get(0).getName() + "cardSetNeedCard0 : " + cardSetNeedCard[0]);
        Log.v("test", tempCardList.get(1).getName() + "cardSetNeedCard1 : " + cardSetNeedCard[1]);
        Log.v("test", tempCardList.get(2).getName() + "cardSetNeedCard2 : " + cardSetNeedCard[2]);
        Log.v("test", tempCardList.get(3).getName() + "cardSetNeedCard3 : " + cardSetNeedCard[3]);
        Log.v("test", tempCardList.get(4).getName() + "cardSetNeedCard4 : " + cardSetNeedCard[4]);
        Log.v("test", tempCardList.get(5).getName() + "cardSetNeedCard5 : " + cardSetNeedCard[5]);

    }

    //카드세트의 포함 카드가 6장 이상인 경우(각성도가 제일 낮은 카드 한장을 빼야 함)
    private boolean isCardListSize() {
        if (tempCardList.size() > 6)
            return true;
        else
            return false;
    }

    //각성도 보유 카드에 따라 수정 후 가장 적은 각성도의 카드 세트발동 조건에서 제외
    private void cardListCheck() {
        modifyCardListAwake();
        if (isCardListSize()) {
            Collections.sort(tempCardList, new Comparator<CardInfo>() {
                @Override
                public int compare(CardInfo o1, CardInfo o2) {
                    if (o1.getAwake() < o2.getAwake())
                        return -1;
                    else
                        return 1;
                }
            });

            tempCardList.remove(0);
        }
    }

    private void modifyCardListAwake() {
        for (int i = 0; i < tempCardList.size(); i++) {
            if (tempCardList.get(i).getAwake() == 5)
                continue;
            while (tempCardList.get(i).needCard() <= 0) {
                tempCardList.get(i).setNum(tempCardList.get(i).getNum() - tempCardList.get(i).nextAwake());
                tempCardList.get(i).setAwake(tempCardList.get(i).getAwake() + 1);
            }
        }
    }

    private boolean isSmaller(int index) {
        for (int i = 0; i < tempCardList.size(); i++) {
            if (index == i)
                continue;
            if (tempCardList.get(i).needCard() < tempCardList.get(index).needCard()) {
                if (tempCardList.get(i).getAwake() == 5)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    private int haveAwakeCheck() {
        int awake = 0;
        for (int i = 0; i < tempCardList.size(); i++) {
            awake += tempCardList.get(i).getAwake();
        }
        return awake;
    }

    private void setCardXNeedCard() {
        for (int i = 0; i < tempCardList.size(); i++) {
            if (getCard0().equals(tempCardList.get(i).getName())) {
                card0NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard1().equals(tempCardList.get(i).getName())) {
                card1NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard2().equals(tempCardList.get(i).getName())) {
                card2NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard3().equals(tempCardList.get(i).getName())) {
                card3NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard4().equals(tempCardList.get(i).getName())) {
                card4NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard5().equals(tempCardList.get(i).getName())) {
                card5NeedCard = cardSetNeedCard[i];
                continue;
            }
            if (getCard6().equals(tempCardList.get(i).getName())) {
                card6NeedCard = cardSetNeedCard[i];
                continue;
            }
        }
    }
}
