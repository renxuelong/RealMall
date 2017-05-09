package com.renxl.realmall.base;

import android.content.Context;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/8 18:37.
 */

public abstract class AddCartAdapter<T> extends BaseAdapter<T> {
    protected AddBtnClickListener addBtnClickListener;

    public AddCartAdapter(List datas, Context context, AddBtnClickListener listener, int layoutId) {
        super(datas, context, layoutId);
    }

    public interface AddBtnClickListener<T> {
        void addClick(T item);
    }
}
