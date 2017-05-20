package com.renxl.realmall.http;

import android.content.Context;
import android.text.TextUtils;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.application.RealMallApp;
import com.renxl.realmall.feature.sign_in.UserLocalData;

/**
 * Created by renxl
 * On 2017/3/31 15:59.
 */

public abstract class BaseClient {

    protected Context context;

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
        get(url, params, callback, true);
    }

    public void get(String url, RequestParams params, HTTPCallback callback, boolean cache) {
        RequestManager.sendRequest(RequestManager.GET, getAbsoluteUrl(url), params, callback, cache);
    }

    public void post(String url, HTTPCallback callback) {
        post(url, "", callback, true);
    }

    public void post(String url, String json, HTTPCallback callback) {
        post(url, json, callback, true);
    }

    public void post(String url, HTTPCallback callback, boolean cache) {
        post(url, "", callback, cache);
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

    public static RequestParams getTokenParams(RequestParams params) {
        if (params == null) {
            params = new RequestParams();
        }
        params.put(Constants.TOKEN, UserLocalData.getToken(RealMallApp.getContext()));
        return params;
    }
}
