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

import java.util.ArrayList;

public class TestSpecial extends Fragment {
    private RecyclerView rv;
    private TestSettingCardAdapter testSettingCardAdapter;
    private TestSettingCard testSettingCard = ((TestSettingCard) TestSettingCard.testSettingCard).getTestSettingCard();

    private ArrayList<CardInfo> cardSpecial = new ArrayList<>();
    private static final String SPECIAL = "스페셜";
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;

    private CharSequence catchFilter;


    public TestSpecial newInstance() {
        TestSpecial testSpecial = new TestSpecial();
        settingCardList();
        return testSpecial;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rvCardListFragment);
        rv.setHasFixedSize(true);
        rv.setBackgroundColor(Color.parseColor("#FFDCE9"));
        testSettingCardAdapter = new TestSettingCardAdapter(getContext(), cardSpecial, testSettingCard);
        rv.setAdapter(testSettingCardAdapter);

        Bundle getData = getArguments();
        if (getData != null) {
            catchFilter = getData.getCharSequence("dataSend");
            testSettingCardAdapter.getFilter().filter(catchFilter);
        }

        return rootView;
    }

    private void settingCardList() {
        cardSpecial = new ArrayList<CardInfo>();

        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(SPECIAL)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardSpecial.add(ci);
            }
        }
    }

}
