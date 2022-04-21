package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainPageExtraDmgVPAdapter extends RecyclerView.Adapter<MainPageExtraDmgVPAdapter.ViewHolder> {

    public Context context;

    private final String EXTRA_DMG = " 추가 피해";
    private final String EXTRA_DMG_PERCENT = "%";

    private ArrayList<ArrayList<ExtraDmgInfo>> extraDmgList;
    private ArrayList<String> extraDmgName;
    private ArrayList<Float> extraDmgValue;

    public MainPageExtraDmgVPAdapter(Context context, ArrayList<ArrayList<ExtraDmgInfo>> extraDmgList) {
        this.context = context;
        this.extraDmgList = extraDmgList;
        setExtraDmgName();
        setExtraDmgValue(extraDmgList);
    }

    @NonNull
    @Override
    public MainPageExtraDmgVPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.vp_extra_dmg, parent, false);
        return new MainPageExtraDmgVPAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPageExtraDmgVPAdapter.ViewHolder holder, int position) {
        int pos = position;
        holder.txtBtnExtraDmg.setText(extraDmgName.get(position) + EXTRA_DMG);
        holder.txtExtraDmg.setText(extraDmgValue.get(position) + EXTRA_DMG_PERCENT);

        holder.txtBtnExtraDmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExtraDmgPage.class);
                intent.putExtra("EDName", extraDmgName.get(pos));
                intent.putParcelableArrayListExtra("EDList", extraDmgList.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return extraDmgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBtnExtraDmg;
        private TextView txtExtraDmg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBtnExtraDmg = itemView.findViewById(R.id.txtBtnExtraDmg);
            txtExtraDmg = itemView.findViewById(R.id.txtExtraDmg);
        }

    }

    private void setExtraDmgName() {
        String[] EDName = {"악마", "야수", "정령", "인간", "기계", "불사", "식물", "물질"};
        extraDmgName = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            extraDmgName.add(EDName[i]);
        }
    }

    public void setExtraDmgValue(ArrayList<ArrayList<ExtraDmgInfo>> extraDmgList) {
        extraDmgValue = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            extraDmgValue.add(getEDValue(extraDmgList.get(i)));
        }
        notifyDataSetChanged();
    }

    private float getEDValue(ArrayList<ExtraDmgInfo> extraDmgInfo) {
        float result = 0;
        for (int i = 0; i < extraDmgInfo.size(); i++) {
            result += extraDmgInfo.get(i).getDmgSum();
        }
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        result = Float.parseFloat(df.format(result));
        return result;
    }


}
