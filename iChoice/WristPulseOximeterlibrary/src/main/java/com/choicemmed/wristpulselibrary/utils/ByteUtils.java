package com.choicemmed.wristpulselibrary.utils;

import java.math.BigInteger;
import java.util.Locale;

/**
 * @author Created by Jiang nan on 2020/1/11 11:35.
 * @description
 **/
public class ByteUtils {

    /**
     * byte数组转十六进制字符串
     * @param bytes
     * @return
     */
    public static String ScanBytes2HexString(byte[] bytes) {
        int len = bytes.length;
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = bytes[bytes.length - 1 - k];
        }
        String ret = "";
        for (int i = 0; i < len; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static String bytes2HexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xff);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result += hex.toLowerCase(Locale.getDefault());
        }
        return result;
    }



    /**
     * 十六进制字符串转byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length() / 2;
        char[] chars = hexString.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }

    /**
     * string二进制转十进制
     * @param binXStr
     * @return
     */
    public static int bin2DecXiao(String binXStr) {
        BigInteger bi = new BigInteger(binXStr, 2);
        return Integer.parseInt(bi.toString());
    }

    /**
     * byte 转 二进制string
     * @param b1
     * @return
     */
    public static String BytetoString(byte b1) {
        String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
        return s1;
    }


    /**
     * byte 转 int
     * @param b
     * @return
     */
    public static int BytetoInt(Byte b){
        return bin2DecXiao(BytetoString(b));
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String byteArrToBinStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * 删除指定位置字符
     * @param s
     * @param pos
     * @return
     */
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    /**
     * 生成16进制累加和校验码
     *
     * @param data 除去校验位的数据
     * @return
     */
    public static String makeChecksum(String data) {
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        //如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }
}
