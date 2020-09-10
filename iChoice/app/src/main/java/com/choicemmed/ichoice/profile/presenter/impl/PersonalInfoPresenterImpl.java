package com.choicemmed.ichoice.profile.presenter.impl;

import android.content.Context;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.profile.presenter.PersonalInfoPresenter;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/3 10:21
 * 修改人：114100
 * 修改时间：2019/4/3 10:21
 * 修改备注：
 */
public class PersonalInfoPresenterImpl extends BasePresenterImpl<IBaseView> implements PersonalInfoPresenter {
    private Context context;

    public PersonalInfoPresenterImpl(Context context,IBaseView iBaseView) {
        this.context = context;
        attachView(iBaseView);
    }

    @Override
    public void sendPersonalInfoRequest(String accessTokenKey, String gender, String birthday, String height, String weight, String firstName, String familyName) {
        StringDialogCallback callback = new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","上传基本数据返回值***"+object);
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
        HttpUtils.sendPersonalInfoRequest(context,accessTokenKey,gender,birthday,height,weight,firstName,familyName,callback);
    }
}
