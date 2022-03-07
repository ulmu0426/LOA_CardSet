package com.ulmu.lostarkcardmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostarkcardmanager.R;


public class CardSetPage extends AppCompatActivity {

    private RecyclerView rv;
    private CardDBHelper dbHelper;
    private CheckBox checkBoxInvisibilityCardSetPage;
    private CardSetAdapter adapter;

    private ImageView imgBtnSortMenu;


    private EditText editSearchCardSet;
    private ImageView imgSearchCardSet;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkCompleteness = false;

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
        adapter = new CardSetAdapter(this, this);

        rv.setAdapter(adapter);


        checkBoxInvisibilityCardSetPage = findViewById(R.id.checkBoxInvisibilityCardSetPage);
        checkBoxInvisibilityCardSetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getCompleteFilter();
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
                                adapter.getDefaultSort();
                                checkDefault = true;
                                checkName = false;
                                checkCompleteness = false;

                                return true;
                            case R.id.nameSort:
                                adapter.getNameSort();

                                checkDefault = false;
                                checkName = true;
                                checkCompleteness = false;
                                return true;
                            case R.id.completenessSort:
                                adapter.getCompletenessSort();
                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = true;

                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });

    }

    @Override
    public void onBackPressed() {
        if (editSearchCardSet.getVisibility() == View.VISIBLE) {
            editSearchCardSet.setVisibility(View.GONE);
            return;
        }

        finish();
    }

    public boolean completeChecked() {
        return checkBoxInvisibilityCardSetPage.isChecked();
    }

    public boolean checkDefault() {
        return checkDefault;
    }

    public boolean checkName() {
        return checkName;
    }

    public boolean checkCompleteness() {
        return checkCompleteness;
    }
}
