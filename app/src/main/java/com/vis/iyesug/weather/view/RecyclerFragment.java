package com.vis.iyesug.weather.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vis.iyesug.weather.R;
import com.vis.iyesug.weather.presenter.RecyclerViewAdapter;
import com.vis.iyesug.weather.presenter.StaggeredViewAdapter;
import com.vis.iyesug.weather.util.Util;


public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewAdapter.OnItemClickListener,
        StaggeredViewAdapter.OnItemClickListener{
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshl;
    private RecyclerView mRecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mRecyclerviewadapter;
    private StaggeredViewAdapter mStaggeredadapter;
    private int flag=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                 Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshl = (SwipeRefreshLayout) mView.findViewById(R.id.id_swiperefreshlayout);
        mRecyclerview = (RecyclerView) mView.findViewById(R.id.id_recyclerview);

        flag = (int) getArguments().get("flag");
        configRecyclerView();

        // 指示器旋转颜色
        mSwipeRefreshl.setColorSchemeResources(R.color.main, R.color.main_dark);
        mSwipeRefreshl.setOnRefreshListener(this);
    }

    private void configRecyclerView() {

        switch (flag) {
            case Util.VERTICAL_LIST:
                mLayoutManager =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case Util.HORIZONTAL_LIST:
                mLayoutManager =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case Util.VERTICAL_GRID:
                mLayoutManager =
                        new GridLayoutManager(getActivity(), Util.SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case Util.HORIZONTAL_GRID:
                mLayoutManager =
                        new GridLayoutManager(getActivity(), Util.SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case Util.STAGGERED_GRID:
                mLayoutManager =
                        new StaggeredGridLayoutManager(Util.SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        if (flag != Util.STAGGERED_GRID) {
            mRecyclerviewadapter = new RecyclerViewAdapter(getActivity());
            mRecyclerviewadapter.setOnItemClickListener(this);
            mRecyclerview.setAdapter(mRecyclerviewadapter);
        } else {
            mStaggeredadapter = new StaggeredViewAdapter(getActivity());
            mStaggeredadapter.setOnItemClickListener(this);
            mRecyclerview.setAdapter(mStaggeredadapter);
        }

        mRecyclerview.setLayoutManager(mLayoutManager);

    }


    //刷新数据
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeRefreshl.setRefreshing(false);
                int temp = (int) (Math.random() * 10);
                if (flag != Util.STAGGERED_GRID) {
                    mRecyclerviewadapter.mData.add(0, "new" + temp);
                    mRecyclerviewadapter.notifyDataSetChanged();
                } else {

                    mStaggeredadapter.mData.add(0, "new" + temp);
                    mStaggeredadapter.mHeights.add(0, (int) (Math.random() * 300) + 200);
                    mStaggeredadapter.notifyDataSetChanged();
                }
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
