package com.example.lostarkcardstatus;

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
import java.util.Collections;
import java.util.Comparator;

public class DemonExtraDmgPage extends AppCompatActivity {
    private RecyclerView rv;
    private TextView txtDED;
    private TextView txtCompleteDED;
    private CheckBox checkBoxInvisibilityDEDPage;
    private DemonExtraDmgAdapter adapter;

    private ImageView imgSearchDED;
    private EditText editSearchDED;

    private CharSequence check;

    private ImageView imgBtnDEDsortMenu;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demon_extra_dmg_page);
        /* 악마 추가 피해 페이지.
         *  작업 목록
         *  1. 악추피 도감 목록 불러오기
         *  2. 악추피 완성 도감 숨기기 기능(풀각만 숨김)
         * */
        DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;

        txtDED = (TextView) findViewById(R.id.txtDED);
        txtCompleteDED = (TextView) findViewById(R.id.txtCompleteDED);
        rv = findViewById(R.id.rvDemonExtraDmg);
        adapter = new DemonExtraDmgAdapter(this, this);
        rv.setAdapter(adapter);

        checkBoxInvisibilityDEDPage = (CheckBox) findViewById(R.id.checkBoxInvisibilityDEDPage);
        checkBoxInvisibilityDEDPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxInvisibilityDEDPage.isChecked()) {
                    check = "notNull";
                } else {
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


        imgBtnDEDsortMenu = findViewById(R.id.imgBtnDEDsortMenu);
        imgBtnDEDsortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DemonExtraDmgPage.this, imgBtnDEDsortMenu);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.item_sort_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.defaultSort:
                                if (DEDInfo.get(0).getId() == 0) {
                                    break;
                                }
                                DEDInfo = adapter.getFilterDED();

                                Collections.sort(DEDInfo, new Comparator<DemonExtraDmgInfo>() {
                                    @Override
                                    public int compare(DemonExtraDmgInfo o1, DemonExtraDmgInfo o2) {
                                        if (o1.getId() < o2.getId()) {
                                            return -1;
                                        } else
                                            return 1;
                                    }
                                });
                                adapter.sortDED(DEDInfo);
                                return true;
                            case R.id.nameSort:
                                if (DEDInfo.get(0).getName() == "거인 토토이크") {
                                    break;
                                }
                                DEDInfo = adapter.getFilterDED();

                                Collections.sort(DEDInfo);
                                adapter.sortDED(DEDInfo);

                                return true;
                            case R.id.completenessSort:
                                if (DEDInfo.get(0).getName() == "") {
                                    break;
                                }
                                DEDInfo = adapter.getFilterDED();
                                DEDInfo = sortByCompletenessDED();
                                adapter.sortDED(DEDInfo);

                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
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

    public boolean completeChecked() {
        return checkBoxInvisibilityDEDPage.isChecked();
    }



    /*
    1. 수집, 각성도 풀
    2. 수집, 각성도 낮음
    3. 수집.
    4. 수집 낮음
    5. 수집 안됨.
     */

    private ArrayList<DemonExtraDmgInfo> sortByCompletenessDED() {
        ArrayList<DemonExtraDmgInfo> tempListDED = new ArrayList<DemonExtraDmgInfo>();
        DemonExtraDmgInfo tempDED;

        for (int i = 0; i < DEDInfo.size(); i++) {    //각성도 풀, 수집 완료
            if ((DEDInfo.get(i).getHaveAwake()) == (DEDInfo.get(i).getCompleteDEDBook() * 5) && DEDInfo.get(i).getCompleteDEDBook() == DEDInfo.get(i).getHaveCard()) {
                tempDED = DEDInfo.get(i);
                tempListDED.add(tempDED);
            }
        }

        Collections.sort(DEDInfo, new Comparator<DemonExtraDmgInfo>() {
            @Override
            public int compare(DemonExtraDmgInfo o1, DemonExtraDmgInfo o2) {
                if ((o1.getCompleteDEDBook() * 5) - o1.getHaveAwake() < (o2.getCompleteDEDBook() * 5) - o2.getHaveAwake()) {
                    return -1;
                } else
                    return 1;
            }
        });

        for (int i = 0; i < DEDInfo.size(); i++) {    //각성도 순 정렬된 DEDInfo 에서 수집완료, 풀각대비 각성도 높은 순서로
            if (DEDInfo.get(i).getCompleteDEDBook() == DEDInfo.get(i).getHaveCard() && !((DEDInfo.get(i).getHaveAwake()) == (DEDInfo.get(i).getCompleteDEDBook() * 5))) {
                tempDED = DEDInfo.get(i);
                tempListDED.add(tempDED);
            }
        }

        Collections.sort(DEDInfo, new Comparator<DemonExtraDmgInfo>() {
            @Override
            public int compare(DemonExtraDmgInfo o1, DemonExtraDmgInfo o2) {
                if ((o1.getCompleteDEDBook() - o1.getHaveCard()) < (o2.getCompleteDEDBook() - o2.getHaveCard())) {
                    return -1;
                } else
                    return 1;
            }
        });

        for (int i = 0; i < DEDInfo.size(); i++) {    //수집완료 순 정렬된 DEDInfo 에서 수집완료순
            if (!(DEDInfo.get(i).getCompleteDEDBook() == DEDInfo.get(i).getHaveCard()) && (DEDInfo.get(i).getDmgSum() == 0)) {
                tempDED = DEDInfo.get(i);
                tempListDED.add(tempDED);
            }
        }

        return tempListDED;
    }

}
