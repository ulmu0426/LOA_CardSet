package com.example.lostarkcardstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public Context context;
    private ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo;
    protected ArrayList<FavoriteCardSetInfo> activationFavoriteCardSet;

    public MainAdapter(Context context) {
        this.context = context;
        favoriteCardSetInfo = ((MainActivity) MainActivity.mainContext).favoriteCardSetInfo;
        activationFavoriteCardSet = new ArrayList<FavoriteCardSetInfo>();
        updateActivationFavoriteCardSet();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite_cardset, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteCardSetInfo item = activationFavoriteCardSet.get(position);

        holder.imgFavoriteCardSet.setImageResource(R.drawable.card_legend_kadan);
        holder.txtFavoriteCardSetName.setText(item.getName());
        holder.txtFavoriteCardSetAwake.setText("각성도 합계 : " + item.getAwake());

    }

    @Override
    public int getItemCount() {
        return activationFavoriteCardSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cvFavoriteCardSet;
        private ImageView imgFavoriteCardSet;
        private TextView txtFavoriteCardSetName;
        private TextView txtFavoriteCardSetAwake;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvFavoriteCardSet = itemView.findViewById(R.id.cvFavoriteCardSet);
            imgFavoriteCardSet = itemView.findViewById(R.id.imgFavoriteCardSet);
            txtFavoriteCardSetName = itemView.findViewById(R.id.txtFavoriteCardSetName);
            txtFavoriteCardSetAwake = itemView.findViewById(R.id.txtFavoriteCardSetAwake);
        }
    }

    public void updateActivationFavoriteCardSet() {
        for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
            if (favoriteCardSetInfo.get(i).getActivation() == 1) {
                FavoriteCardSetInfo favorite = new FavoriteCardSetInfo();
                favorite = favoriteCardSetInfo.get(i);
                activationFavoriteCardSet.add(favorite);
            }
        }
    }

    public void addItem(FavoriteCardSetInfo item) {
        activationFavoriteCardSet.add(0, item);
        notifyItemInserted(0);
    }

    public void removeItem(FavoriteCardSetInfo item) {
        int position = 0;
        for (int i = 0; i < activationFavoriteCardSet.size(); i++) {
            if (activationFavoriteCardSet.get(i).getName().equals(item.getName())) {
                activationFavoriteCardSet.remove(i);
                position = i;
            }
        }
        notifyItemRemoved(position);
    }


}
