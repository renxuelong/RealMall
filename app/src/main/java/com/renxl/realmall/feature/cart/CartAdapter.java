package com.renxl.realmall.feature.cart;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.widget.AddSubView;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/3 11:22.
 */

class CartAdapter extends BaseAdapter<CartBean> {
    private OnAddSubClickListener onAddSubClickListener;

    CartAdapter(List<CartBean> datas, Context context, OnAddSubClickListener listener) {
        super(datas, context, R.layout.item_cart);
        onAddSubClickListener = listener;
    }

    @Override
    public void covert(BaseViewHolder holder, final CartBean item) {
        if (!TextUtils.isEmpty(item.getImgUrl()))
            holder.getSimpleDraweeView(R.id.sdv_cart_item).setImageURI(item.getImgUrl());
        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_cart_title).setText(item.getName());

        String price = item.getPrice() + "";
        holder.getTextView(R.id.tv_cart_price).setText(price);
        holder.getCheckBox(R.id.checkbox_cart_item).setChecked(item.isChecked());

        AddSubView addSubView = holder.getAddSubView(R.id.add_sub_cart_item);
        addSubView.setValue(item.getmCount());
        if (onAddSubClickListener != null) {
            addSubView.setOnAddSubClickListener(new AddSubView.OnAddSubClickListener() {
                @Override
                public void onAddClick(View view, int value) {
                    item.setmCount(value);
                    onAddSubClickListener.addClick(item);
                }

                @Override
                public void onSubClick(View view, int value) {
                    item.setmCount(value);
                    onAddSubClickListener.subClick(item);
                }
            });
        }
    }

    interface OnAddSubClickListener {
        void addClick(CartBean item);

        void subClick(CartBean item);
    }

}
