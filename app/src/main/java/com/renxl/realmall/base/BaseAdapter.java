package com.renxl.realmall.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/7 8:37.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mDatas;
    protected Context mContext;
    private int layoutId;
    protected BaseViewHolder.OnItemClickListener onItemClickListener;

    public BaseAdapter(List<T> datas, Context context, int layoutId) {
        mDatas = datas;
        mContext = context;
        this.layoutId = layoutId;
    }

    public void setOnItemClickListener(BaseViewHolder.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void clear() {
        if (mDatas == null || mDatas.size() <= 0) return;
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setData(T t, int position) {
        if (t == null) return;
        mDatas.set(position, t);
        notifyItemChanged(position);
    }

    public void setData(List<T> datas) {
        setData(datas, 0);
    }

    public void setData(List<T> datas, int position) {
        if (datas == null || datas.size() <= 0) return;
        mDatas.addAll(datas);
        notifyItemRangeChanged(position, datas.size());

    }

    public void resetLayout(int layoutId) {
        this.layoutId = layoutId;
    }

    public abstract void covert(BaseViewHolder holder, T item);

    private T getItem(int position) {
        if (position >= mDatas.size()) return null;
        return mDatas.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new BaseViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        covert(holder, item);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() == 0) return 0;
        return mDatas.size();
    }

    public void setText(TextView tv, String str) {
        if (!TextUtils.isEmpty(str)) tv.setText(str);
    }
}
