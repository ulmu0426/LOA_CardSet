package com.example.lostarkcardstatus;


import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingCardAdapter2 extends RecyclerView.Adapter<SettingCardAdapter2.ViewHolder> {
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardInfo> cardLegend;
    private ArrayList<CardInfo> cardEpic;
    private ArrayList<CardInfo> cardRare;
    private ArrayList<CardInfo> cardUncommon;
    private ArrayList<CardInfo> cardCommon;
    private ArrayList<CardInfo> cardSpecial;
    private Context context;
    private LOA_CardDB cardDBHelper;
    private ArrayList<CardInfo> useCardList;

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";

    private String cardListGrade;

    String preAwake;
    int awakeValue;
    String preHave;

    public SettingCardAdapter2(Context context, String grade) {      //그레이드에 따라 구현
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        this.context = context;
        this.cardListGrade = grade;
        cardDBHelper = new LOA_CardDB(context);
        settingCardList();
        whatCardList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist2, parent, false);

        return new SettingCardAdapter2.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        holder.img.setImageResource(R.drawable.card_legend_wei);
        defaultColorFilter(holder.img, position, filter);

        holder.txtName.setText(useCardList.get(position).getName());

        holder.eTxtAwake.setText(useCardList.get(position).getAwake() + "");
        holder.eTxtHave.setText(useCardList.get(position).getCount() + "");
        holder.isGetCheckbox.setChecked(isChecked(useCardList.get(position).getGetCard()));

        holder.eTxtHave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                preAwake = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(preAwake)) return;
                awakeValue = 0;
                if (holder.eTxtAwake.isFocusable() && !s.toString().equals("")) {
                    try {
                        awakeValue = Integer.parseInt(holder.eTxtAwake.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Log.v("test","ERROR on");
                        return;
                    }
                }
                if(awakeValue == 0){
                    Log.v("test","ERROR on");
                }
                holder.eTxtAwake.setText(awakeValue + "");
                cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setAwake(awakeValue);
                useCardList.get(positionGet).setAwake(awakeValue);
                cardDBHelper.UpdateInfoCardAwake("awake", awakeValue, useCardList.get(positionGet).getId());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*
        holder.eTxtHave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                preAwake[0] = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int haveValue = awakeRangeSet(holder.eTxtHave.getText().toString());
                holder.eTxtHave.setText(haveValue + "");
                cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setCount(haveValue);
                useCardList.get(positionGet).setCount(haveValue);
                cardDBHelper.UpdateInfoCardNum(haveValue, useCardList.get(positionGet).getId());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        */

        holder.isGetCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tf = !holder.isGetCheckbox.isChecked();
                int check = isChecked(tf);
                holder.isGetCheckbox.setChecked(tf);
                useCardList.get(positionGet).setGetCard(check);
                cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setGetCard(check);
                cardDBHelper.UpdateInfoCardCheck(check, useCardList.get(positionGet).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return useCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtName;
        private TextView eTxtAwake;
        private TextView eTxtHave;
        private CheckBox isGetCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            eTxtAwake = itemView.findViewById(R.id.eTxtAwake);
            eTxtHave = itemView.findViewById(R.id.eTxtHave);
            isGetCheckbox = itemView.findViewById(R.id.isGetCheckbox);
        }
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
                ci.setGrade("");
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardSpecial.add(ci);
            } else {
                continue;
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

    private void defaultColorFilter(ImageView iv, int position, ColorFilter filter) {
        if (useCardList.get(position).getGetCard() == 0) {
            iv.setColorFilter(filter);
        } else {
            iv.setColorFilter(null);
        }
    }

    private boolean isChecked(int check) {
        boolean tf = false;
        if (check == 0) {
            tf = false;
        } else if (check == 1) {
            tf = true;
        }
        return tf;
    }

    private int isChecked(boolean tf) {
        int check = 0;
        if (tf)
            check = 1;
        else
            check = 0;
        return check;
    }

    private int awakeRangeSet(String input) {
        int result = 0;
        if (input.isEmpty())
            result = 0;

        if (Integer.parseInt(input) > 5) {
            result = 5;
        } else if (Integer.parseInt(input) < 0) {
            result = 0;
        } else {
            result = Integer.parseInt(input);
        }
        return result;

    }

    //카드 각성도에 따라 최대 보유 카드 수량이 달라짐. 다음에 수정 할것.
    private int numRangeSet(String input) {
        int result = 0;
        if (input.isEmpty())
            result = 0;

        if (Integer.parseInt(input) > 15) {
            result = 15;
        } else if (Integer.parseInt(input) < 0) {
            result = 0;
        } else {
            result = Integer.parseInt(input);
        }

        return result;
    }

    private int matchIndex(int id) {
        int index = 0;
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }
}
