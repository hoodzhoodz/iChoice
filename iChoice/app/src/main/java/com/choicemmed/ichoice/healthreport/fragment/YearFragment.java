package com.choicemmed.ichoice.healthreport.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.activity.CalenderSelectActivity;
import com.choicemmed.ichoice.healthcheck.custom.YearFormatter;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;
import com.choicemmed.ichoice.healthcheck.db.TemperatureOperation;
import com.choicemmed.ichoice.healthcheck.entity.AvgData;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.entity.AvgOxData;
import com.choicemmed.ichoice.healthreport.commend.AvgDataUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.OxSpotData;

import static com.choicemmed.common.FormatUtils.template_DbDateTime;
import static com.choicemmed.common.FormatUtils.template_Month;

public class YearFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.year_year)
    TextView tv_year;
    @BindView(R.id.year_left)
    ImageView calendar_left;
    @BindView(R.id.year_right)
    ImageView calendar_right;
    @BindView(R.id.year_chart)
    LineChart chart;
    @BindView(R.id.bp_year_line)
    CardView bp_line;
    /**
     * 血氧仪历史记录趋势图
     */
    @BindView(R.id.ll_pulse_oximeter_trend)
    LinearLayout llPulseOximeterTrend;
    @BindView(R.id.ox_spo2_chart)
    LineChart spo2Chart;
    @BindView(R.id.ox_pr_chart)
    LineChart prChart;
    @BindView(R.id.ox_rr_chart)
    LineChart rrChart;

    @BindView(R.id.temp_chart)
    LineChart temp_chart;
    @BindView(R.id.temp_card_view)
    CardView temp_card_view;
    @BindView(R.id.tv_unit)
    TextView tv_unit;

    Calendar beginCalendar;
    private Calendar endCalendar = Calendar.getInstance();
    private int unit;
    TemperatureOperation temperatureOperation;
    Map<String, CFT308Data> cft308DataHashMapC = new HashMap<>();
    Map<Integer, CFT308Data> cft308DataMap = new HashMap<>();
    List<Entry> entriesUnitC = new ArrayList<>();
    List<Entry> entriesUnitF = new ArrayList<>();

    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    private Calendar calendar = Calendar.getInstance();
    private static String endDate;
    List<AvgData> avgDataList = new ArrayList<>();
    private Cbp1k1Operation cbp1k1Operation;
    private OxSpotOperation oxSpotOperation;

    Map<Integer, AvgOxData> avgOxDataMap = new HashMap<>();

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                if (calendar.get(Calendar.YEAR) != year) {
                    calendar.set(year, Calendar.JANUARY, 1);
                    MoveToTempChart(calendar);
                    setTextDate();
                    setBpChartData();
                    setPulseOximeterChartData();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_year2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initialize() {
        initData();
        initReceiver();
        setTextDate();
        initLine();
    }

    private void initData() {
        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        beginDate = FormatUtils.getDateTimeString(Long.parseLong(IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().substring(6, IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().length() - 2)), FormatUtils.template_DbDateTime);
        cbp1k1Operation = new Cbp1k1Operation(getContext());
        oxSpotOperation = new OxSpotOperation(getContext());
        unit = (int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1);
        temperatureOperation = new TemperatureOperation(getContext());
        beginCalendar = DateUtils.strToCalendar(beginDate);
        beginCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
        beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
        beginCalendar.set(Calendar.MINUTE, 0);
        beginCalendar.set(Calendar.SECOND, 0);

        endCalendar.add(Calendar.YEAR, 1);
        endCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        endCalendar.set(Calendar.DAY_OF_MONTH, 1);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private void initLine() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(getActivity());
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (deviceDisplay == null) {
            bp_line.setVisibility(View.GONE);
            llPulseOximeterTrend.setVisibility(View.GONE);
            temp_card_view.setVisibility(View.GONE);
            return;
        }

        if (deviceDisplay.getThermometer() == 0) {
            temp_card_view.setVisibility(View.GONE);
        } else {
            temp_card_view.setVisibility(View.VISIBLE);
            initTempChart();
            setTempChartData();
        }
        if (deviceDisplay.getBloodPressure() == 0) {
            bp_line.setVisibility(View.GONE);
        } else {
            bp_line.setVisibility(View.VISIBLE);
            initBpChart();
            setBpChartData();
        }

        if (deviceDisplay.getPulseOximeter() == 0) {
            llPulseOximeterTrend.setVisibility(View.GONE);
        } else {
            llPulseOximeterTrend.setVisibility(View.VISIBLE);
            initPulseOximeterChart();
            setPulseOximeterChartData();
        }

    }

    private void setTempChartData() {
        List<CFT308Data> cft308DataList = temperatureOperation.queryAVGDataByMonth(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!cft308DataList.isEmpty()) {
            for (int i = 0; i < cft308DataList.size(); i++) {
                String startTime = cft308DataList.get(i).getMeasureTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(startTime, template_DbDateTime));

                int startX = DateUtils.differentMonthCalendar(beginCalendar, calendar1);//计算差多少月
                LogUtils.d(TAG, " startTime " + startTime + "   startX  " + startX + "  getTemp  " + cft308DataList.get(i).getTemp());
                entriesUnitC.add(new Entry(startX, Float.parseFloat(cft308DataList.get(i).getTemp())));
                cft308DataMap.put(startX, cft308DataList.get(i));
                String f = String.valueOf((Float.parseFloat(cft308DataList.get(i).getTemp()) * 9 / 5) + 32);
                DecimalFormat df = new DecimalFormat("#.0");
                df.setRoundingMode(RoundingMode.DOWN);
                f = df.format(Float.parseFloat(f));
                entriesUnitF.add(new Entry(startX, Float.parseFloat(f)));
                String everyDayLastMeasureTime = FormatUtils.getDateTimeString1(calendar1.getTimeInMillis(), template_Month);
                cft308DataHashMapC.put(everyDayLastMeasureTime, cft308DataList.get(i));
                LogUtils.d(TAG, "everyDayLastMeasureTime   " + everyDayLastMeasureTime);
            }
        }
        calculationYValue();
    }

    private void calculationYValue() {
        LineDataSet dataSet;
        if (unit == 1) {
            dataSet = new LineDataSet(entriesUnitC, "");
            tv_unit.setText(getString(R.string.temp_unit));
            YAxis leftAxis = temp_chart.getAxisLeft();
            leftAxis.setAxisMaximum(43);
            leftAxis.setAxisMinimum(32);
            leftAxis.setLabelCount(5);
            YAxis rightAxis = temp_chart.getAxisRight();
            rightAxis.setAxisMaximum(43);
            rightAxis.setAxisMinimum(32);
            rightAxis.setLabelCount(5);
        } else {
            dataSet = new LineDataSet(entriesUnitF, "");
            tv_unit.setText(getString(R.string.temp_unit1));
            YAxis leftAxis = temp_chart.getAxisLeft();
            leftAxis.setAxisMaximum(110);
            leftAxis.setAxisMinimum(85);
            leftAxis.setLabelCount(5);
            YAxis rightAxis = temp_chart.getAxisRight();
            rightAxis.setAxisMaximum(110);
            rightAxis.setAxisMinimum(85);
            rightAxis.setLabelCount(5);
        }
        int colorId = getActivity().getResources().getColor(R.color.blue_8c9eff);
        dataSet.setDrawCircleHole(false);
        dataSet.setColor(colorId);
        dataSet.setCircleColor(colorId);
        dataSet.setLineWidth(1f);
        dataSet.setCircleSize(3f);
        LineData spo2Data = new LineData(dataSet);
        temp_chart.setData(spo2Data);


        temp_chart.invalidate();
        MoveToTempChart(calendar);
    }

    private void MoveToTempChart(Calendar calendar1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar1.getTimeInMillis());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int startX = DateUtils.differentMonthCalendar(beginCalendar, calendar);
        Log.d(TAG, "MoveToTempChart    " + FormatUtils.getDateTimeString(calendar.getTimeInMillis(), FormatUtils.template_DbDateTime) + " startX " + startX);
        temp_chart.moveViewToX(startX);
    }

    private void initTempChart() {
        temp_chart.setBackgroundColor(Color.WHITE);
        temp_chart.setDrawGridBackground(true);
        temp_chart.setDrawBorders(false);
        temp_chart.setGridBackgroundColor(Color.WHITE);
        temp_chart.getDescription().setEnabled(false);
        temp_chart.setScaleEnabled(false);
        temp_chart.setTouchEnabled(true);
        temp_chart.getAxisRight().setEnabled(false);
        temp_chart.setDragXEnabled(false);

        XAxis xAxis = temp_chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12 * (endCalendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR)));
        YearFormatter yearFormatter = new YearFormatter();
        xAxis.setValueFormatter(yearFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        temp_chart.setVisibleXRange(0, 12);
        xAxis.setLabelCount(12);
        Description description = new Description();
        description.setText("(h)");
        temp_chart.setDescription(description);
        Legend legend = temp_chart.getLegend();
        legend.setEnabled(false);

        //90~100 间隔2
        YAxis leftAxis = temp_chart.getAxisLeft();
        leftAxis.setAxisMaximum(43);
        leftAxis.setAxisMinimum(32);
        leftAxis.setLabelCount(5);
        YAxis rightAxis = temp_chart.getAxisRight();
        rightAxis.setAxisMaximum(43);
        rightAxis.setAxisMinimum(32);
        rightAxis.setLabelCount(5);
    }


    private void initPulseOximeterChart() {
        initSpO2Chart();
        initPRChart();
        initRRChart();
    }

    /**
     * 初始化Spo2折线图
     */
    private void initSpO2Chart() {
        spo2Chart.setBackgroundColor(Color.WHITE);
        spo2Chart.setDrawGridBackground(true);
        spo2Chart.setDrawBorders(false);
        spo2Chart.setGridBackgroundColor(Color.WHITE);
        spo2Chart.getDescription().setEnabled(false);
        spo2Chart.setScaleEnabled(false);
        spo2Chart.getAxisRight().setEnabled(false);
        spo2Chart.setTouchEnabled(false);

        XAxis xAxis = spo2Chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f);

        xAxis.setValueFormatter(new YearFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        spo2Chart.setVisibleXRange(0, 12);
        xAxis.setLabelCount(12);

        Legend legend = spo2Chart.getLegend();
        legend.setEnabled(false);

        //90~100 间隔2
        YAxis leftAxis = spo2Chart.getAxisLeft();
        leftAxis.setAxisMaximum(100);
        leftAxis.setAxisMinimum(90);
        leftAxis.setLabelCount(6, true);
        YAxis rightAxis = spo2Chart.getAxisRight();
        rightAxis.setAxisMaximum(100);
        rightAxis.setAxisMinimum(90);
        rightAxis.setLabelCount(6, true);
    }

    /**
     * 初始化PR折线图
     */
    private void initPRChart() {
        prChart.setBackgroundColor(Color.WHITE);
        prChart.setDrawGridBackground(true);
        prChart.setDrawBorders(false);
        prChart.setGridBackgroundColor(Color.WHITE);
        prChart.getDescription().setEnabled(false);
        prChart.setScaleEnabled(false);
        prChart.getAxisRight().setEnabled(false);
        prChart.setTouchEnabled(false);

        XAxis xAxis_pr = prChart.getXAxis();
        xAxis_pr.setTextSize(12f);
        xAxis_pr.setAxisMinimum(0f);
        xAxis_pr.setAxisMaximum(12f);

        xAxis_pr.setValueFormatter(new YearFormatter());
        xAxis_pr.setPosition(XAxis.XAxisPosition.BOTTOM);
        prChart.setVisibleXRange(0, 12);
        xAxis_pr.setLabelCount(12);

        Legend legend = prChart.getLegend();
        legend.setEnabled(false);

        //50~120 间隔10
        YAxis leftAxis_pr = prChart.getAxisLeft();
        leftAxis_pr.setAxisMaximum(120);
        leftAxis_pr.setAxisMinimum(50);
        leftAxis_pr.setLabelCount(8, true);
        YAxis rightAxis_pr = prChart.getAxisRight();
        rightAxis_pr.setAxisMaximum(120);
        rightAxis_pr.setAxisMinimum(50);
        rightAxis_pr.setLabelCount(8, true);

    }

    /**
     * 初始化RR折线图
     */
    private void initRRChart() {
        rrChart.setBackgroundColor(Color.WHITE);
        rrChart.setDrawGridBackground(true);
        rrChart.setDrawBorders(false);
        rrChart.setGridBackgroundColor(Color.WHITE);
        rrChart.getDescription().setEnabled(false);
        rrChart.setScaleEnabled(false);
        rrChart.getAxisRight().setEnabled(false);
        rrChart.setTouchEnabled(false);

        //24小时 间隔2h
        XAxis xAxis_pr = rrChart.getXAxis();
        xAxis_pr.setTextSize(12f);
        xAxis_pr.setAxisMinimum(0f);
        xAxis_pr.setAxisMaximum(12f);

        xAxis_pr.setValueFormatter(new YearFormatter());
        xAxis_pr.setPosition(XAxis.XAxisPosition.BOTTOM);
        rrChart.setVisibleXRange(0, 12);
        xAxis_pr.setLabelCount(12);

        Legend legend = rrChart.getLegend();
        legend.setEnabled(false);

        //0~70 间隔10
        YAxis leftAxis_rr = rrChart.getAxisLeft();
        leftAxis_rr.setAxisMaximum(80);
        leftAxis_rr.setAxisMinimum(0);
        leftAxis_rr.setLabelCount(8);
        YAxis rightAxis_rr = rrChart.getAxisRight();
        rightAxis_rr.setAxisMaximum(80);
        rightAxis_rr.setAxisMinimum(0);
        rightAxis_rr.setLabelCount(8);

    }

    private void setPulseOximeterChartData() {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        List<Entry> spo2List = new ArrayList<>();
        List<Entry> prList = new ArrayList<>();
        List<Entry> rrList = new ArrayList<>();
        List<AvgOxData> avgOxDataList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            String dateTimeString = FormatUtils.getDateTimeString(calendar2.getTime(), FormatUtils.template_Month);
            List<OxSpotData> oxSpotDataList = oxSpotOperation.queryByDate(dateTimeString, IchoiceApplication.getAppData().userProfileInfo.getUserId());
            if (!oxSpotDataList.isEmpty() && oxSpotDataList.size() > 0) {
                AvgOxData avgOxData = AvgDataUtils.getAvgOxData(oxSpotDataList);
                avgOxDataMap.put(i, avgOxData);
                avgOxDataList.add(avgOxData);
                spo2List.add(new Entry(i, avgOxData.getSpO2Avg()));
                prList.add(new Entry(i, avgOxData.getPRAvg()));
                if (avgOxData.getRRAvg() > 0) {
                    rrList.add(new Entry(i, avgOxData.getRRAvg()));
                }
            }
            calendar2.add(Calendar.MONTH, 1);
        }

        //图表数据填充
        LineDataSet spo2Set, prSet, rrSet;
        int colorId = getActivity().getResources().getColor(R.color.pulse_oximeter_blue);
        if (spo2Chart.getData() != null && spo2Chart.getData().getDataSetCount() > 0) {
            spo2Set = (LineDataSet) spo2Chart.getData().getDataSetByIndex(0);
            spo2Set.setValues(spo2List);
            spo2Chart.getData().notifyDataChanged();
            spo2Chart.notifyDataSetChanged();
        } else {
            spo2Set = new LineDataSet(spo2List, "");
            spo2Set.setDrawCircleHole(false);
            spo2Set.setColor(colorId);
            spo2Set.setCircleColor(colorId);
            spo2Set.setLineWidth(1f);
            spo2Set.setCircleSize(3f);
            LineData spo2Data = new LineData(spo2Set);
            spo2Chart.setData(spo2Data);
        }

        if (prChart.getData() != null && prChart.getData().getDataSetCount() > 0) {
            prSet = (LineDataSet) prChart.getData().getDataSetByIndex(0);
            prSet.setValues(prList);
            prChart.getData().notifyDataChanged();
            prChart.notifyDataSetChanged();
        } else {
            prSet = new LineDataSet(prList, "");
            prSet.setColor(colorId);
            prSet.setCircleColor(colorId);
            prSet.setDrawCircleHole(false);
            prSet.setLineWidth(1f);
            prSet.setCircleSize(3f);
            LineData prData = new LineData(prSet);
            prChart.setData(prData);

        }

        if (rrChart.getData() != null && rrChart.getData().getDataSetCount() > 0) {
            rrSet = (LineDataSet) rrChart.getData().getDataSetByIndex(0);
            rrSet.setValues(rrList);
            rrChart.getData().notifyDataChanged();
            rrChart.notifyDataSetChanged();
        } else {
            rrSet = new LineDataSet(rrList, "");
            rrSet.setColor(colorId);
            rrSet.setCircleColor(colorId);
            rrSet.setDrawCircleHole(false);
            rrSet.setLineWidth(1f);
            rrSet.setCircleSize(3f);
            LineData rrData = new LineData(rrSet);
            rrChart.setData(rrData);
        }
        //刷新图表，显示数据
        spo2Chart.invalidate();
        prChart.invalidate();
        rrChart.invalidate();
    }

    private void setBpChartData() {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        List<Entry> SysEntries = new ArrayList<>();
        List<Entry> DiaEntries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String dateTimeString = FormatUtils.getDateTimeString(calendar2.getTime(), FormatUtils.template_Month);
            Log.d("dateTimeString", i + "|" + dateTimeString);
            List<Cbp1k1Data> cbp1k1DataList = cbp1k1Operation.queryByDate(dateTimeString, IchoiceApplication.getAppData().userProfileInfo.getUserId());
            if (!cbp1k1DataList.isEmpty()) {
                AvgData avgData = AvgDataUtils.getavgData(cbp1k1DataList);
                avgDataList.add(avgData);
                SysEntries.add(new Entry(i, avgData.getSysAvg()));
                DiaEntries.add(new Entry(i, avgData.getDiaAvg()));
            }
            calendar2.add(Calendar.MONTH, 1);
        }
        LineDataSet sysSet, diaSet;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            sysSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            diaSet = (LineDataSet) chart.getData().getDataSetByIndex(1);
            sysSet.setValues(SysEntries);
            diaSet.setValues(DiaEntries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {

            sysSet = new LineDataSet(SysEntries, "SYS");
            sysSet.setDrawCircleHole(false);
            sysSet.setColor(Color.rgb(255, 128, 0));
            sysSet.setCircleColor(Color.rgb(255, 128, 0));
            sysSet.setCircleSize(5f);
            sysSet.setLineWidth(3f);

            diaSet = new LineDataSet(DiaEntries, "DIA");
            diaSet.setColor(Color.rgb(65, 105, 225));
            diaSet.setCircleColor(Color.rgb(65, 105, 225));
            diaSet.setDrawCircleHole(false);
            diaSet.setLineWidth(3f);
            diaSet.setCircleSize(5f);

            LineData data = new LineData(sysSet, diaSet);
            chart.setData(data);

        }
        chart.invalidate();
    }

    private void initBpChart() {
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(false);
        chart.setGridBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setTouchEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12);

        xAxis.setValueFormatter(new YearFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setVisibleXRange(0, 12);
        xAxis.setLabelCount(12);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(210);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(8, true);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMaximum(210);
        rightAxis.setAxisMinimum(0);
        rightAxis.setLabelCount(8, true);
    }

    private void setTextDate() {
        tv_year.setText(calendar.get(Calendar.YEAR) + "");
        changeArrowImage(calendar);
    }


    @OnClick({R.id.year_right, R.id.year_left, R.id.year_select})
    public void onClick(View view) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        switch (view.getId()) {
            case R.id.year_left:
                if (calendar.get(Calendar.YEAR) <= beginCalendar.get(Calendar.YEAR)) {
                    return;
                }
                calendar.add(Calendar.YEAR, -1);
                MoveToTempChart(calendar);
                setTextDate();
                setBpChartData();
                setPulseOximeterChartData();
                break;

            case R.id.year_right:
                if (calendar.get(Calendar.YEAR) >= Calendar.getInstance().get(Calendar.YEAR)) {
                    return;
                }
                calendar.add(Calendar.YEAR, 1);
                MoveToTempChart(calendar);
                setTextDate();
                setBpChartData();
                setPulseOximeterChartData();
                break;

            case R.id.year_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.All);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            default:
        }
    }

    public void changeArrowImage(Calendar calendar) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        LogUtils.d(TAG, " calendar.getTimeInMillis()  " + FormatUtils.getDateTimeString(calendar.getTimeInMillis(), template_DbDateTime));
        if (isSameYear(calendar, beginCalendar)) {
            calendar_left.setImageResource(R.mipmap.left_gay);
        } else {
            calendar_left.setImageResource(R.mipmap.calendar_left);
        }
        if (isSameYear(calendar, Calendar.getInstance())) {
            calendar_right.setImageResource(R.mipmap.right_gay);
        } else {
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
    }

    public boolean isSameYear(Calendar c, Calendar calendar1) {
        int year = calendar1.get(Calendar.YEAR);
        if (c.get(Calendar.YEAR) == year) {
            return true;
        }
        return false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
