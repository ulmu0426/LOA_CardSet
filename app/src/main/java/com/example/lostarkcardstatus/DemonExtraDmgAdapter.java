package com.example.lostarkcardstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DemonExtraDmgAdapter extends RecyclerView.Adapter<DemonExtraDmgAdapter.ViewHolder>{
    private ArrayList<DemonExtraDmgInfo> demonExtraDmgInfoArrayList;
    private Context context;
    private LOA_Card_DB cardDbHelper;

    public DemonExtraDmgAdapter(ArrayList<DemonExtraDmgInfo> demonExtraDmgInfoArrayList, Context context) {
        this.demonExtraDmgInfoArrayList = demonExtraDmgInfoArrayList;
        this.context = context;
        cardDbHelper = new LOA_Card_DB(context);
    }

    @NonNull
    @Override
    public DemonExtraDmgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_demon_extra_dmg, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DemonExtraDmgAdapter.ViewHolder holder, int position) {
        holder.txtCardbookName_DED.setText(demonExtraDmgInfoArrayList.get(position).getName());
        holder.txtDEDSumValue.setText("악마 계열 피해량 증가 합 : +"+demonExtraDmgInfoArrayList.get(position).getDmgSum(demonExtraDmgInfoArrayList.get(position).getHaveAwake())+"%");
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
        holder.txtDEDCardName0.setText(demonExtraDmgInfoArrayList.get(position).getCard0());
        holder.txtDEDCardName1.setText(demonExtraDmgInfoArrayList.get(position).getCard1());
        holder.txtDEDCardName2.setText(demonExtraDmgInfoArrayList.get(position).getCard2());
        holder.txtDEDCardName3.setText(demonExtraDmgInfoArrayList.get(position).getCard3());
        holder.txtDEDCardName4.setText(demonExtraDmgInfoArrayList.get(position).getCard4());
        holder.txtDEDCardName5.setText(demonExtraDmgInfoArrayList.get(position).getCard5());
        holder.txtDEDCardName6.setText(demonExtraDmgInfoArrayList.get(position).getCard6());
        holder.txtDEDCardName7.setText(demonExtraDmgInfoArrayList.get(position).getCard7());
        holder.txtDEDCardName8.setText(demonExtraDmgInfoArrayList.get(position).getCard8());
        holder.txtDEDCardName9.setText(demonExtraDmgInfoArrayList.get(position).getCard9());
        //조건에 따라 활성화 비활성화 할지 말지 고민 해봐야함
        holder.txtDED_cardCollection.setText(demonExtraDmgInfoArrayList.get(position).getHaveCard() +"장 수집");
        holder.txtDED_cardAwake0.setText(demonExtraDmgInfoArrayList.get(position).getHaveCard()+"장 수집(각성단계 합계"
                +demonExtraDmgInfoArrayList.get(position).getAwake_sum0()+") : 악마 계열 피해량 증가 +"+demonExtraDmgInfoArrayList.get(position).getDmg_p0()+"%");
        holder.txtDED_cardAwake1.setText(demonExtraDmgInfoArrayList.get(position).getHaveCard()+"장 수집(각성단계 합계"
                +demonExtraDmgInfoArrayList.get(position).getAwake_sum1()+") : 악마 계열 피해량 증가 +"+demonExtraDmgInfoArrayList.get(position).getDmg_p1()+"%");
        holder.txtDED_cardAwake2.setText(demonExtraDmgInfoArrayList.get(position).getHaveCard()+"장 수집(각성단계 합계"
                +demonExtraDmgInfoArrayList.get(position).getAwake_sum2()+") : 악마 계열 피해량 증가 +"+demonExtraDmgInfoArrayList.get(position).getDmg_p2()+"%");


    }

    @Override
    public int getItemCount() {
        return demonExtraDmgInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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


    //액티비티에서 호출되는 함수. 현재 어댑터에 새로운 아이템을 전달받아 추가하는 목적
    public void addItem(DemonExtraDmgInfo DED){
        demonExtraDmgInfoArrayList.add(DED);
    }

}
