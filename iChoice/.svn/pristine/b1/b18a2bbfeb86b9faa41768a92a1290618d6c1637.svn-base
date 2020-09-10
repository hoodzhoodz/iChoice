package com.choicemmed.ichoice.framework.utils;

import com.choicemmed.common.LogUtils;
import com.lzy.okgo.model.Response;

import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * Created by GXZ on 2018/4/13.
 * 接口错误调试
 */

public class Debugger {
    private static final String TAG = "test";

    /**
     * 接口失败状态描述  可用于调试
     * @param response
     * @param <T>
     */
    public static <T> void handleError(Response<T> response) {
        if (response == null) {
            return;
        }
        if (response.getException() != null){
            response.getException().printStackTrace();
        }

        StringBuilder sb;
        Call call = response.getRawCall();
        if (call != null) {
            LogUtils.d(TAG,call.request().method() + "\n" + "url：" + call.request().url());
            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            LogUtils.d(TAG,sb.toString());
        }
        okhttp3.Response rawResponse = response.getRawResponse();
        if (rawResponse != null) {
            Headers responseHeadersString = rawResponse.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(rawResponse.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            LogUtils.d(TAG,sb.toString());
        }
    }
}
