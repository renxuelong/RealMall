package com.renxl.realmall.feature.sign_in;

import android.content.Context;
import android.text.TextUtils;

import com.renxl.realmall.application.Constants;
import com.renxl.realmall.utils.JsonUtils;
import com.renxl.realmall.utils.SharedPreferencesUtils;

/**
 * Created by renxl
 * On 2017/5/16 15:45.
 */

public class UserLocalData {

    public static void putUser(Context context, User user) {
        SharedPreferencesUtils.putString(context, Constants.USER, JsonUtils.toString(user));
    }

    public static User getUser(Context context) {
        String str = SharedPreferencesUtils.getString(context, Constants.USER);
        if (TextUtils.isEmpty(str)) return null;
        return JsonUtils.fromJson(str, User.class);
    }

    public static void putToken(Context context, String token) {
        SharedPreferencesUtils.putString(context, Constants.TOKEN, token);
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtils.getString(context, Constants.TOKEN);
    }

    public static void clearUser(Context context) {
        putUser(context, null);
        putToken(context, null);
    }
}
