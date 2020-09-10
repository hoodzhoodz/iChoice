package com.choicemmed.ichoice.healthcheck.fragment.wristpulse;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.custom.Spo2YFormatter;
import com.choicemmed.ichoice.healthcheck.custom.TimeXFormatter;
import com.choicemmed.ichoice.healthcheck.service.W314BleConService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
*Created by
 * @author Jiangnan
 * @Date 2020/1/17.
*/
public class W314B4RealTimeFragment extends BaseFragment {
    public static final String TAG = "W314B4RealTimeFragment";

    @BindView(R.id.spo2_value)
    TextView tvSpo2;
    @BindView(R.id.pluse_rate_value)
    TextView tvPlusRate;
    @BindView(R.id.spo2_chart)
    LineChart spo2Chart;
    @BindView(R.id.pluse_rate_chart)
    LineChart pluseRateChart;
    private static float spcount = 0;
    private static float prcount = 0;

    List<Entry> spEntries = new ArrayList<>();
    List<Entry> prEntries = new ArrayList<>();
    LineDataSet spSet,prSet;
    Calendar mCalendar;

    List<Calendar> spCalendars = new ArrayList<>();
    List<Calendar> prCalendars = new ArrayList<>();
    List<Integer> spData = new ArrayList<>();
    List<Integer> prData = new ArrayList<>();

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int realTimeSpoData = bundle.getInt("realTimeSpoData");
            if (realTimeSpoData == 0){
                return;
            }
            tvSpo2.setText(realTimeSpoData+"");
            if (isMoreScreen){
                spCalendars.remove(0);
                spData.remove(0);
                spCalendars.add(Calendar.getInstance());
                spData.add(realTimeSpoData);
                XAxis spo2ChartXAxis = spo2Chart.getXAxis();
                spo2ChartXAxis.setValueFormatter(new TimeXFormatter(spCalendars.get(0)));
                setSpEntries();
            }else {
                spCalendars.add(Calendar.getInstance());
                spData.add(realTimeSpoData);
                setSpEntries();
            }
            spSet.setValues(spEntries);
            spo2Chart.getData().notifyDataChanged();
            spo2Chart.notifyDataSetChanged();
            spo2Chart.invalidate();
        }
    };

    private void setSpEntries() {
        spEntries.clear();
        for (int i = 0; i < spData.size(); i++) {
            setSpYRange(spData.get(i));
            spcount = DateUtils.differentSecondCalendar(spCalendars.get(0), spCalendars.get(i));
            spEntries.add(new Entry(spcount / 120, spData.get(i)));
        }
    }

    private void setSpYRange(Integer integer) {
        YAxis spo2ChartAxisLeft = spo2Chart.getAxisLeft();
        spo2ChartAxisLeft.resetAxisMinimum();
        spo2ChartAxisLeft.resetAxisMaximum();
        float max = (float) (((int)integer/10 + 1) * 10);
        spo2ChartAxisLeft.setAxisMaximum(max);
        spo2ChartAxisLeft.setAxisMinimum(max-10);
    }

    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int realTimePRData = bundle.getInt("realTimePRData");
            if (realTimePRData == 0){
                return;
            }
            tvPlusRate.setText(realTimePRData+"");
            if (isMoreScreen){
                prCalendars.remove(0);
                prData.remove(0);
                prCalendars.add(Calendar.getInstance());
                prData.add(realTimePRData);
                XAxis pluseRateChartXAxis = pluseRateChart.getXAxis();
                pluseRateChartXAxis.setValueFormatter(new TimeXFormatter(prCalendars.get(0)));
                setPrEntries();
            }else {
                prCalendars.add(Calendar.getInstance());
                prData.add(realTimePRData);
                setPrEntries();
            }
            prSet.setValues(prEntries);
            pluseRateChart.getData().notifyDataChanged();
            pluseRateChart.notifyDataSetChanged();
            pluseRateChart.invalidate();
        }
    };

    private void setPrEntries() {
        prEntries.clear();
        for (int i = 0; i < prData.size(); i++){
            setPrYRange(prData.get(i));
            prcount = DateUtils.differentSecondCalendar(prCalendars.get(0),prCalendars.get(i));
            prEntries.add(new Entry(prcount/120, prData.get(i)));
        }
    }

    private void setPrYRange(Integer integer) {
        YAxis pluseRateChartAxisLeft = pluseRateChart.getAxisLeft();
        pluseRateChartAxisLeft.resetAxisMaximum();
        pluseRateChartAxisLeft.resetAxisMinimum();
        float max = (float) (((int)integer/20 + 1) * 20);
        pluseRateChartAxisLeft.setAxisMaximum(max);
        pluseRateChartAxisLeft.setAxisMinimum(max-20);
    }


    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            isMoreScreen = true;
        }
    };
    private boolean isMoreScreen = false;

    private W314BleConService.ScanBleBinder binder;
    private ServiceConnection bleService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (W314BleConService.ScanBleBinder) service;
            binder.onRealTimeStart();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected int contentViewID() {
        return R.layout.fragment_wpo_real_time;
    }

    @Override
    protected void initialize() {
        onRegisterReceiver();
        initChart();
        mHandler.postDelayed(mRunnable,6*60*1000);
    }

    private void initChart() {
        mCalendar = Calendar.getInstance();
        setSpO2Chart();
        setPluseRateChart();
    }

    private void setPluseRateChart() {
        pluseRateChart.setBackgroundColor(Color.WHITE);
        pluseRateChart.setDrawGridBackground(true);
        pluseRateChart.setDrawBorders(false);
        pluseRateChart.setGridBackgroundColor(Color.WHITE);
        pluseRateChart.getDescription().setEnabled(false);
        pluseRateChart.fitScreen();
        pluseRateChart.setScaleEnabled(false);
        pluseRateChart.getAxisRight().setEnabled(false);
        pluseRateChart.setTouchEnabled(false);

        YAxis pluseRateChartAxisLeft = pluseRateChart.getAxisLeft();
        pluseRateChartAxisLeft.setAxisMaximum(100);
        pluseRateChartAxisLeft.setAxisMinimum(80);
        pluseRateChartAxisLeft.setLabelCount(4);

        XAxis pluseRateChartXAxis = pluseRateChart.getXAxis();
        pluseRateChartXAxis.setTextSize(8f);
        pluseRateChartXAxis.setAxisMaximum(3);
        pluseRateChartXAxis.setAxisMinimum(0);
        pluseRateChartXAxis.setLabelCount(3);

        pluseRateChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        pluseRateChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));

        prSet = new LineDataSet(prEntries,getString(R.string.pulse_rate));
        prSet.setColor(Color.rgb(138,43,226));
        prSet.setDrawCircles(false);
        prSet.setDrawValues(false);
        LineData prlineData = new LineData(prSet);
        pluseRateChart.setData(prlineData);

    }

    private void setSpO2Chart() {
        spo2Chart.setBackgroundColor(Color.WHITE);
        spo2Chart.setDrawGridBackground(true);
        spo2Chart.setDrawBorders(false);
        spo2Chart.setGridBackgroundColor(Color.WHITE);
        spo2Chart.getDescription().setEnabled(false);
        spo2Chart.fitScreen();
        spo2Chart.setScaleEnabled(false);
        spo2Chart.getAxisRight().setEnabled(false);
        spo2Chart.setTouchEnabled(false);

        YAxis spo2ChartAxisLeft = spo2Chart.getAxisLeft();
        spo2ChartAxisLeft.setAxisMaximum(100);
        spo2ChartAxisLeft.setAxisMinimum(90);
        spo2ChartAxisLeft.setLabelCount(5);
        spo2ChartAxisLeft.setValueFormatter(new Spo2YFormatter());

        XAxis spo2ChartXAxis = spo2Chart.getXAxis();
        spo2ChartXAxis.setTextSize(8f);
        spo2ChartXAxis.setAxisMaximum(3);
        spo2ChartXAxis.setAxisMinimum(0);
        spo2ChartXAxis.setLabelCount(3);
        spo2ChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        spo2ChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));

        spSet = new LineDataSet(spEntries,getString(R.string.spo2));
        spSet.setColor(Color.rgb(138,43,226));
        spSet.setDrawCircles(false);
        spSet.setDrawValues(false);
        LineData splineData = new LineData(spSet);
        spo2Chart.setData(splineData);
    }



    private void onRegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("W314BleConService:realTimeSpoData");
        getActivity().registerReceiver(broadcastReceiver,filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("W314BleConService:realTimePRData");
        getActivity().registerReceiver(broadcastReceiver1,filter1);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            getActivity().bindService(new Intent(getContext(), W314BleConService.class), bleService, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
        try {
            binder.onRealTimeEnd();
            binder.cancelConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().unbindService(bleService);
        mHandler.removeCallbacks(mRunnable);
    }


}
