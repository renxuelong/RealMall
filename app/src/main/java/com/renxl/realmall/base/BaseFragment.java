package com.renxl.realmall.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/3/1 9:33.
 */

public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = onCreate(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    protected abstract void init();

    protected abstract View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
