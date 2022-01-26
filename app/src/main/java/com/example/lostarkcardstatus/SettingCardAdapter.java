package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class SettingCardAdapter extends RecyclerView.Adapter<SettingCardAdapter.ViewHolder> {
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardInfo> cardLegend;
    private ArrayList<CardInfo> cardEpic;
    private ArrayList<CardInfo> cardRare;
    private ArrayList<CardInfo> cardUncommon;
    private ArrayList<CardInfo> cardCommon;
    private ArrayList<CardInfo> cardSpecial;
    private Context context;
    private LOA_Card_DB cardDBHelper;
    private ArrayList<CardInfo> useCardList;

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";

    private String cardListGrade;

    public SettingCardAdapter(Context context, String grade) {      //그레이드에 따라 구현
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        settingCardList();
        whatCardList();
        this.context = context;
        this.cardListGrade = grade;
        cardDBHelper = new LOA_Card_DB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        holder.imgCard.setImageResource(R.drawable.card_legend_wei);
        holder.txtCardName.setText(useCardList.get(position).getName());
        if (useCardList.get(position).getGetCard() != 0)
            holder.txtCardAwakeAndHave.setText("각성 : " + useCardList.get(position).getAwake() + "\n보유 : " + useCardList.get(position).getCount());
        else
            holder.txtCardAwakeAndHave.setText("각성 : " + 0 + "\n보유 : " + 0);

        holder.rvCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = positionGet;
                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.just_card);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);



            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cardListGrade.equals(LEGEND)) {
            count = cardLegend.size();
        } else if (cardListGrade.equals(EPIC)) {
            count = cardEpic.size();
        } else if (cardListGrade.equals(RARE)) {
            count = cardRare.size();
        } else if (cardListGrade.equals(UNCOMMON)) {
            count = cardUncommon.size();
        } else if (cardListGrade.equals(COMMON)) {
            count = cardCommon.size();
        } else if (cardListGrade.equals(SPECIAL)) {
            count = cardSpecial.size();
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout rvCardList;
        private ImageView imgCard;
        private TextView txtCardName;
        private TextView txtCardAwakeAndHave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.rvCardList);
            itemView.findViewById(R.id.imgCard);
            itemView.findViewById(R.id.txtCardName);
            itemView.findViewById(R.id.txtCardAwakeAndHave);
        }
    }

    private void settingCardList() {
        int L, E, R, U, C, S;
        L = E = R = U = C = S = 0;
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(LEGEND)) {
                ci.setId(L);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardLegend.add(ci);
                L++;
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(E);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardEpic.add(ci);
                E++;
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(R);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardRare.add(ci);
                R++;
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(U);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardUncommon.add(ci);
                U++;
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(C);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardCommon.add(ci);
                C++;
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(S);
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardSpecial.add(ci);
                S++;
            }
        }
    }

    private void whatCardList() {
        useCardList = new ArrayList<CardInfo>();
        if (cardListGrade.equals(LEGEND)) {
            useCardList = cardLegend;
        } else if (cardListGrade.equals(EPIC)) {
            useCardList = cardEpic;
        } else if (cardListGrade.equals(RARE)) {
            useCardList = cardRare;
        } else if (cardListGrade.equals(UNCOMMON)) {
            useCardList = cardUncommon;
        } else if (cardListGrade.equals(COMMON)) {
            useCardList = cardCommon;
        } else if (cardListGrade.equals(SPECIAL)) {
            useCardList = cardSpecial;
        }
    }
}
