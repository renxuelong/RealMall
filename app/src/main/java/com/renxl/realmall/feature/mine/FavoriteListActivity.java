package com.renxl.realmall.feature.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.feature.orders.Favorite;
import com.renxl.realmall.feature.orders.FavoriteAdapter;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.http.BaseClient;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by renxl
 * On 2017/5/20 10:06.
 */

public class FavoriteListActivity extends BaseActivity implements FavoriteAdapter.DelFavoriteListener {
    @BindView(R.id.toolbar_favorite_list)
    ToolBar mToolbarFavoriteList;
    @BindView(R.id.recycler_favorite_list)
    RecyclerView mRecyclerFavoriteList;

    private FavoriteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        ButterKnife.bind(this);

        initToolbar();
        initList();
    }

    private void initToolbar() {
        mToolbarFavoriteList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initList() {
        User user = UserLocalData.getUser(this);
        if (user == null) {
            finish();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("user_id", user.getId());
        RealMallClient.getInstance().get(Constants.FAVORITE_LIST, BaseClient.getTokenParams(params), new TokenCallback<List<Favorite>>(this) {
            @Override
            public void ok(List<Favorite> response) {
                if (response != null) showList(response);
            }
        });
    }

    private void showList(List<Favorite> response) {
        if (mAdapter == null) {
            mAdapter = new FavoriteAdapter(response, this, this);
            mRecyclerFavoriteList.setAdapter(mAdapter);
            mRecyclerFavoriteList.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter.clear();
            mAdapter.setData(response);
        }
    }

    @Override
    public void complete() {
        initList();
    }
}
