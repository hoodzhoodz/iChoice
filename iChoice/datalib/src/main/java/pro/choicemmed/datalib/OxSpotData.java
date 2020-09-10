package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OxSpotData {
    /**
     * 主键
     */
    @org.greenrobot.greendao.annotation.Id
    private String Id = "";
    /**
     * 数据来源设备名称
     */
    private String deviceName = "";
    /**
     * 用户ID
     */
    private String userId = "";
    /**
     * 数据上传时服务器返回，用于数据操作标识
     */
    private String serverId = "";
    /**
     * 测量时间
     */
    private String measureDateTime = "1997-01-01 00:00:00";
    /**
     * 最后更新时间
     */
    private String lastUpdateTime = "1997-01-01 00:00:00";
    /**
     * 最后上传时间
     */
    private String lastUploadTime = "1997-01-01 00:00:00";
    /**
     * 血氧值
     */
    private int bloodOxygen = 0;
    /**
     * pi值 注：协议给定值为整数，除以10为的值
     */
    private float pi = 0;
    /**
     * 脉率值
     */
    private int pulseRate = 0;
    /**
     * RR
     */
    private int RR = 0;
    /**
     * 设备ID
     */
    private String deviceId = "";
    /**
     * 数据同步状态，0-未上传，1-已上传
     */
    private int syncState = 0;
    /**
     * 创建时间
     */
    private String createTime = "1997-01-01 00:00:00";
    /**
     * 存入数据库时间
     */
    private String logDateTime = "1997-01-01 00:00:00";

    @Generated(hash = 480633240)
    public OxSpotData(String Id, String deviceName, String userId, String serverId,
            String measureDateTime, String lastUpdateTime, String lastUploadTime,
            int bloodOxygen, float pi, int pulseRate, int RR, String deviceId,
            int syncState, String createTime, String logDateTime) {
        this.Id = Id;
        this.deviceName = deviceName;
        this.userId = userId;
        this.serverId = serverId;
        this.measureDateTime = measureDateTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUploadTime = lastUploadTime;
        this.bloodOxygen = bloodOxygen;
        this.pi = pi;
        this.pulseRate = pulseRate;
        this.RR = RR;
        this.deviceId = deviceId;
        this.syncState = syncState;
        this.createTime = createTime;
        this.logDateTime = logDateTime;
    }
    @Generated(hash = 919544592)
    public OxSpotData() {
    }
    public String getId() {
        return this.Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getServerId() {
        return this.serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    public String getMeasureDateTime() {
        return this.measureDateTime;
    }
    public void setMeasureDateTime(String measureDateTime) {
        this.measureDateTime = measureDateTime;
    }
    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    public String getLastUploadTime() {
        return this.lastUploadTime;
    }
    public void setLastUploadTime(String lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }
    public int getBloodOxygen() {
        return this.bloodOxygen;
    }
    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }
    public float getPi() {
        return this.pi;
    }
    public void setPi(float pi) {
        this.pi = pi;
    }
    public int getPulseRate() {
        return this.pulseRate;
    }
    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }
    public int getRR() {
        return this.RR;
    }
    public void setRR(int RR) {
        this.RR = RR;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public int getSyncState() {
        return this.syncState;
    }
    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getLogDateTime() {
        return this.logDateTime;
    }
    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

    @Override
    public String toString() {
        return "OxSpotData{" +
                "Id='" + Id + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", userId='" + userId + '\'' +
                ", serverId='" + serverId + '\'' +
                ", measureDateTime='" + measureDateTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", lastUploadTime='" + lastUploadTime + '\'' +
                ", bloodOxygen=" + bloodOxygen +
                ", pi=" + pi +
                ", pulseRate=" + pulseRate +
                ", RR=" + RR +
                ", deviceId='" + deviceId + '\'' +
                ", syncState=" + syncState +
                ", createTime='" + createTime + '\'' +
                ", logDateTime='" + logDateTime + '\'' +
                '}';
    }
}
