package com.ulmu.lostarkcardmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lostarkcardmanager.R;
import com.google.android.material.tabs.TabLayout;

public class GuidePage extends FragmentActivity {

    private ViewPager vpGuide;
    private TabLayout tabLayout;

    private Button skip;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        vpGuide = findViewById(R.id.vpGuide);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpGuide);

        skip = findViewById(R.id.btnSkip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }


}
