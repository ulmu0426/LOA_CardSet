package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class Test extends AppCompatActivity {

    private ImageView imgJustCard;
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardSetInfo> cardSetInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_card);

        cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;
        for (int i = 0; i < cardSetInfo.size(); i++) {
            Log.v("test", "name : " + cardSetInfo.get(i).getName());
        }

        Collections.sort(cardSetInfo);

        Log.v("test", "------------절취선-------------");

        for (int i = 0; i < cardSetInfo.size(); i++) {
            Log.v("test", "name : " + cardSetInfo.get(i).getName());
        }

        Log.v("test", "------------절취선-------------");

        Collections.sort(cardSetInfo, new Comparator<CardSetInfo>() {
            @Override
            public int compare(CardSetInfo o1, CardSetInfo o2) {
                if (o1.getId() < o2.getId()) {
                    return -1;
                } else
                    return 1;
            }
        });

        for (int i = 0; i < cardSetInfo.size(); i++) {
            Log.v("test", "name : " + cardSetInfo.get(i).getName());
        }

    }
}