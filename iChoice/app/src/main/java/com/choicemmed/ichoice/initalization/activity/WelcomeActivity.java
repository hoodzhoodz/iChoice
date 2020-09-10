package com.choicemmed.ichoice.initalization.activity;

import android.os.Handler;

import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.initalization.config.ConstantConfig;
import pro.choicemmed.datalib.UserProfileInfo;

/**
 * Created by gaofang on 2019/1/11.
 */

public class WelcomeActivity extends BaseActivty {

    private Handler mHandler = new Handler();

    @Override
    protected int contentViewID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initialize() {
        setTopTitle("",false);
        boolean isFirstOpen = (boolean) SharePreferenceUtil.get(WelcomeActivity.this, ConstantConfig.FIRST_OPEN, false);
        IchoiceApplication.getAppData().user.init();
        if (!isFirstOpen) {
            startActivity(GuideActivity.class);
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IchoiceApplication.getAppData().user.isLogin()) {
                    UserProfileInfo userProfileInfos = IchoiceApplication.getInstance().getDaoSession().getUserProfileInfoDao().queryBuilder().unique();
                    IchoiceApplication.getAppData().userProfileInfo = userProfileInfos;
                    startActivity(MainActivity.class);
                    finish();
                }else {
                    startActivity(LoginActivity.class);
                    finish();
                }
            }
        }, 3000);

    }


}
