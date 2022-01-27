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

    private SettingCardAdapter settingCardAdapterL;
    private SettingCardAdapter settingCardAdapterE;
    private SettingCardAdapter settingCardAdapterR;
    private SettingCardAdapter settingCardAdapterU;
    private SettingCardAdapter settingCardAdapterC;
    private SettingCardAdapter settingCardAdapterS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist_page);

        rvLegendList = findViewById(R.id.rvLegendList);
        rvEpicList = findViewById(R.id.rvEpicList);
        rvRareList = findViewById(R.id.rvRareList);
        rvUncommonList = findViewById(R.id.rvUncommonList);
        rvCommonList = findViewById(R.id.rvCommonList);
        rvSpecialList = findViewById(R.id.rvSpecialList);

        settingCardAdapterL = new SettingCardAdapter(this, LEGEND);
        rvLegendList.setAdapter(settingCardAdapterL);

        settingCardAdapterE = new SettingCardAdapter(this, EPIC);
        rvEpicList.setAdapter(settingCardAdapterE);

        settingCardAdapterR = new SettingCardAdapter(this, RARE);
        rvRareList.setAdapter(settingCardAdapterR);

        settingCardAdapterU = new SettingCardAdapter(this, UNCOMMON);
        rvUncommonList.setAdapter(settingCardAdapterU);

        settingCardAdapterC = new SettingCardAdapter(this, COMMON);
        rvCommonList.setAdapter(settingCardAdapterC);

        settingCardAdapterS = new SettingCardAdapter(this, SPECIAL);
        rvSpecialList.setAdapter(settingCardAdapterS);


    }
}
