package com.choicemmed.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern pwd = Pattern.compile("^\\w{6,16}$");
    private final static Pattern mobile = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    private final static Pattern time = Pattern.compile("(\\d+)|(\\-\\d+)");
    private final static Pattern nonChinese = Pattern.compile("[\\u4e00-\\u9fa5]");
    private final static DecimalFormat decimalFormat = new DecimalFormat(".00");


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * @param input "0:00:00"
     * @return
     */
    public static float getMinutes(String input) {
        float minutes = 0;
        if (input.length() == 5) {
            minutes = Integer.parseInt(input.substring(0, 2)) + Integer.parseInt(input.substring(3, 5)) / 60f;
        }

        if (input.length() == 7) {
            minutes = Integer.parseInt(input.substring(2, 4)) + Integer.parseInt(input.substring(5, 7)) / 60f;
        }
        return minutes;
    }

    /**
     * @param input "0:00:00"
     * @return
     */
    public static long getSeconds(String input) {
        long seconds = 0;
        if (input.length() == 5) {
            seconds = Integer.parseInt(input.substring(0, 2)) * 60 + Integer.parseInt(input.substring(3, 5));
        }
        return seconds;
    }

    public static String getNonChineseStr(String input) {
        return input.replaceAll("[\\u4e00-\\u9fa5]", "");
    }

    public static String getDecimalFormatString(float input) {
        return decimalFormat.format(input);
    }

    /**
     * 服务器端返回的日期字符串为"\/Date(1432790083000+0800)\/",时间戳+时区
     * 取得1432790083000
     *
     * @param input
     * @return
     */
    public static String getTimeInMillis(String input) {
        Matcher m = time.matcher(input);
        if (!m.find()) {
            return null;
        }
        return m.group(0);
    }

    public static String getTimeFromSeconds(long input) {
        String minutes = String.valueOf(input / 60);
        String seconds = String.valueOf(input % 60);
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }


    public static String getDateTimeInMillis(String input) {
        Matcher m = time.matcher(input);
        if (!m.find()) {
            return null;
        }
        String dateTimeString = FormatUtils.getDateTimeString(Long.parseLong(m.group(0)), FormatUtils.template_DbDateTime);
        return dateTimeString;
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、
     * 换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str
     * @return boolean
     * @Title rangesMandN
     * @Description 判断是不是一个合法的密码
     */
    public static boolean isPwd(String str) {

        if (pwd.matcher(str).matches()) {
            return true;
        }
        return false;

    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * @param mobiles
     * @return boolean
     * @Title isMobileNO
     * @Description 判断是不是一个合法的电话号码
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobile.matcher(mobiles).matches())
            return true;
        else
            return false;
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    public static int parseInteger(String s) {
        int result = 0;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String checkNull(String str) {
        return str == null ? "" : str;
    }


}
