package com.example.lostarkcardstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private LOA_Card_DB cardDBHelper;
    private ArrayList<CardInfo> cardLegend;
    private ArrayList<Cardbook> cardbookCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_main);

        setInit();


        //카드 세트로 이동.
        TextView txtBtnCardSet = (TextView) findViewById(R.id.txtBtnCardSet);
        txtBtnCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSet_page.class);
                startActivity(intent);
            }
        });

        //카드 세트 디테일 페이지로 바로 이동.(imgCardSet1-3 클릭시)
        ImageView imgCardSet1 = (ImageView)findViewById(R.id.imgCardSet1);
        imgCardSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSet_Detail.class);
                startActivity(intent);
            }
        });

        //카드 도감으로 이동.
        TextView txtBtnCardBook = (TextView) findViewById(R.id.txtBtnCardBookStats);
        txtBtnCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBook_page.class);
                startActivity(intent);
            }
        });
        //악마 추가 피해로 이동
        TextView txtBtnDemonExtraDmg = (TextView) findViewById(R.id.txtBtnDemonExtraDmg);
        txtBtnDemonExtraDmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemonExtraDmg_page.class);
                startActivity(intent);
            }
        });

    }

    private void setInit() {
        cardDBHelper = new LOA_Card_DB(this);

        cardLegend = new ArrayList<>();
        cardbookCritical = new ArrayList<>();
    }

    //뒤로가기 2회 터치시 종료(2.5초 안에 두번 눌러야 함)
    private long backKeyPressedTime = 0;
    private Toast finish;

    @Override
    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressedTime + 2500){
            backKeyPressedTime = System.currentTimeMillis();
            finish = Toast.makeText(this,"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_LONG);
            finish.show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2500){
            finish();
            finish.cancel();
        }
    }

}
