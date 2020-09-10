package com.choicemmed.ichoice.healthcheck.fragment.bloodpressure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.activity.CalenderSelectActivity;
import com.choicemmed.ichoice.healthcheck.custom.YearFormatter;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.healthcheck.entity.AvgData;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthreport.commend.AvgDataUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.Cbp1k1Data;

public class BpYearFragment extends BaseFragment implements OnChartValueSelectedListener, OnChartGestureListener {
    @BindView(R.id.year_year)
    TextView tv_year;
    @BindView(R.id.year_left)
    ImageView calendar_left;
    @BindView(R.id.year_right)
    ImageView calendar_right;
    @BindView(R.id.bp_year_chart)
    LineChart chart;
    @BindView(R.id.time)
    TextView times;
    @BindView(R.id.sys)
    TextView sys;
    @BindView(R.id.dia)
    TextView dia;
    @BindView(R.id.pr)
    TextView pr;
    @BindView(R.id.sys_unit)
    TextView sunit;
    @BindView(R.id.dia_unit)
    TextView dunit;

    private Calendar calendar;
    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    private static String endDate;
    int yearsize = 1;
    Map<Integer,AvgData> avgDataMap = new HashMap<>();
    List<AvgData> avgDataList = new ArrayList<>();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                int month = intent.getIntExtra("month",CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day",CalendarDay.today().getDay());
                calendar.set(year,Calendar.JANUARY,1);
                if (calendar.get(Calendar.YEAR) != year){
                    setTextDate(calendar);
                }
                CalendarMoveChart(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    boolean isunit;
    private Cbp1k1Operation cbp1k1Operation;
    int systolicPressure, diastolicPressure, pulseRate;
    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                isunit = intent.getBooleanExtra("isunit",false);
                initData();
                setTexts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected int contentViewID() {
        return R.layout.fragment_year;
    }

    @Override
    protected void initialize() {
        calendar = Calendar.getInstance();
        initReceiver();
        initData();
        initChart();
        setTextDate(calendar);
        setChartData();
        CalendarMoveChart(calendar);
        initItem();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("UnitSelect");
        getActivity().registerReceiver(broadcastReceiver1,intentFilter1);
    }

    private void initItem() {
        if (avgDataList.isEmpty()){
            return;
        }
        AvgData avgData = avgDataList.get(avgDataList.size()-1);
        systolicPressure = avgData.getSysAvg();
        diastolicPressure = avgData.getDiaAvg();
        pulseRate = avgData.getPrAvg();
        setTexts();
    }


    private void setTexts() {
        if (isunit){
            sys.setText(systolicPressure +"");
            dia.setText(diastolicPressure +"");
            pr.setText(pulseRate +"");
            setUnitText(R.string.mmhg);
        }else {
            int systolicKpa = (int)(systolicPressure*0.133);
            int diastolicKpa = (int)(diastolicPressure*0.133);
            sys.setText(systolicKpa +"");
            dia.setText(diastolicKpa +"");
            pr.setText(pulseRate +"");
            setUnitText(R.string.kpa);
        }
    }

    private void setUnitText(int resId){
        String text = getActivity().getString(resId);
        sunit.setText(text);
        dunit.setText(text);
    }


    private void CalendarMoveChart(Calendar calendar) {
        Calendar beginCalendar= DateUtils.strToCalendar(beginDate);
        int diff = calendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR) ;
        chart.moveViewToX(diff*12f);
    }


    private void setChartData() {
        List<Entry> SysEntries = new ArrayList<>();
        List<Entry> DiaEntries = new ArrayList<>();
        Calendar calendar = DateUtils.strToCalendar(beginDate);
        calendar.set(calendar.get(Calendar.YEAR),Calendar.JANUARY,1);
        for (int i = 0; i < yearsize * 12; i++){
            String dateTimeString = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Month);
            Log.d("dateTimeString",i +"|"+ dateTimeString);
            List<Cbp1k1Data> cbp1k1DataList = cbp1k1Operation.queryByDate(dateTimeString, IchoiceApplication.getAppData().userProfileInfo.getUserId());
            if (!cbp1k1DataList.isEmpty()){
                AvgData avgData= AvgDataUtils.getavgData(cbp1k1DataList);
                avgDataMap.put(i,avgData);
                avgDataList.add(avgData);
                SysEntries.add(new Entry(i,avgData.getSysAvg()));
                DiaEntries.add(new Entry(i,avgData.getDiaAvg()));
            }
            calendar.add(Calendar.MONTH,1);
        }

        if (!avgDataList.isEmpty()){
            SysEntries.add(new Entry(yearsize * 12+1,avgDataList.get(avgDataList.size()-1).getSysAvg()));
            DiaEntries.add(new Entry(yearsize * 12+1,avgDataList.get(avgDataList.size()-1).getDiaAvg()));
        }


        LineDataSet sysSet,diaSet;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            sysSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            diaSet = (LineDataSet) chart.getData().getDataSetByIndex(1);
            sysSet.setValues(SysEntries);
            diaSet.setValues(DiaEntries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        }else {

            sysSet = new LineDataSet(SysEntries,"SYS");
            sysSet.setDrawCircleHole(false);
            sysSet.setColor(Color.rgb(255,128,0));
            sysSet.setCircleColor(Color.rgb(255,128,0));
            sysSet.setCircleSize(5f);
            sysSet.setLineWidth(3f);

            diaSet = new LineDataSet(DiaEntries,"DIA");
            diaSet.setColor(Color.rgb(65,105,225));
            diaSet.setCircleColor(Color.rgb(65,105,225));
            diaSet.setDrawCircleHole(false);
            diaSet.setLineWidth(3f);
            diaSet.setCircleSize(5f);

            LineData data = new LineData(sysSet, diaSet);
            chart.setData(data);

        }
        chart.invalidate();
    }

