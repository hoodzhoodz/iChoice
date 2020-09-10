package com.choicemmed.ichoice.healthcheck.presenter;

import android.content.Context;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.healthcheck.db.W314B4Operation;
import com.choicemmed.ichoice.healthcheck.db.W628Operation;
import com.choicemmed.ichoice.healthcheck.view.WristDataView;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

/**
 * @author: created by JiangNan
 * @Date: 2020/2/19 11
 */
public class WristDataPresenter extends BasePresenterImpl<IBaseView> implements WristDataView {

    private Context mContext;

    public WristDataPresenter(Context context, IBaseView iBaseView) {
        mContext = context;
        attachView(iBaseView);
    }

    @Override
    public void sendWristDataRequest(String accountKey, final String uuid, String startDate, String endDate, String series, final int is628) {
        StringDialogCallback callback = new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","上传基本数据返回值***"+object);
                mView.onSuccess();
                if (is628 == 0){
                    W314B4Operation w314B4Operation = new W314B4Operation(mContext);
                    w314B4Operation.setUpLoadTrue(uuid);
                }else {
                    W628Operation w628Operation = new W628Operation(mContext);
                    w628Operation.setUpLoadTrue(uuid);
                }

            }

            @Override
            public void onMessage(String message) {
                mView.onError(message);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Debugger.handleError(response);
            }
        };
        HttpUtils.upLoadWristRequest(mContext, accountKey, uuid, startDate, endDate, series, is628, callback);
    }


}
