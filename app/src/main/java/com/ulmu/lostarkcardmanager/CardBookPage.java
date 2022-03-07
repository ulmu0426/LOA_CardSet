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
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostarkcardmanager.R;

public class CardBookPage extends AppCompatActivity {
    private static final String CRITICAL = "치명 + ";
    private static final String SPECIALITY = "특화 + ";
    private static final String AGILITY = "신속 + ";
    private final String CRITICAL_BOOK_COMPLETE = "치명 도감 달성 개수 : ";
    private final String AGILITY_BOOK_COMPLETE = "신속 도감 달성 개수 : ";
    private final String SPECIALITY_BOOK_COMPLETE = "특화 도감 달성 개수 : ";
    private RecyclerView rv;
    private CardBookAdapter adapter;

    public CheckBox checkBoxInvisibilityCardBookPage;
    private TextView txtBtnCritical;
    private TextView txtBtnAgility;
    private TextView txtBtnSpeciality;
    private TextView txtBtnNotAchievedSpecificityCritical;
    private TextView txtBtnNotAchievedSpecificitySpeciality;
    private TextView txtBtnNotAchievedSpecificityAgility;

    private ImageView imgSearchCardBook;
    private EditText editSearchCardBook;
    private TableLayout tableStats;

    private ImageView imgBtnCardBookSortMenu;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkCompleteness = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardbook_page);
        /* 카드 도감 페이지.
         *  이루어질 작업 목록
         *  1. 카드 도감 목록 불러오기
         *  2. 완성 도감 온 오프 기능
         *  3. 이름 순 정렬 기능
         *  4. 도감 검색 기능
         * */

        //1. 카드 도감 목록 불러오기
        rv = findViewById(R.id.rvCardbookList);
        adapter = new CardBookAdapter(this, this);

        rv.setAdapter(adapter);

        txtBtnCritical = (TextView) findViewById(R.id.txtBtnCritical);
        txtBtnAgility = (TextView) findViewById(R.id.txtBtnAgility);
        txtBtnSpeciality = (TextView) findViewById(R.id.txtBtnSpeciality);
        txtBtnNotAchievedSpecificityCritical = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificityCritical);
        txtBtnNotAchievedSpecificitySpeciality = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificitySpeciality);
        txtBtnNotAchievedSpecificityAgility = (TextView) findViewById(R.id.txtBtnNotAchievedSpecificityAgility);

        //2. 완성 도감 온 오프 기능
        checkBoxInvisibilityCardBookPage = (CheckBox) findViewById(R.id.checkboxCompleteCardbookInvisibility);
        checkBoxInvisibilityCardBookPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getCompleteFilter();
            }
        });

        //3. 도감 검색 기능
        tableStats = findViewById(R.id.tableStats);
        editSearchCardBook = findViewById(R.id.editSearchCardBook);
        editSearchCardBook.addTextChangedListener(new TextWatcher() {
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

        imgSearchCardBook = (ImageView) findViewById(R.id.imgSearchCardBook);
        imgSearchCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableStats.getVisibility() == View.INVISIBLE) {
                    tableStats.setVisibility(View.VISIBLE);
                    editSearchCardBook.setVisibility(View.INVISIBLE);
                } else {
                    tableStats.setVisibility(View.INVISIBLE);
                    editSearchCardBook.setVisibility(View.VISIBLE);
                }
            }
        });

        //4. 카드 정렬 기능. (기본, 이름, 완성도 순)
        imgBtnCardBookSortMenu = findViewById(R.id.imgBtnCardBookSortMenu);
        imgBtnCardBookSortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardBookPage.this, imgBtnCardBookSortMenu);
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

                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = true;
                                adapter.getCompletenessSort();

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
        if (editSearchCardBook.getVisibility() == View.VISIBLE) {
            editSearchCardBook.setVisibility(View.INVISIBLE);
            tableStats.setVisibility(View.VISIBLE);

            return;
        }

        finish();
    }

    public void setStatAndStatBook(int[] stat, int[] statBookComplete, int[] statBookAll) {
        txtBtnCritical.setText(CRITICAL + stat[0]);
        txtBtnSpeciality.setText(SPECIALITY + stat[1]);
        txtBtnAgility.setText(AGILITY + stat[2]);
        txtBtnNotAchievedSpecificityCritical.setText(CRITICAL_BOOK_COMPLETE + statBookComplete[0] + "/" + statBookAll[0] + "개");
        txtBtnNotAchievedSpecificitySpeciality.setText(SPECIALITY_BOOK_COMPLETE + statBookComplete[1] + "/" + statBookAll[1] + "개");
        txtBtnNotAchievedSpecificityAgility.setText(AGILITY_BOOK_COMPLETE + statBookComplete[2] + "/" + statBookAll[2] + "개");
    }

    public boolean completeChecked() {
        return checkBoxInvisibilityCardBookPage.isChecked();
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
