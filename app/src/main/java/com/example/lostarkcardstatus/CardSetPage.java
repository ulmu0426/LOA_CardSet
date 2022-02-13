package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardSetPage extends AppCompatActivity {

    private RecyclerView rv;
    private CardDBHelper dbHelper;
    private CheckBox checkBoxInvisibilityCardSetPage;
    private CardSetAdapter adapter;

    private ImageView imgBtnSortMenu;
    private ArrayList<CardSetInfo> cardSetInfo;

    private CharSequence check;

    private EditText editSearchCardSet;
    private ImageView imgSearchCardSet;

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

        cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;

        rv = findViewById(R.id.rvCardSet);
        dbHelper = new CardDBHelper(this);
        adapter = new CardSetAdapter(this, this);

        rv.setAdapter(adapter);

        checkBoxInvisibilityCardSetPage = findViewById(R.id.checkBoxInvisibilityCardSetPage);
        checkBoxInvisibilityCardSetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxInvisibilityCardSetPage.isChecked()) {
                    check = "notNull";
                } else {
                    check = "";
                }
                adapter.getCompleteFilter().filter(check);
            }
        });
        editSearchCardSet = findViewById(R.id.editSearchCardSet);
        editSearchCardSet.addTextChangedListener(new TextWatcher() {
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

        imgSearchCardSet = (ImageView) findViewById(R.id.imgSearchCardSet);
        imgSearchCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearchCardSet.getVisibility() == View.GONE) {
                    editSearchCardSet.setVisibility(View.VISIBLE);
                } else {
                    editSearchCardSet.setVisibility(View.GONE);
                }
            }
        });


        imgBtnSortMenu = findViewById(R.id.imgBtnCardSetSortMenu);
        imgBtnSortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardSetPage.this, imgBtnSortMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_sort_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (cardSetInfo.get(0).getId() == 0) {
                                    break;
                                }
                                cardSetInfo = adapter.getFilterCardSet();
                                Collections.sort(cardSetInfo, new Comparator<CardSetInfo>() {
                                    @Override
                                    public int compare(CardSetInfo o1, CardSetInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                adapter.sortCardSet(cardSetInfo);
                                return true;
                            case R.id.nameSort:
                                if (cardSetInfo.get(0).getName() == "가디언의 광기") {
                                    break;
                                }
                                cardSetInfo = adapter.getFilterCardSet();
                                Collections.sort(cardSetInfo);
                                Log.v("test", cardSetInfo.size() + "");
                                adapter.sortCardSet(cardSetInfo);

                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });

    }

    public boolean completeChecked(){
        return checkBoxInvisibilityCardSetPage.isChecked();
    }
}
