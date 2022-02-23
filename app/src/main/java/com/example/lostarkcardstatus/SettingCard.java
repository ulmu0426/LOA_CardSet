package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    private ImageView imgSortLegend;
    private TextView txtCardLegend;
    private EditText editSearchCardL;
    private ImageView imgSearchLegend;
    private MenuItem invisibleListL;

    private ImageView imgSortEpic;
    private TextView txtCardEpic;
    private EditText editSearchCardE;
    private ImageView imgSearchEpic;
    private MenuItem invisibleListE;

    private ImageView imgSortRare;
    private TextView txtCardRare;
    private EditText editSearchCardR;
    private ImageView imgSearchRare;
    private MenuItem invisibleListR;

    private ImageView imgSortUncommon;
    private TextView txtCardUncommon;
    private EditText editSearchCardU;
    private ImageView imgSearchUncommon;
    private MenuItem invisibleListU;

    private ImageView imgSortCommon;
    private TextView txtCardCommon;
    private EditText editSearchCardC;
    private ImageView imgSearchCommon;
    private MenuItem invisibleListC;

    private ImageView imgSortSpecial;
    private TextView txtCardSpecial;
    private EditText editSearchCardS;
    private ImageView imgSearchSpecial;
    private MenuItem invisibleListS;


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

        editSearchCardL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardL.selectAll();
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

        imgSortLegend = findViewById(R.id.imgSortLegend);
        imgSortLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortLegend);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListL = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvLegendList.getVisibility() == View.GONE) {
                    invisibleListL.setTitle("펼치기");
                } else {
                    invisibleListL.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardLegend.get(0).getId() == 0) {
                                    break;
                                }
                                cardLegend = settingCardAdapterL.getFilterCardInfo();
                                Collections.sort(cardLegend, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterL.sortCardList(cardLegend);
                                return true;
                            case R.id.nameSort:
                                if (cardLegend.get(0).getName() == "아만") {
                                    break;
                                }
                                cardLegend = settingCardAdapterL.getFilterCardInfo();
                                Collections.sort(cardLegend);
                                settingCardAdapterL.sortCardList(cardLegend);
                                return true;
                            case R.id.invisibleList:
                                if (rvLegendList.getVisibility() == View.GONE) {
                                    rvLegendList.setVisibility(View.VISIBLE);
                                } else {
                                    rvLegendList.setVisibility(View.GONE);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        editSearchCardE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardE.selectAll();
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

        imgSortEpic = findViewById(R.id.imgSortEpic);
        imgSortEpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortEpic);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListE = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvEpicList.getVisibility() == View.GONE) {
                    invisibleListE.setTitle("펼치기");
                } else {
                    invisibleListE.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardEpic.get(0).getId() == 0) {
                                    break;
                                }
                                cardEpic = settingCardAdapterE.getFilterCardInfo();
                                Collections.sort(cardEpic, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterE.sortCardList(cardEpic);
                                return true;
                            case R.id.nameSort:
                                if (cardEpic.get(0).getName() == "아만") {
                                    break;
                                }
                                cardEpic = settingCardAdapterE.getFilterCardInfo();
                                Collections.sort(cardEpic);
                                settingCardAdapterE.sortCardList(cardEpic);
                                return true;
                            case R.id.invisibleList:
                                if (rvEpicList.getVisibility() == View.GONE) {
                                    rvEpicList.setVisibility(View.VISIBLE);
                                } else {
                                    rvEpicList.setVisibility(View.GONE);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        editSearchCardR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardR.selectAll();
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

        imgSortRare = findViewById(R.id.imgSortRare);
        imgSortRare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortRare);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListR = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvRareList.getVisibility() == View.GONE) {
                    invisibleListR.setTitle("펼치기");
                } else {
                    invisibleListR.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardRare.get(0).getId() == 0) {
                                    break;
                                }
                                cardRare = settingCardAdapterR.getFilterCardInfo();
                                Collections.sort(cardRare, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterR.sortCardList(cardRare);
                                return true;
                            case R.id.nameSort:
                                if (cardRare.get(0).getName() == "아만") {
                                    break;
                                }
                                cardRare = settingCardAdapterR.getFilterCardInfo();
                                Collections.sort(cardRare);
                                settingCardAdapterR.sortCardList(cardRare);
                                return true;
                            case R.id.invisibleList:
                                if (rvRareList.getVisibility() == View.GONE) {
                                    rvRareList.setVisibility(View.VISIBLE);
                                } else {
                                    rvRareList.setVisibility(View.GONE);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        editSearchCardU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardU.selectAll();
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

        imgSortUncommon = findViewById(R.id.imgSortUncommon);
        imgSortUncommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortRare);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListU = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvUncommonList.getVisibility() == View.GONE) {
                    invisibleListU.setTitle("펼치기");
                } else {
                    invisibleListU.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardUncommon.get(0).getId() == 0) {
                                    break;
                                }
                                cardUncommon = settingCardAdapterU.getFilterCardInfo();
                                Collections.sort(cardUncommon, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterU.sortCardList(cardUncommon);
                                return true;
                            case R.id.nameSort:
                                if (cardUncommon.get(0).getName() == "아만") {
                                    break;
                                }
                                cardUncommon = settingCardAdapterU.getFilterCardInfo();
                                Collections.sort(cardUncommon);
                                settingCardAdapterU.sortCardList(cardUncommon);
                                return true;
                            case R.id.invisibleList:
                                if (rvUncommonList.getVisibility() == View.GONE) {
                                    rvUncommonList.setVisibility(View.VISIBLE);
                                } else {
                                    rvUncommonList.setVisibility(View.GONE);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        editSearchCardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardC.selectAll();
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

        imgSortCommon = findViewById(R.id.imgSortCommon);
        imgSortCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortRare);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListC = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvCommonList.getVisibility() == View.GONE) {
                    invisibleListC.setTitle("펼치기");
                } else {
                    invisibleListC.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardCommon.get(0).getId() == 0) {
                                    break;
                                }
                                cardCommon = settingCardAdapterC.getFilterCardInfo();
                                Collections.sort(cardCommon, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterC.sortCardList(cardCommon);
                                return true;
                            case R.id.nameSort:
                                if (cardCommon.get(0).getName() == "아만") {
                                    break;
                                }
                                cardCommon = settingCardAdapterC.getFilterCardInfo();
                                Collections.sort(cardCommon);
                                settingCardAdapterC.sortCardList(cardCommon);
                                return true;
                            case R.id.invisibleList:
                                if (rvCommonList.getVisibility() == View.GONE) {
                                    rvCommonList.setVisibility(View.VISIBLE);
                                } else {
                                    rvCommonList.setVisibility(View.GONE);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        editSearchCardS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchCardS.selectAll();
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

        imgSortSpecial = findViewById(R.id.imgSortSpecial);
        imgSortSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SettingCard.this, imgSortRare);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                invisibleListS = popupMenu.getMenu().findItem(R.id.invisibleList);
                if (rvSpecialList.getVisibility() == View.GONE) {
                    invisibleListS.setTitle("펼치기");
                } else {
                    invisibleListS.setTitle("숨기기");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardSpecial.get(0).getId() == 0) {
                                    break;
                                }
                                cardSpecial = settingCardAdapterS.getFilterCardInfo();
                                Collections.sort(cardSpecial, new Comparator<CardInfo>() {
                                    @Override
                                    public int compare(CardInfo o1, CardInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                settingCardAdapterS.sortCardList(cardSpecial);
                                return true;
                            case R.id.nameSort:
                                if (cardSpecial.get(0).getName() == "아만") {
                                    break;
                                }
                                cardSpecial = settingCardAdapterS.getFilterCardInfo();
                                Collections.sort(cardSpecial);
                                settingCardAdapterS.sortCardList(cardSpecial);
                                return true;
                            case R.id.invisibleList:
                                if (rvSpecialList.getVisibility() == View.GONE) {
                                    rvSpecialList.setVisibility(View.VISIBLE);
                                } else {
                                    rvSpecialList.setVisibility(View.GONE);
                                }
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
        if(editSearchCardL.getVisibility() == View.VISIBLE){
            editSearchCardL.setVisibility(View.INVISIBLE);
            txtCardLegend.setVisibility(View.VISIBLE);
            return;
        }
        if(editSearchCardE.getVisibility() == View.VISIBLE){
            editSearchCardE.setVisibility(View.INVISIBLE);
            txtCardEpic.setVisibility(View.VISIBLE);
            return;
        }
        if(editSearchCardR.getVisibility() == View.VISIBLE){
            editSearchCardR.setVisibility(View.INVISIBLE);
            txtCardRare.setVisibility(View.VISIBLE);
            return;
        }
        if(editSearchCardU.getVisibility() == View.VISIBLE){
            editSearchCardU.setVisibility(View.INVISIBLE);
            txtCardUncommon.setVisibility(View.VISIBLE);
            return;
        }
        if(editSearchCardC.getVisibility() == View.VISIBLE){
            editSearchCardC.setVisibility(View.INVISIBLE);
            txtCardCommon.setVisibility(View.VISIBLE);
            return;
        }
        if(editSearchCardS.getVisibility() == View.VISIBLE){
            editSearchCardS.setVisibility(View.INVISIBLE);
            txtCardSpecial.setVisibility(View.VISIBLE);
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
