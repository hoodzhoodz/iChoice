package com.choicemmed.ichoice.healthcheck.activity.ecg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.view.EcgScaleViewSplit;
import com.choicemmed.ichoice.healthcheck.view.EcgViewSplit;

import butterknife.BindView;
import butterknife.OnClick;


public class EcgInfoBiggerActivity extends BaseActivty {
    @BindView(R.id.home_vEcgBarView1)
    EcgViewSplit home_vEcgBarView1;
    @BindView(R.id.home_vEcgBarView2)
    EcgViewSplit home_vEcgBarView2;
    @BindView(R.id.home_vEcgBarView3)
    EcgViewSplit home_vEcgBarView3;
    @BindView(R.id.home_vEcgScaleView1)
    EcgScaleViewSplit home_vEcgScaleView1;
    @BindView(R.id.home_vEcgScaleView2)
    EcgScaleViewSplit home_vEcgScaleView2;
    @BindView(R.id.home_vEcgScaleView3)
    EcgScaleViewSplit home_vEcgScaleView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int contentViewID() {
        return R.layout.activity_ecginfobigger;
    }

    @Override
    protected void initialize() {
        setTopTitle("", false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("ecgbundle");
        int[] ecgData = bundle.getIntArray("ecgdata");
        home_vEcgScaleView1.setText("0-10s");
        home_vEcgScaleView2.setText("10-20s");
        home_vEcgScaleView3.setText("20-30s");
        if ("A12".equals(IchoiceApplication.type)) {
            if (ecgData != null && ecgData.length >= 7500) {
                int[] one = new int[2500];
                int[] two = new int[2500];
                int[] three = new int[2500];
                System.arraycopy(ecgData, 0, one, 0, 2500);
                System.arraycopy(ecgData, 2500, two, 0, 2500);
                System.arraycopy(ecgData, 5000, three, 0, 2500);
                home_vEcgBarView1.redrawEcg(one);
                home_vEcgBarView2.redrawEcg(two);
                home_vEcgBarView3.redrawEcg(three);
            }
        } else if ("P10".equals(IchoiceApplication.type)) {
            if (ecgData != null && ecgData.length >= 7500) {
                int[] ecgData1 = new int[2500];
                int[] ecgData2 = new int[2500];
                int[] ecgData3 = new int[ecgData.length - 5000];
                for (int i = 0; i < ecgData.length; i++) {
                    if (i < 2500) {
                        ecgData1[i] = ecgData[i];
                    } else if (i < 5000) {
                        ecgData2[i - 2500] = ecgData[i];
                    } else {
                        ecgData3[i - 5000] = ecgData[i];
                    }
                }
                home_vEcgBarView1.redrawEcg(ecgData1);
                home_vEcgBarView2.redrawEcg(ecgData2);
                home_vEcgBarView3.redrawEcg(ecgData3);
            }

        }
    }

    @OnClick({R.id.home_imb_amplify})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_imb_amplify:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
