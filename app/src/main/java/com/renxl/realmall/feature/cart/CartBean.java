package com.renxl.realmall.feature.cart;

import android.os.Parcelable;

import com.renxl.realmall.feature.category.Wares;

/**
 * Created by renxl
 * On 2017/5/3 10:14.
 */

public class CartBean extends Wares implements Parcelable {
    private int mCount;
    private boolean isChecked;

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "mCount=" + mCount +
                ", isChecked=" + isChecked +
                '}' + super.toString();
    }
}
