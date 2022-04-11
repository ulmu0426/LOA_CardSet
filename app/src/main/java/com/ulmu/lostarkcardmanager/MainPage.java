package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    private CardDBHelper cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;         //카드목록
    protected ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo;   //즐겨찾기 목록
    protected ArrayList<CardBookInfo> cardBookInfo; //카드 도감 목록
    protected ArrayList<CardSetInfo> cardSetInfo;   //카드 세트 목록
    protected ArrayList<DemonExtraDmgInfo> DEDInfo; //악추피 목록
    private DrawerLayout drawerLayout_Main;         //메인페이지 메뉴

    protected FavoriteAdapter favoriteAdapter;      //즐겨찾기 어뎁터
    private RecyclerView rv;                        //카드세트의 즐겨찾기 리스트를 보여주기 위한 리사이클러뷰

    private ImageView imgBtnMenu_Main;              //메뉴 버튼

    private TextView txtBtnCardSet_Draw;            //메뉴 안에 있는 카드 세트 페이지 버튼
    private TextView txtBtnCardBook_Draw;           //메뉴 안에 있는 카드 도감 페이지 버튼
    private TextView txtBtnDED_Draw;                //메뉴 안에 있는 악추피 페이지 버튼
    private TextView txtBtnCardList;                //메뉴 안에 있는 카드 목록 페이지 버튼

    //메인화면 값들
    private TextView txtCardBookStat_Critical;      //메인 화면에 있는 카드 도감 스텟 : 치명
    private TextView txtCardBookStat_Speciality;    //메인 화면에 있는 카드 도감 스텟 : 특화
    private TextView txtCardBookStat_Agility;       //메인 화면에 있는 카드 도감 스텟 : 신속
    private TextView txtDemonExtraDmg;              //메인 화면에 있는 악추피 값

    public static Context mainContext;

    private Button btnGuide;
    protected SharedPreferences preferences;        //최초 실행시 가이드 페이지 호출을 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);
        mainContext = this;

        btnGuide = findViewById(R.id.txtGuide);
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


        //치,특,신 값
        txtCardBookStat_Critical = (TextView) findViewById(R.id.txtCardBookStat_Critical);
        txtCardBookStat_Speciality = (TextView) findViewById(R.id.txtCardBookStat_Speciality);
        txtCardBookStat_Agility = (TextView) findViewById(R.id.txtCardBookStat_Agility);

        //악추피 값
        txtDemonExtraDmg = (TextView) findViewById(R.id.txtDEDExtraDmg);

        //DB정보 가져오기
        try {
            setInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //카드 DB 정보 ArrayList 전달
        cardInfo = cardDBHelper.getCardInfo_All();

        //카드 도감 DB 정보 ArrayList 전달
        cardBookInfo = cardDBHelper.getCardBookInfo();
        cardSetInfo = cardDBHelper.getCardSetInfo();

        //악추피 DB 정보 ArrayList 전달
        DEDInfo = cardDBHelper.getDemonExtraDmgInfo();

        favoriteCardSetInfo = cardDBHelper.getFavoriteCardSetInfo();

        //cardList Table 정보를 cardBookInfo,DEDInfo,cardSetInfo List와 DB에 연동
        favoriteUpdate();           //카드세트 즐겨찾기 DB 업데이트


        //치,특,신 값 출력
        int[] stat = {getStatInfo("치명"), getStatInfo("특화"), getStatInfo("신속")};
        setCardBookStatInfo(stat);
        //악추피 값 출력
        float DEDValue = getDEDValueInfo();
        setDemonExtraDmgInfo(DEDValue);

        rv = (RecyclerView) findViewById(R.id.rvCardSet);
        favoriteAdapter = new FavoriteAdapter(mainContext);
        rv.setAdapter(favoriteAdapter);

        //drawerLayout
        imgBtnMenu_Main = (ImageView) findViewById(R.id.imgBtnMenu_Main);
        drawerLayout_Main = (DrawerLayout) findViewById(R.id.drawerLayout_Main);
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

        //drawerLayout 메뉴에 카드목록 터치시 이동
        txtBtnCardList = (TextView) findViewById(R.id.txtBtnCardList);
        txtBtnCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingCard.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //drawerLayout 메뉴에 카드세트 터치시 이동
        txtBtnCardSet_Draw = (TextView) findViewById(R.id.txtBtnCardSet_Draw);
        txtBtnCardSet_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSetPage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //drawerLayout 메뉴에 카드도감 터치시 이동
        txtBtnCardBook_Draw = (TextView) findViewById(R.id.txtBtnCardBook_Draw);
        txtBtnCardBook_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBookPage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //drawerLayout 메뉴에 악추피 터치시 이동
        txtBtnDED_Draw = (TextView) findViewById(R.id.txtBtnDEDExtraDmg);
        txtBtnDED_Draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemonExtraDmgPage.class);
                startActivity(intent);
                drawerLayout_Main.closeDrawer(Gravity.LEFT);
            }
        });

        //카드 세트로 이동.
        TextView txtBtnCardSet = (TextView) findViewById(R.id.txtBtnCardSet);
        txtBtnCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSetPage.class);
                startActivity(intent);
            }
        });

        //카드 도감으로 이동.
        TextView txtBtnCardBook = (TextView) findViewById(R.id.txtBtnCardBookStats);
        txtBtnCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBookPage.class);
                startActivity(intent);
            }
        });
        //악마 추가 피해로 이동
        TextView txtBtnDemonExtraDmg = (TextView) findViewById(R.id.txtBtnDEDExtraDmg);
        txtBtnDemonExtraDmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemonExtraDmgPage.class);
                startActivity(intent);
            }
        });

    }

    //DB정보 App에 입력
    private void setInit() throws IOException {
        cardDBHelper = new CardDBHelper(this);
        cardDBHelper.createDataBase();

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

    //악추피 도감 악추피 값 세팅
    private float getDEDValueInfo() {
        float a = 0;
        DemonExtraDmgAdapter DEDAdapter = new DemonExtraDmgAdapter(DEDInfo);
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && DEDInfo.get(i).getHaveAwake() == DEDInfo.get(i).getAwakeSum2()) {
                a += DEDInfo.get(i).getDmg_p2() + DEDInfo.get(i).getDmg_p1() + DEDInfo.get(i).getDmg_p0();
            } else if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && (DEDInfo.get(i).getHaveAwake() >= DEDInfo.get(i).getAwakeSum1() && DEDInfo.get(i).getHaveAwake() < DEDInfo.get(i).getAwakeSum2())) {
                a += DEDInfo.get(i).getDmg_p1() + DEDInfo.get(i).getDmg_p0();
            } else if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && (DEDInfo.get(i).getHaveAwake() >= DEDInfo.get(i).getAwakeSum0() && DEDInfo.get(i).getHaveAwake() < DEDInfo.get(i).getAwakeSum1())) {
                a += DEDInfo.get(i).getDmg_p0();
            }
        }
        return a;
    }

    //MainPage 악추피 수치 update
    public void setDemonExtraDmgInfo(float value) {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDemonExtraDmg.setText(df.format(value) + "%");
    }


    //최초 실행되는 메소드 : cardSet 정보에서 즐겨찾기 기능 DB와 연동
    private void favoriteUpdate() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            for (int j = 0; j < favoriteCardSetInfo.size(); j++) {
                if (cardSetInfo.get(i).getName().equals(favoriteCardSetInfo.get(j).getName()) && cardSetInfo.get(i).getFavorite()) {
                    Log.v("test", "cardSetInfo : favorite status : "+ cardSetInfo.get(i).getName() + cardSetInfo.get(i).getFavorite());
                    //각성도 정보 및 즐겨찾기 활성화 여부 업데이트
                    favoriteCardSetInfo.get(j).setActivation(true); //즐겨찾기 활성화
                    favoriteCardSetInfo.get(j).setAwake(cardSetInfo.get(i).getHaveAwake());
                }

            }
        }
    }

}
