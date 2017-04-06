package com.renxl.realmall.feature.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.utils.Toast;
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

    private HomeContract.IHomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
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
        homePresenter = new HomePresenter(this);
        homePresenter.start();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setData(List<Advertising> advertisings) {
        if (advertisings == null) return;
        for (final Advertising advertising : advertisings) {
            final TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(advertising.getName()).image(advertising.getImgUrl()).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.show(advertising.getName());
                }
            });
            homeSL.addSlider(textSliderView);
        }

        homeSL.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onStop() {
        homeSL.stopAutoCycle();
        super.onStop();
    }
}