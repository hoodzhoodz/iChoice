package com.choicemmed.ichoice.initalization.entity;

import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.initalization.config.ApiConfig;

/**
 * @author Created by Jiang nan on 2020/1/9 13:39.
 * @description
 **/
public class User {
    private  String token;                  //登录token
    private  boolean isLogin;               //是否登录
    private  String firstName;
    private  String familyName;
    private  String gender;
    private  String birthday;
    private  String height;
    private  String weight;
    private  String email;
    private  String headImgUrl;
    private  String headImgUrl100x100;
    private  String headImgUrl200x200;
    private  String headImgUrl500x400;

    public User() {
    }

    public  String getToken() {
        return token;
    }

    public  boolean isIsLogin() {
        return isLogin;
    }

    public  String getFirstName() {
        return firstName;
    }

    public  String getFamilyName() {
        return familyName;
    }

    public  String getGender() {
        return gender;
    }

    public  String getBirthday() {
        return birthday;
    }

    public  String getHeight() {
        return height;
    }

    public  String getWeight() {
        return weight;
    }

    public  String getEmail() {
        return email;
    }

    public  String getHeadImgUrl() {
        return headImgUrl;
    }

    public  String getHeadImgUrl100x100() {
        return headImgUrl100x100;
    }

    public  String getHeadImgUrl200x200() {
        return headImgUrl200x200;
    }

    public  String getHeadImgUrl500x400() {
        return headImgUrl500x400;
    }

    public  void init() {
        token = PreferenceUtil.getInstance().getPreferences(ApiConfig.TOKEN,"");
        isLogin = PreferenceUtil.getInstance().getPreferences(ApiConfig.IS_LOGIN, false);
        firstName =PreferenceUtil.getInstance().getPreferences(ApiConfig.FIRSTNAME,"");
        familyName = PreferenceUtil.getInstance().getPreferences(ApiConfig.FAMILYNAME,"");
        gender = PreferenceUtil.getInstance().getPreferences(ApiConfig.GENDER,"");
        birthday=PreferenceUtil.getInstance().getPreferences(ApiConfig.BIRTHDAY,"");
        height=PreferenceUtil.getInstance().getPreferences(ApiConfig.HEIGHT,"");
        weight=PreferenceUtil.getInstance().getPreferences(ApiConfig.WEIGHT,"");
        email = PreferenceUtil.getInstance().getPreferences(ApiConfig.EMAIL,"");

        headImgUrl = PreferenceUtil.getInstance().getPreferences(ApiConfig.HEADIMGURL,"");
        headImgUrl100x100 = PreferenceUtil.getInstance().getPreferences(ApiConfig.HEADIMGURL100x100,"");
        headImgUrl200x200 = PreferenceUtil.getInstance().getPreferences(ApiConfig.HEADIMGURL200x200,"");
        headImgUrl500x400 = PreferenceUtil.getInstance().getPreferences(ApiConfig.HEADIMGURL500x400,"");
    }
    public  boolean isLogin() {
        if (isLogin) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 刷新数据 （获取）
     */
    public  void refresh() {
        init();
    }
    /**
     * 退出登录 清除数据
     */
    public  void logout() {
        PreferenceUtil.getInstance().putPreferences(ApiConfig.TOKEN,"");
        PreferenceUtil.getInstance().putPreferences(ApiConfig.IS_LOGIN, false);
        refresh();
    }
}
