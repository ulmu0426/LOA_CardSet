package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestViewPagerAdapter extends RecyclerView.Adapter<TestViewPagerAdapter.ViewHolder> {
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
    private Context context;

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


    private ArrayList<ArrayList<CardInfo>> cardListInfo;

    private TestSettingCard testSettingCard = (TestSettingCard) TestSettingCard.testSettingCard;
    private TestSettingCardAdapter testSettingCardAdapter;

    public TestViewPagerAdapter(Context context) {
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_cardlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(cardListInfo.get(position));
        testSettingCardAdapter = new TestSettingCardAdapter(context, cardListInfo.get(position), testSettingCard);
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

        public void onBind(ArrayList<CardInfo> cardListInfo) {
            if (!cardListInfo.isEmpty()) {
                if (cardListInfo.get(0).getGrade().equals(LEGEND))
                    rv.setBackgroundColor(Color.parseColor("#FFF4BD"));
                if (cardListInfo.get(0).getGrade().equals(EPIC))
                    rv.setBackgroundColor(Color.parseColor("#ECE2FF"));
                if (cardListInfo.get(0).getGrade().equals(RARE))
                    rv.setBackgroundColor(Color.parseColor("#DDEFFF"));
                if (cardListInfo.get(0).getGrade().equals(UNCOMMON))
                    rv.setBackgroundColor(Color.parseColor("#DEFFBB"));
                if (cardListInfo.get(0).getGrade().equals(COMMON))
                    rv.setBackgroundColor(Color.parseColor("#F4F4F4"));
                if (cardListInfo.get(0).getGrade().equals(SPECIAL))
                    rv.setBackgroundColor(Color.parseColor("#FFDCE9"));
            }
        }


    }

    public void filtered(CharSequence s){
        testSettingCardAdapter.getFilter().filter(s);
    }


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
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
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
