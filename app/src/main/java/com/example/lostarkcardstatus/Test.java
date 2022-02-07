package com.example.lostarkcardstatus;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Test extends AppCompatActivity {

    private ImageView imgJustCard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_card);


        imgJustCard = findViewById(R.id.imgJustCard);

        int imageResource = this.getResources().getIdentifier("aman", "drawable", this.getPackageName());

        imgJustCard.setImageResource(imageResource);

    }
}
