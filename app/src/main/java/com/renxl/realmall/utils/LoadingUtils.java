package com.renxl.realmall.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renxl.realmall.R;

/**
 * Created by renxl
 * On 2017/5/10 17:10.
 */

public class LoadingUtils {

    public static Dialog getDialog(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);

        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return dialog;
    }

}
