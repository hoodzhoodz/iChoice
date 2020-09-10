package com.choicemmed.ichoice.healthcheck.activity.bloodpressure;

import android.os.Bundle;
import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.ConnectDeviceActivity;

import butterknife.OnClick;
/**
 *Created by
 * @author  Jiangnan
 * @Date 2019/12/14.
 */
public class DevicesSelectBpActivity extends BaseActivty {

    @Override
    protected int contentViewID() {
        return R.layout.activity_devices_select_bp;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.Blood_Pressure), true);
        setLeftBtnFinish();
    }

    @OnClick({R.id.cbp111,R.id.cbp1K1})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.cbp1K1:
                Bundle bundle = new Bundle();
                bundle.putString("DeviceType", "bp");
                startActivityFinish(ConnectDeviceActivity.class,bundle);
                break;
            case R.id.cbp111:
                IchoiceApplication.singleDialog(getString(R.string.stay), mContext);
            break;
            default:
        }
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