    private void setTextDate(Calendar calendar) {
        tv_year.setText(calendar.get(Calendar.YEAR) + "");
        changeArrowImage(calendar);
    }

    private void initChart() {
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(false);
        chart.setGridBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.setOnChartValueSelectedListener(this);
        chart.setOnChartGestureListener(this);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(yearsize * 12f);

        xAxis.setValueFormatter(new YearFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setVisibleXRange(0,12);
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

    private void initData() {
        isunit = IchoiceApplication.getAppData().userProfileInfo.getIsUnit();
        cbp1k1Operation = new Cbp1k1Operation(getContext());
        List<Cbp1k1Data> cbp1k1DataList = cbp1k1Operation.queryBySyncState(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (cbp1k1DataList.isEmpty()){
            return;
        }
        Cbp1k1Data newCbp1k1Data = cbp1k1DataList.get(cbp1k1DataList.size()-1);
        endDate = newCbp1k1Data.getMeasureDateTime();
        times.setText(endDate.substring(5,7));
        beginDate = cbp1k1DataList.get(0).getMeasureDateTime();
        yearsize = Calendar.getInstance().get(Calendar.YEAR) -  DateUtils.strToCalendar(beginDate).get(Calendar.YEAR) + 1;
    }


    @OnClick({R.id.year_right,R.id.year_left,R.id.year_select})
    public void onClick(View view) {
        Calendar beginCalendar= DateUtils.strToCalendar(beginDate);
        switch (view.getId()){
            case R.id.year_left:
                if (calendar.get(Calendar.YEAR) <= beginCalendar.get(Calendar.YEAR) ){
                    return;
                }
                calendar.add(Calendar.YEAR,-1);
                setTextDate(calendar);
                CalendarMoveChart(calendar);
                break;

            case R.id.year_right:
                if (calendar.get(Calendar.YEAR) >= Calendar.getInstance().get(Calendar.YEAR) ){
                    return;
                }
                calendar.add(Calendar.YEAR,1);
                setTextDate(calendar);
                CalendarMoveChart(calendar);
                break;

            case R.id.year_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.BloodPressure);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
                break;
            default:
        }
    }

    public void changeArrowImage(Calendar calendar){
        Calendar beginCalendar= DateUtils.strToCalendar(beginDate);
        if (beginCalendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)){
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.right_gay);
            return;
        }
        if ( calendar.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR) ){
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
        if ( (calendar.get(Calendar.YEAR) > beginCalendar.get(Calendar.YEAR) ) &&
                (calendar.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR))){
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
        if ( calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) ){
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.right_gay);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        onChartEvent();
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        onChartEvent();
    }

    private void onChartEvent() {
        Calendar gestureCalendar= DateUtils.strToCalendar(beginDate);
        float liftXIndex = chart.getLowestVisibleX();
        float rightXIndex = chart.getHighestVisibleX();
        Log.d("rightXIndex",liftXIndex+"");
        int add = (int)(liftXIndex/12);
        gestureCalendar.add(Calendar.YEAR,add);
        if (gestureCalendar.after(Calendar.getInstance())){
            gestureCalendar = Calendar.getInstance();
        }
        setTextDate(gestureCalendar);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        try {
            float x =  e.getX();
            AvgData avgData = avgDataMap.get((int)x);
            times.setText((int)(x%12+1)+"");
            Log.d("onValueSelected",avgData.toString());
            systolicPressure = avgData.getSysAvg();
            diastolicPressure = avgData.getDiaAvg();
            pulseRate = avgData.getPrAvg();
            setTexts();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected() {

    }
}
