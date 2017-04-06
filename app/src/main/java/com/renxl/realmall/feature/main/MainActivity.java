package com.renxl.realmall.feature.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.feature.cart.CartFragment;
import com.renxl.realmall.feature.category.CategoryFragment;
import com.renxl.realmall.feature.home.HomeFragment;
import com.renxl.realmall.feature.hot.HotFragment;
import com.renxl.realmall.feature.mine.MineFragment;
import com.renxl.realmall.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private LayoutInflater mLayoutInflater;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    private void addTab(Tab tab) {
        mTabHost.addTab(mTabHost.newTabSpec(getString(tab.getTitle())).setIndicator(getTabView(tab)), tab.getFragment(), null);
    }

    private View getTabView(Tab tab) {
        View view = mLayoutInflater.inflate(R.layout.tab_indecator, null);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_tab);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
        imgIcon.setImageResource(tab.getIcon());
        tvTitle.setText(tab.getTitle());
        return view;
    }

}