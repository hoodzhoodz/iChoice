package com.choicemmed.ichoice.framework.http;

/**
 * 项目名称：iChoice
 * 类描述：开发环境控制器
 * 创建人：114100
 * 创建时间：2019/4/1 13:02
 * 修改人：114100
 * 修改时间：2019/4/1 13:02
 * 修改备注：
 */
public class UrlController {
    private String url;


    private static class SingletonController {
        public final static UrlController instance = new UrlController();
    }

    public static UrlController getInstance(boolean isOfficial) {
        SingletonController.instance.init(isOfficial);
        return SingletonController.instance;
    }

    public void init(boolean isOfficial) {
        if (isOfficial) {
            setOfficialUrl();
        } else {
            setFormalUrl();
        }
    }

    private void setOfficialUrl() {
        url = "https://api.huadaifu.cn";
    }

    private void setFormalUrl() {
        url = "https://api.huadaifu.cn:8060";

    }


    public String getUrl() {
        return url;
    }
}
