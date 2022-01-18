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

    public Context context;
    private ArrayList<FavoriteList> favoriteList;
    private ArrayList<CardSetInfo> favorite;

    public MainAdapter(Context context) {
        this.context = context;
        favorite = ((MainActivity) MainActivity.mainContext).cardSetInfo;
        favoriteList = ((MainActivity) MainActivity.mainContext).favoriteList;

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

    private String setFavoriteSet(int position){
        String str ="";
        if(favorite.get(position).getFavorite().isEmpty())
            str = favorite.get(position).getFavorite();
        return str;
    }

}
