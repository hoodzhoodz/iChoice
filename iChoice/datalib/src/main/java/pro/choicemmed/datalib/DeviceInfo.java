package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by Jiang nan on 2019/11/26 9:55.
 * @description
 **/
@Entity
public class DeviceInfo {
    @Override
    public String toString() {
        return "DeviceInfo{" +
                "Id='" + Id + '\'' +
                ", userId='" + userId + '\'' +
                ", bluetoothId='" + bluetoothId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType=" + deviceType +
                ", TypeName='" + TypeName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", logDateTime='" + logDateTime + '\'' +
                '}';
    }

    /**
     * 主键
     */
    @Id
    private String Id = "";
    /**
     * 用户ID
     */
    private String userId = "";
    /**
     * 蓝牙Mac地址
     */
    private String bluetoothId = "";
    /**
     * 设备ID
     */
    private String deviceId = "";
    /**
     * 设备名称
     */
    private String deviceName = "";
    /**
     * 设备类型，1-blood_pressure，2-ecg, 3-pulse_oximeter, 4-sleep_monitor,5-thermometer, 6-alpha_and_scale_out,7-fitness_tracker
     */
    private Integer deviceType = 0;
    /**
     * 类型名称
     */
    private String TypeName ="";
    /**
     * 创建时间
     */
    private String createTime = "1997-01-01 00:00:00";
    /**
     * 最后更新时间
     */
    private String lastUpdateTime = "1997-01-01 00:00:00";
    /**
     * 存入数据库时间
     */
    private String logDateTime = "1997-01-01 00:00:00";
    @Generated(hash = 695069258)
    public DeviceInfo(String Id, String userId, String bluetoothId, String deviceId, String deviceName,
            Integer deviceType, String TypeName, String createTime, String lastUpdateTime, String logDateTime) {
        this.Id = Id;
        this.userId = userId;
        this.bluetoothId = bluetoothId;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.TypeName = TypeName;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.logDateTime = logDateTime;
    }
    @Generated(hash = 2125166935)
    public DeviceInfo() {
    }
    public String getId() {
        return this.Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBluetoothId() {
        return this.bluetoothId;
    }
    public void setBluetoothId(String bluetoothId) {
        this.bluetoothId = bluetoothId;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public Integer getDeviceType() {
        return this.deviceType;
    }
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
    public String getTypeName() {
        return this.TypeName;
    }
    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    public String getLogDateTime() {
        return this.logDateTime;
    }
    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

}
