package com.renxl.realmall.feature.cart;

import com.renxl.realmall.base.BasePresenter;
import com.renxl.realmall.base.BaseView;

/**
 * Created by renxl
 * On 2017/5/3 10:12.
 */

interface CartContract {

    interface ICartView<T> extends BaseView<T> {
        void refData(T t);
    }

    interface ICartPresenter<T> extends BasePresenter {
        void delete(T t);

        void updateData(T t);

        void refData();
    }

}
