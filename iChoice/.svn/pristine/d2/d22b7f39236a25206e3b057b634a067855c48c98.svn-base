package com.choicemmed.ichoice.healthcheck.fragment.bloodpressure;

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



public class BpHistoricalTrendFragment extends BaseFragment {
    private FragmentManager fm;
    private BpDayFragment bpDayFragment;
    private BpWeekFragment bpWeekFragment;
    private BpMonthFragment bpMonthFragment;
    private BpYearFragment bpYearFragment;
    @BindView(R.id.time_trend)
    TabLayout timeTabLayout;

    @BindView(R.id.noSrcollViewPage_trend)
    NoSrcollViewPage viewpager_trend;
    private List<String> titles=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected int contentViewID() {
        return R.layout.fragment_historical_trend;
    }

    @Override
    protected void initialize() {
        bpDayFragment = new BpDayFragment();
        bpWeekFragment = new BpWeekFragment();
        bpMonthFragment = new BpMonthFragment();
        bpYearFragment = new BpYearFragment();
        fragments.add(bpDayFragment);
        fragments.add(bpWeekFragment);
        fragments.add(bpMonthFragment);
        fragments.add(bpYearFragment);

        titles.add(getActivity().getString(R.string.day));
        titles.add(getActivity().getString(R.string.week));
        titles.add(getActivity().getString(R.string.month));
        titles.add(getActivity().getString(R.string.year));

        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        viewpager_trend.setAdapter(adapter);
        timeTabLayout.setupWithViewPager(viewpager_trend);

    }


    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
            @Override
            public Fragment getItem(int position) {
            return fragments.get(position);
        }
            @Override
            public int getCount() {
            return fragments!=null?fragments.size():0;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
