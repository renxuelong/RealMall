package com.renxl.realmall.utils;

import com.renxl.realmall.BuildConfig;

/**
 * Created by renxl
 * On 2017/3/30 18:15.
 */

public class Log {
    private static boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "renxl";

    public static void i(String log) {
        if (DEBUG) {
            android.util.Log.i(TAG, log + "");
        }
    }

    public static void i(String tag, String log) {
        if (DEBUG) {
            android.util.Log.i(tag, log + "");
        }
    }
}
