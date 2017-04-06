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

    public HomePresenter(HomeContract.IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void start() {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("type", 1);
        HTTPCallback<List<Advertising>> callback = new HTTPCallback<List<Advertising>>() {
            @Override
            public void ok(List<Advertising> advertisings) {
                homeView.setData(advertisings);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
            }
        };
        RealMallClient.getInstance().post(Constants.HOME_BANNER, requestParams, callback, false);
    }


}
