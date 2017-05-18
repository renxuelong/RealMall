package com.renxl.realmall.http;

import android.content.Context;
import android.content.Intent;

import com.renxl.realmall.R;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.utils.Toast;

/**
 * Created by renxl
 * On 2017/5/16 17:03.
 */

public abstract class TokenCallback<T> extends HTTPCallback<T> {
    private Context mContext;

    public TokenCallback(Context context) {
        mType = getSuperclassTypeParameter(getClass());
        mContext = context;
    }

    @Override
    public void tokenError() {
        Toast.show(mContext.getString(R.string.token_error));
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
        UserLocalData.clearUser(mContext);
    }
}
