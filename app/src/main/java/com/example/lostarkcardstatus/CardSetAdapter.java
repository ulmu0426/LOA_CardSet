package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.content.Context;
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
        //없는 카드 안 보이게
        imgVisibility(cardSetInfo.get(position).getCard2(),holder.imgCardSet2);
        imgVisibility(cardSetInfo.get(position).getCard3(),holder.imgCardSet3);
        imgVisibility(cardSetInfo.get(position).getCard4(),holder.imgCardSet4);
        imgVisibility(cardSetInfo.get(position).getCard5(),holder.imgCardSet5);
        imgVisibility(cardSetInfo.get(position).getCard6(),holder.imgCardSet6);

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
                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);


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
    private void imgVisibility(String card, ImageView imageView){
        if(card.isEmpty())
            imageView.setVisibility(View.INVISIBLE);
        else
            imageView.setVisibility(View.VISIBLE);
    }
    //도감에 없는 옵션은 안보이게 지움
    private void optionVisibility(String option, TextView tv){
        if(option.isEmpty())
            tv.setVisibility(View.GONE);
        else
            tv.setVisibility(View.VISIBLE);
    }


}
