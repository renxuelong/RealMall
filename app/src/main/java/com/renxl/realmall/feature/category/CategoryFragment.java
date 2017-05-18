package com.renxl.realmall.feature.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.home.Advertising;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.GridDecoration;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class CategoryFragment extends BaseFragment implements CategoryContract.ICategoryView {

    private static final int NORMAL_STATE = 0;
    private static final int REFRESH_STATE = 1;
    private static final int LOADMORE_STATE = 2;
    private int mState = NORMAL_STATE;

    @BindView(R.id.toolbar_category)
    ToolBar toolbarCategory;
    @BindView(R.id.recycleview_category)
    RecyclerView recycleviewCategory;
    @BindView(R.id.sl_category)
    SliderLayout slCategory;
    @BindView(R.id.recycleview_category_wares)
    RecyclerView recycleviewCategoryWares;
    Unbinder unbinder;
    @BindView(R.id.mrl_category_wares)
    MaterialRefreshLayout mrlCategoryWares;

    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdapter;
    private CategoryContract.ICategoryPresenter mCategoryPresenter;


    @Override
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, null);
    }

    protected void init() {
        mCategoryPresenter = new CategoryPresenter(this);
        mCategoryPresenter.start();
        mrlCategoryWares.setLoadMore(true);
        mrlCategoryWares.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mState = REFRESH_STATE;
                mCategoryPresenter.refreshRequestWares();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mState = LOADMORE_STATE;
                mCategoryPresenter.loadMoreRequestWares();
            }
        });
    }

    @Override
    public void showData(List<Category> categories) {
        if (categories == null || categories.size() <= 0) {
            Toast.show("服务器错误");
            return;
        }
        mCategoryPresenter.requestWares(categories.get(0).getSort());
        mCategoryAdapter = new CategoryAdapter(categories, getContext());
        mCategoryAdapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mState = REFRESH_STATE;
                mCategoryPresenter.requestWares(mCategoryAdapter.getDatas().get(position).getSort());
            }
        });
        recycleviewCategory.setAdapter(mCategoryAdapter);
        recycleviewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showBanner(List<Advertising> advertisings) {
        if (advertisings == null) return;

        for (final Advertising advertising : advertisings) {
            if (TextUtils.isEmpty(advertising.getImgUrl())) continue;
            final DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(advertising.getImgUrl()).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.show(advertising.getName());
                }
            });
            slCategory.addSlider(textSliderView);
        }

    }

    @Override
    public void showWares(final List<Wares> wares) {
        switch (mState) {
            case NORMAL_STATE:
                mWaresAdapter = new WaresAdapter(wares, getContext());
                mWaresAdapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                        intent.putExtra(WareDetailActivity.EXTRA_WARES, mWaresAdapter.getDatas().get(position));
                        startActivity(intent);
                    }
                });
                recycleviewCategoryWares.setAdapter(mWaresAdapter);
                recycleviewCategoryWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recycleviewCategoryWares.addItemDecoration(new GridDecoration());
                break;
            case REFRESH_STATE:
                mWaresAdapter.clear();
                if (wares == null || wares.size() <= 0) {
                    Toast.show("服务器异常");
                    return;
                }
                mWaresAdapter.setData(wares);
                mrlCategoryWares.finishRefresh();
                break;
            case LOADMORE_STATE:
                mWaresAdapter.setData(wares, mWaresAdapter.getDatas().size());
                mrlCategoryWares.finishRefreshLoadMore();
                break;
        }
    }

    @Override
    public void loadAll() {
        if (mrlCategoryWares != null) {
            mrlCategoryWares.finishRefreshLoadMore();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        slCategory.stopAutoCycle();
    }

    @Override
    public void fail() {
        Toast.show("服务器错误");
    }
}