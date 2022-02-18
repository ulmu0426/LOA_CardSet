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
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardBookPage extends AppCompatActivity {
    private static final String CRITICAL = "치명 + ";
    private static final String SPECIALITY = "특화 + ";
    private static final String AGILITY = "신속 + ";
    private final String CRITICAL_BOOK_COMPLETE = "치명 도감 달성 개수 : ";
    private final String AGILITY_BOOK_COMPLETE = " 도감 달성 개수 : ";
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

    private CharSequence check;

    private ImageView imgBtnCardBookSortMenu;
    private ArrayList<CardBookInfo> cardBookInfo;

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

        cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;

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
                if (checkBoxInvisibilityCardBookPage.isChecked()) {
                    check = "notNull";
                } else {
                    check = "";
                }
                adapter.getCompleteFilter().filter(check);
            }
        });

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
                                if (cardBookInfo.get(0).getId() == 0) {
                                    break;
                                }
                                cardBookInfo = adapter.getFilterCardBook();
                                Collections.sort(cardBookInfo, new Comparator<CardBookInfo>() {
                                    @Override
                                    public int compare(CardBookInfo o1, CardBookInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                adapter.sortCardBook(cardBookInfo);
                                return true;
                            case R.id.nameSort:
                                if (cardBookInfo.get(0).getName() == "1절만 해") {
                                    break;
                                }
                                cardBookInfo = adapter.getFilterCardBook();
                                Collections.sort(cardBookInfo);
                                adapter.sortCardBook(cardBookInfo);

                                return true;
                            case R.id.completenessSort:
                                if (cardBookInfo.get(0).getName() == "") {
                                    break;
                                }
                                cardBookInfo = adapter.getFilterCardBook();
                                cardBookInfo = sortByCompletenessCardBook();
                                adapter.sortCardBook(cardBookInfo);

                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
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

    public boolean completeChecked() {
        return checkBoxInvisibilityCardBookPage.isChecked();
    }


    /*
    완성1.
    카드 1장 모자란거 2.
    ~카드 n장 모자란거 3.
    카드 하나도 없는거 4.
     */
    private ArrayList<CardBookInfo> sortByCompletenessCardBook() {
        ArrayList<CardBookInfo> tempList = new ArrayList<CardBookInfo>();
        CardBookInfo tempCardBookInfo;
        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getCompleteCardBook() == cardBookInfo.get(i).getHaveCard()) { //다 모은 경우
                tempCardBookInfo = cardBookInfo.get(i);
                tempList.add(tempCardBookInfo);
            }
        }

        ArrayList<CardBookInfo> tempList1 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList2 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList3 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList4 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList5 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList6 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList7 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList8 = new ArrayList<CardBookInfo>();
        ArrayList<CardBookInfo> tempList9 = new ArrayList<CardBookInfo>();
        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (!(cardBookInfo.get(i).getSubComplete() == 0)) { //1장~최대9장모자란 경우
                if (cardBookInfo.get(i).getSubComplete() == 1) {
                    tempList1.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 2) {
                    tempList2.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 3) {
                    tempList3.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 4) {
                    tempList4.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 5) {
                    tempList5.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 6) {
                    tempList6.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 7) {
                    tempList7.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 8) {
                    tempList8.add(cardBookInfo.get(i));
                } else if (cardBookInfo.get(i).getSubComplete() == 9) {
                    tempList9.add(cardBookInfo.get(i));
                } else {
                    continue;
                }
            }
        }
        tempList.addAll(tempList1);
        tempList.addAll(tempList2);
        tempList.addAll(tempList3);
        tempList.addAll(tempList4);
        tempList.addAll(tempList5);
        tempList.addAll(tempList6);
        tempList.addAll(tempList7);
        tempList.addAll(tempList8);
        tempList.addAll(tempList9);

        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getHaveCard() == 0 && (cardBookInfo.get(i).getCompleteCardBook() == 10)) {                 //한 장도 못 모은 경우
                tempCardBookInfo = cardBookInfo.get(i);
                tempList.add(tempCardBookInfo);
            }
        }

        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getName().equals("하늘을 비추는 사막")) {
                Log.v("test", "haveCard" + tempList.get(i).getHaveCard() + ", completeCard : " + tempList.get(i).getCompleteCardBook());
            }
        }

        return tempList;
    }
}
