package com.renxl.realmall.feature.wares_detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.application.RealMallApp;
import com.renxl.realmall.feature.cart.CartProvider;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.utils.WebViewUtils;
import com.renxl.realmall.widget.ToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WareDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WARES = "wares";

    @BindView(R.id.toolbar_ware_detail)
    ToolBar toolbarWareDetail;

    private WebView webWareDetail;
    private int id;
    private Wares mWares;
    private WebInterface webInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_detail);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);

        mWares = getIntent().getParcelableExtra(EXTRA_WARES);
        if (mWares == null) finish();

        initToolbar();
        initWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        ViewGroup contentView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content).findViewById(R.id.content_ware_detail);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        webWareDetail = new WebView(RealMallApp.getContext());
        contentView.addView(webWareDetail, lp);

        webInterface = new WebInterface();
        WebSettings webSettings = webWareDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setAppCacheEnabled(true);

        String url = RealMallClient.getBaseUrl() + Constants.WARES_DETAIL;
        webWareDetail.loadUrl(url);

        webWareDetail.addJavascriptInterface(webInterface, "appInterface");
        webWareDetail.setWebViewClient(new WebClient());
    }

    private void initToolbar() {
        toolbarWareDetail.setNavigationIcon(R.drawable.icon_back);
        toolbarWareDetail.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                showShare();
            }
        });
    }

    private void showShare() {

        Log.i("showShare");

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("商品详情");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWares.getName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(mWares.getImgUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mWares.getImgUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        oks.setSilent(true);

        // 启动分享GUI
        oks.show(this);
    }

    private class WebInterface {

        @JavascriptInterface
        void showDetail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("WebInterface.showDetail");
                    webWareDetail.loadUrl("javascript:showDetail(" + mWares.getId() + ")");
                }
            });
        }

        @JavascriptInterface
        public void buy(int id) {
            Toast.show("购买");
        }

        @JavascriptInterface
        public void addToCart(int id) {
            CartProvider.getInstance().putCartBean(mWares);
            Toast.show("已加入购物车");
        }
    }

    private class WebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webInterface.showDetail();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtils.shutDown(webWareDetail);
    }
}
