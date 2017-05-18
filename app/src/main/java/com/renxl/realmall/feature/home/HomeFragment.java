package com.renxl.realmall.feature.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.renxl.realmall.feature.wares_list.WaresActivity;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.Decoration;
import com.renxl.realmall.widget.ToolBar;

import java.util.List;

import butterknife.BindView;
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
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    protected void init() {
        HomeContract.IHomePresenter homePresenter = new HomePresenter(this);
        homePresenter.start();

        toolbarHome.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {
                Toast.show("onSearchClick");
            }

            @Override
            public void onRightClick() {
                Toast.show("onRightClick");
            }
        });
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
        adapter.setImageClickListener(new HomeAdapter.ImageClickListener() {
            @Override
            public void onImageClick(View view, int campaignId) {
                startAnimation(view, campaignId);
            }
        });

        recycleviewHome.setAdapter(adapter);
        recycleviewHome.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recycleviewHome.addItemDecoration(new Decoration());
    }

    private void startAnimation(View view, final int campaignId) {
        if (view == null) return;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationX", 0.0F, 360.0F).setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(getActivity(), WaresActivity.class);
                intent.putExtra(WaresActivity.CAMPAIGN_ID, campaignId);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    public void showToast(String str) {
        if (TextUtils.isEmpty(str)) return;
        Toast.show(str);
    }
}