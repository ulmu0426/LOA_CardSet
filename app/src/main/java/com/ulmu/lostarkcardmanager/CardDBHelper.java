package com.ulmu.lostarkcardmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
    private static final int DATABASE_VERSION = 6;

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
    private static final String CARD_COLUMN_IMG_PATH = "path";

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
        if (oldVersion < 6) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_BEAST_EXTRA_DMG +
                        " (id INTEGER, name TEXT, card0 TEXT, card1 TEXT, card2 TEXT, card3 TEXT, card4 TEXT, card5 TEXT, card6 TEXT, card7 TEXT, card8 TEXT, card9 TEXT" +
                        ",dmg_p0 REAL,dmg_p1 REAL,dmg_p2 REAL)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BEAST_EXTRA_DMG, null);

            if (cursor.getCount() == 0) {
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(0,'격돌하는 마력','아브렐슈드','아제나&이난나','','','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(1,'누나만 믿어!','사샤','검은이빨','위대한 성 네리아','아제나&이난나','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(2,'다시 살아난다 말할까','루드릭','라하르트','테르나크','지그문트','나베갈','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(3,'루테란 왕위 쟁탈전','슈헤리트','실리안','패자의 검','카마인','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(4,'무기여 잘 있거라','패자의 검','벨크루제','파르쿠나스','진멸의 창','피요르긴','나히니르','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(5,'뼈대있는 가문','마법사 로나운','몬테르크','비슈츠','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(6,'사자탈과 함께 춤을','사자탈','한손','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(7,'세이크리아의 사제','아만','바루투','집행관 솔라스','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(8,'소녀시대','아나벨','마리 파우렌츠','타냐 벤텀','표류소녀 엠마','레나','투란','첼라','여울','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(9,'시엔 여관','아만','세리아','샨디','진저웨일','모르페오','','','','','',0.13,0.13,0.14)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(10,'알트아이젠','시그나투스','솔 그랑데','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(11,'애니츠의 수호신','하누마탄','가디언 루','','','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(12,'역병의 인도자들','역병 인도자','역병군단 바르토','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(13,'오, 아름다운 칼라자여','나비','칼도르','비올레','루티아','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(14,'오레하의 악연','알비온','아르고스','광기를 잃은 쿠크세이튼','','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(15,'욕망군다','비아키스','절망의 레키엘','키즈라','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(16,'운명의 무게','에스더 시엔','사이카','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(17,'정령의 땅','아브렐슈드','아제나&이난나','에페르니아','게르디아','운다트','그노시스','실페리온','','','',0.13,0.13,0.14)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(18,'카단의 행방','카단','신디','데스칼루다','베른 젠로드','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(19,'토토이크의 지혜야!','하이비 집행관','모카모카','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(20,'트리시온','신뢰의 아크 아스타','창조의 아크 오르투스','예지의 아크 아가톤','희망의 아크 엘피스','지혜의 아크 라디체','헌신의 아크 카르타','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(21,'하늘을 비추는 사막','소금거인','천둥','모리나','다단','자이언트 웜','타나토스','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_BEAST_EXTRA_DMG + " VALUES(22,'힘의 잔영','아이히만 박사','카인','에스','제이','','','','','','',0.06,0.07,0.07)");
            }
        }

    }


    //UPDATE 카드리스트 카드 수량 수정
    public void UpdateInfoCardNum(int input, int cardId) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET number = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드리스트 카드 수량 수정(이름으로)
    public void UpdateInfoCardNum(int input, String cardName) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 수량 조절.
        updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET number = " + input + " WHERE name = '" + cardName + "'");
    }

    //UPDATE 카드리스트 카드 각성도 수정
    public void UpdateInfoCardAwake(int input, int cardId) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET awake = " + input + " WHERE id = " + cardId);
    }

    //UPDATE 카드리스트 카드 각성도 수정(이름으로)
    public void UpdateInfoCardAwake(int input, String cardName) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 각성도 조절.
        updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET awake = " + input + " WHERE name = '" + cardName + "'");
    }

    //UPDATE 카드리스트 카드 획득 유무 수정(카드 name)
    public void UpdateInfoCardCheck(boolean input, String cardName) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 name 값으로 카드를 파악하고 해당 카드의 획득 유무 조절.
        if (input)
            updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 1 + " WHERE name = '" + cardName + "'");
        else
            updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 0 + " WHERE name = '" + cardName + "'");
    }

    //UPDATE 카드리스트 카드 획득 유무 수정(카드 id)
    public void UpdateInfoCardCheck(boolean input, int cardId) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //카드 id 값으로 카드를 파악하고 해당 카드의 획득 유무 변경.
        if (input)
            updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 1 + " WHERE id = " + cardId + "");
        else
            updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_LIST + " SET getCard = " + 0 + " WHERE id = " + cardId + "");
    }

    //CardSetInfo 에서 즐겨찾기 유무 업데이트
    public void UpdateInfoCardSetCard(String favoriteName, int cardSetId) {
        SQLiteDatabase updateColumnInfo = getWritableDatabase();
        //cardbook name 값으로 파악하고 해당 카드의 획득 유무 수정.
        updateColumnInfo.execSQL("UPDATE " + TABLE_CARD_SET + " SET favorite = '" + favoriteName + "' WHERE id = " + cardSetId);
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
                String imgPath = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_IMG_PATH));

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
    public ArrayList<ExtraDmgInfo> getExtraDmgInfo(String TABLE_NAME) {
        ArrayList<ExtraDmgInfo> getExtraDmgInfo = new ArrayList<>();
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

                ExtraDmgInfo extraDmgInfo = new ExtraDmgInfo();
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


}
