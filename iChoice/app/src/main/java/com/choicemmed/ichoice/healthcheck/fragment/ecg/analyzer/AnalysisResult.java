package com.choicemmed.ichoice.healthcheck.fragment.ecg.analyzer;

public class AnalysisResult {
    private String HR;
    private String avaRR;
    private String maxRR;
    private String minRR;
    private String SDNN;
    private String startTime;
    private int baseLine;

    public String getHR() {
        return HR;
    }

    public void setHR(String HR) {
        this.HR = HR;
    }

    public String getAvaRR() {
        return avaRR;
    }

    public void setAvaRR(String avaRR) {
        this.avaRR = avaRR;
    }

    public String getMaxRR() {
        return maxRR;
    }

    public void setMaxRR(String maxRR) {
        this.maxRR = maxRR;
    }

    public String getMinRR() {
        return minRR;
    }

    public void setMinRR(String minRR) {
        this.minRR = minRR;
    }

    public String getSDNN() {
        return SDNN;
    }

    public void setSDNN(String SDNN) {
        this.SDNN = SDNN;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(int baseLine) {
        this.baseLine = baseLine;
    }
}
