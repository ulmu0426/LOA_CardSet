package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.lostarkcardmanager.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TestSettingCard extends AppCompatActivity {

    private CardDBHelper cardDBHelper;
    private Context context;

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private TabLayout tabLayout;

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

    private TabItem tILegend;
    private TabItem tIEpic;
    private TabItem tIRare;
    private TabItem tIUncommon;
    private TabItem tICommon;
    private TabItem tISpecial;

    private ImageView imgSearch;
    private ConstraintLayout cvCardList;
    private EditText editSearchCard;

    private ImageView imgMenu;

    private boolean[] checkAll = new boolean[4];
    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkNotAcquiredSort = false;
    private boolean checkAcquiredSort = false;

    private CharSequence filterChar = "";

    private int currentItem;

    public static TestSettingCard testSettingCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist_viewpager);
        testSettingCard = this;
        context = this;
        checkAll[0] = checkDefault;
        checkAll[1] = checkName;
        checkAll[2] = checkNotAcquiredSort;
        checkAll[3] = checkAcquiredSort;

        tILegend = findViewById(R.id.tILegend);
        tIEpic = findViewById(R.id.tIEpic);
        tIRare = findViewById(R.id.tIRare);
        tIUncommon = findViewById(R.id.tIUncommon);
        tICommon = findViewById(R.id.tICommon);
        tISpecial = findViewById(R.id.tISpecial);


        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        settingCardList();


        viewPager = findViewById(R.id.vpCardList);
        fragmentPagerAdapter = new TestFragmentPagerAdapter(getSupportFragmentManager());

        tabLayout = findViewById(R.id.tabLayoutCardList);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

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


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        editSearchCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle data = new Bundle();

                if (s.length() == 0) {
                    filterChar = "";
                } else {
                    filterChar = s;
                }

                data.putCharSequence("dataSend", filterChar);

                if (currentItem == 0){
                    TestLegend sendFragment = new TestLegend();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
                if (currentItem == 1){
                    TestEpic sendFragment = new TestEpic();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
                if (currentItem == 2){
                    TestRare sendFragment = new TestRare();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
                if (currentItem == 3){
                    TestUncommon sendFragment = new TestUncommon();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
                if (currentItem == 4){
                    TestCommon sendFragment = new TestCommon();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
                if (currentItem == 5){
                    TestSpecial sendFragment = new TestSpecial();
                    sendFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.clFragment, sendFragment).commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cardDBHelper = new CardDBHelper(context);
        imgMenu = findViewById(R.id.imgMenu_ViewPager);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imgMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_cardlist_menu, popupMenu.getMenu());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle data = new Bundle();

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

                                return true;

                            case R.id.nameSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 1)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }

                                return true;

                            case R.id.notAcquiredSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 2)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }

                                return true;

                            case R.id.acquiredSort:
                                for (int i = 0; i < checkAll.length; i++) {
                                    if (i == 3)
                                        checkAll[i] = true;
                                    else
                                        checkAll[i] = false;
                                }

                                return true;

                            case R.id.allCheck:
                                for (int i = 0; i < cardInfo.size(); i++) {
                                    cardInfo.get(i).setGetCard(1);
                                    cardDBHelper.UpdateInfoCardCheck(cardInfo.get(i).getGetCard(), cardInfo.get(i).getId());
                                }
                                settingCardList();


                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    public TestSettingCard getTestSettingCard() {
        return this.testSettingCard;
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
                ci.setGrade(cardInfo.get(i).getGrade());
                cardLegend.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardEpic.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(RARE)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardRare.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(UNCOMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardUncommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(COMMON)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardCommon.add(ci);
            } else if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
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

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        private int currentPage = 0;

        public void onPageSelected(int position) {
            currentPage = position;
        }

        public int getCurrentPage() {
            return currentPage;
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
