package com.renxl.realmall.feature.cart;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.renxl.realmall.utils.JsonUtils;
import com.renxl.realmall.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renxl
 * On 2017/5/3 12:55.
 */

public class CartProvider {
    private static final String CART_KEY = "cart";
    private static SparseArray<CartBean> mData;
    private Context mContext;

    public CartProvider(Context context) {
        mData = new SparseArray<>();
        mContext = context;
        initSparseArray();
    }

    private void initSparseArray() {
        List<CartBean> list = getListFromLocal();
        if (list == null || list.size() <= 0) return;
        for (CartBean cartBean : list) {
            mData.put((int) (cartBean.getId()), cartBean);
        }
    }

    public void putCartBean(CartBean cartBean) {
        if (cartBean == null) return;

        CartBean temp = mData.get((int) (cartBean.getId()));
        if (temp == null) {
            cartBean.setmCount(1);
        } else {
            if (cartBean.getmCount() == 0) {
                cartBean.setmCount(temp.getmCount() + 1);
                cartBean.setChecked(temp.isChecked());
            }
        }
        mData.put((int) (cartBean.getId()), cartBean);
        commit();
    }

    public void delete(CartBean cartBean) {
        mData.delete((int) (cartBean.getId()));
        commit();
    }

    private void commit() {
        String json = getJsonFromData();
        SharedPreferencesUtils.putString(mContext, CART_KEY, json);
    }

    private String getJsonFromData() {
        List<CartBean> lists = getCartBeanList();
        return JsonUtils.toString(lists);
    }

    public List<CartBean> getCartBeanList() {
        List<CartBean> lists = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            lists.add(mData.valueAt(i));
        }
        return lists;
    }

    private List<CartBean> getListFromLocal() {
        String json = SharedPreferencesUtils.getString(mContext, CART_KEY);
        if (TextUtils.isEmpty(json)) return null;
        return JsonUtils.fromJson(json, new TypeToken<List<CartBean>>() {
        }.getType());
    }
}
