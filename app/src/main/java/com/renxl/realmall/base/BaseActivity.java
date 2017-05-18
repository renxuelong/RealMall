package com.renxl.realmall.base;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import com.renxl.realmall.utils.LoadingUtils;

/**
 * Created by renxl
 * On 2017/3/29 20:46.
 */

public class BaseActivity extends AppCompatActivity {

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

}
