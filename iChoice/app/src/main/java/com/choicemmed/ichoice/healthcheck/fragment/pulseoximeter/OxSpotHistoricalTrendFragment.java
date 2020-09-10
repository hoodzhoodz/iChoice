package com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.custom.NoSrcollViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author gaofang
 * 点测历史记录趋势图
 */
public class OxSpotHistoricalTrendFragment extends BaseFragment {

    @BindView(R.id.time_trend)
    TabLayout timeTabLayout;
    @BindView(R.id.viewpage_ox_trend)
    NoSrcollViewPage viewPageTrend;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int contentViewID() {
        return R.layout.fragment_ox_spot_historical_trend;
    }

    @Override
    protected void initialize() {

        OxDayFragment oxDayFragment = new OxDayFragment();
        OxWeekFragment oxWeekFragment = new OxWeekFragment();
        OxMonthFragment oxMonthFragment = new OxMonthFragment();
        OxYearFragment oxYearFragment = new OxYearFragment();
        fragments.add(oxDayFragment);
        fragments.add(oxWeekFragment);
        fragments.add(oxMonthFragment);
        fragments.add(oxYearFragment);
        titles.add(getActivity().getString(R.string.day));
        titles.add(getActivity().getString(R.string.week));
        titles.add(getActivity().getString(R.string.month));
        titles.add(getActivity().getString(R.string.year));

        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        viewPageTrend.setAdapter(myAdapter);
        timeTabLayout.setupWithViewPager(viewPageTrend);
    }

    class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments != null ? fragments.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}

