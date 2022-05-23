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

public class MainPage extends AppCompatActivity {
    private CardDBHelper cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;                 //카드목록
    protected ArrayList<CardSetInfo> favoriteCardSetInfo;   //즐겨찾기 목록
    protected ArrayList<CardBookInfo> cardBookInfo;         //카드 도감 목록
    protected ArrayList<CardSetInfo> cardSetInfo;           //카드 세트 목록
    protected ArrayList<ExtraDmgInfo> beastExtraDmgInfo;    //야추피 목록
    protected ArrayList<ExtraDmgInfo> demonExtraDmgInfo;    //악추피 목록
    protected ArrayList<ExtraDmgInfo> humanExtraDmgInfo;    //인추피 목록

    private TextView txtBtnCardSet;
    private TextView txtBtnCardBook;

    private RecyclerView rvFavorite;                        //카드세트의 즐겨찾기 리스트를 보여주기 위한 리사이클러뷰
    protected FavoriteAdapter favoriteAdapter;              //즐겨찾기 어뎁터

    private String[] EDName = {"악마", "야수", "인간", "정령", "기계", "불사", "식물", "물질", "곤충"};
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
    private TextView txtBtnHED_Draw;                //메뉴 안에 있는 야추피 페이지 버튼
    private TextView txtBtnCardList;                //메뉴 안에 있는 카드 목록 페이지 버튼

    //메인화면 값들
    private TextView txtCardBookStat_Critical;      //메인 화면에 있는 카드 도감 스텟 : 치명
    private TextView txtCardBookStat_Speciality;    //메인 화면에 있는 카드 도감 스텟 : 특화
    private TextView txtCardBookStat_Agility;       //메인 화면에 있는 카드 도감 스텟 : 신속

    public static Context mainContext;


    private Button btnGuide;

    private CardSetNeedCardInfo cardSetNeedCardInfo;
    private CardSetInfo selectedCardSet;

    private Button btnSegubit;
    private Button btnNamba;
    private Button btnAmgubit;

    protected SharedPreferences preferences;        //최초 실행시 가이드 페이지 호출을 위한 변수

    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String TABLE_BEAST_EXTRA_DMG = "beast_extra_dmg";  //야추피 테이블 명
    private static final String TABLE_HUMAN_EXTRA_DMG = "human_extra_dmg";  //야추피 테이블 명
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

