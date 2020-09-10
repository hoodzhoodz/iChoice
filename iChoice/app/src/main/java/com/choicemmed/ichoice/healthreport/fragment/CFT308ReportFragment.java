package com.choicemmed.ichoice.healthreport.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.activity.CalenderSelectActivity;
import com.choicemmed.ichoice.healthcheck.adapter.CFT308HistoryAdapter1;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.CFT308DataDao;
import pro.choicemmed.datalib.DeviceDisplay;

import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;


public class CFT308ReportFragment extends BaseFragment {

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
    @BindView(R.id.rl)
    RecyclerView recyclerView;
    private Calendar calendar = Calendar.getInstance();
    String dateTimeString;
    private List<CFT308Data> cft308DataList;
    private CFT308HistoryAdapter1 cft308HistoryAdapter;
    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                int month = intent.getIntExtra("month", CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day", CalendarDay.today().getDay());
                calendar.set(year, month, day);
                setTextDate();
                setViewData(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    boolean isunit = IchoiceApplication.getAppData().userProfileInfo.getIsUnit();
    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                isunit = intent.getBooleanExtra("isunit", false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    public static Fragment getInstance() {
        Fragment fragment = new CFT308ReportFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_report_cft308;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        initReceiver();
        initBeginTime();
        setTextDate();
        setViewData(calendar);
    }

    private void initBeginTime() {
        String strDate = IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime();
        strDate = strDate.substring(6, strDate.length() - 2);
        beginDate = DateUtils.getDateToString(Long.parseLong(strDate), FormatUtils.template_DbDateTime);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("UnitSelect");
        getActivity().registerReceiver(broadcastReceiver1, intentFilter1);
    }


    private void setViewData(Calendar calendar) {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(getActivity());
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (deviceDisplay != null && deviceDisplay.getThermometer() != 0) {
            dateTimeString = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date);
            cft308DataList = getDaoSession(getActivity()).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId()), CFT308DataDao.Properties.MeasureTime.like(dateTimeString + "%")).orderDesc(CFT308DataDao.Properties.MeasureTime).list();
        } else {
            cft308DataList.clear();
        }

        cft308HistoryAdapter = new CFT308HistoryAdapter1(getActivity(), cft308DataList);
        recyclerView.setAdapter(cft308HistoryAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_left:
                Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
                if (calendar.before(beginCalendar) || isBegin(calendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, -1);
                setViewData(calendar);
                setTextDate();
                break;

            case R.id.calendar_right:
                if (calendar.after(Calendar.getInstance()) || DateUtils.isToday(calendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, 1);
                setViewData(calendar);
                setTextDate();
                break;

            case R.id.calender_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.Thermometer);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            default:
        }
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
        getActivity().unregisterReceiver(broadcastReceiver1);
    }
}
