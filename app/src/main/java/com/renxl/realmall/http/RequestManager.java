package com.renxl.realmall.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.renxl.realmall.BuildConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by renxl
 * On 2017/3/30 20:08.
 * 执行网络请求的类
 */

public class RequestManager {
    public static final int GET = 1;
    public static final int POST = 2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "RequestManager";

    private static OkHttpClient okHttpClient;
    private static Gson mGson;
    private static Handler mHandler;

    static {
        // 初始化 okHttpClient 并添加 Stetho 拦截器监测网络
        okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 禁止 APP 中的请求直接访问该方法，必须通过 RealMallClient
     *
     * @param method
     * @param url
     * @param requestParams
     * @param callback
     */
    static void sendRequest(int method, final String url, RequestParams requestParams, final BaseCallback callback) {
        sendRequest(method, url, requestParams, callback, true);
    }

    static void sendRequest(int method, final String url, RequestParams requestParams, final BaseCallback callback, boolean cache) {
        excute(url, callback, getRequest(method, url, requestParams, callback, cache, getRequestBody(requestParams)));
    }

    static void sendRequest(int method, final String url, String json, final BaseCallback callback) {
        sendRequest(method, url, json, callback, true);
    }

    static void sendRequest(int method, final String url, String json, final BaseCallback callback, boolean cache) {
        excute(url, callback, getRequest(method, url, null, callback, cache, getRequestBody(json)));
    }

    /**
     * 执行请求的方法
     *
     * @param url      url
     * @param callback 回调接口
     * @param request  请求 Request
     */
    private static void excute(final String url, final BaseCallback callback, final Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                log("onFailure 请求结果：" + e.getMessage());
                if (callback == null) {
                    log("回调失败(请求无 Callback):" + e.getMessage());
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback.cache != null) {
                            callback.ok(getResponseByResult(callback.cache, callback));
                            return;
                        }
                        callback.fail(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                log("onResponse 请求结果：" + result);
                if (callback == null) {
                    log("回调失败(请求无 Callback)" + result);
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(result)) return;
                        callback.ok(getResponseByResult(result, callback));
                    }
                });
                HTTPFileCache.put(url, result);
            }
        });
    }

    private static Object getResponseByResult(String result, BaseCallback callback) {
        return mGson.fromJson(result, callback.mType);
    }

    /**
     * 根据请求参数构造请求 Request
     * * @param method
     *
     * @param url
     * @param requestParams
     * @param callback
     * @param cache
     * @param body
     * @return
     */
    private static Request getRequest(int method, String url, RequestParams requestParams, final BaseCallback callback, boolean cache, RequestBody body) {
        Request request;
        String fullUrl = url;
        if (method == GET && requestParams != null) {
            fullUrl = requestParams.toQueryString(url);
            request = new Request.Builder().url(fullUrl).build();
        } else if (method == POST && body != null) {
            request = new Request.Builder().url(fullUrl).post(body).build();
        } else {
            request = new Request.Builder().url(fullUrl).build();
        }
        if (cache && callback != null) {
            callback.cache = HTTPFileCache.get(fullUrl);
        }
        return request;
    }

    /**
     * Post 参数为 Json 时的 RequestBody 构造
     *
     * @param json
     * @return
     */
    private static RequestBody getRequestBody(String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        return requestBody;
    }

    /**
     * Post 请求时的 RequestBody 构造方法
     *
     * @param requestParams
     * @return
     */
    private static RequestBody getRequestBody(RequestParams requestParams) {
        FormBody.Builder builder = new FormBody.Builder();
        if (requestParams != null && requestParams.params != null) {
            HashMap<String, Object> params = (HashMap<String, Object>) requestParams.params;
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = String.valueOf(params.get(key));
                builder.add(key, value);
            }
        } else {
            Log.e(TAG, "Post 请求体为空！！！");
        }
        return builder.build();
    }

    private static void log(String log) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(log)) {
            Log.i(TAG, log);
        }
    }
}
