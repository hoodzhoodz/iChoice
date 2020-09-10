package com.choicemmed.ichoice.healthcheck.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.framework.widget.EventDecorator;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.healthcheck.db.DeviceDisplayOperation;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;
import com.choicemmed.ichoice.healthcheck.db.W314B4Operation;
import com.choicemmed.ichoice.healthcheck.db.W628Operation;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.CFT308DataDao;
import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.OxRealTimeData;
import pro.choicemmed.datalib.OxRealTimeDataDao;
import pro.choicemmed.datalib.OxSpotData;
import pro.choicemmed.datalib.W314B4Data;
import pro.choicemmed.datalib.W628Data;

import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;

public class CalenderActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener, OnDateSelectedListener {
    private GestureDetector detector;
    private static final String TAG = "CalendarView";
    private MaterialCalendarView widget;
    List<CalendarDay> calendarDays = new ArrayList<>();
    private CalendarDay beginCalendarDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calender_select);
        detector = new GestureDetector(this, this);
        initView();
        initDate();
    }

    private void initDate() {
        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt("type")) {
            case DevicesType.All:
                initWristPluseOximeter();
                initBloodPressureDate();
                initTemperature();
                initPulseOximeter(bundle);
                widget.addDecorator(new EventDecorator(Color.GREEN, calendarDays));
                break;
            case DevicesType.BloodPressure:
                initBloodPressureDate();
                widget.addDecorator(new EventDecorator(getResources().getColor(R.color.orange), calendarDays));
                break;

            case DevicesType.WristPluseOximeter:
                initWristPluseOximeter();
                widget.addDecorator(new EventDecorator(getResources().getColor(R.color.violet), calendarDays));
                break;
            case DevicesType.Thermometer:
                initTemperature();
                widget.addDecorator(new EventDecorator(getResources().getColor(R.color.red_normal), calendarDays));
                break;
            case DevicesType.PulseOximeter:
                initPulseOximeter(bundle);
                widget.addDecorator(new EventDecorator(getResources().getColor(R.color.pulse_oximeter_blue), calendarDays));
                break;
            default:
        }
        initBeginTime();
        widget.state().edit().setMinimumDate(beginCalendarDay).commit();
        widget.setSelectedDate(DateUtils.strToCalendar(bundle.getString("current_date")));
    }

    /**
     * 初始化点测血氧日期数据
     *
     * @param bundle
     */
    private void initPulseOximeter(Bundle bundle) {
        if (bundle.getInt("mode") == 1) {
            List<OxRealTimeData> spotDataList = getDaoSession(this).getOxRealTimeDataDao().queryBuilder().where(OxRealTimeDataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).orderAsc(OxRealTimeDataDao.Properties.MeasureDateStartTime).build().list();
            if (spotDataList.isEmpty()) {
                return;
            }
            for (OxRealTimeData oxRealTimeData : spotDataList) {
                String strDate = oxRealTimeData.getCreateTime();
                Date date = DateUtils.strToDate(strDate);
                calendarDays.add(CalendarDay.from(date));
            }

        } else {

            OxSpotOperation spotOperation = new OxSpotOperation(this);
            List<OxSpotData> spotDataList = spotOperation.queryBySyncState(IchoiceApplication.getAppData().userProfileInfo.getUserId());
            if (spotDataList.isEmpty()) {
                return;
            }
            for (OxSpotData spotData : spotDataList) {
                String strDate = spotData.getCreateTime();
                Date date = DateUtils.strToDate(strDate);
                calendarDays.add(CalendarDay.from(date));
            }
        }


    }

    private void initTemperature() {
        DeviceDisplayOperation deviceDisplayOperation = new DeviceDisplayOperation(this);
        DeviceDisplay deviceDisplay = deviceDisplayOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (deviceDisplay != null && deviceDisplay.getThermometer() != 0) {
            List<CFT308Data> cft308DataList = getDaoSession(this).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId())).list();
            if (!cft308DataList.isEmpty()) {
                for (CFT308Data cft308Data : cft308DataList) {
                    String DataDate = cft308Data.getMeasureTime();
                    Date date = DateUtils.strToDate(DataDate);
                    calendarDays.add(CalendarDay.from(date));
                }
            }
        }

    }

    private void initBeginTime() {
        String strDate = IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime();
        strDate = strDate.substring(6, strDate.length() - 2);
        String beginDate = DateUtils.getDateToString(Long.parseLong(strDate), FormatUtils.template_DbDateTime);
        beginCalendarDay = CalendarDay.from(DateUtils.strToCalendar(beginDate));
    }

    private void initWristPluseOximeter() {
        W314B4Operation w314B4Operation = new W314B4Operation(this);
        List<W314B4Data> w314B4DataList = w314B4Operation.queryByUser(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        W628Operation w628Operation = new W628Operation(this);
        List<W628Data> w628DataList = w628Operation.queryByUser(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!w314B4DataList.isEmpty()) {
            for (W314B4Data w314B4Data : w314B4DataList) {
                String DataDate = w314B4Data.getStartDate();
                Date date = DateUtils.strToDate(DataDate);
                calendarDays.add(CalendarDay.from(date));
            }
        }

        if (!w628DataList.isEmpty()) {
            for (W628Data w628Data : w628DataList) {
                String DataDate = w628Data.getStartDate();
                Date date = DateUtils.strToDate(DataDate);
                calendarDays.add(CalendarDay.from(date));
            }
        }
    }

    private void initBloodPressureDate() {
        Cbp1k1Operation cbp1k1Operation = new Cbp1k1Operation(this);
        List<Cbp1k1Data> cbp1k1DataList = cbp1k1Operation.queryBySyncState(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (cbp1k1DataList.isEmpty()) {
            return;
        }
        for (Cbp1k1Data cbp1k1Data : cbp1k1DataList) {
            String DataDate = cbp1k1Data.getCreateTime();
            Date date = DateUtils.strToDate(DataDate);
            calendarDays.add(CalendarDay.from(date));
        }
    }

    private void initView() {
        widget = (MaterialCalendarView) findViewById(R.id.materialCalendarView);
        widget.setSelectedDate(CalendarDay.today());
        widget.state().edit().setMaximumDate(CalendarDay.today()).commit();
        widget.setOnDateChangedListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float minMove = 120;
        float minVelocity = 0;
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();
        if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) { // 下滑
            finish();
        }
        return false;
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Intent intent = new Intent("CalenderSelect");
        intent.putExtra("year", date.getYear());
        intent.putExtra("month", date.getMonth());
        intent.putExtra("day", date.getDay());
        sendBroadcast(intent);
        finish();
    }


}
