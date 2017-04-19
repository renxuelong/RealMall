package com.renxl.realmall.feature.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.Decoration;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class HomeFragment extends BaseFragment implements HomeContract.IHomeView<List<Advertising>> {

    Unbinder unbinder;
    @BindView(R.id.sl_home)
    SliderLayout homeSL;
    @BindView(R.id.toolbar_home)
    ToolBar toolbarHome;
    @BindView(R.id.recycleview_home)
    RecyclerView recycleviewHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeContract.IHomePresenter homePresenter = new HomePresenter(this);
        homePresenter.start();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);

        return view;
    }

    private View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        toolbarHome.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {
                Toast.show("onSearchClick");
            }

            @Override
            public void onRightClich() {
                Toast.show("onRightClich");
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(List<Advertising> advertisings) {
        if (advertisings == null) return;
        for (final Advertising advertising : advertisings) {
            if (TextUtils.isEmpty(advertising.getImgUrl()) || TextUtils.isEmpty(advertising.getName()))
                continue;
            final TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(advertising.getName()).image(advertising.getImgUrl()).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.show(advertising.getName());
                }
            });
            homeSL.addSlider(textSliderView);
        }
    }

    @Override
    public void fail() {
    }

    @Override
    public void onStop() {
        homeSL.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void showRecommend(List<Recommend> recommends) {
        if (recommends == null || recommends.size() <= 0) return;

        final HomeAdapter adapter = new HomeAdapter(recommends, this.getContext());
        adapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Recommend recommend = adapter.getDatas().get(position);
                Toast.show("Item - " + position + "--" + recommend.getTitle());
            }
        });
        recycleviewHome.setAdapter(adapter);
        recycleviewHome.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recycleviewHome.addItemDecoration(new Decoration());
    }

    @Override
    public void showToast(String str) {
        if (TextUtils.isEmpty(str)) return;
        Toast.show(str);
    }
}