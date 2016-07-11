package com.vis.iyesug.weather.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vis.iyesug.weather.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.id_textview);
    }
}
