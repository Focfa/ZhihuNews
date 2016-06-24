package com.example.zhihunews.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Model.PhoenixNews.ContentBody;
import com.example.zhihunews.Model.PhoenixNews.PhoenixNewsContent;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;

/**
 * Created by Lxq on 2016/6/20.
 */

public class PhoenixNewsContentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FrameLayout webViewContainer;
    private String contentUrl;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoenix_content);
        initViews();

        Intent intent = getIntent();
        contentUrl = intent.getStringExtra(Constants.URL);
        Log.d("tag","contentUrl =" +contentUrl);
        initWebView();

        getContent(contentUrl);

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_content_phoenix);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBar = (ProgressBar) findViewById(R.id.progress_phoenix_content);
        webViewContainer = (FrameLayout) findViewById(R.id.webView_container_phoenix);

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView () {
        webView = new WebView(this);
        webViewContainer.addView(webView);
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

    private void getContent(String contentUrl) {
        VolleyManager.getInstance(this).GetGsonRequest(this, contentUrl, PhoenixNewsContent.class,
                new Response.Listener<PhoenixNewsContent>() {
                    @Override
                    public void onResponse(PhoenixNewsContent response) {
                        if(response != null) {
                            showContent(response);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        hideProgress();
                    }
                });
    }

    private void showContent(PhoenixNewsContent phoenixNewsContent) {
        //add css style to webView

        String header = "<h1> "+ phoenixNewsContent.getBody().getTitle() +"</h1>";
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/phoenix.css\" type=\"text/css\">";
        String js = "<script src= \"file:///android_asset/css/detail.js\"> </script>";
        String html = "<html><head>" + css +"</head><body>"+ header + phoenixNewsContent.getBody().getText() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
      //  webView.loadUrl(phoenixNewsContent.getBody().getWwwurl());
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

}
