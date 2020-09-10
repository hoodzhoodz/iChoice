package com.choicemmed.ichoice.healthcheck.activity.bloodpressure;

import android.os.Bundle;
import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.Constant;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.initalization.activity.MainActivity;
import com.choicemmed.ichoice.profile.activity.WebViewActivity;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.OnClick;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;
/**
*Created by
 * @author Jiangnan
 * @Date 2019/12/10.
*/
public class DeviceSettingbpActivity extends BaseActivty {


    @Override
    protected int contentViewID() {
        return R.layout.activity_device_setting;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.settings), true);
        setLeftBtnFinish();
    }

    @OnClick({R.id.unit_bp,R.id.how_use_bp,R.id.faq_bp,R.id.delete_device_bp})
    public void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.unit_bp:
                ActivityUtils.addActivity(this);
                startActivity(UnitBpActivity.class);
                break;
            case R.id.how_use_bp:
                ActivityUtils.addActivity(this);
                bundle.putString(Constant.TYPE, Constant.BP_HOW_TO_USE);
                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.faq_bp:
                ActivityUtils.addActivity(this);
                bundle.putString(Constant.TYPE, Constant.BP_FAQ);
                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.delete_device_bp:
                MyCenterPopupView centerPopupView = new MyCenterPopupView(this);
                XPopup.get(this).asCustom(centerPopupView).show();
                centerPopupView.setDoublePopup("", getString(R.string.tip_delete)
                        , getResources().getString(R.string.no)
                        , getResources().getString(R.string.yes), new MyCenterPopupView.PositiveClickListener() {
                            @Override
                            public void onPositiveClick() {

                            }
                        }, new MyCenterPopupView.NegativeClickListener() {
                            @Override
                            public void onNegativeClick() {
                                deleteDevice();
                                ActivityUtils.removeAll();
                                startActivity(MainActivity.class);
                            }
                        });

                break;
                default:
        }

    }

    private void deleteDevice() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setBloodPressure(0);
        deviceDisplayOperation.updateDeviceDisplay(deviceDisplay);

        DeviceOperation deviceOperation = new DeviceOperation(this);
        List<DeviceInfo> deviceInfos = deviceOperation.queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.BloodPressure);
        for (DeviceInfo deviceInfo: deviceInfos){
            deviceOperation.deleteDv(deviceInfo);
        }

//        Cbp1k1Operation cbp1k1Operation = new Cbp1k1Operation(this);
//        cbp1k1Operation.deletAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
