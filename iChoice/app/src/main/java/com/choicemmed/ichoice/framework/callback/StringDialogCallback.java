package com.choicemmed.ichoice.framework.callback;

import android.content.Context;

import com.lzy.okgo.request.base.Request;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 15:28
 * 修改人：114100
 * 修改时间：2019/4/1 15:28
 * 修改备注：
 */
public abstract class StringDialogCallback extends StringCallback {

    public static Context mContext;

    @Override
    public void onStart(Request<String, ? extends Request> request) {
//        if(mDialog != null && !mDialog.isShowing()){
//            mDialog.show();
//        }
    }

    @Override
    public void onFinish() {
//        if(mDialog != null && mDialog.isShowing()){
//            mDialog.cancel();
//        }
    }
}

