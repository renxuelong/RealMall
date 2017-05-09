package com.renxl.realmall.feature.wares_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.AddCartAdapter;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.cart.CartBean;
import com.renxl.realmall.feature.cart.CartProvider;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.feature.hot.HotAdapter;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
import com.renxl.realmall.utils.Pager;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaresActivity extends BaseActivity implements Pager.OnPageListener<Wares> {
    public static final String CAMPAIGN_ID = "campaignId";
    /**
     * 排序标识
     * 默认：0  销量：1  价格：2
     */
    private static final String ORDER_BY = "orderBy";
    private static final String ORDER_DEFAULT = "0";
    private static final String ORDER_SALES = "1";
    private static final String ORDER_PRICE = "2";

    private static final int STATE_LIST = 1;
    private static final int STATE_GRID = 2;
    private static int state = STATE_LIST;

    private CartProvider cartProvider;

    @BindView(R.id.tab_layout_wares)
    TabLayout tabLayoutWares;
    @BindView(R.id.tv_list_size)
    TextView tvListSize;
    @BindView(R.id.refresh_wares)
    MaterialRefreshLayout refreshWares;
    @BindView(R.id.recyclerview_wares)
    RecyclerView recyclerViewWares;
    @BindView(R.id.toolbar_wares_list)
    ToolBar toolbarWaresList;

    private HotAdapter adapter;
    private int campaignId;
    private Pager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares);
        ButterKnife.bind(this);

        cartProvider = CartProvider.getInstance();
        campaignId = getIntent().getIntExtra(CAMPAIGN_ID, 1);

        initToolBar();
        initTabLayout();
        initPage();
    }

    private void initToolBar() {
        toolbarWaresList.setNavigationIcon(R.drawable.icon_back);
        toolbarWaresList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarWaresList.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                if (adapter == null) return;
                switch (state) {
                    case STATE_LIST:
                        state = STATE_GRID;
                        toolbarWaresList.setRightBtnImg(getResources().getDrawable(R.drawable.icon_list_32));
                        adapter.resetLayout(R.layout.item_wares_grid);
                        recyclerViewWares.setLayoutManager(new GridLayoutManager(WaresActivity.this, 2));
                        recyclerViewWares.setAdapter(adapter);
                        break;
                    case STATE_GRID:
                        state = STATE_LIST;
                        toolbarWaresList.setRightBtnImg(getResources().getDrawable(R.drawable.icon_grid_32));
                        adapter.resetLayout(R.layout.item_hot_list);
                        recyclerViewWares.setLayoutManager(new LinearLayoutManager(WaresActivity.this));
                        recyclerViewWares.setAdapter(adapter);
                        break;
                }
            }
        });
    }

    private void initPage() {
        pager = new Pager.Builder(new TypeToken<BaseBean<Wares>>() {
        }.getType())
                .setRefreshLayout(refreshWares)
                .setCanLoadMore(true)
                .setRecyclerView(recyclerViewWares)
                .setOnPageListener(this)
                .setUrl(Constants.WARES_LIST)
                .putParams(CAMPAIGN_ID, campaignId)
                .putParams(ORDER_BY, ORDER_DEFAULT)
                .build();
        pager.start();
    }

    private void initTabLayout() {
        tabLayoutWares.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayoutWares.setSelectedTabIndicatorHeight(3);
        tabLayoutWares.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.colorPrimaryDark));

        TabLayout.Tab tab1 = tabLayoutWares.newTab();
        tab1.setTag(ORDER_DEFAULT);
        tab1.setText("默认");
        TabLayout.Tab tab2 = tabLayoutWares.newTab();
        tab2.setTag(ORDER_PRICE);
        tab2.setText("价格");
        TabLayout.Tab tab3 = tabLayoutWares.newTab();
        tab3.setTag(ORDER_SALES);
        tab3.setText("销量");

        tabLayoutWares.addTab(tab1);
        tabLayoutWares.addTab(tab2);
        tabLayoutWares.addTab(tab3);

        tabLayoutWares.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String order = (String) tab.getTag();
                if (TextUtils.isEmpty(order)) return;
                switch (order) {
                    case ORDER_DEFAULT:
                        pager.putParam(ORDER_BY, ORDER_DEFAULT);
                        break;
                    case ORDER_SALES:
                        pager.putParam(ORDER_BY, ORDER_SALES);
                        break;
                    case ORDER_PRICE:
                        pager.putParam(ORDER_BY, ORDER_PRICE);
                        break;
                }
                pager.putParam(Pager.PARAM_CUR_PAGE, 1);
                pager.setStatus(Pager.STATE_REFRESH);
                pager.requestData();
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
    public void load(List<Wares> list, int totalPage, int totalCount) {
        tvListSize.setText("共有 " + totalCount + "件商品");
        if (list == null || totalCount <= 0) return;

        adapter = new HotAdapter(list, this, new AddCartAdapter.AddBtnClickListener<Wares>() {
            @Override
            public void addClick(Wares item) {
                cartProvider.putCartBean(item);
            }
        });
        adapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(WaresActivity.this, WareDetailActivity.class);
                intent.putExtra(WareDetailActivity.EXTRA_WARES, adapter.getDatas().get(position));
                startActivity(intent);
            }
        });

        recyclerViewWares.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWares.setAdapter(adapter);
    }

    @Override
    public void refresh(List<Wares> list, int totalPage, int totalCount) {
        if (list == null || totalCount <= 0) return;

        tvListSize.setText("共有 " + totalCount + "件商品");
        adapter.clear();
        adapter.setData(list);
        refreshWares.finishRefresh();
    }

    @Override
    public void loadMore(List<Wares> list, int totalPage, int totalCount) {
        adapter.setData(list);
        refreshWares.finishRefreshLoadMore();
        tvListSize.setText("共有 " + totalCount + "件商品");
    }
}
