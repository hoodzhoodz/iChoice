package com.choicemmed.ichoice.initalization.presenter.impl;

import android.content.Context;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.initalization.entity.GsUserProfileEntity;
import com.choicemmed.ichoice.initalization.presenter.LoginPresenter;
import com.choicemmed.ichoice.initalization.view.ILoginView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import pro.choicemmed.datalib.UserProfileInfo;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 16:48
 * 修改人：114100
 * 修改时间：2019/4/1 16:48
 * 修改备注：
 */
public class LoginPresenterImpl extends com.choicemmed.ichoice.framework.presenter.BasePresenterImpl<ILoginView> implements LoginPresenter {
    private Context context;
    public LoginPresenterImpl(Context context, ILoginView iLoginView) {
        this.context=context;
        attachView(iLoginView);
    }

    @Override
    public void sendLoginRequest(String account, String password) {
        com.choicemmed.ichoice.framework.callback.StringDialogCallback callback=new com.choicemmed.ichoice.framework.callback.StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","登陆返回数据*****"+object);
                try {
                    JSONObject json=object.getJSONObject("Data");
                    com.choicemmed.ichoice.framework.utils.PreferenceUtil.getInstance().putPreferences(ApiConfig.TOKEN,json.getString("AccessTokenKey"));
                    com.choicemmed.ichoice.framework.utils.PreferenceUtil.getInstance().putPreferences(ApiConfig.EMAIL,json.getString("Email"));
                    com.choicemmed.ichoice.framework.utils.PreferenceUtil.getInstance().putPreferences(ApiConfig.IS_LOGIN,true);
                    IchoiceApplication.getAppData().user.refresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onLoginSuccess(IchoiceApplication.getAppData().user.getToken());
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

    @Override
    public void getetUserProfile(String accessTokenKey) {
        StringDialogCallback callback = new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","获取概要信息返回值****"+object);
                GsUserProfileEntity entity = null;
                try{
                    entity= new Gson().fromJson(String.valueOf(object),GsUserProfileEntity.class);
                    if (entity !=null){
                        IchoiceApplication.getAppData().gsUserProfileEntity = entity;
                        GsUserProfileEntity.DataBean bean=entity.getData();
                        String birthDay="1980-01-01";
                        if (!bean.getBirthday().equals("") && bean.getBirthday()!=null){
                            try {
                                birthDay=FormatUtils.parseDateFromServer(bean.getBirthday());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        UserProfileInfo userProfileInfo = new UserProfileInfo(bean.getId(),bean.getUserId(),bean.getEmail(),bean.getPassword(),bean.getGender(),birthDay,
                                bean.getLengthShowUnitSystem(),bean.getHeight(),bean.getWeightShowUnitSystem(),bean.getWeight(),bean.getStartWeight(),bean.getTimeZoneId(),
                                bean.getTimeZoneName(),bean.getTimeDifference(),bean.getCountryId(),bean.getCountryName(),bean.getShortName(),bean.getCity(),bean.getPostalCode(),
                                bean.getAboutMe(),bean.getTimeSystem(),bean.getTemperatureUnit(),bean.getStrideLength(),bean.getRunningStrideLength(),bean.getFullName(),bean.getNickName(),
                                bean.getPhotoExtension(),bean.getPhoto100x100(),bean.getPhoto200x200(),bean.getPhoto500x400(),bean.getProfileItemVisitLevelSeries(),bean.getUserGroupId(),
                                bean.getUserStatus(),bean.getSignupDateTime(),bean.getSignupIP(),bean.getLoginTimes(),bean.getLastLoginDateTime(),bean.getLastLoginIP(),bean.getBPUnitSystem(),
                                bean.isValidEmail(),bean.getSource(),bean.getRecordCount(),bean.getUtcNow(),bean.getUnreadMessageNum(),bean.getSysMessageModellist(),bean.getUpdateWeight(),
                                bean.getDueDate(),bean.getGenstationDate(),bean.getFirstName(),bean.getFamilyName(),bean.getRegType(),bean.getPhone(),bean.getLinkId(),bean.isDelState(),bean.getLastUpdateTime(),true);
                        IchoiceApplication.getInstance().getDaoSession().insertOrReplace(userProfileInfo);
                        IchoiceApplication.getAppData().userId = bean.getUserId();
                        IchoiceApplication.getAppData().userProfileInfo = userProfileInfo;
                        IchoiceApplication.getAppData().userEmail = bean.getEmail();
                        mView.onUserProfileSuccess();
                    }

                }catch (JsonSyntaxException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onMessage(String message) {
                mView.onUserProfileError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.getetUserProfile(context,accessTokenKey,callback);
    }
}
