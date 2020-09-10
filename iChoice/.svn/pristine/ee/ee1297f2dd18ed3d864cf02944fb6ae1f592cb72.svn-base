package com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
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
import java.util.List;

import butterknife.BindView;


public class OXRealCheckFragment extends BaseFragment {
    static String TAG = OXRealCheckFragment.class.getSimpleName();
    @BindView(R.id.ox_pr)
    TextView ox_pr;
    @BindView(R.id.ox_sp02)
    TextView ox_sp02;
    @BindView(R.id.ox_pi)
    TextView ox_pi;
    @BindView(R.id.ox_rr)
    TextView ox_rr;
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

    private boolean isMoreScreen;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            isMoreScreen = true;
        }
    };
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCalendar = Calendar.getInstance();
            if (intent.getAction().equals("beginSaveAndChart")) {
                LogUtils.d(TAG, "beginSaveAndChart");

                isMoreScreen = false;
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 6 * 60 * 1000);
                spo2Chart.getXAxis().setValueFormatter(new TimeXFormatter(mCalendar));
                spData.clear();
                spCalendars.clear();

                rrChart.getXAxis().setValueFormatter(new TimeXFormatter(mCalendar));
                rrData.clear();
                rrCalendars.clear();
                pluseRateChart.getXAxis().setValueFormatter(new TimeXFormatter(mCalendar));
                prData.clear();
                prCalendars.clear();

            } else if (intent.getAction().equals("onOxRealtimeData")) {
                try {
                    refreshData(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private void refreshData(Intent intent) {
        int sp02 = intent.getExtras().getInt("spo2", 0);
        int pr = intent.getExtras().getInt("pr", 0);
        int rr = intent.getExtras().getInt("rr", 0);
//        pr=new Random().nextInt(250)+1;
        LogUtils.d(TAG, "pr" + pr);
        float pi = intent.getExtras().getFloat("pi", 0);
        if (sp02 != 0) {
            ox_sp02.setText(sp02 + "");
            if (isMoreScreen) {
                spCalendars.remove(0);
                spData.remove(0);
                spCalendars.add(mCalendar);
                spData.add(sp02);
                XAxis spo2ChartXAxis = spo2Chart.getXAxis();
                spo2ChartXAxis.setValueFormatter(new TimeXFormatter(spCalendars.get(0)));
            } else {
                spCalendars.add(mCalendar);
                spData.add(sp02);
            }
            setSpEntries();
            spSet.setValues(spEntries);
            spo2Chart.getData().notifyDataChanged();
            spo2Chart.notifyDataSetChanged();
            spo2Chart.invalidate();

        } else {
            ox_sp02.setText("--");
        }
        if (rr != 0) {
            ox_rr.setText(rr + "");
            if (isMoreScreen) {
                rrCalendars.remove(0);
                rrData.remove(0);
                rrCalendars.add(mCalendar);
                rrData.add(rr);
                XAxis xAxis = rrChart.getXAxis();
                xAxis.setValueFormatter(new TimeXFormatter(spCalendars.get(0)));
            } else {
                rrCalendars.add(mCalendar);
                rrData.add(rr);
            }
            setRREntries();
            rrSet.setValues(rrEntries);
            rrChart.getData().notifyDataChanged();
            rrChart.notifyDataSetChanged();
            rrChart.invalidate();

        } else {
            ox_rr.setText("--");
        }

        if (pr != 0) {
            ox_pr.setText(pr + "");
            if (isMoreScreen) {
                prCalendars.remove(0);
                prData.remove(0);
                prCalendars.add(mCalendar);
                prData.add(pr);
                XAxis pluseRateChartXAxis = pluseRateChart.getXAxis();
                pluseRateChartXAxis.setValueFormatter(new TimeXFormatter(prCalendars.get(0)));
                setPrEntries();

            } else {
                prCalendars.add(mCalendar);
                prData.add(pr);
                setPrEntries();
            }
            prSet.setValues(prEntries);
            pluseRateChart.getData().notifyDataChanged();
            pluseRateChart.notifyDataSetChanged();
            pluseRateChart.invalidate();


        } else {
            ox_pr.setText("--");
        }
        if (pi != 0) {
            ox_pi.setText(pi + "");
        } else {
            ox_pi.setText("--");
        }
    }

    private void setSpEntries() {
        spEntries.clear();
        for (int i = 0; i < spData.size(); i++) {
            setSpYRange(spData.get(i));
            spcount = DateUtils.differentSecondCalendar(spCalendars.get(0), spCalendars.get(i));
            spEntries.add(new Entry(spcount / 120, spData.get(i)));
        }
    }

    private void setRREntries() {
        rrEntries.clear();
        for (int i = 0; i < rrData.size(); i++) {
            rrcount = DateUtils.differentSecondCalendar(rrCalendars.get(0), rrCalendars.get(i));
            rrEntries.add(new Entry(rrcount / 120, rrData.get(i)));
        }
    }
    private void setSpYRange(Integer integer) {
        YAxis spo2ChartAxisLeft = spo2Chart.getAxisLeft();
        spo2ChartAxisLeft.resetAxisMinimum();
        spo2ChartAxisLeft.resetAxisMaximum();
        float max = (float) (((int) integer / 10 + 1) * 10);
        spo2ChartAxisLeft.setAxisMaximum(max);
        spo2ChartAxisLeft.setAxisMinimum(max - 10);
    }


    private void setPrEntries() {
        prEntries.clear();
        for (int i = 0; i < prData.size(); i++) {
            setPrYRange(prData.get(i));
            prcount = DateUtils.differentSecondCalendar(prCalendars.get(0), prCalendars.get(i));
            prEntries.add(new Entry(prcount / 120, prData.get(i)));
        }
    }

    private void setPrYRange(Integer integer) {
        YAxis pluseRateChartAxisLeft = pluseRateChart.getAxisLeft();
        pluseRateChartAxisLeft.resetAxisMaximum();
        pluseRateChartAxisLeft.resetAxisMinimum();

        if (integer >= 30 && integer < 50) {
            pluseRateChartAxisLeft.setAxisMaximum(50);
            pluseRateChartAxisLeft.setAxisMinimum(30);
            pluseRateChartAxisLeft.setLabelCount(2);
        } else if (integer >= 50 && integer < 120) {
            pluseRateChartAxisLeft.setAxisMaximum(120);
            pluseRateChartAxisLeft.setAxisMinimum(50);
            pluseRateChartAxisLeft.setLabelCount(7);
        } else if (integer >= 120 && integer < 200) {
            pluseRateChartAxisLeft.setAxisMaximum(200);
            pluseRateChartAxisLeft.setAxisMinimum(120);
            pluseRateChartAxisLeft.setLabelCount(8);
        } else if (integer >= 200 && integer < 250) {
            pluseRateChartAxisLeft.setAxisMaximum(250);
            pluseRateChartAxisLeft.setAxisMinimum(200);
            pluseRateChartAxisLeft.setLabelCount(5);
        } else {
            float max = (float) (((int) integer / 20 + 1) * 20);
            pluseRateChartAxisLeft.setAxisMaximum(max);
            pluseRateChartAxisLeft.setAxisMinimum(max - 20);
            pluseRateChartAxisLeft.setLabelCount(5);
        }

    }



    @Override
    protected int contentViewID() {
        return R.layout.fragment_ox_real_check;
    }

    @Override
    protected void initialize() {
        mCalendar = Calendar.getInstance();
        initReceiver();
        initChart();
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
        rrChart.fitScreen();
        rrChart.setScaleEnabled(false);
        rrChart.getAxisRight().setEnabled(false);
        rrChart.setTouchEnabled(false);

        YAxis rrChartAxisLeft = rrChart.getAxisLeft();
        rrChartAxisLeft.setAxisMaximum(80);
        rrChartAxisLeft.setAxisMinimum(0);
        rrChartAxisLeft.setLabelCount(8);

        XAxis rrChartXAxis = rrChart.getXAxis();
        rrChartXAxis.setTextSize(8f);
        rrChartXAxis.setAxisMaximum(3);
        rrChartXAxis.setAxisMinimum(0);
        rrChartXAxis.setLabelCount(3);
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

        spSet = new LineDataSet(spEntries, getString(R.string.spo2));
        spSet.setColor(Color.rgb(138, 43, 226));
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
        pluseRateChartAxisLeft.setAxisMaximum(120);
        pluseRateChartAxisLeft.setAxisMinimum(50);
        pluseRateChartAxisLeft.setLabelCount(7);

        XAxis pluseRateChartXAxis = pluseRateChart.getXAxis();
        pluseRateChartXAxis.setTextSize(8f);
        pluseRateChartXAxis.setAxisMaximum(3);
        pluseRateChartXAxis.setAxisMinimum(0);
        pluseRateChartXAxis.setLabelCount(3);

        pluseRateChartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        pluseRateChartXAxis.setValueFormatter(new TimeXFormatter(mCalendar));

        prSet = new LineDataSet(prEntries, getString(R.string.pulse_rate));
        prSet.setColor(Color.rgb(138, 43, 226));
        prSet.setDrawCircles(false);
        prSet.setDrawValues(false);
        LineData prlineData = new LineData(prSet);
        pluseRateChart.setData(prlineData);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("onOxRealtimeData");
        intentFilter.addAction("beginSaveAndChart");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        mHandler.removeCallbacks(mRunnable);
    }

}
