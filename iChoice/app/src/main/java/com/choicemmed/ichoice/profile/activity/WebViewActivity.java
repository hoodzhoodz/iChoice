package com.choicemmed.ichoice.profile.activity;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.Constant;
import com.choicemmed.ichoice.framework.utils.WebViewUtil;

import java.util.Locale;

import butterknife.BindView;

public class WebViewActivity extends BaseActivty {
    @BindView(R.id.progressBar_about)
    ProgressBar progressBar;
    @BindView(R.id.webview_about)
    WebView webView;

    @Override
    protected int contentViewID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initialize() {
        String language = Locale.getDefault().getLanguage();
        setLeftBtnFinish();
        WebViewUtil webViewUtil = new WebViewUtil();
        String type = getIntent().getExtras().getString(Constant.TYPE);
        if (type.equals(Constant.TEMP_HOW_TO_USE)) {
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/temperature/cft308/Temprature_HowToUse_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/temperature/cft308/Temprature_HowToUse_en.html");
            }
            setTopTitle(getResources().getString(R.string.how_to_use), true);


        } else if (type.equals(Constant.TEMP_FAQ)) {
            setTopTitle(getResources().getString(R.string.faq), true);
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/temperature/cft308/Temprature_FAQ_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/temperature/cft308/Temprature_FAQ_en.html");
            }

        } else if (type.equals(Constant.DISCLAIMER)) {
            setTopTitle(getResources().getString(R.string.disclaimer), true);
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/disclaimer/Disclaimer_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/disclaimer/Disclaimer_en.html");
            }
        } else if (type.equals(Constant.ABOUT_CHOICEMMED)) {
            setTopTitle(getResources().getString(R.string.about_choicemmed), true);
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/about_us/choicehistory_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/about_us/choicehistory_en.html");
            }
        } else if (type.equals(Constant.BP_HOW_TO_USE)) {
            setTopTitle(getResources().getString(R.string.how_to_use), true);
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/bp/cbp1k1/CBP1K1_instructions_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/bp/cbp1k1/CBP1K1_instructions_en.html");
            }
        } else if (type.equals(Constant.BP_FAQ)) {
            setTopTitle(getResources().getString(R.string.faq), true);
            if (language.contains("zh")) {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/bp/cbp1k1/CBP1K1_FAQ_zh.html");
            } else {
                webViewUtil.ClientWebView(WebViewActivity.this, webView, progressBar, "file:///android_asset/bp/cbp1k1/CBP1K1_FAQ_en.html");
            }
        }
    }
}
