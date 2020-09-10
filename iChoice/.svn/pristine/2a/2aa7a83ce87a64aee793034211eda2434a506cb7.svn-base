package com.choicemmed.ichoice.initalization.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v4.math.MathUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.choicemmed.common.ToastUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.utils.JudgeUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.initalization.presenter.impl.ForgetPwdPresenterImpl;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.view.IBaseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaofang on 2019/1/23.
 * 忘记密码-设置新密码
 */

public class ForgetPwdActivity extends BaseActivty implements IBaseView {
    @BindView(R.id.input_code)
    TextInputLayout inputCode;
    @BindView(R.id.input_new_pwd)
    TextInputLayout inputNewPwd;
    @BindView(R.id.input_confirm_pwd)
    TextInputLayout inputConfirmPwd;
    @BindView(R.id.btn_pwd_commit)
    Button mBtnPwdCommit;

    private String email_phone;
    private ForgetPwdPresenterImpl forgetPwdPresenter;

    @Override
    protected int contentViewID() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.forgot_password), true);
        setLeftBtnFinish();
        email_phone= getIntent().getStringExtra("email_phone");
        forgetPwdPresenter=new ForgetPwdPresenterImpl(this,this);

    }


    @OnClick(R.id.btn_pwd_commit)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_pwd_commit:
                String code=inputCode.getEditText().getText().toString();
                String password=inputNewPwd.getEditText().getText().toString();
                String confirmPwd = inputConfirmPwd.getEditText().getText().toString();
                if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPwd)){
                    if (!PermissionsUtils.isNetworkConnected(this)) {
                        MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
                        return;
                    }
                    if (!JudgeUtils.isCode(code)){
                        MethodsUtils.showErrorTip(this,getString(R.string.code_error));
                        return;
                    }
                    if (password.equals(confirmPwd)){
                        if (JudgeUtils.isPassword(confirmPwd)){
                            forgetPwdPresenter.sendForgetPwdRequest(email_phone,code,password);
                        }else {
                            MethodsUtils.showErrorTip(this,getString(R.string.tip_password_not));
                        }
                    }else {
                        MethodsUtils.showErrorTip(this,getString(R.string.inconsistent_password));
                    }

                }else {
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
                }

                break;
        }
    }

    @Override
    public void onSuccess() {
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onError(String msg) {
        Log.d("ForgetPwdActivity",msg);
        if (msg.equals("Account_ValidCodeTimeout")){
            MethodsUtils.showErrorTip(this,getString(R.string.code_timeout));
        }
        if (msg.equals("Account_ValidCodeFalse")){
            MethodsUtils.showErrorTip(this,getString(R.string.code_error));
        }

    }
}
