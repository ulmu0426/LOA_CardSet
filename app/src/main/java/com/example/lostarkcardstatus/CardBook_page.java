package com.example.lostarkcardstatus;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardBook_page extends AppCompatActivity {
    private RecyclerView rv;
    private LOA_Card_DB dbHelper;
    private ArrayList<Cardbook_All> allCardBook;

    private CheckBox checkboxCompleteCardbookInvisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardbook_page);
        /* 카드 도감 페이지.
         *  이루어질 작업 목록
         *  1. 카드 도감 목록 불러오기
         *  2. 완성 도감 온 오프 기능
         *  3. 각 특성 클릭시 각 특성 스텟만 올려주는 도감 목록 으로 이동
         *  4. 이름 순 정렬 기능
         *  5. 도감 검색 기능
         * */


        //1. 카드 도감 목록 불러오기
        rv = findViewById(R.id.rvCardbookList);
        dbHelper = new LOA_Card_DB(this);
        allCardBook = dbHelper.getCardBookInfo_All();
        CardBook_Adapter adapter = new CardBook_Adapter(allCardBook, this);

        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //2. 완성 도감 온 오프 기능
        checkboxCompleteCardbookInvisibility = (CheckBox) findViewById(R.id.checkboxCompleteCardbookInvisibility);
        checkboxCompleteCardbookInvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxCompleteCardbookInvisibility.isChecked()) {   //체크되어있으면 완성도감 안보이게

                } else {                                                   //미체크시 완성도감 보이게

                }
            }
        });


    }

}
