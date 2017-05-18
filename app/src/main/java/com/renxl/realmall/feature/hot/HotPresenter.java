package com.renxl.realmall.feature.hot;

import android.content.Context;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BasePageBean;
import com.renxl.realmall.feature.cart.CartProvider;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

/**
 * Created by renxl
 * On 2017/4/5 16:12.
 */

class HotPresenter implements HotConstract.IHotPresenter<Wares> {

    private HotConstract.IHotView mHotView;
    private int currentPage = 1;
    private int totalPage;
    private CartProvider mCartProvider;

    HotPresenter(HotConstract.IHotView hotView, Context context) {
        mHotView = hotView;
        mCartProvider = CartProvider.getInstance();
    }

    @Override
    public void start() {
        getData();
    }

    private void getData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("curPage", currentPage);
        int pageSize = 20;
        requestParams.put("pageSize", pageSize);
        RealMallClient.getInstance().get(Constants.HOT_WEARS, requestParams, new HTTPCallback<BasePageBean<Wares>>() {
            @Override
            public void ok(BasePageBean<Wares> response) {
                if (response == null) mHotView.fail();
                totalPage = response.getTotalPage();
                mHotView.showData(response.getList());
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                mHotView.fail();
            }
        });
    }

    @Override
    public void refresh() {
        currentPage = 1;
        getData();
    }

    @Override
    public void loadMore() {
        if (currentPage > totalPage) {
            mHotView.loadAll();
            return;
        }
        currentPage += 1;
        getData();
    }

    @Override
    public void addCart(Wares wares) {
        mCartProvider.putCartBean(wares);
    }
}
