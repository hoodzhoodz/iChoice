package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CFT308Data {
    @Id
    private String uuid;
    /**
     * 用户ID
     */
    private String userId = "";
    private String startDate;
    private String endDate;
    private String measureTime;
    private String unit;
    private String temp = "";
    private String accountKey;
    private String upLoadFlag = "false";

    @Generated(hash = 814510794)
    public CFT308Data(String uuid, String userId, String startDate, String endDate,
                      String measureTime, String unit, String temp, String accountKey,
                      String upLoadFlag) {
        this.uuid = uuid;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.measureTime = measureTime;
        this.unit = unit;
        this.temp = temp;
        this.accountKey = accountKey;
        this.upLoadFlag = upLoadFlag;
    }

    @Generated(hash = 1535257218)
    public CFT308Data() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getUpLoadFlag() {
        return upLoadFlag;
    }

    public void setUpLoadFlag(String upLoadFlag) {
        this.upLoadFlag = upLoadFlag;
    }

    @Override
    public String toString() {
        return "CFT308Data{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", measureTime='" + measureTime + '\'' +
                ", unit='" + unit + '\'' +
                ", temp='" + temp + '\'' +
                ", accountKey='" + accountKey + '\'' +
                ", upLoadFlag='" + upLoadFlag + '\'' +
                '}';
    }
}
