package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DemonExtraDmg_page extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;
    private LOA_Card_DB DbHelper;
    private TextView txtDemonExtraDmg_DemonExtraPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demon_extra_dmg_page);
        /* 악마 추가 피해 페이지.
         *  작업 목록
         *  1. 악추피 도감 목록 불러오기
         *  2. 악추피 완성 도감 숨기기 기능(풀각만 숨김)
         * */

        rv = findViewById(R.id.rvDemonExtraDmg);
        DbHelper = new LOA_Card_DB(this);
        DEDInfo = DbHelper.getDemonExtraDmgInfo();
        DemonExtraDmgAdapter adapter = new DemonExtraDmgAdapter(this, this);

        rv.setAdapter(adapter);



    }
    public void setDED(float value, int DEDCount, int DEDBook){
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDemonExtraDmg_DemonExtraPage.setText("악마 추가 피해 + "+ df.format(value) + "%");
    }
}
