package com.renxl.realmall.feature.home;

import com.renxl.realmall.base.BasePresenter;
import com.renxl.realmall.base.BaseView;

import java.util.List;

/**
 * Created by renxl
 * On 2017/3/1 9:41.
 */

class HomeContract {

    interface IHomePresenter extends BasePresenter {

    }

    interface IHomeView<T extends Object> extends BaseView<T> {
        void setRecommend(List<Recommend> recommends);

        void showToast(String str);
    }

}
