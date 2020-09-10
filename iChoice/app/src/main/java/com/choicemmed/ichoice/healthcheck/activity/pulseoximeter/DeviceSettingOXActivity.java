package com.choicemmed.ichoice.healthcheck.activity.pulseoximeter;

import android.view.View;
import android.widget.LinearLayout;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OxWorkingModeDialogFragment;
import com.choicemmed.ichoice.initalization.activity.MainActivity;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;

public class DeviceSettingOXActivity extends BaseActivty implements OxWorkingModeDialogFragment.Listerner {
    @BindView(R.id.ll_work_mode)
    LinearLayout ll_work_mode;
    private OxWorkingModeDialogFragment dialogFragment;
    @Override
    protected int contentViewID() {
        return R.layout.activity_device_setting_c208;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.settings), true);
        setLeftBtnFinish();
        if (!getIntent().getBooleanExtra("work_mode", true)) {
            ll_work_mode.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.delete_device_c208, R.id.how_to_use, R.id.how_to_faq, R.id.tv_work_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_device_c208:
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
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.TYPE, Constant.TEMP_HOW_TO_USE);
//                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.how_to_faq:
//                Bundle bundle1 = new Bundle();
//                bundle1.putString(Constant.TYPE, Constant.TEMP_FAQ);
//                startActivity(WebViewActivity.class, bundle1);
                break;
            case R.id.tv_work_mode:
                dialogFragment = new OxWorkingModeDialogFragment();
                dialogFragment.setCancelable(false);
                dialogFragment.setListerner(this);
                dialogFragment.show(getSupportFragmentManager(), "DeviceSettingOXActivity");
                break;
            default:
        }
    }

    private void deleteDevice() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setPulseOximeter(0);
        deviceDisplayOperation.updateDeviceDisplay(deviceDisplay);

        DeviceOperation deviceOperation = new DeviceOperation(this);
        List<DeviceInfo> deviceInfos = deviceOperation.queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.PulseOximeter);
        for (DeviceInfo deviceInfo : deviceInfos) {
            deviceOperation.deleteDv(deviceInfo);
        }

//        List<CFT308Data> list = getDaoSession(this).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).build().list();
//        if (!list.isEmpty()) {
//            getDaoSession(this).getCFT308DataDao().deleteInTx(list);
//        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void change() {
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
        finish();
    }
}
