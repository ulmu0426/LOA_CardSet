package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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


    private TextView txtCardLegend;
    private EditText editSearchCardL;
    private ImageView imgSearchLegend;

    private TextView txtCardEpic;
    private EditText editSearchCardE;
    private ImageView imgSearchEpic;

    private TextView txtCardRare;
    private EditText editSearchCardR;
    private ImageView imgSearchRare;

    private TextView txtCardUncommon;
    private EditText editSearchCardU;
    private ImageView imgSearchUncommon;

    private TextView txtCardCommon;
    private EditText editSearchCardC;
    private ImageView imgSearchCommon;

    private TextView txtCardSpecial;
    private EditText editSearchCardS;
    private ImageView imgSearchSpecial;


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

        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        settingCardList();

        rvLegendList = findViewById(R.id.rvLegendList);
        rvEpicList = findViewById(R.id.rvEpicList);
        rvRareList = findViewById(R.id.rvRareList);
        rvUncommonList = findViewById(R.id.rvUncommonList);
        rvCommonList = findViewById(R.id.rvCommonList);
        rvSpecialList = findViewById(R.id.rvSpecialList);

        settingCardAdapterL = new SettingCardAdapter(this, cardLegend);
        rvLegendList.setAdapter(settingCardAdapterL);

        txtCardLegend = (TextView) findViewById(R.id.txtCardLegend);
        editSearchCardL = (EditText) findViewById(R.id.editSearchCardL);

        editSearchCardL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterL.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchLegend = (ImageView) findViewById(R.id.imgSearchLegend);
        imgSearchLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardL.getVisibility() == View.INVISIBLE) {
                    editSearchCardL.setVisibility(View.VISIBLE);
                    txtCardLegend.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardL.setVisibility(View.INVISIBLE);
                    txtCardLegend.setVisibility(View.VISIBLE);
                }
            }
        });

        settingCardAdapterE = new SettingCardAdapter(this, cardEpic);
        rvEpicList.setAdapter(settingCardAdapterE);

        txtCardEpic = (TextView) findViewById(R.id.txtCardEpic);
        editSearchCardE = (EditText) findViewById(R.id.editSearchCardE);

        editSearchCardE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterE.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchEpic = (ImageView) findViewById(R.id.imgSearchEpic);
        imgSearchEpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardE.getVisibility() == View.INVISIBLE) {
                    editSearchCardE.setVisibility(View.VISIBLE);
                    txtCardEpic.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardE.setVisibility(View.INVISIBLE);
                    txtCardEpic.setVisibility(View.VISIBLE);
                }
            }
        });

        settingCardAdapterR = new SettingCardAdapter(this, cardRare);
        rvRareList.setAdapter(settingCardAdapterR);

        txtCardRare = (TextView) findViewById(R.id.txtCardRare);
        editSearchCardR = (EditText) findViewById(R.id.editSearchCardR);

        editSearchCardR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterR.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchRare = (ImageView) findViewById(R.id.imgSearchRare);
        imgSearchRare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardR.getVisibility() == View.INVISIBLE) {
                    editSearchCardR.setVisibility(View.VISIBLE);
                    txtCardRare.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardR.setVisibility(View.INVISIBLE);
                    txtCardRare.setVisibility(View.VISIBLE);
                }
            }
        });

        settingCardAdapterU = new SettingCardAdapter(this, cardUncommon);
        rvUncommonList.setAdapter(settingCardAdapterU);

        txtCardUncommon = (TextView) findViewById(R.id.txtCardUncommon);
        editSearchCardU = (EditText) findViewById(R.id.editSearchCardU);

        editSearchCardU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterU.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchUncommon = (ImageView) findViewById(R.id.imgSearchUncommon);
        imgSearchUncommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardU.getVisibility() == View.INVISIBLE) {
                    editSearchCardU.setVisibility(View.VISIBLE);
                    txtCardUncommon.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardU.setVisibility(View.INVISIBLE);
                    txtCardUncommon.setVisibility(View.VISIBLE);
                }
            }
        });

        settingCardAdapterC = new SettingCardAdapter(this, cardCommon);
        rvCommonList.setAdapter(settingCardAdapterC);

        txtCardCommon = (TextView) findViewById(R.id.txtCardCommon);
        editSearchCardC = (EditText) findViewById(R.id.editSearchCardC);

        editSearchCardC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterC.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchCommon = (ImageView) findViewById(R.id.imgSearchCommon);
        imgSearchCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardC.getVisibility() == View.INVISIBLE) {
                    editSearchCardC.setVisibility(View.VISIBLE);
                    txtCardCommon.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardC.setVisibility(View.INVISIBLE);
                    txtCardCommon.setVisibility(View.VISIBLE);
                }
            }
        });

        settingCardAdapterS = new SettingCardAdapter(this, cardSpecial);
        rvSpecialList.setAdapter(settingCardAdapterS);

        txtCardSpecial = (TextView) findViewById(R.id.txtCardSpecial);
        editSearchCardS = (EditText) findViewById(R.id.editSearchCardS);

        editSearchCardS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingCardAdapterS.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchSpecial = (ImageView) findViewById(R.id.imgSearchSpecial);
        imgSearchSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardS.getVisibility() == View.INVISIBLE) {
                    editSearchCardS.setVisibility(View.VISIBLE);
                    txtCardSpecial.setVisibility(View.INVISIBLE);
                } else {
                    editSearchCardS.setVisibility(View.INVISIBLE);
                    txtCardSpecial.setVisibility(View.VISIBLE);
                }
            }
        });

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
