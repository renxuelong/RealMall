package com.renxl.realmall.base;

/**
 * Created by renxl
 * On 2017/3/1 9:37.
 */

public interface BaseView<T extends Object> {
    void setData(T t);
    void fail();
}
