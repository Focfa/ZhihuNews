package com.example.zhihunews.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.zhihunews.APP;

import java.security.Key;

/**
 * Created by Lxq on 2016/5/25.
 */
public class SPUtils {
    private static Context context = APP.getContext();
    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    private static SharedPreferences.Editor editor = preferences.edit();

    public static void save(String key,String value) {
        editor.putString(key,value);
        editor.apply();
    }

    public static void save(String key,Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void save(String key,int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static String get(String Key,String defaultValue) {
        return preferences.getString(Key,defaultValue);
    }

    public static Boolean get(String key,Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static int get(String key,int defaultValue) {
        return preferences.getInt(key,defaultValue);
    }

    //归并排序
    public static void mergeSort(int[] arr){
        long lasttime = System.currentTimeMillis();
        int[] temp =new int[arr.length];
        internalMergeSort(arr, temp, 0, arr.length-1);
        long a = System.currentTimeMillis()-lasttime;
      //  Log.d("Test", "MergeSort time is  " + a);
    }
    private static void internalMergeSort(int[] a, int[] b, int left, int right){
        //当left==right的时，已经不需要再划分了
        if (left<right){
            int middle = (left+right)/2;
            internalMergeSort(a, b, left, middle);          //左子数组
            internalMergeSort(a, b, middle+1, right);       //右子数组
            mergeSortedArray(a, b, left, middle, right);    //合并两个子数组
        }
    }
    // 合并两个有序子序列 arr[left, ..., middle] 和 arr[middle+1, ..., right]。temp是辅助数组。
    private static void mergeSortedArray(int arr[], int temp[], int left, int middle, int right){
        int i=left;
        int j=middle+1;
        int k=0;
        while ( i<=middle && j<=right){
            if (arr[i] <=arr[j]){
                temp[k++] = arr[i++];
            }
            else{
                temp[k++] = arr[j++];
            }
        }
        while (i <=middle){
            temp[k++] = arr[i++];
        }
        while ( j<=right){
            temp[k++] = arr[j++];
        }
        //把数据复制回原数组
        for (i=0; i<k; ++i){
            arr[left+i] = temp[i];
        }
    }
}
