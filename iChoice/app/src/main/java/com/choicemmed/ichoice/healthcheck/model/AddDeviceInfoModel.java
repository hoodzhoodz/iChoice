package com.choicemmed.ichoice.healthcheck.model;

import android.content.Context;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.model.imodel.ISaveDeviceInfoModel;

import java.util.Date;
import java.util.List;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;

/**
 * @author Created by Jiang nan on 2019/11/26 10:51.
 * @description
 **/
public class AddDeviceInfoModel {
    private static final String TAG = "AddDeviceInfoModel";
    private Context mContext;
    private ISaveDeviceInfoModel iSaveDeviceInfoModel;

    public AddDeviceInfoModel(Context mContext, ISaveDeviceInfoModel iSaveDeviceInfoModel) {
        this.mContext = mContext;
        this.iSaveDeviceInfoModel = iSaveDeviceInfoModel;
    }

    public void saveDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfo.setLogDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        LogUtils.d(TAG, "deviceInfo:" + deviceInfo.toString());
        DeviceOperation deviceOperation = new DeviceOperation(mContext);
        List<DeviceInfo> deviceInfos = deviceOperation.queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "", deviceInfo.getDeviceType());
        if (deviceInfos.isEmpty()) {
            deviceInfo.setId(UuidUtils.getUuid());
            deviceOperation.insert(deviceInfo);
            iSaveDeviceInfoModel.saveDeviceSuccess();
            return;
        }
        deviceInfo.setId(deviceInfos.get(0).getId());
        deviceOperation.update(deviceInfo);
        iSaveDeviceInfoModel.saveDeviceSuccess();
    }

    public void saveDeviceDisplayInfo(DeviceDisplay deviceDisplay, int deviceType) {
        deviceDisplay.setLogDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        LogUtils.d(TAG, "deviceDisplay:" + deviceDisplay.toString());
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(mContext);
        List<DeviceDisplay> deviceDisplays = deviceDisplayOperation.queryByUserIds(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "");
        if (deviceDisplays == null || deviceDisplays.isEmpty()) {
            deviceDisplayOperation.insert(deviceDisplay);
            iSaveDeviceInfoModel.saveOrUpdateDeviceDisplaySuccess();
            return;
        }
        deviceDisplay = deviceDisplays.get(0);
        deviceDisplay.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        switch (deviceType) {
            case DevicesType.BloodPressure:
                deviceDisplay.setBloodPressure(1);
                break;
            case DevicesType.Ecg:
                deviceDisplay.setEcg(1);
                break;
            case DevicesType.PulseOximeter:
                deviceDisplay.setPulseOximeter(1);
                break;
            case DevicesType.WristPluseOximeter:
                deviceDisplay.setWristPluseOximeter(1);
                break;
            case DevicesType.Thermometer:
                deviceDisplay.setThermometer(1);
                break;
            case DevicesType.Scale:
                deviceDisplay.setScale(1);
                break;
            case DevicesType.FitnessTracker:
                deviceDisplay.setFitnessTracker(1);
                break;
                default:
        }
        deviceDisplayOperation.update(deviceDisplay);
        iSaveDeviceInfoModel.saveOrUpdateDeviceDisplaySuccess();
    }

}
