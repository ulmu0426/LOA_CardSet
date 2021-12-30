package com.example.lostarkcardstatus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardBook_page extends AppCompatActivity {
    private RecyclerView rv;
    private LOA_Card_DB dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardbook_page);
        /* 카드 도감 페이지.
        *  이루어질 작업 목록
        *  1. 카드 도감 목록 불러오기
        *  2. 완성 도감 온 오프 기능
        *  3. 각 특성 클릭시 각 특성 스텟만 올려주는 도감 목록 으로 이동.
        *  4. 이름 순 정렬 기능.
        * */

        rv = findViewById(R.id.rvCardbookList);
        dbHelper = new LOA_Card_DB(this);
        ArrayList<Cardbook> agility = dbHelper.getCardBookInfo_Agility();
        CardBook_Adapter adapter = new CardBook_Adapter(agility, this);



    }
}
