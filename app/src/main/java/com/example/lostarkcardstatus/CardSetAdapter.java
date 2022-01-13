package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.ViewHolder> {
    private ArrayList<CardSetInfo> cardSetInfo;
    private ArrayList<CardInfo> cardInfo;
    private Context context;
    private LOA_Card_DB cardDbHelper;
    private final String CARDSET_AWAKE = "각성 : ";
    private final String CARDSET_CARD_NUM = "보유 : ";

    public CardSetAdapter(ArrayList<CardSetInfo> cardSetInfo, Context context) {
        this.cardSetInfo = ((MainActivity) MainActivity.mainContext).cardSetInfo;
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        this.context = context;
        cardDbHelper = new LOA_Card_DB(context);
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
        holder.txtCardSetAwake.setText("카드수집 각성합 : " + cardSetInfo.get(position).getHaveAwake() + "각성");
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
                TextView txtCardSetName_Detail = dialog.findViewById(R.id.txtCardSetName_Detail);
                TextView txtCardSetAwake_Detail = dialog.findViewById(R.id.txtCardSetAwake_Detail);

                ImageView imgCardSetDetail0 = dialog.findViewById(R.id.imgCardSetDetail0);
                ImageView imgCardSetDetail1 = dialog.findViewById(R.id.imgCardSetDetail1);
                ImageView imgCardSetDetail2 = dialog.findViewById(R.id.imgCardSetDetail2);
                ImageView imgCardSetDetail3 = dialog.findViewById(R.id.imgCardSetDetail3);
                ImageView imgCardSetDetail4 = dialog.findViewById(R.id.imgCardSetDetail4);
                ImageView imgCardSetDetail5 = dialog.findViewById(R.id.imgCardSetDetail5);
                ImageView imgCardSetDetail6 = dialog.findViewById(R.id.imgCardSetDetail6);
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

                txtCardSetName_Detail.setText(cardSetInfo.get(pos).getName());
                txtCardSetAwake_Detail.setText(cardSetInfo.get(pos).getHaveAwake());

                txtCardSetName0.setText(cardSetInfo.get(pos).getCard0());
                txtCardSetName1.setText(cardSetInfo.get(pos).getCard1());
                txtCardSetName2.setText(cardSetInfo.get(pos).getCard2());
                txtCardSetName3.setText(cardSetInfo.get(pos).getCard3());
                txtCardSetName4.setText(cardSetInfo.get(pos).getCard4());
                txtCardSetName5.setText(cardSetInfo.get(pos).getCard5());
                txtCardSetName6.setText(cardSetInfo.get(pos).getCard6());

                txtHaveAwakeHaveCard0.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard0() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard0())).getHaveCard());
                txtHaveAwakeHaveCard1.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard1() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard1())).getHaveCard());
                txtHaveAwakeHaveCard2.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard2() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard2())).getHaveCard());
                txtHaveAwakeHaveCard3.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard3() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard3())).getHaveCard());
                txtHaveAwakeHaveCard4.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard4() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard4())).getHaveCard());
                txtHaveAwakeHaveCard5.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard5() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard5())).getHaveCard());
                txtHaveAwakeHaveCard6.setText(CARDSET_AWAKE + cardSetInfo.get(pos).getAwakeCard6() + "\n" + CARDSET_CARD_NUM + cardSetInfo.get(getIndex(cardInfo, cardSetInfo.get(pos).getCard6())).getHaveCard());

                imgCardSetDetail0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imgCardSetDetail6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
