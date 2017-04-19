package com.renxl.realmall.feature.category;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/18 15:49.
 */

public class WaresAdapter extends BaseAdapter<Wares> {
    public WaresAdapter(List datas, Context context) {
        super(datas, context, R.layout.item_category_wares);
    }

    @Override
    public void covert(BaseViewHolder holder, Wares wares) {
        if (!TextUtils.isEmpty(wares.getImgUrl()))
            holder.getSimpleDraweeView(R.id.sdv_category_wares).setImageURI(wares.getImgUrl());

        SimpleDraweeView simpleDraweeView = holder.getSimpleDraweeView(R.id.sdv_category_wares);
//        simpleDraweeView.getLayoutParams().width =

        if (!TextUtils.isEmpty(wares.getName()))
            holder.getTextView(R.id.tv_category_wares_title).setText(wares.getName());

        holder.getTextView(R.id.tv_category_wares_price).setText(String.format(mContext.getResources().getString(R.string.rmb),
                String.format(mContext.getResources().getString(R.string.pointtwo), wares.getPrice())));
    }
}
