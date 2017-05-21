package com.renxl.realmall.feature.orders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.utils.Toast;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/21 14:23.
 */

public class FavoriteAdapter extends BaseAdapter<Favorite> {

    private DelFavoriteListener mDelFavoriteListener;

    public FavoriteAdapter(List<Favorite> datas, Context context, DelFavoriteListener listener) {
        super(datas, context, R.layout.item_hot_list);
        mDelFavoriteListener = listener;
    }

    @Override
    public void covert(BaseViewHolder holder, Favorite item) {
        if (!TextUtils.isEmpty(item.getWares().getImgUrl()))
            holder.getSimpleDraweeView(R.id.sdv_hot_list_icon).setImageURI(item.getWares().getImgUrl());

        if (!TextUtils.isEmpty(item.getWares().getName()))
            holder.getTextView(R.id.tv_hot_list_name).setText(item.getWares().getName());

        holder.getTextView(R.id.tv_hot_list_price).setText(String.format(mContext.getString(R.string.rmb),
                String.format(mContext.getString(R.string.pointtwo), item.getWares().getPrice())));

        initFavorite(holder, item);
    }

    private void initFavorite(BaseViewHolder holder, final Favorite item) {
        holder.getButton(R.id.btn_hot_list_add).setVisibility(View.GONE);
        TextView tvLook = holder.getTextView(R.id.tv_favorite_list_look);
        TextView tvDel = holder.getTextView(R.id.tv_favorite_list_del);
        tvLook.setVisibility(View.VISIBLE);
        tvDel.setVisibility(View.VISIBLE);

        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("找相似");
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFavorite(item);
            }
        });

    }

    private void delFavorite(Favorite item) {
        final RequestParams params = new RequestParams();
        params.put("id", item.getId());
        RealMallClient.getInstance().post(Constants.FAVORITE_DELETE, RealMallClient.getTokenParams(params), new TokenCallback<BaseBean>(mContext) {
            @Override
            public void ok(BaseBean response) {
                if (response.getStatus() == 1 && mDelFavoriteListener != null)
                    mDelFavoriteListener.complete();
                else
                    fail(response.getMessage());
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show("移除收藏失败 " + errorMessage);
            }
        });
    }

    public interface DelFavoriteListener {
        void complete();
    }
}
