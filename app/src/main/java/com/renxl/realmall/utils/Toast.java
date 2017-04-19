package com.renxl.realmall.utils;

import android.content.Context;
import android.text.TextUtils;

import com.renxl.realmall.application.RealMallApp;

/**
 * Created by renxl
 * On 2017/4/5 15:08.
 */

public class Toast {

    private static android.widget.Toast toast;

    private static Context mContext;

    static {
        mContext = RealMallApp.getContext();
    }

    public static void show(String log) {
        if (TextUtils.isEmpty(log)) return;
        toast = android.widget.Toast.makeText(mContext, log, android.widget.Toast.LENGTH_SHORT);
        toast.show();
    }

}
