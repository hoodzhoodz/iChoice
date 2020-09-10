package com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter;

import android.graphics.Color;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.custom.Spo2YFormatter;
import com.choicemmed.ichoice.healthcheck.custom.TimeXFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import pro.choicemmed.datalib.OxRealTimeData;
import pro.choicemmed.datalib.OxRealTimeDataDao;

import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;


public class OXRealCheckChartActivity extends BaseActivty {
    static String TAG = OXRealCheckChartActivity.class.getSimpleName();
    @BindView(R.id.spo2_chart)
    LineChart spo2Chart;
    @BindView(R.id.pluse_rate_chart)
    LineChart pluseRateChart;
    @BindView(R.id.rr_chart)
    LineChart rrChart;
    private OxRealTimeData oxRealTimeData;

    List<Entry> spEntries = new ArrayList<>();
    List<Entry> prEntries = new ArrayList<>();
    List<Entry> rrEntries = new ArrayList<>();
    LineDataSet spSet, prSet, rrSet;
    Calendar mCalendar;
    int x;
    int lastIndex;
    private int prMin;
    private int prMax;


    @Override
    protected int contentViewID() {
        return R.layout.activity__ox_real_chart;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.real_time_measurement), true);
        setLeftBtnFinish();
        oxRealTimeData = getDaoSession(this).getOxRealTimeDataDao().queryBuilder().where(OxRealTimeDataDao.Properties.Id.eq(getIntent().getExtras().getString("uuid"))).unique();
        String[] data = oxRealTimeData.getSeries().split("\\|");
        for (int i = 0; i < data.length; i++) {
            String[] s = data[i].split(",");
            int x = Integer.parseInt(s[0]);
            int spo2 = Integer.parseInt(s[1]);
            int pr = Integer.parseInt(s[2]);
            int rr = Integer.parseInt(s[4]);
            spEntries.add(new Entry((x * 1.0f) / 120, spo2));
            prEntries.add(new Entry((x * 1.0f) / 120, pr));
            if (rr != 0) {
                rrEntries.add(new Entry((x * 1.0f) / 120, rr));
            }
            checkPrLimit(pr);
        }
        lastIndex = Integer.parseInt(data[data.length - 1].split(",")[0]);
        mCalendar = Calendar.getInstance();
        Date start = FormatUtils.parseDate(oxRealTimeData.getMeasureDateStartTime(), FormatUtils.template_DbDateTime);
        Date end = FormatUtils.parseDate(oxRealTimeData.getMeasureDateEndTime(), FormatUtils.template_DbDateTime);
        mCalendar.setTime(start);
        int during = (int) (end.getTime() - start.getTime()) / 1000;
            if (during % 60 != 0) {
                x = (during / 60) + 1;
                if (x % 2 != 0) {
                    x = x + 1;
                }
            } else {
                x = during / 60;
            }

        initChart();
    }

    private void checkPrLimit(int pr) {
        //第一组数据
        if (prMin == 0 && prMax == 0) {
            prMax = pr;
            prMin = pr;
            return;
        }
        prMax = pr > prMax ? pr : prMax;
        prMin = pr < prMin ? pr : prMin;

        //上限取大于等于当前数最接近的10的整数倍，如98取100、90取90;
        //下限取小于等于当前数最接近的10的整数倍，如65取60、60取60;
        prMin = prMin % 10 == 0 ? prMin : prMin / 10 * 10;
        prMax = prMax % 10 == 0 ? prMax : prMax / 10 * 10 + 10;
    }
    private void initChart() {
        setSpO2Chart();
        setPluseRateChart();
        setRRChart();
    }

    private void setRRChart() {
        rrChart.setBackgroundColor(Color.WHITE);
        rrChart.setDrawGridBackground(true);
        rrChart.setDrawBorders(false);
        rrChart.setGridBackgroundColor(Color.WHITE);
        rrChart.getDescription().setEnabled(false);
        rrChart.setScaleEnabled(false);
        rrChart.getAxisRight().setEnabled(false);

        YAxis rrChartAxisLeft = rrChart.getAxisLeft();
        rrChartAxisLeft.setAxisMaximum(80);
        rrChartAxisLeft.setAxisMinimum(0);
        rrChartAxisLeft.setLabelCount(8);

        XAxis rrChartXAxis = rrChart.getXAxis();
        rrChartXAxis.setTextSize(8f);
        rrChartXAxis.setAxisMaximum(x / 2);
        rrChartXAxis.setAxisMinimum(0);
            rrChartXAxis.setLabelCount(3);
        rrChart.setVisibleXRange(0, 3);
        rrChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        rrChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));

        rrSet = new LineDataSet(rrEntries, getString(R.string.rr));
        rrSet.setColor(Color.rgb(138, 43, 226));
        rrSet.setDrawCircles(false);
        rrSet.setDrawValues(false);
        LineData splineData = new LineData(rrSet);
        rrChart.setData(splineData);
    }

    private void setSpO2Chart() {
        spo2Chart.setBackgroundColor(Color.WHITE);
        spo2Chart.setDrawGridBackground(true);
        spo2Chart.setDrawBorders(false);
        spo2Chart.setGridBackgroundColor(Color.WHITE);
        spo2Chart.setScaleEnabled(false);
        spo2Chart.getDescription().setEnabled(false);
        spo2Chart.getAxisRight().setEnabled(false);

        YAxis spo2ChartAxisLeft = spo2Chart.getAxisLeft();
        spo2ChartAxisLeft.setAxisMaximum(100);
        spo2ChartAxisLeft.setAxisMinimum(90);
        spo2ChartAxisLeft.setLabelCount(5);
        spo2ChartAxisLeft.setValueFormatter(new Spo2YFormatter());

        XAxis spo2ChartXAxis = spo2Chart.getXAxis();
        spo2ChartXAxis.setTextSize(8f);
        spo2ChartXAxis.setAxisMinimum(0f);
        spo2ChartXAxis.setAxisMaximum(x / 2);
        spo2Chart.setVisibleXRange(0, 3);
        spo2ChartXAxis.setLabelCount(3);
        spo2ChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        spo2ChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));
        spSet = new LineDataSet(spEntries, getString(R.string.spo2));
        spSet.setColor(Color.rgb(138, 43, 226));
        spSet.setDrawCircles(false);
        spSet.setDrawValues(false);
        LineData splineData = new LineData(spSet);
        spo2Chart.setData(splineData);
