package com.choicemmed.ichoice.healthcheck.fragment.wristpulse;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.framework.widget.EventDecorator;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.ReportWpoActivity;
import com.choicemmed.ichoice.healthcheck.adapter.W628HistorySleepAdapter;
import com.choicemmed.ichoice.healthcheck.db.W628Operation;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import pro.choicemmed.datalib.W628Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class W628HistoricalRecordFragment extends BaseFragment implements OnDateSelectedListener {
    public static final String TAG = "W628HistoricalRecordFragment";
    @BindView(R.id.trend_materialCalendarView)
    MaterialCalendarView widget;
    private List<CalendarDay> calendarDays = new ArrayList<>();
    @BindView(R.id.historical_record_list)
    RecyclerView historicalList;
    private W628Operation w628Operation = new W628Operation(getContext());
    private W628HistorySleepAdapter w628HistorySleepAdapter;

    @Override
    protected int contentViewID() {
        return R.layout.fragment_wpo_historical_trend;
    }

    @Override
    protected void initialize() {
        historicalList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        initMaterialCalendar();
        initDate();
        refeshData(Calendar.getInstance());

    }

    @SuppressLint("LongLogTag")
    private void refeshData(Calendar calendar) {
        String dateTimeString = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date);
        final List<W628Data> w628DataList = w628Operation.queryByUserDate(IchoiceApplication.getAppData().userProfileInfo.getUserId(), dateTimeString);
        Log.d(TAG, "refeshData: "+ w628DataList.toString());
        w628HistorySleepAdapter = new W628HistorySleepAdapter(getContext(), w628DataList);
        historicalList.setAdapter(w628HistorySleepAdapter);
        w628HistorySleepAdapter.setOnItemClickListener(new W628HistorySleepAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                if (!PermissionsUtils.isNetworkConnected(getContext())) {
                    MethodsUtils.showErrorTip(getActivity(),getString(R.string.no_signal));
                    return;
                }
                W628Data w628Data = w628DataList.get(position);
                Bundle bundle = new Bundle();
                Log.d(TAG, "w628Data: " + w628Data);
                bundle.putString("TYPE","W628");
                bundle.putString("UUID",w628Data.getUuid());
                startActivity(ReportWpoActivity.class, bundle);
            }
        });
    }

    //W628Data{uuid='f0daf586-495e-42df-a0fe-cf1ea8472ad7', userId='8242', startDate='2020-06-19 10:12:25', endDate='2020-06-19 10:16:13', series='', accountKey='41c8c43083bc4be28522cd5afa492e1f', upLoadFlag='false'}
//     [W628Data{uuid='0c214d86-73fe-4d60-b4f1-f3d43d551d5f', userId='8242', startDate='2020-06-19 10:20:59', endDate='2020-06-19 10:24:57', series='', accountKey='41c8c43083bc4be28522cd5afa492e1f', upLoadFlag='true'}, W628Data{uuid='f0daf586-495e-42df-a0fe-cf1ea8472ad7', userId='8242', startDate='2020-06-19 10:12:25', endDate='2020-06-19 10:16:13', series='', accountKey='41c8c43083bc4be28522cd5afa492e1f', upLoadFlag='false'}]
    private void initDate() {
        List<W628Data> w628DataList = w628Operation.queryByUser(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (w628DataList.isEmpty()){
            return;
        }
        for (W628Data w628Data: w628DataList){
            String DateString = w628Data.getStartDate();
            Date date = DateUtils.strToDate(DateString);
            calendarDays.add(CalendarDay.from(date));
        }
        widget.addDecorator(new EventDecorator(ContextCompat.getColor(getContext(),R.color.violet), calendarDays));
    }

    private void initMaterialCalendar() {
        widget.setSelectedDate(CalendarDay.today());
        String strDate = IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime();
        strDate = strDate.substring(6,strDate.length()-2);
        String beginDate = DateUtils.getDateToString(Long.parseLong(strDate), FormatUtils.template_DbDateTime);
        CalendarDay calendarDay = CalendarDay.from(DateUtils.strToCalendar(beginDate));
        widget.state().edit().setMinimumDate(calendarDay).commit();
        widget.state().edit().setMaximumDate(CalendarDay.today()).commit();
        widget.setOnDateChangedListener(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        refeshData(date.getCalendar());
    }
}
