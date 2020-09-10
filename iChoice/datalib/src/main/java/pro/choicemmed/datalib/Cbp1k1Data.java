package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by Jiang nan on 2019/11/20 17:59.
 * @description
 **/
@Entity
public class Cbp1k1Data {
    /**
     * 主键
     */
    @Id
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
     * 高压值
     */
    private Integer systolic = 0;
    /**
     * 低压值
     */
    private Integer diastolic = 0;
    /**
     * 脉率值
     */
    private Integer pulseRate = 0;
    /**
     * 设备ID
     */
    private String deviceId = "";
    /**
     * 数据同步状态，0-未上传，1-已上传
     */
    private Integer syncState = 0;
    /**
     * 创建时间
     */
    private String createTime = "1997-01-01 00:00:00";
    /**
     * 存入数据库时间
     */
    private String logDateTime = "1997-01-01 00:00:00";
    @Generated(hash = 737586089)
    public Cbp1k1Data(String Id, String deviceName, String userId, String serverId,
            String measureDateTime, String lastUpdateTime, String lastUploadTime,
            Integer systolic, Integer diastolic, Integer pulseRate, String deviceId,
            Integer syncState, String createTime, String logDateTime) {
        this.Id = Id;
        this.deviceName = deviceName;
        this.userId = userId;
        this.serverId = serverId;
        this.measureDateTime = measureDateTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUploadTime = lastUploadTime;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulseRate = pulseRate;
        this.deviceId = deviceId;
        this.syncState = syncState;
        this.createTime = createTime;
        this.logDateTime = logDateTime;
    }
    @Generated(hash = 593543856)
    public Cbp1k1Data() {
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
    public Integer getSystolic() {
        return this.systolic;
    }
    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }
    public Integer getDiastolic() {
        return this.diastolic;
    }
    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }
    public Integer getPulseRate() {
        return this.pulseRate;
    }
    public void setPulseRate(Integer pulseRate) {
        this.pulseRate = pulseRate;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Integer getSyncState() {
        return this.syncState;
    }
    public void setSyncState(Integer syncState) {
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

}
