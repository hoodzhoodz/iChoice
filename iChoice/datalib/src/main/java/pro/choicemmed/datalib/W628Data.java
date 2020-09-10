package pro.choicemmed.datalib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @anthor by jiangnan
 * @Date on 2020/3/3.
 */
@Entity
public class W628Data {
    @Id
    private String uuid;
    /**
     * 用户ID
     */
    private String userId = "";
    private String startDate;
    private String endDate;
    private String series = "";
    private String accountKey;
    private String upLoadFlag = "false";
    @Generated(hash = 1695682778)
    public W628Data(String uuid, String userId, String startDate, String endDate,
            String series, String accountKey, String upLoadFlag) {
        this.uuid = uuid;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.series = series;
        this.accountKey = accountKey;
        this.upLoadFlag = upLoadFlag;
    }
    @Generated(hash = 408065711)
    public W628Data() {
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getSeries() {
        return this.series;
    }
    public void setSeries(String series) {
        this.series = series;
    }
    public String getAccountKey() {
        return this.accountKey;
    }
    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }
    public String getUpLoadFlag() {
        return this.upLoadFlag;
    }
    public void setUpLoadFlag(String upLoadFlag) {
        this.upLoadFlag = upLoadFlag;
    }

    @Override
    public String toString() {
        return "W628Data{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", series='" + series + '\'' +
                ", accountKey='" + accountKey + '\'' +
                ", upLoadFlag='" + upLoadFlag + '\'' +
                '}';
    }
}
