package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
    private Context context;


    private static final String[] STAT = {"치명", "특화", "신속"};

    private float DEDDmg;
    private ArrayList<CardBookInfo> cardBookInfo;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;

    private ArrayList<CardInfo> cardLegend;
    private ArrayList<CardInfo> cardEpic;
    private ArrayList<CardInfo> cardRare;
    private ArrayList<CardInfo> cardUncommon;
    private ArrayList<CardInfo> cardCommon;
    private ArrayList<CardInfo> cardSpecial;

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";


    private ArrayList<ArrayList<CardInfo>> cardListInfo;    //카드리스트를 담고있는 리스트(뷰페이저에 뿌려줄 리스트)

    private SettingCardAdapter testSettingCardAdapter;

    public ViewPagerAdapter(Context context) {
        this.context = context;
        this.cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;
        this.DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;
        cardListInfo = new ArrayList<>();
        settingCardList();
        cardListInfo.add(cardLegend);
        cardListInfo.add(cardEpic);
        cardListInfo.add(cardRare);
        cardListInfo.add(cardUncommon);
        cardListInfo.add(cardCommon);
        cardListInfo.add(cardSpecial);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.vp_cardlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
        testSettingCardAdapter = new SettingCardAdapter(context, cardListInfo.get(position));

        holder.rv.setAdapter(testSettingCardAdapter);

    }

    @Override
    public int getItemCount() {
        return cardListInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.rvCardListFragment);
        }

        //각 뷰의 페이지 배경색
        public void onBind(int position) {

            switch (position) {
                case 0:
                    rv.setBackgroundColor(Color.parseColor("#FFF4BD"));
                    break;
                case 1:
                    rv.setBackgroundColor(Color.parseColor("#ECE2FF"));
                    break;
                case 2:
                    rv.setBackgroundColor(Color.parseColor("#DDEFFF"));
                    break;
                case 3:
                    rv.setBackgroundColor(Color.parseColor("#DEFFBB"));
                    break;
                case 4:
                    rv.setBackgroundColor(Color.parseColor("#F4F4F4"));
                    break;
                case 5:
                    rv.setBackgroundColor(Color.parseColor("#FFDCE9"));
                    break;
                default:
                    return;
            }

        }

    }

    //검색된 리스트 리턴
    private ArrayList<CardInfo> searchFilter(CharSequence filterString, ArrayList<CardInfo> cardInfo) {
        ArrayList<CardInfo> tempFilterList = new ArrayList<>();
        String charString = filterString.toString();
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().contains(charString)) {
                tempFilterList.add(cardInfo.get(i));
            }
        }
        return tempFilterList;
    }

    //검색 리스트를 뷰에 뿌림
    public void search(CharSequence filterString) {
        ArrayList<CardInfo> tempFilterListL = new ArrayList<>();
        ArrayList<CardInfo> tempFilterListE = new ArrayList<>();
        ArrayList<CardInfo> tempFilterListR = new ArrayList<>();
        ArrayList<CardInfo> tempFilterListU = new ArrayList<>();
        ArrayList<CardInfo> tempFilterListC = new ArrayList<>();
        ArrayList<CardInfo> tempFilterListS = new ArrayList<>();
        tempFilterListL.addAll(cardLegend);
        tempFilterListE.addAll(cardEpic);
        tempFilterListR.addAll(cardRare);
        tempFilterListU.addAll(cardUncommon);
        tempFilterListC.addAll(cardCommon);
        tempFilterListS.addAll(cardSpecial);

        if (!filterString.equals(null) || filterString.length() != 0) {
            tempFilterListL = searchFilter(filterString, tempFilterListL);
            tempFilterListE = searchFilter(filterString, tempFilterListE);
            tempFilterListR = searchFilter(filterString, tempFilterListR);
            tempFilterListU = searchFilter(filterString, tempFilterListU);
            tempFilterListC = searchFilter(filterString, tempFilterListC);
            tempFilterListS = searchFilter(filterString, tempFilterListS);
        } else {
            tempFilterListL.addAll(cardLegend);
            tempFilterListE.addAll(cardEpic);
            tempFilterListR.addAll(cardRare);
            tempFilterListU.addAll(cardUncommon);
            tempFilterListC.addAll(cardCommon);
            tempFilterListS.addAll(cardSpecial);
        }

        cardListInfo.set(0, tempFilterListL);
        cardListInfo.set(1, tempFilterListE);
        cardListInfo.set(2, tempFilterListR);
        cardListInfo.set(3, tempFilterListU);
        cardListInfo.set(4, tempFilterListC);
        cardListInfo.set(5, tempFilterListS);
        notifyDataSetChanged();
    }

    //현재 정렬 종류 리턴
    private int boolArrayCheck(boolean[] checkAll) {
        int check = 0;
        // 0 : 디폴트
        // 1 : 이름
        // 2 : 미획득
        // 3 : 획득
        for (int i = 0; i < checkAll.length; i++) {
            if (checkAll[i]) {
                check = i;
            }
        }

        return check;
    }

    //정렬시키고 정렬된 리스트를 뷰에 뿌림
    public void sortingCard(boolean[] checkAll) {
        switch (boolArrayCheck(checkAll)) {
            case 0:
                getDefaultSort(cardLegend);
                getDefaultSort(cardEpic);
                getDefaultSort(cardRare);
                getDefaultSort(cardUncommon);
                getDefaultSort(cardCommon);
                getDefaultSort(cardSpecial);
                break;
            case 1:
                getNameSort(cardLegend);
                getNameSort(cardEpic);
                getNameSort(cardRare);
                getNameSort(cardUncommon);
                getNameSort(cardCommon);
                getNameSort(cardSpecial);
                break;
            case 2:
                getNotAcquiredSort(cardLegend);
                getNotAcquiredSort(cardEpic);
                getNotAcquiredSort(cardRare);
                getNotAcquiredSort(cardUncommon);
                getNotAcquiredSort(cardCommon);
                getNotAcquiredSort(cardSpecial);
                break;
            case 3:
                getAcquiredSort(cardLegend);
                getAcquiredSort(cardEpic);
                getAcquiredSort(cardRare);
                getAcquiredSort(cardUncommon);
                getAcquiredSort(cardCommon);
                getAcquiredSort(cardSpecial);
                break;
            default:
                return;
        }
        cardListInfo.set(0, cardLegend);
        cardListInfo.set(1, cardEpic);
        cardListInfo.set(2, cardRare);
        cardListInfo.set(3, cardUncommon);
        cardListInfo.set(4, cardCommon);
        cardListInfo.set(5, cardSpecial);

        notifyDataSetChanged();
    }

    //기본정렬
    private void getDefaultSort(ArrayList<CardInfo> cardInfo) {
        Collections.sort(cardInfo, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                else
                    return 1;
            }
        });
        notifyDataSetChanged();
    }

    //이름 정렬
    private void getNameSort(ArrayList<CardInfo> cardInfo) {
        Collections.sort(cardInfo);
        notifyDataSetChanged();
    }

    //미획득 카드 정렬
    private void getNotAcquiredSort(ArrayList<CardInfo> cardInfo) {
        Collections.sort(cardInfo, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getGetCard() <= o2.getGetCard())
                    return -1;
                else
                    return 1;
            }
        });
        notifyDataSetChanged();
    }

    //획득 카드 정렬
    private void getAcquiredSort(ArrayList<CardInfo> cardInfo) {
        Collections.sort(cardInfo, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getGetCard() <= o2.getGetCard())
                    return 1;
                else
                    return -1;
            }
        });
        notifyDataSetChanged();
    }

    //모든 카드 미획득(카드 획득 유무를 모두 미획득으로 변경
    public void allUncheck() {
        for (int i = 0; i < cardListInfo.size(); i++) {
            for (int j = 0; j < cardListInfo.get(i).size(); j++) {
                cardListInfo.get(i).get(j).setGetCard(0);
            }
        }
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
        haveStatUpdate();
        haveDEDUpdate();
        notifyDataSetChanged();
    }

    //모든 카드 획득
    public void allCheck() {
        for (int i = 0; i < cardListInfo.size(); i++) {
            for (int j = 0; j < cardListInfo.get(i).size(); j++) {
                cardListInfo.get(i).get(j).setGetCard(1);
            }
        }
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
        haveStatUpdate();
        haveDEDUpdate();

        notifyDataSetChanged();
    }

    // DB에 도감을 완성 시킨 경우 true else false
    private boolean isCompleteCardBook(CardBookInfo cardBookInfo) {
        if (cardBookInfo.getHaveCard() == cardBookInfo.getCompleteCardBook())
            return true;
        else
            return false;
    }

    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate() {
        int[] haveStat = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardBookInfo.size(); j++) {
                if (cardBookInfo.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardBookInfo.get(j))) {
                    haveStat[i] += cardBookInfo.get(j).getValue();
                }
            }
        }
        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
    }

    //DED Dmb 값
    private void haveDEDUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        DEDDmg = 0;
        for (int i = 0; i < DEDInfo.size(); i++) {
            DEDDmg += DEDInfo.get(i).getDmgSum();
        }
        DEDDmg = Float.parseFloat(df.format(DEDDmg));
        ((MainPage) MainPage.mainContext).setDemonExtraDmgInfo(DEDDmg);
    }

    //카드 목록 update
    private void settingCardList() {
        cardLegend = new ArrayList<CardInfo>();
        cardEpic = new ArrayList<CardInfo>();
        cardRare = new ArrayList<CardInfo>();
        cardUncommon = new ArrayList<CardInfo>();
        cardCommon = new ArrayList<CardInfo>();
        cardSpecial = new ArrayList<CardInfo>();

        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(LEGEND)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardSpecial.add(ci);
            } else {
                continue;
            }
        }
    }
}
