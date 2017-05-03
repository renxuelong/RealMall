package com.renxl.realmall.feature.cart;

import android.content.Context;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/3 11:49.
 */

public class CartPresenter implements CartContract.ICartPresenter<CartBean> {
    private CartContract.ICartView<List<CartBean>> mView;
    private Context mContext;
    private CartProvider mCartProvider;

    public CartPresenter(CartContract.ICartView cartView, Context context) {
        mView = cartView;
        mContext = context;
        mCartProvider = new CartProvider(mContext);
    }

    @Override
    public void start() {
        List<CartBean> list = mCartProvider.getCartBeanList();
        mView.showData(list);
    }

    @Override
    public void updateData(int position) {
        CartBean cartBean = mCartProvider.getCartBeanList().get(position);
        cartBean.setChecked(!cartBean.isChecked());

        mCartProvider.putCartBean(cartBean);

        mView.refData(mCartProvider.getCartBeanList(), position);
    }

    @Override
    public void updateData(CartBean cartBean) {
        mCartProvider.putCartBean(cartBean);
    }

    @Override
    public void refData() {
        List<CartBean> list = mCartProvider.getCartBeanList();
        mView.refData(list);
    }
}