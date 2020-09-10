package com.choice.c208sdkblelibrary.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class ByteUtils {
    public static byte[] cmdString2Bytes(String cmd, boolean withSumCode) {
        byte[] value = hexString2Bytes(cmd);
        if (withSumCode) {
            byte verifySum = 0;
            for (int i = 2; i < value.length; i++) {
                verifySum += value[i];
            }
            byte[] values = new byte[value.length + 1];
            for (int i = 0; i < value.length; i++) {
                values[i] = value[i];
            }
            values[value.length] = verifySum;
            return values;
        } else {
            return value;
        }
    }

    /**
     * 十六进制字符串倒叙
     *
     * @param hexString
     * @return
     */
    public static String hexStringReverse(String hexString) {
        if (hexString.length() % 2 != 0)
            return null;
        int len = hexString.length() / 2;
        char[] chars = hexString.toCharArray();
        String[] hexStr = new String[len];
        String result = "";
        for (int i = 0, j = len - 1; j >= 0; i += 2, j--) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            result = hexStr[j] + result;
        }

        return result;
    }
    public static byte[] reverseBytes(byte[] a) {
        int len = a.length;
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = a[a.length - 1 - k];
        }
        return b;
    }

    /**
     * byte数组转十六进制字符串
     *
     * @param bytes
     * @return
     */
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
     * 十六进制字符串转ArrayList<Byte>
     *
     * @param hexString
     * @return
     */
    public static ArrayList<Byte> hexString2List(String hexString) {
        int len = hexString.length() / 2;
        char[] chars = hexString.toCharArray();
        String[] hexStr = new String[len];
        ArrayList<Byte> list = new ArrayList<Byte>();
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            list.add((byte) Integer.parseInt(hexStr[j], 16));
        }
        return list;
    }

    /**
     * 根据给定的补位符格式化时间，实现字节补位，如：aab--->0aab
     *
     * @param date     格式化时间
     * @param coverBit 补位
     * @return 格式化后的时间
     */
    public static String coverByte(String date, String coverBit) {
        if (date.length() % 2 != 0)
            date = String.format(Locale.getDefault(), "%s%s", coverBit, date);
        return date;
    }

    /**
     * 十六进制转二进制
     *
     * @param hexString 十六进制字符串
     * @return 二进制字符串
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

}
