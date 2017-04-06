package com.renxl.realmall.feature.hot;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class HotFragment extends BaseFragment implements HotConstract.IHotView<Wears> {

    @BindView(R.id.toolbar_hot)
    ToolBar toolbarHot;
    @BindView(R.id.recycleview_hot)
    RecyclerView recycleviewHot;
    Unbinder unbinder;
    @BindView(R.id.image)
    SimpleDraweeView image;

    private HotConstract.IHotPresenter mHotPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, null);
        mHotPresenter = new HotPresenter(this);
        mHotPresenter.start();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void setData(Wears wears) {
        List<Wears.ListBean> listBean = wears.getList();
        for (Wears.ListBean bean : listBean) {
            Log.i(bean.toString());
        }

        if (listBean != null && listBean.get(0) != null && listBean.get(0).getImgUrl() != null) {
            Uri uri = Uri.parse(listBean.get(0).getImgUrl());
            image.setImageURI(uri);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}