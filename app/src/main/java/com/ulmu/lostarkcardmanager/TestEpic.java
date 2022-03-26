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
    private RecyclerView rv;
    private TestSettingCardAdapter testSettingCardAdapter;
    private TestSettingCard testSettingCard = ((TestSettingCard) TestSettingCard.testSettingCard).getTestSettingCard();

    private ArrayList<CardInfo> cardEpic = new ArrayList<>();
    private static final String EPIC = "영웅";
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;

    private CharSequence catchFilter;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    public static TestEpic newInstance() {
        TestEpic testEpic = new TestEpic();
        return testEpic;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist, container, false);

        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        rv = (RecyclerView) rootView.findViewById(R.id.rvCardListFragment);
        rv.setHasFixedSize(true);
        rv.setBackgroundColor(Color.parseColor("#ECE2FF"));
        settingCardList();
        testSettingCardAdapter = new TestSettingCardAdapter(getContext(), cardEpic, testSettingCard);
        rv.setAdapter(testSettingCardAdapter);

        Bundle getData = getArguments();
        if (getData != null) {
            catchFilter = getData.getCharSequence("dataSend");
            testSettingCardAdapter.getFilter().filter(catchFilter);
        }

        return rootView;
    }

    private void settingCardList() {
        cardEpic = new ArrayList<CardInfo>();

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onResume();
        if (isResumed()) {
            if (isVisibleToUser) {
                onCreateView(inflater, container, savedInstanceState);
            }
        }
    }
}
