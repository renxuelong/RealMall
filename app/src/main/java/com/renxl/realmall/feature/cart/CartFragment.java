package com.renxl.realmall.feature.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class CartFragment extends BaseFragment implements CartContract.ICartView<List<CartBean>> {

    private final int STATE_COMPLETE = 0;
    private final int STATE_EDIT = 1;

    @BindView(R.id.cart_toolbar)
    ToolBar cartToolbar;
    @BindView(R.id.recyclerview_cart_list)
    RecyclerView recyclerviewCartList;
    @BindView(R.id.checkbox_cart_select_alll)
    CheckBox checkboxCartSelectAlll;
    @BindView(R.id.tv_cart_total_price)
    TextView tvCartTotalPrice;
    @BindView(R.id.btn_cart_pay)
    Button btnCartPay;
    @BindView(R.id.btn_cart_del)
    Button btnCartDel;
    Unbinder unbinder;

    private CartContract.ICartPresenter<CartBean> mCartPresenter;
    private CartAdapter mAdapter;

    @Override
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, null);
    }

    @Override
    protected void init() {
        mCartPresenter = new CartPresenter(this, getContext());
        mCartPresenter.start();
        cartToolbar.setTag(STATE_COMPLETE);

        cartToolbar.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                int state = (int) cartToolbar.getTag();
                switch (state) {
                    case STATE_COMPLETE:
                        showDelControl();
                        break;
                    case STATE_EDIT:
                        hideDelControl();
                        break;
                }
            }
        });
    }

    private void showDelControl() {
        cartToolbar.setRightBtnText(getString(R.string.complete));
        btnCartDel.setVisibility(View.VISIBLE);
        btnCartPay.setVisibility(View.GONE);
        tvCartTotalPrice.setVisibility(View.GONE);

        mAdapter.refCheckAllItem(false);
        checkboxCartSelectAlll.setChecked(false);
        cartToolbar.setTag(STATE_EDIT);
    }

    private void hideDelControl() {
        cartToolbar.setRightBtnText(getString(R.string.delete));
        btnCartDel.setVisibility(View.GONE);
        btnCartPay.setVisibility(View.VISIBLE);
        tvCartTotalPrice.setVisibility(View.VISIBLE);

        mAdapter.refCheckAllItem(true);
        checkboxCartSelectAlll.setChecked(true);
        cartToolbar.setTag(STATE_COMPLETE);
    }

    @Override
    public void showData(List<CartBean> cartBeenList) {
        if (cartBeenList == null || cartBeenList.size() <= 0)
            cartBeenList = new ArrayList<>();
        mAdapter = new CartAdapter(cartBeenList, getContext(), tvCartTotalPrice, checkboxCartSelectAlll, new CartAdapter.Listener() {
            @Override
            public void addClick(CartBean item) {
                mCartPresenter.updateData(item);
            }

            @Override
            public void subClick(CartBean item) {
                mCartPresenter.updateData(item);
            }

            @Override
            public void itemClick(CartBean item) {
                mCartPresenter.updateData(item);
            }

            @Override
            public void delete(CartBean item) {
                mCartPresenter.delete(item);
            }

        });
        recyclerviewCartList.setAdapter(mAdapter);
        checkboxCartSelectAlll.setChecked(mAdapter.isCheckAll());
        mAdapter.showTotalPrice();
        recyclerviewCartList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewCartList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void refData(List<CartBean> cartBeen) {
        mAdapter.clear();
        mAdapter.setData(cartBeen);
        checkboxCartSelectAlll.setChecked(mAdapter.isCheckAll());
        mAdapter.showTotalPrice();
    }

    @Override
    public void fail() {

    }

    @OnClick({R.id.btn_cart_pay, R.id.btn_cart_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cart_pay:
                toPay();
                break;
            case R.id.btn_cart_del:
                mAdapter.delete();
                break;
        }
    }

    private void toPay() {
        List<CartBean> lists = CartProvider.getInstance().getCheckedCarBeanList();
        if(lists == null || lists.size() <= 0) {
            Toast.show("请选择需要结算的商品");
            return;
        }

        Intent jumpIntent = new Intent(getActivity(), FillOrderActivity.class);
        if (UserLocalData.getUser(getContext()) == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra(LoginActivity.JUMP_INTENT, jumpIntent);
            startActivity(intent);
        } else
            startActivity(jumpIntent);
    }

    public void refData() {
        mCartPresenter.refData();
    }
}