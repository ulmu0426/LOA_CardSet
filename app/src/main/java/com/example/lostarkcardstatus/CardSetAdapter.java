package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
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

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.ViewHolder> {
    private ArrayList<CardSetInfo> cardSetInfo;
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo;
    private Context context;
    private LOA_CardDB cardDbHelper;
    private MainAdapter mainAdapter;
    private final String CARDSET_AWAKE = "각성 : ";
    private final String CARDSET_CARD_NUM = "보유 : ";
    private final String CARDSET_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String CARDSET_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String CARDSET_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String CARDSET_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String CARDSET_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String CARDSET_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String CARDSET_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String CARD_SET_AWAKE_SUM = "각성 합계 : ";


    public CardSetAdapter(Context context) {
        this.cardSetInfo = ((MainActivity) MainActivity.mainContext).cardSetInfo;
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        this.context = context;
        this.mainAdapter = ((MainActivity) MainActivity.mainContext).mainAdapter;
        this.favoriteCardSetInfo = ((MainActivity) MainActivity.mainContext).favoriteCardSetInfo;
        cardDbHelper = new LOA_CardDB(context);
        ((MainActivity) MainActivity.mainContext).haveCardSetCheckUpdate();
    }

    @NonNull
    @Override
    public CardSetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardset, parent, false);

        return new CardSetAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSetAdapter.ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        holder.txtCardSetName.setText(cardSetInfo.get(position).getName());
        holder.txtCardSetAwake.setText("각성 합계 : " + cardSetInfo.get(position).getHaveAwake());
        //이미지뷰 구현할것
        holder.imgCardSet0.setImageResource(R.drawable.card_legend_kadan);
        holder.imgCardSet1.setImageResource(R.drawable.card_legend_ninab);
        holder.imgCardSet2.setImageResource(R.drawable.card_legend_shandi);
        holder.imgCardSet3.setImageResource(R.drawable.card_legend_azena_inanna);
        holder.imgCardSet4.setImageResource(R.drawable.card_legend_bahuntur);
        holder.imgCardSet5.setImageResource(R.drawable.card_legend_silian);
        holder.imgCardSet6.setImageResource(R.drawable.card_legend_wei);

        imgDefaultColor(holder.imgCardSet0, filter, cardSetInfo.get(position).getCheckCard0());
        imgDefaultColor(holder.imgCardSet1, filter, cardSetInfo.get(position).getCheckCard1());
        imgDefaultColor(holder.imgCardSet2, filter, cardSetInfo.get(position).getCheckCard2());
        imgDefaultColor(holder.imgCardSet3, filter, cardSetInfo.get(position).getCheckCard3());
        imgDefaultColor(holder.imgCardSet4, filter, cardSetInfo.get(position).getCheckCard4());
        imgDefaultColor(holder.imgCardSet5, filter, cardSetInfo.get(position).getCheckCard5());
        imgDefaultColor(holder.imgCardSet6, filter, cardSetInfo.get(position).getCheckCard6());

        //없는 카드 안 보이게
        imgVisibility(cardSetInfo.get(position).getCard2(), holder.imgCardSet2);
        imgVisibility(cardSetInfo.get(position).getCard3(), holder.imgCardSet3);
        imgVisibility(cardSetInfo.get(position).getCard4(), holder.imgCardSet4);
        imgVisibility(cardSetInfo.get(position).getCard5(), holder.imgCardSet5);
        imgVisibility(cardSetInfo.get(position).getCard6(), holder.imgCardSet6);

        //텍스트 구현
        holder.txtCardSet_Cardname0.setText(cardSetInfo.get(position).getCard0());
        holder.txtCardSet_Cardname1.setText(cardSetInfo.get(position).getCard1());
        holder.txtCardSet_Cardname2.setText(cardSetInfo.get(position).getCard2());
        holder.txtCardSet_Cardname3.setText(cardSetInfo.get(position).getCard3());
        holder.txtCardSet_Cardname4.setText(cardSetInfo.get(position).getCard4());
        holder.txtCardSet_Cardname5.setText(cardSetInfo.get(position).getCard5());
        holder.txtCardSet_Cardname6.setText(cardSetInfo.get(position).getCard6());

        holder.txtCardSetOption0.setText(cardSetInfo.get(position).getSet_bonus0());
        holder.txtCardSetOption1.setText(cardSetInfo.get(position).getSet_bonus1());
        holder.txtCardSetOption2.setText(cardSetInfo.get(position).getSet_bonus2());
        holder.txtCardSetOption3.setText(cardSetInfo.get(position).getSet_bonus3());
        holder.txtCardSetOption4.setText(cardSetInfo.get(position).getSet_bonus4());
        holder.txtCardSetOption5.setText(cardSetInfo.get(position).getSet_bonus5());
        //옵션 빈칸 지우기
        optionVisibility(cardSetInfo.get(position).getSet_bonus2(), holder.txtCardSetOption2);
        optionVisibility(cardSetInfo.get(position).getSet_bonus3(), holder.txtCardSetOption3);
        optionVisibility(cardSetInfo.get(position).getSet_bonus4(), holder.txtCardSetOption4);
        optionVisibility(cardSetInfo.get(position).getSet_bonus5(), holder.txtCardSetOption5);

        holder.cvCardSetBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = positionGet;
                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.card_set_detail);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                ImageView imgFavorites = dialog.findViewById(R.id.imgFavorites);
                TextView txtCardSetName_Detail = dialog.findViewById(R.id.txtCardSetName_Detail);
                TextView txtCardSetAwake_Detail = dialog.findViewById(R.id.txtCardSetAwake_Detail);

                ImageView imgCardSetDetail0 = dialog.findViewById(R.id.imgCardSetDetail0);
                ImageView imgCardSetDetail1 = dialog.findViewById(R.id.imgCardSetDetail1);
                ImageView imgCardSetDetail2 = dialog.findViewById(R.id.imgCardSetDetail2);
                ImageView imgCardSetDetail3 = dialog.findViewById(R.id.imgCardSetDetail3);
                ImageView imgCardSetDetail4 = dialog.findViewById(R.id.imgCardSetDetail4);
                ImageView imgCardSetDetail5 = dialog.findViewById(R.id.imgCardSetDetail5);
                ImageView imgCardSetDetail6 = dialog.findViewById(R.id.imgCardSetDetail6);

                imgFavorites.setImageResource(R.drawable.gold_star);
                setFavoriteImg(imgFavorites, pos, filter);

                imgFavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = imgGrayScale(imgFavorites, filter);

                        if (check == 0) {//즐찾이 아니게 되면
                            for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
                                if (favoriteCardSetInfo.get(i).getName().equals(cardSetInfo.get(pos).getName())) {
                                    cardSetInfo.get(pos).setFavorite("");
                                    favoriteCardSetInfo.get(i).setActivation(check);
                                    cardDbHelper.UpdateInfoFavoriteList(cardSetInfo.get(pos).getHaveAwake(), check, favoriteCardSetInfo.get(i).getName());
                                    cardDbHelper.UpdateInfoCardSetCard("", cardSetInfo.get(pos).getId());
                                    mainAdapter.removeItem(favoriteCardSetInfo.get(i));
                                    Toast.makeText(context, "카드 수량 증가? : " + mainAdapter.getItemCount(), Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {         //즐찾이 되면
                            for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
                                if (favoriteCardSetInfo.get(i).getName().equals(cardSetInfo.get(pos).getName())) {
                                    cardSetInfo.get(pos).setFavorite(favoriteCardSetInfo.get(i).getName());
                                    favoriteCardSetInfo.get(i).setActivation(check);
                                    cardDbHelper.UpdateInfoFavoriteList(cardSetInfo.get(pos).getHaveAwake(), check, favoriteCardSetInfo.get(i).getName());
                                    cardDbHelper.UpdateInfoCardSetCard(favoriteCardSetInfo.get(i).getName(), cardSetInfo.get(pos).getId());
                                    mainAdapter.addItem(favoriteCardSetInfo.get(i));
                                    Toast.makeText(context, "카드 수량 증가? : " + mainAdapter.getItemCount(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        notifyDataSetChanged();
                    }
                });

                //이미지 기본 색상 : 획득카드가 아니면 흑백
                imgDefaultColor(imgCardSetDetail0, filter, cardSetInfo.get(pos).getCheckCard0());
                imgDefaultColor(imgCardSetDetail1, filter, cardSetInfo.get(pos).getCheckCard1());
                imgDefaultColor(imgCardSetDetail2, filter, cardSetInfo.get(pos).getCheckCard2());
                imgDefaultColor(imgCardSetDetail3, filter, cardSetInfo.get(pos).getCheckCard3());
                imgDefaultColor(imgCardSetDetail4, filter, cardSetInfo.get(pos).getCheckCard4());
                imgDefaultColor(imgCardSetDetail5, filter, cardSetInfo.get(pos).getCheckCard5());
                imgDefaultColor(imgCardSetDetail6, filter, cardSetInfo.get(pos).getCheckCard6());

                imgCardSetDetail0.setImageResource(R.drawable.card_legend_kadan);
                imgCardSetDetail1.setImageResource(R.drawable.card_legend_ninab);
                imgCardSetDetail2.setImageResource(R.drawable.card_legend_shandi);
                imgCardSetDetail3.setImageResource(R.drawable.card_legend_azena_inanna);
                imgCardSetDetail4.setImageResource(R.drawable.card_legend_bahuntur);
                imgCardSetDetail5.setImageResource(R.drawable.card_legend_silian);
                imgCardSetDetail6.setImageResource(R.drawable.card_legend_wei);

                TextView txtCardSetName0 = dialog.findViewById(R.id.txtCardSetName0);
                TextView txtCardSetName1 = dialog.findViewById(R.id.txtCardSetName1);
                TextView txtCardSetName2 = dialog.findViewById(R.id.txtCardSetName2);
                TextView txtCardSetName3 = dialog.findViewById(R.id.txtCardSetName3);
                TextView txtCardSetName4 = dialog.findViewById(R.id.txtCardSetName4);
                TextView txtCardSetName5 = dialog.findViewById(R.id.txtCardSetName5);
                TextView txtCardSetName6 = dialog.findViewById(R.id.txtCardSetName6);

                TextView txtHaveAwakeHaveCard0 = dialog.findViewById(R.id.txtHaveAwakeHaveCard0);
                TextView txtHaveAwakeHaveCard1 = dialog.findViewById(R.id.txtHaveAwakeHaveCard1);
                TextView txtHaveAwakeHaveCard2 = dialog.findViewById(R.id.txtHaveAwakeHaveCard2);
                TextView txtHaveAwakeHaveCard3 = dialog.findViewById(R.id.txtHaveAwakeHaveCard3);
                TextView txtHaveAwakeHaveCard4 = dialog.findViewById(R.id.txtHaveAwakeHaveCard4);
                TextView txtHaveAwakeHaveCard5 = dialog.findViewById(R.id.txtHaveAwakeHaveCard5);
                TextView txtHaveAwakeHaveCard6 = dialog.findViewById(R.id.txtHaveAwakeHaveCard6);

                imgVisibility(cardSetInfo.get(pos).getCard2(), imgCardSetDetail2, txtCardSetName2, txtHaveAwakeHaveCard2);
                imgVisibility(cardSetInfo.get(pos).getCard3(), imgCardSetDetail3, txtCardSetName3, txtHaveAwakeHaveCard3);
                imgVisibility(cardSetInfo.get(pos).getCard4(), imgCardSetDetail4, txtCardSetName4, txtHaveAwakeHaveCard4);
                imgVisibility(cardSetInfo.get(pos).getCard5(), imgCardSetDetail5, txtCardSetName5, txtHaveAwakeHaveCard5);
                imgVisibility(cardSetInfo.get(pos).getCard6(), imgCardSetDetail6, txtCardSetName6, txtHaveAwakeHaveCard6);

                txtCardSetName_Detail.setText(cardSetInfo.get(pos).getName());
                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake() + "각성");

                txtCardSetName0.setText(cardSetInfo.get(pos).getCard0());
                txtCardSetName1.setText(cardSetInfo.get(pos).getCard1());
                txtCardSetName2.setText(cardSetInfo.get(pos).getCard2());
                txtCardSetName3.setText(cardSetInfo.get(pos).getCard3());
                txtCardSetName4.setText(cardSetInfo.get(pos).getCard4());
                txtCardSetName5.setText(cardSetInfo.get(pos).getCard5());
                txtCardSetName6.setText(cardSetInfo.get(pos).getCard6());

                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard0() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).getCount());
                txtHaveAwakeHaveCard1.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard1() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).getCount());
                txtHaveAwakeHaveCard2.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard2() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).getCount());
                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard3() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).getCount());
                txtHaveAwakeHaveCard4.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard4() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).getCount());
                txtHaveAwakeHaveCard5.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard5() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getCount());
                txtHaveAwakeHaveCard6.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard6() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).getCount());

                Dialog dialogAwakeNHaveCard = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialogAwakeNHaveCard.setContentView(R.layout.awake_havecard_change);
                EditText etxtAwake = dialogAwakeNHaveCard.findViewById(R.id.eTxtAwake);
                EditText etxtNum = dialogAwakeNHaveCard.findViewById(R.id.etxtNum);
                Button btnCancer = dialogAwakeNHaveCard.findViewById(R.id.btnCancer);
                Button btnOK = dialogAwakeNHaveCard.findViewById(R.id.btnOK);


                txtHaveAwakeHaveCard0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard0() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD0_CHECK, awake, cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard0());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard0(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).setCount(number);
                                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard0() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard0(), awake, cardSetInfo.get(pos).getCheckCard0());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard1() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD1_CHECK, Integer.parseInt(String.valueOf(etxtAwake.getText())), cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard1());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard1(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).setCount(number);
                                txtHaveAwakeHaveCard1.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard1() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard1(), awake, cardSetInfo.get(pos).getCheckCard1());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard2() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD2_CHECK, Integer.parseInt(String.valueOf(etxtAwake.getText())), cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard2());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard2(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).setCount(number);
                                txtHaveAwakeHaveCard2.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard2() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard2(), awake, cardSetInfo.get(pos).getCheckCard2());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard3() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD3_CHECK, awake, cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard3());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard3(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).setCount(number);
                                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard3() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard3(), awake, cardSetInfo.get(pos).getCheckCard3());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard4() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD4_CHECK, awake, cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard4());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard4(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).setCount(number);
                                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard4() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard4(), awake, cardSetInfo.get(pos).getCheckCard4());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard0() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD5_CHECK, awake, cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard5());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard5(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).setCount(number);
                                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard5() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard5(), awake, cardSetInfo.get(pos).getCheckCard5());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });
                txtHaveAwakeHaveCard6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.setText(cardSetInfo.get(pos).getAwakeCard6() + "");
                        etxtNum.setText(cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).getCount() + "");

                        dialogAwakeNHaveCard.show();
                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAwakeNHaveCard.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = awakeRangeSet(etxtAwake.getText().toString());
                                int number = numRangeSet(etxtNum.getText().toString());
                                etxtAwake.setText(awake + "");
                                etxtNum.setText(number + "");
                                cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD6_CHECK, awake, cardSetInfo.get(pos).getId());   //DED cardAwake 업데이트(DED DB)
                                cardDbHelper.UpdateInfoCardCheck("number", number, cardSetInfo.get(pos).getCard6());     //카드 수집 업데이트(cardList DB)
                                cardDbHelper.UpdateInfoCardAwake("awake", awake, cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(pos).setAwakeCard6(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).setCount(number);
                                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard6() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).getCount());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + cardSetInfo.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(cardSetInfo.get(pos).getCard6(), awake, cardSetInfo.get(pos).getCheckCard6());
                                mainAdapter.setAwake(cardSetInfo.get(pos).getName(), awake);

                                Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail0, filter, cardSetInfo.get(pos).getCheckCard0());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD0_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard0());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard0(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();
                    }
                });
                imgCardSetDetail1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail1, filter, cardSetInfo.get(pos).getCheckCard1());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD1_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard1());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard1(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail2, filter, cardSetInfo.get(pos).getCheckCard2());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD2_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard2());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard2(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail3, filter, cardSetInfo.get(pos).getCheckCard3());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD3_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard3());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard3(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail4, filter, cardSetInfo.get(pos).getCheckCard4());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD4_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard4());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard4(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail5, filter, cardSetInfo.get(pos).getCheckCard5());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD5_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard5());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard5(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail6, filter, cardSetInfo.get(pos).getCheckCard6());              //카드 획득 유무 0 미획득, 1획득
                        cardDbHelper.UpdateInfoDEDCard(CARDSET_COLUMN_NAME_CARD6_CHECK, cardCheck, cardSetInfo.get(pos).getId());   //cardX수집 유무 업데이트(CardSet DB)
                        cardDbHelper.UpdateInfoCardCheck("getCard", cardCheck, cardSetInfo.get(pos).getCard6());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(pos).setCheckCard6(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)
                        updateCardSetPage();

                        notifyDataSetChanged();

                    }
                });


                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return cardSetInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cvCardSetBackground;
        private TextView txtCardSetName;
        private TextView txtCardSetAwake;
        private ImageView imgCardSet0;
        private ImageView imgCardSet1;
        private ImageView imgCardSet2;
        private ImageView imgCardSet3;
        private ImageView imgCardSet4;
        private ImageView imgCardSet5;
        private ImageView imgCardSet6;
        private TextView txtCardSet_Cardname0;
        private TextView txtCardSet_Cardname1;
        private TextView txtCardSet_Cardname2;
        private TextView txtCardSet_Cardname3;
        private TextView txtCardSet_Cardname4;
        private TextView txtCardSet_Cardname5;
        private TextView txtCardSet_Cardname6;
        private TextView txtCardSetOption0;
        private TextView txtCardSetOption1;
        private TextView txtCardSetOption2;
        private TextView txtCardSetOption3;
        private TextView txtCardSetOption4;
        private TextView txtCardSetOption5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCardSetBackground = itemView.findViewById(R.id.cvCardSetBackground);
            txtCardSetName = itemView.findViewById(R.id.txtCardSetName);
            txtCardSetAwake = itemView.findViewById(R.id.txtCardSetAwake);
            imgCardSet0 = itemView.findViewById(R.id.imgCardSet0);
            imgCardSet1 = itemView.findViewById(R.id.imgCardSet1);
            imgCardSet2 = itemView.findViewById(R.id.imgCardSet2);
            imgCardSet3 = itemView.findViewById(R.id.imgCardSet3);
            imgCardSet4 = itemView.findViewById(R.id.imgCardSet4);
            imgCardSet5 = itemView.findViewById(R.id.imgCardSet5);
            imgCardSet6 = itemView.findViewById(R.id.imgCardSet6);
            txtCardSet_Cardname0 = itemView.findViewById(R.id.txtCardSet_Cardname0);
            txtCardSet_Cardname1 = itemView.findViewById(R.id.txtCardSet_Cardname1);
            txtCardSet_Cardname2 = itemView.findViewById(R.id.txtCardSet_Cardname2);
            txtCardSet_Cardname3 = itemView.findViewById(R.id.txtCardSet_Cardname3);
            txtCardSet_Cardname4 = itemView.findViewById(R.id.txtCardSet_Cardname4);
            txtCardSet_Cardname5 = itemView.findViewById(R.id.txtCardSet_Cardname5);
            txtCardSet_Cardname6 = itemView.findViewById(R.id.txtCardSet_Cardname6);
            txtCardSetOption0 = itemView.findViewById(R.id.txtCardSetOption0);
            txtCardSetOption1 = itemView.findViewById(R.id.txtCardSetOption1);
            txtCardSetOption2 = itemView.findViewById(R.id.txtCardSetOption2);
            txtCardSetOption3 = itemView.findViewById(R.id.txtCardSetOption3);
            txtCardSetOption4 = itemView.findViewById(R.id.txtCardSetOption4);
            txtCardSetOption5 = itemView.findViewById(R.id.txtCardSetOption5);

        }
    }

    //도감에 없는 카드는 안보이게
    private void imgVisibility(String card, ImageView imageView) {
        if (card.isEmpty())
            imageView.setVisibility(View.INVISIBLE);
        else
            imageView.setVisibility(View.VISIBLE);
    }

    //도감에 없는 옵션은 안보이게 지움
    private void optionVisibility(String option, TextView tv) {
        if (option.isEmpty())
            tv.setVisibility(View.GONE);
        else
            tv.setVisibility(View.VISIBLE);
    }

    //카드 이미지, 이름, 각성도 및 보유 카드 안보이게
    private void imgVisibility(String card, ImageView imgCard, TextView txtCardName, TextView txtAwakeHave) {
        if (card.isEmpty()) {
            imgCard.setVisibility(View.INVISIBLE);
            txtCardName.setVisibility(View.INVISIBLE);
            txtAwakeHave.setVisibility(View.INVISIBLE);
        } else {
            imgCard.setVisibility(View.VISIBLE);
            txtCardName.setVisibility(View.VISIBLE);
            txtAwakeHave.setVisibility(View.VISIBLE);
        }
    }

    //획득 못한 카드는 흑백이 기본으로 보이도록 최초 설정
    private void imgDefaultColor(ImageView iv, ColorMatrixColorFilter filter, int check) {
        if (check == 1)
            iv.setColorFilter(null);
        else
            iv.setColorFilter(filter);
    }

    //클릭시 카드를 흑백으로 바꾸는 함수, 데이터베이스 카드 도감 획득 유무도 변경.
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

    //클릭시 카드를 흑백으로 바꾸는 함수, 데이터베이스 카드 도감 획득 유무도 변경.
    private int imgGrayScale(ImageView iv, ColorMatrixColorFilter filter) {
        int check = 0;
        if (iv.getColorFilter() != filter) {
            iv.setColorFilter(filter);
            check = 0;
        } else {
            iv.setColorFilter(null);
            check = 1;
        }
        return check;
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

    private void setFavoriteImg(ImageView iv, int position, ColorMatrixColorFilter filter) {
        iv.setImageResource(R.drawable.gold_star);
        if (!cardSetInfo.get(position).getFavorite().isEmpty()) {
            iv.setColorFilter(null);
        } else
            iv.setColorFilter(filter);
    }

    private void updateAwakeFavoriteCardSetInfoAndDB(String changeAwakeCardName, int changeAwake, int check) {
        for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
            if (favoriteCardSetInfo.get(i).getName().equals(changeAwakeCardName)) {
                favoriteCardSetInfo.get(i).setAwake(changeAwake);
                cardDbHelper.UpdateInfoFavoriteList(changeAwake, check, favoriteCardSetInfo.get(i).getName());
                break;
            }
        }
    }

    private void updateCardSetPage() {

    }

}
