package com.ulmu.lostarkcardmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TestExtraDmgAdapter extends RecyclerView.Adapter<TestExtraDmgAdapter.Viewholder> {

    public TestExtraDmgAdapter() {

    }

    @NonNull
    @Override
    public TestExtraDmgAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TestExtraDmgAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
