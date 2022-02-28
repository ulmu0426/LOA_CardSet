package com.example.lostarkcardstatus;


import static com.example.lostarkcardstatus.MainPage.mainContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SettingCardAdapter extends RecyclerView.Adapter<SettingCardAdapter.ViewHolder> {

    private static final String[] STAT = {"치명", "특화", "신속"};

    private float DEDDmg;
    private ArrayList<CardInfo> cardInfo;
    private Context context;
    private CardDBHelper cardDBHelper;
    private ArrayList<CardInfo> useCardList;
    private ArrayList<CardInfo> filterCardInfo;
    private ArrayList<CardBookInfo> cardBookInfo;
    private ArrayList<DemonExtraDmgInfo> DEDInfo;

    public SettingCardAdapter(Context context, ArrayList<CardInfo> useCardList) {
        this.cardInfo = ((MainPage) mainContext).cardInfo;
        this.cardBookInfo = ((MainPage) mainContext).cardBookInfo;
        this.DEDInfo = ((MainPage) mainContext).DEDInfo;
        this.context = context;
        this.useCardList = useCardList;
        this.filterCardInfo = useCardList;
        cardDBHelper = new CardDBHelper(context);
    }

    public interface OnItemClickEventListener {
        void onItemClick(View a_view, int a_position);
    }

    private OnItemClickEventListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickEventListener a_listener) {
        mItemClickListener = a_listener;
    }

    public ArrayList<CardInfo> getFilterCardInfo() {
        return this.filterCardInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardlist, parent, false);

        return new SettingCardAdapter.ViewHolder(holder, mItemClickListener);
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

        holder.txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                    cardInfoDialog.setContentView(R.layout.just_card);

                    WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                    TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                    ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                    EditText etxtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                    EditText etxtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                    TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                    Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);
                    Button btnCancer = cardInfoDialog.findViewById(R.id.btnCancer_JustCard);

                    txtJustCardName.setText(filterCardInfo.get(positionGet).getName());
                    imgJustCard.setImageResource(getCardImg(filterCardInfo.get(positionGet).getName()));
                    etxtJustCardAwake.setText(filterCardInfo.get(positionGet).getAwake() + "");
                    etxtJustCardHave.setText(filterCardInfo.get(positionGet).getCount() + "");
                    txtJustCardAcquisition_info.setText(filterCardInfo.get(positionGet).getAcquisition_info());

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!(Integer.parseInt(etxtJustCardAwake.getText().toString()) == filterCardInfo.get(positionGet).getAwake())) {
                                filterCardInfo.get(positionGet).setAwake(Integer.parseInt(etxtJustCardAwake.getText().toString()));
                                cardDBHelper.UpdateInfoCardAwake(filterCardInfo.get(positionGet).getAwake(), filterCardInfo.get(positionGet).getId());
                                ((MainPage) MainPage.mainContext).cardBookUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDUpdate();
                            }
                            if (!(Integer.parseInt(etxtJustCardHave.getText().toString()) == filterCardInfo.get(positionGet).getCount())) {
                                filterCardInfo.get(positionGet).setCount(Integer.parseInt(etxtJustCardHave.getText().toString()));
                                cardDBHelper.UpdateInfoCardNum(filterCardInfo.get(positionGet).getCount(), filterCardInfo.get(positionGet).getId());
                                ((MainPage) MainPage.mainContext).cardBookUpdate();
                                ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                                haveDEDUpdate();
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

            }
        });
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog cardInfoDialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                cardInfoDialog.setContentView(R.layout.just_card);

                WindowManager.LayoutParams params = cardInfoDialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                cardInfoDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

                TextView txtJustCardName = cardInfoDialog.findViewById(R.id.txtJustCardName);
                ImageView imgJustCard = cardInfoDialog.findViewById(R.id.imgJustCard);

                EditText etxtJustCardAwake = cardInfoDialog.findViewById(R.id.etxtJustCardAwake);
                EditText etxtJustCardHave = cardInfoDialog.findViewById(R.id.etxtJustCardHave);

                TextView txtJustCardAcquisition_info = cardInfoDialog.findViewById(R.id.txtJustCardAcquisition_info);
                Button btnOk = cardInfoDialog.findViewById(R.id.btnOK_JustCard);
                Button btnCancer = cardInfoDialog.findViewById(R.id.btnCancer_JustCard);

                txtJustCardName.setText(filterCardInfo.get(positionGet).getName());
                imgJustCard.setImageResource(getCardImg(filterCardInfo.get(positionGet).getName()));
                etxtJustCardAwake.setText(filterCardInfo.get(positionGet).getAwake() + "");
                etxtJustCardHave.setText(filterCardInfo.get(positionGet).getCount() + "");
                txtJustCardAcquisition_info.setText(filterCardInfo.get(positionGet).getAcquisition_info());

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(Integer.parseInt(etxtJustCardAwake.getText().toString()) == filterCardInfo.get(positionGet).getAwake())) {
                            filterCardInfo.get(positionGet).setAwake(Integer.parseInt(etxtJustCardAwake.getText().toString()));
                            cardDBHelper.UpdateInfoCardAwake(filterCardInfo.get(positionGet).getAwake(), filterCardInfo.get(positionGet).getId());
                            ((MainPage) MainPage.mainContext).cardBookUpdate();
                            ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                            haveDEDUpdate();
                        }
                        if (!(Integer.parseInt(etxtJustCardHave.getText().toString()) == filterCardInfo.get(positionGet).getCount())) {
                            filterCardInfo.get(positionGet).setCount(Integer.parseInt(etxtJustCardHave.getText().toString()));
                            cardDBHelper.UpdateInfoCardNum(filterCardInfo.get(positionGet).getCount(), filterCardInfo.get(positionGet).getId());
                            ((MainPage) MainPage.mainContext).cardBookUpdate();
                            ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                            haveDEDUpdate();
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
        holder.txtAwakeAndHave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
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
                            haveDEDUpdate();
                            notifyDataSetChanged();
                            awakeHaveDialog.cancel();
                        }
                    });

                    awakeHaveDialog.show();

                }
            }
        });

        holder.txtAwakeAndHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        haveDEDUpdate();
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
                    filterCardInfo.get(positionGet).setGetCard(1);
                    cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setGetCard(1);
                    cardDBHelper.UpdateInfoCardCheck(1, filterCardInfo.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);

                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate(cardBookInfo);
                    haveDEDUpdate();
                } else {
                    useCardList.get(positionGet).setGetCard(0);
                    filterCardInfo.get(positionGet).setGetCard(0);
                    cardInfo.get(matchIndex(filterCardInfo.get(positionGet).getId())).setGetCard(0);
                    cardDBHelper.UpdateInfoCardCheck(0, filterCardInfo.get(positionGet).getId());
                    defaultColorFilter(holder.img, positionGet, filter);

                    ((MainPage) MainPage.mainContext).cardBookUpdate();
                    ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
                    haveStatUpdate(cardBookInfo);
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

        public ViewHolder(@NonNull View itemView, final OnItemClickEventListener mItemClickListener) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtAwakeAndHave = itemView.findViewById(R.id.txtAwakeAndHave);
            isGetCheckbox = itemView.findViewById(R.id.isGetCheckbox);

        }

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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterCardInfo = useCardList;
                } else {
                    ArrayList<CardInfo> filteringList = new ArrayList<CardInfo>();
                    for (int i = 0; i < useCardList.size(); i++) {
                        if (useCardList.get(i).getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(useCardList.get(i));
                        }
                    }
                    filterCardInfo = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterCardInfo;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterCardInfo = (ArrayList<CardInfo>) results.values;
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
    public boolean isCompleteCardBook(CardBookInfo cardbook_all) {
        if (cardbook_all.getHaveCard() == cardbook_all.getCompleteCardBook())
            return true;
        else
            return false;
    }


    public void getDefaultSort() {
        Collections.sort(filterCardInfo, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                else
                    return 1;
            }
        });
        notifyDataSetChanged();
    }

    public void getNameSort() {
        Collections.sort(filterCardInfo);
        notifyDataSetChanged();
    }

    public void allCardCheck() {

        for (int j = 0; j < filterCardInfo.size(); j++) {
            filterCardInfo.get(j).setGetCard(1);
        }
        ((MainPage) MainPage.mainContext).cardBookUpdate();
        ((MainPage) MainPage.mainContext).haveDEDCardCheckUpdate();
        haveStatUpdate(cardBookInfo);
        haveDEDUpdate();
        notifyDataSetChanged();
    }

}