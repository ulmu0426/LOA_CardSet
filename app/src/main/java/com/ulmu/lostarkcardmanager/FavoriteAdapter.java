package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostarkcardmanager.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    public Context context;
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardSetInfo> cardSetInfo;
    private ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo;
    protected ArrayList<FavoriteCardSetInfo> activationFavoriteCardSet;

    public FavoriteAdapter(Context context) {
        this.context = context;
        favoriteCardSetInfo = ((MainPage) MainPage.mainContext).favoriteCardSetInfo;
        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;
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

        String cardSetName = cardSetMainImg(activationFavoriteCardSet.get(position).getName());

        holder.imgFavoriteCardSet.setImageResource(getCardImg(cardSetName));
        holder.txtFavoriteCardSetName.setText(activationFavoriteCardSet.get(position).getName());
        holder.txtFavoriteCardSetAwake.setText("각성도 합계 : " + activationFavoriteCardSet.get(position).getAwake());

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
        activationFavoriteCardSet.clear();
        for (int i = 0; i < favoriteCardSetInfo.size(); i++) {
            if (favoriteCardSetInfo.get(i).getActivation() == 1) {
                FavoriteCardSetInfo favorite = new FavoriteCardSetInfo();
                favorite = favoriteCardSetInfo.get(i);
                activationFavoriteCardSet.add(favorite);
            }
        }
        notifyDataSetChanged();
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

    //CardSet에서 Awake값 변경시 즉시 메인 페이지에서 Awake 값이 바로 변경되도록 하는 메소드
    public void setAwake(String awakeSetName, int changeAwake) {
        for (int i = 0; i < activationFavoriteCardSet.size(); i++) {
            if (activationFavoriteCardSet.get(i).getName().equals(awakeSetName)) {
                activationFavoriteCardSet.get(i).setAwake(changeAwake);
                break;
            }
        }
        notifyDataSetChanged();
    }

    //DED에서 Awake값 변경시 즉시 메인 페이지에서 Awake 값이 바로 변경되도록 하는 메소드
    public void setAwake(ArrayList<CardSetInfo> awakeSetName) {
        for (int i = 0; i < awakeSetName.size(); i++) {
            for (int j = 0; j < activationFavoriteCardSet.size(); j++) {
                if (activationFavoriteCardSet.get(j).getName().equals(awakeSetName.get(i).getName())) {
                    activationFavoriteCardSet.get(j).setAwake(awakeSetName.get(i).getHaveAwake());
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    private int getCardImg(String cardName) {
        String name = "";
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardName)) {
                name = cardInfo.get(i).getPath();
                break;
            }
        }
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

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
