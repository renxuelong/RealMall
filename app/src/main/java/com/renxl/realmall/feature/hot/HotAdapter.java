package com.renxl.realmall.feature.hot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.utils.Toast;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/7 10:24.
 */

class HotAdapter extends BaseAdapter<Wares> {


    private AddBtnClickListener addBtnClickListener;

    HotAdapter(List<Wares> datas, Context context, AddBtnClickListener listener) {
        super(datas, context, R.layout.item_hot_list);
        addBtnClickListener = listener;
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    public void covert(BaseViewHolder holder, final Wares item) {
        if (!TextUtils.isEmpty(item.getImgUrl() ))
            holder.getSimpleDraweeView(R.id.img_hot_list_icon).setImageURI(item.getImgUrl());

        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_hot_list_name).setText(item.getName());

        holder.getTextView(R.id.tv_hot_list_price).setText(String.format(mContext.getString(R.string.rmb),
                String.format(mContext.getString(R.string.pointtwo), item.getPrice())));

        if (addBtnClickListener == null) return;
        holder.getButton(R.id.btn_hot_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("操作成功");
                addBtnClickListener.addClick(item);
            }
        });
    }

    interface AddBtnClickListener {
        void addClick(Wares item);
    }
}
