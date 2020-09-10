package pro.choicemmed.datalib;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OxRealTimeData {
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
     * 测量开始时间
     */
    private String measureDateStartTime = "1997-01-01 00:00:00";

    /**
     * 测量结束时间
     */
    private String measureDateEndTime = "1997-01-01 00:00:00";

    /**
     * 最后更新时间
     */
    private String lastUpdateTime = "1997-01-01 00:00:00";
    /**
     * 上传服务器上传时间
     */
    private String lastUploadTime = "1997-01-01 00:00:00";


    /**
     * 连续血氧 格式：序列号(浮点型)：序号,血氧,脉率,pi，呼吸率  没有当前测量项的话存0。10秒一存
     * <p>
     * 例子：1,spo2,pr,0，rr|2
     */
    private String series;

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

    @Generated(hash = 857386787)
    public OxRealTimeData(String Id, String deviceName, String userId,
                          String serverId, String measureDateStartTime, String measureDateEndTime,
                          String lastUpdateTime, String lastUploadTime, String series,
                          String deviceId, Integer syncState, String createTime,
                          String logDateTime) {
        this.Id = Id;
        this.deviceName = deviceName;
        this.userId = userId;
        this.serverId = serverId;
        this.measureDateStartTime = measureDateStartTime;
        this.measureDateEndTime = measureDateEndTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUploadTime = lastUploadTime;
        this.series = series;
        this.deviceId = deviceId;
        this.syncState = syncState;
        this.createTime = createTime;
        this.logDateTime = logDateTime;
    }

    @Generated(hash = 1542926509)
    public OxRealTimeData() {
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

    public String getMeasureDateStartTime() {
        return this.measureDateStartTime;
    }

    public void setMeasureDateStartTime(String measureDateStartTime) {
        this.measureDateStartTime = measureDateStartTime;
    }

    public String getMeasureDateEndTime() {
        return this.measureDateEndTime;
    }

    public void setMeasureDateEndTime(String measureDateEndTime) {
        this.measureDateEndTime = measureDateEndTime;
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

    public String getSeries() {
        return this.series;
    }

    public void setSeries(String series) {
        this.series = series;
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

    @Override
    public String toString() {
        return "OxRealTimeData{" +
                "Id='" + Id + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", userId='" + userId + '\'' +
                ", serverId='" + serverId + '\'' +
                ", measureDateStartTime='" + measureDateStartTime + '\'' +
                ", measureDateEndTime='" + measureDateEndTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", lastUploadTime='" + lastUploadTime + '\'' +
                ", series='" + series + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", syncState=" + syncState +
                ", createTime='" + createTime + '\'' +
                ", logDateTime='" + logDateTime + '\'' +
                '}';
    }


}
