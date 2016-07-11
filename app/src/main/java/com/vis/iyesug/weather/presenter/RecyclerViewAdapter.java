package com.vis.iyesug.weather.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vis.iyesug.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    public Context mContext;
    public List<String> mData;
    public LayoutInflater mLayoutinflater;
    public OnItemClickListener mOnitemclicklistener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnitemclicklistener = listener;
    }

    public  RecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
        mLayoutinflater = LayoutInflater.from(mContext);

        //数据源
        mData = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mData.add((char) i + "");
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=mLayoutinflater.inflate(R.layout.item,parent,false);
        RecyclerViewHolder holder=new RecyclerViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        if(mOnitemclicklistener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mOnitemclicklistener.onItemClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){


                @Override
                public boolean onLongClick(View view) {
                    mOnitemclicklistener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
        holder.mTextView.setText(mData.get(position));

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }
}
