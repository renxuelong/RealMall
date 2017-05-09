package com.renxl.realmall.feature.hot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.base.AddCartAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.Toast;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/7 10:24.
 */

public class HotAdapter extends AddCartAdapter<Wares> {
    public HotAdapter(List<Wares> datas, Context context, AddBtnClickListener listener) {
        super(datas, context, listener, R.layout.item_hot_list);
        addBtnClickListener = listener;
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    public void covert(BaseViewHolder holder, final Wares item) {
        if (!TextUtils.isEmpty(item.getImgUrl() ))
            holder.getSimpleDraweeView(R.id.sdv_hot_list_icon).setImageURI(item.getImgUrl());

        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_hot_list_name).setText(item.getName());

        holder.getTextView(R.id.tv_hot_list_price).setText(String.format(mContext.getString(R.string.rmb),
                String.format(mContext.getString(R.string.pointtwo), item.getPrice())));

        if (addBtnClickListener == null) return;
        if(holder.getButton(R.id.btn_hot_list_add) == null) return;
        holder.getButton(R.id.btn_hot_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("操作成功");
                addBtnClickListener.addClick(item);
            }
        });
    }
}
