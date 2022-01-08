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

    //카드 테이블 6개
    private static final String TABLE_CARD_LEGEND = "card_legend";
    private static final String TABLE_CARD_EPIC = "card_epic";
    private static final String TABLE_CARD_RARE = "card_rare";
    private static final String TABLE_CARD_UNCOMMON = "card_uncommon";
    private static final String TABLE_CARD_COMMON = "card_common";
    private static final String TABLE_CARD_SPECIAL = "card_special";

    private static final String TABLE_CARD_LIST = "cardList";
    private static final String CARD_COLUMN_GRADE = "grade";
    private static final String CARD_COLUMN_ACQUISITION_INFO = "acquisition_info";
    //카드테이블이 공유하는 column
    private static final String CARD_COLUMN_ID = "id";            //테이블별 카드 번호
    private static final String CARD_COLUMN_NAME = "name";        //카드 이름
    private static final String CARD_COLUMN_NUMBER = "number";    //카드 보유 장수
    private static final String CARD_COLUMN_AWAKE = "awake";      //카드 각성도



    //도감 테이블 3개
    private static final String TABLE_CARDBOOK_CRITICAL = "cardbook_critical";
    private static final String TABLE_CARDBOOK_SPECIALITY = "cardbook_speciality";
    private static final String TABLE_CARDBOOK_AGILITY = "cardbook_agility";
    //all 도감 테이블1개
    private static final String TABLE_CARDBOOK_ALL = "cardbook_all";
    //도감 테이블이 공유하는 column
    private static final String CARDBOOK_COLUMN_ID = "id";            //테이블별 카드 번호
    private static final String CARDBOOK_COLUMN_NAME = "name";        //카드도감 이름
    private static final String CARDBOOK_COLUMN_VALUE = "value";      //카드 특성 증가량
    private static final String CARDBOOK_COLUMN_CARD0 = "card0";      //카드1
    private static final String CARDBOOK_COLUMN_CARD1 = "card1";      //카드2
    private static final String CARDBOOK_COLUMN_CARD2 = "card2";      //카드3
    private static final String CARDBOOK_COLUMN_CARD3 = "card3";      //카드4
    private static final String CARDBOOK_COLUMN_CARD4 = "card4";      //카드5
    private static final String CARDBOOK_COLUMN_CARD5 = "card5";      //카드6
    private static final String CARDBOOK_COLUMN_CARD6 = "card6";      //카드7
    private static final String CARDBOOK_COLUMN_CARD7 = "card7";      //카드8
    private static final String CARDBOOK_COLUMN_CARD8 = "card8";      //카드9
    private static final String CARDBOOK_COLUMN_CARD9 = "card9";      //카드10
    //all 도감 테이블 column
    private static final String CARDBOOK_COLUMN_OPTION = "option";   //옵션
    private static final String CARDBOOK_COLUMN_CARD0_CHECK = "card0_check";
    private static final String CARDBOOK_COLUMN_CARD1_CHECK = "card1_check";
    private static final String CARDBOOK_COLUMN_CARD2_CHECK = "card2_check";
    private static final String CARDBOOK_COLUMN_CARD3_CHECK = "card3_check";
    private static final String CARDBOOK_COLUMN_CARD4_CHECK = "card4_check";
    private static final String CARDBOOK_COLUMN_CARD5_CHECK = "card5_check";
    private static final String CARDBOOK_COLUMN_CARD6_CHECK = "card6_check";
    private static final String CARDBOOK_COLUMN_CARD7_CHECK = "card7_check";
    private static final String CARDBOOK_COLUMN_CARD8_CHECK = "card8_check";
    private static final String CARDBOOK_COLUMN_CARD9_CHECK = "card9_check";
    private static final String CARDBOOK_COLUMN_HAVECARD = "haveCard";


    //악추피 테이블
    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";
    //악추피 column
    //도감테이블 column id~card9까지 동일
    private static final String DED_COLUMN_AWAKE_SUM0 = "awake_sum0";   //int
    private static final String DED_COLUMN_AWAKE_SUM1 = "awake_sum1";
    private static final String DED_COLUMN_AWAKE_SUM2 = "awake_sum2";
    private static final String DED_COLUMN_DMG_P0 = "dmg_p0";           //float
    private static final String DED_COLUMN_DMG_P1 = "dmg_p1";
    private static final String DED_COLUMN_DMG_P2 = "dmg_p2";

    private static final String DED_COLUMN_HAVE_AWAKE = "haveAwake";
    private static final String DED_COLUMN_HAVE_CARD =  "haveCard";

    //카드 세트 column
    //도감테이블 column id~card6까지 동일
    private static final String TABLE_CARDSET = "cardSet";
    private static final String CARDSET_SETBONUS0 = "set_bonus0";
    private static final String CARDSET_SETBONUS1 = "set_bonus1";
    private static final String CARDSET_SETBONUS2 = "set_bonus2";
    private static final String CARDSET_SETBONUS3 = "set_bonus3";
    private static final String CARDSET_SETBONUS4 = "set_bonus4";
    private static final String CARDSET_SETBONUS5 = "set_bonus5";
    private static final String CARDSET_HAVECARD = "haveCard";


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
    public void UpdateInfoCardNum(String tableName, String number, int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + number + " = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드 도감 획득 유무 수정
    public void UpdateInfoCardBookCard(String tableName, String columnName, int cardCheck, int cardBookId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + columnName + " = " + cardCheck + " WHERE id = " + cardBookId);
    }
    //UPDATE 카드 도감 획득 유무 수정
    public void UpdateInfoDEDCard(String tableName, String columnName, int cardCheck, int cardBookId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + columnName + " = " + cardCheck + " WHERE id = " + cardBookId);
    }


    //UPDATE 카드 각성도 수정
    public void UpdateInfoCardAwake(String tableName, String number, int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + number + " = " + input + " WHERE id = " + cardId);
    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfo_All() {     //모든카드 목록 넣기
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

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                cardinfo.setAcquisition_info(acquisition_info);
                cardinfo.setGrade(grade);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;
    }

    //SELECT : 카드 가져오기(카드 이름, 보유 카드 개수, 카드 각성 수치)
    //L,E,R,U,C
    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoL() {     //전설카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_LEGEND + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;
    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoE() {     //영웅카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_EPIC + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;
    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoR() {     //희귀카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_RARE + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoU() {     //고급카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_UNCOMMON + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoC() {     //일반카드 목록 넣기기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_COMMON + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfoS() {     //스페셜카드 목록 넣기기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_SPECIAL + " ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setCount(count);
                cardinfo.setAwake(awake);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;

    }

    //SELECT : 카드 도감 가져오기(도감 명, 도감 완성 특성 추가 값, 도감에 속한 카드 명(최대10)
    @SuppressLint("Range")
    public ArrayList<Cardbook> getCardBookInfo_Critical() {       //카드도감 치명 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_CRITICAL + " ORDER BY id DESC", null);
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

                Cardbook cardbook_info = new Cardbook();
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
                getInfo.add((cardbook_info));
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<Cardbook> getCardBookInfo_Specility() {       //카드도감 특화 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_SPECIALITY + " ORDER BY id DESC", null);
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

                Cardbook cardbook_info = new Cardbook();
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
                getInfo.add((cardbook_info));
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<Cardbook> getCardBookInfo_Agility() {       //카드도감 크리티컬 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_AGILITY + " ORDER BY id DESC", null);
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

                Cardbook cardbook_info = new Cardbook();
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
                getInfo.add((cardbook_info));
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
                int card0_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD0_CHECK));
                int card1_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD1_CHECK));
                int card2_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD2_CHECK));
                int card3_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD3_CHECK));
                int card4_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD4_CHECK));
                int card5_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD5_CHECK));
                int card6_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD6_CHECK));
                int card7_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD7_CHECK));
                int card8_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD8_CHECK));
                int card9_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD9_CHECK));
                int haveCard = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_HAVECARD));

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
                cardbook_info.setCard0_check(card0_check);
                cardbook_info.setCard1_check(card1_check);
                cardbook_info.setCard2_check(card2_check);
                cardbook_info.setCard3_check(card3_check);
                cardbook_info.setCard4_check(card4_check);
                cardbook_info.setCard5_check(card5_check);
                cardbook_info.setCard6_check(card6_check);
                cardbook_info.setCard7_check(card7_check);
                cardbook_info.setCard8_check(card8_check);
                cardbook_info.setCard9_check(card9_check);
                cardbook_info.setCompleteCardBook(haveCard);
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
                int id = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_NAME));
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
                int awake_sum0 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM0));
                int awake_sum1 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM1));
                int awake_sum2 = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_AWAKE_SUM2));
                float dmg_p0 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P0));
                float dmg_p1 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P1));
                float dmg_p2 = cursor.getFloat(cursor.getColumnIndex(DED_COLUMN_DMG_P2));
                int haveAwake = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_HAVE_AWAKE));
                int haveCard = cursor.getInt(cursor.getColumnIndex(DED_COLUMN_HAVE_CARD));
                int card0_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD0_CHECK));
                int card1_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD1_CHECK));
                int card2_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD2_CHECK));
                int card3_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD3_CHECK));
                int card4_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD4_CHECK));
                int card5_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD5_CHECK));
                int card6_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD6_CHECK));
                int card7_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD7_CHECK));
                int card8_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD8_CHECK));
                int card9_check = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD9_CHECK));


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
                demonExtraDmgInfo.setCompleteDEDBook(haveCard);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
                demonExtraDmgInfo.setCard0_check(card0_check);
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
                int id = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_CARD6));
                String set_bonus0 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS0));
                String set_bonus1 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS1));
                String set_bonus2 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS2));
                String set_bonus3 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS3));
                String set_bonus4 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS4));
                String set_bonus5 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS5));
                int haveCard = cursor.getInt(cursor.getColumnIndex(CARDSET_HAVECARD));
                int haveAwake = cursor.getInt(cursor.getColumnIndex(CARDSET_HAVECARD));


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
