package com.renxl.realmall.feature.category;

import android.content.Context;
import android.text.TextUtils;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/18 14:58.
 */

public class CategoryAdapter extends BaseAdapter<Category> {

    public CategoryAdapter(List<Category> datas, Context context) {
        super(datas, context, R.layout.item_category);
    }

    @Override
    public void covert(BaseViewHolder holder, Category item) {
        if (!TextUtils.isEmpty(item.getName()))
            holder.getTextView(R.id.tv_category_name).setText(item.getName());
    }
}
