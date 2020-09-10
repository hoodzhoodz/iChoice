package com.choicemmed.ichoice.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.initalization.activity.LoginActivity;
import com.lxj.xpopup.XPopup;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 17:05
 * 修改人：114100
 * 修改时间：2019/4/1 17:05
 * 修改备注：
 */
public class MethodsUtils {
    /**
     * 错误提示弹窗
     * @param context
     * @param msg
     */
    public static void showErrorTip(Context context, String msg) {
        MyCenterPopupView centerPopupView = new MyCenterPopupView(context);
        XPopup.get(context).asCustom(centerPopupView).show();
        centerPopupView.setSinglePopup("", msg
                , context.getResources().getString(R.string.ok)
                , new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {

                    }
                });
    }

    public static void showDoubleTip(final Activity activity, String msg) {
        MyCenterPopupView centerPopupView = new MyCenterPopupView(activity);
        XPopup.get(activity).asCustom(centerPopupView).show();
        centerPopupView.setDoublePopup("", msg
                , activity.getResources().getString(R.string.no)
                , activity.getResources().getString(R.string.yes), new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {

                    }
                }, new MyCenterPopupView.NegativeClickListener() {
                    @Override
                    public void onNegativeClick() {
                        ActivityUtils.removeAll();
                        IchoiceApplication.getAppData().user.logout();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                });
    }
}
