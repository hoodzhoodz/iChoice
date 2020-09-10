package com.choicemmed.ichoice.healthcheck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.bloodpressure.SearchDeviceCbp1k1Activity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceActivity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceW314b4Activity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceW628Activity;
import com.choicemmed.ichoice.framework.utils.DevicesType;

import butterknife.BindView;
import butterknife.OnClick;

import static com.choicemmed.ichoice.framework.utils.DevicesType.A12_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_NEW_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_OLD_DEVICE_NAME;

/**
 *Created by
 * @author  Jiangnan
 * @Date 2010/1/14.
 */
public class ConnectDeviceActivity extends BaseActivty {

    @BindView(R.id.image_device)
    ImageView imageDevice;
    private String deviceType;
    @Override
    protected int contentViewID() {
        return R.layout.activity_connect_device;
    }

    @Override
    protected void initialize() {
        Bundle bundle = getIntent().getExtras();
        deviceType = bundle.getString(DevicesType.Device);
        switch (deviceType){
            case "bp":
                setTopTitle(getResources().getString(R.string.Blood_Pressure), true);
                imageDevice.setImageResource(R.mipmap.add_device_cbp1k1);
                break;
            case "W314B4":
                setTopTitle(getResources().getString(R.string.wrist_pulse_oximeter), true);
                imageDevice.setImageResource(R.mipmap.wp_w314);
                break;
            case "W628":
                setTopTitle(getResources().getString(R.string.wrist_pulse_oximeter), true);
                imageDevice.setImageResource(R.mipmap.wp_w628);
                break;
            case "CFT308":
                setTopTitle(getResources().getString(R.string.infrared_temperature), true);
                imageDevice.setImageResource(R.mipmap.cft308_pic);
                break;
            case A12_DEVICE_NAME:
            case P10_NEW_DEVICE_NAME:
            case P10_OLD_DEVICE_NAME:
                setTopTitle(getResources().getString(R.string.ecg), true);
//                imageDevice.setImageResource(0);
                break;
            default:

        }
        setLeftBtnFinish();
    }



    @OnClick({R.id.btn_connect})
    public void onClick(View v){
      switch (v.getId()) {
          case R.id.btn_connect:
              Bundle bundle = new Bundle();
              bundle.putString(DevicesType.Device, deviceType);
              switch (deviceType){
                  case "bp":
                      startActivityFinish(SearchDeviceCbp1k1Activity.class, bundle);
                      break;
                  case "W314B4":
                      startActivityFinish(SearchDeviceW314b4Activity.class, bundle);
                      break;
                  case "W628":
                      startActivityFinish(SearchDeviceW628Activity.class, bundle);
                      break;
                  case "CFT308":
                  case A12_DEVICE_NAME:
                  case P10_NEW_DEVICE_NAME:
                  case P10_OLD_DEVICE_NAME:
                      startActivityFinish(SearchDeviceActivity.class, bundle);
                      break;
                  default:
              }
              break;
              default:
      }
    }
}
