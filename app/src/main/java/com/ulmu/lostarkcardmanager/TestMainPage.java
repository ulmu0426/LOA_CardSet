package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;

public class TestMainPage extends AppCompatActivity {
    private CardDBHelper cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;         //카드목록
    protected ArrayList<TestExtraDmgInfo> beastExtraDmgInfo; //야추피 목록
    protected ArrayList<TestExtraDmgInfo> demonExtraDmgInfo; //악추피 목록

    private ViewPager2 vpXED;
    private TestMainPageExtraDmgViewPagerAdapter extraDmgViewPagerAdapter;
    private ArrayList<ArrayList<TestExtraDmgInfo>> extraDmgList;

    private DrawerLayout drawerLayout_Main;         //메인페이지 메뉴

    private ImageView imgBtnMenu_Main;              //메뉴 버튼

    public static Context testMainContext;

    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String TABLE_BEAST_EXTRA_DMG = "beast_extra_dmg";  //야추피 테이블 명

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_main_page);
        testMainContext = this;


        //DB정보 가져오기
        try {
            setInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //카드 DB 정보 ArrayList 전달
        cardInfo = cardDBHelper.getCardInfo_All();

        //추피 DB 정보 ArrayList 전달
        demonExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_DEMON_EXTRA_DMG);
        beastExtraDmgInfo = cardDBHelper.getExtraDmgInfo(TABLE_BEAST_EXTRA_DMG);

        extraDmgList = new ArrayList<>();
        extraDmgList.add(demonExtraDmgInfo);
        extraDmgList.add(beastExtraDmgInfo);

        extraDmgViewPagerAdapter = new TestMainPageExtraDmgViewPagerAdapter(this, extraDmgList);
        vpXED = findViewById(R.id.vpExtraDmg);
        vpXED.setAdapter(extraDmgViewPagerAdapter);

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

    public void setExtraDmgList(String EDName, ArrayList<TestExtraDmgInfo> extraDmgInfo) {
        String[] extraDmgName = {"악마", "야수", "정령", "인간", "기계", "불사", "식물", "물질"};
        for (int i = 0; i < extraDmgName.length; i++) {
            if (extraDmgName[i].equals(EDName)) {
                extraDmgList.remove(i);
                extraDmgList.add(i, extraDmgInfo);
            }
        }
        extraDmgViewPagerAdapter.setExtraDmgValue(extraDmgList);
    }

}
