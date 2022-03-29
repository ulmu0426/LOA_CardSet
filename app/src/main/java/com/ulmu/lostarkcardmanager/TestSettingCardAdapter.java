package com.ulmu.lostarkcardmanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestSettingCardAdapter extends RecyclerView.Adapter<TestSettingCardAdapter.ViewHolder> {

    private static final String[] STAT = {"치명", "특화", "신속"};

    private float DEDDmg;
    private ArrayList<CardBookInfo> cardBookInfo;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;
    private FavoriteAdapter favoriteAdapter;

    private Context context;
    private ArrayList<CardInfo> cardInfo;
    private ArrayList<CardInfo> filterCardInfo;
    private ArrayList<CardInfo> useCardList;
    private CardDBHelper cardDBHelper;
    private ArrayList<FavoriteCardSetInfo> favoriteCardSetInfo;
    private ArrayList<CardSetInfo> cardSetInfo;

    public TestSettingCardAdapter(Context context, ArrayList<CardInfo> useCardList) {
        this.cardInfo = ((MainPage) MainPage.mainContext).cardInfo;
        this.context = context;
        this.cardBookInfo = ((MainPage) MainPage.mainContext).cardBookInfo;
        this.DEDInfo = ((MainPage) MainPage.mainContext).DEDInfo;
        this.favoriteAdapter = ((MainPage) MainPage.mainContext).favoriteAdapter;
        this.favoriteCardSetInfo = ((MainPage) MainPage.mainContext).favoriteCardSetInfo;
        this.cardSetInfo = ((MainPage) MainPage.mainContext).cardSetInfo;
        this.useCardList = useCardList;
        this.filterCardInfo = useCardList;
        cardDBHelper = new CardDBHelper(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist, parent, false);

        return new TestSettingCardAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionGet = position;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        holder.img.setImageResource(getCardImg(filterCardInfo.get(position).getName()));
        defaultColorFilter(holder.img, position, filter);

        holder.txtName.setText(filterCardInfo.get(position).getName());

        holder.txtAwakeAndHave.setText("각성 : " + filterCardInfo.get(position).getAwake() + "  보유 : " + filterCardInfo.get(position).getCount());
        holder.isGetCheckbox.setChecked(isChecked(filterCardInfo.get(position).getGetCard()));

        //click 대신 touch 로 변경. -> click 을 두번 해야 Dialog 가 뜨던 현상 방지를 위해.
        holder.txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        cardInfoDialog.setContentView(R.layout.just_card);

                        WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                        TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                        ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                        TextView txtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                        TextView txtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                        TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                        Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);

                        txtJustCardName.setText(filterCardInfo.get(positionGet).getName());
                        imgJustCard.setImageResource(getCardImg(filterCardInfo.get(positionGet).getName()));
                        txtJustCardAwake.setText(filterCardInfo.get(positionGet).getAwake() + "");
                        txtJustCardHave.setText(filterCardInfo.get(positionGet).getCount() + "");
                        txtJustCardAcquisition_info.setText(filterCardInfo.get(positionGet).getAcquisition_info());

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardInfoDialog.cancel();
                            }
                        });

                        cardInfoDialog.show();
                }

                return false;
            }
        });

        holder.txtAwakeAndHave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Dialog awakeHaveDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                        awakeHaveDialog.setContentView(R.layout.awake_havecard_change);

                        NumberPicker numberPickerAwake = awakeHaveDialog.findViewById(R.id.numberPickerAwake);
                        numberPickerAwake.setMinValue(0);
                        numberPickerAwake.setMaxValue(5);
                        numberPickerAwake.setWrapSelectorWheel(false);

                        NumberPicker numberPickerHave = awakeHaveDialog.findViewById(R.id.numberPickerHave);
                        numberPickerHave.setMinValue(0);
                        numberPickerHave.setMaxValue(15);
                        numberPickerHave.setWrapSelectorWheel(false);

                        Button btnCancer = awakeHaveDialog.findViewById(R.id.btnCancer);
                        Button btnOK = awakeHaveDialog.findViewById(R.id.btnOK);
                        numberPickerAwake.setValue(filterCardInfo.get(positionGet).getAwake());
                        numberPickerAwake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                            }
                        });
                        numberPickerHave.setMaxValue(maxHaveValue(numberPickerAwake.getValue()));
                        numberPickerHave.setValue(filterCardInfo.get(positionGet).getCount());

                        btnCancer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                awakeHaveDialog.cancel();
                            }
                        });

                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int awake = numberPickerAwake.getValue();
                                int number = numberPickerHave.getValue();
                                numberPickerAwake.setValue(awake);
                                numberPickerHave.setValue(number);

                                //카드 arrayList update
                                useCardList.get(positionGet).setAwake(awake);
                                useCardList.get(positionGet).setCount(number);
                                filterCardInfo.get(positionGet).setAwake(awake);
                                filterCardInfo.get(positionGet).setCount(number);

                                cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setAwake(awake);
                                cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setCount(number);

                                //카드 DB update
                                cardDBHelper.UpdateInfoCardAwake(awake, filterCardInfo.get(positionGet).getId());
                                cardDBHelper.UpdateInfoCardNum(number, filterCardInfo.get(positionGet).getId());

                                holder.txtAwakeAndHave.setText("각성 : " + awake + "  보유 : " + number);
                                ((MainPage) MainPage.mainContext).cardBookUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                favoriteAdapter.setAwake(filterCardInfo.get(positionGet).getName(), filterCardInfo.get(positionGet).getAwake());

                                haveStatUpdate();
                                haveDEDUpdate();
                                notifyDataSetChanged();
                                awakeHaveDialog.cancel();
                            }
                        });

                        awakeHaveDialog.show();
                }
                return false;
            }
        });


        holder.isGetCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isGetCheckbox.isChecked()) {
                    useCardList.get(positionGet).setGetCard(1);
                    filterCardInfo.get(positionGet).setGetCard(1);
                    cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setGetCard(1);
                    cardDBHelper.UpdateInfoCardCheck(1, filterCardInfo.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);

                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate();
                    haveDEDUpdate();
                } else {
                    useCardList.get(positionGet).setGetCard(0);
                    filterCardInfo.get(positionGet).setGetCard(0);
                    cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setGetCard(0);
                    cardDBHelper.UpdateInfoCardCheck(0, filterCardInfo.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);

                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate();
                    haveDEDUpdate();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return filterCardInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtName;
        private TextView txtAwakeAndHave;
        private CheckBox isGetCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtAwakeAndHave = itemView.findViewById(R.id.txtAwakeAndHave);
            isGetCheckbox = itemView.findViewById(R.id.isGetCheckbox);
        }
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

    private void defaultColorFilter(ImageView iv, int position, ColorFilter filter) {
        if (filterCardInfo.get(position).getGetCard() == 0) {
            iv.setColorFilter(filter);
            iv.setBackgroundColor(Color.parseColor("#FFFFFF"));

        } else {
            iv.setColorFilter(null);
            setCardBorder(iv, position);
        }
    }

    private void setCardBorder(ImageView iv, int position) {
        if (filterCardInfo.get(position).getGrade().equals("전설")) {
            iv.setBackgroundColor(Color.parseColor("#FFB300"));
        } else if (filterCardInfo.get(position).getGrade().equals("영웅")) {
            iv.setBackgroundColor(Color.parseColor("#5E35B1"));
        } else if (filterCardInfo.get(position).getGrade().equals("희귀")) {
            iv.setBackgroundColor(Color.parseColor("#1E88E5"));
        } else if (filterCardInfo.get(position).getGrade().equals("고급")) {
            iv.setBackgroundColor(Color.parseColor("#7CB342"));
        } else if (filterCardInfo.get(position).getGrade().equals("일반")) {
            iv.setBackgroundColor(Color.parseColor("#A1A1A1"));
        } else if (filterCardInfo.get(position).getGrade().equals("스페셜")) {
            iv.setBackgroundColor(Color.parseColor("#DF4F84"));
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

    private int maxHaveValue(int haveAwake) {
        if (haveAwake == 5) {
            return 0;
        } else if (haveAwake == 4) {
            return 5;
        } else if (haveAwake == 3) {
            return 9;
        } else if (haveAwake == 2) {
            return 12;
        } else if (haveAwake == 1) {
            return 14;
        } else if (haveAwake == 0) {
            return 15;
        } else
            return 0;
    }

    //스텟, 도감 달성 개수 업데이트 메소드
    private void haveStatUpdate() {
        int[] haveStat = new int[]{0, 0, 0};

        for (int i = 0; i < haveStat.length; i++) {
            for (int j = 0; j < cardBookInfo.size(); j++) {
                if (cardBookInfo.get(j).getOption().equals(STAT[i]) && isCompleteCardBook(cardBookInfo.get(j))) {
                    haveStat[i] += cardBookInfo.get(j).getValue();
                }
            }
        }
        ((MainPage) MainPage.mainContext).setCardBookStatInfo(haveStat);
    }

    //DED Dmb 값
    private void haveDEDUpdate() {
        DecimalFormat df = new DecimalFormat("0.00");//소수점 둘째자리까지 출력
        DEDDmg = 0;
        for (int i = 0; i < DEDInfo.size(); i++) {
            DEDDmg += DEDInfo.get(i).getDmgSum();
        }
        DEDDmg = Float.parseFloat(df.format(DEDDmg));
        ((MainPage) MainPage.mainContext).setDemonExtraDmgInfo(DEDDmg);
    }

    // DB에 도감을 완성 시킨 경우 true else false
    private boolean isCompleteCardBook(CardBookInfo cardBookInfo) {
        if (cardBookInfo.getHaveCard() == cardBookInfo.getCompleteCardBook())
            return true;
        else
            return false;
    }


}