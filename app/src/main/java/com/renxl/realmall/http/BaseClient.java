package com.renxl.realmall.http;

import android.text.TextUtils;

/**
 * Created by renxl
 * On 2017/3/31 15:59.
 */

public abstract class BaseClient {


    public abstract String getHost();

    private String getAbsoluteUrl(String url) {
        if (TextUtils.isEmpty(url)) return getHost();
        return getHost() + url;
    }

    public void get(String url, HTTPCallback callback) {
        get(url, null, callback);
    }

    public void get(String url, HTTPCallback callback, boolean cache) {
        get(url, null, callback, cache);
    }

    public void get(String url, RequestParams params, HTTPCallback callback) {
        RequestManager.sendRequest(RequestManager.GET, getAbsoluteUrl(url), params, callback);
    }

    public void get(String url, RequestParams params, HTTPCallback callback, boolean cache) {
        RequestManager.sendRequest(RequestManager.GET, getAbsoluteUrl(url), params, callback, cache);
    }

    public void post(String url, HTTPCallback callback) {
        post(url, "", callback, true);
    }

    public void post(String url, String json, HTTPCallback callback, boolean cache) {
        RequestManager.sendRequest(RequestManager.POST, getAbsoluteUrl(url), json, callback, cache);
    }

    public void post(String url, RequestParams params, HTTPCallback callback) {
        post(url, params, callback, true);
    }

    public void post(String url, RequestParams params, HTTPCallback callback, boolean cache) {
        RequestManager.sendRequest(RequestManager.POST, getAbsoluteUrl(url), params, callback, cache);
    }
}
