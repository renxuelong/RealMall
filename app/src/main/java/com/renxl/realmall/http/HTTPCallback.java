package com.renxl.realmall.http;

import com.renxl.realmall.BuildConfig;

/**
 * Created by renxl
 * On 2017/3/30 20:15.
 */

public abstract class HTTPCallback<T> extends BaseCallback<T> {

    @Override
    public void setMock(String mock) {
        mMoke = mock;
    }

    @Override
    public void mock(T mock) {

    }

    @Override
    public void ok(T response) {
        if (response == null && mMoke != null && BuildConfig.DEBUG) {

        }
    }

    @Override
    public void fail(String errorMessage) {
        if (mMoke != null && BuildConfig.DEBUG) {

        }
    }
}
