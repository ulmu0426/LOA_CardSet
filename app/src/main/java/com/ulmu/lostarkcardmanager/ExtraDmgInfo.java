package com.ulmu.lostarkcardmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExtraDmgInfo implements Comparable<ExtraDmgInfo>, Parcelable {
    private ArrayList<CardInfo> cardInfo;

    private int id;

    //추피 도감 이름
    private String name;

    //추피 도감에 필요한 카드
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

    //추가 피해 수치
    private float dmgP0;
    private float dmgP1;
    private float dmgP2;

    //현재 보유한 카드 수
    private int haveCard;
    //완성에 필요한 카드 수
    private int needCard;

    public ExtraDmgInfo() {
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.haveCard = 0;
    }


    protected ExtraDmgInfo(Parcel in) {
        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        id = in.readInt();
        name = in.readString();
        card0 = in.readString();
        card1 = in.readString();
        card2 = in.readString();
        card3 = in.readString();
        card4 = in.readString();
        card5 = in.readString();
        card6 = in.readString();
        card7 = in.readString();
        card8 = in.readString();
        card9 = in.readString();
        dmgP0 = in.readFloat();
        dmgP1 = in.readFloat();
        dmgP2 = in.readFloat();
        haveCard = in.readInt();
        needCard = in.readInt();
    }

    public static final Creator<ExtraDmgInfo> CREATOR = new Creator<ExtraDmgInfo>() {
        @Override
        public ExtraDmgInfo createFromParcel(Parcel in) {
            return new ExtraDmgInfo(in);
        }

        @Override
        public ExtraDmgInfo[] newArray(int size) {
            return new ExtraDmgInfo[size];
        }
    };

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

    public float getDmgP0() {
        return dmgP0;
    }

    public void setDmgP0(float dmgP0) {
        this.dmgP0 = dmgP0;
    }

    public float getDmgP1() {
        return dmgP1;
    }

    public void setDmgP1(float dmgP1) {
        this.dmgP1 = dmgP1;
    }

    public float getDmgP2() {
        return dmgP2;
    }

    public void setDmgP2(float dmgP2) {
        this.dmgP2 = dmgP2;
    }

    //DED 도감 달성시 현재 도감의 악마 추가피해 수치
    public float getDmgSum() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        float result = 0;
        if (!(getHaveCard() == getNeedCard())) {  //도감 미완성시 0 리턴
            return 0;
        }
        if (getAwakeSum0() <= getHaveAwake() && getHaveAwake() < getAwakeSum1())    //각성합이 첫번째 각성 조건 달성시
            result = getDmgP0();
        else if (getAwakeSum1() <= getHaveAwake() && getHaveAwake() < getAwakeSum2())    //각성합이 두번째 각성 조건 달성시
            result = getDmgP0() + getDmgP1();
        else if (getAwakeSum2() == getHaveAwake())                                   //각성합이 최대 조건 달성시
            result = getDmgP0() + getDmgP1() + getDmgP2();
        if (trision()) {
            if ((getAwakeSum2() + 5) == getHaveAwake())
                result = getDmgP0() + getDmgP1() + getDmgP2() + getDmgP2();
        }
        result = Float.parseFloat(df.format(result));
        return result;
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
        if (trision()) needCard--;

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
        if (trision()) haveCard--;

        return haveCard;
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

    //X번 카드 보유 수
    public int getNumCard0() {
        return getCardNum(getCard0());
    }

    public int getNumCard1() {
        return getCardNum(getCard1());
    }

    public int getNumCard2() {
        return getCardNum(getCard2());
    }

    public int getNumCard3() {
        return getCardNum(getCard3());
    }

    public int getNumCard4() {
        return getCardNum(getCard4());
    }

    public int getNumCard5() {
        return getCardNum(getCard5());
    }

    public int getNumCard6() {
        return getCardNum(getCard6());
    }

    public int getNumCard7() {
        return getCardNum(getCard7());
    }

    public int getNumCard8() {
        return getCardNum(getCard8());
    }

    public int getNumCard9() {
        return getCardNum(getCard9());
    }

    //이름순 정렬을 위한 메소드
    @Override
    public int compareTo(ExtraDmgInfo o) {
        return this.getName().compareTo(o.getName());
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

    //다음 활성화가 가까운 순서대로 정렬을 위해 필요한 메소드
    public int fastComplete() {
        if (getHaveCard() != getNeedCard()) {
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

    private int getCardNum(String cardX) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardX))
                return cardInfo.get(i).getNum();
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(card0);
        dest.writeString(card1);
        dest.writeString(card2);
        dest.writeString(card3);
        dest.writeString(card4);
        dest.writeString(card5);
        dest.writeString(card6);
        dest.writeString(card7);
        dest.writeString(card8);
        dest.writeString(card9);
        dest.writeFloat(dmgP0);
        dest.writeFloat(dmgP1);
        dest.writeFloat(dmgP2);
        dest.writeInt(haveCard);
        dest.writeInt(needCard);
    }

    public boolean trision() {
        if (getName().equals("트리시온"))
            return true;
        return false;
    }
}
