package com.example.lostarkcardstatus;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardBook_page extends AppCompatActivity {
    private static final String CRITICAL = "치명 + ";
    private static final String SPECIALITY = "특화 + ";
    private static final String AGILITY = "신속 + ";
    private final String CRITICAL_BOOK_COMPLETE = "치명 도감 달성 개수 : ";
    private final String AGILITY_BOOK_COMPLETE = " 도감 달성 개수 : ";
    private final String SPECIALITY_BOOK_COMPLETE = "특화 도감 달성 개수 : ";
    private RecyclerView rv;
    private LOA_Card_DB dbHelper;
    private ArrayList<Cardbook_All> allCardBook;

    private CheckBox checkboxCompleteCardbookInvisibility;
    private TextView txtBtnCritical;
    private TextView txtBtnAgility;
    private TextView txtBtnSpeciality;
    private TextView txtBtnNotAchievedSpecificityCritical;
    private TextView txtBtnNotAchievedSpecificitySpeciality;
    private TextView txtBtnNotAchievedSpecificityAgility;

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
        CardBook_Adapter adapter = new CardBook_Adapter(this, this);

        rv.setAdapter(adapter);

        txtBtnCritical = (TextView) findViewById(R.id.txtBtnCritical);
        txtBtnAgility = (TextView) findViewById(R.id.txtBtnAgility);
        txtBtnSpeciality = (TextView) findViewById(R.id.txtBtnSpeciality);
        txtBtnNotAchievedSpecificityCritical = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificityCritical);
        txtBtnNotAchievedSpecificitySpeciality = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificitySpeciality);
        txtBtnNotAchievedSpecificityAgility = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificityAgility);

        setStatAndStatBook(adapter.getHaveStat(),adapter.getHaveStatCardBookCount(),adapter.getHaveStatCardBook());


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

    public void setStatAndStatBook(int[] stat, int[] statBookComplete, int[] statBookAll) {
        txtBtnCritical.setText(CRITICAL + stat[0]);
        txtBtnSpeciality.setText(SPECIALITY + stat[1]);
        txtBtnAgility.setText(AGILITY + stat[2]);
        txtBtnNotAchievedSpecificityCritical.setText(CRITICAL_BOOK_COMPLETE + statBookComplete[0] + "/" + statBookAll[0] + "개");
        txtBtnNotAchievedSpecificitySpeciality.setText(SPECIALITY_BOOK_COMPLETE + statBookComplete[1] + "/" + statBookAll[1] + "개");
        txtBtnNotAchievedSpecificityAgility.setText(AGILITY_BOOK_COMPLETE + statBookComplete[2] + "/" + statBookAll[2] + "개");
    }

}
