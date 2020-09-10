package com.choicemmed.ichoice.framework.utils;

import android.util.Log;

import com.choicemmed.common.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:zheng zhong on 2016/4/26 16:22
 */
public class ORECalculate {
    private static final String TAG = "ORECalculate";

    public static int oreCountTimes(String series) {
//        LogUtils.d(TAG,series);0:100@60#16,1:100@60#16,2:100@60#16,3:100@60#16
//        series = "0:100@60#16,9:85@60#16,10:100@60#16,";
        int lastTime = 0;
        int lastSpo = 0;
        int lastPr = 0;
        String[] result = series.split(",");
        List<String> fillAfterData = new ArrayList<>();
        if ("".equals(series)){
            return 0;
        }
        for (int i = 0; i < result.length; i++) {
            String each = result[i];
            String[] eachData = each.split(":");
            int time = Integer.parseInt(eachData[0]);
            String SpoAndPr = eachData[1];
            String[] data = SpoAndPr.split("@");
            int spo = Integer.parseInt(data[0]);
            if (i == 0) {
                lastTime = time;
                lastSpo = spo;
            }
            if (i != 0 && time - lastTime > 1 && spo <= lastSpo) {
                int suspendTime = time - lastTime;
                float spoChange = lastSpo - spo;
                float average = spoChange / suspendTime;
                int addSpo = 0;
                int addPr = 10;
                float last = lastSpo;
                for (int j = 0; j < suspendTime - 1; j++) {
                    lastTime++;
                    last = last - average;
                    addSpo = Math.round(last);
                    String addData = lastTime + ":" + addSpo + "@" + addPr;
                    fillAfterData.add(addData);
                }
                lastSpo = addSpo;
            }
            lastTime = time;
            lastSpo = spo;
            fillAfterData.add(result[i]);
        }
//        for (String s : fillAfterData) {
//            LogUtils.d(TAG,s);
//        }

        return OreTimes(fillAfterData);
    }


    private static int OreTimes(List<String> fillAfterData) {
        int SpoData[] = new int[fillAfterData.size()];
        int i = 0;
        for (String data : fillAfterData) {
            String[] eachData = data.split(":");
            String SpoAndPr = eachData[1];
            String[] sleepData = SpoAndPr.split("@");
            int spo = Integer.parseInt(sleepData[0]);
            SpoData[i] = spo;
            i++;
        }
//        SpoData[fillAfterData.size()] = 200;
        int headSpo = SpoData[0];
        int headIndex = 0;
        int OreTimes = 0;

        for (int j = 1; j < SpoData.length; j++) {
            if (SpoData[j - 1] < SpoData[j]) {
                if ((headSpo - SpoData[j - 1]) >= 4) {
                    if ((j - 1 - headIndex + 1) <= 180 && (j - 1 - headIndex + 1) >= 10) {
                        OreTimes++;
                    } else if ((j - 1 - headIndex + 1) > 180) {
                        if ((SpoData[j - 1] - headSpo) >= 4) {
                            OreTimes++;
                        }
                    }
                }
                headSpo = SpoData[j];
                headIndex = j;
            }

        }
//        LogUtils.d(TAG, OreTimes+"");
        return OreTimes;
    }

    public static int getMinSpo(String series) {
        int lastTime = 0;
        int lastSpo = 0;
        int lastPr = 0;
        if ("".equals(series)){
            return 91;
        }
        String[] result = series.split(",");
        List<String> fillAfterData = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            String each = result[i];
            String[] eachData = each.split(":");
            int time = Integer.parseInt(eachData[0]);
            String SpoAndPr = eachData[1];
            String[] data = SpoAndPr.split("@");
            int spo = Integer.parseInt(data[0]);
            if (i == 0) {
                lastTime = time;
                lastSpo = spo;
            }
            if (i != 0 && time - lastTime > 1 && spo <= lastSpo) {
                int suspendTime = time - lastTime;
                float spoChange = lastSpo - spo;
                float average = spoChange / suspendTime;
                int addSpo = 0;
                int addPr = 10;
                float last = lastSpo;
                for (int j = 0; j < suspendTime - 1; j++) {
                    lastTime++;
                    last = last - average;
                    addSpo = Math.round(last);
                    String addData = lastTime + ":" + addSpo + "@" + addPr;
                    fillAfterData.add(addData);
                }
                lastSpo = addSpo;
            }
            lastTime = time;
            lastSpo = spo;
            fillAfterData.add(result[i]);
        }
        return minSpo(fillAfterData);
    }


    private static int minSpo(List<String> fillAfterData) {
        int SpoData[] = new int[fillAfterData.size()];
        int i = 0;
        for (String data : fillAfterData) {
            String[] eachData = data.split(":");
            String SpoAndPr = eachData[1];
            String[] sleepData = SpoAndPr.split("@");
            int spo = Integer.parseInt(sleepData[0]);
            SpoData[i] = spo;
            i++;
        }
        int minSpo = SpoData[0];
        for (int j = 1; j < SpoData.length; j++) {
            minSpo = Math.min(minSpo, SpoData[j]);
        }
        return minSpo;
    }

}
