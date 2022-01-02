package com.example.lostarkcardstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.ViewHolder>{
    private ArrayList<CardSetInfo> cardSetInfo;
    private Context context;
    private LOA_Card_DB cardDbHelper;

    public CardSetAdapter(ArrayList<CardSetInfo> cardSetInfo, Context context) {
        this.cardSetInfo = cardSetInfo;
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
        holder.txtCardSetName.setText(cardSetInfo.get(position).getName());
        holder.txtCardSetAwake.setText("카드수집 각성합 : "+cardSetInfo.get(position).getHaveAwake()+"각성");
        //이미지뷰 구현할것
        holder.imgCardSet0.setImageResource(R.drawable.card_legend_kadan);
        holder.imgCardSet1.setImageResource(R.drawable.card_legend_ninab);
        holder.imgCardSet2.setImageResource(R.drawable.card_legend_shandi);
        holder.imgCardSet3.setImageResource(R.drawable.card_legend_azena_inanna);
        holder.imgCardSet4.setImageResource(R.drawable.card_legend_bahuntur);
        holder.imgCardSet5.setImageResource(R.drawable.card_legend_silian);
        holder.imgCardSet6.setImageResource(R.drawable.card_legend_wei);
        //텍스트 구현
        holder.txtCardSet_Cardname0.setText(cardSetInfo.get(position).getCard0());
        holder.txtCardSet_Cardname1.setText(cardSetInfo.get(position).getCard1());
        holder.txtCardSet_Cardname2.setText(cardSetInfo.get(position).getCard2());
        holder.txtCardSet_Cardname3.setText(cardSetInfo.get(position).getCard3());
        holder.txtCardSet_Cardname4.setText(cardSetInfo.get(position).getCard4());
        holder.txtCardSet_Cardname5.setText(cardSetInfo.get(position).getCard5());
        holder.txtCardSet_Cardname6.setText(cardSetInfo.get(position).getCard6());

    }

    @Override
    public int getItemCount() {
        return cardSetInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

        }
    }

    //액티비티에서 호출되는 함수. 현재 어댑터에 새로운 아이템을 전달받아 추가하는 목적
    public void addItem(CardSetInfo cb){
        cardSetInfo.add(cb);
    }


}
