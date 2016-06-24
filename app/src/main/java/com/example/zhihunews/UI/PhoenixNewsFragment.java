package com.example.zhihunews.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhihunews.Adapter.tabsPagerAdapter;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.R;

import java.lang.reflect.Array;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lxq on 2016/6/15.
 */

public class PhoenixNewsFragment extends Fragment {
    public static final String ARGUMENT = "argument";
    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private tabsPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> mTitles;
    private MainActivity mainActivity;

    public static PhoenixNewsFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        PhoenixNewsFragment phoenixNewsFragment = new PhoenixNewsFragment();
        phoenixNewsFragment.setArguments(bundle);
        return phoenixNewsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.content_main_phoenix,container,false);
        }
        initViews();
        return rootView;
    }

    private void initViews() {
        mainActivity = (MainActivity) getActivity();
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_phoenix);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_phoenix);
        pagerAdapter = new tabsPagerAdapter(getChildFragmentManager());
        initFragments();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initFragments() {
        mTitles = new ArrayList<>();
        fragments = new ArrayList<>();

        String [] titles = new String[]
                {getString(R.string.header_line),
                getString(R.string.silence),
                getString(R.string.travel),
                getString(R.string.showbiz),
                getString(R.string.sports),
                getString(R.string.read),
                getString(R.string.military),
                getString(R.string.beauty),
                getString(R.string.video)};
        mTitles = Arrays.asList(titles);
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_SYLB));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_KJ));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_LY));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_YL));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_TY));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_DS));
        fragments.add(PhoenixNewsFragmentsCommon.newsInstance(API.TYPE_JS));
        fragments.add(FragmentBeauty.newInstance(""));
        fragments.add(FragmentVideo.newInstance(""));
        if (fragments.size() != titles.length) {
            throw new IllegalArgumentException("You need add all fragments in "+getClass().getSimpleName());
        }
        pagerAdapter.setFragments(fragments,mTitles);
    }


}
