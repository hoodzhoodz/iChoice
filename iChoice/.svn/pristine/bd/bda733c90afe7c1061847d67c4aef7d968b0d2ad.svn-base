package com.choicemmed.ichoice.healthcheck.presenter;

import android.content.Context;

import com.choicemmed.ichoice.healthcheck.model.AddDeviceInfoModel;
import com.choicemmed.ichoice.healthcheck.model.imodel.ISaveDeviceInfoModel;
import com.choicemmed.ichoice.healthcheck.view.ISaveDeviceInfoView;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;


/**
 * @author Created by Jiang nan on 2019/11/26 10:28.
 * @description
 **/
public class SaveDeviceInfoPresenter implements ISaveDeviceInfoModel {
    private static final String TAG = "SaveDeviceInfoPresenter";
    private Context mContext;
    private ISaveDeviceInfoView deviceInfoView;
    private AddDeviceInfoModel deviceInfoModel;

    public SaveDeviceInfoPresenter(Context mContext, ISaveDeviceInfoView deviceInfoView) {
        this.mContext = mContext;
        this.deviceInfoView = deviceInfoView;
        deviceInfoModel = new AddDeviceInfoModel(mContext,this);
    }

    public void callModelSaveDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfoModel.saveDeviceInfo(deviceInfo);
    }

    public void callModelSaveBindDeviceInfo(DeviceDisplay deviceDisplay, int deviceType) {
        deviceInfoModel.saveDeviceDisplayInfo(deviceDisplay, deviceType);
    }


    @Override
    public void saveDeviceSuccess() {
        deviceInfoView.saveDeviceInfoFinish();
    }

    @Override
    public void saveOrUpdateDeviceDisplaySuccess() {
        deviceInfoView.saveOrUpdateDeviceDisplayFinish();
    }
}
