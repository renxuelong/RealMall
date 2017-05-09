package com.renxl.realmall.feature.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/13 10:29.
 */

class HomeAdapter extends BaseAdapter<Recommend> {

    private static final int ITEM_R = 0;
    private static final int ITEM_L = 1;
    private ImageClickListener mListener;

    HomeAdapter(List<Recommend> datas, Context context) {
        super(datas, context, R.layout.item_home_recommend_left);
    }

    public void setImageClickListener(ImageClickListener listener) {
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_home_recommend_left;
        if (viewType == ITEM_R) {
            layoutId = R.layout.item_home_recommend_right;
        }
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        view.getLayoutParams().height = mContext.getResources().getDisplayMetrics().widthPixels / 3 * 2;
        return new BaseViewHolder(view, onItemClickListener);
    }

    @Override
    public void covert(final BaseViewHolder holder, final Recommend item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            holder.getTextView(R.id.tv_home_recommend_title).setText(item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getCpOne().getImgUrl())) {
            holder.getSimpleDraweeView(R.id.sdv_recommend_big).setImageURI(item.getCpOne().getImgUrl());
            holder.getSimpleDraweeView(R.id.sdv_recommend_big).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onImageClick(holder.getSimpleDraweeView(R.id.sdv_recommend_big), item.getCpOne().getId());
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(item.getCpTwo().getImgUrl())) {
            holder.getSimpleDraweeView(R.id.sdv_recommend_up).setImageURI(item.getCpTwo().getImgUrl());
            holder.getSimpleDraweeView(R.id.sdv_recommend_up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onImageClick(holder.getSimpleDraweeView(R.id.sdv_recommend_up), item.getCpTwo().getId());
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(item.getCpThree().getImgUrl())) {
            holder.getSimpleDraweeView(R.id.sdv_recommend_down).setImageURI(item.getCpThree().getImgUrl());
            holder.getSimpleDraweeView(R.id.sdv_recommend_down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onImageClick(holder.getSimpleDraweeView(R.id.sdv_recommend_down), item.getCpThree().getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ITEM_L;
        }
        return ITEM_R;
    }

    interface ImageClickListener {
        void onImageClick(View view, int campaignId);
    }
}
