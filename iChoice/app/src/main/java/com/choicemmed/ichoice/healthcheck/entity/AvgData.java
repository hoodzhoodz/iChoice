package com.choicemmed.ichoice.healthcheck.entity;

/**
 * @author Created by Jiang nan on 2019/12/3 17:16.
 * @description
 **/
public class AvgData {
    private int SysAvg = 0;
    private int DiaAvg = 0;
    private int PrAvg = 0;

    public AvgData(int sysAvg, int diaAvg, int prAvg) {
        SysAvg = sysAvg;
        DiaAvg = diaAvg;
        PrAvg = prAvg;
    }

    public int getSysAvg() {
        return SysAvg;
    }

    public void setSysAvg(int sysAvg) {
        SysAvg = sysAvg;
    }

    public int getDiaAvg() {
        return DiaAvg;
    }

    public void setDiaAvg(int diaAvg) {
        DiaAvg = diaAvg;
    }

    public int getPrAvg() {
        return PrAvg;
    }

    public void setPrAvg(int prAvg) {
        PrAvg = prAvg;
    }

    @Override
    public String toString() {
        return "AvgData{" +
                "SysAvg=" + SysAvg +
                ", DiaAvg=" + DiaAvg +
                ", PrAvg=" + PrAvg +
                '}';
    }
}
