package com.renxl.realmall.feature.category;

import com.renxl.realmall.base.BasePresenter;
import com.renxl.realmall.base.BaseView;
import com.renxl.realmall.feature.home.Advertising;

import java.util.List;

/**
 * Created by renxl
 * On 2017/4/18 13:50.
 */

interface CategoryContract {
    interface ICategoryView extends BaseView<List<Category>> {
        void showBanner(List<Advertising> advertisings);

        void showWares(List<Wares> wares);

        void loadAll();
    }

    interface ICategoryPresenter extends BasePresenter {
        void requestWares(int categoryId);

        void refreshRequestWares();

        void loadMoreRequestWares();
    }
}
