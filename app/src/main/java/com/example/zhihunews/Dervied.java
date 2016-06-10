package com.example.zhihunews;

import android.util.Log;

/**
 * Created by Lxq on 2016/6/1.
 */
public class Dervied extends Base  {


    private String name = "dervied";

    public Dervied() {
        tellName();
        printName();
    }

    public void tellName() {
        Log.d("Test","Dervied tell name: " + name);
    }

    public void printName() {
        Log.d("test","Dervied print name: " + name);
    }

    public static void main(String[] args){

        new Dervied();
    }
}
