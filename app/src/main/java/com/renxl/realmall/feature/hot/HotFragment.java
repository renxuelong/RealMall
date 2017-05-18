package com.renxl.realmall.feature.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class HotFragment extends BaseFragment implements HotConstract.IHotView<List<Wares>>, BaseViewHolder.OnItemClickListener {

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_LOADMORE = 2;

    Unbinder unbinder;
    @BindView(R.id.toolbar_hot)
    ToolBar toolbarHot;
    @BindView(R.id.recycleview_hot)
    RecyclerView recycleviewHot;
    @BindView(R.id.srl_hot)
    MaterialRefreshLayout srlHot;

    private int state = STATE_NORMAL;
    private HotAdapter adapter;
    private HotConstract.IHotPresenter<Wares> mHotPresenter;

    @Override
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot, null, false);
    }

    @Override
    protected void init() {
        mHotPresenter = new HotPresenter(this, getContext());
        mHotPresenter.start();
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        srlHot.setLoadMore(true);
        srlHot.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                doRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                doLoadMore();
            }
        });
    }

    private void doLoadMore() {
        state = STATE_LOADMORE;
        mHotPresenter.loadMore();
    }

    private void doRefresh() {
        state = STATE_REFRESH;
        mHotPresenter.refresh();
    }

    @Override
    public void showData(List<Wares> wares) {
        if (wares == null) return;

        switch (state) {
            case STATE_NORMAL:
                adapter = new HotAdapter(wares, getContext(), new HotAdapter.AddBtnClickListener<Wares>() {
                    @Override
                    public void addClick(Wares item) {
                        mHotPresenter.addCart(item);
                    }
                });
                adapter.setOnItemClickListener(this);
                recycleviewHot.setAdapter(adapter);
                recycleviewHot.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycleviewHot.setItemAnimator(new DefaultItemAnimator());
                recycleviewHot.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                break;
            case STATE_REFRESH:
                adapter.clear();
                adapter.setData(wares);
                srlHot.finishRefresh();
                break;
            case STATE_LOADMORE:
                adapter.setData(wares, adapter.getDatas().size());
                srlHot.finishRefreshLoadMore();
                break;
        }
    }

    @Override
    public void fail() {
        switch (state) {
            case STATE_NORMAL:
                Toast.show("数据加载失败");
                break;
            case STATE_REFRESH:
                srlHot.finishRefresh();
                Toast.show("刷新失败");
                break;
            case STATE_LOADMORE:
                srlHot.finishRefreshLoadMore();
                Toast.show("数据加载失败");
                state = STATE_NORMAL;
                break;
        }
    }

    @Override
    public void loadAll() {
        srlHot.finishRefreshLoadMore();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), WareDetailActivity.class);
        intent.putExtra(WareDetailActivity.EXTRA_WARES, adapter.getDatas().get(position));
        startActivity(intent);
    }
}
