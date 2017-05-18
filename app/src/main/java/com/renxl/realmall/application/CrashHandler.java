package com.renxl.realmall.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import com.renxl.realmall.utils.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by renxl
 * On 2017/5/15 10:18.
 */

class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final File PATH = RealMallApp.getContext().getExternalFilesDir(null);
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";
    private static final String TAG = "CrashHandler";
    private static CrashHandler instance = new CrashHandler();

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }

    /**
     * 为 Thread 类设置 UncaughtExceptionHandler 对象
     *
     * @param context 上下文对象
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 有 Crash 时的处理方法
     *
     * @param t 线程
     * @param e 异常
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        // 记录 Crash 信息
        dumpExceptionToSDCard(e);

        // 如果系统有默认的异常处理器，则使用默认的异常处理器来处理，否则就交给自己来处理
        if (mUncaughtExceptionHandler != null) {
            mUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 将异常信息保存到 SD 卡的特定文件中
     *
     * @param e 异常
     */
    private void dumpExceptionToSDCard(Throwable e) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            Log.i(TAG, "Sdcard unmounted,skip dump exception");
        if (PATH == null) return;

        File file = new File(PATH, "Crash/log");
        if (!file.exists() && file.mkdirs()) return;

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(System.currentTimeMillis());
        File crashFile = new File(file, FILE_NAME + date + FILE_NAME_SUFFIX);

        String data = e.getMessage();

        try {
            PrintWriter pw = new PrintWriter(crashFile);
            pw.println(date);
            dumpPhoneInfo(pw);
            e.printStackTrace(pw);
            pw.close();
        } catch (FileNotFoundException | PackageManager.NameNotFoundException e1) {
            Log.i(TAG, "dump crash info failed " + e1.getMessage());
        }
    }

    /**
     * 打印设备信息
     *
     * @param pw 输出流
     * @throws PackageManager.NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        if (pw == null) return;
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.println("App Version: " + pi.versionName + "_" + pi.versionCode);

        pw.println("Vendor: " + Build.MANUFACTURER);

        pw.println("Model: " + Build.MODEL);

        pw.println("CPU_ ABI: " + Build.CPU_ABI);
    }
}