        //DB정보 가져오기
        try {
            setInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //카드 DB 정보 ArrayList 전달
        cardInfo = cardDBHelper.getCardInfo();
        //카드 세트, 즐겨찾기 카드세트, 카드 도감 DB 정보 ArrayList 전달
        cardSetInfo = cardDBHelper.getCardSetInfo();
        cardBookInfo = cardDBHelper.getCardBookInfo();

        //추피 DB 정보 ArrayList 전달
        demonExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_DEMON_EXTRA_DMG);
        beastExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_BEAST_EXTRA_DMG);
        humanExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_HUMAN_EXTRA_DMG);

        extraDmgList = new ArrayList<>();
        extraDmgList.add(demonExtraDmgInfo);
        extraDmgList.add(beastExtraDmgInfo);
        extraDmgList.add(humanExtraDmgInfo);


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
                selectCardSet("세상을 구하는 빛");
                cardSetNeedCardInfo = new CardSetNeedCardInfo(selectedCardSet, goal);

                TextView txtSegubitAwake = segubitDialog.findViewById(R.id.txtSegubitAwake);
                txtSegubitAwake.setText("현재 각성 합계 : " + cardSetNeedCardInfo.getHaveAwake());
                TextView txtGoal = segubitDialog.findViewById(R.id.txtGoal);
                txtGoal.setText(goal + "각성에 필요한 세구빛 카드 수");

                //각성도
                TextView txtShandiAwake = segubitDialog.findViewById(R.id.txtShandiAwake);
                TextView txtAzenaInannaAwake = segubitDialog.findViewById(R.id.txtAzenaInannaAwake);
                TextView txtNinabAwake = segubitDialog.findViewById(R.id.txtNinabAwake);
                TextView txtKadanAwake = segubitDialog.findViewById(R.id.txtKadanAwake);
                TextView txtBahunturAwake = segubitDialog.findViewById(R.id.txtBahunturAwake);
                TextView txtSillianAwake = segubitDialog.findViewById(R.id.txtSillianAwake);
                TextView txtWeiAwake = segubitDialog.findViewById(R.id.txtWeiAwake);
                txtShandiAwake.setText(cardSetNeedCardInfo.getAwakeCard0() + "");
                txtAzenaInannaAwake.setText(cardSetNeedCardInfo.getAwakeCard1() + "");
                txtNinabAwake.setText(cardSetNeedCardInfo.getAwakeCard2() + "");
                txtKadanAwake.setText(cardSetNeedCardInfo.getAwakeCard3() + "");
                txtBahunturAwake.setText(cardSetNeedCardInfo.getAwakeCard4() + "");
                txtSillianAwake.setText(cardSetNeedCardInfo.getAwakeCard5() + "");
                txtWeiAwake.setText(cardSetNeedCardInfo.getAwakeCard6() + "");
                //현재 보유카드
                TextView txtShandiNum = segubitDialog.findViewById(R.id.txtShandiNum);
                TextView txtAzenaInannaNum = segubitDialog.findViewById(R.id.txtAzenaInannaNum);
                TextView txtNinabNum = segubitDialog.findViewById(R.id.txtNinabNum);
                TextView txtKadanNum = segubitDialog.findViewById(R.id.txtKadanNum);
                TextView txtBahunturNum = segubitDialog.findViewById(R.id.txtBahunturNum);
                TextView txtSillianNum = segubitDialog.findViewById(R.id.txtSillianNum);
                TextView txtWeiNum = segubitDialog.findViewById(R.id.txtWeiNum);
                txtShandiNum.setText(cardSetNeedCardInfo.getNumCard0() + "");
                txtAzenaInannaNum.setText(cardSetNeedCardInfo.getNumCard1() + "");
                txtNinabNum.setText(cardSetNeedCardInfo.getNumCard2() + "");
                txtKadanNum.setText(cardSetNeedCardInfo.getNumCard3() + "");
                txtBahunturNum.setText(cardSetNeedCardInfo.getNumCard4() + "");
                txtSillianNum.setText(cardSetNeedCardInfo.getNumCard5() + "");
                txtWeiNum.setText(cardSetNeedCardInfo.getNumCard6() + "");

                //필요카드
                TextView txtShandiNeedNum = segubitDialog.findViewById(R.id.txtShandiNeedNum);
                TextView txtAzenaInannaNeedNum = segubitDialog.findViewById(R.id.txtAzenaInannaNeedNum);
                TextView txtNinabNeedNum = segubitDialog.findViewById(R.id.txtNinabNeedNum);
                TextView txtKadanNeedNum = segubitDialog.findViewById(R.id.txtKadanNeedNum);
                TextView txtBahunturNeedNum = segubitDialog.findViewById(R.id.txtBahunturNeedNum);
                TextView txtSillianNeedNum = segubitDialog.findViewById(R.id.txtSillianNeedNum);
                TextView txtWeiNeedNum = segubitDialog.findViewById(R.id.txtWeiNeedNum);
                txtShandiNeedNum.setText(cardSetNeedCardInfo.getCard0NeedCard() + "");
                txtAzenaInannaNeedNum.setText(cardSetNeedCardInfo.getCard1NeedCard() + "");
                txtNinabNeedNum.setText(cardSetNeedCardInfo.getCard2NeedCard() + "");
                txtKadanNeedNum.setText(cardSetNeedCardInfo.getCard3NeedCard() + "");
                txtBahunturNeedNum.setText(cardSetNeedCardInfo.getCard4NeedCard() + "");
                txtSillianNeedNum.setText(cardSetNeedCardInfo.getCard5NeedCard() + "");
                txtWeiNeedNum.setText(cardSetNeedCardInfo.getCard6NeedCard() + "");

                TextView txtNeedCard = segubitDialog.findViewById(R.id.txtNeedCard);
                txtNeedCard.setText(goal + "각 까지 최소 필요 카드 수 : " + cardSetNeedCardInfo.getCardSetNeedCardSum());

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
                selectCardSet("남겨진 바람의 절벽");
                cardSetNeedCardInfo = new CardSetNeedCardInfo(selectedCardSet, goal);

                TextView txtNambaName = nambaDialog.findViewById(R.id.txtSegubitName);
                txtNambaName.setText("남겨진 바람의 절벽");
                TextView txtSegubitAwake = nambaDialog.findViewById(R.id.txtSegubitAwake);
                txtSegubitAwake.setText("현재 각성 합계 : " + cardSetNeedCardInfo.getHaveAwake());
                TextView txtGoal = nambaDialog.findViewById(R.id.txtGoal);
                txtGoal.setText(goal + "각성에 필요한 남바절 카드 수");

                TextView txtAman = nambaDialog.findViewById(R.id.txtShandi);
                TextView txtSeria = nambaDialog.findViewById(R.id.txtAzenaInanna);
                TextView txtSolas = nambaDialog.findViewById(R.id.txtNinab);
                TextView txtKingSillian = nambaDialog.findViewById(R.id.txtKadan);
                TextView txtKamain = nambaDialog.findViewById(R.id.txtBahuntur);
                TextView txtDerunAman = nambaDialog.findViewById(R.id.txtSillian);
                txtAman.setText(cardSetNeedCardInfo.getCard0());
                txtSeria.setText(cardSetNeedCardInfo.getCard1());
                txtSolas.setText(cardSetNeedCardInfo.getCard2());
                txtKingSillian.setText(cardSetNeedCardInfo.getCard3());
                txtKamain.setText(cardSetNeedCardInfo.getCard4());
                txtDerunAman.setText(cardSetNeedCardInfo.getCard5());

                //각성도
                TextView txtAmanAwake = nambaDialog.findViewById(R.id.txtShandiAwake);
                TextView txtSeriaAwake = nambaDialog.findViewById(R.id.txtAzenaInannaAwake);
                TextView txtSolasAwake = nambaDialog.findViewById(R.id.txtNinabAwake);
                TextView txtKingSillianAwake = nambaDialog.findViewById(R.id.txtKadanAwake);
                TextView txtKamainAwake = nambaDialog.findViewById(R.id.txtBahunturAwake);
                TextView txtDerunAmanAwake = nambaDialog.findViewById(R.id.txtSillianAwake);
                txtAmanAwake.setText(cardSetNeedCardInfo.getAwakeCard0() + "");
                txtSeriaAwake.setText(cardSetNeedCardInfo.getAwakeCard1() + "");
                txtSolasAwake.setText(cardSetNeedCardInfo.getAwakeCard2() + "");
                txtKingSillianAwake.setText(cardSetNeedCardInfo.getAwakeCard3() + "");
                txtKamainAwake.setText(cardSetNeedCardInfo.getAwakeCard4() + "");
                txtDerunAmanAwake.setText(cardSetNeedCardInfo.getAwakeCard5() + "");
                //현재 보유카드
                TextView txtAmanNum = nambaDialog.findViewById(R.id.txtShandiNum);
                TextView txtSeriaNum = nambaDialog.findViewById(R.id.txtAzenaInannaNum);
                TextView txtSolasNum = nambaDialog.findViewById(R.id.txtNinabNum);
                TextView txtKingSillianNum = nambaDialog.findViewById(R.id.txtKadanNum);
                TextView txtKamainNum = nambaDialog.findViewById(R.id.txtBahunturNum);
                TextView txtDerunAmanNum = nambaDialog.findViewById(R.id.txtSillianNum);
                txtAmanNum.setText(cardSetNeedCardInfo.getNumCard0() + "");
                txtSeriaNum.setText(cardSetNeedCardInfo.getNumCard1() + "");
                txtSolasNum.setText(cardSetNeedCardInfo.getNumCard2() + "");
                txtKingSillianNum.setText(cardSetNeedCardInfo.getNumCard3() + "");
                txtKamainNum.setText(cardSetNeedCardInfo.getNumCard4() + "");
                txtDerunAmanNum.setText(cardSetNeedCardInfo.getNumCard5() + "");

                //필요카드
                TextView txtAmanNeedNum = nambaDialog.findViewById(R.id.txtShandiNeedNum);
                TextView txtSeriaNeedNum = nambaDialog.findViewById(R.id.txtAzenaInannaNeedNum);
                TextView txtSolasNeedNum = nambaDialog.findViewById(R.id.txtNinabNeedNum);
                TextView txtKingSillianNeedNum = nambaDialog.findViewById(R.id.txtKadanNeedNum);
                TextView txtKamainNeedNum = nambaDialog.findViewById(R.id.txtBahunturNeedNum);
                TextView txtDerunAmanNeedNum = nambaDialog.findViewById(R.id.txtSillianNeedNum);
                txtAmanNeedNum.setText(cardSetNeedCardInfo.getCard0NeedCard() + "");
                txtSeriaNeedNum.setText(cardSetNeedCardInfo.getCard1NeedCard() + "");
                txtSolasNeedNum.setText(cardSetNeedCardInfo.getCard2NeedCard() + "");
                txtKingSillianNeedNum.setText(cardSetNeedCardInfo.getCard3NeedCard() + "");
                txtKamainNeedNum.setText(cardSetNeedCardInfo.getCard4NeedCard() + "");
                txtDerunAmanNeedNum.setText(cardSetNeedCardInfo.getCard5NeedCard() + "");

                TextView txtNeedCard = nambaDialog.findViewById(R.id.txtNeedCard);
                txtNeedCard.setText(goal + "각 까지 최소 필요 카드 수 : " + cardSetNeedCardInfo.getCardSetNeedCardSum());

                nambaDialog.show();
            }
        });

        btnAmgubit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
                Dialog amgubitDialog = new Dialog(mainContext, android.R.style.Theme_Material_Light_Dialog);

                amgubitDialog.setContentView(R.layout.segubit);

                WindowManager.LayoutParams params = amgubitDialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                amgubitDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                TableRow lastRow = amgubitDialog.findViewById(R.id.lastRow);
                lastRow.setVisibility(View.GONE);

                goal = nextAmgubit();
                selectCardSet("카제로스의 군단장");
                cardSetNeedCardInfo = new CardSetNeedCardInfo(selectedCardSet, goal);

                TextView txtAmgubitName = amgubitDialog.findViewById(R.id.txtSegubitName);
                txtAmgubitName.setText("카제로스의 군단장");
                TextView txtAmgubitAwake = amgubitDialog.findViewById(R.id.txtSegubitAwake);
                txtAmgubitAwake.setText("현재 각성 합계 : " + cardSetNeedCardInfo.getHaveAwake());
                TextView txtGoal = amgubitDialog.findViewById(R.id.txtGoal);
                txtGoal.setText(goal + "각성에 필요한 암구빛 카드 수");

                TextView txtBaltan = amgubitDialog.findViewById(R.id.txtShandi);
                TextView txtIllakan = amgubitDialog.findViewById(R.id.txtAzenaInanna);
                TextView txtBiakiss = amgubitDialog.findViewById(R.id.txtNinab);
                TextView txtAbrelshud = amgubitDialog.findViewById(R.id.txtKadan);
                TextView txtKamen = amgubitDialog.findViewById(R.id.txtBahuntur);
                TextView txtKuksaiten = amgubitDialog.findViewById(R.id.txtSillian);
                txtBaltan.setText(cardSetNeedCardInfo.getCard0());
                txtIllakan.setText(cardSetNeedCardInfo.getCard1());
                txtBiakiss.setText(cardSetNeedCardInfo.getCard2());
                txtAbrelshud.setText(cardSetNeedCardInfo.getCard3());
                txtKamen.setText(cardSetNeedCardInfo.getCard4());
                txtKuksaiten.setText(cardSetNeedCardInfo.getCard5());

                //각성도
                TextView txtBaltanAwake = amgubitDialog.findViewById(R.id.txtShandiAwake);
                TextView txtIllakanAwake = amgubitDialog.findViewById(R.id.txtAzenaInannaAwake);
                TextView txtBiakissAwake = amgubitDialog.findViewById(R.id.txtNinabAwake);
                TextView txtAbrelshudAwake = amgubitDialog.findViewById(R.id.txtKadanAwake);
                TextView txtKamenAwake = amgubitDialog.findViewById(R.id.txtBahunturAwake);
                TextView txtKuksaitenAwake = amgubitDialog.findViewById(R.id.txtSillianAwake);
                txtBaltanAwake.setText(cardSetNeedCardInfo.getAwakeCard0() + "");
                txtIllakanAwake.setText(cardSetNeedCardInfo.getAwakeCard1() + "");
                txtBiakissAwake.setText(cardSetNeedCardInfo.getAwakeCard2() + "");
                txtAbrelshudAwake.setText(cardSetNeedCardInfo.getAwakeCard3() + "");
                txtKamenAwake.setText(cardSetNeedCardInfo.getAwakeCard4() + "");
                txtKuksaitenAwake.setText(cardSetNeedCardInfo.getAwakeCard5() + "");

                //현재 보유카드
                TextView txtBaltanNum = amgubitDialog.findViewById(R.id.txtShandiNum);
                TextView txtIllakanNum = amgubitDialog.findViewById(R.id.txtAzenaInannaNum);
                TextView txtBiakissNum = amgubitDialog.findViewById(R.id.txtNinabNum);
                TextView txtAbrelshudNum = amgubitDialog.findViewById(R.id.txtKadanNum);
                TextView txtKamenNum = amgubitDialog.findViewById(R.id.txtBahunturNum);
                TextView txtKuksaitenNum = amgubitDialog.findViewById(R.id.txtSillianNum);
                txtBaltanNum.setText(cardSetNeedCardInfo.getNumCard0() + "");
                txtIllakanNum.setText(cardSetNeedCardInfo.getNumCard1() + "");
                txtBiakissNum.setText(cardSetNeedCardInfo.getNumCard2() + "");
                txtAbrelshudNum.setText(cardSetNeedCardInfo.getNumCard3() + "");
                txtKamenNum.setText(cardSetNeedCardInfo.getNumCard4() + "");
                txtKuksaitenNum.setText(cardSetNeedCardInfo.getNumCard5() + "");

                //필요카드
                TextView txtBaltanNeedNum = amgubitDialog.findViewById(R.id.txtShandiNeedNum);
                TextView txtIllakanNeedNum = amgubitDialog.findViewById(R.id.txtAzenaInannaNeedNum);
                TextView txtBiakissNeedNum = amgubitDialog.findViewById(R.id.txtNinabNeedNum);
                TextView txtAbrelshudNeedNum = amgubitDialog.findViewById(R.id.txtKadanNeedNum);
                TextView txtKamenNeedNum = amgubitDialog.findViewById(R.id.txtBahunturNeedNum);
                TextView txtKuksaitenNeedNum = amgubitDialog.findViewById(R.id.txtSillianNeedNum);
                txtBaltanNeedNum.setText(cardSetNeedCardInfo.getCard0NeedCard() + "");
                txtIllakanNeedNum.setText(cardSetNeedCardInfo.getCard1NeedCard() + "");
                txtBiakissNeedNum.setText(cardSetNeedCardInfo.getCard2NeedCard() + "");
                txtAbrelshudNeedNum.setText(cardSetNeedCardInfo.getCard3NeedCard() + "");
                txtKamenNeedNum.setText(cardSetNeedCardInfo.getCard4NeedCard() + "");
                txtKuksaitenNeedNum.setText(cardSetNeedCardInfo.getCard5NeedCard() + "");

                TextView txtNeedCard = amgubitDialog.findViewById(R.id.txtNeedCard);
                txtNeedCard.setText(goal + "각 까지 최소 필요 카드 수 : " + cardSetNeedCardInfo.getCardSetNeedCardSum());

                amgubitDialog.show();
            }
        });


        preferences = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent guideIntent = new Intent(MainPage.this, GuidePage.class);
            startActivity(guideIntent);
            preferences.edit().putBoolean("isFirstRun", false).apply();
        }


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
        txtBtnHED_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getEDIndex("인간");
                intentED.putExtra("EDName", "인간");
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
        btnAmgubit = findViewById(R.id.btnAmgubit);

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
        txtBtnHED_Draw = (TextView) findViewById(R.id.txtBtnHED_Draw);

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


    private void selectCardSet(String cardSetName) {
        selectedCardSet = new CardSetInfo();
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals(cardSetName)) {
                selectedCardSet = cardSetInfo.get(i);
            }

        }
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

    private int nextAmgubit() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("카제로스의 군단장")) {
                if (cardSetInfo.get(i).getHaveAwake() < 18)
                    return 18;
                else
                    return 30;
            }
        }
        return 18;
    }

}
