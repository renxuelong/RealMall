package com.renxl.realmall.utils;

import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by renxl
 * On 2017/5/9 18:11.
 */

public class WebViewUtils {

    public static void shutDown(WebView webView) {
        if (webView == null) return;
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.removeAllViews();
        webView.setTag(null);
        webView.clearHistory();
        webView.destroy();
    }
}
