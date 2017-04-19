package com.renxl.realmall.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by renxl
 * On 2017/4/18 17:18.
 */

public class GridDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 3;
        outRect.top = 3;
        outRect.right = 3;
        outRect.bottom = 3;
    }
}
