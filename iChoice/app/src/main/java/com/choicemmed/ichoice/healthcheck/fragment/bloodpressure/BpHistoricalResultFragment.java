package com.choicemmed.ichoice.healthcheck.fragment.bloodpressure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.choicemmed.ichoice.healthcheck.adapter.HistoryBpAdapter;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.Cbp1k1Data;


public class BpHistoricalResultFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.historical_list)
    RecyclerView historical_list;
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

    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;

    private static String beginDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
    private List<Cbp1k1Data> cbp1k1DataList;
    private HistoryBpAdapter historyBpAdapter;

    private Calendar calendar;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year",CalendarDay.today().getYear());
                int month = intent.getIntExtra("month",CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day",CalendarDay.today().getDay());
                calendar.set(year,month,day);
                setTextDate();
                refreshdata(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                refreshdata(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                refreshdata(calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected int contentViewID() {
        return R.layout.fragment_bp_historical_result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initialize() {
        historical_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        calendar = Calendar.getInstance();
        initReceiver();
        refreshdata(calendar);
        setTextDate();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_414141));

    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("UnitSelect");
        getActivity().registerReceiver(broadcastReceiver1,intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter("onMeasureResult");
        getActivity().registerReceiver(broadcastReceiver2,intentFilter2);
    }

    private void refreshdata(Calendar c) {
        Cbp1k1Operation cbp1k1Operation = new Cbp1k1Operation(getActivity());
        List<Cbp1k1Data> cbp1k1DataAll = cbp1k1Operation.queryBySyncState(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!cbp1k1DataAll.isEmpty()){
            beginDate = cbp1k1DataAll.get(0).getMeasureDateTime();
        }
        String dateTimeString = FormatUtils.getDateTimeString(c.getTime(), FormatUtils.template_Date);
        Log.d("sqltime",dateTimeString);
        cbp1k1DataList = cbp1k1Operation.queryByDate(dateTimeString, IchoiceApplication.getAppData().userProfileInfo.getUserId());
        historyBpAdapter = new HistoryBpAdapter(getActivity(),cbp1k1DataList);
        historical_list.setAdapter(historyBpAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.calendar_left,R.id.calendar_right,R.id.calender_select})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calendar_left:
                Calendar beginCalendar= DateUtils.strToCalendar(beginDate);
                if (calendar.before(beginCalendar) || isBegin(calendar)){
                    return;
                }
                calendar.add(Calendar.DATE, -1);
                refreshdata(calendar);
                setTextDate();
                break;

            case R.id.calendar_right:
                if (calendar.after(Calendar.getInstance()) || DateUtils.isToday(calendar)){
                    return;
                }
                calendar.add(Calendar.DATE, 1);
                refreshdata(calendar);
                setTextDate();
                break;

            case R.id.calender_select:
                Bundle bundle = new Bundle();
                bundle.putInt("type", DevicesType.BloodPressure);
                startActivity(CalenderSelectActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
                break;
                default:
        }
    }

    public static boolean isBegin(Calendar calendar){
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DATE);
        Calendar begin = DateUtils.strToCalendar(beginDate);
        int nYear = begin.get(Calendar.YEAR);
        int nMonth = begin.get(Calendar.MONTH);
        int nDay = begin.get(Calendar.DAY_OF_MONTH);
        if (Year == nYear&& Month == nMonth &&Day == nDay){
            return true;
        }
        return false;
    }

    public void changeArrowImage(Calendar calendar){
        Calendar beginCalendar= DateUtils.strToCalendar(beginDate);
        if (isBegin(Calendar.getInstance())){
            calendar_left.setImageResource(R.mipmap.left_gay);
            calendar_right.setImageResource(R.mipmap.right_gay);
            return;
        }
        if (DateUtils.isToday(calendar)){
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.right_gay);
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        if ( calendar.after(beginCalendar) && calendar.before(c)){
            calendar_left.setImageResource(R.mipmap.calendar_left);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
        if (isBegin(calendar)){
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
    public void onRefresh() {
        refreshdata(calendar);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
        getActivity().unregisterReceiver(broadcastReceiver2);
    }
}
