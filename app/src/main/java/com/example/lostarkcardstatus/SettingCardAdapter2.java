package com.example.lostarkcardstatus;


import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        holder.txtAwake.setText(useCardList.get(position).getAwake() + "");
        holder.txtHave.setText(useCardList.get(position).getCount() + "");
        holder.isGetCheckbox.setChecked(isChecked(useCardList.get(position).getGetCard()));

        holder.changeAwakeHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog awakeHaveDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                awakeHaveDialog.setContentView(R.layout.awake_havecard_change);

                EditText etxtAwake = awakeHaveDialog.findViewById(R.id.eTxtAwake);
                EditText etxtNum = awakeHaveDialog.findViewById(R.id.etxtNum);
                Button btnCancer = awakeHaveDialog.findViewById(R.id.btnCancer);
                Button btnOK = awakeHaveDialog.findViewById(R.id.btnOK);

                etxtAwake.setText(holder.txtAwake.getText().toString());
                etxtNum.setText(holder.txtHave.getText().toString());

                btnCancer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        awakeHaveDialog.cancel();
                    }
                });

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int awake = awakeRangeSet(etxtAwake.getText().toString());
                        int number = numRangeSet(etxtNum.getText().toString());
                        etxtAwake.setText(awake + "");
                        etxtNum.setText(number + "");
                        //카드 arrayList update
                        useCardList.get(positionGet).setAwake(awake);
                        useCardList.get(positionGet).setCount(number);
                        cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setAwake(awake);
                        cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setCount(number);
                        //카드 DB update
                        cardDBHelper.UpdateInfoCardAwake(awake, useCardList.get(positionGet).getId());
                        cardDBHelper.UpdateInfoCardNum(number, useCardList.get(positionGet).getId());

                        Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                        awakeHaveDialog.cancel();
                    }
                });

                awakeHaveDialog.show();
            }
        });

        holder.isGetCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.isGetCheckbox.isChecked();
                holder.isGetCheckbox.setChecked(checked);
                if (holder.isGetCheckbox.isChecked()) {
                    useCardList.get(positionGet).setGetCard(1);
                    cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setGetCard(1);
                    cardDBHelper.UpdateInfoCardCheck(1, useCardList.get(positionGet).getId());
                } else {
                    useCardList.get(positionGet).setGetCard(0);
                    cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setGetCard(0);
                    cardDBHelper.UpdateInfoCardCheck(0, useCardList.get(positionGet).getId());
                }

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
        private TextView txtAwake;
        private TextView txtHave;
        private CheckBox isGetCheckbox;
        private LinearLayout changeAwakeHave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtAwake = itemView.findViewById(R.id.txtAwake);
            txtHave = itemView.findViewById(R.id.txtHave);
            isGetCheckbox = itemView.findViewById(R.id.isGetCheckbox);
            changeAwakeHave = itemView.findViewById(R.id.changeAwakeHave);
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
