package com.choicemmed.ichoice.healthcheck.adddevice.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.adapter.recycleViewAdapter;
import com.choicemmed.ichoice.healthcheck.adddevice.entity.DeviceIconvo;

import java.util.ArrayList;
import java.util.List;

public class DeviceSelectActivity extends BaseActivty implements recycleViewAdapter.recycleViewItemSelectListener {

    RecyclerView recyclerView;

    @Override
    protected int contentViewID() {
        return R.layout.activity_select_device;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.pulse_oximeter), true);
        setLeftBtnFinish();

        List<DeviceIconvo> data = new ArrayList<>();
        Intent intent = getIntent();
        int titleId = intent.getIntExtra("titleId", R.id.rl_add_devices_pulse_oximeter);
        switch (titleId) {
            case R.id.rl_add_devices_pulse_oximeter:
                data.add(new DeviceIconvo("MD300CI218", R.mipmap.add_device_pulse_oximeter_md300ci218, R.mipmap.add_device_pulse_oximeter_md300ci218_positive, R.mipmap.add_device_pulse_oximeter_md300ci218_connect));
                data.add(new DeviceIconvo("MD300CI218R", R.mipmap.add_device_pulse_oximeter_md300ci218, R.mipmap.add_device_pulse_oximeter_md300ci218_positive, R.mipmap.add_device_pulse_oximeter_md300ci218_connect));
                data.add(new DeviceIconvo("OX200", R.mipmap.add_device_pulse_oximeter_ox200, R.mipmap.add_device_pulse_oximeter_ox200_positive, R.mipmap.add_device_pulse_oximeter_ox200_connect));
                data.add(new DeviceIconvo("MD300C208", R.mipmap.add_device_pulse_oximeter_md300c208, R.mipmap.add_device_pulse_oximeter_md300c208_positive, R.mipmap.add_device_pulse_oximeter_md300c208_connect));
                data.add(new DeviceIconvo("MD300C208S", R.mipmap.add_device_pulse_oximeter_md300c208s, R.mipmap.add_device_pulse_oximeter_md300c208s_positive, R.mipmap.add_device_pulse_oximeter_md300c208s_connect));
                data.add(new DeviceIconvo("MD300C228", R.mipmap.add_device_pulse_oximeter_md300c228, R.mipmap.add_device_pulse_oximeter_md300c228_positive, R.mipmap.add_device_pulse_oximeter_md300c228_connect));
                data.add(new DeviceIconvo("MD300C228S", R.mipmap.add_device_pulse_oximeter_md300c228s, R.mipmap.add_device_pulse_oximeter_md300c228s_positive, R.mipmap.add_device_pulse_oximeter_md300c228s_connect));
                data.add(new DeviceIconvo("MD300I-G", R.mipmap.add_device_pulse_oximeter_md300i_g, R.mipmap.add_device_pulse_oximeter_md300i_g_positive, R.mipmap.add_device_pulse_oximeter_md300i_g_connect));
                break;
            default:
        }
        recycleViewAdapter adapter = new recycleViewAdapter(data, this);
        recyclerView = findViewById(R.id.select_device_recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DeviceIconvo deviceIconvo) {
        startActivityNOFinish(DeviceConnectTipActivity.class, deviceIconvo);
    }
}
