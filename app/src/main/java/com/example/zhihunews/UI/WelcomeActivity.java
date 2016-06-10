package com.example.zhihunews.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zhihunews.R;

/**
 * Created by Lxq on 2016/5/28.
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageView welcome_image;
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


    }


    private void loadImage() {

    }


    @Override
    public void onBackPressed() {
        //disable back button when showing splash
    }
}
