package com.ulmu.lostarkcardmanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TestFragmentPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragmentArrayList;

    public TestFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragmentArrayList) {
        super(fragmentActivity);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void filtering(int position) {
        switch (position) {
            case 0:
                fragmentArrayList.set(position, new TestLegend().newInstance());
                break;
            case 1:
                fragmentArrayList.set(position, new TestEpic().newInstance());
                break;
            case 2:
                fragmentArrayList.set(position, new TestRare().newInstance());
                break;
            case 3:
                fragmentArrayList.set(position, new TestUncommon().newInstance());
                break;
            case 4:
                fragmentArrayList.set(position, new TestCommon().newInstance());
                break;
            case 5:
                fragmentArrayList.set(position, new TestSpecial().newInstance());
                break;
        }
        notifyDataSetChanged();
    }

}
