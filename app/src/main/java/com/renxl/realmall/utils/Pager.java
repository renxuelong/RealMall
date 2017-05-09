package com.renxl.realmall.utils;

import android.support.v7.widget.RecyclerView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by renxl
 * On 2017/5/8 14:50.
 */

public class Pager {

    public static final String PARAM_CUR_PAGE = "curPage";
    private static final String PARAM_PAGE_SIZE = "pageSize";
    private static final int STATE_LOAD = 0;
    public static final int STATE_REFRESH = 1;
    private static final int STATE_LOADMORE = 2;
    private static final int PAGE_SIZE = 20;
    private int status = STATE_LOAD;

    private Type type;
    private int currentPage = 1;
    private int totalPage = 0;

    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;
    private OnPageListener pageListener;
    private RecyclerView.LayoutManager layoutManager;
    private RequestParams params;
    private String url;
    private boolean loadMore;

    private Pager(Type type) {
        this.type = type;
        params = new RequestParams();
    }

    public void putParam(String key, Object value) {
        params.put(key, value);
    }

    public void start() {
        check();
        params.put(PARAM_PAGE_SIZE, PAGE_SIZE);
        params.put(PARAM_CUR_PAGE, currentPage);
        refreshLayout.setLoadMore(loadMore);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                status = STATE_REFRESH;
                currentPage = 1;
                params.put(PARAM_CUR_PAGE, currentPage);
                requestData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                status = STATE_LOADMORE;
                currentPage += 1;
                if (currentPage > totalPage) {
                    currentPage -= 1;
                    refreshLayout.finishRefreshLoadMore();
                    Toast.show("无更多商品");
                    return;
                }
                params.put(PARAM_CUR_PAGE, currentPage);
                requestData();
            }
        });
        requestData();
    }

    private void check() {
        if (recyclerView == null)
            throw new RuntimeException("RecycleView can not be null");
        if (refreshLayout == null)
            throw new RuntimeException("refreshLayout can not be null");
        if (url == null)
            throw new RuntimeException("Url can not be null");

    }

    public <T> void requestData() {
        HTTPCallback httpCallback = new HTTPCallback<BaseBean<T>>() {
            @Override
            public void ok(BaseBean<T> response) {

                totalPage = response.getTotalPage();

                switch (status) {
                    case STATE_LOAD:
                        pageListener.load(response.getList(), response.getTotalPage(), response.getTotalCount());
                        break;
                    case STATE_REFRESH:
                        pageListener.refresh(response.getList(), response.getTotalPage(), response.getTotalCount());
                        break;
                    case STATE_LOADMORE:
                        pageListener.loadMore(response.getList(), response.getTotalPage(), response.getTotalCount());
                        break;
                }
            }
        };
        httpCallback.mType = type;
        RealMallClient.getInstance().get(url, params, httpCallback);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Builder {

        private Pager mPager;

        public Builder(Type type) {
            mPager = new Pager(type);
        }

        public Pager build() {

            return mPager;
        }

        public Builder setUrl(String url) {
            mPager.url = url;
            return this;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            mPager.recyclerView = recyclerView;
            return this;
        }


        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            mPager.refreshLayout = refreshLayout;
            return this;
        }

        public Builder setOnPageListener(OnPageListener pageListener) {
            mPager.pageListener = pageListener;
            return this;
        }

        public Builder putParams(String key, Object value) {
            mPager.params.put(key, value);
            return this;
        }

        public Builder setCanLoadMore(boolean canLoadMore) {
            mPager.loadMore = canLoadMore;
            return this;
        }

    }

    public interface OnPageListener<T> {
        void load(List<T> list, int totalPage, int totalCount);

        void refresh(List<T> list, int totalPage, int totalCount);

        void loadMore(List<T> list, int totalPage, int totalCount);
    }
}
