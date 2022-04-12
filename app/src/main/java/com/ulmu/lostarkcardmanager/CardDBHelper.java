package com.ulmu.lostarkcardmanager;

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

public class CardDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "loaCardDb.db";
    private static final int DATABASE_VERSION = 5;

    //assets 폴더
    private static String DB_PATH = "";

    //DB 공통 column
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CARD0 = "card0";
    private static final String COLUMN_CARD1 = "card1";
    private static final String COLUMN_CARD2 = "card2";
    private static final String COLUMN_CARD3 = "card3";
    private static final String COLUMN_CARD4 = "card4";
    private static final String COLUMN_CARD5 = "card5";
    private static final String COLUMN_CARD6 = "card6";
    private static final String COLUMN_CARD7 = "card7";
    private static final String COLUMN_CARD8 = "card8";
    private static final String COLUMN_CARD9 = "card9";

    //카드 목록 테이블 column
    private static final String TABLE_CARD_LIST = "cardList";                       //카드정보 테이블 명
    private static final String CARD_COLUMN_NUMBER = "number";                      //카드 보유 장수
    private static final String CARD_COLUMN_AWAKE = "awake";                        //카드 각성도
    private static final String CARD_COLUMN_ACQUISITION_INFO = "acquisition_info";  //카드 획득처 정보
    private static final String CARD_COLUMN_GRADE = "grade";                        //카드 등급
    private static final String CARD_COLUMN_CHECK = "getCard";                        //카드 획득 유무
    private static final String CARD_COLUMN_IMGPATH = "path";

    //카드 세트 column
    private static final String TABLE_CARD_SET = "cardSet";                      //카드세트 테이블 명
    private static final String CARDSET_SETBONUS0 = "set_bonus0";               //카드세트 효과 1번째
    private static final String CARDSET_SETBONUS1 = "set_bonus1";               //카드세트 효과 2번째
    private static final String CARDSET_SETBONUS2 = "set_bonus2";               //카드세트 효과 3번째
    private static final String CARDSET_SETBONUS3 = "set_bonus3";               //카드세트 효과 4번째
    private static final String CARDSET_SETBONUS4 = "set_bonus4";               //카드세트 효과 5번째
    private static final String CARDSET_SETBONUS5 = "set_bonus5";               //카드세트 효과 6번째
    private static final String CARDSET_NEEDAWAKE0 = "needAwake0";              //0번째 효과발동에 필요한 각성도
    private static final String CARDSET_NEEDAWAKE1 = "needAwake1";              //1번째 효과발동에 필요한 각성도
    private static final String CARDSET_NEEDAWAKE2 = "needAwake2";              //2번째 효과발동에 필요한 각성도
    private static final String CARDSET_COLUMN_FAVORITE = "favorite";

    //카드 도감 테이블 column
    private static final String TABLE_CARDBOOK_ALL = "cardbook_all";            //카드 도감테이블 명
    private static final String CARDBOOK_COLUMN_VALUE = "value";                //카드 특성 증가량
    private static final String CARDBOOK_COLUMN_OPTION = "option";              //치명,특화,신속

    //추피 테이블 column
    private static final String TABLE_DEMON_EXTRA_DMG = "demon_extra_dmg";  //악추피 테이블 명
    private static final String TABLE_BEAST_EXTRA_DMG = "beast_extra_dmg";  //야추피 테이블 명
    private static final String COLUMN_DMG_P0 = "dmg_p0";                   //각성합계 1단계 데미지 보너스
    private static final String COLUMN_DMG_P1 = "dmg_p1";                   //각성합계 2단계 데미지 보너스
    private static final String COLUMN_DMG_P2 = "dmg_p2";                   //각성합계 3단계 데미지 보너스


    //즐겨찾기 목록
    private static final String FAVORITE_CARD_SET_TABLE_NAME = "favoriteCardSet";
    private static final String FAVORITE_CARD_SET_COLUMN_AWAKE = "awake";


    public CardDBHelper(@Nullable Context context) {
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
            } catch (IOException mIOException) {
                Log.v("test", "Error발생");
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
        if (oldVersion < 2) {
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET card3 = '' WHERE id = 8");
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET card4 = '' WHERE id = 8");
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET card5 = '' WHERE id = 8");
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET cardListSum = 3 WHERE id = 8");
        }
        if (oldVersion < 3) {
            db.execSQL("UPDATE " + TABLE_DEMON_EXTRA_DMG + " SET awake_sum2 = 25 WHERE id = 5");
            db.execSQL("UPDATE " + TABLE_DEMON_EXTRA_DMG + " SET awake_sum2 = 25 WHERE id = 7");
            db.execSQL("INSERT INTO " + TABLE_CARD_LIST + " VALUES(40059,'파파', 0, 0,'고급 카드 팩\n고급 카드 선택 팩 ','고급', 0,'card_uncommon_papa')");
        }
        if (oldVersion < 4) {
            db.execSQL("DELETE FROM cardList WHERE id is NULL");
        }
        if (oldVersion < 5) {
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET cardListSum = 6, card3 = '에스더 루테란', card4 = '아비시나', card5 ='마법사 로나운' WHERE id = 20");
        }
    }


    //UPDATE 카드리스트 카드 수량 수정
    public void UpdateInfoCardNum(int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET number = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드리스트 카드 각성도 수정
    public void UpdateInfoCardAwake(int input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET awake = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드리스트 카드 획득 유무 수정(카드 name)
    public void UpdateInfoCardCheck(boolean input, String cardName) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 name 값으로 카드를 파악하고 해당 카드의 획득 유무 조절.
        if (input)
            updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 1 + " WHERE name = '" + cardName + "'");
        else
            updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 0 + " WHERE name = '" + cardName + "'");
    }

    //UPDATE 카드리스트 카드 획득 유무 수정(카드 id)
    public void UpdateInfoCardCheck(boolean input, int cardId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 획득 유무 변경.
        if (input)
            updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 1 + " WHERE id = " + cardId + "");
        else
            updateColumInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 0 + " WHERE id = " + cardId + "");
    }

    //CardSetInfo 에서 즐겨찾기 유무 업데이트
    public void UpdateInfoCardSetCard(String favoriteName, int cardBookId) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + TABLE_CARD_SET + " SET favorite = '" + favoriteName + "' WHERE id = " + cardBookId);
    }

    //즐겨찾기 목록 업데이트
    public void UpdateInfoFavoriteList(int setAwake, int setActivation, String whereName) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        updateColumInfo.execSQL("UPDATE " + FAVORITE_CARD_SET_TABLE_NAME + " SET awake = " + setAwake + ", activation = " + setActivation + " WHERE name = '" + whereName + "'");
    }

    //DED 에서 카드 값 변경시 즐겨찾기 리스트에서 세트 값만 변동
    public void UpdateInfoFavoriteList(int setAwake, String whereName) {
        SQLiteDatabase updateColumInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumInfo.execSQL("UPDATE " + FAVORITE_CARD_SET_TABLE_NAME + " SET awake = " + setAwake + " WHERE name = '" + whereName + "'");
    }

    @SuppressLint("Range")
    public ArrayList<CardInfo> getCardInfo_All() {     //모든카드 리스트 가져오기
        ArrayList<CardInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_LIST + " ORDER BY id", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_NUMBER));
                int awake = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_AWAKE)));
                String acquisition_info = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_ACQUISITION_INFO));
                String grade = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_GRADE));
                int getCard = cursor.getInt((cursor.getColumnIndex(CARD_COLUMN_CHECK)));
                String imgPath = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_IMGPATH));

                CardInfo cardinfo = new CardInfo();
                cardinfo.setId(id);
                cardinfo.setName(name);
                cardinfo.setNum(count);
                cardinfo.setAwake(awake);
                cardinfo.setAcquisition_info(acquisition_info);
                cardinfo.setGrade(grade);
                if (getCard == 1)
                    cardinfo.setGetCard(true);
                else
                    cardinfo.setGetCard(false);
                cardinfo.setPath(imgPath);
                getInfo.add(cardinfo);
            }
        }
        cursor.close();

        return getInfo;
    }

    @SuppressLint("Range")
    public ArrayList<CardBookInfo> getCardBookInfo() {       //카드도감 전체 항목 가져오기
        ArrayList<CardBookInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDBOOK_ALL + " ORDER BY id", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int value = cursor.getInt(cursor.getColumnIndex(CARDBOOK_COLUMN_VALUE));
                String card0 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD6));
                String card7 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD7));
                String card8 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD8));
                String card9 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD9));
                String option = cursor.getString(cursor.getColumnIndex(CARDBOOK_COLUMN_OPTION));

                CardBookInfo cardBookInfo = new CardBookInfo();
                cardBookInfo.setId(id);
                cardBookInfo.setName(name);
                cardBookInfo.setValue(value);
                cardBookInfo.setCard0(card0);
                cardBookInfo.setCard1(card1);
                cardBookInfo.setCard2(card2);
                cardBookInfo.setCard3(card3);
                cardBookInfo.setCard4(card4);
                cardBookInfo.setCard5(card5);
                cardBookInfo.setCard6(card6);
                cardBookInfo.setCard7(card7);
                cardBookInfo.setCard8(card8);
                cardBookInfo.setCard9(card9);
                cardBookInfo.setOption(option);

                getInfo.add((cardBookInfo));
            }
        }
        cursor.close();

        return getInfo;

    }


    @SuppressLint("Range")
    public ArrayList<DemonExtraDmgInfo> getDemonExtraDmgInfo() {       //악추피 항목 가져오기
        ArrayList<DemonExtraDmgInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEMON_EXTRA_DMG + " ORDER BY id", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD6));
                String card7 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD7));
                String card8 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD8));
                String card9 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD9));
                float dmg_p0 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P0));
                float dmg_p1 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P1));
                float dmg_p2 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P2));


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
                demonExtraDmgInfo.setDmg_p0(dmg_p0);
                demonExtraDmgInfo.setDmg_p1(dmg_p1);
                demonExtraDmgInfo.setDmg_p2(dmg_p2);
                getInfo.add(demonExtraDmgInfo);
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<TestExtraDmgInfo> getExtraDmgInfo(String TABLE_NAME) {
        ArrayList<TestExtraDmgInfo> getExtraDmgInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD6));
                String card7 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD7));
                String card8 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD8));
                String card9 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD9));
                float dmgP0 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P0));
                float dmgP1 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P1));
                float dmgP2 = cursor.getFloat(cursor.getColumnIndex(COLUMN_DMG_P2));

                TestExtraDmgInfo extraDmgInfo = new TestExtraDmgInfo();
                extraDmgInfo.setId(id);
                extraDmgInfo.setName(name);
                extraDmgInfo.setCard0(card0);
                extraDmgInfo.setCard1(card1);
                extraDmgInfo.setCard2(card2);
                extraDmgInfo.setCard3(card3);
                extraDmgInfo.setCard4(card4);
                extraDmgInfo.setCard5(card5);
                extraDmgInfo.setCard6(card6);
                extraDmgInfo.setCard7(card7);
                extraDmgInfo.setCard8(card8);
                extraDmgInfo.setCard9(card9);
                extraDmgInfo.setDmgP0(dmgP0);
                extraDmgInfo.setDmgP1(dmgP1);
                extraDmgInfo.setDmgP2(dmgP2);

                getExtraDmgInfo.add(extraDmgInfo);
            }
        }
        cursor.close();
        return getExtraDmgInfo;
    }


    @SuppressLint("Range")
    public ArrayList<CardSetInfo> getCardSetInfo() {       //카드 세트 항목 가져오기
        ArrayList<CardSetInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_SET + " ORDER BY id", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String card0 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD0));
                String card1 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD1));
                String card2 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD2));
                String card3 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD3));
                String card4 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD4));
                String card5 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD5));
                String card6 = cursor.getString(cursor.getColumnIndex(COLUMN_CARD6));
                String set_bonus0 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS0));
                String set_bonus1 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS1));
                String set_bonus2 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS2));
                String set_bonus3 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS3));
                String set_bonus4 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS4));
                String set_bonus5 = cursor.getString(cursor.getColumnIndex(CARDSET_SETBONUS5));
                int needAwake0 = cursor.getInt(cursor.getColumnIndex(CARDSET_NEEDAWAKE0));
                int needAwake1 = cursor.getInt(cursor.getColumnIndex(CARDSET_NEEDAWAKE1));
                int needAwake2 = cursor.getInt(cursor.getColumnIndex(CARDSET_NEEDAWAKE2));
                String favorite = cursor.getString(cursor.getColumnIndex(CARDSET_COLUMN_FAVORITE));


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

                cardSetInfo.setNeedAwake0(needAwake0);
                cardSetInfo.setNeedAwake1(needAwake1);
                cardSetInfo.setNeedAwake2(needAwake2);
                if (favorite.equals(name))
                    cardSetInfo.setFavorite(true);
                getInfo.add((cardSetInfo));
            }
        }
        cursor.close();

        return getInfo;

    }

    @SuppressLint("Range")
    public ArrayList<FavoriteCardSetInfo> getFavoriteCardSetInfo() {     //모든카드 리스트 가져오기
        ArrayList<FavoriteCardSetInfo> getInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FAVORITE_CARD_SET_TABLE_NAME + " ORDER BY name", null);
        if (cursor.getCount() != 0) {
            //데이터가 조회된 경우 수행
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int awake = cursor.getInt((cursor.getColumnIndex(FAVORITE_CARD_SET_COLUMN_AWAKE)));
                FavoriteCardSetInfo cardInfo = new FavoriteCardSetInfo();
                cardInfo.setName(name);
                cardInfo.setAwake(awake);
                getInfo.add(cardInfo);
            }
        }
        cursor.close();

        return getInfo;
    }


}
