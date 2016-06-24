package com.example.zhihunews;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Adapter.tabsPagerAdapter;
import com.example.zhihunews.Model.ThemesListJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.UI.CommonFragment;
import com.example.zhihunews.UI.FragmentBeauty;
import com.example.zhihunews.UI.FragmentFresh;
import com.example.zhihunews.UI.FragmentNews;
import com.example.zhihunews.UI.PhoenixNewsFragment;
import com.example.zhihunews.Utils.SPUtils;
import com.example.zhihunews.Utils.ShowTips;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawLayout;
    private Toolbar toolbar;
    private boolean backPressed;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments ;
    private List<String> titles;
    private tabsPagerAdapter tabsPagerAdapter;
    public Realm realm;
    private String currentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.hotnews));
        initDrawer();
        initNavigationView();
        replaceFragment(FragmentNews.newInstance(""),CommonFragment.THEME_FIRSTPAGE);
     //   initTabLayoutAndPaper();
       // realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawer() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawerLayout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawLayout.setDrawerListener(toggle);
        toggle.syncState();
    }
    private void initNavigationView() {
       mNavigationView = (NavigationView) findViewById(R.id.nav_view);
       mNavigationView.setNavigationItemSelectedListener(this);
       mNavigationView.inflateMenu(R.menu.nav_menu);
       Menu menu = mNavigationView.getMenu();
       menu.getItem(0).setChecked(true);
    }

    private void initTabLayoutAndPaper() {
      //  mTabLayout = (TabLayout) findViewById(R.id.tabs);
     //   mViewPager = (ViewPager) findViewById(R.id.viewPaper_news);
        tabsPagerAdapter = new tabsPagerAdapter(getSupportFragmentManager());
        initFragments();
        mViewPager.setAdapter(tabsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id == R.id.nav_firstPage) {
            replaceFragment(FragmentNews.newInstance(""),CommonFragment.THEME_FIRSTPAGE);
            toolbar.setTitle(getResources().getString(R.string.hotnews));
        }else if(id == R.id.nav_internetSafe) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_ISAVE),CommonFragment.THEME_ISAVE);
            toolbar.setTitle(getResources().getString(R.string.internet_safe));
        }else if(id == R.id.nav_psychology) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_PSYCHOLOGY),CommonFragment.THEME_PSYCHOLOGY);
            toolbar.setTitle(getResources().getString(R.string.psychology));
        }else if(id == R.id.nav_userLike) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_USERLIKE),CommonFragment.THEME_USERLIKE);
            toolbar.setTitle(getResources().getString(R.string.userLike));
        }else if(id == R.id.nav_noBored) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_NOBORED),CommonFragment.THEME_NOBORED);
            toolbar.setTitle(getResources().getString(R.string.noBored));
        }else if(id == R.id.nav_design) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_DESIGN),CommonFragment.THEME_DESIGN);
            toolbar.setTitle(getResources().getString(R.string.design));
        }else if(id == R.id.nav_bigCompany) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_COMPANY),CommonFragment.THEME_COMPANY);
            toolbar.setTitle(getResources().getString(R.string.bigcompany));
        }else if(id == R.id.nav_movie) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_MOVIE),CommonFragment.THEME_MOVIE);
            toolbar.setTitle(getResources().getString(R.string.movie));
        }else if(id == R.id.nav_game) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_GAME),CommonFragment.THEME_GAME);
            toolbar.setTitle(getResources().getString(R.string.game));
        }else if (id == R.id.nav_music) {
            replaceFragment(CommonFragment.newInstance(CommonFragment.THEME_MUSIC),CommonFragment.THEME_MUSIC);
            toolbar.setTitle(getResources().getString(R.string.music));
        }else if( id == R.id.nav_phoenixNews) {
            replaceFragment(PhoenixNewsFragment.newInstance(""),getString(R.string.phoenixNews));
            toolbar.setTitle(getString(R.string.phoenixNews));
        }
         mDrawLayout.closeDrawer(GravityCompat.START);
         return true;
    }



    @Override
    public void onBackPressed() {
        if(mDrawLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawLayout.closeDrawer(GravityCompat.START);
        }else
             exitBy2Click();
    }

    private void exitBy2Click() {
        if(backPressed) {
            super.onBackPressed();
            return;
        }
        backPressed = true;
        ShowTips.showSnack(mDrawLayout,R.string.exit_app);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressed = false;
            }
        }, 2000);
    }

    public void replaceFragment(Fragment fragment, String themeType) {
        if(currentType != themeType ) {
            currentType = themeType;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main_container, fragment, themeType);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        fragments.add(FragmentNews.newInstance(""));
        fragments.add(FragmentFresh.newInstance(""));
        titles.add(getString(R.string.zhihunews));
        titles.add(getString(R.string.freshnews));
        tabsPagerAdapter.setFragments(fragments,titles);
    }

    public DrawerLayout getmDrawLayout() {
        return mDrawLayout;
    }
}
