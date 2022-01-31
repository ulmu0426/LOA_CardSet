package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingCardAdapter extends RecyclerView.Adapter<SettingCardAdapter.ViewHolder> {
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

    public SettingCardAdapter(Context context, String grade) {      //그레이드에 따라 구현
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
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        holder.imgJustCard.setImageResource(R.drawable.card_legend_wei);
        defaultColorFilter(holder.imgJustCard, position, filter);

        holder.txtCardName.setText(useCardList.get(position).getName());
        if (useCardList.get(position).getGetCard() != 0)
            holder.txtCardAwakeAndHave.setText("각성 : " + useCardList.get(position).getAwake() + "\n보유 : " + useCardList.get(position).getCount());
        else
            holder.txtCardAwakeAndHave.setText("각성 : " + 0 + "\n보유 : " + 0);

        holder.rvCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = positionGet;
                Dialog dialogJustCard = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialogJustCard.setContentView(R.layout.just_card);

                WindowManager.LayoutParams params = dialogJustCard.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogJustCard.getWindow().setAttributes((WindowManager.LayoutParams) params);

                TextView txtJustCardName = dialogJustCard.findViewById(R.id.txtJustCardName);
                ImageView imgJustCard = dialogJustCard.findViewById(R.id.imgJustCard);
                EditText etxtJustCardAwake = dialogJustCard.findViewById(R.id.etxtJustCardAwake); // 각성도
                EditText etxtJustCardHave = dialogJustCard.findViewById(R.id.etxtJustCardHave);   // 보유카드
                TextView txtJustCardAcquisition_info = dialogJustCard.findViewById(R.id.txtJustCardAcquisition_info);   //획득 카드 유무
                Button btnCancer_JustCard = dialogJustCard.findViewById(R.id.btnCancer_JustCard);
                Button btnOK_JustCard = dialogJustCard.findViewById(R.id.btnOK_JustCard);

                txtJustCardName.setText(useCardList.get(pos).getName());
                imgJustCard.setImageResource(R.drawable.card_legend_kamaine);
                defaultColorFilter(imgJustCard, pos, filter);
                etxtJustCardAwake.setText(useCardList.get(pos).getAwake() + "");
                etxtJustCardHave.setText(useCardList.get(pos).getCount() + "");
                txtJustCardAcquisition_info.setText(useCardList.get(pos).getAcquisition_info() + "");

                imgJustCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = setColorFilter(imgJustCard, filter);
                        useCardList.get(pos).setGetCard(check);
                        setCheckCard(useCardList.get(pos), check);  //cardInfo cardListDB 업데이트

                        notifyDataSetChanged();
                    }
                });

                btnCancer_JustCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogJustCard.cancel();
                    }
                });

                btnOK_JustCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int awake = awakeRangeSet(etxtJustCardAwake.getText().toString());
                        int number = numRangeSet(etxtJustCardHave.getText().toString());
                        etxtJustCardAwake.setText(awake + "");
                        etxtJustCardHave.setText(number + "");
                        useCardList.get(pos).setAwake(awake);
                        useCardList.get(pos).setCount(number);
                        cardInfo.get(matchIndex(useCardList.get(pos).getId())).setAwake(awake);
                        cardInfo.get(matchIndex(useCardList.get(pos).getId())).setCount(number);
                        cardDBHelper.UpdateInfoCardAwake( awake, useCardList.get(pos).getId());
                        cardDBHelper.UpdateInfoCardNum(number, useCardList.get(pos).getId());

                        Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                        dialogJustCard.cancel();
                    }
                });

                dialogJustCard.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return useCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout rvCardList;
        private ImageView imgJustCard;
        private TextView txtCardName;
        private TextView txtCardAwakeAndHave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCardList = itemView.findViewById(R.id.rvCardList);
            imgJustCard = itemView.findViewById(R.id.imgJustCard);
            txtCardName = itemView.findViewById(R.id.txtCardName);
            txtCardAwakeAndHave = itemView.findViewById(R.id.txtCardAwakeAndHave);
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

    //미획득 카드는 클릭시 컬러로, 획득카드는 클릭시 흑백으로 변경 시키며 획득유무 변경
    private int setColorFilter(ImageView iv, ColorFilter filter) {
        int check = 0;
        if (iv.getColorFilter() == filter) {
            check = 1;
            iv.setColorFilter(null);
        } else {
            check = 0;
            iv.setColorFilter(filter);
        }
        return check;
    }

    private void setCheckCard(CardInfo ci, int check) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(ci.getName())) {
                if (ci.getGetCard() == check) {
                    cardInfo.get(i).setGetCard(check);
                    cardDBHelper.UpdateInfoCardCheck( check, cardInfo.get(i).getName());
                    break;
                }
            }
        }
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

    private int matchIndex(int id){
        int index = 0;
        for(int i = 0; i < cardInfo.size();i++){
            if(cardInfo.get(i).getId() == id){
                index = i;
                break;
            }
        }
        return index;
    }
}
