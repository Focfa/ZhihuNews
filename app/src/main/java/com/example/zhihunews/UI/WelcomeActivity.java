package com.example.zhihunews.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.StartImageJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.Utils.NetUtils;
import android.support.v7.appcompat.R.anim;

/**
 * Created by Lxq on 2016/5/28.
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageView welcome_image;
    private TextView imageVersion;
    private String imageUrl;
    private long lastGetTime;
    private static final int DELAY_DURATION = 1500;
    private static final int GETIMAGE_DURATION = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeacitivity);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        welcome_image = (ImageView) findViewById(R.id.welcome_img);
        imageVersion = (TextView) findViewById(R.id.image_version);
        lastGetTime = System.currentTimeMillis();
        loadImage();
        startAppDelay();
    }

    private void loadImage() {

        if(NetUtils.isWifi(this)) {

            VolleyManager.getInstance(this).GetGsonRequest(this, API.START_IMAGE, StartImageJson.class,
                    new Response.Listener<StartImageJson>() {
                        @Override
                        public void onResponse(StartImageJson response) {
                            if(response != null) {
                                imageUrl = response.getImg();
                                imageVersion.setText(response.getText());
                                ImagerLoad.load(getApplication(),imageUrl,welcome_image);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(System.currentTimeMillis() - lastGetTime < GETIMAGE_DURATION ) {
                                loadImage();
                                return;
                            }
                            ImagerLoad.load(getApplication(),R.drawable.welcome,welcome_image);
                            error.printStackTrace();
                        }
                    });
        }else {
            ImagerLoad.load(getApplication(),R.drawable.welcome,welcome_image);
        }
    }


    @Override
    public void onBackPressed() {
        //disable back button when showing splash
    }

    private void startAppDelay() {
        welcome_image.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, DELAY_DURATION);
    }

    private void startApp() {
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(anim.abc_grow_fade_in_from_bottom, anim.abc_shrink_fade_out_from_bottom);
        finish(); // destroy itself
    }
}
