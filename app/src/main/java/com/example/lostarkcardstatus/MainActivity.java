package com.example.lostarkcardstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private LOA_Card_DB cardDBHelper;
    protected ArrayList<CardInfo> cardInfo;
    protected ArrayList<Cardbook_All> cardbook_all;
    protected ArrayList<CardSetInfo> cardSetInfo;
    protected ArrayList<DemonExtraDmgInfo> DEDInfo;
    private TextView txtCardBookStat_Critical;
    private TextView txtCardBookStat_Speciality;
    private TextView txtCardBookStat_Agility;

    private TextView txtDemonExtraDmg;
    public static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_main);
        mainContext = this;

        //치,특,신 값
        txtCardBookStat_Critical = (TextView) findViewById(R.id.txtCardBookStat_Critical);
        txtCardBookStat_Speciality = (TextView) findViewById(R.id.txtCardBookStat_Speciality);
        txtCardBookStat_Agility = (TextView) findViewById(R.id.txtCardBookStat_Agility);
        //악추피 값
        txtDemonExtraDmg = (TextView) findViewById(R.id.txtDemonExtraDmg);

        try {
            setInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //카드 DB 정보 ArrayList 전달
        cardInfo = cardDBHelper.getCardInfo_All();

        //카드 도감 DB 정보 ArrayList 전달
        cardbook_all = cardDBHelper.getCardBookInfo_All();
        cardSetInfo = cardDBHelper.getCardSetInfo();

        //악추피 DB 정보 ArrayList 전달
        DEDInfo = cardDBHelper.getDemonExtraDmgInfo();

        //cardList Table 정보를 cardbook_all,DEDInfo,cardSetInfo ArrayList와 DB에 연동
        cardBookUpdate();           //카드 도감 DB
        haveDEDCardCheckUpdate();   //악추피 DB
        haveCardSetCheckUpdate();   //카드세트 DB

        DEDDBErrorFix();   //악추피 에러픽스 : 비정상적 종료시 악추피 도감에서 악추피도감에 존재하지 않는 카드의 checkCardX의 check가 0에서 1로 바뀌는 오류 수정

        //치,특,신 값 출력
        int[] stat = {getStatInfo("치명"), getStatInfo("특화"), getStatInfo("신속")};
        setCardBookStatInfo(stat);
        //악추피 값 출력
        float DEDValue = getDEDValueInfo();
        setDemonExtraDmgInfo(DEDValue);

        test();


        //카드 세트로 이동.
        TextView txtBtnCardSet = (TextView) findViewById(R.id.txtBtnCardSet);
        txtBtnCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSet_page.class);
                startActivity(intent);
            }
        });

        //카드 세트 디테일 페이지로 바로 이동.(imgCardSet1-3 클릭시)
        ImageView imgCardSet1 = (ImageView) findViewById(R.id.imgCardSet1);
        imgCardSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardSet_Detail.class);
                startActivity(intent);
            }
        });

        //카드 도감으로 이동.
        TextView txtBtnCardBook = (TextView) findViewById(R.id.txtBtnCardBookStats);
        txtBtnCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardBook_page.class);
                startActivity(intent);
            }
        });
        //악마 추가 피해로 이동
        TextView txtBtnDemonExtraDmg = (TextView) findViewById(R.id.txtBtnDemonExtraDmg);
        txtBtnDemonExtraDmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemonExtraDmg_page.class);
                startActivity(intent);
            }
        });

    }

    //DB정보 App에 입력
    private void setInit() throws IOException {
        cardDBHelper = new LOA_Card_DB(this);
        cardDBHelper.createDataBase();

    }

    //뒤로가기 2회 터치시 종료(2.5초 안에 두번 눌러야 함)
    private long backKeyPressedTime = 0;
    private Toast finish;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            finish = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG);
            finish.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            finish.cancel();
        }
    }

    //카드 도감 (치명, 특화, 신속) 값 입력
    private int getStatInfo(String STAT) {
        int a = 0;
        CardBook_Adapter cardBookAdapter = new CardBook_Adapter(cardbook_all);
        for (int i = 0; i < cardbook_all.size(); i++) {
            if (cardbook_all.get(i).getOption().equals(STAT) && cardBookAdapter.isCompleteCardBook(cardbook_all.get(i))) {
                a += cardbook_all.get(i).getValue();
            }
        }
        return a;
    }

    public void setCardBookStatInfo(int[] stat) {
        txtCardBookStat_Critical.setText(stat[0] + "");
        txtCardBookStat_Speciality.setText(stat[1] + "");
        txtCardBookStat_Agility.setText(stat[2] + "");
    }

    //악추피 도감 악추피 입력
    private float getDEDValueInfo() {
        float a = 0;
        DemonExtraDmgAdapter DEDAdapter = new DemonExtraDmgAdapter(DEDInfo);
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && DEDInfo.get(i).getHaveAwake() == DEDInfo.get(i).getAwake_sum2()) {
                a += DEDInfo.get(i).getDmg_p2() + DEDInfo.get(i).getDmg_p1() + DEDInfo.get(i).getDmg_p0();
            } else if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && (DEDInfo.get(i).getHaveAwake() >= DEDInfo.get(i).getAwake_sum1() && DEDInfo.get(i).getHaveAwake() < DEDInfo.get(i).getAwake_sum2())) {
                a += DEDInfo.get(i).getDmg_p1() + DEDInfo.get(i).getDmg_p0();
            } else if (DEDAdapter.isCompleteDED(DEDInfo.get(i)) && (DEDInfo.get(i).getHaveAwake() >= DEDInfo.get(i).getAwake_sum0() && DEDInfo.get(i).getHaveAwake() < DEDInfo.get(i).getAwake_sum1())) {
                a += DEDInfo.get(i).getDmg_p0();
            }
        }
        return a;
    }

    public void setDemonExtraDmgInfo(float value) {

        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        txtDemonExtraDmg.setText(df.format(value) + "%");
    }

    private final String CARD_BOOK_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String CARD_BOOK_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String CARD_BOOK_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String CARD_BOOK_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String CARD_BOOK_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String CARD_BOOK_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String CARD_BOOK_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String CARD_BOOK_COLUMN_NAME_CARD7_CHECK = "checkCard7";
    private final String CARD_BOOK_COLUMN_NAME_CARD8_CHECK = "checkCard8";
    private final String CARD_BOOK_COLUMN_NAME_CARD9_CHECK = "checkCard9";

    //최초 실행되는 메소드 : cardList 정보를 cardbook_all과 연동
    private void cardBookUpdate() {
        for (int i = 0; i < cardbook_all.size(); i++) {
            for (int j = 0; j < cardInfo.size(); j++) {
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard0())) {  //카드 이름이 같으면 실행됨.(실행후 이번 반복 해제)
                    cardbook_all.get(i).setCheckCard0(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD0_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());  //카드 획득유무 업데이트
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard1())) {
                    cardbook_all.get(i).setCheckCard1(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD1_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard2())) {
                    cardbook_all.get(i).setCheckCard2(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD2_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard3())) {
                    cardbook_all.get(i).setCheckCard3(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD3_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard4())) {
                    cardbook_all.get(i).setCheckCard4(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD4_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard5())) {
                    cardbook_all.get(i).setCheckCard5(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD5_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard6())) {
                    cardbook_all.get(i).setCheckCard6(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD6_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard7())) {
                    cardbook_all.get(i).setCheckCard7(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD7_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard8())) {
                    cardbook_all.get(i).setCheckCard8(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD8_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
                if (cardInfo.get(j).getName().equals(cardbook_all.get(i).getCard9())) {
                    cardbook_all.get(i).setCheckCard9(cardInfo.get(j).getGetCard());
                    cardDBHelper.UpdateInfoCardBookCard(CARD_BOOK_COLUMN_NAME_CARD9_CHECK, cardInfo.get(j).getGetCard(), cardbook_all.get(i).getId());
                }
            }
        }
    }

    private final String DED_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String DED_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String DED_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String DED_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String DED_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String DED_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String DED_COLUMN_NAME_CARD6_CHECK = "checkCard6";
    private final String DED_COLUMN_NAME_CARD7_CHECK = "checkCard7";
    private final String DED_COLUMN_NAME_CARD8_CHECK = "checkCard8";
    private final String DED_COLUMN_NAME_CARD9_CHECK = "checkCard9";

    private final String DED_COLUMN_NAME_CARD0_AWAKE = "awakeCard0";
    private final String DED_COLUMN_NAME_CARD1_AWAKE = "awakeCard1";
    private final String DED_COLUMN_NAME_CARD2_AWAKE = "awakeCard2";
    private final String DED_COLUMN_NAME_CARD3_AWAKE = "awakeCard3";
    private final String DED_COLUMN_NAME_CARD4_AWAKE = "awakeCard4";
    private final String DED_COLUMN_NAME_CARD5_AWAKE = "awakeCard5";
    private final String DED_COLUMN_NAME_CARD6_AWAKE = "awakeCard6";
    private final String DED_COLUMN_NAME_CARD7_AWAKE = "awakeCard7";
    private final String DED_COLUMN_NAME_CARD8_AWAKE = "awakeCard8";
    private final String DED_COLUMN_NAME_CARD9_AWAKE = "awakeCard9";

    //최초 실행되는 메소드 : cardList 정보를 DEDInfo에 연동
    private void haveDEDCardCheckUpdate() {
        for (int i = 0; i < DEDInfo.size(); i++) {
            for (int j = 0; j < cardInfo.size(); j++) {
                if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard0())) {  //카드 이름이 같으면 실행됨.(실행후 이번 반복 해제)
                    //카드 이름이 같으면
                    DEDInfo.get(i).setCheckCard0(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard0(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD0_CHECK, DEDInfo.get(i).getCheckCard0(), DEDInfo.get(i).getId());  //카드 획득유무 업데이트
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD0_AWAKE, DEDInfo.get(i).getAwakeCard0(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard1())) {
                    DEDInfo.get(i).setCheckCard1(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard1(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD1_CHECK, DEDInfo.get(i).getCheckCard1(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD1_AWAKE, DEDInfo.get(i).getAwakeCard1(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard2())) {
                    DEDInfo.get(i).setCheckCard2(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard2(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD2_CHECK, DEDInfo.get(i).getCheckCard2(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD2_AWAKE, DEDInfo.get(i).getAwakeCard2(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard3())) {
                    DEDInfo.get(i).setCheckCard3(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard3(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD3_CHECK, DEDInfo.get(i).getCheckCard3(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD3_AWAKE, DEDInfo.get(i).getAwakeCard3(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard4())) {
                    DEDInfo.get(i).setCheckCard4(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard4(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD4_CHECK, DEDInfo.get(i).getCheckCard4(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD4_AWAKE, DEDInfo.get(i).getAwakeCard4(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard5())) {
                    DEDInfo.get(i).setCheckCard5(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard5(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD5_CHECK, DEDInfo.get(i).getCheckCard5(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD5_AWAKE, DEDInfo.get(i).getAwakeCard5(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard6())) {
                    DEDInfo.get(i).setCheckCard6(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard6(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD6_CHECK, DEDInfo.get(i).getCheckCard6(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD6_AWAKE, DEDInfo.get(i).getAwakeCard6(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard7())) {
                    DEDInfo.get(i).setCheckCard7(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard7(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD7_CHECK, DEDInfo.get(i).getCheckCard7(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD7_AWAKE, DEDInfo.get(i).getAwakeCard7(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard8())) {
                    DEDInfo.get(i).setCheckCard8(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard8(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD8_CHECK, DEDInfo.get(i).getCheckCard8(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD8_AWAKE, DEDInfo.get(i).getAwakeCard8(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(DEDInfo.get(i).getCard9())) {
                    DEDInfo.get(i).setCheckCard9(cardInfo.get(j).getGetCard());
                    DEDInfo.get(i).setAwakeCard9(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD9_CHECK, DEDInfo.get(i).getCheckCard9(), DEDInfo.get(i).getId());
                    cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD9_AWAKE, DEDInfo.get(i).getAwakeCard9(), DEDInfo.get(i).getId());   //카드 각성도 업데이트
                }
            }
            cardDBHelper.UpdateInfoDEDCard("haveCard", DEDInfo.get(i).getHaveCard(), DEDInfo.get(i).getId());
            cardDBHelper.UpdateInfoDEDCard("haveAwake", DEDInfo.get(i).getHaveAwake(), DEDInfo.get(i).getId());
        }
    }

    private final String CARDSET_COLUMN_NAME_CARD0_CHECK = "checkCard0";
    private final String CARDSET_COLUMN_NAME_CARD1_CHECK = "checkCard1";
    private final String CARDSET_COLUMN_NAME_CARD2_CHECK = "checkCard2";
    private final String CARDSET_COLUMN_NAME_CARD3_CHECK = "checkCard3";
    private final String CARDSET_COLUMN_NAME_CARD4_CHECK = "checkCard4";
    private final String CARDSET_COLUMN_NAME_CARD5_CHECK = "checkCard5";
    private final String CARDSET_COLUMN_NAME_CARD6_CHECK = "checkCard6";

    private final String CARDSET_COLUMN_NAME_CARD0_AWAKE = "awakeCard0";
    private final String CARDSET_COLUMN_NAME_CARD1_AWAKE = "awakeCard1";
    private final String CARDSET_COLUMN_NAME_CARD2_AWAKE = "awakeCard2";
    private final String CARDSET_COLUMN_NAME_CARD3_AWAKE = "awakeCard3";
    private final String CARDSET_COLUMN_NAME_CARD4_AWAKE = "awakeCard4";
    private final String CARDSET_COLUMN_NAME_CARD5_AWAKE = "awakeCard5";
    private final String CARDSET_COLUMN_NAME_CARD6_AWAKE = "awakeCard6";

    //최초 실행되는 메소드 : cardList 정보를 CardSet에 연동
    private void haveCardSetCheckUpdate() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            for (int j = 0; j < cardInfo.size(); j++) {
                if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard0())) {  //카드 이름이 같으면 실행됨.(실행후 이번 반복 해제)
                    //카드 이름이 같으면
                    cardSetInfo.get(i).setCheckCard0(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard0(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD0_CHECK, cardSetInfo.get(i).getCheckCard0(), cardSetInfo.get(i).getId());  //카드 획득유무 업데이트
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD0_AWAKE, cardSetInfo.get(i).getAwakeCard0(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard1())) {
                    cardSetInfo.get(i).setCheckCard1(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard1(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD1_CHECK, cardSetInfo.get(i).getCheckCard1(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD1_AWAKE, cardSetInfo.get(i).getAwakeCard1(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard2())) {
                    cardSetInfo.get(i).setCheckCard2(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard2(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD2_CHECK, cardSetInfo.get(i).getCheckCard2(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD2_AWAKE, cardSetInfo.get(i).getAwakeCard2(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard3())) {
                    cardSetInfo.get(i).setCheckCard3(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard3(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD3_CHECK, cardSetInfo.get(i).getCheckCard3(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD3_AWAKE, cardSetInfo.get(i).getAwakeCard3(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard4())) {
                    cardSetInfo.get(i).setCheckCard4(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard4(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD4_CHECK, cardSetInfo.get(i).getCheckCard4(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD4_AWAKE, cardSetInfo.get(i).getAwakeCard4(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard5())) {
                    cardSetInfo.get(i).setCheckCard5(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard5(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD5_CHECK, cardSetInfo.get(i).getCheckCard5(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD5_AWAKE, cardSetInfo.get(i).getAwakeCard5(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                } else if (cardInfo.get(j).getName().equals(cardSetInfo.get(i).getCard6())) {
                    cardSetInfo.get(i).setCheckCard6(cardInfo.get(j).getGetCard());
                    cardSetInfo.get(i).setAwakeCard6(cardInfo.get(j).getAwake());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD6_CHECK, cardSetInfo.get(i).getCheckCard6(), cardSetInfo.get(i).getId());
                    cardDBHelper.UpdateInfoCardSetCard(CARDSET_COLUMN_NAME_CARD6_AWAKE, cardSetInfo.get(i).getAwakeCard6(), cardSetInfo.get(i).getId());   //카드 각성도 업데이트
                }
            }
            cardDBHelper.UpdateInfoCardSetCard("haveCard", cardSetInfo.get(i).getHaveCard(), cardSetInfo.get(i).getId());
            cardDBHelper.UpdateInfoCardSetCard("haveAwake", cardSetInfo.get(i).getHaveAwake(), cardSetInfo.get(i).getId());

        }

    }

    private void DEDDBErrorFix() {
        for (int i = 0; i < DEDInfo.size(); i++) {
            if (DEDInfo.get(i).getCard2().equals("")) {
                DEDInfo.get(i).setCheckCard2(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD2_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard3().equals("")) {
                DEDInfo.get(i).setCheckCard3(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD3_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard4().equals("")) {
                DEDInfo.get(i).setCheckCard4(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD4_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard5().equals("")) {
                DEDInfo.get(i).setCheckCard5(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD5_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard6().equals("")) {
                DEDInfo.get(i).setCheckCard6(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD6_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard7().equals("")) {
                DEDInfo.get(i).setCheckCard7(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD7_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard8().equals("")) {
                DEDInfo.get(i).setCheckCard8(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD8_CHECK, 0, DEDInfo.get(i).getId());
            }
            if (DEDInfo.get(i).getCard9().equals("")) {
                DEDInfo.get(i).setCheckCard9(0);
                cardDBHelper.UpdateInfoDEDCard(DED_COLUMN_NAME_CARD9_CHECK, 0, DEDInfo.get(i).getId());
            }
        }
    }


    private void test() {
        for (int i = 0; i < cardSetInfo.size(); i++) {
            if (cardSetInfo.get(i).getName().equals("세상을 구하는 빛")) {
                Toast.makeText(this, "셋옵 5번 : " + cardSetInfo.get(i).getSet_bonus5(), Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

}
