package com.example.zhihunews.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Model.ThemeStory;
import com.example.zhihunews.Model.ZhihuCentent;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.DB;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.Utils.ShowTips;

import java.io.Serializable;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Lxq on 2016/6/9.
 */

public class FragmentThemeDetail extends Fragment {
    private View rootView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FrameLayout web_container;
    private List<ThemeStory> storyList;
    private int pisition;
    private ThemeStory themeStory;
    private WebView webView;
    private ZhihuCentent zhihuCentent;
    private Realm realm;
    private long lastGetTime;
    private static final int GER_DURATION = 2000;


    public static FragmentThemeDetail newInstance(int position, List<ThemeStory> storyList) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION,position);
        bundle.putSerializable(Constants.LIST,(Serializable)storyList);
        FragmentThemeDetail fragmentThemeDetail = new FragmentThemeDetail();
        fragmentThemeDetail.setArguments(bundle);
        return  fragmentThemeDetail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_theme_detail,container,false);
        }
      //  progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
      //  toolbar = (Toolbar) rootView.findViewById(R.id.common_toolbar);
     //   web_container = (FrameLayout) rootView.findViewById(R.id.webview_container);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_theme_detail);
        toolbar = (Toolbar) rootView.findViewById(R.id.fragment_theme_toolbar);
        web_container = (FrameLayout) rootView.findViewById(R.id.webview_container);
        initWebView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        lastGetTime = System.currentTimeMillis();
        getContent(themeStory.getId());
    }


    private void initData() {

        if(getArguments() != null) {
            pisition = getArguments().getInt(Constants.POSITION);
            storyList = (List<ThemeStory>) getArguments().getSerializable(Constants.LIST);
            themeStory = storyList.get(pisition);
        }
        toolbar.setTitle(themeStory.getTitle());
        final AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        Log.d("fragmentthemeDetail","titel is" + themeStory.getTitle());
    }

    private void getContent(final int contentid) {
        VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.BASE_URL_ZHIHU+contentid,ZhihuCentent.class,
                new Response.Listener<ZhihuCentent>() {
                    @Override
                    public void onResponse(ZhihuCentent response) {
                        // Log.d("getcontent",response.getTitle());
                        // zhihuCentent = response;

                      //  if(zhihuCentent == null) {
                            showContent(response);
                     //   }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(System.currentTimeMillis() - lastGetTime < GER_DURATION) {
                            getContent(contentid);
                            return;
                        }
                        error.printStackTrace();
                        hideProgress();
                        ShowTips.showSnack(web_container, R.string.no_net);
                    }
                });
    }

    private void showContent(ZhihuCentent zhihuCentent) {
        //add css style to webView
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + zhihuCentent.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = new WebView(getActivity());
        web_container.addView(webView);
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.setVisibility(View.VISIBLE);
                            hideProgress();
                        }
                    }, 100);

                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

}
