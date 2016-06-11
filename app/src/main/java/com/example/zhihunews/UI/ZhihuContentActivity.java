package com.example.zhihunews.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Model.ZhihuCentent;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.DB;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.Utils.ShowTips;

import io.realm.Realm;

/**
 * Created by Lxq on 2016/6/11.
 */

public class ZhihuContentActivity extends AppCompatActivity {
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageView headerImage;
    private FrameLayout web_container;
    private WebView webView;
    private String toolBarTitle;
    private int content_id;
    private String newsType;
    private ZhihuCentent zhihuCentent;
    private Realm realm;
    private long lastGetTime;
    private static final int GER_DURATION = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitivy_zhihu_content);
        realm = Realm.getDefaultInstance();
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_content);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBar = (ProgressBar) findViewById(R.id.progress);
        headerImage = (ImageView) findViewById(R.id.content_header_img);
        web_container = (FrameLayout) findViewById(R.id.web_container);

        Intent intent = getIntent();
        toolBarTitle = intent.getStringExtra(Constants.TITLE);
        content_id = intent.getIntExtra(Constants.ID, 0);
        newsType = intent.getStringExtra(Constants.NEWS_TYPE);
        toolbarLayout.setTitle(toolBarTitle);
        if(newsType.equals(Constants.HOT_NEWS) ) {
            zhihuCentent = DB.getById(realm, content_id, ZhihuCentent.class);
        }
        initWebView();
        if(zhihuCentent == null) {
            getContent(content_id,newsType);
            lastGetTime = System.currentTimeMillis();
        }else {
            showContent(zhihuCentent);
        }

    }

    private void getContent(final int contentid, final String newsType) {
        VolleyManager.getInstance(this).GetGsonRequest(this, API.BASE_URL_ZHIHU+contentid,ZhihuCentent.class,
                new Response.Listener<ZhihuCentent>() {
                    @Override
                    public void onResponse(ZhihuCentent response) {
                        // Log.d("getcontent",response.getTitle());
                        // zhihuCentent = response;
                        if(newsType.equals(Constants.HOT_NEWS)) {
                            DB.saveOrUpdate(realm, response);
                        }
                        if(zhihuCentent == null) {
                            showContent(response);
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(System.currentTimeMillis() - lastGetTime < GER_DURATION) {
                            getContent(content_id,newsType);
                            return;
                        }
                        error.printStackTrace();
                        hideProgress();
                        ShowTips.showSnack(web_container, R.string.no_net);
                    }
                });
    }

    private void showContent(ZhihuCentent zhihuCentent) {
        ImagerLoad.load(this,zhihuCentent.getImage(),headerImage);
        //add css style to webView
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + zhihuCentent.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = new WebView(this);
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
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
