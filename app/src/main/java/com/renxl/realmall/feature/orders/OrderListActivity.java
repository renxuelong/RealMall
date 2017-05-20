package com.renxl.realmall.feature.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseActivity;

/**
 * Created by renxl
 * On 2017/5/20 10:06.
 */

public class OrderListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
    }
}
