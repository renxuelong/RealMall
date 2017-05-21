package com.renxl.realmall.feature.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.http.BaseClient;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by renxl
 * On 2017/5/20 10:06.
 */

public class OrderListActivity extends BaseActivity {

    private static final String TAG_TOTAL = "total";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_WAIT = "wait";
    private static final String TAG_FAIL = "fail";

    @BindView(R.id.toolbar_order_list)
    ToolBar mToolbarOrderList;
    @BindView(R.id.table_order_list)
    TabLayout mTableOrderList;
    @BindView(R.id.recycler_order_list)
    RecyclerView mRecyclerOrderList;

    private OrderAdapter mAdapter;
    private int status = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);

        initToolbar();
        initTabLayout();
        initOrderList();
    }

    private void initToolbar() {
        mToolbarOrderList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initOrderList() {

        User user = UserLocalData.getUser(this);
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(LoginActivity.EXTRA_RESULT, true);
            startActivityForResult(intent, REQUEST_CODE);
            return;
        }

        RequestParams params = new RequestParams();
        params.put("status", status);
        params.put("user_id", user.getId());
        RealMallClient.getInstance().get(Constants.ORDER_LIST, BaseClient.getTokenParams(params), new TokenCallback<List<CompleteOrder>>(this) {
            @Override
            public void ok(List<CompleteOrder> response) {
                showOrders(response);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
            }
        });
    }

    private void showOrders(List<CompleteOrder> response) {
        if (mAdapter == null) {
            if (response == null) return;
            Collections.reverse(response);
            mAdapter = new OrderAdapter(response, this);
            mRecyclerOrderList.setAdapter(mAdapter);
            mRecyclerOrderList.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter.clear();
            if (response == null) return;
            Collections.reverse(response);
            mAdapter.setData(response);
        }
    }

    private void initTabLayout() {
        mTableOrderList.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        mTableOrderList.setSelectedTabIndicatorHeight(3);
        mTableOrderList.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.colorPrimaryDark));

        TabLayout.Tab tab1 = mTableOrderList.newTab();
        tab1.setTag(TAG_TOTAL);
        tab1.setText("全部");
        TabLayout.Tab tab2 = mTableOrderList.newTab();
        tab2.setTag(TAG_SUCCESS);
        tab2.setText("支付成功");
        TabLayout.Tab tab3 = mTableOrderList.newTab();
        tab3.setTag(TAG_WAIT);
        tab3.setText("待支付");
        TabLayout.Tab tab4 = mTableOrderList.newTab();
        tab4.setTag(TAG_FAIL);
        tab4.setText("支付失败");

        mTableOrderList.addTab(tab1);
        mTableOrderList.addTab(tab2);
        mTableOrderList.addTab(tab3);
        mTableOrderList.addTab(tab4);

        mTableOrderList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String order = (String) tab.getTag();
                if (TextUtils.isEmpty(order)) return;
                switch (order) {
                    case TAG_TOTAL:
                        status = 2;
                        break;
                    case TAG_SUCCESS:
                        status = 1;
                        break;
                    case TAG_WAIT:
                        status = 0;
                        break;
                    case TAG_FAIL:
                        status = -2;
                        break;
                }
                initOrderList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initOrderList();
    }
}
