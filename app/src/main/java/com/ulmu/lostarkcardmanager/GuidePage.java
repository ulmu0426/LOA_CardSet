package com.ulmu.lostarkcardmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.lostarkcardmanager.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GuidePage extends FragmentActivity {

    private ViewPager vpGuide;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    private Button skip;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        vpGuide = findViewById(R.id.vpGuide);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.addFragment(new StepOneFragment());
        viewPagerAdapter.addFragment(new StepTwoFragment());
        viewPagerAdapter.addFragment(new StepThreeFragment());
        viewPagerAdapter.addFragment(new StepFourFragment());


        vpGuide.setAdapter(viewPagerAdapter);
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

        next = findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vpGuide.getCurrentItem() == viewPagerAdapter.getCount()) {
                    finish();
                } else {
                    vpGuide.setCurrentItem(vpGuide.getCurrentItem() + 1, true);
                }

            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }

    public static class StepOneFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.guide_step_one, container, false);
        }
    }

    public static class StepTwoFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.guide_step_two, container, false);
        }
    }

    public static class StepThreeFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.guide_step_three, container, false);
        }
    }

    public static class StepFourFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.guide_step_four, container, false);
        }
    }
}
