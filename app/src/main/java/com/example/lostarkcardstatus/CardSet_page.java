package com.example.lostarkcardstatus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardSet_page extends AppCompatActivity {

    private RecyclerView rv;
    private LOA_Card_DB dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_set_page);
        /* 카드 세트 페이지.
         *  작업 목록
         *  1. 카드 세트 목록 불러오기
         *  2. 카드 세트 클릭시 카드세트 세부 페이지로 이동
         *  3. 카드 세트 즐겨찾기 기능(메인페이지에 띄울 카드 세트 지정)
         * */

        rv = findViewById(R.id.rvCardSet);
        dbHelper = new LOA_Card_DB(this);
        CardSetAdapter adapter = new CardSetAdapter(this);

        rv.setAdapter(adapter);


    }
}
