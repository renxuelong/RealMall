package com.renxl.realmall.feature.cart;

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
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class CartFragment extends BaseFragment implements CartContract.ICartView<List<CartBean>> {
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
    Unbinder unbinder;

    private CartContract.ICartPresenter<CartBean> mCartPresenter;
    private CartAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        unbinder = ButterKnife.bind(this, view);

        mCartPresenter = new CartPresenter(this, getContext());
        mCartPresenter.start();

        return view;
    }

    @Override
    public void showData(List<CartBean> cartBeenList) {
        if (cartBeenList == null || cartBeenList.size() <= 0)
            cartBeenList = new ArrayList<>();
        mAdapter = new CartAdapter(cartBeenList, getContext(), new CartAdapter.OnAddSubClickListener() {
            @Override
            public void addClick(CartBean item) {
                mCartPresenter.updateData(item);
            }

            @Override
            public void subClick(CartBean item) {
                mCartPresenter.updateData(item);
            }

        });
        mAdapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mCartPresenter.updateData(position);
            }
        });
        recyclerviewCartList.setAdapter(mAdapter);
        recyclerviewCartList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewCartList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void refData() {
        mCartPresenter.refData();
    }

    @Override
    public void refData(List<CartBean> cartBeen) {
        mAdapter.clear();
        mAdapter.setData(cartBeen);
    }

    @Override
    public void refData(List<CartBean> cartBeanList, int position) {
        CartBean cartBean = cartBeanList.get(position);
        mAdapter.setData(cartBean, position);
    }

    @Override
    public void fail() {

    }

    @OnClick({R.id.checkbox_cart_select_alll, R.id.btn_cart_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox_cart_select_alll:
                break;
            case R.id.btn_cart_pay:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}