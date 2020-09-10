package com.choicemmed.ichoice.healthcheck.fragment.ecg.analyzer;

import android.util.Log;


public class EcgAnalyzer {

    public static EcgAnalyzerResult analyze(float[] ecg) {
        EcgAnalyzerResult result = new EcgAnalyzerResult();

        int frequency = 250;
        int data_len = 30 * frequency;
        float[] wav_coef = new float[7500];
        float[] _wav_coef = new float[7500];
        int qrs_num = 0;
        int step = 100;
        int[] qrs_w = new int[150];//QRS波位置
        float median_value = 0;
        int temp = 0;
        int[] wav_no = new int[400];
        int[] _wav_no = new int[400];
        double SDNN = 0;
        int[] RR = new int[150];
        ;//RR间期
        int matrix_size = 400;
        for (int i = 5; i < data_len - 5; i++) {
            wav_coef[i] = (ecg[i + 1] - ecg[i - 1]) * 0.60653067f
                    + (ecg[i + 2] - ecg[i - 2]) * 0.27067056f
                    + (ecg[i + 3] - ecg[i - 3]) * 0.033326991f
                    + (ecg[i + 4] - ecg[i - 4]) * 0.001341851f;
        }
        for (int i = 5; i < data_len - 5; i++) {
            _wav_coef[i] = Math.abs(wav_coef[i]);
        }
        for (int i = 0; i < 400; i++) {
            float max = 0;
            for (int j = 0; j < data_len / 2; j++) {
                if (_wav_coef[2 * j] > max) {
                    max = _wav_coef[2 * j];
                    temp = 2 * j;
                }

            }
            wav_no[i] = temp;
            _wav_coef[temp] = 0;
        }
        qrs_num = search_qrs250f30s(step, qrs_w, wav_coef, wav_no, matrix_size, data_len);//搜索心搏
        qrs_num = qrs_num <= 150 ? qrs_num : 150;
        for (int i = 0; i < qrs_num - 1; i++)//计算RR间期
        {
            if (i < 150) {
                RR[i] = qrs_w[i + 1] - qrs_w[i];
            }
        }
        int sum_temp = 0;
        for (int i = 0; i < qrs_num; i++) {
            sum_temp = sum_temp + RR[i];
        }
        if (qrs_num > 1) {
            result.setRr_average(sum_temp * 4 / (qrs_num - 1));
        }
        for (int i = 0; i < qrs_num - 1; i++)//RR间期平方和，因为SDNN单位是毫秒，所以采样率要转换
        {
//            SDNN = SDNN + 16 * RR[i] * RR[i];
            SDNN = SDNN + 16 * (4 * RR[i] - sum_temp * 4 / (qrs_num - 1)) * (4 * RR[i] - sum_temp * 4 / (qrs_num - 1));

        }
        if (qrs_num > 1) {
            SDNN = SDNN / (qrs_num - 1);
        }
        SDNN = Math.sqrt(SDNN);//标准差
        result.setSdnn((int) SDNN);
        int max_temp = -10000;
        int min_temp = 10000;
        for (int i = 0; i < qrs_num; i++) {
            if (RR[i] >= max_temp) {
                max_temp = RR[i];
            }
        }
        result.setRr_max(max_temp * 4);//最大RR间期
        for (int i = 0; i < qrs_num - 1; i++) {
            if (RR[i] <= min_temp) {
                if (RR[i] > 0) {
                    min_temp = RR[i];
                }
            }
        }
        Log.d("min_temp", "" + min_temp);
        result.setRr_min(min_temp * 4);//最小RR间期

        Log.d("result", result.toString());
        return result;
    }

    private static int search_qrs250f30s(int step, int[] qrs_w, float[] wav_coef, int[] index, int MatrixSize, int DataLength) {
        int qrs_num = 0;
        for (int i = 0; i <= MatrixSize - 2; i++) {
            for (int j = i + 1; j <= MatrixSize - 1; j++) {
                if ((Math.abs(index[i] - index[j]) <= step && index[i] > 0 && index[j] > 0) || index[j] < 25 || index[j] > DataLength - 60) {
                    index[j] = 0;
                }
            }
        }
        int j = 0;
        for (int i = 0; i < MatrixSize; i++) {
            if (index[i] > 0 && j < 150 && index[i] > 25 && index[i] < DataLength - 250) {
                qrs_w[j] = index[i];
                j += 1;
                qrs_num += 1;
            }
        }
        sort_descend_int(qrs_w, qrs_num);
        return (qrs_num);
    }

    //对整型数据按照从小到大顺序排序
    private static void sort_descend_int(int[] shuzu, int length) {
        int temp = 0;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (shuzu[j] > shuzu[j + 1]) {
                    temp = shuzu[j];
                    shuzu[j] = shuzu[j + 1];
                    shuzu[j + 1] = temp;
                }
            }
        }
    }
}
