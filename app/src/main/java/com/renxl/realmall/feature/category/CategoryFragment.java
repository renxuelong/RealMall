package com.renxl.realmall.feature.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class CategoryFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, null);

        return view;
    }

}