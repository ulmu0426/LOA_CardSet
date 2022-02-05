package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DemonExtraDmgAdapter extends RecyclerView.Adapter<DemonExtraDmgAdapter.ViewHolder> {
    private ArrayList<DemonExtraDmgInfo> DEDInfo;
    private ArrayList<DemonExtraDmgInfo> filterDED;
    private Context context;
    private DemonExtraDmgPage DED_page;
    private ArrayList<CardInfo> cardInfo;
    private CardDBHelper cardDbHelper;

    private final String DED_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String DED_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String DED_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String DED_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String DED_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String DED_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String DED_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String DED_COLUMN_NAME_CARD7_CHECK = "checkCard7";
    private final String DED_COLUMN_NAME_CARD8_CHECK = "checkCard8";
    private final String DED_COLUMN_NAME_CARD9_CHECK = "checkCard9";

    private final String DED_COLUMN_NAME_CARD0_AWAKE = "awakeCard0";
    private final String DED_COLUMN_NAME_CARD1_AWAKE = "awakeCard1";
    private final String DED_COLUMN_NAME_CARD2_AWAKE = "awakeCard2";
    private final String DED_COLUMN_NAME_CARD3_AWAKE = "awakeCard3";
    private final String DED_COLUMN_NAME_CARD4_AWAKE = "awakeCard4";
    private final String DED_COLUMN_NAME_CARD5_AWAKE = "awakeCard5";
    private final String DED_COLUMN_NAME_CARD6_AWAKE = "awakeCard6";
    private final String DED_COLUMN_NAME_CARD7_AWAKE = "awakeCard7";
    private final String DED_COLUMN_NAME_CARD8_AWAKE = "awakeCard8";
    private final String DED_COLUMN_NAME_CARD9_AWAKE = "awakeCard9";

    private final String DED_DIALOG_CARD_AWAKE = "각성 : ";
    private final String DED_DIALOG_CARD_NUM = "보유 : ";

    private float haveDED;
    private int completeDED;

    public DemonExtraDmgAdapter(ArrayList<DemonExtraDmgInfo> DEDInfo) {
        this.DEDInfo = DEDInfo;
    }

    public float getHaveDED() {
        return haveDED;
    }

    public DemonExtraDmgAdapter(Context context, DemonExtraDmgPage demonExtraDmg_page) {
        this.DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;
        this.filterDED = ((MainPage) MainPage.mainContext).DEDInfo;
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.context = context;
        cardDbHelper = new CardDBHelper(context);
        this.DED_page = demonExtraDmg_page;
        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
        updateDEDPage();
    }

    private void pixUpdate() {
        updateDEDPage();
        DED_page.setDED(haveDED);
        DED_page.setDEDBook(completeDED, getItemCount());
    }

    @NonNull
    @Override
    public DemonExtraDmgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_demon_extra_dmg, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DemonExtraDmgAdapter.ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        holder.txtCardbookName_DED.setText(filterDED.get(position).getName());
        holder.txtDEDSumValue.setText("악마 계열 피해량 증가 합 : +" + filterDED.get(position).getDmgSum(filterDED.get(position).getHaveAwake()) + "%");

        //이미지뷰 구현할것
        holder.imgDEDCard0.setImageResource(R.drawable.card_legend_kadan);
        holder.imgDEDCard1.setImageResource(R.drawable.card_legend_ninab);
        holder.imgDEDCard2.setImageResource(R.drawable.card_legend_shandi);
        holder.imgDEDCard3.setImageResource(R.drawable.card_legend_azena_inanna);
        holder.imgDEDCard4.setImageResource(R.drawable.card_legend_bahuntur);
        holder.imgDEDCard5.setImageResource(R.drawable.card_legend_silian);
        holder.imgDEDCard6.setImageResource(R.drawable.card_legend_wei);
        holder.imgDEDCard7.setImageResource(R.drawable.card_legend_dereonaman);
        holder.imgDEDCard8.setImageResource(R.drawable.card_legend_kamaine);
        holder.imgDEDCard9.setImageResource(R.drawable.card_legend_aman);

        //카드 이름 텍스트
        holder.txtDEDCardName0.setText(filterDED.get(position).getCard0());
        holder.txtDEDCardName1.setText(filterDED.get(position).getCard1());
        holder.txtDEDCardName2.setText(filterDED.get(position).getCard2());
        holder.txtDEDCardName3.setText(filterDED.get(position).getCard3());
        holder.txtDEDCardName4.setText(filterDED.get(position).getCard4());
        holder.txtDEDCardName5.setText(filterDED.get(position).getCard5());
        holder.txtDEDCardName6.setText(filterDED.get(position).getCard6());
        holder.txtDEDCardName7.setText(filterDED.get(position).getCard7());
        holder.txtDEDCardName8.setText(filterDED.get(position).getCard8());
        holder.txtDEDCardName9.setText(filterDED.get(position).getCard9());
        //미 획득 카드는 흑백으로
        imgDefaultColor(holder.imgDEDCard0, filter, filterDED.get(position).getCheckCard0());
        imgDefaultColor(holder.imgDEDCard1, filter, filterDED.get(position).getCheckCard1());
        imgDefaultColor(holder.imgDEDCard2, filter, filterDED.get(position).getCheckCard2());
        imgDefaultColor(holder.imgDEDCard3, filter, filterDED.get(position).getCheckCard3());
        imgDefaultColor(holder.imgDEDCard4, filter, filterDED.get(position).getCheckCard4());
        imgDefaultColor(holder.imgDEDCard5, filter, filterDED.get(position).getCheckCard5());
        imgDefaultColor(holder.imgDEDCard6, filter, filterDED.get(position).getCheckCard6());
        imgDefaultColor(holder.imgDEDCard7, filter, filterDED.get(position).getCheckCard7());
        imgDefaultColor(holder.imgDEDCard8, filter, filterDED.get(position).getCheckCard8());
        imgDefaultColor(holder.imgDEDCard9, filter, filterDED.get(position).getCheckCard9());

        //없는 카드 안 보이게
        imgVisibility(filterDED.get(position).getCard2(), holder.imgDEDCard2, holder.txtDEDCardName2);
        imgVisibility(filterDED.get(position).getCard3(), holder.imgDEDCard3, holder.txtDEDCardName3);
        imgVisibility(filterDED.get(position).getCard4(), holder.imgDEDCard4, holder.txtDEDCardName4);
        imgVisibility(filterDED.get(position).getCard5(), holder.imgDEDCard5, holder.txtDEDCardName5);
        imgVisibility(filterDED.get(position).getCard6(), holder.imgDEDCard6, holder.txtDEDCardName6);
        imgVisibility(filterDED.get(position).getCard7(), holder.imgDEDCard7, holder.txtDEDCardName7);
        imgVisibility(filterDED.get(position).getCard8(), holder.imgDEDCard8, holder.txtDEDCardName8);
        imgVisibility(filterDED.get(position).getCard9(), holder.imgDEDCard9, holder.txtDEDCardName9);
        //수집효과 나열
        holder.txtDED_cardCollection.setText(filterDED.get(position).getCompleteDEDBook() + "장 수집");
        holder.txtDED_cardAwake0.setText(filterDED.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + filterDED.get(position).getAwake_sum0() + ") : 악마 계열 피해량 증가 +" + filterDED.get(position).getDmg_p0() + "%");
        holder.txtDED_cardAwake1.setText(filterDED.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + filterDED.get(position).getAwake_sum1() + ") : 악마 계열 피해량 증가 +" + filterDED.get(position).getDmg_p1() + "%");
        holder.txtDED_cardAwake2.setText(filterDED.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + filterDED.get(position).getAwake_sum2() + ") : 악마 계열 피해량 증가 +" + filterDED.get(position).getDmg_p2() + "%");

        //카드 모두 획득 + 각성도에 따라 배경 색 변환 흰/보라/민트/연두/노랑 순으로(노랑은 완전수집+풀각성)
        isCompleteCardBookBackgroundColor(filterDED.get(position), holder.cvDemonExtraDmgBackground);


        holder.cvDemonExtraDmgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogDEDDetail = new Dialog(context);
                dialogDEDDetail.setContentView(R.layout.demon_extra_dmg_detail);
                WindowManager.LayoutParams params = dialogDEDDetail.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogDEDDetail.getWindow().setAttributes((WindowManager.LayoutParams) params);
                int pos = positionGet;

                TextView txtDEDCardBookName = dialogDEDDetail.findViewById(R.id.txtDEDCardBookName);
                TextView txtDED_AwakeValue = dialogDEDDetail.findViewById(R.id.txtDED_AwakeValue);
                txtDEDCardBookName.setText(filterDED.get(pos).getName());
                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                ImageView imgDED_Detail_Card0 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card0);
                ImageView imgDED_Detail_Card1 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card1);
                ImageView imgDED_Detail_Card2 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card2);
                ImageView imgDED_Detail_Card3 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card3);
                ImageView imgDED_Detail_Card4 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card4);
                ImageView imgDED_Detail_Card5 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card5);
                ImageView imgDED_Detail_Card6 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card6);
                ImageView imgDED_Detail_Card7 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card7);
                ImageView imgDED_Detail_Card8 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card8);
                ImageView imgDED_Detail_Card9 = dialogDEDDetail.findViewById(R.id.imgDED_Detail_Card9);

                //카드 이미지 세팅 - 추후 변경 예정
                imgDED_Detail_Card0.setImageResource(R.drawable.card_legend_kadan);
                imgDED_Detail_Card1.setImageResource(R.drawable.card_legend_ninab);
                imgDED_Detail_Card2.setImageResource(R.drawable.card_legend_shandi);
                imgDED_Detail_Card3.setImageResource(R.drawable.card_legend_azena_inanna);
                imgDED_Detail_Card4.setImageResource(R.drawable.card_legend_bahuntur);
                imgDED_Detail_Card5.setImageResource(R.drawable.card_legend_silian);
                imgDED_Detail_Card6.setImageResource(R.drawable.card_legend_wei);
                imgDED_Detail_Card7.setImageResource(R.drawable.card_legend_dereonaman);
                imgDED_Detail_Card8.setImageResource(R.drawable.card_legend_kamaine);
                imgDED_Detail_Card9.setImageResource(R.drawable.card_legend_aman);
                //이미지 기본 색상 : 획득카드가 아니면 흑백
                imgDefaultColor(imgDED_Detail_Card0, filter, filterDED.get(pos).getCheckCard0());
                imgDefaultColor(imgDED_Detail_Card1, filter, filterDED.get(pos).getCheckCard1());
                imgDefaultColor(imgDED_Detail_Card2, filter, filterDED.get(pos).getCheckCard2());
                imgDefaultColor(imgDED_Detail_Card3, filter, filterDED.get(pos).getCheckCard3());
                imgDefaultColor(imgDED_Detail_Card4, filter, filterDED.get(pos).getCheckCard4());
                imgDefaultColor(imgDED_Detail_Card5, filter, filterDED.get(pos).getCheckCard5());
                imgDefaultColor(imgDED_Detail_Card6, filter, filterDED.get(pos).getCheckCard6());
                imgDefaultColor(imgDED_Detail_Card7, filter, filterDED.get(pos).getCheckCard7());
                imgDefaultColor(imgDED_Detail_Card8, filter, filterDED.get(pos).getCheckCard8());
                imgDefaultColor(imgDED_Detail_Card9, filter, filterDED.get(pos).getCheckCard9());

                //텍스트 연결
                //카드 이름
                TextView txtDED_Detail_Card0 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card0);
                TextView txtDED_Detail_Card1 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card1);
                TextView txtDED_Detail_Card2 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card2);
                TextView txtDED_Detail_Card3 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card3);
                TextView txtDED_Detail_Card4 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card4);
                TextView txtDED_Detail_Card5 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card5);
                TextView txtDED_Detail_Card6 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card6);
                TextView txtDED_Detail_Card7 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card7);
                TextView txtDED_Detail_Card8 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card8);
                TextView txtDED_Detail_Card9 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_Card9);
                //각성도
                TextView txtDED_Detail_CardAwakeHaveCard0 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard0);
                TextView txtDED_Detail_CardAwakeHaveCard1 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard1);
                TextView txtDED_Detail_CardAwakeHaveCard2 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard2);
                TextView txtDED_Detail_CardAwakeHaveCard3 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard3);
                TextView txtDED_Detail_CardAwakeHaveCard4 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard4);
                TextView txtDED_Detail_CardAwakeHaveCard5 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard5);
                TextView txtDED_Detail_CardAwakeHaveCard6 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard6);
                TextView txtDED_Detail_CardAwakeHaveCard7 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard7);
                TextView txtDED_Detail_CardAwakeHaveCard8 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard8);
                TextView txtDED_Detail_CardAwakeHaveCard9 = dialogDEDDetail.findViewById(R.id.txtDED_Detail_CardAwakeHaveCard9);

                //카드 이름 세팅
                txtDED_Detail_Card0.setText(filterDED.get(pos).getCard0());
                txtDED_Detail_Card1.setText(filterDED.get(pos).getCard1());
                txtDED_Detail_Card2.setText(filterDED.get(pos).getCard2());
                txtDED_Detail_Card3.setText(filterDED.get(pos).getCard3());
                txtDED_Detail_Card4.setText(filterDED.get(pos).getCard4());
                txtDED_Detail_Card5.setText(filterDED.get(pos).getCard5());
                txtDED_Detail_Card6.setText(filterDED.get(pos).getCard6());
                txtDED_Detail_Card7.setText(filterDED.get(pos).getCard7());
                txtDED_Detail_Card8.setText(filterDED.get(pos).getCard8());
                txtDED_Detail_Card9.setText(filterDED.get(pos).getCard9());
                //카드 각성도 세팅
                txtDED_Detail_CardAwakeHaveCard0.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard0() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).getCount());
                txtDED_Detail_CardAwakeHaveCard1.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard1() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).getCount());
                txtDED_Detail_CardAwakeHaveCard2.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard2() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).getCount());
                txtDED_Detail_CardAwakeHaveCard3.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard3() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).getCount());
                txtDED_Detail_CardAwakeHaveCard4.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard4() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).getCount());
                txtDED_Detail_CardAwakeHaveCard5.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard5() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).getCount());
                txtDED_Detail_CardAwakeHaveCard6.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard6() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).getCount());
                txtDED_Detail_CardAwakeHaveCard7.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard7() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).getCount());
                txtDED_Detail_CardAwakeHaveCard8.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard8() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).getCount());
                txtDED_Detail_CardAwakeHaveCard9.setText(DED_DIALOG_CARD_AWAKE + filterDED.get(pos).getAwakeCard9() + "\n" + DED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).getCount());

                //없는 카드 안 보이게 - 카드 이미지, 카드 이름, 카드 각성도, 카드 보유 수
                imgVisibility(filterDED.get(pos).getCard2(), imgDED_Detail_Card2, txtDED_Detail_Card2, txtDED_Detail_CardAwakeHaveCard2);
                imgVisibility(filterDED.get(pos).getCard3(), imgDED_Detail_Card3, txtDED_Detail_Card3, txtDED_Detail_CardAwakeHaveCard3);
                imgVisibility(filterDED.get(pos).getCard4(), imgDED_Detail_Card4, txtDED_Detail_Card4, txtDED_Detail_CardAwakeHaveCard4);
                imgVisibility(filterDED.get(pos).getCard5(), imgDED_Detail_Card5, txtDED_Detail_Card5, txtDED_Detail_CardAwakeHaveCard5);
                imgVisibility(filterDED.get(pos).getCard6(), imgDED_Detail_Card6, txtDED_Detail_Card6, txtDED_Detail_CardAwakeHaveCard6);
                imgVisibility(filterDED.get(pos).getCard7(), imgDED_Detail_Card7, txtDED_Detail_Card7, txtDED_Detail_CardAwakeHaveCard7);
                imgVisibility(filterDED.get(pos).getCard8(), imgDED_Detail_Card8, txtDED_Detail_Card8, txtDED_Detail_CardAwakeHaveCard8);
                imgVisibility(filterDED.get(pos).getCard9(), imgDED_Detail_Card9, txtDED_Detail_Card9, txtDED_Detail_CardAwakeHaveCard9);

                Dialog dialogChangeAwakeAndNum = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialogChangeAwakeAndNum.setContentView(R.layout.awake_havecard_change);
                EditText etxtAwake = dialogChangeAwakeAndNum.findViewById(R.id.eTxtAwake);
                EditText etxtNum = dialogChangeAwakeAndNum.findViewById(R.id.etxtNum);
                Button btnCancer = dialogChangeAwakeAndNum.findViewById(R.id.btnCancer);
                Button btnOK = dialogChangeAwakeAndNum.findViewById(R.id.btnOK);


                txtDED_Detail_CardAwakeHaveCard0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard0() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).getCount() + "");

                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD0_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard0(awake);
                                filterDED.get(pos).setAwakeCard0(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard0.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard1() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).getCount() + "");
                        dialogChangeAwakeAndNum.show();

                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD1_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard1(awake);
                                filterDED.get(pos).setAwakeCard1(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard1.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard2() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD2_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard2(awake);
                                filterDED.get(pos).setAwakeCard2(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard2.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard3() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD3_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard3(awake);
                                filterDED.get(pos).setAwakeCard3(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard3.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard4() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD4_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard4(awake);
                                filterDED.get(pos).setAwakeCard4(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard4.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard5() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD5_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard5(awake);
                                filterDED.get(pos).setAwakeCard5(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard5.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard6() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD6_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard6(awake);
                                filterDED.get(pos).setAwakeCard6(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard6.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard7() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD7_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard7(awake);
                                filterDED.get(pos).setAwakeCard7(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard7.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard8() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD8_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard8(awake);
                                filterDED.get(pos).setAwakeCard8(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard8.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(filterDED.get(pos).getAwakeCard9() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).getCount() + "");
                        dialogChangeAwakeAndNum.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD9_AWAKE, awake, filterDED.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).getId());    //카드 각성도 업데이트(cardListDB)
                                DEDInfo.get(getIndex(filterDED.get(pos))).setAwakeCard9(awake);
                                filterDED.get(pos).setAwakeCard9(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).setCount(number);
                                txtDED_Detail_CardAwakeHaveCard9.setText(DED_DIALOG_CARD_AWAKE + awake + "\n" + DED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterDED.get(pos).getHaveAwake());

                                updateDEDPage();
                                isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });

                imgDED_Detail_Card0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card0, filter, filterDED.get(pos).getCheckCard0());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD0_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard0());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard0(cardCheck);                                                        //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard0(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard0())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card1, filter, filterDED.get(pos).getCheckCard1());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD1_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard1());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard1(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard1(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard1())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card2, filter, filterDED.get(pos).getCheckCard2());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD2_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard2());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard2(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard2(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard2())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card3, filter, filterDED.get(pos).getCheckCard3());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD3_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard3());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard3(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard3(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard3())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card4, filter, filterDED.get(pos).getCheckCard4());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD4_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard4());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard4(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard4(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard4())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card5, filter, filterDED.get(pos).getCheckCard5());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD5_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard5());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard5(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard5(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard5())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card6, filter, filterDED.get(pos).getCheckCard6());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD6_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard6());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard6(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard6(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard6())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card7, filter, filterDED.get(pos).getCheckCard7());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD7_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard7());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard7(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard7(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard7())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card8, filter, filterDED.get(pos).getCheckCard8());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD8_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard8());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard8(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard8(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard8())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });
                imgDED_Detail_Card9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card9, filter, filterDED.get(pos).getCheckCard9());
                        cardDbHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD9_CHECK, cardCheck, filterDED.get(pos).getId());   //cardX수집 유무 업데이트(DED DB)
                        cardDbHelper.UpdateInfoCardCheck(cardCheck, filterDED.get(pos).getCard9());     //카드 수집 유무 업데이트(cardList DB)
                        DEDInfo.get(getIndex(filterDED.get(pos))).setCheckCard9(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterDED.get(pos).setCheckCard9(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterDED.get(pos).getCard9())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateDEDPage();
                        isCompleteCardBookBackgroundColor(filterDED.get(pos), holder.cvDemonExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)

                        notifyDataSetChanged();

                    }
                });


                dialogDEDDetail.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return filterDED.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cvDemonExtraDmgBackground;
        private TextView txtCardbookName_DED;
        private TextView txtDEDSumValue;
        private ImageView imgDEDCard0;
        private ImageView imgDEDCard1;
        private ImageView imgDEDCard2;
        private ImageView imgDEDCard3;
        private ImageView imgDEDCard4;
        private ImageView imgDEDCard5;
        private ImageView imgDEDCard6;
        private ImageView imgDEDCard7;
        private ImageView imgDEDCard8;
        private ImageView imgDEDCard9;
        private TextView txtDEDCardName0;
        private TextView txtDEDCardName1;
        private TextView txtDEDCardName2;
        private TextView txtDEDCardName3;
        private TextView txtDEDCardName4;
        private TextView txtDEDCardName5;
        private TextView txtDEDCardName6;
        private TextView txtDEDCardName7;
        private TextView txtDEDCardName8;
        private TextView txtDEDCardName9;
        private TextView txtDED_cardCollection;
        private TextView txtDED_cardAwake0;
        private TextView txtDED_cardAwake1;
        private TextView txtDED_cardAwake2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvDemonExtraDmgBackground = itemView.findViewById(R.id.cvDemonExtraDmgBackground);
            txtCardbookName_DED = itemView.findViewById(R.id.txtCardbookName_DED);
            txtDEDSumValue = itemView.findViewById(R.id.txtDEDSumValue);
            imgDEDCard0 = itemView.findViewById(R.id.imgDEDCard0);
            imgDEDCard1 = itemView.findViewById(R.id.imgDEDCard1);
            imgDEDCard2 = itemView.findViewById(R.id.imgDEDCard2);
            imgDEDCard3 = itemView.findViewById(R.id.imgDEDCard3);
            imgDEDCard4 = itemView.findViewById(R.id.imgDEDCard4);
            imgDEDCard5 = itemView.findViewById(R.id.imgDEDCard5);
            imgDEDCard6 = itemView.findViewById(R.id.imgDEDCard6);
            imgDEDCard7 = itemView.findViewById(R.id.imgDEDCard7);
            imgDEDCard8 = itemView.findViewById(R.id.imgDEDCard8);
            imgDEDCard9 = itemView.findViewById(R.id.imgDEDCard9);
            txtDEDCardName0 = itemView.findViewById(R.id.txtDEDCardName0);
            txtDEDCardName1 = itemView.findViewById(R.id.txtDEDCardName1);
            txtDEDCardName2 = itemView.findViewById(R.id.txtDEDCardName2);
            txtDEDCardName3 = itemView.findViewById(R.id.txtDEDCardName3);
            txtDEDCardName4 = itemView.findViewById(R.id.txtDEDCardName4);
            txtDEDCardName5 = itemView.findViewById(R.id.txtDEDCardName5);
            txtDEDCardName6 = itemView.findViewById(R.id.txtDEDCardName6);
            txtDEDCardName7 = itemView.findViewById(R.id.txtDEDCardName7);
            txtDEDCardName8 = itemView.findViewById(R.id.txtDEDCardName8);
            txtDEDCardName9 = itemView.findViewById(R.id.txtDEDCardName9);
            txtDED_cardCollection = itemView.findViewById(R.id.txtDED_cardCollection);
            txtDED_cardAwake0 = itemView.findViewById(R.id.txtDED_cardAwake0);
            txtDED_cardAwake1 = itemView.findViewById(R.id.txtDED_cardAwake1);
            txtDED_cardAwake2 = itemView.findViewById(R.id.txtDED_cardAwake2);

        }
    }

    //도감에 없는 카드는 안보이게
    private void imgVisibility(String card, ImageView imgCard, TextView txtCardName) {
        if (card.isEmpty()) {
            imgCard.setVisibility(View.INVISIBLE);
            txtCardName.setVisibility(View.INVISIBLE);
        } else {
            imgCard.setVisibility(View.VISIBLE);
            txtCardName.setVisibility(View.VISIBLE);
        }
    }

    //각성도까지 안
    private void imgVisibility(String card, ImageView imgCard, TextView txtCardName, TextView txtAwake) {
        if (card.isEmpty()) {
            imgCard.setVisibility(View.INVISIBLE);
            txtCardName.setVisibility(View.INVISIBLE);
            txtAwake.setVisibility(View.INVISIBLE);
        } else {
            imgCard.setVisibility(View.VISIBLE);
            txtCardName.setVisibility(View.VISIBLE);
            txtAwake.setVisibility(View.VISIBLE);
        }
    }

    //획득 못한 카드는 흑백이 기본으로 보이도록
    private void imgDefaultColor(ImageView iv, ColorMatrixColorFilter filter, int check) {
        if (check == 1)
            iv.setColorFilter(null);
        else
            iv.setColorFilter(filter);
    }

    //클릭시 카드를 흑백으로 바꿈.(흑백이면 컬러로, 컬러면 흑백으로), 데이터베이스 카드 도감 획득 유무도 변경.(흑백은 0, 컬러는 1)
    private int imgGrayScale(ImageView iv, ColorMatrixColorFilter filter, int check) {
        if (iv.getColorFilter() != filter) {
            iv.setColorFilter(filter);
            check = 0;
        } else {
            iv.setColorFilter(null);
            check = 1;
        }
        return check;
    }

    // 악추피 도감작을 완성시키면 각성도에 따라 도감의 배경을 각 단계별로 흰색->민트색->초록색->노란색으로 바꿈
    private void isCompleteCardBookBackgroundColor(DemonExtraDmgInfo DEDInfo, ConstraintLayout cv) {
        if ((DEDInfo.getHaveCard() == DEDInfo.getCompleteDEDBook())) {
            if (DEDInfo.getHaveAwake() == DEDInfo.getAwake_sum2())
                cv.setBackgroundColor(Color.parseColor("#D0FFE870"));   //노랑 - 전부수집+풀각성
            else if (DEDInfo.getHaveAwake() < DEDInfo.getAwake_sum2() && DEDInfo.getHaveAwake() >= DEDInfo.getAwake_sum1())
                cv.setBackgroundColor(Color.parseColor("#CFFFCC"));//초록 - 전부수집+올4각성 이상
            else if (DEDInfo.getHaveAwake() < DEDInfo.getAwake_sum1() && DEDInfo.getHaveAwake() >= DEDInfo.getAwake_sum0())
                cv.setBackgroundColor(Color.parseColor("#CCFFFB"));//민트 - 전부수집+올2각성
            else
                cv.setBackgroundColor(Color.parseColor("#C5BEFF"));//연보라 - 전부수집
        } else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteDED(DemonExtraDmgInfo DEDInfo) {
        if (DEDInfo.getHaveCard() == DEDInfo.getCompleteDEDBook())
            return true;
        else
            return false;
    }

    private boolean isAllCompleteDED(DemonExtraDmgInfo DEDInfo) {
        if (DEDInfo.getHaveCard() == DEDInfo.getCompleteDEDBook()){
            if(DEDInfo.getHaveAwake() == DEDInfo.getAwake_sum2())
                return true;
            else
                return false;
        }else{
            return false;
        }
    }


    //악추피 값 합산.
    public void haveDEDUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        haveDED = 0;
        completeDED = 0;
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (isCompleteDED(DEDInfo.get(i))) {
                haveDED += DEDInfo.get(i).getDmgSum(DEDInfo.get(i).getHaveAwake());
                if (DEDInfo.get(i).getAwake_sum2() == DEDInfo.get(i).getHaveAwake()) {
                    completeDED++;
                }
            }
        }
        haveDED = Float.parseFloat(df.format(haveDED));
    }

    //cardList 갱신을 위한 메소드
    private int getIndex(ArrayList<CardInfo> cardInfo, String name) {
        int index = 0;
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }
        return index;
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

    private void updateDEDPage() {
        haveDEDUpdate();                                                                                     //악추피 값,완성도 갱신
        DED_page.setDED(haveDED);                                                                           //악추피 페이지 값 갱신한 것 세팅
        DED_page.setDEDBook(completeDED, getItemCount());                                                    //악추피 도감 완성도 갱신한 것 세팅
        ((MainPage) MainPage.mainContext).setDemonExtraDmgInfo(haveDED);                            //MainPage 악추피 값 갱신한 것 세팅
    }

    private int getIndex(DemonExtraDmgInfo demonExtraDmgInfo){
        int index = 0;
        for(int i =0 ;i<DEDInfo.size();i++){
            if(DEDInfo.get(i).getId() == demonExtraDmgInfo.getId()) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterDED = DEDInfo;
                } else {
                    ArrayList<DemonExtraDmgInfo> filteringList = new ArrayList<DemonExtraDmgInfo>();
                    for (int i = 0; i < DEDInfo.size(); i++) {
                        if (!isAllCompleteDED(DEDInfo.get(i))) {
                            filteringList.add(DEDInfo.get(i));
                        }
                    }
                    filterDED = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterDED;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterDED = (ArrayList<DemonExtraDmgInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
