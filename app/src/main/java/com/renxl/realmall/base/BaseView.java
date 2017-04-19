package com.renxl.realmall.base;

/**
 * Created by renxl
 * On 2017/3/1 9:37.
 */

public interface BaseView<T extends Object> {
    void showData(T t);
    void fail();
}
