package com.renxl.realmall.base;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.renxl.realmall.utils.LoadingUtils;

/**
 * Created by renxl
 * On 2017/3/29 20:46.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public static final int REQUEST_CODE = 10001;

    private Dialog alertDialog = null;

    public void showLoading() {
        if (alertDialog == null) {
            alertDialog = LoadingUtils.getDialog(this);
        }
        if (alertDialog.isShowing()) return;
        alertDialog.show();
    }

    public void hideLoading() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.hide();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 为了彻底解决点击除EditText时键盘的隐藏功能，需要在Activity的事件分发逻辑中，添加点击位置的判断，
        // 如果当前焦点是EditText，点击位置不是EditText则隐藏
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getCurrentFocus() != null) {
            if (getCurrentFocus() instanceof EditText) {
                // 判断是否需要隐藏
                if (shouleHideInputMethod(ev, getCurrentFocus()) && getCurrentFocus().getWindowToken() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean shouleHideInputMethod(MotionEvent event, View v) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int right = left + v.getWidth();
        int bottom = top + v.getHeight();

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        return !(x > left && x < right && y > top && y < bottom);
    }
}
