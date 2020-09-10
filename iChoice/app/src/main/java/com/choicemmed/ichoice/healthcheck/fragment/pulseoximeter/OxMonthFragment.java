package com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.activity.CalenderSelectActivity;
import com.choicemmed.ichoice.healthcheck.custom.MouthFormatter;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;
import com.choicemmed.ichoice.healthcheck.entity.AvgOxData;
import com.choicemmed.ichoice.healthreport.commend.AvgDataUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.OxSpotData;

/**
 * @author gaofang
 * @Date 修改于 2020-06-29
 * 历史趋势图（月）
 */
public class OxMonthFragment extends BaseFragment {
    private static final String TAG = OxMonthFragment.class.getSimpleName();
    @BindView(R.id.ox_spo2_chart)
    LineChart spo2Chart;
    @BindView(R.id.ox_pr_chart)
    LineChart prChart;
    @BindView(R.id.ox_rr_chart)
    LineChart rrChart;

    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_day_split)
    TextView tvDaySplit;
    @BindView(R.id.tv_chart_title_spo2)
    TextView tvChartTitleSpo2;
    @BindView(R.id.tv_ox_spo2)
    TextView tvOxSpo2;
    @BindView(R.id.calendar_left)
    ImageView calendarLeft;
    @BindView(R.id.calendar_right)
    ImageView calendarRight;

    @BindView(R.id.tv_time)
    TextView times;
    @BindView(R.id.ox_trend_bottom_spo2_tv)
    TextView tvSpo2Value;
    @BindView(R.id.ox_trend_bottom_pr_tv)
    TextView tvPrValue;
    /**
     * RR值
     */
    @BindView(R.id.ox_trend_bottom_rr_tv)
    TextView tvRrValue;
    private Calendar calendar;
    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    List<AvgOxData> avgOxDataList = new ArrayList<>();
    private OxSpotOperation oxSpotOperation;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                int month = intent.getIntExtra("month", CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day", CalendarDay.today().getDay());
                calendar.set(year, month, day);
                setTextDate(calendar);
                setChartData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_ox_spot_month_trend;
    }

    @Override
    protected void initialize() {
        calendar = Calendar.getInstance();
        beginDate = FormatUtils.getDateTimeString(Long.parseLong(IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().substring(6, IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().length() - 2)), FormatUtils.template_DbDateTime);
        oxSpotOperation = new OxSpotOperation(getContext());
        setSpo2View();
        initSpO2Chart();
        initPRChart();
        initRRChart();
        initReceiver();
        setTextDate(calendar);
        setChartData();
    }

    /**
     * 设置Spo2富文本样式
     */
    private void setSpo2View() {
        String strSpo2 = getActivity().getResources().getString(R.string.ox_spo2);
        SpannableString spannableString = new SpannableString(strSpo2);
        spannableString.setSpan(new AbsoluteSizeSpan(40), strSpo2.length() - 1, strSpo2.length(), 0);
        tvChartTitleSpo2.setText(spannableString);
        SpannableString spannableString1 = new SpannableString(strSpo2);
        spannableString1.setSpan(new AbsoluteSizeSpan(20), strSpo2.length() - 1, strSpo2.length(), 0);
        tvOxSpo2.setText(spannableString1);
    }

    /**
     * 初始化日期广播
     */
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 设置日期选择显示
     *
     * @param calendar
     */
    private void setTextDate(Calendar calendar) {
        this.calendar = calendar;
        tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        tvMonth.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        tvDaySplit.setVisibility(View.GONE);
        changeArrowImage(calendar);
    }

    /**
     * 日期左右选择按钮状态变化
     *
     * @param calendar
     */
    public void changeArrowImage(Calendar calendar) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        if (isMonth(Calendar.getInstance(), beginCalendar)) {
            calendarLeft.setImageResource(R.mipmap.left_gay);
            calendarRight.setImageResource(R.mipmap.right_gay);
            return;
        }
        if (isMonth(calendar, beginCalendar)) {
            calendarLeft.setImageResource(R.mipmap.left_gay);
            calendarRight.setImageResource(R.mipmap.arrow_right);
        }
        if (calendar.after(beginCalendar) && calendar.before(Calendar.getInstance())) {
            calendarLeft.setImageResource(R.mipmap.calendar_left);
            calendarRight.setImageResource(R.mipmap.arrow_right);
        }
        if (isMonth(calendar, Calendar.getInstance())) {
            calendarLeft.setImageResource(R.mipmap.calendar_left);
            calendarRight.setImageResource(R.mipmap.right_gay);
        }
    }

    public boolean isMonth(Calendar c, Calendar calendar1) {
        int year = calendar1.get(Calendar.YEAR);
        int month = calendar1.get(Calendar.MONTH);
        if (c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == month) {
            return true;
        }
        return false;
    }


    @OnClick({R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        switch (view.getId()) {
            case R.id.calendar_left:
                if (isMonth(calendar, beginCalendar) || calendar.before(beginCalendar)) {
                    return;
                }
                calendar.add(Calendar.MONTH, -1);
                setTextDate(calendar);
                setChartData();
                break;
            case R.id.calendar_right:
                if (isMonth(calendar, Calendar.getInstance()) || calendar.after(Calendar.getInstance())) {
                    return;
                }
                calendar.add(Calendar.MONTH, 1);
                setTextDate(calendar);
                setChartData();
                break;
            case R.id.calender_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.PulseOximeter);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            default:
                break;

        }
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
        spo2Chart.setDragXEnabled(false);
        spo2Chart.setOnChartValueSelectedListener(spo2SelectedListener);

        XAxis xAxis = spo2Chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(4f);

        xAxis.setValueFormatter(new MouthFormatter(getString(R.string.week)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        spo2Chart.setVisibleXRange(0, 4);
        xAxis.setLabelCount(4);

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
        prChart.setDragXEnabled(false);
        prChart.setOnChartValueSelectedListener(prSelectedListener);

        XAxis xAxis_pr = prChart.getXAxis();
        xAxis_pr.setTextSize(12f);
        xAxis_pr.setAxisMinimum(0f);
        xAxis_pr.setAxisMaximum(4f);

        xAxis_pr.setValueFormatter(new MouthFormatter(getString(R.string.week)));
        xAxis_pr.setPosition(XAxis.XAxisPosition.BOTTOM);
        prChart.setVisibleXRange(0, 4);
        xAxis_pr.setLabelCount(4);

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
        rrChart.setDragXEnabled(false);
        rrChart.setOnChartValueSelectedListener(rrSelectedListener);
        //24小时 间隔2h
        XAxis xAxis_pr = rrChart.getXAxis();
        xAxis_pr.setTextSize(12f);
        xAxis_pr.setAxisMinimum(0f);
        xAxis_pr.setAxisMaximum(4f);

        xAxis_pr.setValueFormatter(new MouthFormatter(getString(R.string.week)));
        xAxis_pr.setPosition(XAxis.XAxisPosition.BOTTOM);
        rrChart.setVisibleXRange(0, 4);
        xAxis_pr.setLabelCount(4);

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


    private void setChartData() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(calendar.getTime());
        List<Entry> spo2List = new ArrayList<>();
        List<Entry> prList = new ArrayList<>();
        List<Entry> rrList = new ArrayList<>();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), 1, 0, 0, 0);
        int currentMonth = calendar1.get(Calendar.MONTH);
        avgOxDataList.clear();
        for (int i = 0; i <= 4; i++) {
            if ((calendar1.get(Calendar.MONTH)) != currentMonth) {
                break;
            }
            int day = calendar1.get(Calendar.DAY_OF_WEEK);
            if (day != 1) {
                calendar1.add(Calendar.DATE, (Calendar.SATURDAY - day) + 1);
            }

            String beginTime = FormatUtils.getDateTimeString(calendar1.getTime(), FormatUtils.template_Date);
            calendar1.add(Calendar.DATE, 7);
            String endTime = FormatUtils.getDateTimeString(calendar1.getTime(), FormatUtils.template_Date);
            Log.d("weekTime", i + "|" + beginTime + "|" + endTime);
            List<OxSpotData> oxSpotDataList = oxSpotOperation.queryToMonth(IchoiceApplication.getAppData().userProfileInfo.getUserId(), beginTime, endTime);
            if (oxSpotDataList != null) {
                AvgOxData avgOxData = AvgDataUtils.getAvgOxData(oxSpotDataList);
                avgOxDataList.add(avgOxData);
                spo2List.add(new Entry(i, avgOxData.getSpO2Avg()));
                prList.add(new Entry(i, avgOxData.getPRAvg()));
                Log.d("weekTime", i + "|" + avgOxData + "|" + avgOxData.toString());
                if (avgOxData.getRRAvg() > 0) {
                    rrList.add(new Entry(i, avgOxData.getRRAvg()));
                }
            }
        }
        if (avgOxDataList.size() > 0) {
            tvSpo2Value.setText(String.valueOf(avgOxDataList.get(avgOxDataList.size() - 1).getSpO2Avg()));
            tvPrValue.setText(String.valueOf(avgOxDataList.get(avgOxDataList.size() - 1).getPRAvg()));
            tvRrValue.setText(avgOxDataList.get(avgOxDataList.size() - 1).getRRAvg() > 0 ? String.valueOf(avgOxDataList.get(avgOxDataList.size() - 1).getRRAvg()) : getActivity().getResources().getString(R.string.ox_no_data));
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

    private OnChartValueSelectedListener spo2SelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            try {
                float x = e.getX();
                AvgOxData avgOxData = avgOxDataList.get((int) x);
                tvSpo2Value.setText(String.valueOf(avgOxData.getSpO2Avg()));
                tvPrValue.setText(String.valueOf(avgOxData.getPRAvg()));
                tvRrValue.setText(avgOxData.getRRAvg() > 0 ? String.valueOf(avgOxData.getRRAvg()) : getActivity().getResources().getString(R.string.ox_no_data));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected() {

        }
    };
    private OnChartValueSelectedListener prSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            try {
                float x = e.getX();
                AvgOxData avgOxData = avgOxDataList.get((int) x);
//                setDateTime(x);
                tvSpo2Value.setText(String.valueOf(avgOxData.getSpO2Avg()));
                tvPrValue.setText(String.valueOf(avgOxData.getPRAvg()));
                tvRrValue.setText(avgOxData.getRRAvg() > 0 ? String.valueOf(avgOxData.getRRAvg()) : getActivity().getResources().getString(R.string.ox_no_data));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected() {

        }
    };
    private OnChartValueSelectedListener rrSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            try {
                float x = e.getX();
                AvgOxData avgOxData = avgOxDataList.get((int) x);
                tvSpo2Value.setText(String.valueOf(avgOxData.getSpO2Avg()));
                tvPrValue.setText(String.valueOf(avgOxData.getPRAvg()));
                tvRrValue.setText(avgOxData.getRRAvg() > 0 ? String.valueOf(avgOxData.getRRAvg()) : getActivity().getResources().getString(R.string.ox_no_data));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected() {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
