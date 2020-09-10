package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OxSpotHistoricalTrendFragment;
import com.choicemmed.ichoice.healthcheck.fragment.temperature.CFT308Fragment;
import com.choicemmed.ichoice.healthcheck.fragment.temperature.TemperatureHistoricalTrendFragment;
import com.choicemmed.ichoice.healthreport.fragment.CFT308ReportFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfraredThermometerActivity extends BaseActivty {

    @BindView(R.id.tv_sleep)
    TextView tvSleep;

    @BindView(R.id.tv_wpo_historical_trend)
    TextView tvHistoricaltrend;
    @BindView(R.id.temp_historicaltrend_tab_textview)
    TextView temp_historicaltrend_tab_textview;

    @BindView(R.id.line_sleep)
    LinearLayout lineSleep;
    @BindView(R.id.line_wpo_historical_trend)
    LinearLayout lineHistoricalTrend;
    @BindView(R.id.temp_historicaltrend)
    LinearLayout temp_historicaltrend;


    private CFT308Fragment cft308Fragment;
    private CFT308ReportFragment cft308ReportFragment;
    private TemperatureHistoricalTrendFragment trendFragment;
    private FragmentManager fManager;

    @Override
    protected int contentViewID() {
        return R.layout.activity_result_device_cft308;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.infrared_temperature), true);
        setLeftBtnFinish();
        setRightBtn(true, R.mipmap.setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DeviceSettingTempActivity.class);
//                startActivityFinish();
            }
        });
        ActivityUtils.addActivity(this);
        fManager = getSupportFragmentManager();
        tvSleep.performLongClick();
        lineSleep.performClick();
    }


    //重置所有文本的选中状态
    private void setSelected() {
        tvSleep.setSelected(false);
        tvHistoricaltrend.setSelected(false);
        lineSleep.setSelected(false);
        lineHistoricalTrend.setSelected(false);
        temp_historicaltrend_tab_textview.setSelected(false);
        temp_historicaltrend.setSelected(false);
    }

    @OnClick({R.id.line_sleep, R.id.line_wpo_historical_trend, R.id.temp_historicaltrend})
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        switch (view.getId()) {
            case R.id.line_sleep:
                setSelected();
                tvSleep.setSelected(true);
                lineSleep.setSelected(true);
                cft308Fragment = new CFT308Fragment();
                fragmentTransaction.add(R.id.fragment_result, cft308Fragment);
                break;
            case R.id.line_wpo_historical_trend:
                setSelected();
                tvHistoricaltrend.setSelected(true);
                lineHistoricalTrend.setSelected(true);
                cft308ReportFragment = new CFT308ReportFragment();
                fragmentTransaction.add(R.id.fragment_result, cft308ReportFragment);
                break;
            case R.id.temp_historicaltrend:
                setSelected();
                temp_historicaltrend_tab_textview.setSelected(true);
                temp_historicaltrend.setSelected(true);
                trendFragment = new TemperatureHistoricalTrendFragment();
                fragmentTransaction.add(R.id.fragment_result, trendFragment);
                break;
            default:
        }
        List<Fragment> fragments = fManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commit();
    }
}
