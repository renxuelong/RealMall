package com.renxl.realmall.feature.wares_detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.feature.cart.CartProvider;
import com.renxl.realmall.feature.category.Wares;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WareDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WARES = "wares";

    @BindView(R.id.toolbar_ware_detail)
    ToolBar toolbarWareDetail;
    @BindView(R.id.web_ware_detail)
    WebView webWareDetail;

    private int id;
    private Wares mWares;
    private WebInterface webInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_detail);
        ButterKnife.bind(this);

        mWares = getIntent().getParcelableExtra(EXTRA_WARES);
        if (mWares == null) finish();

        initToolbar();
        initWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
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
        toolbarWareDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
