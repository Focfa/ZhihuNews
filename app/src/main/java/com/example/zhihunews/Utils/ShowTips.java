package com.example.zhihunews.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Lxq on 2016/5/22.
 */
public class ShowTips {

    public static void showSnack(View rootView,int textId) {

       if(rootView!=null) {
           Snackbar.make(rootView,textId,Snackbar.LENGTH_SHORT).show();
       }
    }

    public static void showSnackLong(View rootView,int textId) {

        if(rootView!=null) {
            Snackbar.make(rootView,textId,Snackbar.LENGTH_LONG).show();
        }
    }
}
