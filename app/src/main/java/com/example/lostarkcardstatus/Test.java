package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    private ImageView imgJustCard;
    private ArrayList<CardInfo> cardInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_card);



    }


}
