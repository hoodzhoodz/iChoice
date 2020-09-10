package com.choicemmed.ichoice.healthcheck.adddevice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.adddevice.activity.ox.SearchDeviceOXActivity;
import com.choicemmed.ichoice.healthcheck.adddevice.entity.DeviceIconvo;

import butterknife.OnClick;

public class DeviceConnectTipActivity extends BaseActivty {
    @Override
    protected int contentViewID() {
        return R.layout.activity_device_connect_tip;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.pulse_oximeter), true);
        setLeftBtnFinish();

        ImageView imageView = findViewById(R.id.activity_device_connect_tip_imageView);
        Intent intent = getIntent();
        DeviceIconvo deviceIconvo = (DeviceIconvo) intent.getSerializableExtra("deviceIconvo");
        imageView.setImageResource(deviceIconvo.getIconImageConnect());
    }

    @OnClick({R.id.activity_device_connect_tip_button})
    public void onClick(View view) {
        Intent intent = getIntent();
        DeviceIconvo deviceIconvo = (DeviceIconvo) intent.getSerializableExtra("deviceIconvo");
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, deviceIconvo.getIconName());
        startActivityFinish(SearchDeviceOXActivity.class, bundle);

    }
}
