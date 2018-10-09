package com.xxl.hellomeizi.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class MeiZiPagerAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public MeiZiPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
        if (mTitles == null) {
            mTitles = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
