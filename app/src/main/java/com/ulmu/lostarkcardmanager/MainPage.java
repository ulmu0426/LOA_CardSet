package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    private CardDBHelper cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;         //카드목록
    protected ArrayList<CardSetInfo> favoriteCardSetInfo;   //즐겨찾기 목록
    protected ArrayList<CardBookInfo> cardBookInfo; //카드 도감 목록
    protected ArrayList<CardSetInfo> cardSetInfo;   //카드 세트 목록
    protected ArrayList<ExtraDmgInfo> beastExtraDmgInfo; //야추피 목록
    protected ArrayList<ExtraDmgInfo> demonExtraDmgInfo; //악추피 목록

    private TextView txtBtnCardSet;
    private TextView txtBtnCardBook;

    private RecyclerView rvFavorite;                        //카드세트의 즐겨찾기 리스트를 보여주기 위한 리사이클러뷰
    protected FavoriteAdapter favoriteAdapter;      //즐겨찾기 어뎁터

    private String[] EDName = {"악마", "야수", "정령", "인간", "기계", "불사", "식물", "물질"};
    private ViewPager2 vpXED;
    private ImageView btnVpPrevious;
    private ImageView btnVpNext;
    private MainPageExtraDmgVPAdapter extraDmgViewPagerAdapter;
    protected ArrayList<ArrayList<ExtraDmgInfo>> extraDmgList;

    private DrawerLayout drawerLayout_Main;         //메인페이지 메뉴

    private ImageView imgBtnMenu_Main;              //메뉴 버튼
    private TextView txtBtnCardSet_Draw;            //메뉴 안에 있는 카드 세트 페이지 버튼
    private TextView txtBtnCardBook_Draw;           //메뉴 안에 있는 카드 도감 페이지 버튼
    private TextView txtBtnDED_Draw;                //메뉴 안에 있는 악추피 페이지 버튼
    private TextView txtBtnBED_Draw;                //메뉴 안에 있는 야추피 페이지 버튼
    private TextView txtBtnCardList;                //메뉴 안에 있는 카드 목록 페이지 버튼

    //메인화면 값들
    private TextView txtCardBookStat_Critical;      //메인 화면에 있는 카드 도감 스텟 : 치명
    private TextView txtCardBookStat_Speciality;    //메인 화면에 있는 카드 도감 스텟 : 특화
    private TextView txtCardBookStat_Agility;       //메인 화면에 있는 카드 도감 스텟 : 신속

    public static Context mainContext;

    private Button btnGuide;
    private Button txtSeGuBitch;
    protected SharedPreferences preferences;        //최초 실행시 가이드 페이지 호출을 위한 변수

    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String TABLE_BEAST_EXTRA_DMG = "beast_extra_dmg";  //야추피 테이블 명

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);
        mainContext = this;
        initXml();

        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuidePage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        preferences = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent guideIntent = new Intent(MainPage.this, GuidePage.class);
            startActivity(guideIntent);
            preferences.edit().putBoolean("isFirstRun", false).apply();
        }

        //DB정보 가져오기
        try {
            setInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //카드 DB 정보 ArrayList 전달
        cardInfo = cardDBHelper.getCardInfo_All();
        //카드 세트, 즐겨찾기 카드세트, 카드 도감 DB 정보 ArrayList 전달
        cardSetInfo = cardDBHelper.getCardSetInfo();
        cardBookInfo = cardDBHelper.getCardBookInfo();

        //추피 DB 정보 ArrayList 전달
        demonExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_DEMON_EXTRA_DMG);
        beastExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_BEAST_EXTRA_DMG);

        extraDmgList = new ArrayList<>();
        extraDmgList.add(demonExtraDmgInfo);
        extraDmgList.add(beastExtraDmgInfo);

        extraDmgViewPagerAdapter = new MainPageExtraDmgVPAdapter(this, extraDmgList);
        vpXED.setAdapter(extraDmgViewPagerAdapter);

        setPreNextVisible();
        btnVpPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpXED.setCurrentItem(vpXED.getCurrentItem() - 1);
                setPreNextVisible();
            }
        });

        btnVpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpXED.setCurrentItem(vpXED.getCurrentItem() + 1);
                setPreNextVisible();
            }
        });

        vpXED.registerOnPageChangeCallback(pageChangeCallback);

        favoriteUpdate();           //카드세트 즐겨찾기 DB 업데이트

        //치,특,신 값 출력
        int[] stat = {getStatInfo("치명"), getStatInfo("특화"), getStatInfo("신속")};
        setCardBookStatInfo(stat);

        favoriteAdapter = new FavoriteAdapter(mainContext);
        rvFavorite.setAdapter(favoriteAdapter);

        //메뉴 페이지
        //drawerLayout
        imgBtnMenu_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout_Main.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout_Main.openDrawer(Gravity.LEFT);
                } else {
                    drawerLayout_Main.closeDrawer(Gravity.LEFT);
                }
            }
        });

        //drawerLayout 메뉴에 '카드목록' 터치시 이동
        txtBtnCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingCard.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //drawerLayout 메뉴에 '카드세트' 터치시 이동
        txtBtnCardSet_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSetPage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //drawerLayout 메뉴에 '카드도감' 터치시 이동
        txtBtnCardBook_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBookPage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //추가 피해 페이지 이동
        Intent intentED = new Intent(getApplicationContext(), ExtraDmgPage.class);
        txtBtnDED_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getEDIndex("악마");
                intentED.putExtra("EDName", "악마");
                intentED.putParcelableArrayListExtra("EDList", extraDmgList.get(index));
                startActivity(intentED);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });
        txtBtnBED_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getEDIndex("야수");
                intentED.putExtra("EDName", "야수");
                intentED.putParcelableArrayListExtra("EDList", extraDmgList.get(index));
                startActivity(intentED);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //메인 페이지
        //카드 세트로 이동.
        txtBtnCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSetPage.class);
                startActivity(intent);
            }
        });

        //카드 도감으로 이동.
        txtBtnCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBookPage.class);
                startActivity(intent);
            }
        });

    }

    //DB정보 App에 입력
    private void setInit() throws IOException {
        cardDBHelper = new CardDBHelper(this);
        cardDBHelper.createDataBase();

    }

    private void initXml() {
        btnGuide = findViewById(R.id.txtGuide);

        //치,특,신 값
        txtCardBookStat_Critical = (TextView) findViewById(R.id.txtCardBookStat_Critical);
        txtCardBookStat_Speciality = (TextView) findViewById(R.id.txtCardBookStat_Speciality);
        txtCardBookStat_Agility = (TextView) findViewById(R.id.txtCardBookStat_Agility);

        //카드 세트
        txtBtnCardSet = (TextView) findViewById(R.id.txtBtnCardSet);
        //카드 도감
        txtBtnCardBook = (TextView) findViewById(R.id.txtBtnCardBookStats);
        //악추피 뷰페이저
        vpXED = findViewById(R.id.vpExtraDmg);
        btnVpPrevious = findViewById(R.id.btnVpPrevious);
        btnVpNext = findViewById(R.id.btnVpNext);

        //즐겨찾기 리사이클러뷰
        rvFavorite = (RecyclerView) findViewById(R.id.rvCardSet);

        //좌측 매뉴 오픈 버튼, 좌측 매뉴
        imgBtnMenu_Main = (ImageView) findViewById(R.id.imgBtnMenu_Main);
        drawerLayout_Main = (DrawerLayout) findViewById(R.id.drawerLayout_Main);

        //좌측 메뉴의 카드 목록, 카드 세트, 카드 도감
        txtBtnCardList = (TextView) findViewById(R.id.txtBtnCardList);
        txtBtnCardSet_Draw = (TextView) findViewById(R.id.txtBtnCardSet_Draw);
        txtBtnCardBook_Draw = (TextView) findViewById(R.id.txtBtnCardBook_Draw);
        txtBtnDED_Draw = (TextView) findViewById(R.id.txtBtnDED_Draw);
        txtBtnBED_Draw = (TextView) findViewById(R.id.txtBtnBED_Draw);

        txtSeGuBitch = findViewById(R.id.txtSeGuBitch);
    }

    //뒤로가기 2회 터치시 종료(2.5초 안에 두번 눌러야 함)
    private long backKeyPressedTime = 0;
    private Toast finish;

    @Override
    public void onBackPressed() {
        if (drawerLayout_Main.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout_Main.closeDrawer(Gravity.LEFT);
            return;
        }

        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            finish = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG);
            finish.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            finish.cancel();
        }
    }


    //카드 도감 (치명, 특화, 신속) 값 입력
    private int getStatInfo(String STAT) {
        int a = 0;
        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getOption().equals(STAT) && (cardBookInfo.get(i).getNeedCard() == cardBookInfo.get(i).getHaveCard())) {
                a += cardBookInfo.get(i).getValue();
            }
        }
        return a;
    }

    //MainPage 카드도감 현황 update
    public void setCardBookStatInfo(int[] stat) {
        txtCardBookStat_Critical.setText(stat[0] + "");
        txtCardBookStat_Speciality.setText(stat[1] + "");
        txtCardBookStat_Agility.setText(stat[2] + "");
    }


    public void setExtraDmgList() {
        extraDmgViewPagerAdapter.setExtraDmgValue(extraDmgList);
    }

    //최초 실행되는 메소드 : cardSet 정보에서 즐겨찾기 기능 DB와 연동
    private void favoriteUpdate() {
        favoriteCardSetInfo = new ArrayList<>();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getFavorite()) {
                favoriteCardSetInfo.add(cardSetInfo.get(i));
            }

        }
    }

    private int getEDIndex(String ED) {
        for (int i = 0; i < EDName.length; i++) {
            if (EDName[i].equals(ED))
                return i;
        }
        return 0;
    }

    private void setPreNextVisible() {
        if (vpXED.getCurrentItem() == 0)
            btnVpPrevious.setVisibility(View.INVISIBLE);
        else if (vpXED.getCurrentItem() > 0)
            btnVpPrevious.setVisibility(View.VISIBLE);

        if (vpXED.getCurrentItem() == (extraDmgList.size() - 1))
            btnVpNext.setVisibility(View.INVISIBLE);
        else
            btnVpNext.setVisibility(View.VISIBLE);
    }

    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            setPreNextVisible();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

    };

    private CardSetInfo getSeGuBit() {
        CardSetInfo cardSet = new CardSetInfo();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("세상을 구하는 빛"))
                cardSet = cardSetInfo.get(i);
        }

        return cardSet;
    }

    private int[] needCardInfo(CardSetInfo cardSetSEGUBIT) {

        int[] cardAwake = {0, 0, 0, 0, 0, 0, 0};
        int[] cardNum = {0, 0, 0, 0, 0, 0, 0};
        int[] needCardNum = {0, 0, 0, 0, 0, 0, 0};
        int goal = 18;
        if (cardSetSEGUBIT.getHaveAwake() >= 18)
            goal = 30;
        cardAwake[0] = cardSetSEGUBIT.getAwakeCard0();
        cardAwake[1] = cardSetSEGUBIT.getAwakeCard1();
        cardAwake[2] = cardSetSEGUBIT.getAwakeCard2();
        cardAwake[3] = cardSetSEGUBIT.getAwakeCard3();
        cardAwake[4] = cardSetSEGUBIT.getAwakeCard4();
        cardAwake[5] = cardSetSEGUBIT.getAwakeCard5();
        cardAwake[6] = cardSetSEGUBIT.getAwakeCard6();

        cardNum[0] = cardSetSEGUBIT.getNumCard0();
        cardNum[1] = cardSetSEGUBIT.getNumCard1();
        cardNum[2] = cardSetSEGUBIT.getNumCard2();
        cardNum[3] = cardSetSEGUBIT.getNumCard3();
        cardNum[4] = cardSetSEGUBIT.getNumCard4();
        cardNum[5] = cardSetSEGUBIT.getNumCard5();
        cardNum[6] = cardSetSEGUBIT.getNumCard6();

        int minAwakeIndex = 0;
        int minAwake = 6;
        for (int i = 0; i < cardAwake.length; i++) {
            if(minAwake < cardAwake[i])
                minAwake = cardAwake[i];
        }


        return cardAwake;
    }

}
