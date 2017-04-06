package com.renxl.realmall.application;

import android.app.Application;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by renxl
 * On 2017/3/29 11:06.
 */

public class RealMallApp extends Application {

    private static RealMallApp instance;

    public static TextView view;
    public static boolean isAdd;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Stetho 网络监测工具初始化
        Stetho.initializeWithDefaults(this);
        // 内存泄漏工具
        LeakCanary.install(this);
        // 图片加载工具 Fresco 初始化
        Fresco.initialize(this);
    }

    public static Application getContext() {
        return instance;
    }
}
