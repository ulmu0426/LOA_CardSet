package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class GuidePageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public GuidePageAdapter(Context context) {
        this.context = context;
    }

    public int[] guide_img = {

            R.drawable.guide0,
            R.drawable.guide1,
            R.drawable.guide2,
            R.drawable.guide3,
            R.drawable.guide4


    };


    @Override
    public int getCount() {
        return guide_img.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.vp_guide, container, false);

        ImageView ivGuide = (ImageView) view.findViewById(R.id.ivGuide);

        ivGuide.setImageResource(guide_img[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }
}
