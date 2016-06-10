package com.example.zhihunews;

import android.util.Log;

/**
 * Created by Lxq on 2016/6/1.
 */
public class Base {

    private String name = "base";

    public Base() {
        tellName();
        printName();
    }

    public void tellName() {
        Log.d("test","Base tell name: " + name);
    }

    public void printName() {
        Log.d("test","Base print name: " + name);
    }
}
