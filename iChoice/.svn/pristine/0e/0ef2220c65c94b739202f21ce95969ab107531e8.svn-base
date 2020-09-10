package com.choicemmed.ichoice.healthreport.commend;

import com.choicemmed.ichoice.healthcheck.entity.AvgData;
import com.choicemmed.ichoice.healthcheck.entity.AvgOxData;

import java.util.List;

import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.OxSpotData;

/**
 * @author Created by Jiang nan on 2019/12/6 13:33.
 * @description
 **/
public class AvgDataUtils {

    /**
     * 求血压数据平均值
     *
     * @param cbp1k1DataList
     * @return
     */
    public static AvgData getavgData(List<Cbp1k1Data> cbp1k1DataList) {
        int AvgSys = 0, AvgDia = 0, AvgPr = 0;
        int SumSys = 0, SumDia = 0, SumPr = 0;
        for (int i = 0; i < cbp1k1DataList.size(); i++) {
            SumSys += cbp1k1DataList.get(i).getSystolic();
            SumDia += cbp1k1DataList.get(i).getDiastolic();
            SumPr += cbp1k1DataList.get(i).getPulseRate();
        }
        AvgSys = (int) SumSys / cbp1k1DataList.size();
        AvgDia = (int) SumDia / cbp1k1DataList.size();
        AvgPr = (int) SumPr / cbp1k1DataList.size();
        return new AvgData(AvgSys, AvgDia, AvgPr);
    }

    /**
     * 求血氧点测数据平均值
     *
     * @param oxSpotDataList
     * @return
     */
    public static AvgOxData getAvgOxData(List<OxSpotData> oxSpotDataList) {
        int AvgOx = 0, AvgPR = 0, AvgRR = 0;
        int SumOx = 0, SumPR = 0, SumPI = 0, SumRR = 0;
        for (int i = 0; i < oxSpotDataList.size(); i++) {
            SumOx += oxSpotDataList.get(i).getBloodOxygen();
            SumPR += oxSpotDataList.get(i).getPulseRate();
            SumRR += oxSpotDataList.get(i).getRR();
        }
        AvgOx = (int) SumOx / oxSpotDataList.size();
        AvgPR = (int) SumPR / oxSpotDataList.size();
        AvgRR = (int) SumRR / oxSpotDataList.size();
        return new AvgOxData(AvgOx, AvgPR, AvgRR);

    }

}
