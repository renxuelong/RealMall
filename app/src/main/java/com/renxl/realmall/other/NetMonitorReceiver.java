package com.renxl.realmall.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.renxl.realmall.application.RealMallApp;

import static android.content.Context.WINDOW_SERVICE;

public class NetMonitorReceiver extends BroadcastReceiver {

    Context mContext;
    TextView view = RealMallApp.view;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        Log.i("renxl", "onReceive");
        if (NetUtils.isConnected(context)) {
            removeWindow();
        } else {
            addWindow();
        }
    }

    private void removeWindow() {
        if (!RealMallApp.isAdd) return;

        Log.i("renxl", "removeWindow");
        if (RealMallApp.view != null) {
            WindowManager manager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
            manager.removeView(view);
            RealMallApp.isAdd = false;
            Log.i("renxl", "removeWindow111");
        } else {
            Log.i("renxl", "removeWindow111222");
        }
    }

    private void addWindow() {
        if (RealMallApp.isAdd) return;
        if (view == null) {
            view = new TextView(mContext);
            view.setText("Text");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("renxl", "onClickonClickonClick");
                }
            });
            view.setBackgroundColor(Color.RED);
            RealMallApp.view = view;
        }
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;

        WindowManager manager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        manager.addView(view, mLayoutParams);
        RealMallApp.isAdd = true;
    }
}
