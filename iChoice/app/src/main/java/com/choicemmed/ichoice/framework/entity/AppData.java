package com.choicemmed.ichoice.framework.entity;

import com.choicemmed.ichoice.initalization.entity.GsUserProfileEntity;
import com.choicemmed.ichoice.initalization.entity.User;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.UserProfileInfo;

/**
 * @author Created by Jiang nan on 2019/11/21 11:27.
 * @description
 **/
public class AppData {

    /**
     *保存用户
     */
    public String userId;

    /**
     * greendao的user的实例
     */
    public UserProfileInfo userProfileInfo = null;

    /**
     * useremail
     */
    public String userEmail = null;

    /**
     * user 网络获取的信息
     */
    public GsUserProfileEntity gsUserProfileEntity = null;

    public DeviceDisplay deviceDisplay = null;

    public User user = new User();

}
