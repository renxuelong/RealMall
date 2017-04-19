package com.renxl.realmall.feature.category;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.feature.home.Advertising;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/18 14:00.
 */

class CategoryPresenter implements CategoryContract.ICategoryPresenter {
    private int mCurPage = 1;
    private int mCategoryId;
    private int totalPage;

    private CategoryContract.ICategoryView mCategoryView;

    CategoryPresenter(CategoryContract.ICategoryView iCategoryView) {
        mCategoryView = iCategoryView;
    }

    @Override
    public void start() {
        requestCategory();
        requestBanner();
    }

    private void requestCategory() {
        RealMallClient.getInstance().get(Constants.CATEGORY, new HTTPCallback<List<Category>>() {
            @Override
            public void ok(List<Category> response) {
                mCategoryView.showData(response);
            }

            @Override
            public void fail(String errorMessage) {
                mCategoryView.fail();
            }
        });
    }

    private void requestBanner() {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("type", 1);
        HTTPCallback<List<Advertising>> callback = new HTTPCallback<List<Advertising>>() {
            @Override
            public void ok(List<Advertising> advertisings) {
                mCategoryView.showBanner(advertisings);
            }

            @Override
            public void fail(String errorMessage) {
                mCategoryView.fail();
            }
        };
        RealMallClient.getInstance().get(Constants.HOME_BANNER, requestParams, callback);
    }

    private void requestWares() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("categoryId", 1);

        RealMallClient.getInstance().get(Constants.CATEGORY, new HTTPCallback<BaseBean<Wares>>() {
            @Override
            public void ok(BaseBean<Wares> response) {
                if (response == null || response.getList() == null || response.getList().size() <= 0) {
                    mCategoryView.fail();
                    return;
                }
                mCategoryView.showWares(response.getList());
            }

            @Override
            public void fail(String errorMessage) {
                mCategoryView.fail();
            }
        });
    }

    @Override
    public void requestWares(int categoryId) {
        if (categoryId == mCategoryId) return;
        mCurPage = 1;
        mCategoryId = categoryId;
        doRequestWares();
    }

    @Override
    public void refreshRequestWares() {
        mCurPage = 1;
        doRequestWares();
    }

    @Override
    public void loadMoreRequestWares() {
        if (mCurPage > totalPage) {
            mCategoryView.loadAll();
            return;
        }
        mCurPage += 1;
        doRequestWares();
    }

    private void doRequestWares() {
        int PAGE_SIZE = 20;
        RequestParams requestParams = new RequestParams();
        requestParams.put("categoryId", mCategoryId);
        requestParams.put("curPage", mCurPage);
        requestParams.put("pageSize", PAGE_SIZE);
        RealMallClient.getInstance().get(Constants.CATEGORY_WARES, requestParams, new HTTPCallback<BaseBean<Wares>>() {
            @Override
            public void ok(BaseBean<Wares> response) {
                totalPage = response.getTotalPage();
                mCategoryView.showWares(response.getList());
            }

            @Override
            public void fail(String errorMessage) {
                mCategoryView.fail();
            }
        });
    }
}
