package com.renxl.realmall.feature.orders;

import com.renxl.realmall.base.BaseBean;

/**
 * Created by renxl
 * On 2017/5/20 16:18.
 */

public class Order<T> extends BaseBean {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
