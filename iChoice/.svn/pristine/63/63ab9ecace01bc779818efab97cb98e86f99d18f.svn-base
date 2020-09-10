package com.choicemmed.ichoice.framework.http;

import android.content.Context;

import com.choicemmed.common.IdcardUtils;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.Locale;

/**
 * 项目名称：iChoice
 * 类描述：请求配置
 * 创建人：114100
 * 创建时间：2019/4/1 13:08
 * 修改人：114100
 * 修改时间：2019/4/1 13:08
 * 修改备注：
 */
public class HttpUtils {
    /**
     * 获取验证码
     * @param context
     * @param account
     * @param codeType
     * @param callback
     */

//    @{@"appKey":APPKEY,@"appSecret":APPSECRET,@"account":self.phoneNumberTextField.text,@"codeType":@"1",@"locale":[ToolList currentLocale]}
    public static void sendValidCodeRequest(Context context, String account, String codeType, StringDialogCallback callback) {
        String language = Locale.getDefault().getLanguage();
        if (language.contains("zh")) {
            language = "zh";
        } else {
            language = "en";
        }
        OkGo.<String>post(Urls.ValidCode).tag(context)
                .params("appKey",ApiConfig.AppKey)
                .params("appSecret",ApiConfig.AppSecret)
                .params("account",account)
                .params("codeType",codeType)
                .params("locale", language)
                .execute(callback);
    }

    /**
     * 注册
     * @param context
     * @param email_phone
     * @param validcode
     * @param password
     * @param subscribe
     * @param callback
     */
    public static void sendRegisterRequset(Context context, String email_phone, String validcode, String password, String subscribe, StringDialogCallback callback) {
        String eamil="";
        String phone="";
        String regtype="";
        if (IdcardUtils.validateEmail(email_phone)){
            eamil=email_phone;
            regtype="1";
        } else if (IdcardUtils.isMobileNO(email_phone)){
            phone=email_phone;
            regtype="2";
        }
        OkGo.<String>post(Urls.Register).tag(context)
                .params("appKey",ApiConfig.AppKey)
                .params("appSecret",ApiConfig.AppSecret)
                .params("email",eamil)
                .params("phone",phone)
                .params("validcode",validcode)
                .params("regtype",regtype)
                .params("password",password)
                .params("subscribe",subscribe)
                .execute(callback);
    }

    /**
     * 登陆
     * @param context
     * @param account
     * @param password
     * @param callback
     */
    public static void sendLoginRequest(Context context, String account, String password, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Login).tag(context)
                .params("appKey",ApiConfig.AppKey)
                .params("appSecret",ApiConfig.AppSecret)
                .params("account",account)
                .params("password",password)
                .execute(callback);
    }

    /**
     * 重置密码
     * @param context
     * @param account 手机号或者密码
     * @param validcode 6位验证码
     * @param password  重置密码
     * @param callback
     */
    public static void sendForgetPwdRequest(Context context, String account, String validcode, String password, StringDialogCallback callback) {
        OkGo.<String>post(Urls.ResetPSW).tag(context)
                .params("appKey",ApiConfig.AppKey)
                .params("appSecret",ApiConfig.AppSecret)
                .params("account",account)
                .params("validcode",validcode)
                .params("password",password)
                .execute(callback);
    }

    /**
     * 上传头像
     * @param context
     * @param token
     * @param avatar
     * @param callback
     */
    public static void sendAvatarRequest(Context context, String token, File avatar, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Avatar).tag(context)
                .params("accessTokenKey",token)
                .params("avatar",avatar)
                .execute(callback);
    }

    /**
     * 上传用户基本信息
     * @param context
     * @param token
     * @param gender
     * @param birthday
     * @param height
     * @param weight
     * @param firstName
     * @param familyName
     * @param callback
     */
    public static void sendPersonalInfoRequest(Context context, String token, String gender, String birthday, String height, String weight, String firstName, String familyName, StringDialogCallback callback) {
        OkGo.<String>post(Urls.PersonalInfo).tag(context)
                .params("accessTokenKey",token)
                .params("gender",gender)
                .params("birthday",birthday)
                .params("height",height)
                .params("lengthShowUnitSystem","1")
                .params("weight",weight)
                .params("weightShowUnitSystem","1")
                .params("timeZoneId","0")
                .params("timeDifference","8")
                .params("timeSystem","12")
                .params("firstName",firstName)
                .params("familyName",familyName)
                .execute(callback);

    }

    /**
     * 修改密码
     * @param context
     * @param token
     * @param oldPassword
     * @param newPassword
     * @param callback
     */
    public static void sendUploadPassword(Context context, String token, String oldPassword, String newPassword, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Password).tag(context)
                .params("accessTokenKey",token)
                .params("oldPassword",oldPassword)
                .params("newPassword",newPassword)
                .execute(callback);
    }

    /**
     * 获取用户概要信息
     * @param context
     * @param accessTokenKey
     * @param callback
     */
    public static void getetUserProfile(Context context, String accessTokenKey, StringDialogCallback callback) {
        OkGo.<String>post(Urls.UserProfile).tag(context)
                .params("accessTokenKey",accessTokenKey)
                .execute(callback);
    }

    /**
     * 上传头像
     * @param context
     * @param token
     * @param file
     * @param callback
     */
    public static void sendProfileRequest(Context context, String token, File file, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Avatar).tag(context)
                .params("accessTokenKey",token)
                .params("avatar",file)
                .execute(callback);
    }

    /**
     * 上传腕式血氧数据
     * @param context
     * @param accountKey
     * @param uuid
     * @param startDate
     * @param endDate
     * @param series
     * @param callback
     */
    public static void upLoadWristRequest(Context context, String accountKey, String uuid, String startDate, String endDate, String series, int is628, StringDialogCallback callback){
        String language = Locale.getDefault().getLanguage();
        if (language.contains("zh")) {
            language = "zh";
        } else {
            language = "en";
        }
        OkGo.<String>post(Urls.UploadWristData).tag(context)
                .params("accessTokenKey", accountKey)
                .params("linkId", uuid)
                .params("startDate", startDate)
                .params("finishDate", endDate)
                .params("series", series)
                .params("is628", is628)
                .params("locale", language)
                .execute(callback);
    }

    public static void upLoadEcgRequest(Context context, String accountKey, String uuid, String startDate, String endDate, String series, int is628, StringDialogCallback callback) {
        String language = Locale.getDefault().getLanguage();
        if (language.contains("zh")) {
            language = "zh";
        } else {
            language = "en";
        }
        OkGo.<String>post(Urls.UploadEcgData).tag(context)
                .params("accessTokenKey", accountKey)
                .params("linkId", uuid)
                .params("startDate", startDate)
                .params("finishDate", endDate)
                .params("series", series)
                .params("is628", is628)
                .params("locale", language)
                .execute(callback);
    }



}
