package com.renxl.realmall.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by renxl
 * On 2017/5/17 11:10.
 */

public class ManifestUtils {

    public static String getMetaDataValue(Context context, String name, String def) {
        String value = getMetaDataValue(context, name);
        return TextUtils.isEmpty(value) ? def : value;
    }

    public static String getMetaDataValue(Context context, String name) {
        String value = null;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                value = (String) info.metaData.get(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not read the name in the manifest file.", e);
        }

        if (value == null) {
            throw new RuntimeException("The name '" + name + "' is not defined in the manifest file's meta data.");
        }

        return value;
    }

}
