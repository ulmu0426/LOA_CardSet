package com.example.lostarkcardstatus;


import static com.example.lostarkcardstatus.MainPage.mainContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingCardAdapter extends RecyclerView.Adapter<SettingCardAdapter.ViewHolder> {

    private static final String[] STAT = {"치명", "특화", "신속"};
    private ArrayList<CardInfo> cardInfo;
    private Context context;
    private CardDBHelper cardDBHelper;
    private ArrayList<CardInfo> useCardList;
    private ArrayList<CardInfo> searchList;
    private ArrayList<CardBookInfo> cardBookInfo;

    public SettingCardAdapter(Context context, ArrayList<CardInfo> useCardList) {
        this.cardInfo = ((MainPage) mainContext).cardInfo;
        this.cardBookInfo = ((MainPage) mainContext).cardBookInfo;
        this.context = context;
        this.useCardList = useCardList;
        this.searchList = useCardList;
        cardDBHelper = new CardDBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist, parent, false);

        return new SettingCardAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);


        holder.img.setImageResource(getCardImg(searchList.get(position).getName()));
        defaultColorFilter(holder.img, position, filter);

        holder.txtName.setText(searchList.get(position).getName());

        holder.txtAwake.setText(searchList.get(position).getAwake() + "");
        holder.txtHave.setText(searchList.get(position).getCount() + "");
        holder.isGetCheckbox.setChecked(isChecked(searchList.get(position).getGetCard()));

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                cardInfoDialog.setContentView(R.layout.just_card);

                TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);
                EditText etxtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                EditText etxtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);
                TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                Button btnOk = cardInfoDialog.findViewById(R.id.btnOK);
                Button btnCancer = cardInfoDialog.findViewById(R.id.btnCancer);

                txtJustCardName.setText(searchList.get(positionGet).getName());
                imgJustCard.setImageResource(getCardImg(searchList.get(positionGet).getName()));
                etxtJustCardAwake.setText(searchList.get(positionGet).getAwake());
                etxtJustCardHave.setText(searchList.get(positionGet).getCount());
                txtJustCardAcquisition_info.setText(searchList.get(positionGet).getAcquisition_info());
                etxtJustCardAwake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtJustCardAwake.selectAll();
                    }
                });
                etxtJustCardHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtJustCardHave.selectAll();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etxtJustCardAwake.getText().toString().equals(searchList.get(positionGet).getAwake() + "")) {
                            searchList.get(positionGet).setAwake(Integer.parseInt(etxtJustCardAwake.getText().toString()));
                            cardDBHelper.UpdateInfoCardAwake(searchList.get(positionGet).getAwake(), searchList.get(positionGet).getId());
                            ((MainPage) MainPage.mainContext).cardBookUpdate();
                            ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                        }
                        if (!etxtJustCardHave.getText().toString().equals(searchList.get(positionGet).getCount() + "")) {
                            searchList.get(positionGet).setCount(Integer.parseInt(etxtJustCardHave.getText().toString()));
                            cardDBHelper.UpdateInfoCardNum(searchList.get(positionGet).getCount(), searchList.get(positionGet).getId());
                            ((MainPage) MainPage.mainContext).cardBookUpdate();
                            ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                        }

                        notifyDataSetChanged();
                        cardInfoDialog.cancel();
                    }
                });
                btnCancer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardInfoDialog.cancel();
                    }
                });

                cardInfoDialog.show();
            }
        });

        holder.changeAwakeHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog awakeHaveDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                awakeHaveDialog.setContentView(R.layout.awake_havecard_change);

                EditText etxtAwake = awakeHaveDialog.findViewById(R.id.eTxtAwake);
                EditText etxtNum = awakeHaveDialog.findViewById(R.id.etxtNum);
                Button btnCancer = awakeHaveDialog.findViewById(R.id.btnCancer);
                Button btnOK = awakeHaveDialog.findViewById(R.id.btnOK);

                etxtAwake.setText(holder.txtAwake.getText().toString());
                etxtNum.setText(holder.txtHave.getText().toString());

                etxtAwake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtAwake.selectAll();
                    }
                });
                etxtNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etxtNum.selectAll();
                    }
                });

                btnCancer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        awakeHaveDialog.cancel();
                    }
                });

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int awake = awakeRangeSet(etxtAwake.getText().toString());
                        int number = numRangeSet(etxtNum.getText().toString());
                        etxtAwake.setText(awake + "");
                        etxtNum.setText(number + "");
                        //카드 arrayList update
                        useCardList.get(positionGet).setAwake(awake);
                        useCardList.get(positionGet).setCount(number);
                        searchList.get(positionGet).setAwake(awake);
                        searchList.get(positionGet).setCount(number);

                        cardInfo.get(matchIndex(searchList.get(positionGet).getId())).setAwake(awake);
                        cardInfo.get(matchIndex(searchList.get(positionGet).getId())).setCount(number);
                        //카드 DB update
                        cardDBHelper.UpdateInfoCardAwake(awake, searchList.get(positionGet).getId());
                        cardDBHelper.UpdateInfoCardNum(number, searchList.get(positionGet).getId());

                        Toast.makeText(context, "각성도, 카드 보유 숫자 수정 완료.", Toast.LENGTH_LONG).show();
                        holder.txtAwake.setText(awake + "");
                        holder.txtHave.setText(number + "");
                        ((MainPage) MainPage.mainContext).cardBookUpdate();
                        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                        notifyDataSetChanged();
                        awakeHaveDialog.cancel();
                    }
                });

                awakeHaveDialog.show();
            }
        });

        holder.isGetCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isGetCheckbox.isChecked()) {
                    useCardList.get(positionGet).setGetCard(1);
                    searchList.get(positionGet).setGetCard(1);
                    cardInfo.get(matchIndex(useCardList.get(positionGet).getId())).setGetCard(1);
                    cardDBHelper.UpdateInfoCardCheck(1, useCardList.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);
                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate(cardBookInfo);
                } else {
                    useCardList.get(positionGet).setGetCard(0);
                    searchList.get(positionGet).setGetCard(0);
                    cardInfo.get(matchIndex(searchList.get(positionGet).getId())).setGetCard(0);
                    cardDBHelper.UpdateInfoCardCheck(0, searchList.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);

                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate(cardBookInfo);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtName;
        private TextView txtAwake;
        private TextView txtHave;
        private CheckBox isGetCheckbox;
        private LinearLayout changeAwakeHave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtAwake = itemView.findViewById(R.id.txtAwake);
            txtHave = itemView.findViewById(R.id.txtHave);
            isGetCheckbox = itemView.findViewById(R.id.isGetCheckbox);
            changeAwakeHave = itemView.findViewById(R.id.changeAwakeHave);
        }
    }


    private void defaultColorFilter(ImageView iv, int position, ColorFilter filter) {
        if (useCardList.get(position).getGetCard() == 0) {
            iv.setColorFilter(filter);
        } else {
            iv.setColorFilter(null);
        }
    }

    private boolean isChecked(int check) {
        boolean tf = false;
        if (check == 0) {
            tf = false;
        } else if (check == 1) {
            tf = true;
        }
        return tf;
    }

    private int isChecked(boolean tf) {
        int check = 0;
        if (tf)
            check = 1;
        else
            check = 0;
        return check;
    }

    private int awakeRangeSet(String input) {
        int result = 0;
        if (input.isEmpty())
            result = 0;

        if (Integer.parseInt(input) > 5) {
            result = 5;
        } else if (Integer.parseInt(input) < 0) {
            result = 0;
        } else {
            result = Integer.parseInt(input);
        }
        return result;

    }

    //카드 각성도에 따라 최대 보유 카드 수량이 달라짐. 다음에 수정 할것.
    private int numRangeSet(String input) {
        int result = 0;
        if (input.isEmpty())
            result = 0;

        if (Integer.parseInt(input) > 15) {
            result = 15;
        } else if (Integer.parseInt(input) < 0) {
            result = 0;
        } else {
            result = Integer.parseInt(input);
        }

        return result;
    }

    private int matchIndex(int id) {
        int index = 0;
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchList = useCardList;
                } else {
                    ArrayList<CardInfo> filteringList = new ArrayList<CardInfo>();
                    for (int i = 0; i < useCardList.size(); i++) {
                        if (useCardList.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(useCardList.get(i));
                        }
                    }
                    searchList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchList = (ArrayList<CardInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private int getCardImg(String cardName) {
        String name = "";
        for (int i = 0; i < cardInfo.size(); i++) {
            if (cardInfo.get(i).getName().equals(cardName)) {
                name = cardInfo.get(i).getPath();
                break;
            }
        }
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

        return imageResource;
    }


    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate(ArrayList<CardBookInfo> cardbook_all) {
        int[] haveStat = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardbook_all.size(); j++) {
                if (cardbook_all.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardbook_all.get(j))) {
                    haveStat[i] += cardbook_all.get(j).getValue();
                }
            }
        }
        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
    }

    // DB에 도감을 완성 시킨 경우 true else false
    public boolean isCompleteCardBook(CardBookInfo cardbook_all) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }

}
