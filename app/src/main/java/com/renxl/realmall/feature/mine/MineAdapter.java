package com.renxl.realmall.feature.mine;

import android.content.Context;

import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.category.Wares;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/5 10:25.
 */

public class MineAdapter extends BaseAdapter<Wares> {

    public MineAdapter(List<Wares> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public void covert(BaseViewHolder holder, Wares item) {

    }
}
