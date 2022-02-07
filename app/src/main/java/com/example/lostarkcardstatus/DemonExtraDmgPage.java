package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class DemonExtraDmgPage extends AppCompatActivity {
    private RecyclerView rv;
    private TextView txtDED;
    private TextView txtCompleteDED;
    private CheckBox checkBoxInvisibilityDEDPage;

    private ImageView imgSearchDED;
    private EditText editSearchDED;

    private CharSequence check;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demon_extra_dmg_page);
        /* 악마 추가 피해 페이지.
         *  작업 목록
         *  1. 악추피 도감 목록 불러오기
         *  2. 악추피 완성 도감 숨기기 기능(풀각만 숨김)
         * */
        txtDED = (TextView) findViewById(R.id.txtDED);
        txtCompleteDED = (TextView) findViewById(R.id.txtCompleteDED);
        rv = findViewById(R.id.rvDemonExtraDmg);
        DemonExtraDmgAdapter adapter = new DemonExtraDmgAdapter(this, this);
        rv.setAdapter(adapter);

        checkBoxInvisibilityDEDPage = (CheckBox) findViewById(R.id.checkBoxInvisibilityDEDPage);
        checkBoxInvisibilityDEDPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxInvisibilityDEDPage.isChecked()){
                    check = "notNull";
                }else {
                    check = "";
                }
                adapter.getCompleteFilter().filter(check);
            }
        });

        editSearchDED = findViewById(R.id.editSearchDED);
        editSearchDED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getSearchFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imgSearchDED = (ImageView) findViewById(R.id.imgSearchDED);
        imgSearchDED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDED.getVisibility() == View.INVISIBLE) {
                    txtDED.setVisibility(View.VISIBLE);
                    txtCompleteDED.setVisibility(View.VISIBLE);
                    editSearchDED.setVisibility(View.INVISIBLE);
                } else {
                    txtDED.setVisibility(View.INVISIBLE);
                    txtCompleteDED.setVisibility(View.INVISIBLE);
                    editSearchDED.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void setDED(float value) {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDED.setText("악마 추가 피해 + " + df.format(value) + "%");
    }

    public void setDEDBook(int completeDED, int DEDBook) {
        txtCompleteDED.setText("완성 도감 개수 : " + completeDED + "/" + DEDBook + "개");
    }
}
