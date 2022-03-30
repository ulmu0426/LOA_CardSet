package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingCard extends AppCompatActivity {

    private Context context;
    private CardDBHelper cardDBHelper;

    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardInfo> cardLegend;
    private ArrayList<CardInfo> cardEpic;
    private ArrayList<CardInfo> cardRare;
    private ArrayList<CardInfo> cardUncommon;
    private ArrayList<CardInfo> cardCommon;
    private ArrayList<CardInfo> cardSpecial;

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";

    private RecyclerView rvList;
    private TextView btnL;
    private TextView btnE;
    private TextView btnR;
    private TextView btnU;
    private TextView btnC;
    private TextView btnS;

    private SettingCard settingCard;
    private SettingCardAdapter adapter;

    private ImageView imgMenu;

    private ImageView imgSearch;
    private EditText editSearchCard;

    private ConstraintLayout cvCardList;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkNotAcquiredSort = false;
    private boolean checkAcquiredSort = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist);
        context = this;
        settingCard = this;
        cardDBHelper = new CardDBHelper(context);

        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;

        settingCardList();

        rvList = findViewById(R.id.rvList);

        adapter = new SettingCardAdapter(context, cardLegend, settingCard);
        rvList.setAdapter(adapter);

        btnL = findViewById(R.id.btnL);
        btnE = findViewById(R.id.btnE);
        btnR = findViewById(R.id.btnR);
        btnC = findViewById(R.id.btnC);
        btnU = findViewById(R.id.btnU);
        btnS = findViewById(R.id.btnS);

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardLegend, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingBold(btnL);
                setBtnSettingNormal(btnE);
                setBtnSettingNormal(btnR);
                setBtnSettingNormal(btnU);
                setBtnSettingNormal(btnC);
                setBtnSettingNormal(btnS);
                rvList.setBackgroundColor(Color.parseColor("#FFF4BD"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardEpic, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingNormal(btnL);
                setBtnSettingBold(btnE);
                setBtnSettingNormal(btnR);
                setBtnSettingNormal(btnU);
                setBtnSettingNormal(btnC);
                setBtnSettingNormal(btnS);
                rvList.setBackgroundColor(Color.parseColor("#ECE2FF"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardRare, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingNormal(btnL);
                setBtnSettingNormal(btnE);
                setBtnSettingBold(btnR);
                setBtnSettingNormal(btnU);
                setBtnSettingNormal(btnC);
                setBtnSettingNormal(btnS);
                rvList.setBackgroundColor(Color.parseColor("#DDEFFF"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });
        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardUncommon, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingNormal(btnL);
                setBtnSettingNormal(btnE);
                setBtnSettingNormal(btnR);
                setBtnSettingBold(btnU);
                setBtnSettingNormal(btnC);
                setBtnSettingNormal(btnS);
                rvList.setBackgroundColor(Color.parseColor("#DEFFBB"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardCommon, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingNormal(btnL);
                setBtnSettingNormal(btnE);
                setBtnSettingNormal(btnR);
                setBtnSettingNormal(btnU);
                setBtnSettingBold(btnC);
                setBtnSettingNormal(btnS);
                rvList.setBackgroundColor(Color.parseColor("#F4F4F4"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new SettingCardAdapter(context, cardSpecial, settingCard);
                rvList.setAdapter(adapter);
                setBtnSettingNormal(btnL);
                setBtnSettingNormal(btnE);
                setBtnSettingNormal(btnR);
                setBtnSettingNormal(btnU);
                setBtnSettingNormal(btnC);
                setBtnSettingBold(btnS);
                rvList.setBackgroundColor(Color.parseColor("#FFDCE9"));
                followSorting();
                adapter.notifyDataSetChanged();
            }
        });

        cvCardList = findViewById(R.id.cvCardList);

        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvCardList.getVisibility() == View.VISIBLE)
                    cvCardList.setVisibility(View.GONE);
                else
                    cvCardList.setVisibility(View.VISIBLE);
            }
        });

        editSearchCard = findViewById(R.id.editSearchCard);
        editSearchCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                if (s.length() == 0) {
                    settingCardList();
                    if (btnL.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardLegend, settingCard);
                        rvList.setAdapter(adapter);
                    }
                    if (btnE.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardEpic, settingCard);
                        rvList.setAdapter(adapter);
                    }
                    if (btnR.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardRare, settingCard);
                        rvList.setAdapter(adapter);
                    }
                    if (btnU.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardUncommon, settingCard);
                        rvList.setAdapter(adapter);
                    }
                    if (btnC.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardCommon, settingCard);
                        rvList.setAdapter(adapter);
                    }
                    if (btnS.getTypeface() == Typeface.DEFAULT_BOLD) {
                        adapter = new SettingCardAdapter(context, cardSpecial, settingCard);
                        rvList.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgMenu = findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imgMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                adapter.getDefaultSort();
                                checkDefault = true;
                                checkName = false;
                                checkNotAcquiredSort = false;
                                checkAcquiredSort = false;

                                return true;

                            case R.id.nameSort:
                                adapter.getNameSort();
                                checkDefault = false;
                                checkName = true;
                                checkNotAcquiredSort = false;
                                checkAcquiredSort = false;

                                return true;

                            case R.id.notAcquiredSort:
                                adapter.getNotAcquiredSort();
                                checkDefault = false;
                                checkName = false;
                                checkNotAcquiredSort = true;
                                checkAcquiredSort = false;

                                return true;

                            case R.id.acquiredSort:
                                adapter.getAcquiredSort();
                                checkDefault = false;
                                checkName = false;
                                checkNotAcquiredSort = false;
                                checkAcquiredSort = true;

                                return true;

                            case R.id.allCheck:
                                for (int i = 0; i < cardInfo.size(); i++) {
                                    cardInfo.get(i).setGetCard(1);
                                    cardDBHelper.UpdateInfoCardCheck(cardInfo.get(i).getGetCard(), cardInfo.get(i).getId());
                                }
                                settingCardList();
                                adapter.allCardCheck();

                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (cvCardList.getVisibility() == View.VISIBLE) {
            cvCardList.setVisibility(View.GONE);
            return;
        }
        finish();
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
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setNum(cardInfo.get(i).getNum());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardSpecial.add(ci);
            } else {
                continue;
            }
        }
    }

    private void setBtnSettingNormal(TextView btnGrade) {
        btnGrade.setTextSize(18);
        btnGrade.setTypeface(Typeface.DEFAULT);
    }

    private void setBtnSettingBold(TextView btnBold) {
        btnBold.setTextSize(20);
        btnBold.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean isCheckDefault() {
        return checkDefault;
    }

    public boolean isCheckName() {
        return checkName;
    }

    public boolean isCheckNotAcquiredSort() {
        return checkNotAcquiredSort;
    }

    public boolean isCheckAcquiredSort() {
        return checkAcquiredSort;
    }

    public void followSorting() {
        if (isCheckDefault())
            adapter.getDefaultSort();

        if (isCheckName())
            adapter.getNameSort();

        if (isCheckNotAcquiredSort())
            adapter.getNotAcquiredSort();

        if (isCheckAcquiredSort())
            adapter.getAcquiredSort();

    }
}
