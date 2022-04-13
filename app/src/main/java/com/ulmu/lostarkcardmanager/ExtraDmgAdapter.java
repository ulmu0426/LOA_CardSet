package com.ulmu.lostarkcardmanager;

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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExtraDmgAdapter extends RecyclerView.Adapter<ExtraDmgAdapter.ViewHolder> {
    private Context context;
    private String EDName;
    private final String ED_TEXT = " 계열 피해량 증가 합 : +";
    private final String ED_TEXT_ = " 계열 피해량 증가 : +";
    private final String ED_PERCENT = "%";

    private final String XED_DIALOG_CARD_AWAKE = "각성 : ";
    private final String XED_DIALOG_CARD_NUM = "보유 : ";

    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
    private ExtraDmgPage extraDmgPage;
    private ArrayList<ExtraDmgInfo> extraDmgInfo;
    private ArrayList<ExtraDmgInfo> filterXED;

    private CardDBHelper cardDBHelper;

    //미완성된 DED 도감 목록
    private ArrayList<ExtraDmgInfo> baseFilteredXED;

    private float haveXED;
    private int completeXED;

    public ExtraDmgAdapter(Context context, String EDName, ArrayList<ExtraDmgInfo> extraDmgInfo, ExtraDmgPage extraDmgPage) {
        this.context = context;
        this.EDName = EDName;
        this.extraDmgInfo = extraDmgInfo;
        this.filterXED = extraDmgInfo;
        this.extraDmgPage = extraDmgPage;
        this.baseFilteredXED = new ArrayList<>();
        setBaseFilteredXED();
        cardDBHelper = new CardDBHelper(context);
        updateXEDPage();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_extra_dmg, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        holder.txtXEDName.setText(extraDmgInfo.get(position).getName());
        holder.txtXEDSumValue.setText(EDName + ED_TEXT + extraDmgInfo.get(position).getDmgSum() + ED_PERCENT);

        holder.imgXEDCard0.setImageResource(getCardImg(filterXED.get(position).getCard0()));
        holder.imgXEDCard1.setImageResource(getCardImg(filterXED.get(position).getCard1()));
        holder.imgXEDCard2.setImageResource(getCardImg(filterXED.get(position).getCard2()));
        holder.imgXEDCard3.setImageResource(getCardImg(filterXED.get(position).getCard3()));
        holder.imgXEDCard4.setImageResource(getCardImg(filterXED.get(position).getCard4()));
        holder.imgXEDCard5.setImageResource(getCardImg(filterXED.get(position).getCard5()));
        holder.imgXEDCard6.setImageResource(getCardImg(filterXED.get(position).getCard6()));
        holder.imgXEDCard7.setImageResource(getCardImg(filterXED.get(position).getCard7()));
        holder.imgXEDCard8.setImageResource(getCardImg(filterXED.get(position).getCard8()));
        holder.imgXEDCard9.setImageResource(getCardImg(filterXED.get(position).getCard9()));


        holder.txtXEDCardName0.setText(filterXED.get(position).getCard0());
        holder.txtXEDCardName1.setText(filterXED.get(position).getCard1());
        holder.txtXEDCardName2.setText(filterXED.get(position).getCard2());
        holder.txtXEDCardName3.setText(filterXED.get(position).getCard3());
        holder.txtXEDCardName4.setText(filterXED.get(position).getCard4());
        holder.txtXEDCardName5.setText(filterXED.get(position).getCard5());
        holder.txtXEDCardName6.setText(filterXED.get(position).getCard6());
        holder.txtXEDCardName7.setText(filterXED.get(position).getCard7());
        holder.txtXEDCardName8.setText(filterXED.get(position).getCard8());
        holder.txtXEDCardName9.setText(filterXED.get(position).getCard9());

        //미 획득 카드는 흑백으로
        imgDefaultColor(holder.imgXEDCard0, filter, filterXED.get(position).isCheckCard0(), filterXED.get(position).getCard0());
        imgDefaultColor(holder.imgXEDCard1, filter, filterXED.get(position).isCheckCard1(), filterXED.get(position).getCard1());
        imgDefaultColor(holder.imgXEDCard2, filter, filterXED.get(position).isCheckCard2(), filterXED.get(position).getCard2());
        imgDefaultColor(holder.imgXEDCard3, filter, filterXED.get(position).isCheckCard3(), filterXED.get(position).getCard3());
        imgDefaultColor(holder.imgXEDCard4, filter, filterXED.get(position).isCheckCard4(), filterXED.get(position).getCard4());
        imgDefaultColor(holder.imgXEDCard5, filter, filterXED.get(position).isCheckCard5(), filterXED.get(position).getCard5());
        imgDefaultColor(holder.imgXEDCard6, filter, filterXED.get(position).isCheckCard6(), filterXED.get(position).getCard6());
        imgDefaultColor(holder.imgXEDCard7, filter, filterXED.get(position).isCheckCard7(), filterXED.get(position).getCard7());
        imgDefaultColor(holder.imgXEDCard8, filter, filterXED.get(position).isCheckCard8(), filterXED.get(position).getCard8());
        imgDefaultColor(holder.imgXEDCard9, filter, filterXED.get(position).isCheckCard9(), filterXED.get(position).getCard9());
        //없는 카드 안 보이게
        imgVisibility(filterXED.get(position).getCard2(), holder.imgXEDCard2, holder.txtXEDCardName2);
        imgVisibility(filterXED.get(position).getCard3(), holder.imgXEDCard3, holder.txtXEDCardName3);
        imgVisibility(filterXED.get(position).getCard4(), holder.imgXEDCard4, holder.txtXEDCardName4);
        imgVisibility(filterXED.get(position).getCard5(), holder.imgXEDCard5, holder.txtXEDCardName5);
        imgVisibility(filterXED.get(position).getCard6(), holder.imgXEDCard6, holder.txtXEDCardName6);
        imgVisibility(filterXED.get(position).getCard7(), holder.imgXEDCard7, holder.txtXEDCardName7);
        imgVisibility(filterXED.get(position).getCard8(), holder.imgXEDCard8, holder.txtXEDCardName8);
        imgVisibility(filterXED.get(position).getCard9(), holder.imgXEDCard9, holder.txtXEDCardName9);

        holder.txtXED_cardCollection.setText(filterXED.get(position).getNeedCard() + "장 수집");
        holder.txtXED_cardAwake0.setText(filterXED.get(position).getNeedCard() + "장 수집(각성단계 합계" +
                filterXED.get(position).getAwakeSum0() + ") : " + EDName + ED_TEXT_ + filterXED.get(position).getDmgP0() + ED_PERCENT);
        holder.txtXED_cardAwake1.setText(filterXED.get(position).getNeedCard() + "장 수집(각성단계 합계" +
                filterXED.get(position).getAwakeSum1() + ") : " + EDName + ED_TEXT_ + filterXED.get(position).getDmgP1() + ED_PERCENT);
        holder.txtXED_cardAwake2.setText(filterXED.get(position).getNeedCard() + "장 수집(각성단계 합계" +
                filterXED.get(position).getAwakeSum2() + ") : " + EDName + ED_TEXT_ + filterXED.get(position).getDmgP2() + ED_PERCENT);

        //카드 모두 획득 + 각성도에 따라 배경 색 변환 흰/보라/민트/연두/노랑 순으로(노랑은 완전수집+풀각성)
        isCompleteCardBookBackgroundColor(filterXED.get(position), holder.cvExtraDmgBackground);

        holder.cvExtraDmgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogDEDDetail = new Dialog(context);
                dialogDEDDetail.setContentView(R.layout.extra_dmg_detail);
                WindowManager.LayoutParams params = dialogDEDDetail.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogDEDDetail.getWindow().setAttributes((WindowManager.LayoutParams) params);
                int pos = positionGet;

                TextView txtDEDCardBookName = dialogDEDDetail.findViewById(R.id.txtDEDCardBookName);
                TextView txtDED_AwakeValue = dialogDEDDetail.findViewById(R.id.txtDED_AwakeValue);
                TextView txtDED_NextStep = dialogDEDDetail.findViewById(R.id.txtDED_NextStep);
                txtDEDCardBookName.setText(filterXED.get(pos).getName());
                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());
                nextDED(txtDED_NextStep, filterXED.get(pos));

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
                imgDED_Detail_Card0.setImageResource(getCardImg(filterXED.get(pos).getCard0()));
                imgDED_Detail_Card1.setImageResource(getCardImg(filterXED.get(pos).getCard1()));
                imgDED_Detail_Card2.setImageResource(getCardImg(filterXED.get(pos).getCard2()));
                imgDED_Detail_Card3.setImageResource(getCardImg(filterXED.get(pos).getCard3()));
                imgDED_Detail_Card4.setImageResource(getCardImg(filterXED.get(pos).getCard4()));
                imgDED_Detail_Card5.setImageResource(getCardImg(filterXED.get(pos).getCard5()));
                imgDED_Detail_Card6.setImageResource(getCardImg(filterXED.get(pos).getCard6()));
                imgDED_Detail_Card7.setImageResource(getCardImg(filterXED.get(pos).getCard7()));
                imgDED_Detail_Card8.setImageResource(getCardImg(filterXED.get(pos).getCard8()));
                imgDED_Detail_Card9.setImageResource(getCardImg(filterXED.get(pos).getCard9()));

                //이미지 기본 색상 : 획득카드가 아니면 흑백
                imgDefaultColor(imgDED_Detail_Card0, filter, filterXED.get(pos).isCheckCard0(), filterXED.get(pos).getCard0());
                imgDefaultColor(imgDED_Detail_Card1, filter, filterXED.get(pos).isCheckCard1(), filterXED.get(pos).getCard1());
                imgDefaultColor(imgDED_Detail_Card2, filter, filterXED.get(pos).isCheckCard2(), filterXED.get(pos).getCard2());
                imgDefaultColor(imgDED_Detail_Card3, filter, filterXED.get(pos).isCheckCard3(), filterXED.get(pos).getCard3());
                imgDefaultColor(imgDED_Detail_Card4, filter, filterXED.get(pos).isCheckCard4(), filterXED.get(pos).getCard4());
                imgDefaultColor(imgDED_Detail_Card5, filter, filterXED.get(pos).isCheckCard5(), filterXED.get(pos).getCard5());
                imgDefaultColor(imgDED_Detail_Card6, filter, filterXED.get(pos).isCheckCard6(), filterXED.get(pos).getCard6());
                imgDefaultColor(imgDED_Detail_Card7, filter, filterXED.get(pos).isCheckCard7(), filterXED.get(pos).getCard7());
                imgDefaultColor(imgDED_Detail_Card8, filter, filterXED.get(pos).isCheckCard8(), filterXED.get(pos).getCard8());
                imgDefaultColor(imgDED_Detail_Card9, filter, filterXED.get(pos).isCheckCard9(), filterXED.get(pos).getCard9());

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
                txtDED_Detail_Card0.setText(filterXED.get(pos).getCard0());
                txtDED_Detail_Card1.setText(filterXED.get(pos).getCard1());
                txtDED_Detail_Card2.setText(filterXED.get(pos).getCard2());
                txtDED_Detail_Card3.setText(filterXED.get(pos).getCard3());
                txtDED_Detail_Card4.setText(filterXED.get(pos).getCard4());
                txtDED_Detail_Card5.setText(filterXED.get(pos).getCard5());
                txtDED_Detail_Card6.setText(filterXED.get(pos).getCard6());
                txtDED_Detail_Card7.setText(filterXED.get(pos).getCard7());
                txtDED_Detail_Card8.setText(filterXED.get(pos).getCard8());
                txtDED_Detail_Card9.setText(filterXED.get(pos).getCard9());
                //카드 각성도 세팅
                txtDED_Detail_CardAwakeHaveCard0.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard0() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getNum());
                txtDED_Detail_CardAwakeHaveCard1.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard1() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getNum());
                txtDED_Detail_CardAwakeHaveCard2.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard2() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getNum());
                txtDED_Detail_CardAwakeHaveCard3.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard3() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getNum());
                txtDED_Detail_CardAwakeHaveCard4.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard4() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getNum());
                txtDED_Detail_CardAwakeHaveCard5.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard5() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getNum());
                txtDED_Detail_CardAwakeHaveCard6.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard6() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getNum());
                txtDED_Detail_CardAwakeHaveCard7.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard7() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getNum());
                txtDED_Detail_CardAwakeHaveCard8.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard8() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getNum());
                txtDED_Detail_CardAwakeHaveCard9.setText(XED_DIALOG_CARD_AWAKE + filterXED.get(pos).getAwakeCard9() + "\n" + XED_DIALOG_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getNum());

                //없는 카드 안 보이게 - 카드 이미지, 카드 이름, 카드 각성도, 카드 보유 수
                imgVisibility(filterXED.get(pos).getCard2(), imgDED_Detail_Card2, txtDED_Detail_Card2, txtDED_Detail_CardAwakeHaveCard2);
                imgVisibility(filterXED.get(pos).getCard3(), imgDED_Detail_Card3, txtDED_Detail_Card3, txtDED_Detail_CardAwakeHaveCard3);
                imgVisibility(filterXED.get(pos).getCard4(), imgDED_Detail_Card4, txtDED_Detail_Card4, txtDED_Detail_CardAwakeHaveCard4);
                imgVisibility(filterXED.get(pos).getCard5(), imgDED_Detail_Card5, txtDED_Detail_Card5, txtDED_Detail_CardAwakeHaveCard5);
                imgVisibility(filterXED.get(pos).getCard6(), imgDED_Detail_Card6, txtDED_Detail_Card6, txtDED_Detail_CardAwakeHaveCard6);
                imgVisibility(filterXED.get(pos).getCard7(), imgDED_Detail_Card7, txtDED_Detail_Card7, txtDED_Detail_CardAwakeHaveCard7);
                imgVisibility(filterXED.get(pos).getCard8(), imgDED_Detail_Card8, txtDED_Detail_Card8, txtDED_Detail_CardAwakeHaveCard8);
                imgVisibility(filterXED.get(pos).getCard9(), imgDED_Detail_Card9, txtDED_Detail_Card9, txtDED_Detail_CardAwakeHaveCard9);

                Dialog dialogChangeAwakeAndNum = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialogChangeAwakeAndNum.setContentView(R.layout.awake_havecard_change);
                NumberPicker numberPickerAwake = dialogChangeAwakeAndNum.findViewById(R.id.numberPickerAwake);
                numberPickerAwake.setMinValue(0);
                numberPickerAwake.setMaxValue(5);
                numberPickerAwake.setWrapSelectorWheel(false);

                NumberPicker numberPickerHave = dialogChangeAwakeAndNum.findViewById(R.id.numberPickerHave);
                numberPickerHave.setMinValue(0);
                numberPickerHave.setMaxValue(15);
                numberPickerHave.setWrapSelectorWheel(false);
                Button btnCancer = dialogChangeAwakeAndNum.findViewById(R.id.btnCancer);
                Button btnOK = dialogChangeAwakeAndNum.findViewById(R.id.btnOK);


                txtDED_Detail_CardAwakeHaveCard0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard0());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard0.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard0(), awake));

                                if (filterXED.get(pos).isCheckCard0() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }extraDmgPage

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();

                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard1());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard1.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard1(), awake));

                                if (filterXED.get(pos).isCheckCard1() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getId());
                                }


                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();

                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard2());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard2.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                if (filterXED.get(pos).isCheckCard2() & awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getId());
                                }

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard2(), awake));

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();


                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard3());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard3.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard3(), awake));

                                if (filterXED.get(pos).isCheckCard3() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();


                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard4());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard4.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard4(), awake));

                                if (filterXED.get(pos).isCheckCard4() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();


                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard5());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard5.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard5(), awake));

                                if (filterXED.get(pos).isCheckCard5() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();


                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard6());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard6.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard6(), awake));

                                if (filterXED.get(pos).isCheckCard6() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();


                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard7());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard7.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard7(), awake));

                                if (filterXED.get(pos).isCheckCard7() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();

                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard8());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard8.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                if (filterXED.get(pos).isCheckCard8() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getId());
                                }

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard8(), awake));

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();

                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });
                txtDED_Detail_CardAwakeHaveCard9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterXED.get(pos).getAwakeCard9());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getNum());
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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getId());    //카드 각성도 업데이트(cardListDB)

                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).setNum(number);
                                txtDED_Detail_CardAwakeHaveCard9.setText(XED_DIALOG_CARD_AWAKE + awake + "\n" + XED_DIALOG_CARD_NUM + number);
                                txtDED_AwakeValue.setText("현재 각성 합계 : " + filterXED.get(pos).getHaveAwake());

                                //즐겨찾기 리스트 DB 갱신 및 업데이트
