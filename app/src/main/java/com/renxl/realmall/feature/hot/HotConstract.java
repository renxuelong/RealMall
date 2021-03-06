package com.renxl.realmall.feature.hot;

import com.renxl.realmall.base.BasePresenter;
import com.renxl.realmall.base.BaseView;

/**
 * Created by renxl
 * On 2017/4/5 15:57.
 */

class HotConstract {
    interface IHotPresenter<T> extends BasePresenter {
        void refresh();

        void loadMore();

        void addCart(T t);
    }

    interface IHotView<T> extends BaseView<T> {
        void loadAll();
    }
}
