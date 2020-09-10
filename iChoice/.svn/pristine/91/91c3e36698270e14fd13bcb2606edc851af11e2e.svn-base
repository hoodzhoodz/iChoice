package com.choicemmed.ichoice.healthreport.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.adapter.TitleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ReportTrendFragment extends BaseFragment {

    private FragmentManager fm;
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;
    private YearFragment yearFragment;

    @BindView(R.id.time_trend)
    TabLayout timeTabLayout;
    @BindView(R.id.viewpager_trend)
    ViewPager viewpager_trend;

    public static Fragment getInstance() {
        Fragment fragment = new ReportTrendFragment();
        return fragment;
    }
    @Override
    protected int contentViewID() {
        return R.layout.fragment_report_trend;
    }

    @Override
    protected void initialize() {
        dayFragment = new DayFragment();
        weekFragment =  new WeekFragment();
        monthFragment = new MonthFragment();
        yearFragment = new YearFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(dayFragment);
        fragments.add(weekFragment);
        fragments.add(monthFragment);
        fragments.add(yearFragment);
        String[] titles = {getActivity().getString(R.string.day), getActivity().getString(R.string.week), getActivity().getString(R.string.month), getActivity().getString(R.string.year)};
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getFragmentManager(), fragments, titles);
        viewpager_trend.setAdapter(adapter);
        timeTabLayout.setupWithViewPager(viewpager_trend);

    }
}
