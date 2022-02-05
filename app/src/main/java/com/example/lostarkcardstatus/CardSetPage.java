package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CardSetPage extends AppCompatActivity {

    private RecyclerView rv;
    private CardDBHelper dbHelper;
    private CheckBox checkBoxInvisibilityCardSetPage;

    private CharSequence check;

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
        dbHelper = new CardDBHelper(this);
        CardSetAdapter adapter = new CardSetAdapter(this);

        rv.setAdapter(adapter);

        checkBoxInvisibilityCardSetPage = findViewById(R.id.checkBoxInvisibilityCardSetPage);
        checkBoxInvisibilityCardSetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxInvisibilityCardSetPage.isChecked()){
                    check = "notNull";
                }else {
                    check = "";
                }
                adapter.getFilter().filter(check);
            }
        });

    }
}
