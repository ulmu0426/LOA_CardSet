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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardBookAdapter extends RecyclerView.Adapter<CardBookAdapter.ViewHolder> {
    private final String[] STAT = {"치명", "특화", "신속"};
    private int[] haveStat;
    private int[] haveStatCardBook;
    private int[] haveStatCardBookCount;

    public int[] getHaveStat() {
        return haveStat;
    }

    public int[] getHaveStatCardBook() {
        return haveStatCardBook;
    }

    public int[] getHaveStatCardBookCount() {
        return haveStatCardBookCount;
    }

    private static final String CARD_COLUMN_CHECK = "getCard";               //카드 획득 유무
    private final String CARD_BOOK_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String CARD_BOOK_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String CARD_BOOK_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String CARD_BOOK_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String CARD_BOOK_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String CARD_BOOK_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String CARD_BOOK_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String CARD_BOOK_COLUMN_NAME_CARD7_CHECK = "checkCard7";
    private final String CARD_BOOK_COLUMN_NAME_CARD8_CHECK = "checkCard8";
    private final String CARD_BOOK_COLUMN_NAME_CARD9_CHECK = "checkCard9";

    private ArrayList<CardbookInfo> cardbook_all;
    private ArrayList<CardInfo> cardInfo;
    private Context context;
    private LOA_CardDB cardDbHelper;
    private cardBookPage cardBook_page;

    public CardBookAdapter(ArrayList<CardbookInfo> cardbook_all) {
        this.cardbook_all = cardbook_all;
    }

    public CardBookAdapter(Context context, cardBookPage cardBook_page) {
        this.cardbook_all = ((MainActivity) MainActivity.mainContext).cardbook_all;
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        this.context = context;
        cardDbHelper = new LOA_CardDB(context);
        this.cardBook_page = cardBook_page;
        ((MainActivity) MainActivity.mainContext).cardBookUpdate();
        haveStatUpdate(cardbook_all);   //haveCardToCardBookUpdate()로 얻은 정보를 바탕으로 최초 값 획득

    }

    @NonNull
    @Override
    public CardBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardbook, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CardBookAdapter.ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);

        holder.txtCardbookName.setText(cardbook_all.get(position).getName());
        holder.txtCardbookValue.setText(cardbook_all.get(position).getOption() + " + " + cardbook_all.get(position).getValue());
        //이미지뷰 구현할것
        holder.imgCardBook0.setImageResource(R.drawable.card_legend_kadan);
        holder.imgCardBook1.setImageResource(R.drawable.card_legend_ninab);
        holder.imgCardBook2.setImageResource(R.drawable.card_legend_shandi);
        holder.imgCardBook3.setImageResource(R.drawable.card_legend_azena_inanna);
        holder.imgCardBook4.setImageResource(R.drawable.card_legend_bahuntur);
        holder.imgCardBook5.setImageResource(R.drawable.card_legend_silian);
        holder.imgCardBook6.setImageResource(R.drawable.card_legend_wei);
        holder.imgCardBook7.setImageResource(R.drawable.card_legend_dereonaman);
        holder.imgCardBook8.setImageResource(R.drawable.card_legend_kamaine);
        holder.imgCardBook9.setImageResource(R.drawable.card_legend_aman);
        //없는 카드는 흑백(기본), 획득한 카드는 컬러로
        imgDefaultColor(holder.imgCardBook0, filter, cardbook_all.get(position).getCheckCard0());
        imgDefaultColor(holder.imgCardBook1, filter, cardbook_all.get(position).getCheckCard1());
        imgDefaultColor(holder.imgCardBook2, filter, cardbook_all.get(position).getCheckCard2());
        imgDefaultColor(holder.imgCardBook3, filter, cardbook_all.get(position).getCheckCard3());
        imgDefaultColor(holder.imgCardBook4, filter, cardbook_all.get(position).getCheckCard4());
        imgDefaultColor(holder.imgCardBook5, filter, cardbook_all.get(position).getCheckCard5());
        imgDefaultColor(holder.imgCardBook6, filter, cardbook_all.get(position).getCheckCard6());
        imgDefaultColor(holder.imgCardBook7, filter, cardbook_all.get(position).getCheckCard7());
        imgDefaultColor(holder.imgCardBook8, filter, cardbook_all.get(position).getCheckCard8());
        imgDefaultColor(holder.imgCardBook9, filter, cardbook_all.get(position).getCheckCard9());
        //도감에 해당하지 않는 프레임 제거
        imgVisibility(cardbook_all.get(position).getCard2(), holder.imgCardBook2, holder.txtCardbook_Cardname2);
        imgVisibility(cardbook_all.get(position).getCard3(), holder.imgCardBook3, holder.txtCardbook_Cardname3);
        imgVisibility(cardbook_all.get(position).getCard4(), holder.imgCardBook4, holder.txtCardbook_Cardname4);
        imgVisibility(cardbook_all.get(position).getCard5(), holder.imgCardBook5, holder.txtCardbook_Cardname5);
        imgVisibility(cardbook_all.get(position).getCard6(), holder.imgCardBook6, holder.txtCardbook_Cardname6);
        imgVisibility(cardbook_all.get(position).getCard7(), holder.imgCardBook7, holder.txtCardbook_Cardname7);
        imgVisibility(cardbook_all.get(position).getCard8(), holder.imgCardBook8, holder.txtCardbook_Cardname8);
        imgVisibility(cardbook_all.get(position).getCard9(), holder.imgCardBook9, holder.txtCardbook_Cardname9);
        //카드 모두 획득시 백그라운드 컬러 노란색으로
        isCompleteCardBookBackgroundColor(cardbook_all.get(position), holder.cvCardbookBackground);

        //텍스트 구현
        holder.txtCardbook_Cardname0.setText(cardbook_all.get(position).getCard0());
        holder.txtCardbook_Cardname1.setText(cardbook_all.get(position).getCard1());
        holder.txtCardbook_Cardname2.setText(cardbook_all.get(position).getCard2());
        holder.txtCardbook_Cardname3.setText(cardbook_all.get(position).getCard3());
        holder.txtCardbook_Cardname4.setText(cardbook_all.get(position).getCard4());
        holder.txtCardbook_Cardname5.setText(cardbook_all.get(position).getCard5());
        holder.txtCardbook_Cardname6.setText(cardbook_all.get(position).getCard6());
        holder.txtCardbook_Cardname7.setText(cardbook_all.get(position).getCard7());
        holder.txtCardbook_Cardname8.setText(cardbook_all.get(position).getCard8());
        holder.txtCardbook_Cardname9.setText(cardbook_all.get(position).getCard9());

        holder.cvCardbookBackground.setOnClickListener(new View.OnClickListener() { //카드 도감 item 터치시 카드도감 dialog가 뜨며 내용 수정 가능
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.cardbook_name_and_cardlist);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                int pos = positionGet;  //position 값을 넣기 위해 넣은 인수
                //카드도감 이름, 옵션 연결
                TextView txtCardBookName_incardbooknamexmlpage = dialog.findViewById(R.id.txtCardBookName_incardbooknamexmlpage);
                TextView txtCardBookValue_incardbooknamexmlpage = dialog.findViewById(R.id.txtCardBookValue_incardbooknamexmlpage);
                txtCardBookName_incardbooknamexmlpage.setText(cardbook_all.get(pos).getName());
                txtCardBookValue_incardbooknamexmlpage.setText(cardbook_all.get(pos).getOption() + " + " + cardbook_all.get(pos).getValue());
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
                txtCardBookName_CardName0.setText(cardbook_all.get(pos).getCard0());
                txtCardBookName_CardName1.setText(cardbook_all.get(pos).getCard1());
                txtCardBookName_CardName2.setText(cardbook_all.get(pos).getCard2());
                txtCardBookName_CardName3.setText(cardbook_all.get(pos).getCard3());
                txtCardBookName_CardName4.setText(cardbook_all.get(pos).getCard4());
                txtCardBookName_CardName5.setText(cardbook_all.get(pos).getCard5());
                txtCardBookName_CardName6.setText(cardbook_all.get(pos).getCard6());
                txtCardBookName_CardName7.setText(cardbook_all.get(pos).getCard7());
                txtCardBookName_CardName8.setText(cardbook_all.get(pos).getCard8());
                txtCardBookName_CardName9.setText(cardbook_all.get(pos).getCard9());


                ImageView imgCardBookName_CardImg0 = dialog.findViewById(R.id.imgCardBookName_CardImg0);
                ImageView imgCardBookName_CardImg1 = dialog.findViewById(R.id.imgCardBookName_CardImg1);
                ImageView imgCardBookName_CardImg2 = dialog.findViewById(R.id.imgCardBookName_CardImg2);
                ImageView imgCardBookName_CardImg3 = dialog.findViewById(R.id.imgCardBookName_CardImg3);
                ImageView imgCardBookName_CardImg4 = dialog.findViewById(R.id.imgCardBookName_CardImg4);
                ImageView imgCardBookName_CardImg5 = dialog.findViewById(R.id.imgCardBookName_CardImg5);
                ImageView imgCardBookName_CardImg6 = dialog.findViewById(R.id.imgCardBookName_CardImg6);
                ImageView imgCardBookName_CardImg7 = dialog.findViewById(R.id.imgCardBookName_CardImg7);
                ImageView imgCardBookName_CardImg8 = dialog.findViewById(R.id.imgCardBookName_CardImg8);
                ImageView imgCardBookName_CardImg9 = dialog.findViewById(R.id.imgCardBookName_CardImg9);
                //미획득 카드는 흑백(기본), 획득한 카드는 컬러로
                imgDefaultColor(imgCardBookName_CardImg0, filter, cardbook_all.get(pos).getCheckCard0());
                imgDefaultColor(imgCardBookName_CardImg1, filter, cardbook_all.get(pos).getCheckCard1());
                imgDefaultColor(imgCardBookName_CardImg2, filter, cardbook_all.get(pos).getCheckCard2());
                imgDefaultColor(imgCardBookName_CardImg3, filter, cardbook_all.get(pos).getCheckCard3());
                imgDefaultColor(imgCardBookName_CardImg4, filter, cardbook_all.get(pos).getCheckCard4());
                imgDefaultColor(imgCardBookName_CardImg5, filter, cardbook_all.get(pos).getCheckCard5());
                imgDefaultColor(imgCardBookName_CardImg6, filter, cardbook_all.get(pos).getCheckCard6());
                imgDefaultColor(imgCardBookName_CardImg7, filter, cardbook_all.get(pos).getCheckCard7());
                imgDefaultColor(imgCardBookName_CardImg8, filter, cardbook_all.get(pos).getCheckCard8());
                imgDefaultColor(imgCardBookName_CardImg9, filter, cardbook_all.get(pos).getCheckCard9());
                //없는 카드는 안 보이게
                imgVisibility(cardbook_all.get(pos).getCard2(), dialog.findViewById(R.id.imgCardBookName_CardImg2), dialog.findViewById(R.id.txtCardBookName_CardName2));
                imgVisibility(cardbook_all.get(pos).getCard3(), dialog.findViewById(R.id.imgCardBookName_CardImg3), dialog.findViewById(R.id.txtCardBookName_CardName3));
                imgVisibility(cardbook_all.get(pos).getCard4(), dialog.findViewById(R.id.imgCardBookName_CardImg4), dialog.findViewById(R.id.txtCardBookName_CardName4));
                imgVisibility(cardbook_all.get(pos).getCard5(), dialog.findViewById(R.id.imgCardBookName_CardImg5), dialog.findViewById(R.id.txtCardBookName_CardName5));
                imgVisibility(cardbook_all.get(pos).getCard6(), dialog.findViewById(R.id.imgCardBookName_CardImg6), dialog.findViewById(R.id.txtCardBookName_CardName6));
                imgVisibility(cardbook_all.get(pos).getCard7(), dialog.findViewById(R.id.imgCardBookName_CardImg7), dialog.findViewById(R.id.txtCardBookName_CardName7));
                imgVisibility(cardbook_all.get(pos).getCard8(), dialog.findViewById(R.id.imgCardBookName_CardImg8), dialog.findViewById(R.id.txtCardBookName_CardName8));
                imgVisibility(cardbook_all.get(pos).getCard9(), dialog.findViewById(R.id.imgCardBookName_CardImg9), dialog.findViewById(R.id.txtCardBookName_CardName9));

                //컬러필터 흑백
                imgCardBookName_CardImg0.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg0, filter, cardbook_all.get(pos).getCheckCard0());
                        //도감 db 갱신, 도감 arraylist 갱신 (cardCheckX);
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD0_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard0(cardCheck);
                        //업데이트 된 정보 갱신
                        haveStatUpdate(cardbook_all);
                        //카드 db 갱신,카드리스트 갱신
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard0());
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard0())).setGetCard(cardCheck);
                        //카드
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg1, filter, cardbook_all.get(pos).getCheckCard1());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD1_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard1(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard1());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard1())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg2, filter, cardbook_all.get(pos).getCheckCard2());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD2_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard2(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard2());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard2())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg3, filter, cardbook_all.get(pos).getCheckCard3());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD3_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard3(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard3());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard3())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg4.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg4, filter, cardbook_all.get(pos).getCheckCard4());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD4_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard4(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard4());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard4())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg5.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg5, filter, cardbook_all.get(pos).getCheckCard5());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD5_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard5(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard5());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard5())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg6.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg6, filter, cardbook_all.get(pos).getCheckCard6());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD6_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard6(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard6());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard6())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg7.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg7, filter, cardbook_all.get(pos).getCheckCard7());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD7_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard7(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard7());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard7())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg8.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg8, filter, cardbook_all.get(pos).getCheckCard8());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD8_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard8(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard8());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard8())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                imgCardBookName_CardImg9.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg9, filter, cardbook_all.get(pos).getCheckCard9());
                        cardDbHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD9_CHECK, cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCheckCard9(cardCheck);
                        haveStatUpdate(cardbook_all);
                        cardDbHelper.UpdateInfoCardCheck(CARD_COLUMN_CHECK, cardCheck, cardbook_all.get(pos).getCard9());    //카드 db 갱신
                        cardInfo.get(getIndex(cardInfo, cardbook_all.get(pos).getCard9())).setGetCard(cardCheck);             //카드리스트 갱신
                        cardBook_page.setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
                        ((MainActivity) MainActivity.mainContext).setCardBookStatInfo(haveStat);
                        notifyDataSetChanged();
                    }
                });
                dialog.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return cardbook_all.size();    //세 도감 수의 합
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCardbookName;
        private TextView txtCardbookValue;
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
            txtCardbookName = itemView.findViewById(R.id.txtCardbookName);
            txtCardbookValue = itemView.findViewById(R.id.txtCardbookValue);
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

    // DB에 도감을 완성시키면 도감의 배경을 노란색으로 칠해 획득유무를 추가로 알려줌.
    private void isCompleteCardBookBackgroundColor(CardbookInfo cardbook_all, ConstraintLayout cv) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            cv.setBackgroundColor(Color.parseColor("#D0FFE870"));
        else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }


    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(CardbookInfo cardbook_all) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate(ArrayList<CardbookInfo> cardbook_all) {
        haveStat = new int[]{0, 0, 0};
        haveStatCardBook = new int[]{0, 0, 0};
        haveStatCardBookCount = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardbook_all.size(); j++) {
                if (cardbook_all.get(j).getOption().equals(STAT[i]))
                    haveStatCardBook[i]++;
                if (cardbook_all.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardbook_all.get(j))) {
                    haveStatCardBookCount[i]++;
                    haveStat[i] += cardbook_all.get(j).getValue();
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
}
