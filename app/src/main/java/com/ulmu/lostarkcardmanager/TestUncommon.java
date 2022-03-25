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

public class TestUncommon extends Fragment {
    private static String Uncommon = "고급";
    private RecyclerView rvU;
    private TestSettingCardAdapter testSettingCardAdapter;
    private TestSettingCard testSettingCard = ((TestSettingCard) TestSettingCard.testSettingCard).getTestSettingCard();

    private ArrayList<CardInfo> cardUncommon = new ArrayList<>();
    private ArrayList<CardInfo> cardInfo = ((MainPage) MainPage.mainContext).cardInfo;

    public static TestUncommon newInstance(){
        TestUncommon testUncommon = new TestUncommon();
        return testUncommon;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cardlist,container,false);

        rvU = (RecyclerView) rootView.findViewById(R.id.rvCardListFragment);
        rvU.setHasFixedSize(true);
        settingUncommon();
        rvU.setBackgroundColor(Color.parseColor("#DEFFBB"));
        testSettingCardAdapter = new TestSettingCardAdapter(getContext(), cardUncommon, testSettingCard);
        rvU.setAdapter(testSettingCardAdapter);

        return rootView;
    }

    private void settingUncommon(){
        for (int i = 0; i < cardInfo.size(); i++) {
            CardInfo ci = new CardInfo();
            if (cardInfo.get(i).getGrade().equals(Uncommon)) {
                ci.setId(cardInfo.get(i).getId());
                ci.setName(cardInfo.get(i).getName());
                ci.setCount(cardInfo.get(i).getCount());
                ci.setAwake(cardInfo.get(i).getAwake());
                ci.setAcquisition_info(cardInfo.get(i).getAcquisition_info());
                ci.setGetCard(cardInfo.get(i).getGetCard());
                ci.setGrade(cardInfo.get(i).getGrade());
                cardUncommon.add(ci);
            }
        }
    }
}
