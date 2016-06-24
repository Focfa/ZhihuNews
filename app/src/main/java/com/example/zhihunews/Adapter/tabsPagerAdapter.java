package com.example.zhihunews.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

/**
 * Created by Lxq on 2016/5/22.
 */
public class tabsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> stringTitles;
    public tabsPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments,List<String> titles) {
        this.fragmentList = fragments;
        this.stringTitles = titles;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringTitles.get(position);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }
}
