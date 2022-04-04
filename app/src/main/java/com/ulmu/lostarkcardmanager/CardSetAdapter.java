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

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.ViewHolder> {
    private final String[] STAT = {"치명", "특화", "신속"};
    float haveDED = 0;
    private ArrayList<CardSetInfo> cardSetInfo;     //Default 카드 세트 ArrayList
    private ArrayList<CardSetInfo> filterCardSet;   //리사이클러뷰에 뿌려줄 실제 리스트
    private ArrayList<CardInfo> cardInfo;           //기본 카드 목록 ArrayList
    private ArrayList<CardBookInfo> cardBookInfo;   //기본 카드 도감 목록 ArrayList -> 카드세트에서 수정한 값이 메인페이지의 카드 도감 달성 현황을 변동시킬 수 있도록.
    private ArrayList<DemonExtraDmgInfo> DEDInfo;   //기본 악추피 도감 목록 ArrayList -> 카드세트에서 수정한 값이 메인페이지의 악추피 추가 피해 현황을 변동시킬 수 있도록.

    private FavoriteAdapter favoriteAdapter;        //즐겨찾기 어뎁터(카드세트 페이지에서 즐겨찾기된 카드세트의 세부 값 변경시 메인 페이지와 연결해 바로 변경 될 수 있도록 하기 위해 넣음)
    private ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo; //즐겨찾기된 카드세트 목록 ArrayList
    private Context context;
    private CardDBHelper cardDBHelper;              //DB값 변경을 위해 필요한 DBHelper
    private CardSetPage cardSetPage;                //CardSetPage 에서 리사이클러뷰의 정렬, 완성도감 체크 유무등의 정보를 얻기 위해

    private final String CARDSET_AWAKE = "각성 : ";
    private final String CARDSET_CARD_NUM = "보유 : ";
    private final String CARDSET_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String CARDSET_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String CARDSET_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String CARDSET_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String CARDSET_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String CARDSET_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String CARDSET_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String CARDSET_COLUMN_NAME_CARD0_AWAKE = "awakeCard0";
    private final String CARDSET_COLUMN_NAME_CARD1_AWAKE = "awakeCard1";
    private final String CARDSET_COLUMN_NAME_CARD2_AWAKE = "awakeCard2";
    private final String CARDSET_COLUMN_NAME_CARD3_AWAKE = "awakeCard3";
    private final String CARDSET_COLUMN_NAME_CARD4_AWAKE = "awakeCard4";
    private final String CARDSET_COLUMN_NAME_CARD5_AWAKE = "awakeCard5";
    private final String CARDSET_COLUMN_NAME_CARD6_AWAKE = "awakeCard6";
    private final String CARD_SET_AWAKE_SUM = "각성 합계 : ";

    private ArrayList<CardSetInfo> baseFilteredCardSet; //검색기능에 쓰기 위해 완성된 도감을 제외한 기본 카드 세트 목록 ArrayList

    public CardSetAdapter(Context context, CardSetPage cardSetPage) {
        this.cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;
        this.filterCardSet = ((MainPage) MainPage.mainContext).cardSetInfo;
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;
        this.DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;
        this.context = context;
        this.cardSetPage = cardSetPage;
        this.favoriteAdapter = ((MainPage) MainPage.mainContext).favoriteAdapter;
        this.favoriteCardSetInfo = ((MainPage) MainPage.mainContext).favoriteCardSetInfo;
        cardDBHelper = new CardDBHelper(context);
        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
        baseFilteredCardSet = new ArrayList<CardSetInfo>();
        setFilteredCardSet();
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

        holder.txtCardSetName.setText(filterCardSet.get(position).getName());
        setFavoriteImg(holder.imgFavoritesCardSet, position, filter);
        holder.txtCardSetAwake.setText("각성 합계 : " + filterCardSet.get(position).getHaveAwake());

        //이미지뷰 구현할것
        holder.imgCardSet0.setImageResource(getCardImg(filterCardSet.get(position).getCard0()));
        holder.imgCardSet1.setImageResource(getCardImg(filterCardSet.get(position).getCard1()));
        holder.imgCardSet2.setImageResource(getCardImg(filterCardSet.get(position).getCard2()));
        holder.imgCardSet3.setImageResource(getCardImg(filterCardSet.get(position).getCard3()));
        holder.imgCardSet4.setImageResource(getCardImg(filterCardSet.get(position).getCard4()));
        holder.imgCardSet5.setImageResource(getCardImg(filterCardSet.get(position).getCard5()));
        holder.imgCardSet6.setImageResource(getCardImg(filterCardSet.get(position).getCard6()));

        imgDefaultColor(holder.imgCardSet0, filter, filterCardSet.get(position).getCheckCard0(), position, filterCardSet.get(position).getCard0());
        imgDefaultColor(holder.imgCardSet1, filter, filterCardSet.get(position).getCheckCard1(), position, filterCardSet.get(position).getCard1());
        imgDefaultColor(holder.imgCardSet2, filter, filterCardSet.get(position).getCheckCard2(), position, filterCardSet.get(position).getCard2());
        imgDefaultColor(holder.imgCardSet3, filter, filterCardSet.get(position).getCheckCard3(), position, filterCardSet.get(position).getCard3());
        imgDefaultColor(holder.imgCardSet4, filter, filterCardSet.get(position).getCheckCard4(), position, filterCardSet.get(position).getCard4());
        imgDefaultColor(holder.imgCardSet5, filter, filterCardSet.get(position).getCheckCard5(), position, filterCardSet.get(position).getCard5());
        imgDefaultColor(holder.imgCardSet6, filter, filterCardSet.get(position).getCheckCard6(), position, filterCardSet.get(position).getCard6());

        //없는 카드 안 보이게
        imgVisibility(filterCardSet.get(position).getCard2(), holder.imgCardSet2);
        imgVisibility(filterCardSet.get(position).getCard3(), holder.imgCardSet3);
        imgVisibility(filterCardSet.get(position).getCard4(), holder.imgCardSet4);
        imgVisibility(filterCardSet.get(position).getCard5(), holder.imgCardSet5);
        imgVisibility(filterCardSet.get(position).getCard6(), holder.imgCardSet6);

        //텍스트 구현
        holder.txtCardSet_Cardname0.setText(filterCardSet.get(position).getCard0());
        holder.txtCardSet_Cardname1.setText(filterCardSet.get(position).getCard1());
        holder.txtCardSet_Cardname2.setText(filterCardSet.get(position).getCard2());
        holder.txtCardSet_Cardname3.setText(filterCardSet.get(position).getCard3());
        holder.txtCardSet_Cardname4.setText(filterCardSet.get(position).getCard4());
        holder.txtCardSet_Cardname5.setText(filterCardSet.get(position).getCard5());
        holder.txtCardSet_Cardname6.setText(filterCardSet.get(position).getCard6());

        holder.txtCardSetOption0.setText(filterCardSet.get(position).getSet_bonus0());
        holder.txtCardSetOption1.setText(filterCardSet.get(position).getSet_bonus1());
        holder.txtCardSetOption2.setText(filterCardSet.get(position).getSet_bonus2());
        holder.txtCardSetOption3.setText(filterCardSet.get(position).getSet_bonus3());
        holder.txtCardSetOption4.setText(filterCardSet.get(position).getSet_bonus4());
        holder.txtCardSetOption5.setText(filterCardSet.get(position).getSet_bonus5());
        //옵션 빈칸 지우기
        optionVisibility(filterCardSet.get(position).getSet_bonus2(), holder.txtCardSetOption2);
        optionVisibility(filterCardSet.get(position).getSet_bonus3(), holder.txtCardSetOption3);
        optionVisibility(filterCardSet.get(position).getSet_bonus4(), holder.txtCardSetOption4);
        optionVisibility(filterCardSet.get(position).getSet_bonus5(), holder.txtCardSetOption5);

        isCompleteCardBookBackgroundColor(filterCardSet.get(position), holder.cvCardSetBackground);
        isCompleteCardBookBackgroundColor(filterCardSet.get(position), holder.imgFavoritesCardSet);

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
                TextView txtCardSetNextStep_Detail = dialog.findViewById(R.id.txtCardSetNextStep_Detail);

                ImageView imgCardSetDetail0 = dialog.findViewById(R.id.imgCardSetDetail0);
                ImageView imgCardSetDetail1 = dialog.findViewById(R.id.imgCardSetDetail1);
                ImageView imgCardSetDetail2 = dialog.findViewById(R.id.imgCardSetDetail2);
                ImageView imgCardSetDetail3 = dialog.findViewById(R.id.imgCardSetDetail3);
                ImageView imgCardSetDetail4 = dialog.findViewById(R.id.imgCardSetDetail4);
                ImageView imgCardSetDetail5 = dialog.findViewById(R.id.imgCardSetDetail5);
                ImageView imgCardSetDetail6 = dialog.findViewById(R.id.imgCardSetDetail6);

                imgFavorites.setImageResource(R.drawable.gold_star);
                setFavoriteImg(imgFavorites, pos, filter);


                //이미지 기본 색상 : 획득카드가 아니면 흑백
                imgDefaultColor(imgCardSetDetail0, filter, filterCardSet.get(pos).getCheckCard0(), pos, filterCardSet.get(pos).getCard0());
                imgDefaultColor(imgCardSetDetail1, filter, filterCardSet.get(pos).getCheckCard1(), pos, filterCardSet.get(pos).getCard1());
                imgDefaultColor(imgCardSetDetail2, filter, filterCardSet.get(pos).getCheckCard2(), pos, filterCardSet.get(pos).getCard2());
                imgDefaultColor(imgCardSetDetail3, filter, filterCardSet.get(pos).getCheckCard3(), pos, filterCardSet.get(pos).getCard3());
                imgDefaultColor(imgCardSetDetail4, filter, filterCardSet.get(pos).getCheckCard4(), pos, filterCardSet.get(pos).getCard4());
                imgDefaultColor(imgCardSetDetail5, filter, filterCardSet.get(pos).getCheckCard5(), pos, filterCardSet.get(pos).getCard5());
                imgDefaultColor(imgCardSetDetail6, filter, filterCardSet.get(pos).getCheckCard6(), pos, filterCardSet.get(pos).getCard6());

                imgCardSetDetail0.setImageResource(getCardImg(filterCardSet.get(pos).getCard0()));
                imgCardSetDetail1.setImageResource(getCardImg(filterCardSet.get(pos).getCard1()));
                imgCardSetDetail2.setImageResource(getCardImg(filterCardSet.get(pos).getCard2()));
                imgCardSetDetail3.setImageResource(getCardImg(filterCardSet.get(pos).getCard3()));
                imgCardSetDetail4.setImageResource(getCardImg(filterCardSet.get(pos).getCard4()));
                imgCardSetDetail5.setImageResource(getCardImg(filterCardSet.get(pos).getCard5()));
                imgCardSetDetail6.setImageResource(getCardImg(filterCardSet.get(pos).getCard6()));

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

                imgVisibility(filterCardSet.get(pos).getCard2(), imgCardSetDetail2, txtCardSetName2, txtHaveAwakeHaveCard2);
                imgVisibility(filterCardSet.get(pos).getCard3(), imgCardSetDetail3, txtCardSetName3, txtHaveAwakeHaveCard3);
                imgVisibility(filterCardSet.get(pos).getCard4(), imgCardSetDetail4, txtCardSetName4, txtHaveAwakeHaveCard4);
                imgVisibility(filterCardSet.get(pos).getCard5(), imgCardSetDetail5, txtCardSetName5, txtHaveAwakeHaveCard5);
                imgVisibility(filterCardSet.get(pos).getCard6(), imgCardSetDetail6, txtCardSetName6, txtHaveAwakeHaveCard6);

                txtCardSetName_Detail.setText(filterCardSet.get(pos).getName());
                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());
                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                txtCardSetName0.setText(filterCardSet.get(pos).getCard0());
                txtCardSetName1.setText(filterCardSet.get(pos).getCard1());
                txtCardSetName2.setText(filterCardSet.get(pos).getCard2());
                txtCardSetName3.setText(filterCardSet.get(pos).getCard3());
                txtCardSetName4.setText(filterCardSet.get(pos).getCard4());
                txtCardSetName5.setText(filterCardSet.get(pos).getCard5());
                txtCardSetName6.setText(filterCardSet.get(pos).getCard6());

                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard0() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getNum());
                txtHaveAwakeHaveCard1.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard1() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getNum());
                txtHaveAwakeHaveCard2.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard2() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getNum());
                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard3() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getNum());
                txtHaveAwakeHaveCard4.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard4() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getNum());
                txtHaveAwakeHaveCard5.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard5() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getNum());
                txtHaveAwakeHaveCard6.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard6() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getNum());

                imgFavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = imgGrayScale(imgFavorites, filter);
                        if (check == 0) {//즐찾이 아니게 되면
                            for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
                                if (favoriteCardSetInfo.get(i).getName().equals(filterCardSet.get(pos).getName())) {
                                    cardSetInfo.get(getIndex(filterCardSet.get(pos))).setFavorite("");
                                    filterCardSet.get(pos).setFavorite("");
                                    favoriteCardSetInfo.get(i).setActivation(check);
                                    cardDBHelper.UpdateInfoFavoriteList(filterCardSet.get(pos).getHaveAwake(), check, favoriteCardSetInfo.get(i).getName());
                                    cardDBHelper.UpdateInfoCardSetCard("", filterCardSet.get(pos).getId());
                                    favoriteAdapter.removeItem(favoriteCardSetInfo.get(i));
                                    holder.imgFavoritesCardSet.setColorFilter(filter);

                                    /*
                                    if (cardSetPage.checkFavorite())
                                        getFavoriteSort();
                                    */

                                }
                            }
                        } else {         //즐찾이 되면
                            for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
                                if (favoriteCardSetInfo.get(i).getName().equals(filterCardSet.get(pos).getName())) {
                                    cardSetInfo.get(getIndex(filterCardSet.get(pos))).setFavorite(favoriteCardSetInfo.get(i).getName());
                                    filterCardSet.get(pos).setFavorite(favoriteCardSetInfo.get(i).getName());
                                    favoriteCardSetInfo.get(i).setActivation(check);
                                    favoriteCardSetInfo.get(i).setAwake(cardSetInfo.get(getIndex(filterCardSet.get(pos))).getHaveAwake());
                                    cardDBHelper.UpdateInfoFavoriteList(favoriteCardSetInfo.get(pos).getAwake(), check, favoriteCardSetInfo.get(i).getName());
                                    cardDBHelper.UpdateInfoCardSetCard(favoriteCardSetInfo.get(i).getName(), filterCardSet.get(pos).getId());
                                    favoriteAdapter.updateActivationFavoriteCardSet();
                                    holder.imgFavoritesCardSet.setColorFilter(null);

                                    /*
                                    if (cardSetPage.checkFavorite())
                                        getFavoriteSort();

                                    */
                                }
                            }
                        }

                        notifyDataSetChanged();
                    }
                });

                Dialog dialogAwakeNHaveCard = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialogAwakeNHaveCard.setContentView(R.layout.awake_havecard_change);
                NumberPicker numberPickerAwake = dialogAwakeNHaveCard.findViewById(R.id.numberPickerAwake);
                numberPickerAwake.setMinValue(0);
                numberPickerAwake.setMaxValue(5);
                numberPickerAwake.setWrapSelectorWheel(false);

                NumberPicker numberPickerHave = dialogAwakeNHaveCard.findViewById(R.id.numberPickerHave);
                numberPickerHave.setMinValue(0);
                numberPickerHave.setMaxValue(15);
                numberPickerHave.setWrapSelectorWheel(false);


                Button btnCancer = dialogAwakeNHaveCard.findViewById(R.id.btnCancer);
                Button btnOK = dialogAwakeNHaveCard.findViewById(R.id.btnOK);


                imgCardSetDetail0.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard0());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard0()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard0() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getAcquisition_info());

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
                txtHaveAwakeHaveCard0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard0());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard0());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD0_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD0_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard0(awake);
                                filterCardSet.get(pos).setAwakeCard0(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).setNum(number);
                                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard0() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard0());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail0, filter, filterCardSet.get(pos).getCheckCard0(), pos, filterCardSet.get(pos).getCard0());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));


                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();
                                if (cardSetPage.completeChecked())
                                    completePartRemove();

                                 */

                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail1.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard1());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard1()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard1() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getAcquisition_info());

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
                txtHaveAwakeHaveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard1());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard1());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD1_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD1_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard1(awake);
                                filterCardSet.get(pos).setAwakeCard1(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).setNum(number);
                                txtHaveAwakeHaveCard1.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard1() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard1());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail1, filter, filterCardSet.get(pos).getCheckCard1(), pos, filterCardSet.get(pos).getCard1());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail2.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard2());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard2()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard2() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getAcquisition_info());

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
                txtHaveAwakeHaveCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard2());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard2());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD2_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD2_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard2(awake);
                                filterCardSet.get(pos).setAwakeCard2(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).setNum(number);
                                txtHaveAwakeHaveCard2.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard2() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard2());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail2, filter, filterCardSet.get(pos).getCheckCard2(), pos, filterCardSet.get(pos).getCard2());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail3.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard3());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard3()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard3() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getAcquisition_info());

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
                txtHaveAwakeHaveCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard3());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard3());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD3_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD3_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard3(awake);
                                filterCardSet.get(pos).setAwakeCard3(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).setNum(number);
                                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard3() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard3());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail3, filter, filterCardSet.get(pos).getCheckCard3(), pos, filterCardSet.get(pos).getCard3());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail4.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard4());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard4()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard4() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getAcquisition_info());

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
                txtHaveAwakeHaveCard4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard4());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard4());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD4_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD4_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard4(awake);
                                filterCardSet.get(pos).setAwakeCard4(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).setNum(number);
                                txtHaveAwakeHaveCard4.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard4() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard4());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail4, filter, filterCardSet.get(pos).getCheckCard4(), pos, filterCardSet.get(pos).getCard4());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail5.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard5());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard5()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard5() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getAcquisition_info());

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
                txtHaveAwakeHaveCard5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard5());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard5());                                             //카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD5_AWAKE, awake, filterCardSet.get(pos).getId()); //카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD5_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());             //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard5(awake);
                                filterCardSet.get(pos).setAwakeCard5(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).setNum(number);
                                txtHaveAwakeHaveCard5.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard5() + "\n" + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard5());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail5, filter, filterCardSet.get(pos).getCheckCard5(), pos, filterCardSet.get(pos).getCard5());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail6.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardSet.get(pos).getCard6());
                        imgJustCard.setImageResource(getCardImg(filterCardSet.get(pos).getCard6()));
                        txtJustCardAwake.setText(filterCardSet.get(pos).getAwakeCard6() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getAcquisition_info());

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
                txtHaveAwakeHaveCard6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPickerAwake.setValue(filterCardSet.get(pos).getAwakeCard6());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getNum());

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
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                updateDEDAwakeInfoDB(awake, filterCardSet.get(pos).getCard6());//카드 각성도 업데이트(DED DB)
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD6_AWAKE, awake, filterCardSet.get(pos).getId());//카드 각성도 업데이트(cardSet DB)
                                int check = 0;
                                if (awake > 0) check = 1;
                                cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD6_CHECK, check, filterCardSet.get(pos).getId());//카드 획득 업데이트(cardSet DB)
                                cardDBHelper.UpdateInfoCardNum(number, cardInfo.get(getIndex(filterCardSet.get(pos))).getId());     //카드 수집 업데이트(cardList DB)
                                cardDBHelper.UpdateInfoCardAwake(awake, cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getId());    //카드 각성도 업데이트(cardListDB)
                                cardSetInfo.get(getIndex(filterCardSet.get(pos))).setAwakeCard6(awake);
                                filterCardSet.get(pos).setAwakeCard6(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).setAwake(awake);
                                cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).setNum(number);
                                txtHaveAwakeHaveCard6.setText(CARDSET_AWAKE + filterCardSet.get(pos).getAwakeCard6() + "\n"
                                        + CARDSET_CARD_NUM + cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getNum());
                                txtCardSetAwake_Detail.setText(CARD_SET_AWAKE_SUM + filterCardSet.get(pos).getHaveAwake());

                                updateAwakeFavoriteCardSetInfoAndDB(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake(), filterCardSet.get(pos).getCheckCard6());
                                favoriteAdapter.setAwake(filterCardSet.get(pos).getName(), filterCardSet.get(pos).getHaveAwake());

                                ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDDmgUpdate();
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                                isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                                imgDefaultColor(imgCardSetDetail6, filter, filterCardSet.get(pos).getCheckCard6(), pos, filterCardSet.get(pos).getCard6());
                                nextSetBonus(txtCardSetNextStep_Detail, filterCardSet.get(pos));

                                /*
                                if (cardSetPage.checkCompleteness())
                                    getCompletenessSort();

                                 */
                                if (cardSetPage.completeChecked())
                                    completePartRemove();


                                notifyDataSetChanged();
                                dialogAwakeNHaveCard.cancel();
                            }
                        });
                    }
                });

                imgCardSetDetail0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail0, filter, pos, filterCardSet.get(pos).getCard0());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard0());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD0_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard0());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard0(cardCheck);         //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard0(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard0())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail0, filterCardSet.get(pos).getCard0());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();
                    }
                });
                imgCardSetDetail1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail1, filter, pos, filterCardSet.get(pos).getCard1());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard1());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD1_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard1());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard1(cardCheck);      //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard1(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard1())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail1, filterCardSet.get(pos).getCard1());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail2, filter, pos, filterCardSet.get(pos).getCard2());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard2());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD2_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard2());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard2(cardCheck);                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard2(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard2())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail2, filterCardSet.get(pos).getCard2());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail3, filter, pos, filterCardSet.get(pos).getCard3());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard3());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD3_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard3());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard3(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard3(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard3())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail3, filterCardSet.get(pos).getCard3());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail4, filter, pos, filterCardSet.get(pos).getCard4());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard4());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD4_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard4());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard4(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard4(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard4())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail4, filterCardSet.get(pos).getCard4());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail5, filter, pos, filterCardSet.get(pos).getCard5());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard5());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD5_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard5());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard5(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard5(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard5())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail5, filterCardSet.get(pos).getCard5());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();

                        notifyDataSetChanged();

                    }
                });
                imgCardSetDetail6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardSetDetail6, filter, pos, filterCardSet.get(pos).getCard6());              //카드 획득 유무 0 미획득, 1획득
                        updateDEDCheckInfoDB(cardCheck, filterCardSet.get(pos).getCard6());              //cardX수집 유무 업데이트(DED DB)
                        cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD6_CHECK, cardCheck, filterCardSet.get(pos).getId());//cardX수집 유무 업데이트(CardSet DB)
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardSet.get(pos).getCard6());     //카드 수집 유무 업데이트(cardList DB)
                        cardSetInfo.get(getIndex(filterCardSet.get(pos))).setCheckCard6(cardCheck);                                                          //cardX수집 유무 업데이트(현재 DED array )
                        filterCardSet.get(pos).setCheckCard6(cardCheck);
                        cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).setGetCard(cardCheck);                //카드 수집 유무 업데이트(현재 cardList array)

                        if (cardInfo.get(getIndex(cardInfo, filterCardSet.get(pos).getCard6())).getAwake() > 0) {
                            cardAwake(imgCardSetDetail6, filterCardSet.get(pos).getCard6());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        ((MainPage) MainPage.mainContext).haveCardSetCheckUpdate();
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.cvCardSetBackground);
                        isCompleteCardBookBackgroundColor(filterCardSet.get(pos), holder.imgFavoritesCardSet);
                        haveStatUpdate();
                        haveDEDDmgUpdate();

                        /*
                        if (cardSetPage.checkCompleteness()) {
                            getCompletenessSort();
                        }

                         */
                        if (cardSetPage.completeChecked())
                            completePartRemove();


                        notifyDataSetChanged();

                    }
                });

                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return filterCardSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cvCardSetBackground;
        private TextView txtCardSetName;
        private ImageView imgFavoritesCardSet;
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
            imgFavoritesCardSet = itemView.findViewById(R.id.imgFavoritesCardSet);
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

    // 카드작이 완성되면 각성도에 따라 도감의 배경을 흰색->노란색으로 바꿈
    private void isCompleteCardBookBackgroundColor(CardSetInfo cardSetInfo, ConstraintLayout cv) {
        if ((cardSetInfo.isCompleteCardSet()) && (cardSetInfo.getHaveAwake() >= (cardSetInfo.getHaveCard() * 5))) {
            cv.setBackgroundColor(Color.parseColor("#FFF4BD"));
        } else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    // 카드작이 완성되면 각성도에 따라 도감의 배경을 흰색->노란색으로 바꿈
    private void isCompleteCardBookBackgroundColor(CardSetInfo cardSetInfo, ImageView cv) {
        if ((cardSetInfo.isCompleteCardSet()) && (cardSetInfo.getHaveAwake() >= (cardSetInfo.getHaveCard() * 5))) {
            cv.setBackgroundColor(Color.parseColor("#FFF4BD"));
        } else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    //각 세트 보너스 수치도달까지 남는 각성도 수
    private void nextSetBonus(TextView txtCardSetNextStep_Detail, CardSetInfo cardSetInfo) {
        if (cardSetInfo.getNeedAwake0() != 0) {
            if (cardSetInfo.getNeedAwake0() > cardSetInfo.getHaveAwake()) {   //카드 각성도가 필요카드 최소수보다 작을때
                txtCardSetNextStep_Detail.setText("다음 효과 까지 : " + (cardSetInfo.getNeedAwake0() - cardSetInfo.getHaveAwake()) + " 남음");
            } else if (cardSetInfo.getNeedAwake0() <= cardSetInfo.getHaveAwake() && cardSetInfo.getNeedAwake1() > cardSetInfo.getHaveAwake()) {
                txtCardSetNextStep_Detail.setText("다음 효과 까지 : " + (cardSetInfo.getNeedAwake1() - cardSetInfo.getHaveAwake()) + " 남음");
            } else if (cardSetInfo.getNeedAwake1() <= cardSetInfo.getHaveAwake() && cardSetInfo.getNeedAwake2() > cardSetInfo.getHaveAwake()) {
                txtCardSetNextStep_Detail.setText("다음 효과 까지 : " + (cardSetInfo.getNeedAwake2() - cardSetInfo.getHaveAwake()) + " 남음");
            } else {
                txtCardSetNextStep_Detail.setVisibility(View.GONE);
            }
        } else {
            txtCardSetNextStep_Detail.setVisibility(View.GONE);
        }
    }

    //획득 못한 카드는 흑백이 기본으로 보이도록 최초 설정
    private void imgDefaultColor(ImageView iv, ColorMatrixColorFilter filter, int check, int position, String name) {
        if (check == 1) {
            setCardBorder(iv, name);
            iv.setColorFilter(null);
        } else {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
        }
    }

    //클릭시 카드를 흑백으로 바꾸는 함수, 데이터베이스 카드 도감 획득 유무도 변경.
    private int imgGrayScale(ImageView iv, ColorMatrixColorFilter filter) {
        int check = 0;
        if (iv.getColorFilter() != filter) {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
            check = 0;
        } else {
            iv.setColorFilter(null);
            check = 1;
        }
        return check;
    }

    //클릭시 카드의 각성도 정보가 1이상인 경우 카드가 무조건 획득으로(컬러필터 제거 및 획득)
    private void cardAwake(ImageView iv, String name) {
        iv.setColorFilter(null);
        setCardBorder(iv, name);
    }

    //클릭시 카드를 흑백으로 바꾸는 함수, 데이터베이스 카드 도감 획득 유무도 변경.
    private int imgGrayScale(ImageView iv, ColorMatrixColorFilter filter, int position, String name) {
        int check = 0;
        if (iv.getColorFilter() != filter) {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
            check = 0;
        } else {
            setCardBorder(iv, name);
            iv.setColorFilter(null);
            check = 1;
        }
        return check;
    }

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

    //cardSet 원본의 Index 를 찾기 위한 메소드
    private int getIndex(CardSetInfo filterCardSet) {
        int index = 0;
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getId() == filterCardSet.getId()) {
                index = i;
                break;
            }
        }
        return index;
    }

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

    private void setFavoriteImg(ImageView iv, int position, ColorMatrixColorFilter filter) {
        iv.setImageResource(R.drawable.gold_star);
        if (!filterCardSet.get(position).getFavorite().isEmpty()) {
            iv.setColorFilter(null);
        } else
            iv.setColorFilter(filter);
    }

    private void updateAwakeFavoriteCardSetInfoAndDB(String changeAwakeCardName, int changeAwake, int check) {
        for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
            if (favoriteCardSetInfo.get(i).getName().equals(changeAwakeCardName)) {
                favoriteCardSetInfo.get(i).setAwake(changeAwake);
                cardDBHelper.UpdateInfoFavoriteList(changeAwake, check, favoriteCardSetInfo.get(i).getName());
                break;
            }
        }
    }

    private boolean isAllCompleteCardSet(CardSetInfo cardSetInfo) {
        if (cardSetInfo.isCompleteCardSet()) { //카드 세트 수집 완료시.
            if (cardSetInfo.getHaveAwake() == (cardSetInfo.getHaveCard() * 5)) //수집한 카드의 각성도합이 최대값일시
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    public void getCompleteFilter() {
        filterCardSet = cardSetInfo;

        if (cardSetPage.completeChecked()) {
            completePartRemove();
        }

        if (cardSetPage.checkDefault()) {
            getDefaultSort();
        }
        if (cardSetPage.checkName()) {
            getNameSort();
        }
        if (cardSetPage.checkCompleteness()) {
            getCompletenessSort();
        }
        if (cardSetPage.checkFavorite()) {
            getFavoriteSort();
        }

        notifyDataSetChanged();

    }

    private void completePartRemove() {
        ArrayList<CardSetInfo> filteringList = new ArrayList<CardSetInfo>();
        for (int i = 0; i < filterCardSet.size(); i++) {
            if (!isAllCompleteCardSet(filterCardSet.get(i))) {
                filteringList.add(filterCardSet.get(i));
            }
        }
        filterCardSet = filteringList;
    }

    public Filter getSearchFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (cardSetPage.completeChecked()) {
                    if (charString.isEmpty()) {
                        filterCardSet = baseFilteredCardSet;
                    } else {
                        ArrayList<CardSetInfo> filteringList = new ArrayList<CardSetInfo>();
                        for (int i = 0; i < baseFilteredCardSet.size(); i++) {
                            if (baseFilteredCardSet.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(baseFilteredCardSet.get(i));
                            }
                        }
                        filterCardSet = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCardSet;
                    return filterResults;
                } else {
                    if (charString.isEmpty()) {
                        filterCardSet = cardSetInfo;
                    } else {
                        ArrayList<CardSetInfo> filteringList = new ArrayList<CardSetInfo>();
                        for (int i = 0; i < cardSetInfo.size(); i++) {
                            if (cardSetInfo.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(cardSetInfo.get(i));
                            }
                        }
                        filterCardSet = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCardSet;
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterCardSet = (ArrayList<CardSetInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private void setFilteredCardSet() {
        ArrayList<CardSetInfo> filteringList = new ArrayList<CardSetInfo>();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (!isAllCompleteCardSet(cardSetInfo.get(i))) {
                filteringList.add(cardSetInfo.get(i));
            }
        }
        baseFilteredCardSet = filteringList;
    }

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

    public void getDefaultSort() {
        Collections.sort(filterCardSet, new Comparator<CardSetInfo>() {
            @Override
            public int compare(CardSetInfo o1, CardSetInfo o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                else
                    return 1;
            }
        });
        if (cardSetPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    public void getNameSort() {
        Collections.sort(filterCardSet);
        if (cardSetPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    public void getCompletenessSort() {
        getNameSort();
        Collections.sort(filterCardSet, new Comparator<CardSetInfo>() {
            @Override
            public int compare(CardSetInfo o1, CardSetInfo o2) {
                if (o1.completePercent() < o2.completePercent())
                    return 1;
                else
                    return -1;
            }
        });

        if (cardSetPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    public void getFavoriteSort() {
        getNameSort();
        Collections.sort(filterCardSet, new Comparator<CardSetInfo>() {
            @Override
            public int compare(CardSetInfo o1, CardSetInfo o2) {
                if (o1.favoriteCheck() <= o2.favoriteCheck()) {
                    return -1;
                } else
                    return 1;
            }
        });

        if (cardSetPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //스텟, 도감 달성 개수 업데이트
    private void haveStatUpdate() {
        int[] haveStat = new int[]{0, 0, 0};

        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getOption().equals(STAT[0]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[0] += cardBookInfo.get(i).getValue();
            }
            if (cardBookInfo.get(i).getOption().equals(STAT[1]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[1] += cardBookInfo.get(i).getValue();
            }
            if (cardBookInfo.get(i).getOption().equals(STAT[2]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[2] += cardBookInfo.get(i).getValue();
            }
        }

        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
    }

    //악추피 달성 현황 업데이트
    private void haveDEDDmgUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        haveDED = 0;
        for (int i = 0; i < DEDInfo.size(); i++) {
            haveDED += DEDInfo.get(i).getDmgSum();
        }
        haveDED = Float.parseFloat(df.format(haveDED));

        ((MainPage) MainPage.mainContext).setDemonExtraDmgInfo(haveDED);
    }

    //DEDInfo, DED DB Update : 카드 획득
    private void updateDEDCheckInfoDB(int check, String cardName) {
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (DEDInfo.get(i).getCard0().equals(cardName)) {
                DEDInfo.get(i).setCheckCard0(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard0", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard1().equals(cardName)) {
                DEDInfo.get(i).setCheckCard1(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard1", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard2().equals(cardName)) {
                DEDInfo.get(i).setCheckCard2(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard2", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard3().equals(cardName)) {
                DEDInfo.get(i).setCheckCard3(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard3", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard4().equals(cardName)) {
                DEDInfo.get(i).setCheckCard4(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard4", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard5().equals(cardName)) {
                DEDInfo.get(i).setCheckCard5(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard5", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard6().equals(cardName)) {
                DEDInfo.get(i).setCheckCard6(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard6", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard7().equals(cardName)) {
                DEDInfo.get(i).setCheckCard7(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard7", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard8().equals(cardName)) {
                DEDInfo.get(i).setCheckCard8(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard8", check, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard9().equals(cardName)) {
                DEDInfo.get(i).setCheckCard9(check);
                cardDBHelper.UpdateInfoDEDCard("checkCard9", check, DEDInfo.get(i).getId());
                continue;
            }
        }

    }

    //DEDInfo, DED DB Update : 카드 획득
    private void updateDEDAwakeInfoDB(int awake, String cardName) {
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (DEDInfo.get(i).getCard0().equals(cardName)) {
                DEDInfo.get(i).setAwakeCard0(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard0", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard0", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard1().equals(cardName)) {
                DEDInfo.get(i).setCheckCard1(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard1", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard1", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard2().equals(cardName)) {
                DEDInfo.get(i).setCheckCard2(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard2", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard2", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard3().equals(cardName)) {
                DEDInfo.get(i).setCheckCard3(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard3", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard3", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard4().equals(cardName)) {
                DEDInfo.get(i).setCheckCard4(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard4", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard4", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard5().equals(cardName)) {
                DEDInfo.get(i).setCheckCard5(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard5", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard5", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard6().equals(cardName)) {
                DEDInfo.get(i).setCheckCard6(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard6", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard6", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard7().equals(cardName)) {
                DEDInfo.get(i).setCheckCard7(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard7", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard7", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard8().equals(cardName)) {
                DEDInfo.get(i).setCheckCard8(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard8", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard8", 1, DEDInfo.get(i).getId());
                continue;
            }
            if (DEDInfo.get(i).getCard9().equals(cardName)) {
                DEDInfo.get(i).setCheckCard9(awake);
                cardDBHelper.UpdateInfoDEDCard("awakeCard9", awake, DEDInfo.get(i).getId());
                if (awake > 0)      //각성도 수치가 1이상이면 무조건 카드는 획득판정
                    cardDBHelper.UpdateInfoDEDCard("checkCard9", 1, DEDInfo.get(i).getId());
                continue;
            }
        }
    }


    private boolean isCompleteCardBook(CardBookInfo cardBook_all) {
        if (cardBook_all.getHaveCard() == cardBook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }
}