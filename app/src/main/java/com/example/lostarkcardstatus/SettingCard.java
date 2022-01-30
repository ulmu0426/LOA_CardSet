package com.example.lostarkcardstatus;

import android.graphics.ColorFilter;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.EOFException;

public class SettingCard extends AppCompatActivity {

    private RecyclerView rvLegendList;
    private RecyclerView rvEpicList;
    private RecyclerView rvRareList;
    private RecyclerView rvUncommonList;
    private RecyclerView rvCommonList;
    private RecyclerView rvSpecialList;

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";

    private SettingCardAdapter2 settingCardAdapterL;
    private SettingCardAdapter2 settingCardAdapterE;
    private SettingCardAdapter2 settingCardAdapterR;
    private SettingCardAdapter2 settingCardAdapterU;
    private SettingCardAdapter2 settingCardAdapterC;
    private SettingCardAdapter2 settingCardAdapterS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist_2);

        rvLegendList = findViewById(R.id.rvLegendList);
        rvEpicList = findViewById(R.id.rvEpicList);
        rvRareList = findViewById(R.id.rvRareList);
        rvUncommonList = findViewById(R.id.rvUncommonList);
        rvCommonList = findViewById(R.id.rvCommonList);
        rvSpecialList = findViewById(R.id.rvSpecialList);

        settingCardAdapterL = new SettingCardAdapter2(this, LEGEND);
        rvLegendList.setAdapter(settingCardAdapterL);

        settingCardAdapterE = new SettingCardAdapter2(this, EPIC);
        rvEpicList.setAdapter(settingCardAdapterE);

        settingCardAdapterR = new SettingCardAdapter2(this, RARE);
        rvRareList.setAdapter(settingCardAdapterR);

        settingCardAdapterU = new SettingCardAdapter2(this, UNCOMMON);
        rvUncommonList.setAdapter(settingCardAdapterU);

        settingCardAdapterC = new SettingCardAdapter2(this, COMMON);
        rvCommonList.setAdapter(settingCardAdapterC);

        settingCardAdapterS = new SettingCardAdapter2(this, SPECIAL);
        rvSpecialList.setAdapter(settingCardAdapterS);


    }
}
