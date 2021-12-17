package com.example.lostarkcardstatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LOA_Card_DB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "loaCardDb.db";
    private static final int DATABASE_VERSION =1;
    //카드 테이블 6개
    private static final String TABLE_CARD_LEGEND = "card_legend";
    private static final String TABLE_CARD_EPIC = "card_epic";
    private static final String TABLE_CARD_RARE = "card_rare";
    private static final String TABLE_CARD_UNCOMMON = "card_uncommon";
    private static final String TABLE_CARD_COMMON = "card_common";
    private static final String TABLE_CARD_SPECIAL = "card_common";
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
        this.context =context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
