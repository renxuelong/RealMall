package com.renxl.realmall.feature.hot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/5 16:46.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {

    private List<Wears.ListBean> beanList;
    private Context mContext;

    public HotAdapter(List<Wears.ListBean> lists, Context context) {
        beanList = lists;
        mContext = context;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {



        public HotViewHolder(View itemView) {
            super(itemView);
        }



    }
}
