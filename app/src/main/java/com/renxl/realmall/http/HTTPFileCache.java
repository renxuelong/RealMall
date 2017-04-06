package com.renxl.realmall.http;

import com.renxl.realmall.application.RealMallApp;

/**
 * Created by renxl
 * On 2017/3/31 10:58.
 */

public class HTTPFileCache {
    public static final String CACHE_NAME = "OKHTTP";

    public static ACache cache = ACache.get(RealMallApp.getContext(), CACHE_NAME);

    public static void put(String key, String value) {
        cache.put(key, value, ACache.TIME_DAY);
    }

    public static String get(String key) {
        return cache.getAsString(key);
    }

    public static void clear() {
        cache.clear();
    }
}
