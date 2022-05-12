package com.ulmu.lostarkcardmanager;

import android.content.Intent;
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
import java.util.ArrayList;

public class ExtraDmgPage extends AppCompatActivity {
    //XX 추가 피해 TextView
    private TextView txtXExtraDmg;
    private TextView txtXED;
    private TextView txtCompleteXED;

    //RecyclerView and Adapter
    private RecyclerView rv;
    private ExtraDmgAdapter adapter;

    private CheckBox checkBoxInvisibilityXEDPage;

    private ImageView imgBtnXEDSortMenu;
    private ImageView imgSearchExtraDmg;
    private EditText editSearchXED;

    private String EDName;
    private ArrayList<ExtraDmgInfo> extraDmgInfo;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkCompleteness = false;
    private boolean checkFastCompleteness = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_dmg_page);
        Intent intent = getIntent();
        init(intent.getStringExtra("EDName"));
        txtXED.setText(intent.getStringExtra("EDName") + " 추가 피해 + " + 0 + "%");

        EDName = getIntent().getStringExtra("EDName");
        extraDmgInfo = getIntent().getParcelableArrayListExtra("EDList");
        adapter = new ExtraDmgAdapter(this, EDName, extraDmgInfo, this);

        rv.setAdapter(adapter);

        checkBoxInvisibilityXEDPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getCompleteFilter();
            }
        });

        editSearchXED.addTextChangedListener(new TextWatcher() {
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

        imgSearchExtraDmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtXED.getVisibility() == View.INVISIBLE) {
                    txtXED.setVisibility(View.VISIBLE);
                    txtCompleteXED.setVisibility(View.VISIBLE);
                    editSearchXED.setVisibility(View.INVISIBLE);
                } else {
                    txtXED.setVisibility(View.INVISIBLE);
                    txtCompleteXED.setVisibility(View.INVISIBLE);
                    editSearchXED.setVisibility(View.VISIBLE);
                }
            }
        });

        imgBtnXEDSortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ExtraDmgPage.this, imgBtnXEDSortMenu);
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
        if (editSearchXED.getVisibility() == View.VISIBLE) {
            editSearchXED.setVisibility(View.INVISIBLE);
            txtXED.setVisibility(View.VISIBLE);
            txtCompleteXED.setVisibility(View.VISIBLE);

            return;
        }
        ((MainPage) MainPage.mainContext).setExtraDmgList();
        ((MainPage) MainPage.mainContext).favoriteAdapter.notifyDataSetChanged();
        finish();
    }

    private void init(String EDName) {
        txtXExtraDmg = findViewById(R.id.txtXExtraDmg);
        txtXED = findViewById(R.id.txtXED);
        rv = findViewById(R.id.rvExtraDmg);
        checkBoxInvisibilityXEDPage = findViewById(R.id.checkBoxInvisibilityXEDPage);
        txtCompleteXED = findViewById(R.id.txtCompleteXED);
        imgBtnXEDSortMenu = findViewById(R.id.imgBtnXEDSortMenu);
        imgSearchExtraDmg = findViewById(R.id.imgSearchExtraDmg);
        editSearchXED = findViewById(R.id.editSearchXED);
        txtXExtraDmg.setText(EDName + " 추가 피해");
    }

    public boolean completeChecked() {
        return checkBoxInvisibilityXEDPage.isChecked();
    }

    public boolean isCheckDefault() {
        return checkDefault;
    }

    public boolean isCheckName() {
        return checkName;
    }

    public boolean isCheckCompleteness() {
        return checkCompleteness;
    }

    public boolean isCheckFastCompleteness() {
        return checkFastCompleteness;
    }

    public void setXED(float value, String EDName) {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtXED.setText(EDName + " 추가 피해 + " + df.format(value) + "%");
    }

    public void setXEDBook(int completeXED, int XEDBook) {
        txtCompleteXED.setText("완성 도감 개수 : " + completeXED + "/" + XEDBook + "개");
    }

}