package com.renxl.realmall.feature.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.utils.Log;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class MineFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, null);
        Log.i("onCreate");
        return view;
    }

}