//                                updateAwakeFavoriteCardSetInfoAndDB(getCardSet(filterXED.get(pos).getCard9(), awake));

                                if (filterXED.get(pos).isCheckCard9() && awake > 0) {
                                    cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).setGetCard(true);
                                    cardDBHelper.UpdateInfoCardCheck(true, cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getId());
                                }

                                isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                                nextDED(txtDED_NextStep, filterXED.get(pos));

                                /*
                                if (extraDmgPage.checkFastCompleteness()) {
                                    getFastCompletenessSort();
                                }
                                if (extraDmgPage.checkCompleteness()) {
                                    getCompletenessSort();
                                }

                                 */
                                if (extraDmgPage.completeChecked())
                                    completePartRemove();

                                updateXEDPage();
                                notifyDataSetChanged();
                                dialogChangeAwakeAndNum.cancel();
                            }
                        });
                    }
                });


                imgDED_Detail_Card0.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard0());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard0()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard0() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card0, filter, filterXED.get(pos).getCard0());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard0());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard0())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card0, filterXED.get(pos).getCard0());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();

                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard1());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard1()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard1() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card1, filter, filterXED.get(pos).getCard1());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard1());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard1())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card1, filterXED.get(pos).getCard1());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard2());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard2()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard2() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card2, filter, filterXED.get(pos).getCard2());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard2());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard2())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card2, filterXED.get(pos).getCard2());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard3());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard3()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard3() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card3, filter, filterXED.get(pos).getCard3());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard3());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard3())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card3, filterXED.get(pos).getCard3());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card4.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard4());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard4()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard4() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card4, filter, filterXED.get(pos).getCard4());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard4());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard4())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card4, filterXED.get(pos).getCard4());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card5.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard5());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard5()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard5() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card5, filter, filterXED.get(pos).getCard5());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard5());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard5())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card5, filterXED.get(pos).getCard5());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card6.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard6());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard6()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard6() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card6, filter, filterXED.get(pos).getCard6());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard6());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard6())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card6, filterXED.get(pos).getCard6());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card7.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard7());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard7()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard7() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card7, filter, filterXED.get(pos).getCard7());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard7());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard7())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card7, filterXED.get(pos).getCard7());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card8.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard8());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard8()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard8() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card8, filter, filterXED.get(pos).getCard8());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard8());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard8())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card8, filterXED.get(pos).getCard8());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });

                imgDED_Detail_Card9.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterXED.get(pos).getCard9());
                        imgJustCard.setImageResource(getCardImg(filterXED.get(pos).getCard9()));
                        txtJustCardAwake.setText(filterXED.get(pos).getAwakeCard9() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                        return false;
                    }
                });
                imgDED_Detail_Card9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean cardCheck = imgGrayScale(imgDED_Detail_Card9, filter, filterXED.get(pos).getCard9());
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterXED.get(pos).getCard9());     //카드 수집 유무 업데이트(cardList DB)
                        cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterXED.get(pos).getCard9())).getAwake() > 0) {
                            cardAwake(imgDED_Detail_Card9, filterXED.get(pos).getCard9());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        imgDefaultColor(holder.imgXEDCard9, filter, filterXED.get(pos).isCheckCard9(), filterXED.get(pos).getCard9());
                        isCompleteCardBookBackgroundColor(filterXED.get(pos), holder.cvExtraDmgBackground);              //악추피 수집단계에 따라 효과를 줌(색 변경)
                        holder.txtXEDSumValue.setText("악마 계열 피해량 증가 합 : + " + filterXED.get(pos).getDmgSum() + "%");

                        /*
                        if (extraDmgPage.checkFastCompleteness()) {
                            getFastCompletenessSort();
                        }
                        if (extraDmgPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                        */
                        if (extraDmgPage.completeChecked())
                            completePartRemove();


                        updateXEDPage();
                        notifyDataSetChanged();

                    }
                });


                dialogDEDDetail.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return extraDmgInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cvExtraDmgBackground;
        private TextView txtXEDName;
        private TextView txtXEDSumValue;
        private ImageView imgXEDCard0;
        private ImageView imgXEDCard1;
        private ImageView imgXEDCard2;
        private ImageView imgXEDCard3;
        private ImageView imgXEDCard4;
        private ImageView imgXEDCard5;
        private ImageView imgXEDCard6;
        private ImageView imgXEDCard7;
        private ImageView imgXEDCard8;
        private ImageView imgXEDCard9;
        private TextView txtXEDCardName0;
        private TextView txtXEDCardName1;
        private TextView txtXEDCardName2;
        private TextView txtXEDCardName3;
        private TextView txtXEDCardName4;
        private TextView txtXEDCardName5;
        private TextView txtXEDCardName6;
        private TextView txtXEDCardName7;
        private TextView txtXEDCardName8;
        private TextView txtXEDCardName9;
        private TextView txtXED_cardCollection;
        private TextView txtXED_cardAwake0;
        private TextView txtXED_cardAwake1;
        private TextView txtXED_cardAwake2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvExtraDmgBackground = itemView.findViewById(R.id.cvExtraDmgBackground);
            txtXEDName = itemView.findViewById(R.id.txtXEDName);
            txtXEDSumValue = itemView.findViewById(R.id.txtXEDSumValue);
            imgXEDCard0 = itemView.findViewById(R.id.imgXEDCard0);
            imgXEDCard1 = itemView.findViewById(R.id.imgXEDCard1);
            imgXEDCard2 = itemView.findViewById(R.id.imgXEDCard2);
            imgXEDCard3 = itemView.findViewById(R.id.imgXEDCard3);
            imgXEDCard4 = itemView.findViewById(R.id.imgXEDCard4);
            imgXEDCard5 = itemView.findViewById(R.id.imgXEDCard5);
            imgXEDCard6 = itemView.findViewById(R.id.imgXEDCard6);
            imgXEDCard7 = itemView.findViewById(R.id.imgXEDCard7);
            imgXEDCard8 = itemView.findViewById(R.id.imgXEDCard8);
            imgXEDCard9 = itemView.findViewById(R.id.imgXEDCard9);
            txtXEDCardName0 = itemView.findViewById(R.id.txtXEDCardName0);
            txtXEDCardName1 = itemView.findViewById(R.id.txtXEDCardName1);
            txtXEDCardName2 = itemView.findViewById(R.id.txtXEDCardName2);
            txtXEDCardName3 = itemView.findViewById(R.id.txtXEDCardName3);
            txtXEDCardName4 = itemView.findViewById(R.id.txtXEDCardName4);
            txtXEDCardName5 = itemView.findViewById(R.id.txtXEDCardName5);
            txtXEDCardName6 = itemView.findViewById(R.id.txtXEDCardName6);
            txtXEDCardName7 = itemView.findViewById(R.id.txtXEDCardName7);
            txtXEDCardName8 = itemView.findViewById(R.id.txtXEDCardName8);
            txtXEDCardName9 = itemView.findViewById(R.id.txtXEDCardName9);
            txtXED_cardCollection = itemView.findViewById(R.id.txtXED_cardCollection);
            txtXED_cardAwake0 = itemView.findViewById(R.id.txtXED_cardAwake0);
            txtXED_cardAwake1 = itemView.findViewById(R.id.txtXED_cardAwake1);
            txtXED_cardAwake2 = itemView.findViewById(R.id.txtXED_cardAwake2);
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
    private void imgDefaultColor(ImageView iv, ColorMatrixColorFilter filter, boolean check, String name) {
        if (check) {
            setCardBorder(iv, name);
            iv.setColorFilter(null);
        } else {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
        }
    }

    //클릭시 카드의 각성도 정보가 1이상인 경우 카드가 무조건 획득으로(컬러필터 제거 및 획득)
    private void cardAwake(ImageView iv, String name) {
        iv.setColorFilter(null);
        setCardBorder(iv, name);
    }

    //클릭시 카드를 흑백으로 바꿈.(흑백이면 컬러로, 컬러면 흑백으로), 데이터베이스 카드 도감 획득 유무도 변경.(흑백은 0, 컬러는 1)
    private boolean imgGrayScale(ImageView iv, ColorMatrixColorFilter filter, String name) {
        if (iv.getColorFilter() != filter) {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
            return false;
        } else {
            setCardBorder(iv, name);
            iv.setColorFilter(null);
            return true;
        }
    }

    // 카드 등급에 따른 테두리색
    private void setCardBorder(ImageView iv, String name) {
        if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("전설")) {
            iv.setBackgroundColor(Color.parseColor("#FFB300"));
        } else if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("영웅")) {
            iv.setBackgroundColor(Color.parseColor("#5E35B1"));
        } else if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("희귀")) {
            iv.setBackgroundColor(Color.parseColor("#1E88E5"));
        } else if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("고급")) {
            iv.setBackgroundColor(Color.parseColor("#7CB342"));
        } else if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("일반")) {
            iv.setBackgroundColor(Color.parseColor("#A1A1A1"));
        } else if (cardInfo.get(getIndex(cardInfo, name)).getGrade().equals("스페셜")) {
            iv.setBackgroundColor(Color.parseColor("#DF4F84"));
        }
    }

    // 악추피 도감작을 완성시키면 각성도에 따라 도감의 배경을 각 단계별로 흰색->민트색->초록색->노란색으로 바꿈
    private void isCompleteCardBookBackgroundColor(ExtraDmgInfo XEDInfo, ConstraintLayout cv) {
        if ((XEDInfo.getHaveCard() == XEDInfo.getNeedCard())) {
            if (XEDInfo.getHaveAwake() == XEDInfo.getAwakeSum2())
                cv.setBackgroundColor(Color.parseColor("#FFF4BD"));   //노랑 - 전부수집+풀각성
            else if (XEDInfo.getHaveAwake() < XEDInfo.getAwakeSum2() && XEDInfo.getHaveAwake() >= XEDInfo.getAwakeSum1())
                cv.setBackgroundColor(Color.parseColor("#CFFFCC"));//초록 - 전부수집+올4각성 이상
            else if (XEDInfo.getHaveAwake() < XEDInfo.getAwakeSum1() && XEDInfo.getHaveAwake() >= XEDInfo.getAwakeSum0())
                cv.setBackgroundColor(Color.parseColor("#CCFFFB"));//민트 - 전부수집+올2각성
            else
                cv.setBackgroundColor(Color.parseColor("#C5BEFF"));//연보라 - 전부수집
        } else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    //다음 악추피 도달까지 남은 각성도 수
    private void nextDED(TextView txtXED_NextStep, ExtraDmgInfo extraDmgInfo) {
        if (extraDmgInfo.getAwakeSum0() > extraDmgInfo.getHaveAwake()) {
            txtXED_NextStep.setText("다음 활성도까지 : " + (extraDmgInfo.getAwakeSum0() - extraDmgInfo.getHaveAwake()) + " 남음");
        } else if (extraDmgInfo.getAwakeSum0() <= extraDmgInfo.getHaveAwake() && extraDmgInfo.getAwakeSum1() > extraDmgInfo.getHaveAwake()) {
            txtXED_NextStep.setText("다음 활성도까지 : " + (extraDmgInfo.getAwakeSum1() - extraDmgInfo.getHaveAwake()) + " 남음");
        } else if (extraDmgInfo.getAwakeSum1() <= extraDmgInfo.getHaveAwake() && extraDmgInfo.getAwakeSum2() > extraDmgInfo.getHaveAwake()) {
            txtXED_NextStep.setText("다음 활성도까지 : " + (extraDmgInfo.getAwakeSum2() - extraDmgInfo.getHaveAwake()) + " 남음");
        } else {
            txtXED_NextStep.setVisibility(View.GONE);
        }

    }

    // DB에 도감을 완성 시킨 경우(카드 수집만) true else false
    public boolean isCompleteDED(ExtraDmgInfo XEDInfo) {
        if (XEDInfo.getHaveCard() == XEDInfo.getNeedCard())
            return true;
        else
            return false;
    }

    // DB에 도감을 완성 시킨 경우(카드수집 + 각성도)
    private boolean isAllCompleteDED(ExtraDmgInfo XEDInfo) {
        if (XEDInfo.getHaveCard() == XEDInfo.getNeedCard()) {
            if (XEDInfo.getHaveAwake() == XEDInfo.getAwakeSum2())
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    //악추피 값 합산.
    public void haveXEDUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        haveXED = 0;
        completeXED = 0;
        for (int i = 0; i < extraDmgInfo.size(); i++) {
            if (isCompleteDED(extraDmgInfo.get(i))) {
                haveXED += extraDmgInfo.get(i).getDmgSum();
                if (extraDmgInfo.get(i).getAwakeSum2() == extraDmgInfo.get(i).getHaveAwake()) {
                    completeXED++;
                }
            }
        }
        haveXED = Float.parseFloat(df.format(haveXED));
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

    //보유카드 수량 제한
    private int maxHaveValue(int haveAwake) {
        if (haveAwake == 5) {
            return 0;
        } else if (haveAwake == 4) {
            return 5;
        } else if (haveAwake == 3) {
            return 9;
        } else if (haveAwake == 2) {
            return 12;
        } else if (haveAwake == 1) {
            return 14;
        } else if (haveAwake == 0) {
            return 15;
        } else
            return 0;
    }

    //카드 이미지 세팅
    private int getCardImg(String cardName) {
        String name = "";
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardName)) {
                name = cardInfo.get(i).getPath();
                break;
            }
        }
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

        return imageResource;
    }

    // 검색 필터
    public Filter getSearchFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (extraDmgPage.completeChecked()) {
                    if (charString.isEmpty()) {
                        filterXED = baseFilteredXED;
                    } else {
                        ArrayList<ExtraDmgInfo> filteringList = new ArrayList<>();
                        for (int i = 0; i < baseFilteredXED.size(); i++) {
                            if (baseFilteredXED.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(baseFilteredXED.get(i));
                            }
                        }
                        filterXED = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterXED;
                    return filterResults;
                } else {
                    if (charString.isEmpty()) {
                        filterXED = extraDmgInfo;
                    } else {
                        ArrayList<ExtraDmgInfo> filteringList = new ArrayList<>();
                        for (int i = 0; i < extraDmgInfo.size(); i++) {
                            if (extraDmgInfo.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(extraDmgInfo.get(i));
                            }
                        }
                        filterXED = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterXED;
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterXED = (ArrayList<ExtraDmgInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    //완성 안된 DED 도감 세팅
    private void setBaseFilteredXED() {
        ArrayList<ExtraDmgInfo> filteringList = new ArrayList<>();
        for (int i = 0; i < extraDmgInfo.size(); i++) {
            if (!isAllCompleteDED(extraDmgInfo.get(i))) {
                filteringList.add(extraDmgInfo.get(i));
            }
        }
        baseFilteredXED = filteringList;
    }

    // 완성도감 필터
    public void getCompleteFilter() {
        filterXED = extraDmgInfo;
        if (extraDmgPage.completeChecked()) {
            completePartRemove();
        }

        if (extraDmgPage.isCheckDefault()) {
            getDefaultSort();
        }
        if (extraDmgPage.isCheckName()) {
            getNameSort();
        }
        if (extraDmgPage.isCheckCompleteness()) {
            getCompletenessSort();
        }
        if (extraDmgPage.isCheckFastCompleteness()) {
            getFastCompletenessSort();
        }

        notifyDataSetChanged();
    }

    //완성도감 지우기
    private void completePartRemove() {
        ArrayList<ExtraDmgInfo> filteringList = new ArrayList<>();
        for (int i = 0; i < filterXED.size(); i++) {
            if (!isAllCompleteDED(filterXED.get(i))) {
                filteringList.add(filterXED.get(i));
            }
        }
        filterXED = filteringList;
    }

    //기본 정렬
    public void getDefaultSort() {
        Collections.sort(filterXED, new Comparator<ExtraDmgInfo>() {
            @Override
            public int compare(ExtraDmgInfo o1, ExtraDmgInfo o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                else
                    return 1;
            }
        });
        if (extraDmgPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //이름 정렬
    public void getNameSort() {
        Collections.sort(filterXED);
        if (extraDmgPage.completeChecked()) {
            completePartRemove();
        }
        notifyDataSetChanged();
    }

    //완성도 정렬
    public void getCompletenessSort() {
        getNameSort();
        Collections.sort(filterXED, new Comparator<ExtraDmgInfo>() {
            @Override
            public int compare(ExtraDmgInfo o1, ExtraDmgInfo o2) {
                if (o1.completePercent() < o2.completePercent())
                    return 1;
                else
                    return -1;
            }
        });

        if (extraDmgPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //다음 활성도가 가까운 순 정렬
    public void getFastCompletenessSort() {
        getNameSort();
        Collections.sort(filterXED, new Comparator<ExtraDmgInfo>() {
            @Override
            public int compare(ExtraDmgInfo o1, ExtraDmgInfo o2) {
                if (o1.fastComplete() <= o2.fastComplete()) {
                    return -1;
                } else
                    return 1;
            }
        });

        if (extraDmgPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }


    // 메인페이지와 DEDPage의 수치 update
    private void updateXEDPage() {
        haveXEDUpdate();                                                                                     //악추피 값,완성도 갱신
        extraDmgPage.setXED(haveXED, EDName);                                                                           //악추피 페이지 값 갱신한 것 세팅
        extraDmgPage.setXEDBook(completeXED, getItemCount());                                                    //악추피 도감 완성도 갱신한 것 세팅
        notifyDataSetChanged();
    }

    public float putHaveXEDValue(){
        return haveXED;
    }

}
