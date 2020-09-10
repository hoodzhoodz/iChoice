package com.choicemmed.cbp1k1sdkblelibrary.cmd.parse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Name: ZhengZhong on 2018/1/23 10:21
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941ParseUtils {

    /**
     * 收缩压Key
     */
    public static final String SYSTOLIC_PRESSURE = "SYSTOLIC_PRESSURE";
    /**
     * 舒张压Key
     */
    public static final String DIASTOLIC_PRESSURE = "DIASTOLIC_PRESSURE";
    /**
     * 脉率Key
     */
    public static final String PULSE_RATE = "PULSE_RATE";

    /**
     * 测量结果指令标识
     */
    private static final String MEASURE_RESULT = "04";
    /**
     * 中间压指令标识
     */
    private static final String MEASURE_MIDDLE = "03";

    /**
     * 接收到中间压标识
     */
    private static boolean middlePressData = false;

    /**
     * 测量数据解析
     *
     * @param result 待解析数据
     */
    public synchronized static Map<String, Integer> parseData(String result) {

        //判断是否接收到中间压
        if (!middlePressData && result.startsWith(MEASURE_MIDDLE)) {
            middlePressData = true;
            return null;
        }
        //解析血压计测量结果数据
        if (middlePressData && result.startsWith(MEASURE_RESULT)) {
            middlePressData = false;
            int sys = Integer.parseInt(result.substring(4, 6), 16);
            int dia = Integer.parseInt(result.substring(6, 8), 16);
            int pr = Integer.parseInt(result.substring(8, 10), 16);
            Map<String, Integer> map = new HashMap<>(3);
            map.put(SYSTOLIC_PRESSURE, sys);
            map.put(DIASTOLIC_PRESSURE, dia);
            map.put(PULSE_RATE, pr);
            return map;
        }
        return null;
    }

    /**
     * 校验CRC(伪循环冗余校验，来自协议(-.-))
     *
     * @param result 待校验数据
     * @return true-成功，false-失败
     */
    public static boolean checkCRC(byte[] result) {
        byte crc = 0;
        //计算检验和值
        for (int i = 0; i < result.length - 1; i++) {
            crc += result[i];
        }
        //校验检验和是否相等
        return crc == result[result.length - 1];
    }


}
