package com.choicemmed.ichoice.profile.fragment;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.WebViewUtil;

import butterknife.BindView;


public class AboutFragment extends BaseFragment {
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
        WebViewUtil webViewUtil = new WebViewUtil();
        webViewUtil.ClientWebView(getActivity(), webView, progressBar, "file:///android_asset/Temprature_HowToUse_en.html.html");
    }
}
