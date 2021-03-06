package com.choicemmed.ichoice.healthcheck.fragment.bloodpressure;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import pro.choicemmed.datalib.Cbp1k1Data;

public class BpMeasureFragment extends BaseFragment {

    @BindView(R.id.tv_systolic)
    TextView tv_systolic;
    @BindView(R.id.tv_diastolic)
    TextView tv_diastolic;
    @BindView(R.id.tv_pulse)
    TextView tv_pulse;
    @BindView(R.id.face1)
    ImageView face1;
    @BindView(R.id.face2)
    ImageView face2;
    @BindView(R.id.face3)
    ImageView face3;
    @BindView(R.id.sunit)
    TextView sunit;
    @BindView(R.id.dunit)
    TextView dunit;
    private Cbp1k1Operation cbp1k1Operation;
    int systolicPressure, diastolicPressure, pulseRate;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                setViewData();
                setTexts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    boolean isunit;
    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                isunit = intent.getBooleanExtra("isunit",false);
                setViewData();
                setTexts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void setTexts() {
        if (isunit){
            tv_systolic.setText(systolicPressure +"");
            tv_diastolic.setText(diastolicPressure +"");
            tv_pulse.setText(pulseRate +"");
            setUnitText(R.string.mmhg);
            Log.d("BpMeasureFragment_mmhg","高压：" + systolicPressure + " 低压：" + diastolicPressure + " 脉率：" + pulseRate);
        }else {
            systolicPressure = (int)(systolicPressure*0.133);
            diastolicPressure = (int)(diastolicPressure*0.133);
            tv_systolic.setText(systolicPressure +"");
            tv_diastolic.setText(diastolicPressure +"");
            tv_pulse.setText(pulseRate +"");
            Log.d("BpMeasureFragment_kpa","高压：" + systolicPressure + " 低压：" + diastolicPressure + " 脉率：" + pulseRate);
            setUnitText(R.string.kpa);
        }
    }

    private void setUnitText(int resId){
        String text = getActivity().getString(resId);
        sunit.setText(text);
        dunit.setText(text);
    }



    @Override
    protected int contentViewID() {
        return R.layout.fragment_bp_measure;
    }

    @Override
    protected void initialize() {
        cbp1k1Operation = new Cbp1k1Operation(getActivity());
        isunit = IchoiceApplication.getAppData().userProfileInfo.getIsUnit();
        initReceiver();
        setViewData();
        setTexts();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("onMeasureResult");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("UnitSelect");
        getActivity().registerReceiver(broadcastReceiver1,intentFilter1);
    }


    /**
     * 初始化血压显示
     */
    private void setViewData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String beginTime = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date);
        calendar.add(Calendar.DATE, 1);
        String endTime = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date);
        final Cbp1k1Data cbp1k1Data = cbp1k1Operation.queryByNowDate(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "",
                beginTime, endTime);
        if (cbp1k1Data != null){
            systolicPressure = cbp1k1Data.getSystolic();
            diastolicPressure =  cbp1k1Data.getDiastolic();
            pulseRate = cbp1k1Data.getPulseRate();
            faceAnimator(cbp1k1Data.getSystolic());
        }
    }

    private void faceAnimator(int value){
        ObjectAnimator objectAnimator;
        if (value < 90) {
            face2.setVisibility(View.GONE);
            face3.setVisibility(View.GONE);
            face1.setVisibility(View.GONE);
        }
        if (value >= 90 && value <= 130) {
            face2.setVisibility(View.GONE);
            face3.setVisibility(View.GONE);
            face1.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(face1,"translationX",0f,200F).setDuration(1000);
            objectAnimator.start();
        } else if (value > 130 && value <= 140) {
            face1.setVisibility(View.GONE);
            face3.setVisibility(View.GONE);
            face2.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(face2,"translationX",0f,380F).setDuration(1200);
            objectAnimator.start();
        }else if (value > 140) {
            face1.setVisibility(View.GONE);
            face2.setVisibility(View.GONE);
            face3.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(face3, "translationX", 0f,540F).setDuration(1400);
            objectAnimator.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
    }
}
