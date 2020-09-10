package com.choicemmed.ichoice.initalization.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.choicemmed.common.IdcardUtils;
import com.choicemmed.common.ToastUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.utils.JudgeUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.initalization.presenter.impl.ForgetUserNamePresenterImpl;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.lxj.xpopup.XPopup;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaofang on 2019/1/22.
 * 忘记密码 -- 用户名发送验证码
 */

public class ForgetUserNameActivity extends BaseActivty implements IBaseView {
    @BindView(R.id.input_email)
    TextInputLayout inputEmail;
    @BindView(R.id.btn_send)
    Button btnSend;

    private ForgetUserNamePresenterImpl forgetUserNamePresenter;

    @Override
    protected int contentViewID() {
        return R.layout.activity_forget_username;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.forgot_password),true);
        setLeftBtnFinish();
        forgetUserNamePresenter = new ForgetUserNamePresenterImpl(this,this);
    }


    @OnClick(R.id.btn_send)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_send:
                String userName = inputEmail.getEditText().getText().toString();
                String language = Locale.getDefault().getLanguage();
                if (language.contains("zh") && !IdcardUtils.isMobileNO(userName) && !JudgeUtils.isEmail(userName)) {
                    MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                    return;
                } else if (!language.contains("zh") && !JudgeUtils.isEmail(userName)) {
                    MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                    return;
                }

                if (userName.isEmpty()){
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
                    return;
                }

                if (!PermissionsUtils.isNetworkConnected(this)) {
                    MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
                    return;
                }
                    forgetUserNamePresenter.sendValidCodeRequest(userName,2+"");

                break;
        }
    }

    @Override
    public void onSuccess() {
        if (IdcardUtils.isMobileNO(inputEmail.getEditText().getText().toString().trim())) {
            showSuccessTip(getString(R.string.send_phone));
        } else {
            showSuccessTip(getString(R.string.send_email));
        }
    }

    @Override
    public void onError(String msg) {
        Log.d("ForgetUserNameActivity",msg);
        if (msg.contains("Email address does not exist")) {
            msg = getString(R.string.Email_address_does_not_exist);
        }
        MethodsUtils.showErrorTip(this,msg);
    }

    private  void showSuccessTip(String msg) {
        MyCenterPopupView centerPopupView = new MyCenterPopupView(this);
        XPopup.get(this).asCustom(centerPopupView).show();
        centerPopupView.setSinglePopup("", msg
                , getResources().getString(R.string.ok)
                , new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Bundle bundle = new Bundle();
                        bundle.putString("email_phone",inputEmail.getEditText().getText().toString());
                        startActivity(ForgetPwdActivity.class,bundle);
                    }
                });
    }
}
