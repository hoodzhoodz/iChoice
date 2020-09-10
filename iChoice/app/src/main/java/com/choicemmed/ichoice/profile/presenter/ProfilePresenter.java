package com.choicemmed.ichoice.profile.presenter;

import java.io.File;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/8 13:31
 * 修改人：114100
 * 修改时间：2019/4/8 13:31
 * 修改备注：
 */
public interface ProfilePresenter {
    void sendProfileRequest(String token, File file);
}
