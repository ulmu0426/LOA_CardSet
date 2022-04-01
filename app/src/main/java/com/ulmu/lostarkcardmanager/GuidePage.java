package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class GuidePage extends AppCompatActivity {
    private Context context;

    private ViewPager vpGuide;
    private LinearLayout tabLayout;

    private GuidePageAdapter guidePageAdapter;

    private TextView[] dots;

    int currentPage;
    private Button previous;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide);

        context = this;

        vpGuide = findViewById(R.id.vpGuide);
        tabLayout = findViewById(R.id.tbGuide);

        guidePageAdapter = new GuidePageAdapter(this);

        vpGuide.addOnPageChangeListener(selectedDotsColor);
        addDotsIndicator(0);

        vpGuide.setAdapter(guidePageAdapter);


        previous = findViewById(R.id.btnPrevious);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpGuide.setCurrentItem(currentPage - 1);
            }
        });

        next = findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpGuide.setCurrentItem(currentPage + 1);
            }
        });
    }


    public void addDotsIndicator(int position) {
        dots = new TextView[5];
        tabLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.default_white));

            tabLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.selected_white));
        }
    }

    ViewPager.OnPageChangeListener selectedDotsColor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;
            Log.v("test", "position : " + position);
            if (currentPage == 0) {
                next.setEnabled(true);
                previous.setEnabled(false);
                previous.setVisibility(View.INVISIBLE);

                next.setText("다음");
                previous.setText("");
            } else if (currentPage == dots.length - 1) {
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);

                next.setText("종료");
                previous.setText("이전");

            } else {
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);

                next.setText("다음");
                previous.setText("이전");

            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vpGuide.setCurrentItem(position + 1);
                    if (position == dots.length - 1) {
                        finish();
                    }
                }
            });
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
