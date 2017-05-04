package com.renxl.realmall.feature.cart;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.widget.AddSubView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by renxl
 * On 2017/5/3 11:22.
 */

class CartAdapter extends BaseAdapter<CartBean> {

    // 全选、全不选、金额计算显示

    private TextView tvTotal;
    private CheckBox checkAll;
    private Listener mListener;

    CartAdapter(List<CartBean> datas, Context context, TextView tv, CheckBox cb, Listener listener) {
        super(datas, context, R.layout.item_cart);
        mListener = listener;
        tvTotal = tv;
        checkAll = cb;

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refCheckAllItem(checkAll.isChecked());
            }
        });
    }

    void refCheckAllItem(boolean isCheck) {
        List<CartBean> lists = getDatas();
        for (int i = 0; i < lists.size(); i++) {
            CartBean cartBean = lists.get(i);
            cartBean.setChecked(isCheck);
            notifyItemChanged(i);
            if (mListener != null) {
                mListener.itemClick(cartBean);
            }
        }
        showTotalPrice();
    }

    void delete() {
        List<CartBean> lists = getDatas();
        for (Iterator<CartBean> iterator = lists.iterator(); iterator.hasNext(); ) {
            CartBean cartBean = iterator.next();
            if (cartBean.isChecked()) {
                int index = lists.indexOf(cartBean);
                iterator.remove();
                notifyItemRemoved(index);
                if (mListener != null) {
                    mListener.delete(cartBean);
                }
            }
        }
    }

    @Override
    public void covert(final BaseViewHolder holder, final CartBean item) {
        if (!TextUtils.isEmpty(item.getImgUrl()))
            holder.getSimpleDraweeView(R.id.sdv_cart_item).setImageURI(item.getImgUrl());
        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_cart_title).setText(item.getName());

        String price = item.getPrice() + "";
        holder.getTextView(R.id.tv_cart_price).setText(price);
        holder.getCheckBox(R.id.checkbox_cart_item).setChecked(item.isChecked());

        AddSubView addSubView = holder.getAddSubView(R.id.add_sub_cart_item);
        addSubView.setValue(item.getmCount());
        if (mListener != null) {
            addSubView.setOnAddSubClickListener(new AddSubView.OnAddSubClickListener() {
                @Override
                public void onAddClick(View view, int value) {
                    item.setmCount(value);
                    mListener.addClick(item);
                    showTotalPrice();
                }

                @Override
                public void onSubClick(View view, int value) {
                    item.setmCount(value);
                    mListener.subClick(item);
                    showTotalPrice();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getDatas().indexOf(item);
                    item.setChecked(!item.isChecked());
                    holder.getCheckBox(R.id.checkbox_cart_item).setChecked(item.isChecked());
                    mListener.itemClick(item);
                    showTotalPrice();
                    refCheckAll();
                }
            });
        }
    }

    private void refCheckAll() {
        if (isCheckAll()) {
            checkAll.setChecked(true);
        } else {
            checkAll.setChecked(false);
        }
    }

    boolean isCheckAll() {
        int size = 0;
        List<CartBean> lists = getDatas();
        for (CartBean list : lists) {
            if (!list.isChecked()) {
                return false;
            } else {
                size++;
            }
        }
        return size == lists.size();
    }

    void showTotalPrice() {
        String text = mContext.getString(R.string.total) + " " + totalPrice();
        tvTotal.setText(text);
    }

    private float totalPrice() {
        float price = 0;
        List<CartBean> datas = getDatas();
        for (CartBean data : datas) {
            if (data.isChecked()) {
                price += data.getmCount() * data.getPrice();
            }
        }
        return price;
    }

    interface Listener {
        void addClick(CartBean item);

        void subClick(CartBean item);

        void itemClick(CartBean item);

        void delete(CartBean item);
    }

}
