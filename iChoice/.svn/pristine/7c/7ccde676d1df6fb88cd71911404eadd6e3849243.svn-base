package com.choicemmed.ichoice.initalization.activity;

import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.choicemmed.common.IdcardUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.JudgeUtils;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.initalization.view.IRegisterView;
import com.choicemmed.ichoice.initalization.presenter.impl.RegisterPresenterImpl;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaofang on 2019/1/22.
 * 注册界面
 */

public class RegisterActivity extends BaseActivty implements IRegisterView {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.input_email)
    TextInputLayout inputEmail;
    @BindView(R.id.btn_send_code)
    Button btnSendCode;
    @BindView(R.id.input_code)
    TextInputLayout inputCode;
    @BindView(R.id.input_new_pwd)
    TextInputLayout inputNewPwd;
    @BindView(R.id.input_confirm_pwd)
    TextInputLayout inputConfirmPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_register_emai_phone)
    EditText etEmian_Phone;
    @BindView(R.id.et_register_code)
    EditText etCode;
    @BindView(R.id.et_register_password)
    EditText etPassword;
    @BindView(R.id.et_register_confirm_password)
    EditText etConfirm_Password;

    private int regtype=0;                  //注册类型 1：邮箱 2：手机号
    private int time = 61; //秒
    private TimeCount timeCount;
    private String eamin_phone;
    private String password;

    private RegisterPresenterImpl registerPresenter;
    @Override
    protected int contentViewID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.new_user), true);
        setLeftBtnFinish();

        registerPresenter=new RegisterPresenterImpl(this,this);
        timeCount = new TimeCount(time * 1000, 1000);

    }

    @OnClick({R.id.btn_register, R.id.btn_send_code})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_register:
                registerNext();
//                startActivityFinish(AvatarActivity.class);
                break;
            case R.id.btn_send_code:

                String language = Locale.getDefault().getLanguage();
                if (language.contains("zh") && !IdcardUtils.isMobileNO(etEmian_Phone.getText().toString().trim()) && !JudgeUtils.isEmail(etEmian_Phone.getText().toString().trim())) {
                    MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                    return;
                } else if (!language.contains("zh") && !JudgeUtils.isEmail(etEmian_Phone.getText().toString().trim())) {
                    MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                    return;
                }

                getMessageCode();
                break;

        }
    }

    /**
     * 获取验证码
     */
    private void getMessageCode() {
        String phoneOrEmail = etEmian_Phone.getText().toString().trim();
        if (!PermissionsUtils.isNetworkConnected(this)) {
            MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
            return;
        }

        if (IdcardUtils.validateEmail(phoneOrEmail)){
            registerPresenter.sendValidCodeRequest(phoneOrEmail,1+"");

        }else if (IdcardUtils.isMobileNO(phoneOrEmail)){
            registerPresenter.sendValidCodeRequest(phoneOrEmail,1+"");

        }else {
            MethodsUtils.showErrorTip(this,getString(R.string.code_fail));
        }
    }

    /**
     * 注册
     */
    private void registerNext() {
        eamin_phone = etEmian_Phone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        String confirm_password= etConfirm_Password.getText().toString().trim();
        if (!TextUtils.isEmpty(etEmian_Phone.getText()) && !TextUtils.isEmpty(etCode.getText()) &&
                !TextUtils.isEmpty(etPassword.getText()) && !TextUtils.isEmpty(etConfirm_Password.getText())){

            String language = Locale.getDefault().getLanguage();
            if (language.contains("zh") && !IdcardUtils.isMobileNO(etEmian_Phone.getText().toString().trim()) && !JudgeUtils.isEmail(etEmian_Phone.getText().toString().trim())) {
                MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                return;
            } else if (!language.contains("zh") && !JudgeUtils.isEmail(etEmian_Phone.getText().toString().trim())) {
                MethodsUtils.showErrorTip(this, getString(R.string.tip_email_incorrect));
                return;
            }


            if (!JudgeUtils.isPassword(etPassword.getText().toString())){
                MethodsUtils.showErrorTip(this,getString(R.string.tip_password_not));
                return;
            }

            if (!JudgeUtils.isCode(code)){
                MethodsUtils.showErrorTip(this,getString(R.string.code_error));
                return;
            }

            if (!password.equals(confirm_password)) {
                MethodsUtils.showErrorTip(this, getString(R.string.inconsistent_password));
                return;
            }

            if (!PermissionsUtils.isNetworkConnected(this)) {
                MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
                return;
            }
            registerPresenter.sendRegisterRequset(eamin_phone,code,password,"1");
        }else {
            MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
        }

    }

    @Override
    public void onValidCodeSuccess() {
        timeCount.start();
        if (IdcardUtils.isMobileNO(etEmian_Phone.getText().toString().trim())) {
            MethodsUtils.showErrorTip(this, getString(R.string.send_phone));
        } else {
            MethodsUtils.showErrorTip(this, getString(R.string.send_email));
        }

    }

    @Override
    public void onValidCodeError(String error) {
        Log.d("onValidCodeError",error);
        if (error.contains("Account has already exists")) {
            error = getString(R.string.account_has_already_exists);
        }
        MethodsUtils.showErrorTip(this,error);
    }

    @Override
    public void onRegisterSuccess() {
        registerPresenter.sendLoginRequest(eamin_phone,password);
    }

    @Override
    public void onRegisterError(String error) {
        Log.d("onRegisterError",error);
        if (error.equals("Account_ValidCodeTimeout")){
            MethodsUtils.showErrorTip(this,getString(R.string.code_timeout));
        }
        if (error.equals("Account_ValidCodeFalse")){
            MethodsUtils.showErrorTip(this,getString(R.string.code_error));
        }
        if (error.equals("Account_NoValidCode")){
            MethodsUtils.showErrorTip(this,getString(R.string.code_not));
        }
        if (error.equals(getString(R.string.not_net))) {
            MethodsUtils.showErrorTip(this, getString(R.string.not_net));
        }

    }

    @Override
    public void onLoginSuccess() {
        startActivityFinish(AvatarActivity.class);
    }

    @Override
    public void onLoginError(String msg) {
        Log.d("onLoginError",msg);
        MethodsUtils.showErrorTip(this,msg);
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {// 计时完毕时触发
            btnSendCode.setText(getString(R.string.rsend_code));
            btnSendCode.setEnabled(true);

        }
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btnSendCode.setText(millisUntilFinished / 1000 + getString(R.string.send_code_time));
            btnSendCode.setEnabled(false);
        }
    }

}
