package com.choicemmed.ichoice.healthcheck.activity.bloodpressure;

import android.content.Intent;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.widget.SwitchButton;
import com.choicemmed.ichoice.healthcheck.db.UserOperation;

import butterknife.BindView;
import pro.choicemmed.datalib.UserProfileInfo;

public class UnitBpActivity extends BaseActivty {

    @BindView(R.id.mmhg_switch)
    SwitchButton mmHg_switch;

    @BindView(R.id.kpa_switch)
    SwitchButton kpa_switch;
    Intent intent = new Intent("UnitSelect");
    UserOperation userOperation = new UserOperation(this);
    @Override
    protected int contentViewID() {
        return R.layout.activity_unit_bp;

    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.unit), true);
        setLeftBtnFinish();
        initView();
    }

    private void initView() {
        final UserProfileInfo userProfileInfo = IchoiceApplication.getAppData().userProfileInfo;
        if (userProfileInfo.getIsUnit()){
            mmHg_switch.setChecked(true);
        }else {
            kpa_switch.setChecked(true);
        }
        mmHg_switch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    kpa_switch.setChecked(false);
                    userProfileInfo.setIsUnit(true);
                    userOperation.upDateUser(userProfileInfo);
                    IchoiceApplication.getAppData().userProfileInfo = userProfileInfo;
                    intent.putExtra("isunit",true);
                    sendBroadcast(intent);

                }else {
                    kpa_switch.setChecked(true);
                }
            }
        });
        kpa_switch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    mmHg_switch.setChecked(false);
                    userProfileInfo.setIsUnit(false);
                    userOperation.upDateUser(userProfileInfo);
                    IchoiceApplication.getAppData().userProfileInfo = userProfileInfo;
                    intent.putExtra("isunit",false);
                    sendBroadcast(intent);
                }else {
                    mmHg_switch.setChecked(true);
                }
            }
        });
    }


}
