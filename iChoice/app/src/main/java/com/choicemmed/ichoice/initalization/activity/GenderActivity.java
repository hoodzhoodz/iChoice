package com.choicemmed.ichoice.initalization.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by gaofang on 2019/1/15.
 * 个人信息设置 - 性别
 */

public class GenderActivity extends BaseActivty {

    @BindView(R.id.img_man)
    ImageView imgMan;
    @BindView(R.id.img_woman)
    ImageView imgWoman;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.tv_woman)
    TextView tvWoman;

    boolean isMan = false,isWoman = false;

    @Override
    protected int contentViewID() {
        return R.layout.activity_gender;
    }

    @Override
    protected void initialize() {
        setLeftBtnFinish();
        setTopTitle(getResources().getString(R.string.new_user), true);
        setSelected();
    }

    private void setSelected() {
        imgMan.setSelected(true);
        imgWoman.setSelected(true);
    }

    private void setNotSelected() {
        imgMan.setSelected(false);
        imgWoman.setSelected(false);
        tvMan.setSelected(false);
        tvWoman.setSelected(false);
    }
    @OnClick({R.id.btn_continue,R.id.img_man,R.id.img_woman})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_man:
                setManSelect();
                break;
            case R.id.img_woman:
                setWomanSelect();
                break;
            case R.id.btn_continue:
                if (isMan == false && isWoman == false) {
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
                    return;

                }
                if (isMan){
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.GENDER,"2");
                    IchoiceApplication.getAppData().user.refresh();
                }
                if (isWoman){
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.GENDER,"3");
                    IchoiceApplication.getAppData().user.refresh();
                }
                ActivityUtils.addActivity(this);
                startActivity(PersonalInfoActivity.class);
                break;

        }
    }

    private void setManSelect() {
        isMan =true;
        isWoman = false;
        setNotSelected();
        imgMan.setSelected(true);
        tvMan.setSelected(true);
    }
    private void setWomanSelect() {
        isWoman =true;
        isMan =false;
        setNotSelected();
        imgWoman.setSelected(true);
        tvWoman.setSelected(true);
    }
}
