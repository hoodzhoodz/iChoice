package com.choicemmed.ichoice.healthcheck.activity;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.entity.DevicesEntity;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.profile.adapter.DevicesAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;

/**
 * 项目名称：iChoice
 * 类描述：我的设备界面
 * 创建人：114100
 * 创建时间：2019/4/3 17:50
 * 修改人：114100
 * 修改时间：2019/4/3 17:50
 * 修改备注：
 */
public class DevicesActivity extends BaseActivty implements SwipeItemClickListener {
    @BindView(R.id.rc_devices)
    SwipeMenuRecyclerView swipeRecyclerView;
    @BindView(R.id.rl_devices)
    RelativeLayout rlDevices;
    List<DevicesEntity> devicesEntities=new ArrayList<>();
    private DevicesAdapter devicesAdapter;
    @Override
    protected int contentViewID() {
        return R.layout.activity_devices;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.my_devices_list), true);
        setLeftBtnFinish();
        initDevicesEnity();
        initsWipeRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSwip();
    }

    private void refreshSwip() {
        devicesAdapter=new DevicesAdapter(devicesEntities);
//        devicesAdapter.openLoadAnimation();
        devicesAdapter.isFirstOnly(true);
        swipeRecyclerView.setAdapter(devicesAdapter);
        rlDevices.setVisibility(View.VISIBLE);
    }

    private void initsWipeRecyclerView() {
        swipeRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRecyclerView.setSwipeItemClickListener(this);
        swipeRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
    }

    private void initDevicesEnity() {
        DeviceOperation deviceOperation = new DeviceOperation(this);
        List<DeviceInfo> deviceInfos = deviceOperation.queryAllDv(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        for (DeviceInfo deviceInfo:deviceInfos){
            switch (deviceInfo.getDeviceType()){
                case DevicesType.BloodPressure:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.blood_pressure_ico));
                    break;
                case DevicesType.Ecg:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.ecg_ico));
                    break;
                case DevicesType.PulseOximeter:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.pulse_oximeter));
                    break;
                case DevicesType.WristPluseOximeter:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.sleep_monitor));
                    break;
                case DevicesType.Thermometer:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(), R.mipmap.big_thermometer2));
                    break;
                case DevicesType.Scale:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.scale));
                    break;
                case DevicesType.FitnessTracker:
                    devicesEntities.add(setDevicesEntity(deviceInfo.getTypeName(),R.mipmap.fitness_tracker));
                    break;
                default:
            }
        }
//        if (deviceDisplay.getEcg() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.ecg),R.mipmap.ecg_ico));
//        }

//        if (deviceDisplay.getPulseOximeter() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.pulse_oximeter),R.mipmap.pulse_oximeter));
//        }
//
//        if (deviceDisplay.getSleeoMonitor() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.sleep_monitor),R.mipmap.sleep_monitor));
//        }
//
//        if (deviceDisplay.getThermometer() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.therometer),R.mipmap.thermometer));
//        }
//
//        if (deviceDisplay.getScale() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.alpha_and_scale_out),R.mipmap.alpha_and_scale_out));
//        }
//
//        if (deviceDisplay.getFitnessTracker() == 1){
//            devicesEntities.add(setDevicesEntity(getString(R.string.fitness_tracker),R.mipmap.fitness_tracker));
//        }

    }

    private DevicesEntity setDevicesEntity(String name, int imageResId) {
        DevicesEntity entity=new DevicesEntity();
        entity.setName(name);
        entity.setImageResId(imageResId);
        return entity;
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用
     **/
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_62);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(DevicesActivity.this)
                        .setBackground(R.drawable.delete_selector_red)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };
    /**
     * RecyclerView的Item的Menu点击监听
     **/
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int menuPosition = menuBridge.getAdapterPosition();
            Log.d("menuPosition",":"+menuPosition);
            refeshEnity(menuPosition);
        }

    };

    public void refeshEnity(int menuPosition){
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceOperation deviceOperation = new DeviceOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (deviceDisplay == null){
            return;
        }
        DevicesEntity entity =devicesEntities.get(menuPosition);
        if (entity.getName().contains("CBP")) {
            deviceDisplay.setBloodPressure(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.BloodPressure);
        } else if (entity.getName().contains("P10") || entity.getName().contains("A12") || entity.getName().contains("HR1")) {
            deviceDisplay.setEcg(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.Ecg);
        } else if (entity.getName().startsWith(getString(R.string.pulse_qximeter))) {
            deviceDisplay.setPulseOximeter(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.PulseOximeter);
        } else if (entity.getName().contains("W314B4") || entity.getName().contains("W628")) {
            deviceDisplay.setWristPluseOximeter(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.WristPluseOximeter);
        } else if (entity.getName().contains("CFT-308")) {
            deviceDisplay.setThermometer(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.Thermometer);
        } else if (entity.getName().contains("Scale")) {
            deviceDisplay.setScale(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.Scale);
        } else if (entity.getName().contains("Fitness Tracker")) {
            deviceDisplay.setFitnessTracker(0);
            deleteDeviceEntity(deviceOperation, entity, DevicesType.FitnessTracker);
        }
        deviceDisplayOperation.updateDeviceDisplay(deviceDisplay);
        devicesEntities.remove(menuPosition);
        refreshSwip();
    }

    private void deleteDeviceEntity(DeviceOperation deviceOperation, DevicesEntity entity , int devicesType) {
        DeviceInfo deviceInfo = deviceOperation.queryByDeviceTypeName(IchoiceApplication.getAppData().userProfileInfo.getUserId(), devicesType, entity.getName());
        if (deviceInfo != null){
            deviceOperation.deleteDv(deviceInfo);
        }
    }


    @Override
    public void onItemClick(View view, int adapterPosition) {

    }

    @OnClick({R.id.rl_devices})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_devices:
                startActivityFinish(AddDevicesActivity.class);
                break;
                default:
        }
    }


}
