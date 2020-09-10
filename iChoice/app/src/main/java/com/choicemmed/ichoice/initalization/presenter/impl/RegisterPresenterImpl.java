package com.choicemmed.ichoice.initalization.presenter.impl;

import android.content.Context;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.initalization.presenter.RegisterPresenter;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.initalization.view.IRegisterView;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 12:01
 * 修改人：114100
 * 修改时间：2019/4/1 12:01
 * 修改备注：
 */
public class RegisterPresenterImpl extends BasePresenterImpl<IRegisterView> implements RegisterPresenter {
    private static final String TAG = "RegisterPresenterImpl";
    private Context context;

    public RegisterPresenterImpl(Context context,IRegisterView iRegisterView) {
        this.context = context;
        attachView(iRegisterView);
    }

    @Override
    public void sendValidCodeRequest(String account, String codeType) {
        StringDialogCallback callback=new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","获取验证码成功****"+object);
                mView.onValidCodeSuccess();
            }

            @Override
            public void onMessage(String message) {
                mView.onValidCodeError(message);
            }

            @Override
            public void onError(Response<String> response) {
                mView.onValidCodeError(context.getString(R.string.not_net));
//                LogUtils.d(TAG,"response.message "+response.message()+"\n"+response.body());
                Debugger.handleError(response);
            }
        };
        HttpUtils.sendValidCodeRequest(context,account,codeType,callback);
    }

    @Override
    public void sendRegisterRequset(String email_phone,String validcode, String password, String subscribe) {
        StringDialogCallback callback=new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                mView.onRegisterSuccess();
            }

            @Override
            public void onMessage(String message) {
                mView.onRegisterError(message);
            }

            @Override
            public void onError(Response<String> response) {
                mView.onRegisterError(context.getString(R.string.not_net));
                Debugger.handleError(response);
            }
        };
        HttpUtils.sendRegisterRequset(context,email_phone,validcode,password,subscribe,callback);
    }

    @Override
    public void sendLoginRequest(String account, String password) {
        StringDialogCallback callback=new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","登陆返回数据*****"+object);
                try {
                    JSONObject json=object.getJSONObject("Data");
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.TOKEN,json.getString("AccessTokenKey"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.EMAIL,json.getString("Email"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.IS_LOGIN,false);
                    IchoiceApplication.getAppData().user.refresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onLoginSuccess();
            }

            @Override
            public void onMessage(String message) {
                mView.onLoginError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.sendLoginRequest(context,account,password,callback);
    }
}
