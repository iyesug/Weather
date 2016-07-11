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
public class StaggeredViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    public Context mContext;
    public List<String> mData;
    public List<Integer> mHeights;
    public LayoutInflater mLayoutinflater;


    public StaggeredViewAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutinflater = LayoutInflater.from(mContext);
        mData = new ArrayList<>();
        mHeights = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mData.add((char) i + "");
        }
        for (int i = 0; i < mData.size(); i++) {
            mHeights.add((int) (Math.random() * 3000) + 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=mLayoutinflater.inflate(R.layout.item,parent,false);
        RecyclerViewHolder holder=new RecyclerViewHolder(mView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder,final int position) {
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }

        ViewGroup.LayoutParams mLayoutParams = holder.mTextView.getLayoutParams();
        mLayoutParams.height = mHeights.get(position);
        holder.mTextView.setLayoutParams(mLayoutParams);
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
