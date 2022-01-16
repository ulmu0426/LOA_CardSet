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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<CardSetInfo> cardSetInfo;
    private Context context;
    private ArrayList<FavoriteList> favoriteList;

    public MainAdapter(Context context) {
        this.cardSetInfo = ((MainActivity) MainActivity.mainContext).cardSetInfo;
        this.context = context;
        favoriteList = setFavoriteList();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite_cardset, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFavoriteCardSet.setImageResource(R.drawable.card_legend_kadan);
        holder.txtFavoriteCardSetName.setText(favoriteList.get(position).getName());
        holder.txtFavoriteCardSetAwake.setText("각성도 합계 : "+ favoriteList.get(position).getAwake());

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFavoriteCardSet;
        private TextView txtFavoriteCardSetName;
        private TextView txtFavoriteCardSetAwake;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavoriteCardSet = itemView.findViewById(R.id.imgFavoriteCardSet);
            txtFavoriteCardSetName = itemView.findViewById(R.id.txtFavoriteCardSetName);
            txtFavoriteCardSetAwake = itemView.findViewById(R.id.txtFavoriteCardSetAwake);
        }
    }

    //즐겨찾기 리스트 추가
    private ArrayList<FavoriteList> setFavoriteList() {
        FavoriteList favorite = new FavoriteList();
        favoriteList = new ArrayList<FavoriteList>();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (!cardSetInfo.get(i).getFavorite().isEmpty()) {
                favorite.setName(cardSetInfo.get(i).getFavorite());
                favorite.setAwake(cardSetInfo.get(i).getHaveAwake() - awakeException(cardSetInfo.get(i)));
                favoriteList.add(favorite);
            }
        }
        return favoriteList;
    }

    //카드 세트의 수가 세트 발동 조건인 6장보다 많을때, 가장 작은 각성도를 가진 값 찾기.
    private int awakeException(CardSetInfo cardSetInfo) {
        int min = 0;
        int haveCardOver = cardSetInfo.getCheckCard0() + cardSetInfo.getCheckCard1() + cardSetInfo.getCheckCard2()
                + cardSetInfo.getCheckCard3() + cardSetInfo.getCheckCard4() + cardSetInfo.getCheckCard5() + cardSetInfo.getCheckCard6();
        int awake[] = {cardSetInfo.getAwakeCard0(), cardSetInfo.getAwakeCard1(), cardSetInfo.getAwakeCard2()
                , cardSetInfo.getAwakeCard3(), cardSetInfo.getAwakeCard4(), cardSetInfo.getAwakeCard5(), cardSetInfo.getAwakeCard6()};
        if (cardSetInfo.getHaveCard() > haveCardOver) {
            min = awake[0];
            for (int i = 0; i < awake.length; i++) {
                if (min < awake[i]) {
                    min = awake[i];
                }
            }
        } else {
            min = 0;
        }

        return min;
    }
}
