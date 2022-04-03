package com.ulmu.lostarkcardmanager;

import static android.graphics.Color.parseColor;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardBookAdapter extends RecyclerView.Adapter<CardBookAdapter.ViewHolder> {
    private final String[] STAT = {"치명", "특화", "신속"};
    private static int[] haveStat;                  //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯 상승치. 치명, 특화, 신속 순서
    private static int[] haveStatCardBook;          //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯별 최대 도감 달성 개수. 치명, 특화, 신속 순서
    private static int[] haveStatCardBookCount;     //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯별 현재 도감 달성 개수. 치명, 특화, 신속 순서

    // CardBook DB Column Name
    private static final String CARD_BOOK_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private static final String CARD_BOOK_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private static final String CARD_BOOK_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private static final String CARD_BOOK_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private static final String CARD_BOOK_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private static final String CARD_BOOK_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private static final String CARD_BOOK_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private static final String CARD_BOOK_COLUMN_NAME_CARD7_CHECK = "checkCard7";
    private static final String CARD_BOOK_COLUMN_NAME_CARD8_CHECK = "checkCard8";
    private static final String CARD_BOOK_COLUMN_NAME_CARD9_CHECK = "checkCard9";

    private ArrayList<CardBookInfo> cardBookInfo;
    private ArrayList<CardBookInfo> filterCardBook;
    private ArrayList<CardInfo> cardInfo;
    private Context context;
    private CardDBHelper cardDBHelper;
    private CardBookPage cardBookPage;

    private ArrayList<CardBookInfo> baseFilteredCardBook;   //완성되지 않은 카드도감 목록

    public CardBookAdapter(ArrayList<CardBookInfo> cardBookInfo) {
        this.cardBookInfo = cardBookInfo;
    }

    public CardBookAdapter(Context context, CardBookPage cardBookPage) {
        this.cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.context = context;
        cardDBHelper = new CardDBHelper(context);
        filterCardBook = ((MainPage) MainPage.mainContext).cardBookInfo;
        this.cardBookPage = cardBookPage;
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        haveStatUpdate(cardBookInfo);   //haveCardToCardBookUpdate()로 얻은 정보를 바탕으로 최초 값 획득
        this.baseFilteredCardBook = new ArrayList<CardBookInfo>();
        setFilteredCardBook();

    }

    @NonNull
    @Override
    public CardBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardbook, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        cardBookPage.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);

        holder.txtCardBookName.setText(filterCardBook.get(position).getName());
        holder.txtCardBookValue.setText(filterCardBook.get(position).getOption() + " + " + filterCardBook.get(position).getValue());
        //이미지뷰 구현할것
        holder.imgCardBook0.setImageResource(getCardImg(filterCardBook.get(position).getCard0()));
        holder.imgCardBook1.setImageResource(getCardImg(filterCardBook.get(position).getCard1()));
        holder.imgCardBook2.setImageResource(getCardImg(filterCardBook.get(position).getCard2()));
        holder.imgCardBook3.setImageResource(getCardImg(filterCardBook.get(position).getCard3()));
        holder.imgCardBook4.setImageResource(getCardImg(filterCardBook.get(position).getCard4()));
        holder.imgCardBook5.setImageResource(getCardImg(filterCardBook.get(position).getCard5()));
        holder.imgCardBook6.setImageResource(getCardImg(filterCardBook.get(position).getCard6()));
        holder.imgCardBook7.setImageResource(getCardImg(filterCardBook.get(position).getCard7()));
        holder.imgCardBook8.setImageResource(getCardImg(filterCardBook.get(position).getCard8()));
        holder.imgCardBook9.setImageResource(getCardImg(filterCardBook.get(position).getCard9()));
        //없는 카드는 흑백(기본), 획득한 카드는 컬러로
        imgDefaultColor(holder.imgCardBook0, filter, filterCardBook.get(position).getCheckCard0(), filterCardBook.get(position).getCard0());
        imgDefaultColor(holder.imgCardBook1, filter, filterCardBook.get(position).getCheckCard1(), filterCardBook.get(position).getCard1());
        imgDefaultColor(holder.imgCardBook2, filter, filterCardBook.get(position).getCheckCard2(), filterCardBook.get(position).getCard2());
        imgDefaultColor(holder.imgCardBook3, filter, filterCardBook.get(position).getCheckCard3(), filterCardBook.get(position).getCard3());
        imgDefaultColor(holder.imgCardBook4, filter, filterCardBook.get(position).getCheckCard4(), filterCardBook.get(position).getCard4());
        imgDefaultColor(holder.imgCardBook5, filter, filterCardBook.get(position).getCheckCard5(), filterCardBook.get(position).getCard5());
        imgDefaultColor(holder.imgCardBook6, filter, filterCardBook.get(position).getCheckCard6(), filterCardBook.get(position).getCard6());
        imgDefaultColor(holder.imgCardBook7, filter, filterCardBook.get(position).getCheckCard7(), filterCardBook.get(position).getCard7());
        imgDefaultColor(holder.imgCardBook8, filter, filterCardBook.get(position).getCheckCard8(), filterCardBook.get(position).getCard8());
        imgDefaultColor(holder.imgCardBook9, filter, filterCardBook.get(position).getCheckCard9(), filterCardBook.get(position).getCard9());
        //도감에 해당하지 않는 프레임 제거
        imgVisibility(filterCardBook.get(position).getCard2(), holder.imgCardBook2, holder.txtCardbook_Cardname2);
        imgVisibility(filterCardBook.get(position).getCard3(), holder.imgCardBook3, holder.txtCardbook_Cardname3);
        imgVisibility(filterCardBook.get(position).getCard4(), holder.imgCardBook4, holder.txtCardbook_Cardname4);
        imgVisibility(filterCardBook.get(position).getCard5(), holder.imgCardBook5, holder.txtCardbook_Cardname5);
        imgVisibility(filterCardBook.get(position).getCard6(), holder.imgCardBook6, holder.txtCardbook_Cardname6);
        imgVisibility(filterCardBook.get(position).getCard7(), holder.imgCardBook7, holder.txtCardbook_Cardname7);
        imgVisibility(filterCardBook.get(position).getCard8(), holder.imgCardBook8, holder.txtCardbook_Cardname8);
        imgVisibility(filterCardBook.get(position).getCard9(), holder.imgCardBook9, holder.txtCardbook_Cardname9);
        //카드 모두 획득시 백그라운드 컬러 노란색으로
        isCompleteCardBookBackgroundColor(filterCardBook.get(position), holder.cvCardbookBackground);

        //텍스트 구현
        holder.txtCardbook_Cardname0.setText(filterCardBook.get(position).getCard0());
        holder.txtCardbook_Cardname1.setText(filterCardBook.get(position).getCard1());
        holder.txtCardbook_Cardname2.setText(filterCardBook.get(position).getCard2());
        holder.txtCardbook_Cardname3.setText(filterCardBook.get(position).getCard3());
        holder.txtCardbook_Cardname4.setText(filterCardBook.get(position).getCard4());
        holder.txtCardbook_Cardname5.setText(filterCardBook.get(position).getCard5());
        holder.txtCardbook_Cardname6.setText(filterCardBook.get(position).getCard6());
        holder.txtCardbook_Cardname7.setText(filterCardBook.get(position).getCard7());
        holder.txtCardbook_Cardname8.setText(filterCardBook.get(position).getCard8());
        holder.txtCardbook_Cardname9.setText(filterCardBook.get(position).getCard9());

        holder.cvCardbookBackground.setOnClickListener(new View.OnClickListener() { //카드 도감 item 터치시 카드도감 dialog가 뜨며 내용 수정 가능
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.cardbook_detail);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                int pos = positionGet;  //position 값을 넣기 위해 넣은 인수
                //카드도감 이름, 옵션 연결
                TextView txtCardBookName = dialog.findViewById(R.id.txtCardBookName);
                TextView txtCardBookValue = dialog.findViewById(R.id.txtCardBookValue);
                txtCardBookName.setText(filterCardBook.get(pos).getName());
                txtCardBookValue.setText(filterCardBook.get(pos).getOption() + " + " + filterCardBook.get(pos).getValue());
                TextView txtCardBookName_CardName0 = dialog.findViewById(R.id.txtCardBookName_CardName0);
                TextView txtCardBookName_CardName1 = dialog.findViewById(R.id.txtCardBookName_CardName1);
                TextView txtCardBookName_CardName2 = dialog.findViewById(R.id.txtCardBookName_CardName2);
                TextView txtCardBookName_CardName3 = dialog.findViewById(R.id.txtCardBookName_CardName3);
                TextView txtCardBookName_CardName4 = dialog.findViewById(R.id.txtCardBookName_CardName4);
                TextView txtCardBookName_CardName5 = dialog.findViewById(R.id.txtCardBookName_CardName5);
                TextView txtCardBookName_CardName6 = dialog.findViewById(R.id.txtCardBookName_CardName6);
                TextView txtCardBookName_CardName7 = dialog.findViewById(R.id.txtCardBookName_CardName7);
                TextView txtCardBookName_CardName8 = dialog.findViewById(R.id.txtCardBookName_CardName8);
                TextView txtCardBookName_CardName9 = dialog.findViewById(R.id.txtCardBookName_CardName9);

                txtCardBookName_CardName0.setText(filterCardBook.get(pos).getCard0());
                txtCardBookName_CardName1.setText(filterCardBook.get(pos).getCard1());
                txtCardBookName_CardName2.setText(filterCardBook.get(pos).getCard2());
                txtCardBookName_CardName3.setText(filterCardBook.get(pos).getCard3());
                txtCardBookName_CardName4.setText(filterCardBook.get(pos).getCard4());
                txtCardBookName_CardName5.setText(filterCardBook.get(pos).getCard5());
                txtCardBookName_CardName6.setText(filterCardBook.get(pos).getCard6());
                txtCardBookName_CardName7.setText(filterCardBook.get(pos).getCard7());
                txtCardBookName_CardName8.setText(filterCardBook.get(pos).getCard8());
                txtCardBookName_CardName9.setText(filterCardBook.get(pos).getCard9());


                ImageView imgCardBookImg0 = dialog.findViewById(R.id.imgCardBookImg0);
                ImageView imgCardBookImg1 = dialog.findViewById(R.id.imgCardBookImg1);
                ImageView imgCardBookImg2 = dialog.findViewById(R.id.imgCardBookImg2);
                ImageView imgCardBookImg3 = dialog.findViewById(R.id.imgCardBookImg3);
                ImageView imgCardBookImg4 = dialog.findViewById(R.id.imgCardBookImg4);
                ImageView imgCardBookImg5 = dialog.findViewById(R.id.imgCardBookImg5);
                ImageView imgCardBookImg6 = dialog.findViewById(R.id.imgCardBookImg6);
                ImageView imgCardBookImg7 = dialog.findViewById(R.id.imgCardBookImg7);
                ImageView imgCardBookImg8 = dialog.findViewById(R.id.imgCardBookImg8);
                ImageView imgCardBookImg9 = dialog.findViewById(R.id.imgCardBookImg9);

                imgCardBookImg0.setImageResource(getCardImg(filterCardBook.get(pos).getCard0()));
                imgCardBookImg1.setImageResource(getCardImg(filterCardBook.get(pos).getCard1()));
                imgCardBookImg2.setImageResource(getCardImg(filterCardBook.get(pos).getCard2()));
                imgCardBookImg3.setImageResource(getCardImg(filterCardBook.get(pos).getCard3()));
                imgCardBookImg4.setImageResource(getCardImg(filterCardBook.get(pos).getCard4()));
                imgCardBookImg5.setImageResource(getCardImg(filterCardBook.get(pos).getCard5()));
                imgCardBookImg6.setImageResource(getCardImg(filterCardBook.get(pos).getCard6()));
                imgCardBookImg7.setImageResource(getCardImg(filterCardBook.get(pos).getCard7()));
                imgCardBookImg8.setImageResource(getCardImg(filterCardBook.get(pos).getCard8()));
                imgCardBookImg9.setImageResource(getCardImg(filterCardBook.get(pos).getCard9()));

                //미획득 카드는 흑백(기본), 획득한 카드는 컬러로
                imgDefaultColor(imgCardBookImg0, filter, filterCardBook.get(pos).getCheckCard0(), filterCardBook.get(pos).getCard0());
                imgDefaultColor(imgCardBookImg1, filter, filterCardBook.get(pos).getCheckCard1(), filterCardBook.get(pos).getCard1());
                imgDefaultColor(imgCardBookImg2, filter, filterCardBook.get(pos).getCheckCard2(), filterCardBook.get(pos).getCard2());
                imgDefaultColor(imgCardBookImg3, filter, filterCardBook.get(pos).getCheckCard3(), filterCardBook.get(pos).getCard3());
                imgDefaultColor(imgCardBookImg4, filter, filterCardBook.get(pos).getCheckCard4(), filterCardBook.get(pos).getCard4());
                imgDefaultColor(imgCardBookImg5, filter, filterCardBook.get(pos).getCheckCard5(), filterCardBook.get(pos).getCard5());
                imgDefaultColor(imgCardBookImg6, filter, filterCardBook.get(pos).getCheckCard6(), filterCardBook.get(pos).getCard6());
                imgDefaultColor(imgCardBookImg7, filter, filterCardBook.get(pos).getCheckCard7(), filterCardBook.get(pos).getCard7());
                imgDefaultColor(imgCardBookImg8, filter, filterCardBook.get(pos).getCheckCard8(), filterCardBook.get(pos).getCard8());
                imgDefaultColor(imgCardBookImg9, filter, filterCardBook.get(pos).getCheckCard9(), filterCardBook.get(pos).getCard9());

                //없는 카드는 안 보이게
                imgVisibility(filterCardBook.get(pos).getCard2(), dialog.findViewById(R.id.imgCardBookImg2), dialog.findViewById(R.id.txtCardBookName_CardName2));
                imgVisibility(filterCardBook.get(pos).getCard3(), dialog.findViewById(R.id.imgCardBookImg3), dialog.findViewById(R.id.txtCardBookName_CardName3));
                imgVisibility(filterCardBook.get(pos).getCard4(), dialog.findViewById(R.id.imgCardBookImg4), dialog.findViewById(R.id.txtCardBookName_CardName4));
                imgVisibility(filterCardBook.get(pos).getCard5(), dialog.findViewById(R.id.imgCardBookImg5), dialog.findViewById(R.id.txtCardBookName_CardName5));
                imgVisibility(filterCardBook.get(pos).getCard6(), dialog.findViewById(R.id.imgCardBookImg6), dialog.findViewById(R.id.txtCardBookName_CardName6));
                imgVisibility(filterCardBook.get(pos).getCard7(), dialog.findViewById(R.id.imgCardBookImg7), dialog.findViewById(R.id.txtCardBookName_CardName7));
                imgVisibility(filterCardBook.get(pos).getCard8(), dialog.findViewById(R.id.imgCardBookImg8), dialog.findViewById(R.id.txtCardBookName_CardName8));
                imgVisibility(filterCardBook.get(pos).getCard9(), dialog.findViewById(R.id.imgCardBookImg9), dialog.findViewById(R.id.txtCardBookName_CardName9));

                imgCardBookImg0.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard0());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard0()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard0())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard0())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard0())).getAcquisition_info());

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
                imgCardBookImg0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg0, filter, filterCardBook.get(pos).getCard0());
                        //도감 db 갱신, 도감 arraylist 갱신 (cardCheckX);
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD0_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard0(cardCheck);
                        filterCardBook.get(pos).setCheckCard0(cardCheck);
                        //업데이트 된 정보 갱신
                        //카드 db 갱신,카드리스트 갱신
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard0());
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard0())).setGetCard(cardCheck);

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard0())).getAwake() > 0) {
                            cardAwake(imgCardBookImg0, filterCardBook.get(pos).getCard0());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        //업데이트 및 갱신
                        updateCardBookAndMain();
/*                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();*/
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg1.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard1());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard1()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard1())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard1())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard1())).getAcquisition_info());

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
                imgCardBookImg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg1, filter, filterCardBook.get(pos).getCard1());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD1_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard1(cardCheck);
                        filterCardBook.get(pos).setCheckCard1(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard1());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard1())).setGetCard(cardCheck);
                        //카드리스트 갱신
                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard1())).getAwake() > 0) {
                            cardAwake(imgCardBookImg1, filterCardBook.get(pos).getCard1());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg2.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard2());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard2()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard2())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard2())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard2())).getAcquisition_info());

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
                imgCardBookImg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg2, filter, filterCardBook.get(pos).getCard2());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD2_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard2(cardCheck);
                        filterCardBook.get(pos).setCheckCard2(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard2());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard2())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard2())).getAwake() > 0) {
                            cardAwake(imgCardBookImg2, filterCardBook.get(pos).getCard2());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg3.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard3());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard3()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard3())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard3())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard3())).getAcquisition_info());

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
                imgCardBookImg3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg3, filter, filterCardBook.get(pos).getCard3());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD3_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard3(cardCheck);
                        filterCardBook.get(pos).setCheckCard3(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard3());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard3())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard3())).getAwake() > 0) {
                            cardAwake(imgCardBookImg3, filterCardBook.get(pos).getCard3());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg4.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard4());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard4()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard4())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard4())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard4())).getAcquisition_info());

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
                imgCardBookImg4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg4, filter, filterCardBook.get(pos).getCard4());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD4_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard4(cardCheck);
                        filterCardBook.get(pos).setCheckCard4(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard4());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard4())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard4())).getAwake() > 0) {
                            cardAwake(imgCardBookImg4, filterCardBook.get(pos).getCard4());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg5.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard5());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard5()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard5())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard5())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard5())).getAcquisition_info());

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
                imgCardBookImg5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg5, filter, filterCardBook.get(pos).getCard5());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD5_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard5(cardCheck);
                        filterCardBook.get(pos).setCheckCard5(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard5());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard5())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard5())).getAwake() > 0) {
                            cardAwake(imgCardBookImg5, filterCardBook.get(pos).getCard5());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg6.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard6());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard6()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard6())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard6())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard6())).getAcquisition_info());

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
                imgCardBookImg6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg6, filter, filterCardBook.get(pos).getCard6());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD6_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard6(cardCheck);
                        filterCardBook.get(pos).setCheckCard6(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard6());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard6())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard6())).getAwake() > 0) {
                            cardAwake(imgCardBookImg6, filterCardBook.get(pos).getCard6());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg7.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard7());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard7()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard7())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard7())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard7())).getAcquisition_info());

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
                imgCardBookImg7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg7, filter, filterCardBook.get(pos).getCard7());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD7_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard7(cardCheck);
                        filterCardBook.get(pos).setCheckCard7(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard7());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard7())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard7())).getAwake() > 0) {
                            cardAwake(imgCardBookImg7, filterCardBook.get(pos).getCard7());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg8.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard8());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard8()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard8())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard8())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard8())).getAcquisition_info());

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
                imgCardBookImg8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg8, filter, filterCardBook.get(pos).getCard8());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD8_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard8(cardCheck);
                        filterCardBook.get(pos).setCheckCard8(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard8());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard8())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard8())).getAwake() > 0) {
                            cardAwake(imgCardBookImg8, filterCardBook.get(pos).getCard8());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
                            completePartRemove();
                        notifyDataSetChanged();
                    }
                });

                imgCardBookImg9.setOnLongClickListener(new View.OnLongClickListener() {
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

                        txtJustCardName.setText(filterCardBook.get(positionGet).getCard9());
                        imgJustCard.setImageResource(getCardImg(filterCardBook.get(positionGet).getCard9()));
                        txtJustCardAwake.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard9())).getAwake() + "");
                        txtJustCardHave.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard9())).getNum() + "");
                        txtJustCardAcquisition_info.setText(cardInfo.get(getIndex(cardInfo, filterCardBook.get(positionGet).getCard9())).getAcquisition_info());

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
                imgCardBookImg9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookImg9, filter, filterCardBook.get(pos).getCard9());
                        cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD9_CHECK, cardCheck, filterCardBook.get(pos).getId());
                        cardBookInfo.get(getIndex(filterCardBook.get(pos).getId())).setCheckCard9(cardCheck);
                        filterCardBook.get(pos).setCheckCard9(cardCheck);
                        cardDBHelper.UpdateInfoCardCheck(cardCheck, filterCardBook.get(pos).getCard9());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard9())).setGetCard(cardCheck);             //카드리스트 갱신

                        if (cardInfo.get(getIndex(cardInfo, filterCardBook.get(pos).getCard9())).getAwake() > 0) {
                            cardAwake(imgCardBookImg9, filterCardBook.get(pos).getCard9());
                            Toast.makeText(context, "해당 카드는 각성도가 존재하여 획득취소되지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                        updateCardBookAndMain();
                        if (cardBookPage.checkCompleteness())
                            getCompletenessSort();
                        if (cardBookPage.completeChecked())
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
        return filterCardBook.size();    //세 도감 수의 합
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCardBookName;
        private TextView txtCardBookValue;
        private ImageView imgCardBook0;
        private ImageView imgCardBook1;
        private ImageView imgCardBook2;
        private ImageView imgCardBook3;
        private ImageView imgCardBook4;
        private ImageView imgCardBook5;
        private ImageView imgCardBook6;
        private ImageView imgCardBook7;
        private ImageView imgCardBook8;
        private ImageView imgCardBook9;
        private TextView txtCardbook_Cardname0;
        private TextView txtCardbook_Cardname1;
        private TextView txtCardbook_Cardname2;
        private TextView txtCardbook_Cardname3;
        private TextView txtCardbook_Cardname4;
        private TextView txtCardbook_Cardname5;
        private TextView txtCardbook_Cardname6;
        private TextView txtCardbook_Cardname7;
        private TextView txtCardbook_Cardname8;
        private TextView txtCardbook_Cardname9;
        private ConstraintLayout cvCardbookBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCardbookBackground = itemView.findViewById(R.id.cvCardBookBackground);
            txtCardBookName = itemView.findViewById(R.id.txtCardbookName);
            txtCardBookValue = itemView.findViewById(R.id.txtCardbookValue);
            imgCardBook0 = itemView.findViewById(R.id.imgCardBook0);
            imgCardBook1 = itemView.findViewById(R.id.imgCardBook1);
            imgCardBook2 = itemView.findViewById(R.id.imgCardBook2);
            imgCardBook3 = itemView.findViewById(R.id.imgCardBook3);
            imgCardBook4 = itemView.findViewById(R.id.imgCardBook4);
            imgCardBook5 = itemView.findViewById(R.id.imgCardBook5);
            imgCardBook6 = itemView.findViewById(R.id.imgCardBook6);
            imgCardBook7 = itemView.findViewById(R.id.imgCardBook7);
            imgCardBook8 = itemView.findViewById(R.id.imgCardBook8);
            imgCardBook9 = itemView.findViewById(R.id.imgCardBook9);
            txtCardbook_Cardname0 = itemView.findViewById(R.id.txtCardbook_Cardname0);
            txtCardbook_Cardname1 = itemView.findViewById(R.id.txtCardbook_Cardname1);
            txtCardbook_Cardname2 = itemView.findViewById(R.id.txtCardbook_Cardname2);
            txtCardbook_Cardname3 = itemView.findViewById(R.id.txtCardbook_Cardname3);
            txtCardbook_Cardname4 = itemView.findViewById(R.id.txtCardbook_Cardname4);
            txtCardbook_Cardname5 = itemView.findViewById(R.id.txtCardbook_Cardname5);
            txtCardbook_Cardname6 = itemView.findViewById(R.id.txtCardbook_Cardname6);
            txtCardbook_Cardname7 = itemView.findViewById(R.id.txtCardbook_Cardname7);
            txtCardbook_Cardname8 = itemView.findViewById(R.id.txtCardbook_Cardname8);
            txtCardbook_Cardname9 = itemView.findViewById(R.id.txtCardbook_Cardname9);


        }

    }

    //도감에 없는 카드는 안보이게
    private void imgVisibility(String card, ImageView imageView, TextView textView) {
        if (card.isEmpty()) {
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    //획득 못한 카드는 흑백이 기본으로 보이도록 최초 설정
    private void imgDefaultColor(ImageView iv, ColorMatrixColorFilter filter, int check, String name) {
        if (check == 1) {
            setCardBorder(iv, name);
            iv.setColorFilter(null);
        } else {
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            iv.setColorFilter(filter);
        }
    }

    //카드 획득시 카드별 등급에 따라 카드 테두리 색 변경
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

    //클릭시 카드의 각성도 정보가 1이상인 경우 카드가 무조건 획득으로(컬러필터 제거 및 획득)
    private void cardAwake(ImageView iv, String name) {
        iv.setColorFilter(null);
        setCardBorder(iv, name);
    }

    //클릭시 카드를 흑백으로 바꾸는 함수, 데이터베이스 카드 도감 획득 유무도 변경.
    private int imgGrayScale(ImageView iv, ColorMatrixColorFilter filter, String name) {
        int check = 0;
        if (iv.getColorFilter() != filter) {
            iv.setColorFilter(filter);
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            check = 0;
        } else {
            iv.setColorFilter(null);
            setCardBorder(iv, name);
            check = 1;
        }
        return check;
    }

    // DB에 도감을 완성시키면 도감의 배경을 노란색으로 칠해 획득유무를 추가로 알려줌.
    private void isCompleteCardBookBackgroundColor(CardBookInfo cardBookInfo, ConstraintLayout cv) {
        if (cardBookInfo.getHaveCard() == cardBookInfo.getCompleteCardBook())
            cv.setBackgroundColor(parseColor("#FFF4BD"));
        else
            cv.setBackgroundColor(parseColor("#FFFFFF"));
    }

    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(CardBookInfo cardbook_all) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate(ArrayList<CardBookInfo> cardBookInfo) {
        haveStat = new int[]{0, 0, 0};
        haveStatCardBook = new int[]{0, 0, 0};
        haveStatCardBookCount = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardBookInfo.size(); j++) {
                if (cardBookInfo.get(j).getOption().equals(STAT[i]))
                    haveStatCardBook[i]++;
                if (cardBookInfo.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardBookInfo.get(j))) {
                    haveStatCardBookCount[i]++;
                    haveStat[i] += cardBookInfo.get(j).getValue();
                }
            }
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

    //cardBookInfo 갱신을 위한 메소드
    private int getIndex(int id) {
        int index = 0;
        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getId() == id) {
                index = i;
            }
        }
        return index;
    }

    //완성도감 숨기기 기능 체크, 체크해제시 완성도감을 숨기고 재정렬.
    public void getCompleteFilter() {
        filterCardBook = cardBookInfo;
        if (cardBookPage.completeChecked()) {
            completePartRemove();
        }
        if (cardBookPage.checkDefault()) {
            getDefaultSort();
        }
        if (cardBookPage.checkName()) {
            getNameSort();
        }
        if (cardBookPage.checkCompleteness()) {
            getCompletenessSort();
        }
        notifyDataSetChanged();
    }

    //카드 검색 기능
    public Filter getSearchFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (cardBookPage.completeChecked()) {
                    if (charString.isEmpty()) {
                        filterCardBook = baseFilteredCardBook;
                    } else {
                        ArrayList<CardBookInfo> filteringList = new ArrayList<CardBookInfo>();
                        for (int i = 0; i < baseFilteredCardBook.size(); i++) {
                            if (baseFilteredCardBook.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(baseFilteredCardBook.get(i));
                            }
                        }
                        filterCardBook = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCardBook;
                    return filterResults;
                } else {
                    if (charString.isEmpty()) {
                        filterCardBook = cardBookInfo;
                    } else {
                        ArrayList<CardBookInfo> filteringList = new ArrayList<CardBookInfo>();
                        for (int i = 0; i < cardBookInfo.size(); i++) {
                            if (cardBookInfo.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteringList.add(cardBookInfo.get(i));
                            }
                        }
                        filterCardBook = filteringList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCardBook;
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterCardBook = (ArrayList<CardBookInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    //완성되지 않은 카드도감 목록 세팅
    private void setFilteredCardBook() {
        ArrayList<CardBookInfo> filteringList = new ArrayList<>();
        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (!isCompleteCardBook(cardBookInfo.get(i))) {
                filteringList.add(cardBookInfo.get(i));
            }
        }
        baseFilteredCardBook = filteringList;
    }

    //완성도감 지우는 함수
    private void completePartRemove() {
        ArrayList<CardBookInfo> filteringList = new ArrayList<>();
        for (int i = 0; i < filterCardBook.size(); i++) {
            if (!isCompleteCardBook(filterCardBook.get(i))) {
                filteringList.add(filterCardBook.get(i));
            }
        }
        filterCardBook = filteringList;
    }

    //기본 정렬
    public void getDefaultSort() {
        Collections.sort(filterCardBook, new Comparator<CardBookInfo>() {
            @Override
            public int compare(CardBookInfo o1, CardBookInfo o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                else
                    return 1;
            }
        });
        if (cardBookPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //이름 정렬
    public void getNameSort() {
        Collections.sort(filterCardBook);
        if (cardBookPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //완성도 순 정렬
    public void getCompletenessSort() {
        getNameSort();
        Collections.sort(filterCardBook, new Comparator<CardBookInfo>() {
            @Override
            public int compare(CardBookInfo o1, CardBookInfo o2) {
                if (o1.getSubComplete() < o2.getSubComplete()) {
                    return -1;
                } else
                    return 1;
            }
        });
        if (cardBookPage.completeChecked()) {
            completePartRemove();
        }

        notifyDataSetChanged();
    }

    //카드 이미지 배치
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

    //메인 페이지에 변한 결과값이 바로 반영되도록 하는 함수리스트 호출
    private void updateCardBookAndMain() {
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        haveStatUpdate(cardBookInfo);
        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
        cardBookPage.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
    }

}
