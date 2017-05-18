package com.renxl.realmall.application;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.format.Formatter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.renxl.realmall.utils.Log;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by renxl
 * On 2017/3/29 11:06.
 */

public class RealMallApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static RealMallApp instance;

    @SuppressLint("StaticFieldLeak")
    public static TextView view;
    public static boolean isAdd;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUtils();
    }

    public static Application getContext() {
        return instance;
    }

    private void initUtils() {
        // 添加为捕获异常监听
        CrashHandler.getInstance().init(this);
        // Stetho 网络监测工具初始化
        Stetho.initializeWithDefaults(this);
        // 内存泄漏工具
        LeakCanary.install(this);
        // 图片加载工具 Fresco 初始化
        Fresco.initialize(this);
    }

    private String formatData(long fileData) {
        return Formatter.formatFileSize(this, fileData);
    }
}
