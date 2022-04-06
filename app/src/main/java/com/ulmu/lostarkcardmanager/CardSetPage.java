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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CardSetPage extends AppCompatActivity {
    private final String[] STAT = {"치명", "특화", "신속"};
    float haveDED = 0;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;   //기본 악추피 도감 목록 ArrayList -> 카드세트에서 수정한 값이 메인페이지의 악추피 추가 피해 현황을 변동시킬 수 있도록.

    private RecyclerView rv;
    private CheckBox checkBoxInvisibilityCardSetPage;   //완성된 카드세트 숨기기를 위한 CheckBox
    private CardSetAdapter adapter;

    private ImageView imgBtnSortMenu;

    private EditText editSearchCardSet;
    private ImageView imgSearchCardSet;

    private boolean checkDefault = true;
    private boolean checkName = false;
    private boolean checkCompleteness = false;
    private boolean checkFavorite = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_set_page);

        /* 카드 세트 페이지.
         *  작업 목록
         *  1. 카드 세트 목록 불러오기
         *  2. 카드 세트 정렬 기능
         *  3. 카드 세트 즐겨찾기 기능(메인페이지에 띄울 카드 세트 지정)
         *  4. 카드 세트 검색 기능(기본, 이름, 완성도, 즐겨찾기)
         *  5. 완성된 카드 세트 숨기기 on/off 기능
         * */

        this.DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;

        rv = findViewById(R.id.rvCardSet);
        adapter = new CardSetAdapter(this, this);

        rv.setAdapter(adapter);

        //완성된 카드 세트 숨기기 on/off
        checkBoxInvisibilityCardSetPage = findViewById(R.id.checkBoxInvisibilityCardSetPage);
        adapter.getDefaultSort();
        checkBoxInvisibilityCardSetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getCompleteFilter();
            }
        });

        //검색
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

        //정렬
        imgBtnSortMenu = findViewById(R.id.imgBtnCardSetSortMenu);
        imgBtnSortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardSetPage.this, imgBtnSortMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_sort_menu, popupMenu.getMenu());
                popupMenu.getMenu().removeItem(R.id.fastCompletenessSort);
                popupMenu.getMenu().getItem(2).setTitle("세트 완성도 순 정렬");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                adapter.getDefaultSort();
                                checkDefault = true;
                                checkName = false;
                                checkCompleteness = false;
                                checkFavorite = false;
                                return true;

                            case R.id.nameSort:
                                adapter.getNameSort();

                                checkDefault = false;
                                checkName = true;
                                checkCompleteness = false;
                                checkFavorite = false;

                                return true;

                            case R.id.completenessSort:
                                adapter.getCompletenessSort();
                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = true;
                                checkFavorite = false;

                                return true;

                            case R.id.favoriteSort:
                                adapter.getFavoriteSort();
                                checkDefault = false;
                                checkName = false;
                                checkCompleteness = false;
                                checkFavorite = true;

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
        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        haveStatUpdate(((MainPage) MainPage.mainContext).cardBookInfo);
        haveDEDDmgUpdate();

        finish();
    }

    private boolean isCompleteCardBook(CardBookInfo cardBook_all) {
        if (cardBook_all.getHaveCard() == cardBook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

    //스텟, 도감 달성 개수 업데이트
    private void haveStatUpdate(ArrayList<CardBookInfo> cardBookInfo) {
        int[] haveStat = new int[]{0, 0, 0};

        for (int i = 0; i < cardBookInfo.size(); i++) {
            if (cardBookInfo.get(i).getOption().equals(STAT[0]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[0] += cardBookInfo.get(i).getValue();
            }
            if (cardBookInfo.get(i).getOption().equals(STAT[1]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[1] += cardBookInfo.get(i).getValue();
            }
            if (cardBookInfo.get(i).getOption().equals(STAT[2]) && isCompleteCardBook(cardBookInfo.get(i))) {
                haveStat[2] += cardBookInfo.get(i).getValue();
            }
        }

        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
    }

    //악추피 달성 현황 업데이트
    private void haveDEDDmgUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        haveDED = 0;
        for (int i = 0; i < DEDInfo.size(); i++) {
            haveDED += DEDInfo.get(i).getDmgSum();
        }
        haveDED = Float.parseFloat(df.format(haveDED));

        ((MainPage) MainPage.mainContext).setDemonExtraDmgInfo(haveDED);
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

    public boolean checkFavorite() {
        return checkFavorite;
    }
}
