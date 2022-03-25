package com.ulmu.lostarkcardmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostarkcardmanager.R;

import java.util.ArrayList;

public class TestEpic extends Fragment {
    private static String EPIC = "영웅";
    private RecyclerView rvE;
    private TestSettingCardAdapter testSettingCardAdapter;
    private TestSettingCard testSettingCard = ((TestSettingCard) TestSettingCard.testSettingCard).getTestSettingCard();

    private ArrayList<CardInfo> cardEpic = new ArrayList<>();
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;

    public static TestEpic newInstance(){
        TestEpic testEpic = new TestEpic();
        return testEpic;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist,container,false);

        rvE = (RecyclerView) rootView.findViewById(R.id.rvCardListFragment);
        rvE.setHasFixedSize(true);
        rvE.setBackgroundColor(Color.parseColor("#ECE2FF"));
        settingEpic();
        testSettingCardAdapter = new TestSettingCardAdapter(getContext(), cardEpic, testSettingCard);
        rvE.setAdapter(testSettingCardAdapter);

        return rootView;
    }

    private void settingEpic(){
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(EPIC)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardEpic.add(ci);
            }
        }
    }
}
