package com.choicemmed.ichoice.framework.http;

/**
 * 项目名称：iChoice
 * 类描述：接口管理
 * 创建人：114100
 * 创建时间：2019/4/1 13:02
 * 修改人：114100
 * 修改时间：2019/4/1 13:02
 * 修改备注：
 */
public class Urls {

    static UrlController urlController = UrlController.getInstance(false);

    public static final String HOST = urlController.getUrl();

    /**验证码**/
    public static final String ValidCode = HOST + "/Account/SendValidCode";

    /**注册**/
    public static final String Register = HOST + "/Account/SignUp";

    /**登陆**/
    public static final String Login = HOST + "/Account/GetAccessTokenKey";

    /**重置密码**/
    public static final String ResetPSW = HOST + "/Account/ResetPSW";

    /**上传头像**/
    public static final String Avatar = HOST + "/Account/UpdateAvatar";

    /**上传用户基本信息**/
    public static final String PersonalInfo = HOST + "/Account/UploadBasicData";

    /**修改密码**/
    public static final String Password = HOST + "/Account/UpdatePassword";

    /**获取用户概要信息**/
    public static final String UserProfile = HOST +"/Account/GetUserProfile";

    public static final String UploadWristData = HOST + "/Sleep/UploadSleepRecord";

    public static final String UploadEcgData = "https://api.huadaifu.cn:8070/Services/ServiceHandler.ashx?method=addrecord";

    public static final String DownLodaEcgResult = "https://api.huadaifu.cn:8070/Services/ServiceHandler.ashx?method=aidiagnosis";

    /**下载sleep报告**/
    public static final  String SleepReport = HOST + "/Sleep/DownLoadRecord";
    /**下载sleep报告**/
    public static final  String SleepReport_android = HOST + "/Sleep/DownLoadRecordAndroid";
    /**
     * 下载ichoiceRelax
     **/
    public static final String DownLoadIchoiceRelax = "http://www.choicemmed.com/files?path=%2Fupload%2Ffiles%2F290aa8ac5269e5e759226ee832a8d4db.apk";
}
