package com.renxl.realmall.feature.hot;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

/**
 * Created by renxl
 * On 2017/4/5 16:12.
 */

public class HotPresenter implements HotConstract.IHotPresenter {

    private HotConstract.IHotView mHotView;

    HotPresenter(HotConstract.IHotView hotView) {
        mHotView = hotView;
    }

    @Override
    public void start() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("curPage", "1");
        requestParams.put("pageSize", "20");
        RealMallClient.getInstance().get(Constants.HOT_WEARS, requestParams, new HTTPCallback<Wears>() {
            @Override
            public void ok(Wears response) {
                super.ok(response);
                mHotView.setData(response);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
            }
        });
    }

}
