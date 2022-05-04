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
    private static final int DATABASE_VERSION = 9;

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
    private static final String TABLE_HUMAN_EXTRA_DMG = "human_extra_dmg";  //인추피 테이블 명
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
        if (oldVersion < 7) {

            try {
                db.execSQL("DROP TABLE favoriteCardSet");
            } catch (Exception e) {
                e.printStackTrace();
            }

            db.execSQL("INSERT INTO cardList VALUES (10025, '에버그레이스', 0,0,'[수집품] 이그네아의 징표(16)\n" +
                    "[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','전설',0,'card_legend_evergrace')");
            db.execSQL("INSERT INTO cardList VALUES (20081, '라우리엘', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','영웅',0,'card_epic_rauriel')");
            db.execSQL("INSERT INTO cardList VALUES (20082, '영원의 아크 카양겔', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','영웅',0,'card_epic_kayangel');");
            db.execSQL("INSERT INTO cardList VALUES (30110, '아자키엘', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','희귀',0,'card_rare_azakiel')");
            db.execSQL("INSERT INTO cardList VALUES (30111, '디오게네스', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','희귀',0,'card_rare_diogenes')");
            db.execSQL("INSERT INTO cardList VALUES (30112, '벨루마테', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','희귀',0,'card_rare_bellumate')");
            db.execSQL("INSERT INTO cardList VALUES (30113, '다이나웨일', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','희귀',0,'card_rare_dienawhale')");
            db.execSQL("INSERT INTO cardList VALUES (40060, '하늘 고래', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_sky_whale')");
            db.execSQL("INSERT INTO cardList VALUES (40061, '별자리 큰뱀', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_constellation_bic_snake')");
            db.execSQL("INSERT INTO cardList VALUES (40062, '티엔', 0,0,'[호감도] 엘가시아 - 티엔\n" +
                    "[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_tien')");
            db.execSQL("INSERT INTO cardList VALUES (40063, '프리우나', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_priuna')");
            db.execSQL("INSERT INTO cardList VALUES (40064, '유클리드', 0,0,'[호감도] 엘가시아 - 예언자 유클리드\n" +
                    "[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_euclid')");
            db.execSQL("INSERT INTO cardList VALUES (40065, '키르케', 0,0,'[호감도] 엘가시아 - 예언자 키르케\n" +
                    "[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','고급',0,'card_uncommon_circe')");
            db.execSQL("INSERT INTO cardList VALUES (50029, '코니', 0,0,'[필드보스]이스라펠(규율의 카드 팩)\n" +
                    "[어비스던전]카양겔(규율의 카드 팩)','일반',0,'card_common_connie')");

            //신규 카드 도감 추가(치,특)
            db.execSQL("INSERT INTO cardbook_all VALUES(53,'사슬전쟁의 종장',3,'에버그레이스','에스더 루테란', '미스틱', '알비온', '카단', '니나브','','','','','치명')");
            db.execSQL("INSERT INTO cardbook_all VALUES(54,'플라티나의 주민들',2,'에버그레이스','두키킹', '혼재의 추오', '', '', '','','','','','특화')");
            //악추피 추가
            db.execSQL("INSERT INTO demon_extra_dmg VALUES(23,'엘베리아의 기적','에버그레이스','라하르트', '에아달린', '아델', '지그문트', '가룸','','','','',0.1,0.1,0.1)");
            //야추피 추가
            db.execSQL("INSERT INTO beast_extra_dmg VALUES(23,'빛의 생명체들','다이나웨일','하늘 고래', '별자리 큰뱀', '코니', '', '','','','','',0.06,0.07,0.07)");

            //트리시온 카드 수정
            db.execSQL("UPDATE " + TABLE_CARDBOOK_ALL + " SET card6 = '영원의 아크 카양겔' WHERE name = '트리시온'");
            db.execSQL("UPDATE " + TABLE_BEAST_EXTRA_DMG + " SET card6 = '영원의 아크 카양겔' WHERE name = '트리시온'");
            db.execSQL("UPDATE " + TABLE_CARD_SET + " SET card6 = '영원의 아크 카양겔' WHERE name = '트리시온'");

            //카드 수집 경로 추가
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[모험의 서] 루테란 동부\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '실리안'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[업적] 격동하는 대지에 침묵을\n" +
                    "[군단장 레이드] 마수군단장 발탄\n" +
                    "심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '발탄'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[업적 달성] : 업적 미구현 \n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '샨디'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[수집품] 이그네아의 징표(6)\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '진저웨일'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[군단장 레이드] 광기군단장 쿠크세이튼\n" +
                    "[업적] 그 광기, 내가 치료해주지\n" +
                    "심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '쿠크세이튼'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[대도시] 떠돌이 상인\n" +
                    "[업적 달성] 보스 헌터 : 고급\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '웨이'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[군단장 레이드] 욕망군단장 비아키스\n" +
                    "[업적] 욕망군단장을 정복한 자\n" +
                    "심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '비아키스'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[업적 달성] 흉가 체험\n" +
                    "[대항해] 악몽을 떠도는 유령선\n" +
                    "[대항해] 그림자를 헤매는 유령선\n" +
                    "[대항해] 폭풍을 부르는 유령선\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '일리아칸'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '카멘'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[군단장 레이드] 몽환군단장 아브렐슈드\n" +
                    "[업적] 현실과 꿈의 경계\n" +
                    "심연의 전설 카드 팩\n" +
                    "군단장 카드 선택 상자' WHERE name = '아브렐슈드'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[호감도] 트리시온 - 베아트리스\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '베아트리스'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전 - 몽환의 궁전 부터 드랍\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[호감도] 로아룬 - 아제나\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩' WHERE name = '아제나&이난나'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전 - 오만의 방주부터 드랍\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[업적 달성] 쇼는 계속되어야 한다!\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '바훈투르'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[비밀지도]\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[업적 달성] 비밀의 공간\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '에스더 루테란'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[비밀지도]\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[업적 달성] 비밀의 공간\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '에스더 시엔'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[비밀지도]\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[업적 달성] 비밀의 공간\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '에스더 갈라투르'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전 - 낙원의 문부터 드랍\n" +
                    "어비스 레이드\n" +
                    "군단장 레이드\n" +
                    "[호감도] 속삭이는 작은 섬 - 니나브\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '니나브'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[퀘스트] 상처 입은 새의 눈물\n" +
                    "전설 카드 팩\n" +
                    "심연의 전설 카드 팩\n" +
                    "참고 : 전설카드 선택 팩으로 획득할 수 없음' WHERE name = '카단'");
            db.execSQL("UPDATE " + TABLE_CARD_LIST + " SET acquisition_info = '[던전] 어비스 던전 - 오레하의 우물\n" +
                    "[업적 달성] 모험의 서 : 파푸니카\n" +
                    "전설 카드 팩\n" +
                    "전설 카드 선택 팩\n" +
                    "심연의 전설 카드 팩' WHERE name = '광기를 잃은 쿠크세이튼'");

        }

        if (oldVersion < 8) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_HUMAN_EXTRA_DMG +
                        " (id INTEGER, name TEXT, card0 TEXT, card1 TEXT, card2 TEXT, card3 TEXT, card4 TEXT, card5 TEXT, card6 TEXT, card7 TEXT, card8 TEXT, card9 TEXT" +
                        ",dmg_p0 REAL,dmg_p1 REAL,dmg_p2 REAL)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HUMAN_EXTRA_DMG, null);

            if (cursor.getCount() == 0) {
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(0,'고블리하도다!','고블린 장로 발루','고비우스 24세','네스','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(1,'광기군단','쿠크세이튼','크란테루스','멜피셔스','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(2,'굶주린 늑대의 길','뮨 히다카','오스피어','다르시','빌헬름','레퓌스','하눈','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(3,'그림자도 밟지 말라 했거늘','난민 파밀리아','고블린 장로 발루','베나르','바루투','천둥','샨디','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(4,'기록되지 않은 승부','일리아칸','에스더 시엔','','','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(5,'남부에 떠오른 태양','제레온','루드벡','하템','키에사','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(6,'내 동료가 되어라!','모카모카','검은이빨','칼스 모론토','아나벨','세비엘','포포','다쿠쿠','표류소녀 엠마','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(7,'냠냠꿀꺽 먹고 싶어!','알리페르','아벤','투란','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(8,'라제니스','베아트리스','니나브','알레그로','','','','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(9,'무쇠팔 무쇠다리','바스티안','진화의 군주 카인','시그나투스','솔 그랑데','에스','제이','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(10,'백귀야행','반다','미령','도철','삭월','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(11,'부정한 자를 포박하라!','하백','파한','웨이','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(12,'사슬전쟁의 에스더','샨디','아제나&이난나','니나브','카단','에스더 갈라투르','에스더 루테란','에스더 시엔','','','',0.13,0.13,0.14)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(13,'살아서 다시 보길 바란다','진 매드닉','바에단','시안','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(14,'세계의 방랑자','카드리','에스더 시엔','샨디','진저웨일','표류소녀 엠마','','','','','',0.13,0.13,0.14)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(15,'왕위를 물려받을 준비','슈헤리트','베르하트','','','','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(16,'우마르 맙소사!','우르르','피에르','이마르','에이케르','나베르','이와르','케이사르','바훈투르','에스더 갈라투르','위대한 성 네리아',0.13,0.13,0.14)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(17,'주인님, 명령을 내려주십시오!','사교도 대제사장','바에단','도굴단장 우고','붉은 남작 에디','삭월','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(18,'질병군단','일리아칸','역병 인도자','하르잘','칼라도세','역병군단 바르토','루아브','나크슌','도륙자 아르르','나잔','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(19,'창의 달인','라하르트','진저웨일','몽환의 나이트','엘버하스틱','아르카디아','하이비 집행관','','','','',0.1,0.1,0.1)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(20,'창천의 수호자','웨이','객주도사','수령도사','월향도사','파한','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(21,'추락한 태양','라하르트','테르나크','나베갈','지그문트','가룸','','','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(22,'필드 보스II','마네스','타르실라','솔 그랑데','브리아레오스','수신 아포라스','고르카그로스','아드린느','','','',0.06,0.07,0.07)");
                db.execSQL("INSERT INTO " + TABLE_HUMAN_EXTRA_DMG + " VALUES(23,'둥지 위로 날아간 뻐꾸기','에버그레이스','혼재의 추오','','','','','','','','',0.1,0.1,0.1)");
            }
        }
        if (oldVersion < 9) {
            //오류대응을 위한 db update
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD_SET, null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range")
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    if (!name.equals("플라티나의 주민들")){
                        return;
                    }
                }
                db.execSQL("INSERT INTO cardSet VALUES(36, '플라티나의 주민들', '에버그레이스', '두키킹', '혼재의 추오','','','','', '3세트 : 가디언 토벌 시 가디언에게 받는 피해 7.5% 감소', '3세트(6각성합계) : 헤드어택 성공 시 적에게 주는 피해 % 증가', '3세트(15각성합계) : 헤드어택 성공 시 적에게 주는 피해 10% 증가','','','',6,12,0,'')");
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
