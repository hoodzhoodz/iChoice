package com.choicemmed.ichoice.healthreport.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/3 14:31
 * 修改人：114100
 * 修改时间：2019/4/3 14:31
 * 修改备注：
 */
public class HealthReportFragment extends BaseFragment {

    @BindView(R.id.tv_measure)
     TextView tv_measure;
    @BindView(R.id.tv_trend)
     TextView tv_trend;
    @BindView(R.id.fragment_report)
    FrameLayout fragment_report;
    @BindView(R.id.report_measure)
    LinearLayout report_measure;
    @BindView(R.id.report_trend)
    LinearLayout report_trend;

    private ReportMeasureFragment report_measureFragment;
    private ReportTrendFragment report_trendFragment;
    private FragmentManager fm;
    public static Fragment getInstance() {
        Fragment fragment = new HealthReportFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void initialize() {
        tv_measure.performLongClick();
        report_measure.performClick();
    }
    //重置所有文本的选中状态
    private void setSelected(){
        tv_measure.setSelected(false);
        tv_trend.setSelected(false);
        report_measure.setSelected(false);
        report_trend.setSelected(false);
    }


    @OnClick({R.id.report_measure,R.id.report_trend})
    public void onClick(View v) {
        fm = getChildFragmentManager();
        FragmentTransaction  transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.report_measure:
                setSelected();
                tv_measure.setSelected(true);
                report_measure.setSelected(true);
                report_measureFragment = new ReportMeasureFragment();
                transaction.add(R.id.fragment_report,report_measureFragment);

                break;
            case R.id.report_trend:
                setSelected();
                tv_trend.setSelected(true);
                report_trend.setSelected(true);
                report_trendFragment = new ReportTrendFragment();
                transaction.add(R.id.fragment_report,report_trendFragment);
                break;
                default:
        }
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment: fragments){
            transaction.remove(fragment);
        }
        transaction.commit();
    }


}
