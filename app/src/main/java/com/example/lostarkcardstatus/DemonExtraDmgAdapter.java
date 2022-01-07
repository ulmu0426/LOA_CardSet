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

public class DemonExtraDmgAdapter extends RecyclerView.Adapter<DemonExtraDmgAdapter.ViewHolder> {
    private ArrayList<DemonExtraDmgInfo> DEDInfo;
    private Context context;
    private DemonExtraDmg_page DED_page;
    private ArrayList<CardInfo> cardInfo;
    private LOA_Card_DB cardDbHelper;
    private float haveDED;
    private int haveDEDBookCount;
    private int haveDEDBook;

    public DemonExtraDmgAdapter(Context context, DemonExtraDmg_page demonExtraDmg_page) {
        this.DEDInfo = ((MainActivity) MainActivity.mainContext).DEDInfo;
        this.context = context;
        cardDbHelper = new LOA_Card_DB(context);
        this.DED_page = demonExtraDmg_page;
        this.cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
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

        holder.txtCardbookName_DED.setText(DEDInfo.get(position).getName());
        holder.txtDEDSumValue.setText("악마 계열 피해량 증가 합 : +" + DEDInfo.get(position).getDmgSum(DEDInfo.get(position).getHaveAwake()) + "%");
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

        //
        holder.txtDEDCardName0.setText(DEDInfo.get(position).getCard0());
        holder.txtDEDCardName1.setText(DEDInfo.get(position).getCard1());
        holder.txtDEDCardName2.setText(DEDInfo.get(position).getCard2());
        holder.txtDEDCardName3.setText(DEDInfo.get(position).getCard3());
        holder.txtDEDCardName4.setText(DEDInfo.get(position).getCard4());
        holder.txtDEDCardName5.setText(DEDInfo.get(position).getCard5());
        holder.txtDEDCardName6.setText(DEDInfo.get(position).getCard6());
        holder.txtDEDCardName7.setText(DEDInfo.get(position).getCard7());
        holder.txtDEDCardName8.setText(DEDInfo.get(position).getCard8());
        holder.txtDEDCardName9.setText(DEDInfo.get(position).getCard9());
        //없는 카드 안 보이게
        imgVisibility(DEDInfo.get(position).getCard2(), holder.imgDEDCard2, holder.txtDEDCardName2);
        imgVisibility(DEDInfo.get(position).getCard3(), holder.imgDEDCard3, holder.txtDEDCardName3);
        imgVisibility(DEDInfo.get(position).getCard4(), holder.imgDEDCard4, holder.txtDEDCardName4);
        imgVisibility(DEDInfo.get(position).getCard5(), holder.imgDEDCard5, holder.txtDEDCardName5);
        imgVisibility(DEDInfo.get(position).getCard6(), holder.imgDEDCard6, holder.txtDEDCardName6);
        imgVisibility(DEDInfo.get(position).getCard7(), holder.imgDEDCard7, holder.txtDEDCardName7);
        imgVisibility(DEDInfo.get(position).getCard8(), holder.imgDEDCard8, holder.txtDEDCardName8);
        imgVisibility(DEDInfo.get(position).getCard9(), holder.imgDEDCard9, holder.txtDEDCardName9);
        //조건에 따라 활성화 비활성화 할지 말지 고민 해봐야함
        holder.txtDED_cardCollection.setText(DEDInfo.get(position).getCompleteDEDBook() + "장 수집");
        holder.txtDED_cardAwake0.setText(DEDInfo.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + DEDInfo.get(position).getAwake_sum0() + ") : 악마 계열 피해량 증가 +" + DEDInfo.get(position).getDmg_p0() + "%");
        holder.txtDED_cardAwake1.setText(DEDInfo.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + DEDInfo.get(position).getAwake_sum1() + ") : 악마 계열 피해량 증가 +" + DEDInfo.get(position).getDmg_p1() + "%");
        holder.txtDED_cardAwake2.setText(DEDInfo.get(position).getCompleteDEDBook() + "장 수집(각성단계 합계"
                + DEDInfo.get(position).getAwake_sum2() + ") : 악마 계열 피해량 증가 +" + DEDInfo.get(position).getDmg_p2() + "%");

        holder.cvDemonExtraDmgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.demon_extra_dmg_detail);
                int pos = positionGet;

                TextView txtDEDCardBookName = dialog.findViewById(R.id.txtDEDCardBookName);
                TextView txtDED_AwakeValue = dialog.findViewById(R.id.txtDED_AwakeValue);
                txtDEDCardBookName.setText(DEDInfo.get(pos).getName());
                txtDED_AwakeValue.setText("각성 합계 : " + DEDInfo.get(pos).getHaveAwake());

                ImageView imgDED_Detail_Card0 = dialog.findViewById(R.id.imgDED_Detail_Card0);
                ImageView imgDED_Detail_Card1 = dialog.findViewById(R.id.imgDED_Detail_Card1);
                ImageView imgDED_Detail_Card2 = dialog.findViewById(R.id.imgDED_Detail_Card2);
                ImageView imgDED_Detail_Card3 = dialog.findViewById(R.id.imgDED_Detail_Card3);
                ImageView imgDED_Detail_Card4 = dialog.findViewById(R.id.imgDED_Detail_Card4);
                ImageView imgDED_Detail_Card5 = dialog.findViewById(R.id.imgDED_Detail_Card5);
                ImageView imgDED_Detail_Card6 = dialog.findViewById(R.id.imgDED_Detail_Card6);
                ImageView imgDED_Detail_Card7 = dialog.findViewById(R.id.imgDED_Detail_Card7);
                ImageView imgDED_Detail_Card8 = dialog.findViewById(R.id.imgDED_Detail_Card8);
                ImageView imgDED_Detail_Card9 = dialog.findViewById(R.id.imgDED_Detail_Card9);

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

