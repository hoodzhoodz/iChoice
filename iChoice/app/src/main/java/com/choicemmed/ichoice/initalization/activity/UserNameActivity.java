package com.choicemmed.ichoice.initalization.activity;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.initalization.config.ApiConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaofang on 2019/1/22.
 *  个人信息设置 - 姓名
 */

public class UserNameActivity extends BaseActivty {
    @BindView(R.id.input_firstname)
    TextInputLayout inputFirstname;
    @BindView(R.id.input_familyname)
    TextInputLayout inputFamilyname;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    @Override
    protected int contentViewID() {
        return R.layout.activity_set_name;
    }

    @Override
    protected void initialize() {
        setLeftBtnFinish();
        setTopTitle(getResources().getString(R.string.new_user), true);

    }


    @OnClick(R.id.btn_continue)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_continue:
                String firstName = inputFirstname.getEditText().getText().toString();
                String familyName = inputFamilyname.getEditText().getText().toString();
                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(familyName)){
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.FIRSTNAME,firstName);
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.FAMILYNAME,familyName);
                    IchoiceApplication.getAppData().user.refresh();
                    ActivityUtils.addActivity(this);
                    startActivity(GenderActivity.class);
                }else {
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
                }

                break;
        }
    }
}
