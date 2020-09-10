package com.choicemmed.common;

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

      public static byte[] cs1toBytes(String cmd, byte check) {

            String[] ss = cmd.split(",");
            byte[] bytes = new byte[ss.length * 2 + 1 + 4];
            bytes[0] = (byte) Integer.parseInt("AA", 16);
            bytes[1] = (byte) Integer.parseInt("55", 16);
            bytes[2] = (byte) Integer.parseInt("E9", 16);
            bytes[3] = (byte) Integer.parseInt("19", 16);


            for(int i=0;i<ss.length;i++){
                  short parseShort = Short.parseShort(ss[i]);
                  bytes[i+i+4] = (byte) parseShort;
                  bytes[i+i+4+1] = (byte) (parseShort>>8);

            }

//            for (int i = 4, j = 0; j < ss.length / 2; i += 2, j++) {
//                  short parseShort = Short.parseShort(ss[i]);
//                  bytes[i] = (byte) parseShort;
//                  bytes[i + 1] = (byte) (parseShort >> 8);
//            }
            bytes[ss.length * 2+4] = check;
            return bytes;
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
       * byte数组转十六进制字符串
       *
       * @param bytes
       * @return
       */
      public static String bytes2HexString(byte[] bytes, int len) {
            String result = "";
            for (int i = 0; i < len; i++) {
                  String hex = Integer.toHexString(bytes[i] & 0xff);
                  if (hex.length() == 1) {
                        hex = '0' + hex;
                  }
                  result += hex.toLowerCase(Locale.getDefault());
            }
            return result;
      }

      /**
       * @param bArray
       * @return
       */
      public static final String bytesToHexString(byte[] bArray, int len) {
            StringBuffer sb = new StringBuffer(bArray.length);
            String sTemp;
            for (int i = 0; i < len; i++) {
                  sTemp = Integer.toHexString(0xFF & bArray[i]);
                  if (sTemp.length() < 2)
                        sb.append(0);
                  sb.append(sTemp.toUpperCase());
            }
            return sb.toString();
      }


      /**
       * 十六进制字符串转byte数组
       * 不能直接string.getBytes() 111行是关键,通过parseInt 再转byte
       *
       * @param hexString
       * @return
       */
      public static byte[] hexString2Bytes(String hexString) {
            // 因为一个字节标示两个数
            int len = hexString.length() / 2;
            char[] chars = hexString.toCharArray();
            String[] hexStr = new String[len];
            // 因为一个字节标示两个数
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
       * short转字节数组，低位在前，高位在后
       *
       * @param s
       * @return
       */
      public static byte[] short2Bytes(short s) {
            byte[] buf = new byte[2];
            for (int i = 0; i < buf.length; i++) {
                  buf[i] = (byte) (s & 0x00ff);
                  s >>= 8;
            }
            return buf;
      }


      /**
       * 16进制字符串转2进制字符串
       *
       * @param hexString
       * @return
       */
      public static String hexString2binaryString(String hexString) {
            if (hexString == null || hexString.length() % 2 != 0)
                  return null;
            String bString = "", tmp;
            for (int i = 0; i < hexString.length(); i++) {
                  tmp = "0000"
                                        + Integer.toBinaryString(Integer.parseInt(hexString
                                        .substring(i, i + 1), 16));
                  bString += tmp.substring(tmp.length() - 4);
            }
            return bString;
      }


}
