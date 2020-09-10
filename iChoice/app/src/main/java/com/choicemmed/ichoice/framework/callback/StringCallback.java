package com.choicemmed.ichoice.framework.callback;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 项目名称：ytj_shanxi
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/3/28 13:15
 * 修改人：114100
 * 修改时间：2019/3/28 13:15
 * 修改备注：
 */
public abstract class StringCallback extends AbsCallback<String> {
    private StringConvert convert;
    private String json;
    private String state;
    private String message;

    public StringCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        json = convert.convertResponse(response);
        response.close();
        return json;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
        try{
            JSONObject object = new JSONObject(json);
            state = object.getString("Code");
            message=object.getString("Data");
            if ("0".equals(state)){
                onSuccess(object);
            }else {
                onMessage(message);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public abstract void onSuccess(JSONObject object);
    public abstract void onMessage(String message);
}
