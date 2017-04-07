package com.renxl.realmall.feature.hot;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

/**
 * Created by renxl
 * On 2017/4/5 16:12.
 */

class HotPresenter implements HotConstract.IHotPresenter {

    private HotConstract.IHotView mHotView;
    private int currentPage = 1;
    private int totalPage;

    HotPresenter(HotConstract.IHotView hotView) {
        mHotView = hotView;
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
        RealMallClient.getInstance().get(Constants.HOT_WEARS, requestParams, new HTTPCallback<Wears>() {
            @Override
            public void ok(Wears response) {
                super.ok(response);
                totalPage = response.getTotalPage();
                mHotView.setData(response);
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
}
