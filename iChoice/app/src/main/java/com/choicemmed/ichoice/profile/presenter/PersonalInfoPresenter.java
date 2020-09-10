package com.choicemmed.ichoice.profile.presenter;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/3 10:22
 * 修改人：114100
 * 修改时间：2019/4/3 10:22
 * 修改备注：
 */
public interface PersonalInfoPresenter {
    void sendPersonalInfoRequest(String accessTokenKey,String gender,String birthday,String height,String weight,String firstName,String familyName);
}
