package com.choice.c208sdkblelibrary.cmd.parse;

import java.util.HashMap;
import java.util.Map;

public class C208ParseUtils {

    /**
     * 测量结果指令标识
     */
    private static final String MEASURE_RESULT = "55aa03";

    /**
     * 测量数据解析
     *
     * @param result 待解析数据
     */
    public synchronized static Map parseData(String result) {
        if (result.startsWith(MEASURE_RESULT)) {
            int ox = Integer.parseInt(result.substring(6, 8), 16);
            int pr = Integer.parseInt(result.substring(8, 10), 16);
            float pi = 0;
            Map<String, String> map = new HashMap<>(3);
            map.put("ox", ox + "");
            map.put("pr", pr + "");
            map.put("pi", pi + "");
            return map;
        }
        return null;
    }
}
