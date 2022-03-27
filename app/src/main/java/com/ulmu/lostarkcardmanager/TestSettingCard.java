package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSettingCard extends AppCompatActivity {

    private CardDBHelper cardDBHelper;
    private Context context;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private TestViewPagerAdapter testViewPagerAdapter;
    private TestFragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<Fragment> fragmentList;

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


        cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        settingCardList();

        tabLayout = findViewById(R.id.tabLayoutCardList);
        viewPager = findViewById(R.id.vpCardList);

        fragmentList = new ArrayList<>();
        fragmentList.add(new TestLegend().newInstance());
        fragmentList.add(new TestEpic().newInstance());
        fragmentList.add(new TestRare().newInstance());
        fragmentList.add(new TestUncommon().newInstance());
        fragmentList.add(new TestCommon().newInstance());
        fragmentList.add(new TestSpecial().newInstance());

        testViewPagerAdapter = new TestViewPagerAdapter(this);
        fragmentPagerAdapter = new TestFragmentPagerAdapter(this, fragmentList);

        //viewPager.setAdapter(testViewPagerAdapter); //리사이클러뷰 형태로 어뎁팅
        viewPager.setAdapter(fragmentPagerAdapter); //프래그먼트 어뎁팅

        final List<String> tabText = Arrays.asList(LEGEND, EPIC, RARE, UNCOMMON, COMMON, SPECIAL);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(TestSettingCard.this);
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

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentItem = viewPager.getCurrentItem();
                Log.v("test", "currentItem : " + currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        editSearchCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                
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
