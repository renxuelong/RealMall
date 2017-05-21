package com.renxl.realmall.feature.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.feature.cart.CartFragment;
import com.renxl.realmall.feature.category.CategoryFragment;
import com.renxl.realmall.feature.home.HomeFragment;
import com.renxl.realmall.feature.hot.HotFragment;
import com.renxl.realmall.feature.mine.MineFragment;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private LayoutInflater mLayoutInflater;
    private FragmentTabHost mTabHost;

    private CartFragment mCartFragment;
    private MineFragment mineFragment;

    private boolean isCart;
    private long keyBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCart) refCart();
    }

    public void initTab() {
        List<Tab> mTabs = new ArrayList<>(5);
        mTabs.add(new Tab(R.drawable.selector_icon_home, R.string.home, HomeFragment.class));
        mTabs.add(new Tab(R.drawable.selector_icon_hot, R.string.hot, HotFragment.class));
        mTabs.add(new Tab(R.drawable.selector_icon_category, R.string.category, CategoryFragment.class));
        mTabs.add(new Tab(R.drawable.selector_icon_cart, R.string.cart, CartFragment.class));
        mTabs.add(new Tab(R.drawable.selector_icon_mine, R.string.mine, MineFragment.class));

        mLayoutInflater = getLayoutInflater();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        for (Tab mTab : mTabs) {
            addTab(mTab);
        }

        mTabHost.setCurrentTab(0);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (getString(R.string.cart).equals(tabId)) {
                    cartSelected();
                } else if (getString(R.string.mine).equals(tabId)) {
                    mineSelected();
                }

                isCart = getString(R.string.cart).equals(tabId);
            }
        });
    }

    private void mineSelected() {
        if (mineFragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.mine));
            if (fragment != null) mineFragment = (MineFragment) fragment;
        } else {
            mineFragment.showUser();
        }
    }

    private void cartSelected() {
        refCart();
    }

    private void refCart() {
        if (mCartFragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if (fragment != null) {
                mCartFragment = (CartFragment) fragment;
                mCartFragment.refData();
            }
        } else {
            mCartFragment.refData();
        }
    }

    private void addTab(Tab tab) {
        mTabHost.addTab(mTabHost.newTabSpec(getString(tab.getTitle())).setIndicator(getTabView(tab)), tab.getFragment(), null);
    }

    private View getTabView(Tab tab) {
        @SuppressLint("InflateParams") View view = mLayoutInflater.inflate(R.layout.tab_main_indecator, null, false);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_tab);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
        imgIcon.setImageResource(tab.getIcon());
        tvTitle.setText(tab.getTitle());
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - keyBackTime < 2000)
                finish();
            else {
                Toast.show("再按一次退出 RealMall");
                keyBackTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}