//        spo2Chart.fitScreen();
    }

    private void setPluseRateChart() {
        pluseRateChart.setBackgroundColor(Color.WHITE);
        pluseRateChart.setDrawGridBackground(true);
        pluseRateChart.setDrawBorders(false);
        pluseRateChart.setGridBackgroundColor(Color.WHITE);
        pluseRateChart.getDescription().setEnabled(false);
        pluseRateChart.setScaleEnabled(false);
        pluseRateChart.getAxisRight().setEnabled(false);

        YAxis pluseRateChartAxisLeft = pluseRateChart.getAxisLeft();
//        默认坐标轴50-120
        if (prMin >= 50 && prMax <= 120) {
            pluseRateChartAxisLeft.setAxisMaximum(120);
            pluseRateChartAxisLeft.setAxisMinimum(50);
            pluseRateChartAxisLeft.setLabelCount(7);
        } else {
            pluseRateChartAxisLeft.setAxisMaximum(prMax);
            pluseRateChartAxisLeft.setAxisMinimum(prMin);
            pluseRateChartAxisLeft.setLabelCount((prMax - prMin) / 10);
        }


        XAxis pluseRateChartXAxis = pluseRateChart.getXAxis();
        pluseRateChartXAxis.setTextSize(8f);
        pluseRateChartXAxis.setAxisMaximum(x / 2);
        pluseRateChartXAxis.setAxisMinimum(0);
        pluseRateChartXAxis.setLabelCount(3);
        pluseRateChart.setVisibleXRange(0, 3);

        pluseRateChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        pluseRateChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));


        prSet = new LineDataSet(prEntries, getString(R.string.pulse_rate));
        prSet.setColor(Color.rgb(138, 43, 226));
        prSet.setDrawCircles(false);
        prSet.setDrawValues(false);
        LineData prlineData = new LineData(prSet);
        pluseRateChart.setData(prlineData);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
