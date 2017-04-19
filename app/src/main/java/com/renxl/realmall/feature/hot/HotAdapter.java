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

    HotAdapter(List<Wares> datas, Context context) {
        super(datas, context, R.layout.item_hot_list);
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    public void covert(BaseViewHolder holder, Wares item) {
        if (!TextUtils.isEmpty(item.getImgUrl() ))
            holder.getSimpleDraweeView(R.id.img_hot_list_icon).setImageURI(item.getImgUrl());

        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_hot_list_name).setText(item.getName());

        holder.getTextView(R.id.tv_hot_list_price).setText(String.format(mContext.getResources().getString(R.string.rmb),
                String.format(mContext.getResources().getString(R.string.pointtwo), item.getPrice())));

        holder.getButton(R.id.btn_hot_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("Add");
            }
        });
    }
}
