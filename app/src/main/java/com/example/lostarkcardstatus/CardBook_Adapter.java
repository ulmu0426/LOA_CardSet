package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardBook_Adapter extends RecyclerView.Adapter<CardBook_Adapter.ViewHolder> {

    private ArrayList<Cardbook_All> cardbook_all;
    private Context context;
    private LOA_Card_DB cardDbHelper;
    public int haveCritical;
    public int haveSpeciality;
    public int haveAgility;
    private final String CRITICAL = "치명";
    private final String AGILITY = "신속";
    private final String SPECIALITY = "특화";

    public CardBook_Adapter(ArrayList<Cardbook_All> cardbook_all, Context context) {
        this.cardbook_all = cardbook_all;
        this.context = context;
        cardDbHelper = new LOA_Card_DB(context);
    }

    @NonNull
    @Override
    public CardBook_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardbook, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CardBook_Adapter.ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

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
        imgDefaultColor(holder.imgCardBook0, filter, cardbook_all.get(position).getCard0_check());
        imgDefaultColor(holder.imgCardBook1, filter, cardbook_all.get(position).getCard1_check());
        imgDefaultColor(holder.imgCardBook2, filter, cardbook_all.get(position).getCard2_check());
        imgDefaultColor(holder.imgCardBook3, filter, cardbook_all.get(position).getCard3_check());
        imgDefaultColor(holder.imgCardBook4, filter, cardbook_all.get(position).getCard4_check());
        imgDefaultColor(holder.imgCardBook5, filter, cardbook_all.get(position).getCard5_check());
        imgDefaultColor(holder.imgCardBook6, filter, cardbook_all.get(position).getCard6_check());
        imgDefaultColor(holder.imgCardBook7, filter, cardbook_all.get(position).getCard7_check());
        imgDefaultColor(holder.imgCardBook8, filter, cardbook_all.get(position).getCard8_check());
        imgDefaultColor(holder.imgCardBook9, filter, cardbook_all.get(position).getCard9_check());
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
                int pos = positionGet;  //position 값을 넣기 위해 넣은 인수
                //카드도감 이름, 옵션 연결
                TextView txtCardBookName_incardbooknamexmlpage = dialog.findViewById(R.id.txtCardBookName_incardbooknamexmlpage);
                TextView txtCardBookValue_incardbooknamexmlpage = dialog.findViewById(R.id.txtCardBookValue_incardbooknamexmlpage);
                txtCardBookName_incardbooknamexmlpage.setText(cardbook_all.get(pos).getName());
                txtCardBookValue_incardbooknamexmlpage.setText(cardbook_all.get(pos).getOption() + " + " + cardbook_all.get(pos).getValue());

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
                imgDefaultColor(imgCardBookName_CardImg0, filter, cardbook_all.get(pos).getCard0_check());
                imgDefaultColor(imgCardBookName_CardImg1, filter, cardbook_all.get(pos).getCard1_check());
                imgDefaultColor(imgCardBookName_CardImg2, filter, cardbook_all.get(pos).getCard2_check());
                imgDefaultColor(imgCardBookName_CardImg3, filter, cardbook_all.get(pos).getCard3_check());
                imgDefaultColor(imgCardBookName_CardImg4, filter, cardbook_all.get(pos).getCard4_check());
                imgDefaultColor(imgCardBookName_CardImg5, filter, cardbook_all.get(pos).getCard5_check());
                imgDefaultColor(imgCardBookName_CardImg6, filter, cardbook_all.get(pos).getCard6_check());
                imgDefaultColor(imgCardBookName_CardImg7, filter, cardbook_all.get(pos).getCard7_check());
                imgDefaultColor(imgCardBookName_CardImg8, filter, cardbook_all.get(pos).getCard8_check());
                imgDefaultColor(imgCardBookName_CardImg9, filter, cardbook_all.get(pos).getCard9_check());
                //없는 카드는 안 보이게
                imgVisibility(cardbook_all.get(pos).getCard2(), dialog.findViewById(R.id.imgCardBookName_CardImg2), dialog.findViewById(R.id.txtCardBookName_Cardname2));
                imgVisibility(cardbook_all.get(pos).getCard3(), dialog.findViewById(R.id.imgCardBookName_CardImg3), dialog.findViewById(R.id.txtCardBookName_Cardname3));
                imgVisibility(cardbook_all.get(pos).getCard4(), dialog.findViewById(R.id.imgCardBookName_CardImg4), dialog.findViewById(R.id.txtCardBookName_Cardname4));
                imgVisibility(cardbook_all.get(pos).getCard5(), dialog.findViewById(R.id.imgCardBookName_CardImg5), dialog.findViewById(R.id.txtCardBookName_Cardname5));
                imgVisibility(cardbook_all.get(pos).getCard6(), dialog.findViewById(R.id.imgCardBookName_CardImg6), dialog.findViewById(R.id.txtCardBookName_Cardname6));
                imgVisibility(cardbook_all.get(pos).getCard7(), dialog.findViewById(R.id.imgCardBookName_CardImg7), dialog.findViewById(R.id.txtCardBookName_Cardname7));
                imgVisibility(cardbook_all.get(pos).getCard8(), dialog.findViewById(R.id.imgCardBookName_CardImg8), dialog.findViewById(R.id.txtCardBookName_Cardname8));
                imgVisibility(cardbook_all.get(pos).getCard9(), dialog.findViewById(R.id.imgCardBookName_CardImg9), dialog.findViewById(R.id.txtCardBookName_Cardname9));

                //컬러필터 흑백
                imgCardBookName_CardImg0.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg0, filter, cardbook_all.get(pos).getCard0_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card0_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard0_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg1, filter, cardbook_all.get(pos).getCard1_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card1_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard1_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg2, filter, cardbook_all.get(pos).getCard2_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card2_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard2_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg3, filter, cardbook_all.get(pos).getCard3_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card3_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard3_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg4.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg4, filter, cardbook_all.get(pos).getCard4_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card4_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard4_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg5.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg5, filter, cardbook_all.get(pos).getCard5_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card5_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard5_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg6.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg6, filter, cardbook_all.get(pos).getCard6_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card6_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard6_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg7.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg7, filter, cardbook_all.get(pos).getCard7_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card7_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard7_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg8.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg8, filter, cardbook_all.get(pos).getCard8_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card8_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard8_check(cardCheck);
                        notifyItemChanged(pos);
                    }
                });
                imgCardBookName_CardImg9.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int cardCheck = imgGrayScale(imgCardBookName_CardImg9, filter, cardbook_all.get(pos).getCard9_check());
                        cardDbHelper.UpdateInfoCardBookCard("cardbook_all", "card9_check", cardCheck, cardbook_all.get(pos).getId());
                        cardbook_all.get(pos).setCard9_check(cardCheck);
                        notifyItemChanged(pos);
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
            cvCardbookBackground = itemView.findViewById(R.id.cvCardbookBackground);
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

    //획득 못한 카드는 흑백이 기본으로 보이도록
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

    //수집 카드 합
    public int haveCard(Cardbook_All cardbook_all) {
        int sum = cardbook_all.getCard0_check() + cardbook_all.getCard1_check() + cardbook_all.getCard2_check() + cardbook_all.getCard3_check() + cardbook_all.getCard4_check()
                + cardbook_all.getCard5_check() + cardbook_all.getCard6_check() + cardbook_all.getCard7_check() + cardbook_all.getCard8_check() + cardbook_all.getCard9_check();
        return sum;
    }

    // DB에 도감을 완성시키면 도감의 배경을 노란색으로 칠해 획득유무를 추가로 알려줌.
    private void isCompleteCardBookBackgroundColor(Cardbook_All cardbook_all, ConstraintLayout cv) {
        if (haveCard(cardbook_all) == cardbook_all.getCompleteCardBook())
            cv.setBackgroundColor(Color.parseColor("#D0FFE870"));
        else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(Cardbook_All cardbook_all){
        if (haveCard(cardbook_all) == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

}
