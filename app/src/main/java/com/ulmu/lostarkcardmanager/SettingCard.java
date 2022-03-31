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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingCard extends AppCompatActivity {

    private CardDBHelper cardDBHelper;
    private Context context;

    private ViewPager2 viewPager;                   //뷰페이저
    private TabLayout tabLayout;                    //뷰페이저탭

    private ViewPagerAdapter viewPagerAdapter;      //뷰페이저 어뎁터
    private ArrayList<CardInfo> cardInfo;           //카드정보
    private ArrayList<CardInfo> cardLegend;         //카드정보 : 전설
    private ArrayList<CardInfo> cardEpic;           //카드정보 : 영웅
    private ArrayList<CardInfo> cardRare;           //카드정보 : 희귀
    private ArrayList<CardInfo> cardUncommon;       //카드정보 : 고급
    private ArrayList<CardInfo> cardCommon;         //카드정보 : 일반
    private ArrayList<CardInfo> cardSpecial;        //카드정보 : 스페셜

    private String LEGEND = "전설";
    private String EPIC = "영웅";
    private String RARE = "희귀";
    private String UNCOMMON = "고급";
    private String COMMON = "일반";
    private String SPECIAL = "스페셜";


    private ImageView imgSearch;
    private ConstraintLayout cvCardList;
    private EditText editSearchCard;

    private ImageView imgMenu;                      //카드목록 메뉴(정렬, 카드 전부획득, 카드 초기화)

    private boolean[] checkAll = new boolean[4];
    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkNotAcquiredSort = false;
    private boolean checkAcquiredSort = false;

    private CharSequence filterChar = "";           //검색한 단어


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist_viewpager);
        context = this;
        cardDBHelper = new CardDBHelper(context);
        checkAll[0] = checkDefault;
        checkAll[1] = checkName;
        checkAll[2] = checkNotAcquiredSort;
        checkAll[3] = checkAcquiredSort;

        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        settingCardList();

        tabLayout = findViewById(R.id.tabLayoutCardList);
        viewPager = findViewById(R.id.vpCardList);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter); //리사이클러뷰 형태로 어뎁팅

        final List<String> tabText = Arrays.asList(LEGEND, EPIC, RARE, UNCOMMON, COMMON, SPECIAL);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(SettingCard.this);
                textView.setText(tabText.get(position));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(null, Typeface.BOLD);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
                switch (position) {
                    case 0:
                        textView.setTextColor(Color.parseColor("#FFF4BD"));
                        break;
                    case 1:
                        textView.setTextColor(Color.parseColor("#ECE2FF"));
                        break;
                    case 2:
                        textView.setTextColor(Color.parseColor("#DDEFFF"));
                        break;
                    case 3:
                        textView.setTextColor(Color.parseColor("#DEFFBB"));
                        break;
                    case 4:
                        textView.setTextColor(Color.parseColor("#F4F4F4"));
                        break;
                    case 5:
                        textView.setTextColor(Color.parseColor("#FFDCE9"));
                        break;
                }
                tab.setCustomView(textView);
            }
        }).attach();

        imgSearch = findViewById(R.id.imgSearch_ViewPager);
        cvCardList = findViewById(R.id.cvViewPager2CardList);

        editSearchCard = findViewById(R.id.editSearchCard_ViewPager);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvCardList.getVisibility() == View.VISIBLE) {
                    editSearchCard.setText("");
                    cvCardList.setVisibility(View.GONE);
                } else
                    cvCardList.setVisibility(View.VISIBLE);
            }
        });

        editSearchCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterChar = s;
                viewPagerAdapter.search(filterChar);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //정렬(기본, 이름, 미획득, 획득) 카드 모두획득, 카드 획득 초기화
        imgMenu = findViewById(R.id.imgMenu_ViewPager);
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
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 0)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }
                                viewPagerAdapter.sortingCard(checkAll);

                                return true;

                            case R.id.nameSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 1)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }
                                viewPagerAdapter.sortingCard(checkAll);

                                return true;

                            case R.id.notAcquiredSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 2)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }
                                viewPagerAdapter.sortingCard(checkAll);


                                return true;

                            case R.id.acquiredSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 3)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }
                                viewPagerAdapter.sortingCard(checkAll);


                                return true;

                            case R.id.allCheck:
                                for (int i = 0; i < cardInfo.size(); i++) {
                                    cardInfo.get(i).setGetCard(1);
                                    cardDBHelper.UpdateInfoCardCheck(cardInfo.get(i).getGetCard(), cardInfo.get(i).getId());
                                }
                                settingCardList();
                                viewPagerAdapter.allCheck();

                                return true;

                            case R.id.allUncheck:
                                for (int i = 0; i < cardInfo.size(); i++) {
                                    cardInfo.get(i).setGetCard(0);
                                    cardDBHelper.UpdateInfoCardCheck(cardInfo.get(i).getGetCard(), cardInfo.get(i).getId());
                                }
                                settingCardList();
                                viewPagerAdapter.allUncheck();

                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    //카드 목록 update
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

    @Override
    public void onBackPressed() {
        if (cvCardList.getVisibility() == View.VISIBLE) {
            cvCardList.setVisibility(View.GONE);
            return;
        }
        finish();
    }


}
