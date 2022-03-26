package com.ulmu.lostarkcardmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TestFragmentPagerAdapter extends FragmentPagerAdapter {

    public TestFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TestLegend.newInstance();
            case 1:
                return TestEpic.newInstance();
            case 2:
                return TestRare.newInstance();
            case 3:
                return TestUncommon.newInstance();
            case 4:
                return TestCommon.newInstance();
            case 5:
                return TestSpecial.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    //상단의 탭 레이아웃 인디케이터의 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "전설";
            case 1:
                return "영웅";
            case 2:
                return "희귀";
            case 3:
                return "고급";
            case 4:
                return "일반";
            case 5:
                return "스페셜";
            default:
                return null;
        }
    }

}
