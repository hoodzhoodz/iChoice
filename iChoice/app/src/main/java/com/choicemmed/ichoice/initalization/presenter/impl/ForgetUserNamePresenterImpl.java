package com.choicemmed.ichoice.initalization.presenter.impl;

import android.content.Context;

import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.initalization.presenter.ForgetUserNamePresenter;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 17:59
 * 修改人：114100
 * 修改时间：2019/4/1 17:59
 * 修改备注：
 */
public class ForgetUserNamePresenterImpl extends BasePresenterImpl<IBaseView> implements ForgetUserNamePresenter {
    private Context context;

    public ForgetUserNamePresenterImpl(Context context,IBaseView iBaseView) {
        this.context = context;
        attachView(iBaseView);
    }

    @Override
    public void sendValidCodeRequest(String account, String codeType) {
        StringDialogCallback callback=new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
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
        HttpUtils.sendValidCodeRequest(context,account,codeType,callback);
    }
}
