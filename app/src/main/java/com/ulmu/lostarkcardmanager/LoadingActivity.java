package com.ulmu.lostarkcardmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class LoadingActivity extends Activity {

    //로딩화면
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MainPage.class));
        finish();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
