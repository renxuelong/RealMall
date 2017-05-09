package com.renxl.realmall.http;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by renxl
 * On 2017/3/30 21:13.
 * 将网络请求参数转化成字符串拼接
 */

public class RequestParams {

    Map<String, Object> params;

    public RequestParams() {
        params = new HashMap<>();
    }

    public void put(String key, String value) {
        addParams(key, value);
    }

    public void put(String key, boolean value) {
        addParams(key, value);
    }

    public void put(String key, int value) {
        addParams(key, value);
    }

    public void put(String key, float value) {
        addParams(key, value);
    }

    public void put(String key, Object value) {
        addParams(key, value);
    }

    public void put(String key, String[] value) {
        if (key == null || value == null) return;

        params.put(key, new JSONArray(Arrays.asList(value)));

    }

    public void put(String key, JSONObject value) {
        if (key == null || value == null) return;

        params.put(key, value);

    }

    public void put(String key, JSONArray value) {
        if (key == null || value == null) return;

        params.put(key, value);

    }

    public RequestParams remove(String key) {
        if (key == null) return this;
        params.remove(key);
        return this;
    }

    public String toString() {
        return params.toString();
    }

    private void addParams(String key, Object value) {
        if (key == null || value == null) return;

        params.put(key, value);

    }

    /**
     * 将参数转化为用 & 相连接的格式，并做解码
     *
     * @return
     */
    private String getEncodedParamString() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> keySet = params.keySet();

        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            if (stringBuilder.length() > 0) stringBuilder.append("&");

            String key = (String) iterator.next();
            String value = String.valueOf(params.get(key));

            stringBuilder.append(URLEncoder.encode(key));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(value));
        }
        return stringBuilder.toString();
    }

    /**
     * 拼接 url 和参数
     *
     * @param url
     * @return
     */
    public String toQueryString(String url) {
        String paramString = getEncodedParamString();
        if (TextUtils.isEmpty(paramString)) return url;
        if (url.indexOf("?") == -1) {
            url += "?" + paramString;
        } else {
            url += "&" + paramString;
        }
        return url;
    }

}
