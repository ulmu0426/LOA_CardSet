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

import java.util.ArrayList;

public class CardBookPage extends AppCompatActivity {
    private final String[] STAT = {"치명", "특화", "신속"};
    private static int[] haveStat;                  //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯 상승치. 치명, 특화, 신속 순서
    private static int[] haveStatCardBook;          //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯별 최대 도감 달성 개수. 치명, 특화, 신속 순서
    private static int[] haveStatCardBookCount;     //카드도감 페이지 상단에 현재 도감 완성도에 따른 스탯별 현재 도감 달성 개수. 치명, 특화, 신속 순서
    private ArrayList<CardBookInfo> cardBookInfo;

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
         *  1. 카드 도감 목록 불러오기
         *  2. 완성 도감 온 오프 기능
         *  3. 정렬 기능(기본, 이름, 완성도 순)
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
        adapter.getDefaultSort();
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
                popupMenu.getMenu().removeItem(R.id.fastCompletenessSort);
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

        cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;

    }

    @Override
    public void onBackPressed() {
        if (editSearchCardBook.getVisibility() == View.VISIBLE) {
            editSearchCardBook.setVisibility(View.INVISIBLE);
            tableStats.setVisibility(View.VISIBLE);

            return;
        }
        haveStatUpdate(cardBookInfo);
        setStatAndStatBook(haveStat, haveStatCardBookCount, haveStatCardBook);
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
        finish();
    }

    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(CardBookInfo cardbook_all) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate(ArrayList<CardBookInfo> cardBookInfo) {
        haveStat = new int[]{0, 0, 0};
        haveStatCardBook = new int[]{0, 0, 0};
        haveStatCardBookCount = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardBookInfo.size(); j++) {
                if (cardBookInfo.get(j).getOption().equals(STAT[i]))
                    haveStatCardBook[i]++;
                if (cardBookInfo.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardBookInfo.get(j))) {
                    haveStatCardBookCount[i]++;
                    haveStat[i] += cardBookInfo.get(j).getValue();
                }
            }
        }
    }

    // 현재 도감 완성 현황 및 스탯 증가치 현황
    public void setStatAndStatBook(int[] stat, int[] statBookComplete, int[] statBookAll) {
        txtBtnCritical.setText(CRITICAL + stat[0]);
        txtBtnSpeciality.setText(SPECIALITY + stat[1]);
        txtBtnAgility.setText(AGILITY + stat[2]);
        txtBtnNotAchievedSpecificityCritical.setText(CRITICAL_BOOK_COMPLETE + statBookComplete[0] + "/" + statBookAll[0] + "개");
        txtBtnNotAchievedSpecificitySpeciality.setText(SPECIALITY_BOOK_COMPLETE + statBookComplete[1] + "/" + statBookAll[1] + "개");
        txtBtnNotAchievedSpecificityAgility.setText(AGILITY_BOOK_COMPLETE + statBookComplete[2] + "/" + statBookAll[2] + "개");
    }


    // 현재 어떤 정렬인지 알 수 있도록 adapter에 정보를 줄 함수
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
