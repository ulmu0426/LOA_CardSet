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
    private static final String CRITICAL = "치명";
    private static final String SPECIALITY = "특화";
    private static final String AGILITY = "신속";
    private RecyclerView rv;
    private LOA_Card_DB dbHelper;
    private ArrayList<Cardbook_All> allCardBook;
    private int critical;
    private int agility;
    private int speciality;

    private CheckBox checkboxCompleteCardbookInvisibility;
    private TextView txtBtnCritical;
    private TextView txtBtnAgility;
    private TextView txtBtnSpeciality;
    private int criticalCardBookCount = 0;
    private int agilityCardBookCount = 0;
    private int specialityCardBookCount = 0;

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


        txtBtnCritical = (TextView) findViewById(R.id.txtBtnCritical);
        txtBtnAgility = (TextView) findViewById(R.id.txtBtnAgility);
        txtBtnSpeciality = (TextView) findViewById(R.id.txtBtnSpeciality);
        TextView txtBtnNotAchievedSpecificityCritical = (TextView)findViewById(R.id.txtBtnNotAchievedSpecificityCritical);
        TextView txtBtnNotAchievedSpecificitySpeciality = (TextView)findViewById(R.id.txtBtnNotAchievedSpecificitySpeciality);
        TextView txtBtnNotAchievedSpecificityAgility = (TextView)findViewById(R.id.txtBtnNotAchievedSpecificityAgility);

        int statCardBookCount = 0;

        for(int i = 0; i < allCardBook.size(); i++){
            if(allCardBook.get(i).getOption().equals(CRITICAL))
                statCardBookCount++;
            if(allCardBook.get(i).getOption().equals(CRITICAL) && adapter.isCompleteCardBook(allCardBook.get(i))){
                criticalCardBookCount++;
                critical += allCardBook.get(i).getValue();
            }
        }
        txtBtnNotAchievedSpecificityCritical.setText("치명 도감 달성 개수 : " + criticalCardBookCount +"/"+statCardBookCount+"개");
        statCardBookCount = 0;
        for(int i = 0; i < allCardBook.size(); i++){
            if(allCardBook.get(i).getOption().equals(AGILITY))
                statCardBookCount++;
            if(allCardBook.get(i).getOption().equals(AGILITY) && adapter.isCompleteCardBook(allCardBook.get(i))){
                specialityCardBookCount++;
                agility += allCardBook.get(i).getValue();
            }
        }
        txtBtnNotAchievedSpecificitySpeciality.setText("특화 도감 달성 개수 : " + specialityCardBookCount +"/"+statCardBookCount+"개");
        statCardBookCount = 0;
        for(int i = 0; i < allCardBook.size(); i++){
            if(allCardBook.get(i).getOption().equals(SPECIALITY))
                statCardBookCount++;
            if(allCardBook.get(i).getOption().equals(SPECIALITY) && adapter.isCompleteCardBook(allCardBook.get(i))){
                agilityCardBookCount++;
                speciality += allCardBook.get(i).getValue();
            }
        }
        txtBtnNotAchievedSpecificityAgility.setText("신속 도감 달성 개수 : " + agilityCardBookCount +"/"+statCardBookCount+"개");
        txtBtnCritical.setText("치명 + " + critical);
        txtBtnSpeciality.setText("특화 + " + speciality);
        txtBtnAgility.setText("신속 + " + agility);



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

    private void getStatus(ArrayList<Cardbook_All> cardbook_all, CardBook_Adapter adapter, int status, String STAT){
        for(int i = 0; i < cardbook_all.size(); i++){
            if(cardbook_all.get(i).getOption().equals(STAT) && adapter.isCompleteCardBook(cardbook_all.get(i)))
                status += cardbook_all.get(i).getValue();
        }
    }


}
