package com.ulmu.lostarkcardmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.lostarkcardmanager.R;

import java.util.ArrayList;

public class TestViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<CardInfo> cardInfo;
    private TestSettingCardAdapter testSettingCardAdapter;
    private TestSettingCard testSettingCard = ((TestSettingCard) TestSettingCard.testSettingCard).getTestSettingCard();

    private ArrayList<ViewPager> viewPagers;

    public TestViewPagerAdapter(Context context, ArrayList<CardInfo> cardInfo) {
        this.context = context;
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.testSettingCardAdapter = new TestSettingCardAdapter(context, cardInfo, testSettingCard);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cardlist_viewpager, null);
        RecyclerView rv = view.findViewById(R.id.rvCardListFragment);
        rv.setAdapter(testSettingCardAdapter);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }
}
