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

        void refData(T t, int position);
    }

    interface ICartPresenter<T> extends BasePresenter {
        void updateData(int position);

        void updateData(T t);

        void refData();
    }

}
