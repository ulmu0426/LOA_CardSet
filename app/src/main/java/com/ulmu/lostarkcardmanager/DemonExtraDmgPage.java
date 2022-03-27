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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class DemonExtraDmgPage extends AppCompatActivity {
    private RecyclerView rv;
    private TextView txtDED;
    private TextView txtCompleteDED;
    private CheckBox checkBoxInvisibilityDEDPage;
    private DemonExtraDmgAdapter adapter;

    private ImageView imgSearchDED;
    private EditText editSearchDED;

    private ImageView imgBtnDEDSortMenu;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkCompleteness = false;
    private boolean checkFastCompleteness = false;

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
        adapter = new DemonExtraDmgAdapter(this, this);
        rv.setAdapter(adapter);

        checkBoxInvisibilityDEDPage = (CheckBox) findViewById(R.id.checkBoxInvisibilityDEDPage);
        adapter.getDefaultSort();
        checkBoxInvisibilityDEDPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getCompleteFilter();
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


        imgBtnDEDSortMenu = findViewById(R.id.imgBtnDEDsortMenu);
        imgBtnDEDSortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DemonExtraDmgPage.this, imgBtnDEDSortMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_sort_menu, popupMenu.getMenu());
                popupMenu.getMenu().removeItem(R.id.favoriteSort);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                adapter.getDefaultSort();
                                checkDefault = true;
                                checkName = false;
                                checkCompleteness = false;
                                checkFastCompleteness = false;

                                return true;

                            case R.id.nameSort:
                                adapter.getNameSort();

                                checkDefault = false;
                                checkName = true;
                                checkCompleteness = false;
                                checkFastCompleteness = false;
                                return true;

                            case R.id.completenessSort:
                                adapter.getCompletenessSort();
                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = true;
                                checkFastCompleteness = false;

                                return true;

                            case R.id.fastCompletenessSort:
                                adapter.getFastCompletenessSort();
                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = false;
                                checkFastCompleteness = true;

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
        if (editSearchDED.getVisibility() == View.VISIBLE) {
            editSearchDED.setVisibility(View.INVISIBLE);
            txtDED.setVisibility(View.VISIBLE);
            txtCompleteDED.setVisibility(View.VISIBLE);

            return;
        }

        finish();
    }

    public void setDED(float value) {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDED.setText("악마 추가 피해 + " + df.format(value) + "%");
    }

    public void setDEDBook(int completeDED, int DEDBook) {
        txtCompleteDED.setText("완성 도감 개수 : " + completeDED + "/" + DEDBook + "개");
    }

    public boolean completeChecked() {
        return checkBoxInvisibilityDEDPage.isChecked();
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

    public boolean checkFastCompleteness() {return checkFastCompleteness;
    }
}
