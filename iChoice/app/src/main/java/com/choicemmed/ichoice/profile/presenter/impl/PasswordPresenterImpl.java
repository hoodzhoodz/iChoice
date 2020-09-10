package com.choicemmed.ichoice.profile.presenter.impl;

import android.content.Context;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.profile.presenter.PasswordPresenter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/3 19:12
 * 修改人：114100
 * 修改时间：2019/4/3 19:12
 * 修改备注：
 */
public class PasswordPresenterImpl extends BasePresenterImpl<IBaseView> implements PasswordPresenter {
    private Context context;

    public PasswordPresenterImpl(Context context,IBaseView iBaseView) {
        this.context = context;
        attachView(iBaseView);
    }

    @Override
    public void sendUploadPassword(String accessTokenKey, String oldPassword, String newPassword) {
        StringDialogCallback callback = new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","修改密码返回数据***"+object);
                try {
                    JSONObject json=object.getJSONObject("Data");
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.TOKEN,json.getString("AccessTokenKey"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.EMAIL,json.getString("Email"));
                    IchoiceApplication.getAppData().user.refresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onSuccess();
            }

            @Override
            public void onMessage(String message) {
                mView.onError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.sendUploadPassword(context,accessTokenKey,oldPassword,newPassword,callback);
    }
}
