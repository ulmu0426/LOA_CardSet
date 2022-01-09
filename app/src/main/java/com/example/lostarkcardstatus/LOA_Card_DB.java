package com.example.lostarkcardstatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LOA_Card_DB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "loaCardDb.db";
    private static final int DATABASE_VERSION = 1;
    //assets 폴더
    private static String DB_PATH = "";

    private SQLiteDatabase mDataBase;

    //카드 테이블 column
    private static final String TABLE_CARD_LIST = "cardList";                       //카드정보 테이블 명
    private static final String CARD_COLUMN_ID = "id";                              //테이블별 카드 번호
    private static final String CARD_COLUMN_NAME = "name";                          //카드 이름
    private static final String CARD_COLUMN_NUMBER = "number";                      //카드 보유 장수
    private static final String CARD_COLUMN_AWAKE = "awake";                        //카드 각성도
    private static final String CARD_COLUMN_ACQUISITION_INFO = "acquisition_info";  //카드 획득처 정보
    private static final String CARD_COLUMN_GRADE = "grade";                        //카드 등급
    private static final String CARD_COLUMN_CHECK = "getCard";                        //카드 획득 유무

    //카드 도감 테이블 column
    private static final String TABLE_CARDBOOK_ALL = "cardbook_all";            //카드 도감테이블 명
    private static final String CARDBOOK_COLUMN_ID = "id";                      //테이블별 카드 번호
    private static final String CARDBOOK_COLUMN_NAME = "name";                  //카드도감 이름
    private static final String CARDBOOK_COLUMN_VALUE = "value";                //카드 특성 증가량
    private static final String CARDBOOK_COLUMN_CARD0 = "card0";                //카드1
    private static final String CARDBOOK_COLUMN_CARD1 = "card1";                //카드2
    private static final String CARDBOOK_COLUMN_CARD2 = "card2";                //카드3
    private static final String CARDBOOK_COLUMN_CARD3 = "card3";                //카드4
    private static final String CARDBOOK_COLUMN_CARD4 = "card4";                //카드5
    private static final String CARDBOOK_COLUMN_CARD5 = "card5";                //카드6
    private static final String CARDBOOK_COLUMN_CARD6 = "card6";                //카드7
    private static final String CARDBOOK_COLUMN_CARD7 = "card7";                //카드8
    private static final String CARDBOOK_COLUMN_CARD8 = "card8";                //카드9
    private static final String CARDBOOK_COLUMN_CARD9 = "card9";                //카드10
    private static final String CARDBOOK_COLUMN_OPTION = "option";              //치명,특화,신속
    private static final String CARDBOOK_COLUMN_CARD_LIST_SUM = "cardListSum";  //도감 활성화를 위해 필요한 카드 수
    private static final String CARDBOOK_COLUMN_HAVECARD = "haveCard";          //도감 활성화를 위해 수집한 카드 수
    private static final String CARDBOOK_COLUMN_CHECKCARD0 = "checkCard0";           //카드1 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD1 = "checkCard1";           //카드2 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD2 = "checkCard2";           //카드3 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD3 = "checkCard3";           //카드4 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD4 = "checkCard4";           //카드5 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD5 = "checkCard5";           //카드6 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD6 = "checkCard6";           //카드7 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD7 = "checkCard7";           //카드8 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD8 = "checkCard8";           //카드9 획득 여부
    private static final String CARDBOOK_COLUMN_CHECKCARD9 = "checkCard9";           //카드10 획득 여부


    //악추피 테이블 column
    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String DED_COLUMN_ID = "id";                       //테이블별 카드 번호
    private static final String DED_COLUMN_NAME = "name";                   //카드도감 이름
    private static final String DED_COLUMN_CARD0 = "card0";                 //카드1
    private static final String DED_COLUMN_CARD1 = "card1";                 //카드2
    private static final String DED_COLUMN_CARD2 = "card2";                 //카드3
    private static final String DED_COLUMN_CARD3 = "card3";                 //카드4
    private static final String DED_COLUMN_CARD4 = "card4";                 //카드5
    private static final String DED_COLUMN_CARD5 = "card5";                 //카드6
    private static final String DED_COLUMN_CARD6 = "card6";                 //카드7
    private static final String DED_COLUMN_CARD7 = "card7";                 //카드8
    private static final String DED_COLUMN_CARD8 = "card8";                 //카드9
    private static final String DED_COLUMN_CARD9 = "card9";                 //카드10
    private static final String DED_COLUMN_AWAKE_SUM0 = "awake_sum0";       //각성합계 1단계
    private static final String DED_COLUMN_AWAKE_SUM1 = "awake_sum1";       //각성합계 2단계
    private static final String DED_COLUMN_AWAKE_SUM2 = "awake_sum2";       //각성합계 3단계
    private static final String DED_COLUMN_DMG_P0 = "dmg_p0";               //각성합계 1단계 데미지 보너스
    private static final String DED_COLUMN_DMG_P1 = "dmg_p1";               //각성합계 2단계 데미지 보너스
    private static final String DED_COLUMN_DMG_P2 = "dmg_p2";               //각성합계 3단계 데미지 보너스
    private static final String DED_COLUMN_HAVE_AWAKE = "haveAwake";        //악추피 도감의 '현재' 각성 합계
    private static final String DED_COLUMN_CARD_LIST_SUM = "cardListSum";   //악추피 도감 '활성화'를 위해 필요한 카드 수
    private static final String DED_COLUMN_HAVECARD = "haveCard";           //악추피 도감 '활성화'를 위해 수집한 카드 수
    private static final String DED_COLUMN_CHECKCARD0 = "checkCard0";            //카드1 획득 여부
    private static final String DED_COLUMN_CHECKCARD1 = "checkCard1";            //카드2 획득 여부
    private static final String DED_COLUMN_CHECKCARD2 = "checkCard2";            //카드3 획득 여부
    private static final String DED_COLUMN_CHECKCARD3 = "checkCard3";            //카드4 획득 여부
    private static final String DED_COLUMN_CHECKCARD4 = "checkCard4";            //카드5 획득 여부
    private static final String DED_COLUMN_CHECKCARD5 = "checkCard5";            //카드6 획득 여부
    private static final String DED_COLUMN_CHECKCARD6 = "checkCard6";            //카드7 획득 여부
    private static final String DED_COLUMN_CHECKCARD7 = "checkCard7";            //카드8 획득 여부
    private static final String DED_COLUMN_CHECKCARD8 = "checkCard8";            //카드9 획득 여부
    private static final String DED_COLUMN_CHECKCARD9 = "checkCard9";            //카드10 획득 여부

    //카드 세트 column
    private static final String TABLE_CARDSET = "cardSet";                      //카드세트 테이블 명
    private static final String CARDSET_COLUMN_ID = "id";                       //테이블별 카드 번호
    private static final String CARDSET_COLUMN_NAME = "name";                   //카드도감 이름
    private static final String CARDSET_COLUMN_CARD0 = "card0";                 //카드1
    private static final String CARDSET_COLUMN_CARD1 = "card1";                 //카드2
    private static final String CARDSET_COLUMN_CARD2 = "card2";                 //카드3
    private static final String CARDSET_COLUMN_CARD3 = "card3";                 //카드4
    private static final String CARDSET_COLUMN_CARD4 = "card4";                 //카드5
    private static final String CARDSET_COLUMN_CARD5 = "card5";                 //카드6
    private static final String CARDSET_COLUMN_CARD6 = "card6";                 //카드7
    private static final String CARDSET_SETBONUS0 = "set_bonus0";               //카드세트 효과 1번째
    private static final String CARDSET_SETBONUS1 = "set_bonus1";               //카드세트 효과 2번째
    private static final String CARDSET_SETBONUS2 = "set_bonus2";               //카드세트 효과 3번째
    private static final String CARDSET_SETBONUS3 = "set_bonus3";               //카드세트 효과 4번째
    private static final String CARDSET_SETBONUS4 = "set_bonus4";               //카드세트 효과 5번째
    private static final String CARDSET_SETBONUS5 = "set_bonus5";               //카드세트 효과 6번째
    private static final String CARDSET_HAVECARD = "haveCard";                  //카드세트 현재 활성화된 카드 수
    private static final String CARDSET_HAVEAWAKE = "haveAwake";                //카드세트의 현재 각성 합계


    public LOA_Card_DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.context = context;
    }

    public void createDataBase() throws IOException {
        //데이터베이스가 없으면 assets폴더에서 복사
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            } catch (IOException mIOExeption) {
                throw new Error("ErrorCopyingDataBase");
            }
        }

    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);

        return dbFile.exists();
    }

    //assets 폴더에서 데이터베이스 복사
    private void copyDataBase() throws IOException {
        InputStream mInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    //UPDATE 카드 수량 수정
    public void UpdateInfoCardNum(String columnName, int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET " + columnName + " = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드 각성도 수정
    public void UpdateInfoCardAwake(String tableName, String columnName, int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET " + columnName + " = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드 획득 유무 수정(카드이름이 같은 경우)
    public void UpdateInfoCardCheck(String columnName, int input, String cardName) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 name 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET " + columnName + " = " + input + " WHERE name = '" + cardName + "';");
    }

    //UPDATE 카드 도감 획득 유무 수정
    public void UpdateInfoCardBookCard(String columnName, int cardCheck, int cardBookId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET " + columnName + " = " + cardCheck + " WHERE id = " + cardBookId);
    }

    //UPDATE 악추피 획득 유무 수정
    public void UpdateInfoDEDCard(String columnName, int cardCheck, int cardBookId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + TABLE_DEMON_EXTRA_DMG + " SET " + columnName + " = " + cardCheck + " WHERE id = " + cardBookId);
    }


    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfo_All() {     //모든카드 리스트 가져오기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_LIST + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));
                String acquisition_info = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_ACQUISITION_INFO));
                String grade = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_GRADE));
                int getCard = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_CHECK)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                cardinfo.setAcquisition_info(acquisition_info);
                cardinfo.setGrade(grade);
                cardinfo.setGetCard(getCard);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;
    }

    @SuppressLint("Range")
    public ArrayList<Cardbook_All> getCardBookInfo_All() {       //카드도감 전체 항목 가져오기
        ArrayList<Cardbook_All> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_ALL + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_NAME));
                int value = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_VALUE));
                String card0 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD6));
                String card7 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD7));
                String card8 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD8));
                String card9 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD9));
                String option = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_OPTION));
                int cardListSum = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD_LIST_SUM));
                int haveCard = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_HAVECARD));
                int checkCard0 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD0));
                int checkCard1 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD1));
                int checkCard2 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD2));
                int checkCard3 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD3));
                int checkCard4 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD4));
                int checkCard5 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD5));
                int checkCard6 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD6));
                int checkCard7 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD7));
                int checkCard8 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD8));
                int checkCard9 = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CHECKCARD9));

                Cardbook_All cardbook_info = new Cardbook_All();
                cardbook_info.setId(id);
                cardbook_info.setName(name);
                cardbook_info.setValue(value);
                cardbook_info.setCard0(card0);
                cardbook_info.setCard1(card1);
                cardbook_info.setCard2(card2);
                cardbook_info.setCard3(card3);
                cardbook_info.setCard4(card4);
                cardbook_info.setCard5(card5);
                cardbook_info.setCard6(card6);
                cardbook_info.setCard7(card7);
                cardbook_info.setCard8(card8);
                cardbook_info.setCard9(card9);
                cardbook_info.setOption(option);
                cardbook_info.setCompleteCardBook(cardListSum);
                cardbook_info.setHaveCard(haveCard);
                cardbook_info.setCheckCard0(checkCard0);
                cardbook_info.setCheckCard1(checkCard1);
                cardbook_info.setCheckCard2(checkCard2);
                cardbook_info.setCheckCard3(checkCard3);
                cardbook_info.setCheckCard4(checkCard4);
                cardbook_info.setCheckCard5(checkCard5);
                cardbook_info.setCheckCard6(checkCard6);
                cardbook_info.setCheckCard7(checkCard7);
                cardbook_info.setCheckCard8(checkCard8);
                cardbook_info.setCheckCard9(checkCard9);
                getInfo.add((cardbook_info));
            }
        }
        cursor.close();

        return getInfo;

    }


    @SuppressLint("Range")
    public ArrayList<DemonExtraDmgInfo> getDemonExtraDmgInfo() {       //악추피 항목 가져오기
        ArrayList<DemonExtraDmgInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEMON_EXTRA_DMG + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DED_COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD6));
                String card7 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD7));
                String card8 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD8));
                String card9 = cursor.getString(cursor.getColumnIndex(DED_COLUMN_CARD9));
                int awake_sum0 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM0));
                int awake_sum1 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM1));
                int awake_sum2 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM2));
                float dmg_p0 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P0));
                float dmg_p1 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P1));
                float dmg_p2 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P2));
                int haveAwake = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_HAVE_AWAKE));
                int cardListSum = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CARD_LIST_SUM));
                int haveCard = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_HAVECARD));
                int checkCard0 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD0));
                int checkCard1 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD1));
                int checkCard2 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD2));
                int checkCard3 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD3));
                int checkCard4 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD4));
                int checkCard5 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD5));
                int checkCard6 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD6));
                int checkCard7 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD7));
                int checkCard8 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD8));
                int checkCard9 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_CHECKCARD9));


                DemonExtraDmgInfo demonExtraDmgInfo = new DemonExtraDmgInfo();
                demonExtraDmgInfo.setId(id);
                demonExtraDmgInfo.setName(name);
                demonExtraDmgInfo.setCard0(card0);
                demonExtraDmgInfo.setCard1(card1);
                demonExtraDmgInfo.setCard2(card2);
                demonExtraDmgInfo.setCard3(card3);
                demonExtraDmgInfo.setCard4(card4);
                demonExtraDmgInfo.setCard5(card5);
                demonExtraDmgInfo.setCard6(card6);
                demonExtraDmgInfo.setCard7(card7);
                demonExtraDmgInfo.setCard8(card8);
                demonExtraDmgInfo.setCard9(card9);
                demonExtraDmgInfo.setAwake_sum0(awake_sum0);
                demonExtraDmgInfo.setAwake_sum1(awake_sum1);
                demonExtraDmgInfo.setAwake_sum2(awake_sum2);
                demonExtraDmgInfo.setDmg_p0(dmg_p0);
                demonExtraDmgInfo.setDmg_p1(dmg_p1);
                demonExtraDmgInfo.setDmg_p2(dmg_p2);
                demonExtraDmgInfo.setHaveAwake(haveAwake);
                demonExtraDmgInfo.setCompleteDEDBook(cardListSum);
                demonExtraDmgInfo.setHaveCard(haveCard);
                demonExtraDmgInfo.setCheckCard0(checkCard0);
                demonExtraDmgInfo.setCheckCard1(checkCard1);
                demonExtraDmgInfo.setCheckCard2(checkCard2);
                demonExtraDmgInfo.setCheckCard3(checkCard3);
                demonExtraDmgInfo.setCheckCard4(checkCard4);
                demonExtraDmgInfo.setCheckCard5(checkCard5);
                demonExtraDmgInfo.setCheckCard6(checkCard6);
                demonExtraDmgInfo.setCheckCard7(checkCard7);
                demonExtraDmgInfo.setCheckCard8(checkCard8);
                demonExtraDmgInfo.setCheckCard9(checkCard9);
                getInfo.add((demonExtraDmgInfo));
            }
        }
        cursor.close();

        return getInfo;

    }


    @SuppressLint("Range")
    public ArrayList<CardSetInfo> getCardSetInfo() {       //카드 세트 항목 가져오기
        ArrayList<CardSetInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDSET + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARDSET_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_CARD6));
                String set_bonus0 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS0));
                String set_bonus1 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS1));
                String set_bonus2 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS2));
                String set_bonus3 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS3));
                String set_bonus4 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS4));
                String set_bonus5 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS5));
                int haveCard = cursor.getInt(cursor.getColumnIndex(CARDSET_HAVECARD));
                int haveAwake = cursor.getInt(cursor.getColumnIndex(CARDSET_HAVEAWAKE));


                CardSetInfo cardSetInfo = new CardSetInfo();
                cardSetInfo.setId(id);
                cardSetInfo.setName(name);
                cardSetInfo.setCard0(card0);
                cardSetInfo.setCard1(card1);
                cardSetInfo.setCard2(card2);
                cardSetInfo.setCard3(card3);
                cardSetInfo.setCard4(card4);
                cardSetInfo.setCard5(card5);
                cardSetInfo.setCard6(card6);
                cardSetInfo.setSet_bonus0(set_bonus0);
                cardSetInfo.setSet_bonus1(set_bonus1);
                cardSetInfo.setSet_bonus2(set_bonus2);
                cardSetInfo.setSet_bonus3(set_bonus3);
                cardSetInfo.setSet_bonus4(set_bonus4);
                cardSetInfo.setSet_bonus5(set_bonus5);
                cardSetInfo.setHaveCard(haveCard);
                cardSetInfo.setHaveAwake(haveAwake);
                getInfo.add((cardSetInfo));
            }
        }
        cursor.close();

        return getInfo;

    }


}
