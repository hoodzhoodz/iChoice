package com.choicemmed.ichoice.framework.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * 项目名称：iChoice
 * 类描述：webView配置类
 * 创建人：114100
 * 创建时间：2019/4/8 10:01
 * 修改人：114100
 * 修改时间：2019/4/8 10:01
 * 修改备注：
 */
public class WebViewUtil {
    @SuppressLint("JavascriptInterface")
    public void ClientWebView(final Activity mactive, WebView webView, final ProgressBar progressBar, String url){
//        WebSettings webSettings = xCont.getSettings();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setAllowFileAccess(true);// 是否可访问本地文件，默认值 true
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//动态更新内容
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setSupportZoom(true);// 是否支持缩放
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具
        webSettings.setUseWideViewPort(true);//扩大比例的缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //自适应屏幕
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 是否可用Javascript(window.open)打开窗口，默认值 false
        webView.setWebChromeClient(new WebChromeClient());// 设置可现实js的alert弹窗
//        mWebView.addJavascriptInterface(new DemoJavaScriptInterface((Activity)xCont,  mWebView,llTitleItem), "android");


        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }});

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                WebResourceResponse response = null;
                response =  super.shouldInterceptRequest(webView, webResourceRequest);
//                String[] namelist = webResourceRequest.getUrl().toString().split("/");
//                return getResponse(response,namelist,mactive);
                return response;

            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                WebResourceResponse response = null;
                response =  super.shouldInterceptRequest(webView, s);
                String[] namelist = s.split("/");
//                return getResponse(response,namelist,mactive);
                return response;
            }
        });
        webView.loadUrl(url);
    }
}
