package com.choicemmed.ichoice.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by Jiang nan on 2019/12/19 17:15.
 * @description
 **/
public class JudgeUtils {
    /**
     * 判断邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPassword(String password) {
        String str = "(?!.*\\s)(?!^[\\u4e00-\\u9fa5]+$)(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{8,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isCode(String password) {
        String str = "^[0-9]{6}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
