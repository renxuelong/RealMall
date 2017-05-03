package com.renxl.realmall.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by renxl
 * On 2017/5/3 15:55.
 */

public class JsonUtils {

    private static Gson mGson;

    static {
        mGson = new Gson();
    }

    public static String toString(Object obj) {
        Log.i("ToString " + mGson.toJson(obj));
        return mGson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        Log.i("FromJson " + json);
        return mGson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        Log.i("FromJson " + json);

        return mGson.fromJson(json, type);
    }
}
