package com.choicemmed.ichoice.healthcheck.fragment.ecg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.activity.CalenderSelectActivity;
import com.choicemmed.ichoice.healthcheck.activity.ecg.EcgAnalysisActivity;
import com.choicemmed.ichoice.healthcheck.adapter.EcgHistoryAdapter;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXRealCheckChartActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.EcgData;
import pro.choicemmed.datalib.EcgDataDao;

import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;
import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.analysis;

/**
 * Created by 117590 on 2020/8/20
 */
public class EcgHistoryFragment extends BaseFragment implements EcgHistoryAdapter.ItemClickListener {
    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.ecg_historical_list)
    RecyclerView recyclerView;
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

    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    private List<EcgData> ecgDataList;
    private EcgHistoryAdapter historyAdapter;
    private Calendar calendar;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("CalenderSelect")) {
                try {
                    int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                    int month = intent.getIntExtra("month", CalendarDay.today().getMonth());
                    int day = intent.getIntExtra("day", CalendarDay.today().getDay());
                    LogUtils.d(TAG, "year " + year + " month " + month + " day " + day);
                    calendar.set(year, month, day);
                    setTextDate();
                    refreshdata(calendar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume()");
        refreshdata(calendar);
        setTextDate();
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_ecg_historical_results;
    }

    @Override
    protected void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        calendar = Calendar.getInstance();
        List<EcgData> listAll = getDaoSession(getActivity()).getEcgDataDao().queryBuilder().where(EcgDataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).orderAsc(EcgDataDao.Properties.MeasureTime).build().list();
        LogUtils.d(TAG, listAll.size() + "");
        if (!listAll.isEmpty()) {
            beginDate = listAll.get(0).getMeasureTime();
        }
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private void refreshdata(Calendar c) {
        String dateTimeString = FormatUtils.getDateTimeString(c.getTime(), FormatUtils.template_Date);
        Log.d("sqltime", dateTimeString);
        ecgDataList = getDaoSession(getActivity()).getEcgDataDao().queryBuilder().where(EcgDataDao.Properties.MeasureTime.like("%" + dateTimeString + "%"), EcgDataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).orderDesc(EcgDataDao.Properties.MeasureTime).build().list();
        historyAdapter = new EcgHistoryAdapter(getActivity(), ecgDataList);
        historyAdapter.setListener(this);
        recyclerView.setAdapter(historyAdapter);
    }

    @OnClick({R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_left:
                Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
                if (calendar.before(beginCalendar) || isBegin(calendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, -1);
                refreshdata(calendar);
                setTextDate();
                break;

            case R.id.calendar_right:
                if (calendar.after(Calendar.getInstance()) || DateUtils.isToday(calendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, 1);
                refreshdata(calendar);
                setTextDate();
                break;

            case R.id.calender_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.Ecg);
                bundle.putInt("mode", 1);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            default:
        }
    }

    public static boolean isBegin(Calendar calendar) {
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DATE);
        Calendar begin = DateUtils.strToCalendar(beginDate);
        int nYear = begin.get(Calendar.YEAR);
        int nMonth = begin.get(Calendar.MONTH);
        int nDay = begin.get(Calendar.DAY_OF_MONTH);
        if (Year == nYear && Month == nMonth && Day == nDay) {
            return true;
        }
        return false;
    }

    public void changeArrowImage(Calendar calendar) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        if (isBegin(Calendar.getInstance())) {
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.right_gay);
            return;
        }
        if (DateUtils.isToday(calendar)) {
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.right_gay);
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        if (calendar.after(beginCalendar) && calendar.before(c)) {
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
        if (isBegin(calendar)) {
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
    }

    private void setTextDate() {
        tv_year.setText(calendar.get(Calendar.YEAR) + "");
        tv_month.setText(calendar.get(Calendar.MONTH) + 1 + "");
        tv_day.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        changeArrowImage(calendar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onItemViewClick(int position) {
        LogUtils.d(TAG, " position " + position);
        EcgData ecgData = ecgDataList.get(position);
        analysis(getContext(), ecgData);
    }
}
