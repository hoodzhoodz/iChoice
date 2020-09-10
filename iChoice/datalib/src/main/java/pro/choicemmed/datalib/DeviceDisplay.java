package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by Jiang nan on 2019/11/26 11:20.
 * @description
 **/
@Entity
public class DeviceDisplay {
    /**
     * 主键
     */
    @Id
    private String ID = "";
    /**
     * 用户ID
     */
    private String UserId = "";

    /**
     * 设备，0-未绑定，1-已绑定
     */
    private Integer BloodPressure = 0;

    private Integer Ecg = 0;

    private Integer PulseOximeter = 0;

    private Integer WristPluseOximeter = 0;

    private Integer Thermometer = 0;

    private Integer Scale = 0;

    private Integer FitnessTracker = 0;
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
    @Generated(hash = 1314997909)
    public DeviceDisplay(String ID, String UserId, Integer BloodPressure,
            Integer Ecg, Integer PulseOximeter, Integer WristPluseOximeter,
            Integer Thermometer, Integer Scale, Integer FitnessTracker,
            String createTime, String lastUpdateTime, String logDateTime) {
        this.ID = ID;
        this.UserId = UserId;
        this.BloodPressure = BloodPressure;
        this.Ecg = Ecg;
        this.PulseOximeter = PulseOximeter;
        this.WristPluseOximeter = WristPluseOximeter;
        this.Thermometer = Thermometer;
        this.Scale = Scale;
        this.FitnessTracker = FitnessTracker;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.logDateTime = logDateTime;
    }
    @Generated(hash = 1141145214)
    public DeviceDisplay() {
    }
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getUserId() {
        return this.UserId;
    }
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
    public Integer getBloodPressure() {
        return this.BloodPressure;
    }
    public void setBloodPressure(Integer BloodPressure) {
        this.BloodPressure = BloodPressure;
    }
    public Integer getEcg() {
        return this.Ecg;
    }
    public void setEcg(Integer Ecg) {
        this.Ecg = Ecg;
    }
    public Integer getPulseOximeter() {
        return this.PulseOximeter;
    }
    public void setPulseOximeter(Integer PulseOximeter) {
        this.PulseOximeter = PulseOximeter;
    }
    public Integer getWristPluseOximeter() {
        return this.WristPluseOximeter;
    }
    public void setWristPluseOximeter(Integer WristPluseOximeter) {
        this.WristPluseOximeter = WristPluseOximeter;
    }
    public Integer getThermometer() {
        return this.Thermometer;
    }
    public void setThermometer(Integer Thermometer) {
        this.Thermometer = Thermometer;
    }
    public Integer getScale() {
        return this.Scale;
    }
    public void setScale(Integer Scale) {
        this.Scale = Scale;
    }
    public Integer getFitnessTracker() {
        return this.FitnessTracker;
    }
    public void setFitnessTracker(Integer FitnessTracker) {
        this.FitnessTracker = FitnessTracker;
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

    @Override
    public String toString() {
        return "DeviceDisplay{" +
                "ID='" + ID + '\'' +
                ", UserId='" + UserId + '\'' +
                ", BloodPressure=" + BloodPressure +
                ", Ecg=" + Ecg +
                ", PulseOximeter=" + PulseOximeter +
                ", WristPluseOximeter=" + WristPluseOximeter +
                ", Thermometer=" + Thermometer +
                ", Scale=" + Scale +
                ", FitnessTracker=" + FitnessTracker +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", logDateTime='" + logDateTime + '\'' +
                '}';
    }
}
