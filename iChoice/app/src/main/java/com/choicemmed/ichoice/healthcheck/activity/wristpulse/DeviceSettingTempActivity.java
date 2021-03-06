package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperTextView;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.Constant;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.BottomMenuFragment;
import com.choicemmed.ichoice.initalization.activity.MainActivity;
import com.choicemmed.ichoice.profile.activity.WebViewActivity;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;


public class DeviceSettingTempActivity extends BaseActivty implements BottomMenuFragment.Changed {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.tv_unit)
    SuperTextView tv_unit;

    @Override
    protected int contentViewID() {
        return R.layout.activity_device_setting_temp;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.settings), true);
        setLeftBtnFinish();
        ActivityUtils.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_unit.setRightString(Integer.parseInt(String.valueOf(SharePreferenceUtil.get(this, "temp_unit", 1))) == 1 ? getResources().getString(R.string.temp_unit) : getResources().getString(R.string.temp_unit1));
    }

    @OnClick({R.id.delete_device_wpo, R.id.tv_unit, R.id.how_to_use, R.id.how_to_faq})
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

            case R.id.tv_unit:
                BottomMenuFragment bottomMenuFragment = new BottomMenuFragment(this);
                bottomMenuFragment.show(getFragmentManager(), "BottomMenuFragment");
                break;
            case R.id.how_to_use:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.TYPE, Constant.TEMP_HOW_TO_USE);
                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.how_to_faq:
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constant.TYPE, Constant.TEMP_FAQ);
                startActivity(WebViewActivity.class, bundle1);
                break;
            default:
        }
    }

    private void deleteDevice() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setThermometer(0);
        deviceDisplayOperation.updateDeviceDisplay(deviceDisplay);

        DeviceOperation deviceOperation = new DeviceOperation(this);
        List<DeviceInfo> deviceInfos = deviceOperation.queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.Thermometer);
        for (DeviceInfo deviceInfo : deviceInfos) {
            deviceOperation.deleteDv(deviceInfo);
        }

//        List<CFT308Data> list = getDaoSession(this).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).build().list();
//        if (!list.isEmpty()) {
//            getDaoSession(this).getCFT308DataDao().deleteInTx(list);
//        }
    }

    @Override
    public void change() {
        tv_unit.setRightString(Integer.parseInt(String.valueOf(SharePreferenceUtil.get(this, "temp_unit", 1))) == 1 ? getResources().getString(R.string.temp_unit) : getResources().getString(R.string.temp_unit1));
    }

    @Override
    public void onBackPressed() {
//        startActivity(InfraredThermometerActivity.class, null);
//        overridePendingTransition(R.anim.activity_no,R.anim.activity_out);
        finish();

    }

    @Override
    public void finish() {
        super.finish();
    }
}
