package com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import pro.choicemmed.datalib.OxSpotData;


public class OXSpotCheckFragment extends BaseFragment {
    static String TAG = OXSpotCheckFragment.class.getSimpleName();
    @BindView(R.id.tv_ox_spot_spo2_text)
    TextView tv_spo2Text;
    @BindView(R.id.tv_ox_spot_spo2)
    TextView tv_spo2;
    @BindView(R.id.tv_ox_spot_pr)
    TextView tv_pr;
    @BindView(R.id.tv_ox_spot_pi)
    TextView tv_pi;
    @BindView(R.id.tv_ox_spot_rr)
    TextView tvRr;
    private OxSpotOperation oxSpotOperation;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                setViewData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_ox_spot_check;
    }

    @Override
    protected void initialize() {
        setSpo2View();
        oxSpotOperation = new OxSpotOperation(getActivity());
        initReceiver();
        setViewData();
    }

    /**
     * 设置Spo2富文本样式
     */
    private void setSpo2View() {
        String strSpo2 = getActivity().getResources().getString(R.string.ox_spo2);
        SpannableString spannableString = new SpannableString(strSpo2);
        spannableString.setSpan(new AbsoluteSizeSpan(40), strSpo2.length() - 1, strSpo2.length(), 0);
        tv_spo2Text.setText(spannableString);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("onOxSpotMeasureResult");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    //初始化点测血氧数据显示
    private void setViewData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        OxSpotData oxSpotData = oxSpotOperation.queryByNow(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "");
        if (oxSpotData == null) {
            return;
        }
        Log.d(TAG, "setViewData: " + oxSpotData.toString() + "   " + IchoiceApplication.getAppData().userProfileInfo.toString());
        tv_spo2.setText(oxSpotData.getBloodOxygen() + "");
        tv_pr.setText(oxSpotData.getPulseRate() + "");
        if (oxSpotData.getPi() != 0) {
            tv_pi.setText(oxSpotData.getPi() + "");
        } else {
            tv_pi.setText(getActivity().getResources().getString(R.string.ox_no_data));
        }

        //显示RR值
        int rr = oxSpotData.getRR();
        tvRr.setText(rr > 0 ? String.valueOf(rr) : getActivity().getResources().getString(R.string.ox_no_data));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
