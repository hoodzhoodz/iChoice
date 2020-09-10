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
import com.choicemmed.ichoice.healthcheck.custom.WeekFormatter;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.db.TemperatureOperation;
import com.choicemmed.ichoice.healthcheck.entity.AvgOxData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.OxSpotData;

import static com.choicemmed.common.FormatUtils.template_Date;
import static com.choicemmed.common.FormatUtils.template_DbDateTime;


/**
 * @author gaofang
 * @date 2020-07-02
 * 增加血氧仪趋势图显示
 */
public class WeekFragment extends BaseFragment {
    private static final String TAG = WeekFragment.class.getSimpleName();
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.calendar_left)
    ImageView calendar_left;
    @BindView(R.id.calendar_right)
    ImageView calendar_right;
    @BindView(R.id.week_chart)
    LineChart chart;

    @BindView(R.id.bp_week_line)
    CardView bp_line;

    @BindView(R.id.temp_chart)
    LineChart temp_chart;
    @BindView(R.id.temp_card_view)
    CardView temp_card_view;
    @BindView(R.id.tv_unit)
    TextView tv_unit;

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
    Calendar beginCalendar;
    private Calendar calendar = Calendar.getInstance();
    private static String beginDate;
    private Calendar endCalendar = Calendar.getInstance();
    int xValueMax;
    private Cbp1k1Operation cbp1k1Operation;
    private OxSpotOperation oxSpotOperation;
    private int unit;
    TemperatureOperation temperatureOperation;
    Map<String, CFT308Data> cft308DataHashMapC = new HashMap<>();
    Map<Integer, CFT308Data> cft308DataMap = new HashMap<>();
    List<Entry> entriesUnitC = new ArrayList<>();
    List<Entry> entriesUnitF = new ArrayList<>();
    String[] weeks;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                int month = intent.getIntExtra("month", CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day", CalendarDay.today().getDay());
                calendar.set(year, month, day);
                CalendarMoveChart(calendar);
                setTextDate(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_week2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initialize() {
        initData();
        initLine();
        CalendarMoveChart(calendar);
        setTextDate(calendar);
    }

    private void initData() {
        unit = (int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1);
        temperatureOperation = new TemperatureOperation(getContext());
        oxSpotOperation = new OxSpotOperation(getContext());
        cbp1k1Operation = new Cbp1k1Operation(getContext());
        weeks = new String[]{getString(R.string.sum), getString(R.string.mon), getString(R.string.tue), getString(R.string.wed), getString(R.string.thu), getString(R.string.fri), getString(R.string.sat)};
        beginDate = FormatUtils.getDateTimeString(Long.parseLong(IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().substring(6, IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().length() - 2)), FormatUtils.template_DbDateTime);
        beginCalendar = DateUtils.strToCalendar(beginDate);
        LogUtils.d(TAG, beginCalendar.get(Calendar.DAY_OF_WEEK) + "");
        beginCalendar.add(Calendar.DATE, Calendar.SUNDAY - beginCalendar.get(Calendar.DAY_OF_WEEK));
        beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
        beginCalendar.set(Calendar.MINUTE, 0);
        beginCalendar.set(Calendar.SECOND, 0);
        endCalendar.add(Calendar.DATE, Calendar.SATURDAY + 1 - Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        xValueMax = DateUtils.differentWeekCalendar(beginCalendar, Calendar.getInstance()) * 7;
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
        //血氧仪
        if (deviceDisplay.getPulseOximeter() == 0) {
            llPulseOximeterTrend.setVisibility(View.GONE);
        } else {
            llPulseOximeterTrend.setVisibility(View.VISIBLE);
            initPulseOximeterChart();
            setPulseOximeterChartData();
        }
    }

    private void setTempChartData() {
        List<CFT308Data> cft308DataList = temperatureOperation.queryAVGDataByDay(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!cft308DataList.isEmpty()) {
            for (int i = 0; i < cft308DataList.size(); i++) {
                long nh = 1000 * 60 * 60 * 24;//一天的毫秒数

                String startTime = cft308DataList.get(i).getMeasureTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(startTime, template_DbDateTime));
                int startX = (int) ((calendar1.getTimeInMillis() - beginCalendar.getTimeInMillis()) / (nh));//计算差多少天
                LogUtils.d(TAG, "startX" + startX + "  getTemp  " + cft308DataList.get(i).getTemp());
                entriesUnitC.add(new Entry(startX, Float.parseFloat(cft308DataList.get(i).getTemp())));
                cft308DataMap.put(startX, cft308DataList.get(i));
                String f = String.valueOf((Float.parseFloat(cft308DataList.get(i).getTemp()) * 9 / 5) + 32);
                DecimalFormat df = new DecimalFormat("#.0");
                df.setRoundingMode(RoundingMode.DOWN);
                f = df.format(Float.parseFloat(f));
                entriesUnitF.add(new Entry(startX, Float.parseFloat(f)));
                String everyDayLastMeasureTime = FormatUtils.getDateTimeString1(calendar1.getTimeInMillis(), template_Date);
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
        xAxis.setAxisMaximum(xValueMax);
        WeekFormatter weekFormatter = new WeekFormatter();
        weekFormatter.setWeeks(weeks);
        xAxis.setValueFormatter(weekFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        temp_chart.setVisibleXRange(0, 7);
        xAxis.setLabelCount(7);

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

        LogUtils.d(TAG, beginCalendar.getTimeInMillis() + "        " + (beginCalendar.getTimeInMillis() / (1000 * 60 * 60 * 24)) * 1000 * 60 * 60 * 24);

        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(xValueMax);
        WeekFormatter weekFormatter = new WeekFormatter();
        weekFormatter.setWeeks(weeks);
        xAxis.setValueFormatter(weekFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        spo2Chart.setVisibleXRange(0, 7);

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
        xAxis_pr.setAxisMinimum(0);
//        xValueMax = weeksize * 7;
        xAxis_pr.setAxisMaximum(xValueMax);


        WeekFormatter weekFormatter = new WeekFormatter();

        weekFormatter.setWeeks(weeks);
        xAxis_pr.setValueFormatter(weekFormatter);
        xAxis_pr.setPosition(XAxis.XAxisPosition.BOTTOM);
        prChart.setVisibleXRange(0, 7);

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
        XAxis xAxis_rr = rrChart.getXAxis();
        xAxis_rr.setTextSize(12f);
        xAxis_rr.setAxisMinimum(0);
//        xValueMax = weeksize * 7;
        xAxis_rr.setAxisMaximum(xValueMax);


        WeekFormatter weekFormatter = new WeekFormatter();
        weekFormatter.setWeeks(weeks);
        xAxis_rr.setValueFormatter(weekFormatter);
        xAxis_rr.setPosition(XAxis.XAxisPosition.BOTTOM);
        rrChart.setVisibleXRange(0, 7);

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

    /**
     * 设置图表数据
     */
    private void setPulseOximeterChartData() {
        List<Entry> spo2List = new ArrayList<>();
        List<Entry> prList = new ArrayList<>();
        List<Entry> rrList = new ArrayList<>();
        List<OxSpotData> oxSpotDataList = oxSpotOperation.queryAVGDataByDay(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!oxSpotDataList.isEmpty()) {
            for (int i = 0; i < oxSpotDataList.size(); i++) {
                long nh = 1000 * 60 * 60 * 24;//一天的毫秒数

                String startTime = oxSpotDataList.get(i).getMeasureDateTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(startTime, FormatUtils.template_DbDateTime));
                int startX = (int) ((calendar1.getTimeInMillis() - beginCalendar.getTimeInMillis()) / nh);//计算差多少天
                LogUtils.d(TAG, "startTime " + startTime + "   startX  " + startX);
                AvgOxData avgOxData = new AvgOxData(oxSpotDataList.get(i).getBloodOxygen(), oxSpotDataList.get(i).getPulseRate(), oxSpotDataList.get(i).getRR());
                spo2List.add(new Entry(startX, avgOxData.getSpO2Avg()));
                prList.add(new Entry(startX, avgOxData.getPRAvg()));
                if (avgOxData.getRRAvg() > 0) {
                    rrList.add(new Entry(startX, avgOxData.getRRAvg()));
                }
            }
        }

        //图表数据填充
        LineDataSet spo2Set, prSet, rrSet;
        int colorId = Objects.requireNonNull(getActivity()).getResources().getColor(R.color.pulse_oximeter_blue);

            spo2Set = new LineDataSet(spo2List, "");
            spo2Set.setDrawCircleHole(false);
            spo2Set.setColor(colorId);
            spo2Set.setCircleColor(colorId);
            spo2Set.setLineWidth(1f);
            spo2Set.setCircleRadius(3f);
            LineData spo2Data = new LineData(spo2Set);
            spo2Chart.setData(spo2Data);

            prSet = new LineDataSet(prList, "");
            prSet.setColor(colorId);
            prSet.setCircleColor(colorId);
            prSet.setDrawCircleHole(false);
            prSet.setLineWidth(1f);
            prSet.setCircleRadius(3f);
            LineData prData = new LineData(prSet);
            prChart.setData(prData);


            rrSet = new LineDataSet(rrList, "");
            rrSet.setColor(colorId);
            rrSet.setCircleColor(colorId);
            rrSet.setDrawCircleHole(false);
            rrSet.setLineWidth(1f);
            rrSet.setCircleRadius(3f);
            LineData rrData = new LineData(rrSet);
            rrChart.setData(rrData);

        //刷新图表，显示数据
        spo2Chart.invalidate();
        prChart.invalidate();
        rrChart.invalidate();
    }

    private void CalendarMoveChart(Calendar endCalendar) {
        int beginday = endCalendar.get(Calendar.DAY_OF_WEEK);
        endCalendar.add(Calendar.DATE, -(beginday - Calendar.SUNDAY));
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        Log.d(TAG, "CalendarMoveChart    " + FormatUtils.getDateTimeString(endCalendar.getTimeInMillis(), FormatUtils.template_DbDateTime));
        long nh = 1000 * 60 * 60 * 24;//一天的毫秒数
        int startX = (int) ((endCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis()) / nh);//计算差多少天
        temp_chart.moveViewToX(startX);
        chart.moveViewToX(startX);
        spo2Chart.moveViewToX(startX);
        prChart.moveViewToX(startX);
        rrChart.moveViewToX(startX);
    }


    private void setBpChartData() {
        List<Entry> SysEntries = new ArrayList<>();
        List<Entry> DiaEntries = new ArrayList<>();
        List<Cbp1k1Data> cbp1k1DataList = cbp1k1Operation.queryAVGDataByDay(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!cbp1k1DataList.isEmpty()) {
            for (int i = 0; i < cbp1k1DataList.size(); i++) {
                long nh = 1000 * 60 * 60 * 24;//一天的毫秒数

                String startTime = cbp1k1DataList.get(i).getMeasureDateTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(startTime, FormatUtils.template_DbDateTime));
                int startX = (int) ((calendar1.getTimeInMillis() - beginCalendar.getTimeInMillis()) / nh);//计算差多少天
                LogUtils.d(TAG, "startTime " + startTime + "   startX  " + startX);
                SysEntries.add(new Entry(startX, cbp1k1DataList.get(i).getSystolic()));
                DiaEntries.add(new Entry(startX, cbp1k1DataList.get(i).getDiastolic()));
            }

        }
        LineDataSet sysSet, diaSet;
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
        chart.invalidate();
    }


    private void initBpChart() {
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(false);
        chart.setGridBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.fitScreen();
        chart.setScaleEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setTouchEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xValueMax);

        WeekFormatter weekFormatter = new WeekFormatter();
        weekFormatter.setWeeks(weeks);
        xAxis.setValueFormatter(weekFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setVisibleXRange(0, 7);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(210);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(8, true);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMaximum(210);
        rightAxis.setAxisMinimum(0);
        rightAxis.setLabelCount(8, true);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        switch (view.getId()) {
            case R.id.calendar_left:
                if (calendar.before(beginCalendar) || isWeek(calendar, beginCalendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, -7);
                CalendarMoveChart(calendar);
                setTextDate(calendar);

                break;

            case R.id.calendar_right:
                if (calendar.after(Calendar.getInstance()) || isWeek(calendar, Calendar.getInstance())) {
                    return;
                }
                calendar.add(Calendar.DATE, 7);
                CalendarMoveChart(calendar);
                setTextDate(calendar);
                break;

            case R.id.calender_select:
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
        if (isWeek(beginCalendar, Calendar.getInstance())) {
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.right_gay);
            return;
        }
        if (isWeek(calendar, Calendar.getInstance())) {
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.right_gay);
        }
        if (calendar.after(beginCalendar) && calendar.before(Calendar.getInstance())) {
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
        if (isWeek(calendar, beginCalendar)) {
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }

    }

    private boolean isWeek(Calendar cal1, Calendar cal2) {
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }

        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }


    private void setTextDate(Calendar calendar) {
        tv_year.setText(calendar.get(Calendar.YEAR) + "");
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, day == Calendar.SUNDAY ? 0 : -(day - Calendar.SUNDAY));
        tv_month.setText(calendar.get(Calendar.MONTH) + 1 + "");
        int sun = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 6);
        int sat = calendar.get(Calendar.DAY_OF_MONTH);
        tv_day.setText(sun + "-" + sat);
        changeArrowImage(calendar);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

}
