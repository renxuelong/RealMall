package com.renxl.realmall.feature.home;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

import java.util.List;

/**
 * Created by renxl
 * On 2017/3/31 14:17.
 */

class HomePresenter implements HomeContract.IHomePresenter {

    private HomeContract.IHomeView homeView;

    HomePresenter(HomeContract.IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void start() {
        getHomeBanner();
        getHomeRecommend();
    }

    private void getHomeRecommend() {
        RealMallClient.getInstance().get(Constants.HOME_RECOMMEND, new HTTPCallback<List<Recommend>>() {
            @Override
            public void ok(List<Recommend> response) {
                homeView.showRecommend(response);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                homeView.showToast("Recommend 请求失败,原因 " + errorMessage);
            }
        });
    }

    private void getHomeBanner() {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("type", 1);
        HTTPCallback<List<Advertising>> callback = new HTTPCallback<List<Advertising>>() {
            @Override
            public void ok(List<Advertising> advertisings) {
                homeView.showData(advertisings);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                homeView.showToast("Banner 请求失败,原因 " + errorMessage);
            }
        };
        RealMallClient.getInstance().post(Constants.HOME_BANNER, requestParams, callback, false);
    }
}
