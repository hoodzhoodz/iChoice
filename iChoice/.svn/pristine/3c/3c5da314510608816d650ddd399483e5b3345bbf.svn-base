package com.choicemmed.ichoice.profile.activity;

import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.initalization.activity.MainActivity;

import butterknife.OnClick;

/**
 * 项目名称：iChoice
 * 类描述：设置界面
 * 创建人：114100
 * 创建时间：2019/4/3 17:51
 * 修改人：114100
 * 修改时间：2019/4/3 17:51
 * 修改备注：
 */
public class SettingsActivity extends BaseActivty {

    @Override
    protected int contentViewID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.settings), true);
        setLeftBtnFinish();
    }
    @OnClick({R.id.stv_setting_password,R.id.stv_setting_information})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.stv_setting_password:
                startActivity(PasswordActivity.class);
                break;
            case R.id.stv_setting_information:
                startActivity(InformationActivity.class);
                break;
                default:
        }
    }
}
