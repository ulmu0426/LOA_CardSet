package com.example.lostarkcardstatus;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DemonExtraDmg_page extends AppCompatActivity {
    private RecyclerView rv;
    private LOA_Card_DB dbHelper;

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
        dbHelper = new LOA_Card_DB(this);
        ArrayList<DemonExtraDmgInfo> demonExtraDmgInfoArrayList = dbHelper.getDemonExtraDmgInfo();
        DemonExtraDmgAdapter adapter = new DemonExtraDmgAdapter(demonExtraDmgInfoArrayList, this);

        rv.setAdapter(adapter);

    }
}
