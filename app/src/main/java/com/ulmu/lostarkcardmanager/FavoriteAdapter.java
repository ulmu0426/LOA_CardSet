package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    public Context context;
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardSetInfo> cardSetInfo;
    protected ArrayList<CardSetInfo> favoriteCardSet; //즐겨찾기 리스트 MainPage에 뿌릴 리스트

    public FavoriteAdapter(Context context) {
        this.context = context;
        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;
        favoriteCardSet = new ArrayList<>();
        updateFavoriteCardSet();
        Collections.sort(favoriteCardSet);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite_cardset, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cardSetName = cardSetMainImg(favoriteCardSet.get(position).getName());

        holder.imgFavoriteCardSet.setImageResource(getCardImg(cardSetName));
        holder.txtFavoriteCardSetName.setText(favoriteCardSet.get(position).getName());
        holder.txtFavoriteCardSetAwake.setText("각성도 합계 : " + favoriteCardSet.get(position).getHaveAwake());

    }

    @Override
    public int getItemCount() {
        return favoriteCardSet.size();
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

    // 실행시 최초 MainPage에 뿌릴 즐겨찾기 list update
    public void updateFavoriteCardSet() {
        favoriteCardSet.clear();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getFavorite()) {
                CardSetInfo favorite = cardSetInfo.get(i);
                favoriteCardSet.add(favorite);
            }
        }
        notifyDataSetChanged();
    }

    //즐겨찾기 해제
    public void removeItem(CardSetInfo item) {
        int position = 0;
        for (int i = 0; i < favoriteCardSet.size(); i++) {
            if (favoriteCardSet.get(i).getName().equals(item.getName())) {
                favoriteCardSet.remove(i);
                position = i;
            }
        }
        notifyItemRemoved(position);
    }

    //즐겨찾기 추가
    public void addItem(CardSetInfo item) {
        favoriteCardSet.add(item);
        Collections.sort(favoriteCardSet);
        notifyDataSetChanged();
    }

    //카드 이미지 세팅 함수
    private int getCardImg(String cardName) {
        String name = "";
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardName)) {
                name = cardInfo.get(i).getPath();
                break;
            }
        }
        int imageResource = context.getResources().getIdentifier(name, "drawable" , context.getPackageName());

        return imageResource;
    }

    private String cardSetMainImg(String cardSetName) {
        String mainImgName = "";
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals(cardSetName)) {
                mainImgName = cardSetInfo.get(i).getCard0();
                break;
            }
        }
        return mainImgName;
    }

}
