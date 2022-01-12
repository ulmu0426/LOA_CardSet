package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DemonExtraDmg_page extends AppCompatActivity {
    private RecyclerView rv;
    private TextView txtDemonExtraDmg_DemonExtraPage;
    private TextView txtCompleteDED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demon_extra_dmg_page);
        /* 악마 추가 피해 페이지.
         *  작업 목록
         *  1. 악추피 도감 목록 불러오기
         *  2. 악추피 완성 도감 숨기기 기능(풀각만 숨김)
         * */
        txtDemonExtraDmg_DemonExtraPage = (TextView) findViewById(R.id.txtDemonExtraDmg_DemonExtraPage);
        txtCompleteDED = (TextView) findViewById(R.id.txtCompleteDED);
        rv = findViewById(R.id.rvDemonExtraDmg);
        DemonExtraDmgAdapter adapter = new DemonExtraDmgAdapter(this, this);
        rv.setAdapter(adapter);

        //DEDAdapter에서 값 정리를 다 하면 활성화 시킬것
        //setDED(adapter.getHaveDED());

    }

    public void setDED(float value) {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDemonExtraDmg_DemonExtraPage.setText("악마 추가 피해 + " + df.format(value) + "%");
    }

    public void setDEDBook(int completeDED, int DEDBook) {
        txtCompleteDED.setText("완성 도감 개수 : " + completeDED + "/" + DEDBook + "개");
    }
}
