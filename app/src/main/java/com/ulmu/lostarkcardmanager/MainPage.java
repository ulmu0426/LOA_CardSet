package com.ulmu.lostarkcardmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainPage extends AppCompatActivity {
    private CardDBHelper cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;                 //카드목록
    protected ArrayList<CardSetInfo> favoriteCardSetInfo;   //즐겨찾기 목록
    protected ArrayList<CardBookInfo> cardBookInfo;         //카드 도감 목록
    protected ArrayList<CardSetInfo> cardSetInfo;           //카드 세트 목록
    protected ArrayList<ExtraDmgInfo> beastExtraDmgInfo;    //야추피 목록
    protected ArrayList<ExtraDmgInfo> demonExtraDmgInfo;    //악추피 목록

    private TextView txtBtnCardSet;
    private TextView txtBtnCardBook;

    private RecyclerView rvFavorite;                        //카드세트의 즐겨찾기 리스트를 보여주기 위한 리사이클러뷰
    protected FavoriteAdapter favoriteAdapter;              //즐겨찾기 어뎁터

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
    private Button btnSegubit;
    private String[] segubitName = {"샨디", "아제나&이난나", "니나브", "카단", "바훈투르", "실리안", "웨이"};
    private Button btnNamba;
    private String[] nambaName = {"아만", "세리아", "집행관 솔라스", "국왕 실리안", "카마인", "데런 아만"};

    protected SharedPreferences preferences;        //최초 실행시 가이드 페이지 호출을 위한 변수

    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String TABLE_BEAST_EXTRA_DMG = "beast_extra_dmg";  //야추피 테이블 명
    int goal = 0;

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


        btnSegubit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
                Dialog segubitDialog = new Dialog(mainContext, android.R.style.Theme_Material_Light_Dialog);

                segubitDialog.setContentView(R.layout.segubit);

                WindowManager.LayoutParams params = segubitDialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                segubitDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                goal = nextSegubit();

                TextView txtSegubitAwake = segubitDialog.findViewById(R.id.txtSegubitAwake);
                txtSegubitAwake.setText("현재 각성 합계 : " + nowSegubit());
                TextView txtGoal = segubitDialog.findViewById(R.id.txtGoal);
                txtGoal.setText(goal + "각성에 필요한 세구빛 카드 수");

                setSegubit();
                //각성도
                TextView txtShandiAwake = segubitDialog.findViewById(R.id.txtShandiAwake);
                TextView txtAzenaInannaAwake = segubitDialog.findViewById(R.id.txtAzenaInannaAwake);
                TextView txtNinabAwake = segubitDialog.findViewById(R.id.txtNinabAwake);
                TextView txtKadanAwake = segubitDialog.findViewById(R.id.txtKadanAwake);
                TextView txtBahunturAwake = segubitDialog.findViewById(R.id.txtBahunturAwake);
                TextView txtSillianAwake = segubitDialog.findViewById(R.id.txtSillianAwake);
                TextView txtWeiAwake = segubitDialog.findViewById(R.id.txtWeiAwake);
                txtShandiAwake.setText(findAwake(segubitName[0]));
                txtAzenaInannaAwake.setText(findAwake(segubitName[1]));
                txtNinabAwake.setText(findAwake(segubitName[2]));
                txtKadanAwake.setText(findAwake(segubitName[3]));
                txtBahunturAwake.setText(findAwake(segubitName[4]));
                txtSillianAwake.setText(findAwake(segubitName[5]));
                txtWeiAwake.setText(findAwake(segubitName[6]));
                //현재 보유카드
                TextView txtShandiNum = segubitDialog.findViewById(R.id.txtShandiNum);
                TextView txtAzenaInannaNum = segubitDialog.findViewById(R.id.txtAzenaInannaNum);
                TextView txtNinabNum = segubitDialog.findViewById(R.id.txtNinabNum);
                TextView txtKadanNum = segubitDialog.findViewById(R.id.txtKadanNum);
                TextView txtBahunturNum = segubitDialog.findViewById(R.id.txtBahunturNum);
                TextView txtSillianNum = segubitDialog.findViewById(R.id.txtSillianNum);
                TextView txtWeiNum = segubitDialog.findViewById(R.id.txtWeiNum);
                txtShandiNum.setText(findNum(segubitName[0]));
                txtAzenaInannaNum.setText(findNum(segubitName[1]));
                txtNinabNum.setText(findNum(segubitName[2]));
                txtKadanNum.setText(findNum(segubitName[3]));
                txtBahunturNum.setText(findNum(segubitName[4]));
                txtSillianNum.setText(findNum(segubitName[5]));
                txtWeiNum.setText(findNum(segubitName[6]));


                segubitGoal(goal);
                //필요카드
                TextView txtShandiNeedNum = segubitDialog.findViewById(R.id.txtShandiNeedNum);
                TextView txtAzenaInannaNeedNum = segubitDialog.findViewById(R.id.txtAzenaInannaNeedNum);
                TextView txtNinabNeedNum = segubitDialog.findViewById(R.id.txtNinabNeedNum);
                TextView txtKadanNeedNum = segubitDialog.findViewById(R.id.txtKadanNeedNum);
                TextView txtBahunturNeedNum = segubitDialog.findViewById(R.id.txtBahunturNeedNum);
                TextView txtSillianNeedNum = segubitDialog.findViewById(R.id.txtSillianNeedNum);
                TextView txtWeiNeedNum = segubitDialog.findViewById(R.id.txtWeiNeedNum);
                txtShandiNeedNum.setText(findSegubitNeedCard(segubit, segubitName[0]) + "");
                txtAzenaInannaNeedNum.setText(findSegubitNeedCard(segubit, segubitName[1]) + "");
                txtNinabNeedNum.setText(findSegubitNeedCard(segubit, segubitName[2]) + "");
                txtKadanNeedNum.setText(findSegubitNeedCard(segubit, segubitName[3]) + "");
                txtBahunturNeedNum.setText(findSegubitNeedCard(segubit, segubitName[4]) + "");
                txtSillianNeedNum.setText(findSegubitNeedCard(segubit, segubitName[5]) + "");
                txtWeiNeedNum.setText(findSegubitNeedCard(segubit, segubitName[6]) + "");

                TextView txtNeedCard = segubitDialog.findViewById(R.id.txtNeedCard);
                txtNeedCard.setText(goal + "각 까지 최소 필요 카드 수 : " + Arrays.stream(segubitNeedCard).sum());

                segubitDialog.show();
            }
        });

        btnNamba.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
                Dialog nambaDialog = new Dialog(mainContext, android.R.style.Theme_Material_Light_Dialog);

                nambaDialog.setContentView(R.layout.segubit);

                WindowManager.LayoutParams params = nambaDialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                nambaDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                TableRow lastRow = nambaDialog.findViewById(R.id.lastRow);
                lastRow.setVisibility(View.GONE);

                goal = nextNamba();
                TextView txtNambaName = nambaDialog.findViewById(R.id.txtSegubitName);
                txtNambaName.setText("남겨진 바람의 절벽");
                TextView txtSegubitAwake = nambaDialog.findViewById(R.id.txtSegubitAwake);
                txtSegubitAwake.setText("현재 각성 합계 : " + nowNamba());
                TextView txtGoal = nambaDialog.findViewById(R.id.txtGoal);
                txtGoal.setText(goal + "각성에 필요한 남바절 카드 수");

                setNamba();
                TextView txtAman = nambaDialog.findViewById(R.id.txtShandi);
                TextView txtSeria = nambaDialog.findViewById(R.id.txtAzenaInanna);
                TextView txtSolas = nambaDialog.findViewById(R.id.txtNinab);
                TextView txtKingSillian = nambaDialog.findViewById(R.id.txtKadan);
                TextView txtKamain = nambaDialog.findViewById(R.id.txtBahuntur);
                TextView txtDerunAman = nambaDialog.findViewById(R.id.txtSillian);
                txtAman.setText(nambaName[0]);
                txtSeria.setText(nambaName[1]);
                txtSolas.setText(nambaName[2]);
                txtKingSillian.setText(nambaName[3]);
                txtKamain.setText(nambaName[4]);
                txtDerunAman.setText(nambaName[5]);

                //각성도
                TextView txtAmanAwake = nambaDialog.findViewById(R.id.txtShandiAwake);
                TextView txtSeriaAwake = nambaDialog.findViewById(R.id.txtAzenaInannaAwake);
                TextView txtSolasAwake = nambaDialog.findViewById(R.id.txtNinabAwake);
                TextView txtKingSillianAwake = nambaDialog.findViewById(R.id.txtKadanAwake);
                TextView txtKamainAwake = nambaDialog.findViewById(R.id.txtBahunturAwake);
                TextView txtDerunAmanAwake = nambaDialog.findViewById(R.id.txtSillianAwake);
                txtAmanAwake.setText(findAwake(nambaName[0]));
                txtSeriaAwake.setText(findAwake(nambaName[1]));
                txtSolasAwake.setText(findAwake(nambaName[2]));
                txtKingSillianAwake.setText(findAwake(nambaName[3]));
                txtKamainAwake.setText(findAwake(nambaName[4]));
                txtDerunAmanAwake.setText(findAwake(nambaName[5]));
                //현재 보유카드
                TextView txtAmanNum = nambaDialog.findViewById(R.id.txtShandiNum);
                TextView txtSeriaNum = nambaDialog.findViewById(R.id.txtAzenaInannaNum);
                TextView txtSolasNum = nambaDialog.findViewById(R.id.txtNinabNum);
                TextView txtKingSillianNum = nambaDialog.findViewById(R.id.txtKadanNum);
                TextView txtKamainNum = nambaDialog.findViewById(R.id.txtBahunturNum);
                TextView txtDerunAmanNum = nambaDialog.findViewById(R.id.txtSillianNum);
                txtAmanNum.setText(findNum(nambaName[0]));
                txtSeriaNum.setText(findNum(nambaName[1]));
                txtSolasNum.setText(findNum(nambaName[2]));
                txtKingSillianNum.setText(findNum(nambaName[3]));
                txtKamainNum.setText(findNum(nambaName[4]));
                txtDerunAmanNum.setText(findNum(nambaName[5]));

                nambaGoal(goal);
                //필요카드
                TextView txtAmanNeedNum = nambaDialog.findViewById(R.id.txtShandiNeedNum);
                TextView txtSeriaNeedNum = nambaDialog.findViewById(R.id.txtAzenaInannaNeedNum);
                TextView txtSolasNeedNum = nambaDialog.findViewById(R.id.txtNinabNeedNum);
                TextView txtKingSillianNeedNum = nambaDialog.findViewById(R.id.txtKadanNeedNum);
                TextView txtKamainNeedNum = nambaDialog.findViewById(R.id.txtBahunturNeedNum);
                TextView txtDerunAmanNeedNum = nambaDialog.findViewById(R.id.txtSillianNeedNum);
                txtAmanNeedNum.setText(findNambaNeedCard(namba, nambaName[0]) + "");
                txtSeriaNeedNum.setText(findNambaNeedCard(namba, nambaName[1]) + "");
                txtSolasNeedNum.setText(findNambaNeedCard(namba, nambaName[2]) + "");
                txtKingSillianNeedNum.setText(findNambaNeedCard(namba, nambaName[3]) + "");
                txtKamainNeedNum.setText(findNambaNeedCard(namba, nambaName[4]) + "");
                txtDerunAmanNeedNum.setText(findNambaNeedCard(namba, nambaName[5]) + "");

                TextView txtNeedCard = nambaDialog.findViewById(R.id.txtNeedCard);
                txtNeedCard.setText(goal + "각 까지 최소 필요 카드 수 : " + Arrays.stream(nambaNeedCard).sum());

                nambaDialog.show();
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
        //세구빛, 남바절 다음 각성까지 얼마나
        btnSegubit = findViewById(R.id.btnSegubit);
        btnNamba = findViewById(R.id.btnNamba);
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

    private ArrayList<CardInfo> segubit;
    private int[] segubitNeedCard;

    private void setSegubit() {
        segubit = new ArrayList<>();
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo tempInfo = new CardInfo();
            if (cardInfo.get(i).getName().equals(segubitName[0])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[1])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[2])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[3])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[4])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[5])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(segubitName[6])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                segubit.add(tempInfo);
            }
        }
    }


    //세구빛 카드 필요카드
    private int findSegubitNeedCard(ArrayList<CardInfo> segubit, String name) {
        for (int i = 0; i < segubit.size(); i++) {
            if (segubit.get(i).getName().equals(name))
                return segubitNeedCard[i];
        }
        return 0;
    }

    private void segubitGoal(int goal) {
        segubitNeedCard = new int[]{0, 0, 0, 0, 0, 0};
        //각성도 순 정렬 완료.
        Collections.sort(segubit, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getAwake() < o2.getAwake())
                    return 1;
                else
                    return -1;
            }
        });
        int minIndex = 0;
        //가장 낮은 각성도 수치가 같은 카드
        for (int i = 0; i < segubit.size(); i++) {
            if (segubit.get(i).getAwake() <= segubit.get(minIndex).getAwake()) {
                minIndex = i;
            }
        }
        //보유카드가 가장 적은 카드를 리스트에서 지우기 위해(카단 우선순위 낮춤)
        for (int i = 0; i < segubit.size(); i++) {
            if (segubit.get(i).getAwake() == segubit.get(minIndex).getAwake()) {
                if (segubit.get(i).getNum() <= segubit.get(minIndex).getNum()) {
                    if (segubit.get(minIndex).getName().equals(segubitName[3])) {
                        continue;
                    } else {
                        minIndex = i;
                    }
                }
            }
        }


        segubit.remove(minIndex);

        Collections.sort(segubit, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if ((o1.nextAwake() - o1.getNum()) < (o2.nextAwake() - o2.getNum()))
                    return -1;
                else
                    return 1;
            }
        });
        if (goal == 18) {
            int i = 0;
            while (!(getHaveAwake(segubit) >= goal)) {
                if ((getHaveAwake(segubit) >= goal))
                    break;
                //현재 인덱스의 카드보다 각성도가 낮거나 보유카드가 작은 카드가 있는 경우 continue;
                if (smallerThanOtherCardsNextAwake(i, segubit)) {
                    i++;
                    if (i == segubit.size()) {
                        i = 0;
                    }
                    continue;
                }

                //각성에 필요한 카드가 충분한 경우
                if (segubit.get(i).getNum() >= segubit.get(i).nextAwake()) {
                    segubit.get(i).setNum(segubit.get(i).getNum() - segubit.get(i).nextAwake());
                    segubit.get(i).setAwake(segubit.get(i).getAwake() + 1);
                } //각성에 필요한 카드가 충분하지 않은 경우 or 각성에 필요한 카드가 없는 경우
                else if (segubit.get(i).getNum() < segubit.get(i).nextAwake()) {
                    segubitNeedCard[i] = segubitNeedCard[i] + (segubit.get(i).nextAwake() - segubit.get(i).getNum());
                    segubit.get(i).setAwake(segubit.get(i).getAwake() + 1);
                    segubit.get(i).setNum(0);
                }

                i++;

                //무한루프(목표 각성도가 될때까지)
                if (i == segubit.size()) {
                    i = 0;
                }
            }
        } else if (goal == 30) {
            for (int i = 0; i < segubitNeedCard.length; i++) {
                segubitNeedCard[i] = segubit.get(i).awakeMax();
            }
        }
    }

    //해당 인덱스의 카드보다 다음 각성에 필요한 카드 수가 적은 경우 true
    private boolean smallerThanOtherCardsNextAwake(int index, ArrayList<CardInfo> seguNamba) {
        if (seguNamba.get(index).nextAwake() - seguNamba.get(index).getNum() <= 0)
            return false;
        for (int i = 0; i < seguNamba.size(); i++) {
            if (i == index)
                continue;
            if (seguNamba.get(i).nextAwake() - seguNamba.get(i).getNum() < seguNamba.get(index).nextAwake() - seguNamba.get(index).getNum()) {
                return true;
            }
        }
        return false;
    }

    private int getHaveAwake(ArrayList<CardInfo> setInfo) {
        int awakeSum = 0;
        int min = 5;
        for (int i = 0; i < setInfo.size(); i++) {
            awakeSum += setInfo.get(i).getAwake();
            if (min > setInfo.get(i).getAwake()) {
                min = setInfo.get(i).getAwake();
            }
        }

        return awakeSum;
    }

    private int nowSegubit() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("세상을 구하는 빛")) {
                return cardSetInfo.get(i).getHaveAwake();
            }
        }
        return 0;
    }

    private int nextSegubit() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("세상을 구하는 빛")) {
                if (cardSetInfo.get(i).getHaveAwake() < 18)
                    return 18;
                else
                    return 30;
            }
        }
        return 18;
    }


    private ArrayList<CardInfo> namba;
    private int[] nambaNeedCard;

    //남바절
    private void setNamba() {
        namba = new ArrayList<>();
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo tempInfo = new CardInfo();
            if (cardInfo.get(i).getName().equals(nambaName[0])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(nambaName[1])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(nambaName[2])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(nambaName[3])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(nambaName[4])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }
            if (cardInfo.get(i).getName().equals(nambaName[5])) {
                tempInfo.setId(cardInfo.get(i).getId());
                tempInfo.setName(cardInfo.get(i).getName());
                tempInfo.setAwake(cardInfo.get(i).getAwake());
                tempInfo.setNum(cardInfo.get(i).getNum());
                tempInfo.setGetCard(cardInfo.get(i).getGetCard());
                tempInfo.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                tempInfo.setPath(cardInfo.get(i).getPath());
                namba.add(tempInfo);
            }

        }
    }

    private void nambaGoal(int goal) {
        nambaNeedCard = new int[]{0, 0, 0, 0, 0, 0};
        Collections.sort(namba, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getAwake() < o2.getAwake())
                    return -1;
                else
                    return 1;
            }
        });
        if (goal == 12) {
            int i = 0;
            while (!(getHaveAwake(namba) >= goal)) {
                if ((getHaveAwake(namba) >= goal))
                    break;
                //현재 인덱스의 카드보다 각성도가 낮거나 보유카드가 작은 카드가 있는 경우 continue;
                if (smallerThanOtherCardsNextAwake(i, namba)) {
                    i++;
                    if (i == namba.size()) {
                        i = 0;
                    }
                    continue;
                }

                //각성에 필요한 카드가 충분한 경우
                if (namba.get(i).getNum() >= namba.get(i).nextAwake()) {
                    namba.get(i).setNum(namba.get(i).getNum() - namba.get(i).nextAwake());
                    namba.get(i).setAwake(namba.get(i).getAwake() + 1);
                } //각성에 필요한 카드가 충분하지 않은 경우 or 각성에 필요한 카드가 없는 경우
                else if (namba.get(i).getNum() < namba.get(i).nextAwake()) {
                    nambaNeedCard[i] = nambaNeedCard[i] + (namba.get(i).nextAwake() - namba.get(i).getNum());
                    namba.get(i).setAwake(namba.get(i).getAwake() + 1);
                    namba.get(i).setNum(0);
                }

                i++;

                //무한루프(목표 각성도가 될때까지)
                if (i == namba.size()) {
                    i = 0;
                }
            }
        } else {
            for (int i = 0; i < nambaNeedCard.length; i++) {
                nambaNeedCard[i] = namba.get(i).awakeMax();
            }
        }
    }


    private int nowNamba() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("남겨진 바람의 절벽")) {
                return cardSetInfo.get(i).getHaveAwake();
            }
        }
        return 0;
    }

    private int nextNamba() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("남겨진 바람의 절벽")) {
                if (cardSetInfo.get(i).getHaveAwake() < 12)
                    return 12;
                else
                    return 30;
            }
        }
        return 12;
    }

    //남바절 카드 필요카드
    private int findNambaNeedCard(ArrayList<CardInfo> namba, String name) {
        for (int i = 0; i < namba.size(); i++) {
            if (namba.get(i).getName().equals(name))
                return nambaNeedCard[i];
        }
        return 0;
    }

    //카드 보유 수
    private String findNum(String name) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (name.equals(cardInfo.get(i).getName())) {
                return cardInfo.get(i).getNum() + "";
            }
        }
        return "0";
    }

    //카드 각성도
    private String findAwake(String name) {
        for (int i = 0; i < cardInfo.size(); i++) {
            if (name.equals(cardInfo.get(i).getName())) {
                return cardInfo.get(i).getAwake() + "";
            }
        }
        return "0";
    }

}
