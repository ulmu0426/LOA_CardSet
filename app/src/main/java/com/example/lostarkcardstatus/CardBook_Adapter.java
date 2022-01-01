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

public class CardBook_Adapter extends RecyclerView.Adapter<CardBook_Adapter.ViewHolder> {

    private ArrayList<Cardbook> cardbookAgility;        //신속도감
    private ArrayList<Cardbook> cardbookSpeciality;     //특화도감
    private ArrayList<Cardbook> cardbookCritical;       //치명도감
    private ArrayList<Cardbook_All> cardbook_all;
    private Context context;
    private LOA_Card_DB cardDbHelper;

    public CardBook_Adapter(ArrayList<Cardbook_All> cardbook_all, Context context) {
        //this.cardbookAgility = cardbookAgility;
        //this.cardbookSpeciality = cardbookSpeciality;
        //this.cardbookCritical = cardbookCritical;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

    //액티비티에서 호출되는 함수. 현재 어댑터에 새로운 아이템을 전달받아 추가하는 목적
    public void addItem(Cardbook cb){
        cardbookAgility.add(cb);
    }

}
