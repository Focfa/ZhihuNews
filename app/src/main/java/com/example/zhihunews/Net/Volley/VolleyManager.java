package com.example.zhihunews.Net.Volley;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zhihunews.APP;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Lxq on 2016/5/23.
 */
public class VolleyManager {
    private static VolleyManager mVolleyManager;
    private  RequestQueue mRequestQueue;

    private VolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, new Okhttp3Stack(new OkHttpClient()));
    }

    public synchronized static VolleyManager getInstance(Context context) {
        if(mVolleyManager==null) {
            mVolleyManager = new VolleyManager(context);
        }
        return mVolleyManager;
    }

    private <T> Request<T> add(Request<T> request) {
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * Get方法
     *
     * @param tag
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GetGsonRequest(Object tag, String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        if(tag!=null) {
            request.setTag(tag);
        }
        add(request);
        return request;
    }

    public <T>GsonRequest<T> GetGsonRequest(Object tag,String url, TypeToken<T> tTypeToken, Response.Listener<T> listener,
                                            Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url,tTypeToken,listener,errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }
    /**
     * Post方式1：Map参数
     *
     * @param tag
     * @param params
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> PostGsonRequest(Object tag, Map<String, String> params, String url,
                                              Class<T> clazz, Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * Post方式2：json字符串
     *
     * @param url
     * @param jsonObject
     * @param listener
     * @param errorListener
     */
    public void PostjsonRequest(Object tag, String url, JSONObject jsonObject, Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                listener, errorListener);
        jsonObjectRequest.setTag(tag);
        add(jsonObjectRequest);

    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
