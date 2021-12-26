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
    private static final int DATABASE_VERSION =1;
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
    //카드테이블이 공유하는 column
    private static final String CARD_COLUMN_ID ="id";            //테이블별 카드 번호
    private static final String CARD_COLUMN_NAME ="name";        //카드 이름
    private static final String CARD_COLUMN_NUMBER ="number";    //카드 보유 장수
    private static final String CARD_COLUMN_AWAKE ="awake";      //카드 각성도


    //도감 테이블 3개
    private static final String TABLE_CARDBOOK_CRITICAL = "cardbook_critical";
    private static final String TABLE_CARDBOOK_SPECIALITY = "cardbook_speciality";
    private static final String TABLE_CARDBOOK_AGILITY = "cardbook_agility";
    //도감 테이블이 공유하는 column
    private static final String CARDBOOK_COLUMN_ID ="id";            //테이블별 카드 번호
    private static final String CARDBOOK_COLUMN_NAME ="name";        //카드도감 이름
    private static final String CARDBOOK_COLUMN_VALUE ="value";    //카드 특성 증가량
    private static final String CARDBOOK_COLUMN_CARD0 ="card0";      //카드1
    private static final String CARDBOOK_COLUMN_CARD1 ="card1";      //카드1
    private static final String CARDBOOK_COLUMN_CARD2 ="card2";      //카드1
    private static final String CARDBOOK_COLUMN_CARD3 ="card3";      //카드1
    private static final String CARDBOOK_COLUMN_CARD4 ="card4";      //카드1
    private static final String CARDBOOK_COLUMN_CARD5 ="card5";      //카드1
    private static final String CARDBOOK_COLUMN_CARD6 ="card6";      //카드1
    private static final String CARDBOOK_COLUMN_CARD7 ="card7";      //카드1
    private static final String CARDBOOK_COLUMN_CARD8 ="card8";      //카드1
    private static final String CARDBOOK_COLUMN_CARD9 ="card9";      //카드1

    public LOA_Card_DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else{
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.context = context;
    }

    public void createDataBase() throws IOException{
        //데이터베이스가 없으면 assets폴더에서 복사
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist){
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            }
            catch (IOException mIOExeption){
                throw new Error("ErrorCopyingDataBase");
            }
        }

    }
    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DATABASE_NAME);

        return dbFile.exists();
    }
    //assets 폴더에서 데이터베이스 복사
    private void copyDataBase() throws IOException{
        InputStream mInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0){
            mOutput.write(mBuffer,0,mLength);
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
    public void UpdateInfoCardNum(String tableName, String number, int input, int cardId){
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + number + " = " + input + " WHERE id = " + cardId);
    }
    //UPDATE 카드 각성도 수정
    public void UpdateInfoCardAwake(String tableName, String number, int input, int cardId){
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumInfo.execSQL("UPDATE " + tableName + " SET " + number + " = " + input + " WHERE id = " + cardId);
    }
    //SELECT : 카드 가져오기(카드 이름, 보유 카드 개수, 카드 각성 수치)
    //L,E,R,U,C
    @SuppressLint("Range")
    public  ArrayList<CardInfo> getCardInfoL(){     //전설카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_LEGEND + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public  ArrayList<CardInfo> getCardInfoE(){     //영웅카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_EPIC + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public  ArrayList<CardInfo> getCardInfoR(){     //희귀카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_RARE + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public  ArrayList<CardInfo> getCardInfoU(){     //고급카드 목록 넣기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_UNCOMMON + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public  ArrayList<CardInfo> getCardInfoC(){     //일반카드 목록 넣기기
       ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_COMMON + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public  ArrayList<CardInfo> getCardInfoS(){     //스페셜카드 목록 넣기기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_SPECIAL + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public ArrayList<Cardbook> getCardBookInfo_Critical(){       //카드도감 치명 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_CRITICAL + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public ArrayList<Cardbook> getCardBookInfo_Specility(){       //카드도감 특화 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_SPECIALITY + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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
    public ArrayList<Cardbook> getCardBookInfo_Agility(){       //카드도감 크리티컬 항목 가져오기
        ArrayList<Cardbook> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_AGILITY + " ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            //데이터가 조회된 경우 수행
            while(cursor.moveToNext()){
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

}
