package com.choicemmed.ichoice.healthcheck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by Jiang nan on 2019/11/28 17:08.
 * @description
 **/
public class TitleFragmentPagerAdapter  extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList=null;
    private String[] titles;

    public TitleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,
                                     ArrayList<Fragment> fragmentList) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
    }
    public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,
                                     List<Fragment> fragmentList, String[] titles) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if (position < mFragmentList.size()) {
            fragment = mFragmentList.get(position);
        } else {
            fragment = mFragmentList.get(0);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0){
            return titles[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