                TextView txtDED_Detail_Card0 = dialog.findViewById(R.id.txtDED_Detail_Card0);
                TextView txtDED_Detail_Card1 = dialog.findViewById(R.id.txtDED_Detail_Card1);
                TextView txtDED_Detail_Card2 = dialog.findViewById(R.id.txtDED_Detail_Card2);
                TextView txtDED_Detail_Card3 = dialog.findViewById(R.id.txtDED_Detail_Card3);
                TextView txtDED_Detail_Card4 = dialog.findViewById(R.id.txtDED_Detail_Card4);
                TextView txtDED_Detail_Card5 = dialog.findViewById(R.id.txtDED_Detail_Card5);
                TextView txtDED_Detail_Card6 = dialog.findViewById(R.id.txtDED_Detail_Card6);
                TextView txtDED_Detail_Card7 = dialog.findViewById(R.id.txtDED_Detail_Card7);
                TextView txtDED_Detail_Card8 = dialog.findViewById(R.id.txtDED_Detail_Card8);
                TextView txtDED_Detail_Card9 = dialog.findViewById(R.id.txtDED_Detail_Card9);

                txtDED_Detail_Card0.setText(DEDInfo.get(pos).getCard0());
                txtDED_Detail_Card1.setText(DEDInfo.get(pos).getCard1());
                txtDED_Detail_Card2.setText(DEDInfo.get(pos).getCard2());
                txtDED_Detail_Card3.setText(DEDInfo.get(pos).getCard3());
                txtDED_Detail_Card4.setText(DEDInfo.get(pos).getCard4());
                txtDED_Detail_Card5.setText(DEDInfo.get(pos).getCard5());
                txtDED_Detail_Card6.setText(DEDInfo.get(pos).getCard6());
                txtDED_Detail_Card7.setText(DEDInfo.get(pos).getCard7());
                txtDED_Detail_Card8.setText(DEDInfo.get(pos).getCard8());
                txtDED_Detail_Card9.setText(DEDInfo.get(pos).getCard9());

                //없는 카드 안 보이게
                imgVisibility(DEDInfo.get(pos).getCard2(), imgDED_Detail_Card2, txtDED_Detail_Card2);
                imgVisibility(DEDInfo.get(pos).getCard3(), imgDED_Detail_Card3, txtDED_Detail_Card3);
                imgVisibility(DEDInfo.get(pos).getCard4(), imgDED_Detail_Card4, txtDED_Detail_Card4);
                imgVisibility(DEDInfo.get(pos).getCard5(), imgDED_Detail_Card5, txtDED_Detail_Card5);
                imgVisibility(DEDInfo.get(pos).getCard6(), imgDED_Detail_Card6, txtDED_Detail_Card6);
                imgVisibility(DEDInfo.get(pos).getCard7(), imgDED_Detail_Card7, txtDED_Detail_Card7);
                imgVisibility(DEDInfo.get(pos).getCard8(), imgDED_Detail_Card8, txtDED_Detail_Card8);
                imgVisibility(DEDInfo.get(pos).getCard9(), imgDED_Detail_Card9, txtDED_Detail_Card9);

                imgDED_Detail_Card0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cardCheck = imgGrayScale(imgDED_Detail_Card0, filter, DEDInfo.get(pos).getCard0_check());
                        cardDbHelper.UpdateInfoCardBookCard("demon_extra_dmg", "card0_check", cardCheck, DEDInfo.get(pos).getId());
                        DEDInfo.get(pos).setCard0_check(cardCheck);
                        haveValueUpdate(DEDInfo, cardInfo);
                        DED_page.setDED(haveDED, haveDEDBookCount, haveDEDBook);

                        notifyDataSetChanged();
                    }
                });
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return DEDInfo.size();
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
    public int haveCard(DemonExtraDmgInfo DEDInfo) {
        int sum = DEDInfo.getCard0_check() + DEDInfo.getCard1_check() + DEDInfo.getCard2_check() + DEDInfo.getCard3_check() + DEDInfo.getCard4_check()
                + DEDInfo.getCard5_check() + DEDInfo.getCard6_check() + DEDInfo.getCard7_check() + DEDInfo.getCard8_check() + DEDInfo.getCard9_check();
        return sum;
    }

    // DB에 도감을 완성시키면 도감의 배경을 노란색으로 칠해 획득유무를 추가로 알려줌.
    private void isCompleteCardBookBackgroundColor(DemonExtraDmgInfo DEDInfo, ConstraintLayout cv) {
        if (haveCard(DEDInfo) == DEDInfo.getCompleteDEDBook())
            cv.setBackgroundColor(Color.parseColor("#D0FFE870"));
        else
            cv.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(DemonExtraDmgInfo DEDInfo) {
        if (haveCard(DEDInfo) == DEDInfo.getCompleteDEDBook())
            return true;
        else
            return false;
    }

    // 도감을 완성 시키고 각성도 합
    private void haveValueUpdate(ArrayList<DemonExtraDmgInfo> DEDInfo, ArrayList<CardInfo> cardInfo) {
        haveDED = 0;
        for (int j = 0; j < DEDInfo.size(); j++) {

        }
    }

}
