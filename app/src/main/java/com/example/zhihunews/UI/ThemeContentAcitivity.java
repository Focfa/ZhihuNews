package com.example.zhihunews.UI;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.zhihunews.Model.ThemeStory;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lxq on 2016/6/9.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class ThemeContentAcitivity extends AppCompatActivity {
    private int position;
    private ThemeDetailPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private int currentPosition;
    private FrameLayout container;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_content);
        container = (FrameLayout) findViewById(R.id.container);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
      //  supportFinishAfterTransition();
        initViews();
       // Log.d("tag","执行到这里？");
    }


    private void initViews() {
        position = getIntent().getIntExtra(Constants.POSITION,0);
       // Log.d("tag","position = "+position);
        currentPosition = position-1;
        List<ThemeStory> storyList = (List<ThemeStory>) getIntent().getSerializableExtra(Constants.LIST);
       // Log.d("tag","title = "+storyList.get(currentPosition).getTitle());
        fragments = new ArrayList<>();

        for(int i = 0;i < storyList.size();i++) {
            fragments.add(FragmentThemeDetail.newInstance(i,storyList));
        }

        pagerAdapter = new ThemeDetailPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                  currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
             //   finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class ThemeDetailPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public ThemeDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public ThemeDetailPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("pageradapter","执行到这里？");
            return fragments.get(position);

        }

        @Override
        public int getCount() {
      //  Log.d("pageradapter","fragments size= "+fragments.size());
            return fragments.size();
        }

    }


}
