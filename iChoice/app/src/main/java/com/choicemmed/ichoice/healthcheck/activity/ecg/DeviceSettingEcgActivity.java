package com.choicemmed.ichoice.healthcheck.activity.ecg;

import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperTextView;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.Constant;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.BottomMenuFragment;
import com.choicemmed.ichoice.initalization.activity.MainActivity;
import com.choicemmed.ichoice.profile.activity.WebViewActivity;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;


public class DeviceSettingEcgActivity extends BaseActivty {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected int contentViewID() {
        return R.layout.activity_device_setting_ecg;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.settings), true);
        setLeftBtnFinish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.delete_device_wpo, R.id.how_to_use, R.id.how_to_faq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_device_wpo:
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

            case R.id.how_to_use:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.TYPE, Constant.ECG_HOWTOUSE);
                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.how_to_faq:
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constant.TYPE, Constant.ECG_FAQ);
                startActivity(WebViewActivity.class, bundle1);
                break;
            default:
        }
    }

    private void deleteDevice() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setEcg(0);
        deviceDisplayOperation.updateDeviceDisplay(deviceDisplay);

        DeviceOperation deviceOperation = new DeviceOperation(this);
        List<DeviceInfo> deviceInfos = deviceOperation.queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.Ecg);
        for (DeviceInfo deviceInfo : deviceInfos) {
            deviceOperation.deleteDv(deviceInfo);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
