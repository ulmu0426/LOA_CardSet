package com.example.lostarkcardstatus;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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


    private ArrayList<CardInfo> searchList;
    private EditText editSearch;
    private ImageView imgSearchLegend;
    private Button btnCancer;
    private Button btnOk;
    private ArrayList<CardInfo> cardLegend;
    private ArrayList<CardInfo> cardEpic;
    private ArrayList<CardInfo> cardRare;
    private ArrayList<CardInfo> cardUncommon;
    private ArrayList<CardInfo> cardCommon;
    private ArrayList<CardInfo> cardSpecial;
    private ArrayList<CardInfo> cardInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist);

        cardInfo = ((MainActivity) MainActivity.mainContext).cardInfo;
        settingCardList();

        rvLegendList = findViewById(R.id.rvLegendList);
        rvEpicList = findViewById(R.id.rvEpicList);
        rvRareList = findViewById(R.id.rvRareList);
        rvUncommonList = findViewById(R.id.rvUncommonList);
        rvCommonList = findViewById(R.id.rvCommonList);
        rvSpecialList = findViewById(R.id.rvSpecialList);

        settingCardAdapterL = new SettingCardAdapter(this, LEGEND);
        rvLegendList.setAdapter(settingCardAdapterL);

        Dialog dialogSearch = new Dialog(this, android.R.style.Theme_Material_Light_Dialog);
        dialogSearch.setContentView(R.layout.dialog_search_card);
        editSearch = dialogSearch.findViewById(R.id.editSearchCard);
        btnCancer = dialogSearch.findViewById(R.id.btnCancer);
        btnOk = dialogSearch.findViewById(R.id.btnOK);

        imgSearchLegend = (ImageView) findViewById(R.id.imgSearchLegend);
        imgSearchLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearch.show();

                btnCancer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSearch.cancel();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String text = editSearch.getText().toString();
                                search(text, cardLegend, settingCardAdapterL);
                            }
                        });
                        dialogSearch.cancel();
                    }
                });

            }
        });

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

    private void search(String searchText, ArrayList<CardInfo> lawList, SettingCardAdapter adapter) {
        searchList = new ArrayList<CardInfo>();

        if (searchText.length() == 0) {
            searchList.addAll(lawList);
        } else {
            for (int i = 0; i < lawList.size(); i++) {
                if (lawList.get(i).getName().toLowerCase().contains(searchText)) {
                    searchList.add(lawList.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void settingCardList() {
        cardLegend = new ArrayList<CardInfo>();
        cardEpic = new ArrayList<CardInfo>();
        cardRare = new ArrayList<CardInfo>();
        cardUncommon = new ArrayList<CardInfo>();
        cardCommon = new ArrayList<CardInfo>();
        cardSpecial = new ArrayList<CardInfo>();

        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(LEGEND)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade("");
                cardSpecial.add(ci);
            } else {
                continue;
            }
        }
    }
}
