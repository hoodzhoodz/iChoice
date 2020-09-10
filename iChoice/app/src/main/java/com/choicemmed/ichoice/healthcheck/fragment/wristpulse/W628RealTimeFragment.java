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
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.healthcheck.custom.Spo2YFormatter;
import com.choicemmed.ichoice.healthcheck.custom.TimeXFormatter;
import com.choicemmed.ichoice.healthcheck.service.W628BleConService;
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

public class W628RealTimeFragment extends BaseFragment {
    public static final String TAG = "W628RealTimeFragment";
    @BindView(R.id.pirrRelative)
    RelativeLayout pirrRelativeLayout;
    @BindView(R.id.RRCard)
    CardView rrCard;

    @BindView(R.id.spo2_value)
    TextView tvSpo2;
    @BindView(R.id.pluse_rate_value)
    TextView tvPlusRate;
    @BindView(R.id.pi_value)
    TextView tvPI;
    @BindView(R.id.rr_value)
    TextView tvRR;
    @BindView(R.id.spo2_chart)
    LineChart spo2Chart;
    @BindView(R.id.pluse_rate_chart)
    LineChart pluseRateChart;
    @BindView(R.id.rr_chart)
    LineChart rrChart;

    private static float spcount = 0;
    private static float prcount = 0;
    private static float rrcount = 0;

    List<Entry> spEntries = new ArrayList<>();
    List<Entry> prEntries = new ArrayList<>();
    List<Entry> rrEntries = new ArrayList<>();
    LineDataSet spSet, prSet, rrSet;
    Calendar mCalendar;

    List<Calendar> spCalendars = new ArrayList<>();
    List<Calendar> prCalendars = new ArrayList<>();
    List<Calendar> rrCalendars = new ArrayList<>();

    List<Integer> spData = new ArrayList<>();
    List<Integer> prData = new ArrayList<>();
    List<Integer> rrData = new ArrayList<>();

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            isMoreScreen = true;
        }
    };
    private boolean isMoreScreen = false;

    private W628BleConService.ScanBleBinder binder;
    private ServiceConnection bleService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (W628BleConService.ScanBleBinder) service;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binder.onRealTimeStart();
                }
            }, 1000);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("W628BleConService:onDisconnected")) {
                MethodsUtils.showErrorTip(getContext(), getString(R.string.device_disconnect));
            }

        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int realTimeSpoData = bundle.getInt("realTimeSpoData");
            if (realTimeSpoData == 0){
                tvSpo2.setText("--");
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
                tvPlusRate.setText("--");
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


    BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            float realTimePIData = bundle.getFloat("realTimePIData");
            if (realTimePIData == 0){
                tvPI.setText("-.-");
                return;
            }
            tvPI.setText(realTimePIData+"");
        }
    };


    BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int realTimeRRData = bundle.getInt("realTimeRRData");
            if (realTimeRRData == 0){
                tvRR.setText("--");
                return;
            }
            tvRR.setText(realTimeRRData+"");
            if (isMoreScreen){
                rrCalendars.remove(0);
                rrData.remove(0);
                rrCalendars.add(Calendar.getInstance());
                rrData.add(realTimeRRData);
                XAxis rrChartXAxis = rrChart.getXAxis();
                rrChartXAxis.setValueFormatter(new TimeXFormatter(rrCalendars.get(0)));
                setRREntries();
            }else {
                rrCalendars.add(Calendar.getInstance());
                rrData.add(realTimeRRData);
                setRREntries();
            }
            rrSet.setValues(rrEntries);
            rrChart.getData().notifyDataChanged();
            rrChart.notifyDataSetChanged();
            rrChart.invalidate();
        }
    };

    private void setRREntries() {
        rrEntries.clear();
        for (int i = 0; i < rrData.size(); i++){
            setRRYRange(rrData.get(i));
            rrcount = DateUtils.differentSecondCalendar(rrCalendars.get(0),rrCalendars.get(i));
            rrEntries.add(new Entry(rrcount/120, rrData.get(i)));
        }
    }

    private void setRRYRange(Integer integer) {
        YAxis rrChartAxisLeft = rrChart.getAxisLeft();
        rrChartAxisLeft.resetAxisMaximum();
        rrChartAxisLeft.resetAxisMinimum();
        float max = (float) (((int)integer/70 + 1) * 70);
        rrChartAxisLeft.setAxisMaximum(max);
        rrChartAxisLeft.setAxisMinimum(max-70);
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_wpo_real_time;
    }

    @Override
    protected void initialize() {
        rrCard.setVisibility(View.VISIBLE);
        pirrRelativeLayout.setVisibility(View.VISIBLE);
        initChart();
        mHandler.postDelayed(mRunnable,6*60*1000);
    }

    private void initChart() {
        mCalendar = Calendar.getInstance();
        setSpO2Chart();
        setPluseRateChart();
        setRRChart();
        onRegisterReceiver();
    }

    private void onRegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("W628BleConService:realTimeSpoData");
        getActivity().registerReceiver(broadcastReceiver,filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("W628BleConService:realTimePRData");
        getActivity().registerReceiver(broadcastReceiver1,filter1);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("W628BleConService:realTimePIData");
        getActivity().registerReceiver(broadcastReceiver2,filter2);
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction("W628BleConService:realTimeRRData");
        getActivity().registerReceiver(broadcastReceiver3,filter3);

        IntentFilter intentFilter5 = new IntentFilter("W628BleConService:onDisconnected");
        getActivity().registerReceiver(broadcastReceiver5, intentFilter5);

    }

    private void setRRChart() {
        rrChart.setBackgroundColor(Color.WHITE);
        rrChart.setDrawGridBackground(true);
        rrChart.setDrawBorders(false);
        rrChart.setGridBackgroundColor(Color.WHITE);
        rrChart.getDescription().setEnabled(false);
        rrChart.fitScreen();
        rrChart.setScaleEnabled(false);
        rrChart.getAxisRight().setEnabled(false);
        rrChart.setTouchEnabled(false);

        YAxis rrChartAxisLeft = rrChart.getAxisLeft();
        rrChartAxisLeft.setAxisMaximum(70);
        rrChartAxisLeft.setAxisMinimum(0);
        rrChartAxisLeft.setLabelCount(7);

        XAxis rrChartXAxis = rrChart.getXAxis();
        rrChartXAxis.setTextSize(8f);
        rrChartXAxis.setAxisMaximum(3);
        rrChartXAxis.setAxisMinimum(0);
        rrChartXAxis.setLabelCount(3);
        rrChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        rrChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));

        rrSet = new LineDataSet(rrEntries,getString(R.string.rr));
        rrSet.setColor(Color.rgb(138,43,226));
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

    @Override
    public void onStart() {
        super.onStart();
        try {
            getActivity().bindService(new Intent(getContext(), W628BleConService.class), bleService, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
        getActivity().unregisterReceiver(broadcastReceiver2);
        getActivity().unregisterReceiver(broadcastReceiver3);
        getActivity().unregisterReceiver(broadcastReceiver5);
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
